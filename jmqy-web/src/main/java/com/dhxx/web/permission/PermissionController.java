package com.dhxx.web.permission;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dhxx.common.sms.SingletonSMSClient;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.facade.service.transport.car.CarFacade;
import com.dhxx.facade.service.transport.driver.DriverFacade;
import com.dhxx.facade.service.user.UserFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.entity.menu.Menu;
import com.dhxx.facade.entity.permission.Permission;

import com.dhxx.facade.entity.score.Score;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.entity.user.Userper;
import com.dhxx.facade.service.company.CompanyFacade;
import com.dhxx.facade.service.menu.MenuFacade;
import com.dhxx.facade.service.permission.PermissionFacade;
import com.dhxx.facade.service.rule.RuleFacade;
import com.dhxx.facade.service.score.ScoreFacade;
import com.github.pagehelper.PageInfo;

/**
 * @author jhy
 * @date 2017/9/13
 * @description 权限管理
 */
@Controller
@RequestMapping("permission")
public class PermissionController {

    private static Logger log = LoggerFactory.getLogger(PermissionController.class);

    @Reference(protocol = "dubbo")
    private PermissionFacade permissionFacade;

    @Reference(protocol = "dubbo")
    private MenuFacade menuFacade;

    @Reference(protocol = "dubbo")
    private ScoreFacade scoreFacade;

    @Reference(protocol = "dubbo")
    private CompanyFacade companyFacade;

    @Reference(protocol = "dubbo")
    private RuleFacade ruleFacade;

    @Reference(protocol = "dubbo")
    private UserFacade userFacade;

    @Reference(protocol = "dubbo")
    private DriverFacade driverFacade;

    @Reference(protocol = "dubbo")
    private CarFacade carFacade;

    //企业用户列表
    @RequestMapping(value = "userList")
    public String userList(Userper user, Model model, HttpServletRequest requst) {
        if (user.getCompanyType() == null) {
            user.setCompanyType(1l);
        }
        PageInfo<Userper> users = (PageInfo<Userper>) permissionFacade.userper(user);
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        return "permission/userList";
    }

    //菜单列表
    @RequestMapping(value = "setPer")
    public String setPer(Userper user, Model model, HttpServletRequest requst) {
        Menu menu = new Menu();
        Long type = user.getCompanyType();
        if (type != null) {
            String role = (type == 0 ? "sys" : (type == 1 || type == 2 ? "charter" : "transport"));
            menu.setRemark(role);
        }
        List<Menu> menus = (List<Menu>) menuFacade.findMenus(menu);
        model.addAttribute("menus", menus);
        PageInfo<Menu> pageInfo = new PageInfo<Menu>(menus);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("user", user);
        List<Long> menuIds = (List<Long>) permissionFacade.menuIds(user.getRoleId());
        model.addAttribute("menuIds", menuIds);
        return "permission/setPer";
    }

    @ResponseBody
    @RequestMapping(value = "doSetPer")
    public Map<String, Object> doSetPer(Permission p) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            permissionFacade.save(p);
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

    //审核企业用户
    @RequestMapping(value = "examine")
    public String examine(Userper user, Model model, HttpServletRequest requst) {
        User u = (User) permissionFacade.findUser(user);
        model.addAttribute("user", u);
        Company company = new Company();
        company.setId(u.getCompanyId());
        List<Company> companys = (List<Company>) companyFacade.find(company);
        model.addAttribute("company", companys.get(0));
        return "permission/examine";
    }

    @ResponseBody
    @RequestMapping(value = "doExamine")
    public Map<String, Object> doExamine(Userper user) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            permissionFacade.examine(user);

            if (user.getCompanyStatus() == 0) {

                Score score = new Score();
                score.setUserId(user.getId());
                score.setOrderId(null);
                score.setTotalScore(0.0);
                score.setConsumeScore(0.0);

                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, 30);//计算30天后的时间
                Date date = c.getTime();

                score.setExpireDate(date);
                scoreFacade.save(score);

                try {
                    User us = new User();
                    us.setId(user.getId());
                    List<User> list = (List<User>) userFacade.find(us);
                    if (!CollectionUtils.isEmpty(list)) {
                        us = list.get(0);
                    }

                    //初始化菜单
                    String menuIds = "52,53,54,55";//menuMapper.findMenuIds();
                    Permission permission = new Permission();
                    permission.setIds(menuIds);
                    //公司ID+角色ID 确定唯一值
                    permission.setRoleId(Long.parseLong(us.getRole()));
                    permission.setCompanyId(us.getCompanyId()+"");
                    permissionFacade.save(permission);

                    SingletonSMSClient.getClient().sendSMS(new String[]{us.getPhone()}, "【马上走】您已经成功升级为企业用户！", "", 5);
                } catch (RemoteException e) {
                    log.error(e.getMessage());
                }

            }
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }


    //包车方状态禁用和启用
    @Transactional
    @ResponseBody
    @RequestMapping(value = "chartered")
    public Long chartered(Company company) {

        Long state = 1l;
        try {

            Car car = new Car();
            Driver driver = new Driver();
            car.setStatus(Integer.parseInt(company.getStatus() + ""));
            car.setCompanyCode(company.getCompanyCode());
            driver.setStatus(company.getStatus() + "");
            driver.setCompanyCode(company.getCompanyCode());
            driverFacade.updateById(driver);
            carFacade.updateById(car);


            User user = new User();
            user.setStatus(Integer.parseInt(company.getStatus() + ""));
            user.setCompanyCode(company.getCompanyCode());
            companyFacade.update(company);
            userFacade.update(user);
        } catch (Exception e) {
            state = -1l;
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return state;
    }


    @RequestMapping(value = "setType")
    public String setType(@RequestParam(value = "companyCode") String companyCode,
                          @RequestParam(value = "companyId") String companyId,
                          @RequestParam(value = "type") String type, Model model, HttpServletRequest requst) {

        Car car = new Car();
        Driver driver = new Driver();
        if (type.equals("1")) {
            car.setCompanyCode(companyCode);
            PageInfo<Car> cars = (PageInfo<Car>) carFacade.list(car);
            model.addAttribute("car", car);
            model.addAttribute("cars", cars);
        } else if (type.equals("2")) {

            driver.setCompanyCode(companyCode);
            PageInfo<Driver> drivers = (PageInfo<Driver>) driverFacade.list(driver);
            model.addAttribute("driver", driver);
            model.addAttribute("drivers", drivers);
        } else {
            car.setCompanyCode(companyCode);
            PageInfo<Car> cars = (PageInfo<Car>) carFacade.list(car);
            driver.setCompanyCode(companyCode);
            PageInfo<Driver> drivers = (PageInfo<Driver>) driverFacade.list(driver);
            model.addAttribute("car", car);
            model.addAttribute("cars", cars);
            model.addAttribute("driver", driver);
            model.addAttribute("drivers", drivers);
        }
        model.addAttribute("companyCode", companyCode);
        model.addAttribute("type", type);
        model.addAttribute("companyId", companyId);


        return "permission/companyList";
    }
}

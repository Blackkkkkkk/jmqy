package com.dhxx.web.sys;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.util.DataUtils;
import com.dhxx.facade.entity.additional.AdditionalNum;
import com.dhxx.facade.entity.charter.invoice.Invoice;
import com.dhxx.facade.entity.company.Company;
import com.dhxx.facade.entity.finance.Finance;
import com.dhxx.facade.entity.menu.Menu;
import com.dhxx.facade.entity.money.Money;
import com.dhxx.facade.entity.money.MoneyDetilBill;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.permission.Permission;
import com.dhxx.facade.entity.remind.Remind;
import com.dhxx.facade.entity.role.Role;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.facade.entity.user.User;
import com.dhxx.facade.service.additional.AdditionalNumFacade;
import com.dhxx.facade.service.charter.invoice.InvoiceFacade;
import com.dhxx.facade.service.company.CompanyFacade;
import com.dhxx.facade.service.finance.FinanceFacade;
import com.dhxx.facade.service.menu.MenuFacade;
import com.dhxx.facade.service.money.MoneyDetilBillFacade;
import com.dhxx.facade.service.money.MoneyFacade;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.permission.PermissionFacade;
import com.dhxx.facade.service.remind.RemindFacade;
import com.dhxx.facade.service.role.RoleFacade;
import com.dhxx.facade.service.transport.car.CarFacade;
import com.dhxx.facade.service.transport.driver.DriverFacade;
import com.dhxx.facade.service.user.UserFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.charter.center.CompanyFile;
import com.dhxx.web.shiro.ShiroDbRealm;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jhy
 * @date 2017/9/19
 * @description
 */
@Controller
@RequestMapping("sys")
public class SysController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(SysController.class);

    @Reference(protocol = "dubbo")
    private FinanceFacade financeFacade;

    @Reference(protocol = "dubbo")
    private RemindFacade remindFacade;

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;

    @Reference(protocol = "dubbo")
    private UserFacade userFacade;

    @Reference(protocol = "dubbo")
    private CompanyFacade companyFacade;

    @Reference(protocol = "dubbo")
    private RoleFacade roleFacade;

    @Reference(protocol = "dubbo")
    private PermissionFacade permissionFacade;

    @Reference(protocol = "dubbo")
    private MenuFacade menuFacade;

    @Reference(protocol = "dubbo")
    private InvoiceFacade invoiceFacade;

    @Reference(protocol = "dubbo")
    private AdditionalNumFacade additionalNumFacade;


    @Reference(protocol = "dubbo")
    private DriverFacade driverFacade;

    @Reference(protocol = "dubbo")
    private CarFacade carFacade;

    @Reference(protocol = "dubbo")
    private MoneyFacade moneyFacade;

    @Reference(protocol = "dubbo")
    private MoneyDetilBillFacade moneyDetilBillFacade;

    /**
     * ========================================个人中心==================================================
     **/
    //个人中心
    @RequestMapping(value = "center")
    public String finance(User user, Company company, Model model, HttpServletRequest request) {
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        user.setId(shiroUser.id);
        List<User> users = (List<User>) userFacade.find(user);
        if (!CollectionUtils.isEmpty(users)) {
            user = users.get(0);
        }
        model.addAttribute("user", user);
        company.setCompanyCode(shiroUser.companyCode);
        List<Company> companys = (List<Company>) companyFacade.find(company);
        if (!CollectionUtils.isEmpty(companys)) {
            company = companys.get(0);
        }
        model.addAttribute("company", company);
        return "sys/center";
    }

    //修改用户信息
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public String updateUser(User user, RedirectAttributes redirectAttributes) {
        String message = "更新成功@1";
        try {
            ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            user.setId(shiroUser.id);
            userFacade.update(user);
        } catch (Exception e) {
            message = message.replace("成功@1", "失败@2");
            log.error(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/sys/center";
    }

    //用户修改自己密码
    @ResponseBody
    @RequestMapping(value = "updatePw", method = RequestMethod.POST)
    public int updatePw(User user) {
        try {
            ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            User u = new User();
            u.setId(shiroUser.id);
            List<User> users = (List<User>) userFacade.find(u);
            if (!CollectionUtils.isEmpty(users)) {
                u = users.get(0);
            }
            String password = u.getUserPassword();
            u.setUserPassword(user.getUserPassword());
            ShiroUserUtils.checkPassword(u);
            if (!password.equals(u.getUserPassword())) {
                return -1;
            }
            user.setUserPassword(user.getNewPassword());
            user.setUserAccount(shiroUser.userAccount);
            user.setId(shiroUser.id);
            ShiroUserUtils.encryptPassword(user);
            userFacade.update(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -2;
        }
        //密码修改成功就登出
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.getSession() != null) {
            ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) currentUser.getPrincipal();
            if (shiroUser != null) {
                currentUser.logout();
                log.debug(shiroUser.getName() + ">>>>>>>>>>>>修改密码成功后，自动退出登录");
            }
        }
        return 0;
    }

    /**
     * ========================================用户管理==================================================
     **/
    //自己创建用户列表
    @RequestMapping(value = "user/list")
    public String userList(User user, Model model, HttpServletRequest requst) {
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        user.setCompanyCode(shiroUser.companyCode);
        user.setType(0);
        PageInfo<User> users = (PageInfo<User>) userFacade.list(user);
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        return "sys/userList";
    }

    @RequestMapping(value = "user/form")
    public String userForm(User user, Model model, HttpServletRequest requst) {

        if (user.getId() != null) {
            List<User> users = (List<User>) userFacade.find(user);
            if (!CollectionUtils.isEmpty(users)) {
                user = users.get(0);
            }
        }
        model.addAttribute("user", user);
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        Role role = new Role();
        role.setCompanyCode(shiroUser.companyCode);
        role.setRoleType(1);
        model.addAttribute("roles", roleFacade.find(role));
        return "sys/userForm";
    }

    //更新或者新增
    @ResponseBody
    @RequestMapping(value = "user/saveOrUpdate")
    public Map<String, Object> userSaveOrUpdate(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            if (user.getId() == null) {
                ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
                user.setCompanyCode(shiroUser.companyCode);
                user.setRegisterDate(new Date());
                user.setType(0);
                user.setStatus(0);
                user.setUserPassword("123456");
                ShiroUserUtils.encryptPassword(user);
                userFacade.save(user);
            } else {
                userFacade.update(user);
            }
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

    /*
    * status:
	* 0:启用 1:禁用 2:不通过
	*/
    @ResponseBody
    @RequestMapping(value = "user/updateById", method = RequestMethod.POST)
    public Long userUpdateById(User user, Model model) {
        try {

            return (Long) userFacade.update(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1l;
        }
    }

    /**
     * ========================================角色管理==================================================
     **/
    //运输方自己创建的角色列表
    @RequestMapping(value = "role/list")
    public String roleList(Role role, Model model, HttpServletRequest requst) {
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        role.setCompanyCode(shiroUser.companyCode);
        role.setRoleType(1);
        PageInfo<Role> roles = (PageInfo<Role>) roleFacade.list(role);
        model.addAttribute("role", role);
        model.addAttribute("roles", roles);
        return "sys/roleList";
    }

    //新增或者修改
    @RequestMapping(value = "role/form")
    public String roleForm(Role role, Model model, HttpServletRequest requst) {
        if (role.getId() != null) {
            List<Role> roles = (List<Role>) roleFacade.find(role);
            if (!CollectionUtils.isEmpty(roles)) {
                role = roles.get(0);
            }
        }
        model.addAttribute("role", role);
        return "sys/roleForm";
    }

    //更新或者新增
    @ResponseBody
    @RequestMapping(value = "role/saveOrUpdate")
    public Map<String, Object> roleSaveOrUpdate(Role role) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            if (role.getId() == null) {
                ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
                role.setCompanyCode(shiroUser.companyCode);
                role.setRoleType(1);
                role.setStatus(0);
                roleFacade.save(role);
            } else {
                roleFacade.update(role);
            }
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

    //角色授权
    @RequestMapping(value = "authorize")
    public String authorize(Role role, Model model, HttpServletRequest requst) {
        Menu menu = new Menu();
        menu.setRemark("sys");
        List<Menu> menus = (List<Menu>) menuFacade.findMenus(menu);
        model.addAttribute("menus", menus);
        PageInfo<Menu> pageInfo = new PageInfo<Menu>(menus);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("role", role);
        List<Long> menuIds = (List<Long>) permissionFacade.menuIds(role.getId());
        if (!menuIds.isEmpty()) {
            model.addAttribute("menuIds", (menuIds.toString().replaceAll(" ", "").replace("[", ",").replace("]", ",")));
        }
        return "sys/authorize";
    }

    //提交角色授权
    @ResponseBody
    @RequestMapping(value = "doAuthorize")
    public Map<String, Object> doAuthorize(Permission p) {
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

    /*
	* status:
	* 0:启用 1:禁用
	*/
    @ResponseBody
    @RequestMapping(value = "role/updateById", method = RequestMethod.POST)
    public Long roleUpdateById(Role role, Model model) {
        try {
            return (Long) roleFacade.update(role);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1l;
        }
    }

    /**
     * ========================================运输企业管理==================================================
     **/
    //运输方自己创建的角色列表
    @RequestMapping(value = "company/list")
    public String companyList(Company company, Model model, HttpServletRequest requst) {
        PageInfo<Company> pageInfo = (PageInfo<Company>) companyFacade.list(company);
        model.addAttribute("company", company);
        model.addAttribute("pageInfo", pageInfo);
        return "sys/companyList";
    }

    //新增
    @RequestMapping(value = "company/form")
    public String companyForm(Model model, HttpServletRequest requst) {
        return "sys/companyForm";
    }

    @ResponseBody
    @RequestMapping(value = "company/set")
    public Map<String, Object> companySet(Company company) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        String msg = "";
        try {
            Company c = (Company) companyFacade.selectOne(company);
            if (c == null) {
               companyFacade.set(company);
            } else {
                state = false;
                msg = "设置失败，值已存在！";
            }
        } catch (Exception e) {
            state = false;
            msg = "设置失败！";
        }
        map.put("msg", msg);
        map.put("state", state);
        return map;
    }

    //保存运输企业信息
    @RequestMapping(value = "company/init", method = RequestMethod.POST)
    public String companyInit(CompanyFile companyFile, User user, Model model, HttpServletRequest request,
                              RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        String message = "添加成功@1";
        try {
            ShiroUserUtils.encryptPassword(user);
            String companyCode = (String) companyFacade.init(user);
            Company company = new Company();
            company.setCompanyCode(companyCode);
            String filePath = "/upload/company/" + companyCode + "/";
            String realPath = request.getSession().getServletContext().getRealPath(filePath);
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdir();
            }
            MultipartFile multipartFile = companyFile.getFile();
            if (!multipartFile.isEmpty()) {
                String fileName = companyCode + "_businessPic." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                company.setBusinessPic(filePath + fileName);
            }
           // companyFacade.update(company);
        } catch (Exception e) {
            message = message.replace("成功@1", "失败@2");
            log.error(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/sys/company/form";
    }

    //校验工号前缀是否存在
    @ResponseBody
    @RequestMapping(value = "checkJobNum")
    public Map<String, Object> checkJobNum(@RequestParam("jobNum") String jobNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            Company company = new Company();
            company.setId(0L);
            company.setJobNumPre(jobNum);
            Company c = (Company) companyFacade.selectOne(company);
            if (c != null) {
                state = false;
            }
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

    /**
     * ========================================业务功能==================================================
     **/
    //财务管理
    @RequestMapping(value = "finance")
    public String finance(Finance finance, Model model, HttpServletRequest request) {
        PageInfo<Finance> finances = (PageInfo<Finance>) financeFacade.list(finance);
        model.addAttribute("finance", finance);
        model.addAttribute("finances", finances);
        return "sys/finance";
    }

    @ResponseBody
    @RequestMapping(value = "finance/remind")
    public Map<String, Object> remind(Remind remind) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            remindFacade.save(remind);
        } catch (Exception e) {
            state = false;
        }
        map.put("state", state);
        return map;
    }

    /**
     * ========================================统计报表==================================================
     **/
    //订单统计
    @RequestMapping(value = "statistic/order")
    public String order(Order order, Model model, HttpServletRequest request) {
        String ids = "0,1,2,3";
        order.setIds(ids);
        System.out.println(orderFacade.list(order));
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);
        return "sys/orderList";
    }

    //线路分析
    @RequestMapping(value = "statistic/line")
    public String line(Model model, HttpServletRequest request) {
        PageInfo<Order> pageInfo = new PageInfo<Order>();
        model.addAttribute("pageInfo", pageInfo);
        return "sys/lineList";
    }

    //粉丝分析
    @RequestMapping(value = "statistic/fans")
    public String fans(Model model, HttpServletRequest request) {
        PageInfo<Order> pageInfo = new PageInfo<Order>();
        model.addAttribute("pageInfo", pageInfo);
        return "sys/fansList";
    }

    //交易分析
    @RequestMapping(value = "statistic/trade")
    public String trade(Model model, HttpServletRequest request) {
        PageInfo<Order> pageInfo = new PageInfo<Order>();
        model.addAttribute("pageInfo", pageInfo);
        return "sys/tradeList";
    }

    /**
     * =============================================发票功能模块=========================================
     **/

    //申请发票
    @RequestMapping(value = "invoice")
    public String invoiceList(Order order, Model model, HttpServletRequest request) {
        //显示申请中，已通过,已快递
        order.setOrderStatus("1,2,3");
        if (null == order) order = new Order();
        order.setStatus(3L);//已完成
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);

        model.addAttribute("order", order);
        model.addAttribute("orders", orders);

        return "sys/invoice_list";
    }


    //查看发票信息
    @RequestMapping(value = "detil")
    public String invoiceList(Invoice invoice, Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = invoiceFacade.find(invoice);
        model.addAttribute("invoice", map);
        //	PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        //	model.addAttribute("order",order);
        //	model.addAttribute("orders", orders);
        return "sys/invoice_detil";
    }


    //审核通过保存发票信息
    @ResponseBody
    @RequestMapping(value = "saveinvoice")
    public Map<String, Object> saveinvoice(Invoice invoice, @RequestParam(value = "actiontype") String actiontype,
                                           Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (actiontype.equals("2")) {

            invoice.setOrderStatus("2");
            map.put("actiontype", "2");

        } else {
            invoice.setOrderStatus("3");
            map.put("actiontype", "3");
        }

        int num = invoiceFacade.update(invoice);

        if (num > 0) {
            map.put("state", "success");
        } else {
            map.put("state", "fail");
        }

        return map;
    }


    //附加项价格设置页面
    @RequestMapping(value = "company/PriceSet")
    public String PriceSet(AdditionalNum additionalNum, Model model, HttpServletRequest request) {
        // PageInfo<Order> pageInfo = new PageInfo<Order>();
        //  model.addAttribute("pageInfo", pageInfo);
        //ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        additionalNumFacade.find(additionalNum);
        Map<String, Object> map = additionalNumFacade.find(additionalNum);
        model.addAttribute("map", map);
        return "sys/companyPriceSet";
    }


    //保存附加项价格
    @ResponseBody
    @RequestMapping(value = "company/savePriceSet")
    public Map<String, Object> savePriceSet(AdditionalNum additionalNum, Model model, HttpServletRequest request) {
        // PageInfo<Order> pageInfo = new PageInfo<Order>();
        //  model.addAttribute("pageInfo", pageInfo);
        Map<String, Object> Returnmap = new HashMap<String, Object>();
        Map<String, Object> map = additionalNumFacade.find(additionalNum);
        if (map == null || map.size() < 1) {
            additionalNumFacade.save(additionalNum);
            Returnmap.put("state", true);
        } else {
            additionalNumFacade.update(additionalNum);
            Returnmap.put("state", true);
        }
        //additionalNumFacade.save(additionalNum);
        model.addAttribute("additionalNum", additionalNum);
        return Returnmap;
    }

    //线路管理
    @RequestMapping(value = "route")
    public String route(HttpServletRequest request) {

        return "sys/route";
    }


    //线路审核
    @RequestMapping(value = "routeExamine")
    public String routeExamine(HttpServletRequest request) {

        return "sys/routeExamine";
    }

    //运输企业的账户详情管理
    @RequestMapping(value = "company/detail")
    public String companyDetail(Company company, Model model, Driver driver, Car car) {

        if (company.getSelectType() == null || company.getSelectType().equals("1")) {

            driver.setCompanyCode(company.getCompanyCode());
            PageInfo<Driver> drivers = (PageInfo<Driver>) driverFacade.list(driver);

            model.addAttribute("driver", driver);
            model.addAttribute("drivers", drivers);
            model.addAttribute("company", company);
        } else {

            car.setCompanyCode(company.getCompanyCode());
            PageInfo<Car> cars = (PageInfo<Car>) carFacade.list(car);
            model.addAttribute("car", car);
            model.addAttribute("cars", cars);

        }

        return "sys/companyDetailList";
    }


    //运输企业点击企业查看运输司机的详情
    @RequestMapping(value = "company/detail/form")
    public String form(Driver driver, Model model, HttpServletRequest requst) {
        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        model.addAttribute("message", driver.getMessage());
        if (driver.getId() != null) {
            List<Driver> drivers = (List<Driver>) driverFacade.find(driver);
            if (!CollectionUtils.isEmpty(drivers)) {
                driver = drivers.get(0);
            }
        }
        Company company = new Company();
        company.setCompanyCode(driver.getCompanyCode());
        List<Company> companys = (List<Company>) companyFacade.find(company);
        company = companys.get(0);
        model.addAttribute("company", company);
        model.addAttribute("driver", driver);
        return "sys/company/form";
    }

    //运输企业点击企业查看运输车辆的详情
    @RequestMapping(value = "company/detail/carForm")
    public String carForm(Car car, Model model, HttpServletRequest requst) {

        if (car.getId() != null || !StringUtils.isEmpty(car.getCarNum())) {
            List<Car> cars = (List<Car>) carFacade.find(car);
            if (!CollectionUtils.isEmpty(cars)) {

                car = cars.get(0);
            }
        } else {
            car.setSeatNumber(4);
        }
        Driver driver = new Driver();
        driver.setCompanyCode(car.getCompanyCode());
        List<Driver> drivers = (List<Driver>) driverFacade.find(driver);
        model.addAttribute("drivers", drivers);
        model.addAttribute("car", car);
        return "sys/company/carForm";
    }


    //平台方订单结算功能
    @ResponseBody
    @RequestMapping(value = "Settlement")
    public String Settlement(Order order) {
        String state = "1";

        try {
            order.setSettlement(2l);
            orderFacade.update(order);
        } catch (Exception e) {
            state = "0";
        }

        return state;
    }


    // 包车方订单情况
    @RequestMapping(value = "charter/settlement")
    public String settlement(Order order, Model model) {
        if (order.getPaymentStatus() == null || order.getPaymentStatus() == 3) {
            order.setPaymentStatus(3l);
            order.setPaymentStatusList("1,2");
        }
        if (order.getPaymentStatus() == 1 || order.getPaymentStatus() == 2) {
            order.setPaymentStatusList(null);
        }

        order.setStatus(3l);

        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);


        model.addAttribute("order", order);
        model.addAttribute("orders", orders);


        return "sys/charterSettlementList";
    }


    //订单结算查询
    @RequestMapping(value = "transport/settlement")
    public String transportSettlement(Order order, Model model) {

        ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();


        if (order.getSettlement() == null || order.getSettlement() == 3) {
            order.setSettlement(3l);
            order.setSettlementList("1,2");
        }
        if (order.getSettlement() == 1 || order.getSettlement() == 2) {
            order.setSettlementList(null);
        }

        order.setStatus(3l);

        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);


        model.addAttribute("order", order);
        model.addAttribute("orders", orders);

        return "sys/trasportSettlementList";
    }

    //金额列表
    @RequestMapping(value = "charter/moneyUserList")
    public String moneyUserList(Money money, Model model, HttpServletRequest requst) {
        money.setCompanyType(2l);
        PageInfo<Money> moneys = (PageInfo<Money>) moneyFacade.list(money);
        model.addAttribute("money", money);
        model.addAttribute("moneys", moneys);
        return "sys/money/userList";
    }


    //设置金额
    @RequestMapping(value = "charter/setMoney")
    public String setCredit(Money money, Model model, HttpServletRequest requst) {
        String companyCode = money.getCompanyCode();
        money = (Money) moneyFacade.findOne(money);
        money.setCompanyCode(companyCode);
        model.addAttribute("money", money);
        return "sys/money/setMoney";
    }


    //保存金额
    @Transactional
    @ResponseBody
    @RequestMapping(value = "charter/doSetMoney")
    public Map<String, Object> doSetCredit(Money money) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean state = true;
        try {
            ShiroDbRealm.ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

            Money mo = new Money();
            mo=(Money)moneyFacade.findOne(money);

            moneyFacade.update(money);
            //moneyDetilBillFacade
            MoneyDetilBill moneyDetilBill = new MoneyDetilBill();
            moneyDetilBill.setUserId(money.getUserId());


            moneyDetilBill.setCreateDate(new Date());
            moneyDetilBill.setMoney(money.getMoney()+"");
            moneyDetilBill.setMoneyBefore(mo.getTotalMoney()+"");
            moneyDetilBill.setMoneyAfter(money.getTotalMoney()+"");
            moneyDetilBill.setCompanyCode(money.getCompanyCode());
            moneyDetilBill.setBalance(money.getStockMoney()+"");
            moneyDetilBill.setUserId(shiroUser.id);
            moneyDetilBill.setType("3");
            moneyDetilBill.setPayMode("7");
            //管理员设置
            moneyDetilBill.setBigOrderCode("PS-"+ DataUtils.getCurrentTimeStr());

            moneyDetilBillFacade.save(moneyDetilBill);

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            state = false;
        }
        map.put("state", state);
        return map;
    }



}



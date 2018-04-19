package com.dhxx.web.transport.car;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.constant.Constant;
import com.dhxx.common.util.DateEditorUtils;
import com.dhxx.facade.entity.order.Order;
import com.dhxx.facade.entity.transport.car.Car;
import com.dhxx.facade.entity.transport.driver.Driver;
import com.dhxx.facade.service.order.OrderFacade;
import com.dhxx.facade.service.transport.car.CarFacade;
import com.dhxx.facade.service.transport.driver.DriverFacade;
import com.dhxx.web.BaseController;
import com.dhxx.web.shiro.ShiroDbRealm.ShiroUser;
import com.dhxx.web.shiro.ShiroUserUtils;
import com.github.pagehelper.PageInfo;


import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
/**
 * <p> 类说明 </p>
 *
 * @author hanrs
 * Date: 2017年09月13日
 * @version 1.01
 * 运输方车辆管理
 */
@Controller
@RequestMapping("transport/car")
@SuppressWarnings("unchecked")
public class CarController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(CarController.class);

    @Reference(protocol = "dubbo")
    private CarFacade carFacade;

    @Reference(protocol = "dubbo")
    private DriverFacade driverFacade;

    @Reference(protocol = "dubbo")
    private OrderFacade orderFacade;

    @RequestMapping(value = "list")
    public String list(Car car, Model model, HttpServletRequest requst) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        car.setCompanyCode(shiroUser.companyCode);
        PageInfo<Car> cars = (PageInfo<Car>) carFacade.list(car);
        model.addAttribute("car", car);
        model.addAttribute("cars", cars);
        return "transport/car/list";
    }

    //车辆任务表
    @RequestMapping(value = "task")
    public String task(Order order, Model model, HttpServletRequest request) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        order.setTransporterCode(shiroUser.companyCode);
        order.setIds("0,1,2");//-4已取消，-3被拒绝，-2待匹配，-1匹配中，0待接受，1等待上车，2在途,3完成
        /*if(StringUtils.isEmpty(order.getTaskTime())){
			order.setTaskTime(new Date());
		}*/
        PageInfo<Order> orders = (PageInfo<Order>) orderFacade.list(order);
        model.addAttribute("order", order);
        model.addAttribute("orders", orders);
        return "transport/car/task";
    }

    @RequestMapping(value = "form")
    public String form(Car car, Model model, HttpServletRequest requst) {
        model.addAttribute("message", car.getMessage());
        if (car.getId() != null || !StringUtils.isEmpty(car.getCarNum())) {
            List<Car> cars = (List<Car>) carFacade.find(car);
            if (!CollectionUtils.isEmpty(cars)) {
                car = cars.get(0);
            }
        } else {
            car.setSeatNumber(4);
        }
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        Driver driver = new Driver();
        driver.setCompanyCode(shiroUser.companyCode);
        List<Driver> drivers = (List<Driver>) driverFacade.find(driver);
        model.addAttribute("drivers", drivers);
        model.addAttribute("car", car);
        return "transport/car/form";
    }

    //校验车牌是否唯一
    @ResponseBody
    @RequestMapping(value = "carNumUnique", method = RequestMethod.POST)
    public Boolean carNumUnique(Car car) {
        List<Car> cars = (List<Car>) carFacade.find(car);
        if (!CollectionUtils.isEmpty(cars)) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * status:
     * 0:启用，1:冻结
     */
    @ResponseBody
    @RequestMapping(value = "updateById", method = RequestMethod.POST)
    public Long updateById(Car car) {
        try {
            return (Long) carFacade.updateById(car);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1l;
        }
    }

    //更新或者新增
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    public String saveOrUpdate(CarFile files, Car car, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        String message = (car.getId() == null ? "新建" : "更新") + "成功@1";
        try {
            if (StringUtils.isEmpty(car.getCarCode())) {
                car.setCarCode(Constant.CA_CODE + DateEditorUtils.dateToStr(DateEditorUtils.format_yyyyMMdd, new Date()));
            }

            String filePath = "/upload/car/" + car.getCarCode() + "/";
            String realPath = request.getSession().getServletContext().getRealPath(filePath);
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdir();
            }
            MultipartFile multipartFile = files.getCarPhotoFile();
            if (!multipartFile.isEmpty()) {
                String fileName = car.getCarCode() + "_carPhoto." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setCarPhoto(filePath + fileName);

                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            multipartFile = files.getCarPhoto1File();
            if (!multipartFile.isEmpty()) {
                String fileName = car.getCarCode() + "_carPhoto1." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setCarPhoto1(filePath + fileName);
                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            multipartFile = files.getCarPhoto2File();
            if (!multipartFile.isEmpty()) {
                String fileName = car.getCarCode() + "_carPhoto2." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setCarPhoto2(filePath + fileName);
                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            multipartFile = files.getCarPhoto3File();
            if (!multipartFile.isEmpty()) {
                String fileName = car.getCarCode() + "_carPhoto3." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setCarPhoto3(filePath + fileName);
                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            multipartFile = files.getCarPhoto4File();
            if (!multipartFile.isEmpty()) {
                String fileName = car.getCarCode() + "_carPhoto4." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setCarPhoto4(filePath + fileName);
                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            multipartFile = files.getCarPhoto5File();
            if (!multipartFile.isEmpty()) {
                String fileName = car.getCarCode() + "_carPhoto5." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setCarPhoto5(filePath + fileName);
                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            multipartFile = files.getDrivingLicenseProsFile();
            if (!multipartFile.isEmpty()) {   //
                String fileName = car.getCarCode() + "_drivingLicensePros." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setDrivingLicensePros(filePath + fileName);
                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            multipartFile = files.getDrivingLicenseConsFile();
            if (!multipartFile.isEmpty()) {
                String fileName = car.getCarCode() + "_drivingLicenseCons." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setDrivingLicenseCons(filePath + fileName);
                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            multipartFile = files.getOperationLicenseFile();
            if (!multipartFile.isEmpty()) {
                String fileName = car.getCarCode() + "_operationLicense." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setOperationLicense(filePath + fileName);
                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            multipartFile = files.getPolicyFile();
            if (!multipartFile.isEmpty()) {
                String fileName = car.getCarCode() + "_policy." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setPolicy(filePath + fileName);
                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            multipartFile = files.getXianlupaiProsFile();
            if (!multipartFile.isEmpty()) {
                String fileName = car.getCarCode() + "_xianlupaiPros." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setXianlupaiPros(filePath + fileName);
                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            multipartFile = files.getXianlupaiConsFile();
            if (!multipartFile.isEmpty()) {
                String fileName = car.getCarCode() + "_xianlupaiCons." + multipartFile.getOriginalFilename().split("\\.")[1];
                multipartFile.transferTo(new File(realPath, fileName));
                car.setXianlupaiCons(filePath + fileName);
                getFixedIcon(realPath + "\\"+fileName,650,450);

            }
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            car.setCreateUser(shiroUser.userAccount);
            car.setUpdateUser(shiroUser.userAccount);
            car.setCreateCompany(shiroUser.companyCode);
            car.setCompanyCode(shiroUser.companyCode);
            carFacade.saveOrUpdate(car);
        } catch (Exception e) {
            message = message.replace("成功@1", "失败@2");
            log.error(e.getMessage());
        }
        return "redirect:/transport/car/form?message=" + URLEncoder.encode(message, "UTF-8") + "&carNum=" + URLEncoder.encode(car.getCarNum(), "UTF-8");
    }
    //更改图片大小 650x450
    public void  getFixedIcon(String filePath, int width, int height) throws Exception{
        File f = new File(filePath);

        BufferedImage bi = ImageIO.read(f);

        double wRatio = (new Integer(width)).doubleValue() / bi.getWidth(); //宽度的比例

        double hRatio = (new Integer(height)).doubleValue() / bi.getHeight(); //高度的比例

        Image image = bi.getScaledInstance(width,height,Image.SCALE_SMOOTH); //设置图像的缩放大小

        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(wRatio,hRatio),null);   //设置图像的缩放比例

        image = op.filter(bi,null);

        int lastLength = filePath.lastIndexOf(".");
        String subFilePath = filePath.substring(0,lastLength);  //得到图片输出路径
        String fileType = filePath.substring(lastLength);  //图片类型
        File zoomFile = new File(subFilePath + fileType);

        Icon ret = null;
        try
        {
            ImageIO.write((BufferedImage)image, "jpg", zoomFile);
            ret = new ImageIcon(zoomFile.getPath());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

       // return ret;
    }


    @RequestMapping(value = "map")
    public String map(Car car, Model model, HttpServletRequest requst) {
        model.addAttribute("car", car);
        return "transport/car/map";
    }

    @RequestMapping(value = "state")
    public String state(Car car, Model model, HttpServletRequest requst) {
        model.addAttribute("car", car);
        return "transport/car/state";
    }

}

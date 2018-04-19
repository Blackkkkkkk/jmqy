package com.dhxx.facade.entity.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.dhxx.common.util.DataUtils;
import com.dhxx.facade.entity.SysBaseEntity;
import com.dhxx.facade.entity.match.Match;

/**
 * <p> 类说明 </p>
 *
 * @author dingbin
 * Date: 2017年09月11日
 * @version 1.01
 * 订单表(t_order)
 */
public class Order extends SysBaseEntity {

    private Long id;//主键
    private String bigOrderCode;//大订单,主订单编码
    private String orderCode;//订单编码
    private String charterCode;//包车方编码
    private String transporterCode;//运输方编码
    private String startPoint;//起始点
    private String startLng;//起始点经度
    private String startLat;//起始点纬度
    private String startArea;//起始点区域
    private String startCity;//起始点地级市
    private String endPoint;//目的地
    private String endLng;//目的地经度
    private String endLat;//目的地纬度
    private String endArea;//目的地区域
    private String endCity;//目的地地级市
    private String viaPoint;//途经点：地点@经度，纬度；地点@经度，纬度；
    private String carType;//车辆类型
    private String carCode;//车辆自编码
    private String carNum;//车牌号码
    private String driverCode;//司机编码
    private String viceDriverCode;//副班司机编码
    private Long tripType;//包车行程类型：1单程  2 往返，3返程
    private Date boardingTime;//上车时间
    private Date debusTime;//下车时间 --20170923
    private Date trackBoardTime;//返程上车时间 --20170923
    private Date trackDebusTime;//返程下车时间 --20170923

    private Long charterDays;//包车天数
    private Long population;//人数
    private Long charterType;//包车类型: 1,旅游包车 ; 2,企业包车，3私人包车
    private String linkMan;//联系人


    private String linkPhone;//联系人电话
    private String additional;//附加选项: 餐费     住宿费     高速路费     保险     水
    private String additionalNumFacade;//附加选项(下单选择的数目): 餐费     住宿费     高速路费     保险     水
    private Long distance;//预估路程
    private Long duration;//预估时长
    private Long fare;//预估车费
    private String trafficPriceCode;//运价计算规则编码
    private Double commission;//佣金
    private Double handsel;//定金
    private Double coupon;//优惠金额
    private Double otherFee;//其他费用
    private Double amount;//总金额
    private Long status;//订单状态：-4已取消，-3被拒绝，-2待匹配，-1匹配中，0待接受，1等待上车，2在途，3完成，4支付超时，5待出发 ，6取消车辆锁定中,7待定

    private Long payType;//支付方式
    private Long paymentStatus;//支付情况：1未支付，2已支付（包车方）
    private Long paymentType;//支付方式：1现付，2月结（包车方）,3 余额支付
    private Long settlement;//结算状态：1未结算，2已结算（运输方） ，3应收款(未结算+已结算)
    private Date placeTime;//下单时间
    private String orderPlacer;//下单账号（id）
    private String orderReceiver;//接单账号（id）
    private Date receiveTime;//接单时间
    private String refuseReason;//运输方拒绝理由
    private String guestbook;//包车方留言
    private String remark;//备注
    private Date updateTime;//修改时间
    private String site;//位置
    private String lng;//经度
    private String lat;//纬度
    private String area;//区域
    private String city;//市级
    private Long complaintId;//投诉表ID
    private Long invoiceId;//发票表ID
    private Long evaluateId;//投诉表ID
    private Date actualUpTime;//实际上车时间
    private Date actualOffTime;//实际下车时间
    private String backOrderCode;//返程单订单编码
    private Date matchTime;//匹配时间
    private Date reserveUpTime;//预留上车时间
    private Date reserveOffTime;//预留下车时间

    private String carTypes;//车辆类型（批量）
    private String carAmounts;//车辆数量（批量）
    private String prices;//批量订单的总金额根据大订单号分组统计求和

    private Date cancelTime;//订单取消时间

    //查询属性
    private Date beginTime;//开始上车时间
    private Date endTime;//结束上车时间
    private Date trackTime;//返程时间
    private String message;//提示信息
    private String type;//1订单详情 2评价 3投诉
    private String orderCodeSe;//订单编码搜索
    private Date taskTime;//任务日期
    private String licenseType;//判断是否有运营证，用于判断在指定时间内无运营证不做筛选  1禁止接单时间内 0非禁止接单时间内
    private String searchType;//模糊查询的界定条件，如果为1的话，采用模糊查询，其他值为原来的查询
    private String searchPlace;//模糊查询的地点查询
    private String DistanceDifference;//未含有途经点的首末差距离
    //附加属性
    private String placerAccount;//下单账号
    private String placerCode;//下单账号企业；
    private String charterCompanyName;//下单账号企业名称
    private String companyName;//接单企业
    private Long remindType;//订单提醒类型 1：包车方 2：运输方
    private String param;//搜索参数
    private Integer seatNumber;//车座数		
    private String driverName;//司机名称
    private String driverPhone;//司机手机
    private String viceDriverName;//副班司机名称
    private String viceDriverPhone;//副班司机手机
    private String refundStatus;//退款状态  0退款中，1退款完成
    private String rate;//  途经里程计费比率
    private String charteredBusCoefficient;//返回包车天数系数

    private String orderStatus; //发票状态  1：申请中  2：已通过  3：已寄出

    private String carPhoto;//搜索车辆展现的图片

    private Long reStstus;//退款状态

    private String settlementList;//结算情况多个

    private String paymentStatusList;//支付情况多个

    private double mileagePrice; //里程单价
    private double viaPrice;//途经里程额单价
    private double emptyOrdersPrice;//空接单单价
    private Double refundMoney;//退款金额

    private String AreaPlace; //筛选是否有地区
    private String viaPointApp;//App要的坐标


    private Integer flag;//0发布，1车辆预定，2返程单预定 ，3修改，4再来一单 ，5在途订单追加车辆
    private Integer top;//前几条


    public String getViaPointApp() {
        return viaPointApp;
    }

    public void setViaPointApp(String viaPointApp) {
        this.viaPointApp = viaPointApp;
    }

    public Double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Long getReStstus() {
        return reStstus;
    }

    public void setReStstus(Long reStstus) {
        this.reStstus = reStstus;
    }

    public String getCharterCompanyName() {
        return charterCompanyName;
    }

    public void setCharterCompanyName(String charterCompanyName) {
        this.charterCompanyName = charterCompanyName;
    }

    public double getEmptyOrdersPrice() {
        return emptyOrdersPrice;
    }

    public void setEmptyOrdersPrice(double emptyOrdersPrice) {
        this.emptyOrdersPrice = emptyOrdersPrice;
    }

    public double getViaPrice() {
        return viaPrice;
    }

    public void setViaPrice(double viaPrice) {
        this.viaPrice = viaPrice;
    }

    public double getMileagePrice() {
        return mileagePrice;
    }

    public void setMileagePrice(double mileagePrice) {
        this.mileagePrice = mileagePrice;
    }

    public String getAreaPlace() {
        return AreaPlace;
    }

    public void setAreaPlace(String areaPlace) {
        AreaPlace = areaPlace;
    }

    public String getPlacerCode() {
        return placerCode;
    }

    public void setPlacerCode(String placerCode) {
        this.placerCode = placerCode;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCharteredBusCoefficient() {
        return charteredBusCoefficient;
    }

    public void setCharteredBusCoefficient(String charteredBusCoefficient) {
        this.charteredBusCoefficient = charteredBusCoefficient;
    }

    public List<String> getpaymentStatusLists() {
        if (StringUtils.isEmpty(paymentStatusList))
            return null;
        return Arrays.asList(paymentStatusList.split(","));
    }


    public String getPaymentStatusList() {
        return paymentStatusList;
    }

    public void setPaymentStatusList(String paymentStatusList) {
        this.paymentStatusList = paymentStatusList;
    }

    public List<String> getsettlementLists() {
        if (StringUtils.isEmpty(settlementList))
            return null;
        return Arrays.asList(settlementList.split(","));
    }

    public String getSettlementList() {
        return settlementList;
    }

    public void setSettlementList(String settlementList) {
        this.settlementList = settlementList;
    }

    public String getCarPhoto() {
        return carPhoto;
    }

    public void setCarPhoto(String carPhoto) {
        this.carPhoto = carPhoto;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getDistanceDifference() {
        return DistanceDifference;
    }

    public void setDistanceDifference(String distanceDifference) {
        DistanceDifference = distanceDifference;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchPlace() {
        return searchPlace;
    }

    public void setSearchPlace(String searchPlace) {
        this.searchPlace = searchPlace;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getAdditionalNumFacade() {
        return additionalNumFacade;
    }

    public void setAdditionalNumFacade(String additionalNumFacade) {
        this.additionalNumFacade = additionalNumFacade;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Match> getMatchList() {
        if (StringUtils.isEmpty(carTypes))
            return null;
        List<Match> matchs = new ArrayList<Match>();
        String[] carTypeList = carTypes.split(",");
        String[] carAmountList = carAmounts.split(",");
        for (int i = 0; i < carTypeList.length; i++) {
            Match m = new Match();
            m.setCarType(carTypeList[i]);
            m.setCarAmount(carAmountList[i]);
            matchs.add(m);
        }
        return matchs;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public String getBigOrderCode() {
        return bigOrderCode;
    }

    public void setBigOrderCode(String bigOrderCode) {
        this.bigOrderCode = bigOrderCode;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCharterCode() {
        return charterCode;
    }

    public void setCharterCode(String charterCode) {
        this.charterCode = charterCode;
    }

    public String getTransporterCode() {
        return transporterCode;
    }

    public void setTransporterCode(String transporterCode) {
        this.transporterCode = transporterCode;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getStartLng() {
        return startLng;
    }

    public void setStartLng(String startLng) {
        this.startLng = startLng;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getEndLng() {
        return endLng;
    }

    public void setEndLng(String endLng) {
        this.endLng = endLng;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getViaPoint() {
        return viaPoint;
    }

    public void setViaPoint(String viaPoint) {
        this.viaPoint = viaPoint;
    }

    public String getViaPointStr() {
        if (!StringUtils.isEmpty(viaPoint)) {
            String[] arr = viaPoint.split(";");
            String str = "";
            for (String s : arr) {
                str += s.split("@")[0] + ",";
            }
            return str.substring(0, str.length() - 1);

        }
        return null;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public String getViceDriverCode() {
        return viceDriverCode;
    }

    public void setViceDriverCode(String viceDriverCode) {
        this.viceDriverCode = viceDriverCode;
    }

    public Long getTripType() {
        return tripType;
    }

    public void setTripType(Long tripType) {
        this.tripType = tripType;
    }

    public Date getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(Date boardingTime) {
        this.boardingTime = boardingTime;
    }

    public Long getCharterDays() {
        return charterDays;
    }

    public void setCharterDays(Long charterDays) {
        this.charterDays = charterDays;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Long getCharterType() {
        return charterType;
    }

    public void setCharterType(Long charterType) {
        this.charterType = charterType;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getFare() {
        return fare;
    }

    public void setFare(Long fare) {
        this.fare = fare;
    }

    public String getTrafficPriceCode() {
        return trafficPriceCode;
    }

    public void setTrafficPriceCode(String trafficPriceCode) {
        this.trafficPriceCode = trafficPriceCode;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getHandsel() {
        return handsel;
    }

    public void setHandsel(Double handsel) {
        this.handsel = handsel;
    }

    public Double getCoupon() {
        return coupon;
    }

    public void setCoupon(Double coupon) {
        this.coupon = coupon;
    }

    public Double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Double otherFee) {
        this.otherFee = otherFee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Long paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Long paymentType) {
        this.paymentType = paymentType;
    }

    public Long getSettlement() {
        return settlement;
    }

    public void setSettlement(Long settlement) {
        this.settlement = settlement;
    }

    public Date getPlaceTime() {
        return placeTime;
    }

    public void setPlaceTime(Date placeTime) {
        this.placeTime = placeTime;
    }

    public String getOrderPlacer() {
        return orderPlacer;
    }

    public void setOrderPlacer(String orderPlacer) {
        this.orderPlacer = orderPlacer;
    }

    public String getOrderReceiver() {
        return orderReceiver;
    }

    public void setOrderReceiver(String orderReceiver) {
        this.orderReceiver = orderReceiver;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getGuestbook() {
        return guestbook;
    }

    public void setGuestbook(String guestbook) {
        this.guestbook = guestbook;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(Date trackTime) {
        this.trackTime = trackTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRemindType() {
        return remindType;
    }

    public void setRemindType(Long remindType) {
        this.remindType = remindType;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getPlacerAccount() {
        return placerAccount;
    }

    public void setPlacerAccount(String placerAccount) {
        this.placerAccount = placerAccount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Long evaluateId) {
        this.evaluateId = evaluateId;
    }

    public Date getActualUpTime() {
        return actualUpTime;
    }

    public void setActualUpTime(Date actualUpTime) {
        this.actualUpTime = actualUpTime;
    }

    public Date getActualOffTime() {
        return actualOffTime;
    }

    public void setActualOffTime(Date actualOffTime) {
        this.actualOffTime = actualOffTime;
    }

    public Date getDebusTime() {
        if (reserveOffTime != null) {

            return DataUtils.getByMinute(reserveOffTime, -2 * 60);
        }
        return debusTime;
    }

    public void setDebusTime(Date debusTime) {
        this.debusTime = debusTime;
    }

    public Date getTrackBoardTime() {
        return trackBoardTime;
    }

    public void setTrackBoardTime(Date trackBoardTime) {
        this.trackBoardTime = trackBoardTime;
    }

    public Date getTrackDebusTime() {
        return trackDebusTime;
    }

    public void setTrackDebusTime(Date trackDebusTime) {
        this.trackDebusTime = trackDebusTime;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Long getPayType() {
        return payType;
    }

    public void setPayType(Long payType) {
        this.payType = payType;
    }

    public String getStartArea() {
        return startArea;
    }

    public void setStartArea(String startArea) {
        this.startArea = startArea;
    }

    public String getEndArea() {
        return endArea;
    }

    public void setEndArea(String endArea) {
        this.endArea = endArea;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getBackOrderCode() {
        return backOrderCode;
    }

    public void setBackOrderCode(String backOrderCode) {
        this.backOrderCode = backOrderCode;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public String getOrderCodeSe() {
        return orderCodeSe;
    }

    public void setOrderCodeSe(String orderCodeSe) {
        this.orderCodeSe = orderCodeSe;
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(String carTypes) {
        this.carTypes = carTypes;
    }

    public String getCarAmounts() {
        return carAmounts;
    }

    public void setCarAmounts(String carAmounts) {
        this.carAmounts = carAmounts;
    }

    public Date getReserveUpTime() {
        return reserveUpTime;
    }

    public void setReserveUpTime(Date reserveUpTime) {
        this.reserveUpTime = reserveUpTime;
    }

    public Date getReserveOffTime() {
        return reserveOffTime;
    }

    public void setReserveOffTime(Date reserveOffTime) {
        this.reserveOffTime = reserveOffTime;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getViceDriverName() {
        return viceDriverName;
    }

    public void setViceDriverName(String viceDriverName) {
        this.viceDriverName = viceDriverName;
    }

    public String getViceDriverPhone() {
        return viceDriverPhone;
    }

    public void setViceDriverPhone(String viceDriverPhone) {
        this.viceDriverPhone = viceDriverPhone;
    }

    public Integer getSeatNumber() {
        if (!StringUtils.isEmpty(carType)) {
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(carType);
            return Integer.parseInt(m.replaceAll("").trim()) - 1;
        }
        return null;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }


    public List<String> getOrderStatusList() {
        if (StringUtils.isEmpty(orderStatus))
            return null;
        return Arrays.asList(orderStatus.split(","));
    }

}
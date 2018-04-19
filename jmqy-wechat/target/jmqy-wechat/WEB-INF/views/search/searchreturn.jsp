<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <link rel="stylesheet" type="text/css" href="${ctx}/images/weuix.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/images/weui2.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/images/weui3.css">
    <script src="${ctx}/images/jquery-3.2.1.min.js"></script>
    <script src="${ctx}/images/zepto.min.js"></script>
    <script src="${ctx}/images/picker.js"></script>
    <script src="${ctx}/images/select.js"></script>
    <link rel="stylesheet" href="${ctx}/images/style.css">
    <title></title>
    <style>
        .divteam{
            height: 45px;
            line-height: 45px;
            border-bottom: 1px solid #ddd;
            display: none;
        }

    </style>
    <!--  <script>
          $(function(){
              $('.ms4').click(function(){
                  $('#msg4').fadeIn();
              })
          });
           </script>
          -->

    <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0"></script>
    <script type="text/javascript" src="https://api.map.baidu.com/getscript?v=2.0&ak=1w2xcsGWIGbPyhIngYF2uBK0&services=&t=20140411133140"></script>

    <!--  <script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script> -->

</head>
<body ontouchstart  class="page-bg">
<img style="float:left;" width="100%" src="${ctx}/images/banner2.jpg">
<form action="${ctx}/search/wechatsearCarTypes" id="myForm"  onsubmit="return check(this)" method="post">
    <input type="hidden" id="orderCode" name="orderCode" value="${order.orderCode}"/>
    <input type="hidden" id="bigOrderCode" name="bigOrderCode" value="${order.bigOrderCode}"/>
    <input type="hidden" id="viaPoint" name="viaPoint" value="${order.viaPoint}"/>
    <input type="hidden" id="distance" name="distance" value="${order.distance}">
    <input type="hidden" id="duration" name="duration" value="${order.duration}">
    <input type="hidden" id="flag" name="flag" value="${order.flag}">
    <input type="hidden" id="carCode" name="carCode" value="${order.carCode}">
    <input type="hidden" id="amount" name="amount" value="">
    <input type="hidden" id="backOrderCode" name="backOrderCode" value="${order.backOrderCode}">
    <input type="hidden" id="carTypes" name="carTypes" value="${order.carTypes}">
    <input type="hidden" id="carAmounts" name="carAmounts" value="${order.carAmounts}">
    <input type="hidden" id="prices" name="prices" value="${order.prices}">
    <input type="hidden" id="licenseType" name="licenseType">

    <div class="ge"></div>
    <div class="hot">
        <div class="title-hot">热门路线选择：</div>
        <div class="hot-content">

            <c:forEach items="${requestScope.hisLines}" var="Line">
                <a href="javascript:clickLines('${Line.startPoint}','${Line.startLng}','${Line.startLat}','${Line.startArea}','${Line.startCity}',
            '${Line.endPoint}','${Line.endLng}','${Line.endLat}','${Line.endArea}','${Line.endCity}')">${Line.startPoint} — ${Line.endPoint}</a>
            </c:forEach>
        </div>
    </div>
    <div class="ge"></div>
    <div class="item">
        <div class="item-left">
            <img class="updown" src="../images/updown.jpg" alt="">
            <img class="add-two" src="../images/add.jpg" alt="">
        </div>
        <div class="item-right">
            <div class="item-right-top">
                起点：<input placeholder="输入起点" type="text" class="john-input ms4"  id="startPoint" name="startPoint" value="${sessionScope.locationPostBase.startPoint}"   onclick="NewWindow(this.id)" >
                <input type="hidden" id="startLng" name="startLng" value="${sessionScope.locationPostBase.startLng}"/>
                <input type="hidden" id="startLat" name="startLat" value="${sessionScope.locationPostBase.startLat}"/>
                <input type="hidden" id="startArea" name="startArea" value="${sessionScope.locationPostBase.startDistrict}"/>
                <input type="hidden" id="startCity" name="startCity" value="${sessionScope.locationPostBase.startCity}"/>
                <span class="turnright"></span>
            </div>
            <div class="item-right-top">
                终点：<input placeholder="输入终点" type="text" class="john-input ms5" id="endPoint" name="endPoint" value="${sessionScope.locationPostBase.endPoint}"  onclick="NewWindow(this.id)">
                <input type="hidden" id="endLng" name="endLng" value="${sessionScope.locationPostBase.endLng}"/>
                <input type="hidden" id="endLat" name="endLat" value="${sessionScope.locationPostBase.endLat}"/>
                <input type="hidden" id="endArea" name="endArea" value="${sessionScope.locationPostBase.endDistrict}"/>
                <input type="hidden" id="endCity" name="endCity" value="${sessionScope.locationPostBase.endCity}"/>
                <span class="turnright"></span>
            </div>
            <div class="item-right-top add">
                途经：<input placeholder="您希望经过的途经站点" type="text" class="john-input ms4"  type="text" id="viaPoints1" name="viaPoints" onclick="NewWindow(this.id)">
                <input type="hidden" id="viaLngs1"  value="" name="viaLngs"/>
                <input type="hidden" id="viaLats1"  value="" name="viaLats"/>
                <input type="hidden" id="viaAreas1"  value="" name="viaAreas"/>
                <span class="turnright"></span>
            </div>

        </div>
        <%--<p>到约：<span class="jg">0公里</span></p>--%>
    </div>
    <div class="ge"></div>
    <div class="item1">
        <div class="item1-left">包车行程：</div>
        <div class="item1-right">
            <input name="tripType" class="rad1" type="radio" checked="checked" value="1"> 单程 &nbsp;&nbsp;<input name="tripType" class="rad2" type="radio" value="2"> 往返
        </div>
    </div>
    <div class="item1">
        <div class="item1-left">出发时间：</div>
        <div class="item1-right"><input id='time' placeholder="起始时间" type="text" id='time'  type="text" name="boardingTime"
                                        value="<fmt:formatDate type='date' pattern='yyyy-MM-dd HH:mm:ss' value='${order.boardingTime}'/>"
        ></div>
    </div>
    <div class="item1 shuang"style="display:none">
        <div class="item1-left">返程时间：</div>
        <div class="item1-right"><input id='time1' placeholder="结束时间" type="text" id='time1'  type="text" name="trackBoardTime"
                                        value="<fmt:formatDate type='date' pattern='yyyy-MM-dd HH:mm:ss' value='${order.trackBoardTime}'/>"
        ></div>
    </div>
    <div class="item1">
        <div class="item1-left">包车天数：</div>
        <div class="item1-right"><input type="text" placeholder="请输入天数" name="charterDays" id="charterDays"></div>
    </div>
    <div class="item1 chexing-all">
        <div class="item1-left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车型：</div>
        <div class="item1-right chexing">
            <input type="text" placeholder="选择车型" class="d2" type="text" id="carType" name="carType"  value="小车4座"><span>x</span><input class="d3" type="text" name="carAmounts" id="carAmount" value="1">
            <i class="icon-add"><img src="../images/add.jpg" alt=""></i>
        </div>
    </div>
    <div class="ge"></div>
    <div class="item1 fujia-all">
        <div class="item1-left">&nbsp;&nbsp;&nbsp;附加项：</div>
        <div class="item1-right fujia">
            <input type="hidden" name="additional" id="additional"/>
            <input type="hidden" name="additionalNumFacade" id="additionalNumFacade"/>
            <input type="checkbox" id="additionals0" name="additionals" title="不限" value="0"  onclick="addAllChoose(this);" > 不限 &nbsp;&nbsp;&nbsp;
            <input type="checkbox" id="additionals1" name="additionals" title="餐费" value="1" onclick="addAllChoose(this);" > 餐费 &nbsp;&nbsp;&nbsp;
            <input type="checkbox" id="additionals2" name="additionals" title="保险" value="2" onclick="addAllChoose(this);" > 保险 &nbsp;&nbsp;&nbsp;
            <br/><input type="checkbox" id="additionals3" name="additionals" title="住宿费" value="3" onclick="addAllChoose(this);" > 住宿费 &nbsp;&nbsp;&nbsp;
            <input type="checkbox" id="additionals4" name="additionals" title="水" value="4" onclick="addAllChoose(this);" > 水　 &nbsp;&nbsp;&nbsp;
            <br/> <input type="checkbox" id="additionals5" name="additionals" title="高速路费" value="5" onclick="addAllChoose(this);" > 高速路费
        </div>
    </div>
    <div id="delBrid">
    <div class="item1" id="addBr" style="display: none;">
        <div id="additionalsinput1" class="divteam">
            <div class="item1-left">餐费：</div>
            <div class="item1-right"><input type="text" placeholder="请输入数量" id="input1" name="meals"  onchange="initAdditionalsNum(this);"></div>
        </div>
        <div id="additionalsinput2" class="divteam">
            <div class="item1-left">保险：</div>
            <div class="item1-right"><input type="text" placeholder="请输入数量" id="input2" name="insurance"  onchange="initAdditionalsNum(this);"></div>
        </div>
        <div id="additionalsinput3" class="divteam">
            <div class="item1-left">住宿费：</div>
            <div class="item1-right"><input type="text" placeholder="请输入数量" id="input3" name="accommodation"  onchange="initAdditionalsNum(this);"></div>
        </div>
        <div id="additionalsinput4" class="divteam">
            <div class="item1-left">水：</div>
            <div class="item1-right"><input type="text" placeholder="请输入数量"  id="input4"  name="water"  onchange="initAdditionalsNum(this);"></div>
        </div>
        <div id="additionalsinput5" class="divteam">
            <div class="item1-left">高速路费：</div>
            <div class="item1-right"><input type="text" placeholder="请输入数量"  id="input5" name="highway"  onchange="initAdditionalsNum(this);"></div>
        </div>
    </div>


    <input type="submit"  name="查找" class="weui_btn bg-blue" onclick="recommendBtn()"></a>
    </div>
    <br/><br/>


</form>


<div class="weui_msg_img hide" id='msg4'>
    <div class="weui_msg_com"><div  class="weui_msg_close"><i class="icon icon-95"></i></div><!-- onclick="$('#msg4').fadeOut();"-->
        <div class="weui_msg_comment" style="height:300px;">
            <div class="seah">

                <input id="inputplace" type="text" placeholder="请输入地点">
                <button onclick="searchMap();">查找</button>

            </div>
            <div style="display: none;" id="map"></div>

        </div>


    </div>
</div>

</body>
</html>
<script src="${ctx}/images/weui.john.js"></script>
<script src="${ctx}/images/john.js"></script>
<script type="text/javascript">


    function addAllChoose(obj){

        if($(obj).val() == 0){

            var num =1
            $('.item1-right.fujia input[type="checkbox"]').each(function() {
                $(this).prop('checked', $(obj).prop('checked'));
                document.getElementById("addBr").setAttribute("style","display:block;");
                //根据id显示对应的附加项的数量输入框
                if(num<6) {

                    //动态添加br
                    var d1 = document.getElementById('addBr');
                    d1.insertAdjacentHTML('afterend', '<br/><br/>');

                    document.getElementById("additionalsinput" + num).setAttribute("style", "display:block;")
                    document.getElementById("input" + num).setAttribute("required", "true")
                }
                num++;
            });

        }else{
            if(!$(obj).prop('checked')){
                $('#additionals0').prop('checked',false);
                document.getElementById("addBr").setAttribute("style", "display:none;");
              //  $('#mealsL').attr("style","display:none;")
            }
        }

        initAdditionals();
        initAdditionalsNum();
    }


    //附加选项点击事件
    function initAdditionals(){


        var additional ='';
        $('#delBrid').find('br').remove();
        $("[name='additionals']").each(function(index,element){
            if(index>0 && document.getElementById("additionals"+$(this).val()).checked == true){

                document.getElementById("addBr").setAttribute("style","display:block;");
                additional = additional + document.getElementById("additionals"+$(this).val()).title+" ";
                //  AdditionalNumFacade = AdditionalNumFacade + document.getElementById("input" + $(this).val()).value+",";
                //显示对应的附加项的数量输入框，并设置为必填项
                document.getElementById("additionalsinput" + $(this).val()).setAttribute("style", "display:block;")
                document.getElementById("input" + $(this).val()).setAttribute("required", "true")

                var d1 = document.getElementById('addBr');
                d1.insertAdjacentHTML('afterend', '<br/><br/>');
            }else {
                if(index>0) {
                    //隐藏对应的附加项的数量输入框
                    document.getElementById("additionalsinput" + $(this).val()).setAttribute("style", "display:none;")
                    document.getElementById("input" + $(this).val()).removeAttribute("required")
                    document.getElementById("input" + $(this).val()).value='';
                }
            }
        });
        if(additional==''){
            document.getElementById("addBr").setAttribute("style","display:none;");
        }
        $('#additional').val(additional);
    }

    function  initAdditionalsNum() {
        var additionalNumFacade='';
        $("[name='additionals']").each(function(index,element){
            if(index>0 && document.getElementById("input"+$(this).val()).value != ''){
                additionalNumFacade = additionalNumFacade + document.getElementById("input" + $(this).val()).value+",";

            }
        })


        $('#additionalNumFacade').val(additionalNumFacade);
    }




    //判断用户是否登录
    $(document).ready(function() {


       // alert('${locationPostBase.viaPoint}')
        initviaPoint('${locationPostBase.viaPoint}');
        initLenAndHour();
    })


    //常用路线触发
    function clickLines(startPoint,startLng,startLat,startArea,startCity,endPoint,endLng,endLat,endArea,endCity) {

        $('#startPoint').val(startPoint);
        $('#startLng').val(startLng);
        $('#startLat').val(startLat);
        $('#startArea').val(startArea);
        $('#startCity').val(startCity);
        $('#endPoint').val(endPoint);
        $('#endLng').val(endLng);
        $('#endLat').val(endLat);
        $('#endArea').val(endArea);
        $('#endCity').val(endCity);
        initLenAndHour();
        setTimeout(function(){
            recommendBtn();
        }, 500);
        $('#charterDays').val('');
        $('#charterDays').attr('disabled','disabled');

    }


  function  initviaPoint(value) {

      var strsFirst= new Array(); //定义一数组
      var strsSeconed = new Array();
      strsFirst=value.split(";"); //字符分割
      for (i=0;i<strsFirst.length-1;i++ )
      {
          strsSeconed= strsFirst[i].split(",");

          for (j=0;j<strsSeconed.length ;j++ )
          {
              var num;
              if(j==0){
                  num=strsSeconed[j]
                  if(action='1') {
                      $('.add-two').click();
                  }
              }else if(j ==  1){
                  $("#viaPoints"+num).val(strsSeconed[j]);

              }else if(j == 2){
                  $("#viaLngs"+num).val(strsSeconed[j]);

              }else if(j == 3){
                  $("#viaLats"+num).val(strsSeconed[j]);

              }else if(j == 4){
                  $("#viaAreas"+num).val(strsSeconed[j]);

              }
          }
      }


      var viaPoint = '';
      $("[name='viaPoints']").each(function(index,element){
          if($(this).val() != null && $(this).val() != ''){
              viaPoint += $(this).val()+'@'+$(this).next().val()+','+$(this).next().next().val()+';';
          }
      });
      $('#viaPoint').val(viaPoint);



  }

    //点击搜索触发
    function recommendBtn(){

       // alert("11="+form.startPoint.value)
        initCars();
      $('#flag').val(9);

  //   $('#submitBtn').click()
    }

    //地图加载》》》》》》start
    var xPoint = 0;
    var yPoint = 0;

    //创建地图实例
    var map = new BMap.Map("map");

    //创建点坐标
    var point = new BMap.Point(xPoint, yPoint);

    var markers = [];//标注数组



    //初始化车型和数量
    function initCars(){
        var carTypes = '';
        var len = $("[name='carType']").length;
        $("[name='carType']").each(function(index,element){
            if(index != len)

                carTypes +=$(this).val()+','
        });



        $('#carTypes').val(carTypes);
        var carAmounts = '';
        var lens = $("[name='carAmount']").length;
        $("[name='carAmount']").each(function(index,element){

            if(index != lens)
                carAmounts +=$(this).val()+','
        });

        $('#carAmounts').val(carAmounts);
    }
    //计算起始点的路程和时间
    function initLenAndHour(){
        if(($('#startPoint').val() != null && $('#startPoint').val() != '') && ($('#endPoint').val() != null && $('#endPoint').val() != '')){
            var startPoint = new BMap.Point($('#startLng').val(),$('#startLat').val());
            var endPoint = new BMap.Point($('#endLng').val(),$('#endLat').val());

            var viaPoint = '';
            var waypoints = [];
            $("[name='viaPoints']").each(function(index,element){
                if($(this).val() != null && $(this).val() != ''){
                    viaPoint = viaPoint + $(this).val()+'@'+$(this).next().val()+','+$(this).next().next().val()+';';
                    waypoints[index] = new BMap.Point($(this).next().val(),$(this).next().next().val());
                }
            });

            $('#viaPoint').val(viaPoint);

             //去掉数组为空的
            for(var i = 0 ;i<waypoints.length;i++)
            {
                if(waypoints[i] == "" || typeof(waypoints[i]) == "undefined")
                {
                    waypoints.splice(i,1);
                    i= i-1;
                }

            }
            console.log(waypoints)
            searchLine(startPoint, endPoint, waypoints);
        }
    }

    //首页选取起止点的初始化
    function initArea(){
        var gc = new BMap.Geocoder();
        if('${area.startArea}' != null && '${area.startArea}' != ''){
            function mySFun(){
                var pp = localS.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
                gc.getLocation(pp, function(rs){
                    var addComp = rs.addressComponents;
                    var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                    $('#startArea').val(addComp.district);
                    $('#startCity').val(addComp.city);
                });
                $('#startPoint').val('${area.startArea}');
                $('#startLng').val(pp.lng);
                $('#startLat').val(pp.lat);
            }
            var localS = new BMap.LocalSearch(map, { //智能搜索
                onSearchComplete: mySFun
            });
            localS.search('${area.startArea}');
        }
        if('${area.endArea}' != null && '${area.endArea}' != ''){
            function myEFun(){
                var pp = localE.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
                gc.getLocation(pp, function(rs){
                    var addComp = rs.addressComponents;
                    var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                    $('#endArea').val(addComp.district);
                    $('#endCity').val(addComp.city);
                });
                $('#endPoint').val('${area.endArea}');
                $('#endLng').val(pp.lng);
                $('#endLat').val(pp.lat);
            }
            var localE = new BMap.LocalSearch(map, { //智能搜索
                onSearchComplete: myEFun
            });
            localE.search('${area.endArea}');
        }
    }

    //地图加载》》》》》》start
    var xPoint = 0;
    var yPoint = 0;

    //创建地图实例
    var map = new BMap.Map("map");

    //创建点坐标
    var point = new BMap.Point(xPoint, yPoint);

    var markers = [];//标注数组

    //初始化地图，设置中心点坐标和地图级别
    map.centerAndZoom(point, 13);
    //添加默认比例尺控件
    map.addControl(new BMap.ScaleControl());
    //滚轮缩放事件
    map.enableScrollWheelZoom();
    //开启连续缩放效果
    map.enableContinuousZoom();
    //地图加载》》》》》》end

    //获取线路规划
    function searchLine(startPoint, endPoint, waypoints){
        var route = 0;

        var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, enableDragging: false}, onSearchComplete: searchComplete, policy: route});

        map.clearOverlays();
        driving.search(startPoint, endPoint,{waypoints:waypoints});//waypoints表示途经点
    }

    var searchComplete = function (results){

        var plan = results.getPlan(0);
        var lineLength = plan.getDistance(true);
        var lineNeedHours = plan.getDuration(true);
        $("#distance").val(plan.getDistance(false));
        $("#duration").val(Math.ceil(plan.getDuration(false)*1.15));

    };







    function  NewWindow(id) {


        var state;
        if(id=="startPoint"){
            state="start";
        }else if(id=="endPoint"){
            state="end"

        }

        var iHeight = $(window).height();
        var iWidth = $(document).height();
        var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-10-iWidth)/2;


       if(((id.indexOf("viaPoints"))==0)){


           var str =($("#viaPoint").val()).replace(/@/g,",");
           var strsFirst= new Array(); //定义一数组
           strsFirst=str.split(";"); //字符分割
           str = '';
           for (i=0;i<strsFirst.length-1;i++ ) {
               str  += (i+1)+","+strsFirst[i]+";"
           }
           window.location.href='${ctx}/search/BaiDuMap?ViaPoint='+id+'&str='+str, "newwindow",'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no'
       }else {
           window.location.href='${ctx}/search/BaiDuMap?state='+state, "newwindow",'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no'

       }

    }

    $(document).ready(function(){

           //返回为undefined 清空值
         if($("#endPoint").val() == "undefined" || typeof($("#endPoint").val()) == "undefined") {
             $("#endPoint").val("");
             $("#endLng").val("");
             $("#endLat").val("");
             $("#endArea").val("");
             $("#endCity").val("");
         }

        if($("#startPoint").val() == "undefined" || typeof($("#startPoint").val()) == "undefined") {
            $("#startPoint").val("");
            $("#startLng").val("");
            $("#startLat").val("");
            $("#startArea").val("");
            $("#startCity").val("");
        }


         var  endPoint=$("#endPoint").val();
         var charterDays= $("#charterDays").val();
         //如果endPoint有值和没值包车天数的input状态
         if(endPoint!=null&&endPoint!=''){
             $("#charterDays").attr("disabled","disabled");
         }
         if(endPoint==null&&endPoint==''){
            $("#charterDays").attr("disabled","");
          }


     });



    //表单数据校验
    function check(form) {

      /*  if(form.carType.value==''||form.carType.value==null){
           // alert()
            alert("车型不能为空")
            form.carType.focus();
            return false;
        }*/
        if(form.startPoint.value==''||form.startPoint.value==null) {
            alert("起始点不能为空!");
            form.startPoint.focus();
            return false;
        }
        if((form.endPoint.value==''||form.endPoint.value==null)&&(form.charterDays.value==''||form.charterDays.value==null)){
            alert("【终点】和【包车天数】必填其一！");
            form.endPoint.focus();
            return false;
        }
        if((form.endPoint.value!=''&&form.endPoint.value!=null)&&(form.charterDays.value!=''&&form.charterDays.value!=null)){
            alert("【终点】和【包车天数】只能填其一！");
            form.endPoint.focus();
            return false;
        }
        if(form.time.value==''||form.time.value==null){
            alert("上车时间不能为空!");
            form.time.focus();
            return false;
        }

        var bordtime = form.boardingTime.value;
        bordtime = bordtime.replace(/[&\|\\\*:\-]/g,"");
        bordtime = bordtime.substring(bordtime.indexOf(" ")+1,bordtime.length)

        var  Starttime  = '${map.ordernotsetStarttime}';
        Starttime  = Starttime.replace(/[&\|\\\*:\-]/g,"");

        var  Endtime  = '${map.ordernotsetEndtime}';
        Endtime  = Endtime.replace(/[&\|\\\*:\-]/g,"");



        if(!(parseInt(bordtime)<parseInt(Starttime)) && !(parseInt(bordtime)>parseInt(Endtime))){
            $('#licenseType').val("1");
            //  return "请重新选择时间,在【${map.ordernotsetStarttime}】和【${map.ordernotsetEndtime}】 暂不发车！";
        }else{
            $('#licenseType').val("0");
        }
      /*  if(form.carAmount.value==''||form.carAmount.value==null){
            alert("车辆数目不能为空!");
            form.carAmount.focus();
            return false;
        }*/
        return true;
    }



</script>

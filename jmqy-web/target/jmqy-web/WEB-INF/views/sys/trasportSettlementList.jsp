<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>运输方订单情况</title>
    <script>

    </script>
</head>
<body>

        <form  id="searchForm" class="layui-form" action="${ctx}/sys/transport/settlement" method="post">
            <input type="hidden" name="pageNum" id="pageNum" value="${order.pageNum}"/>
            <input type="hidden" name="pageSize" id="pageSize" value="${order.pageSize}"/>
            <input type="hidden" id="order" name="order" value="${order.order}"/>
            <input type="hidden" id="sortType" name="sortType" value="${order.sortType}"/>


            <input type="hidden" id="orderCodeSe" name="orderCodeSe" value="${order.orderCodeSe}"/>
            <input type="hidden" id="searchPlace" name="searchPlace" value="${order.searchPlace}"/>
            <input type="hidden" id="carType" name="carType" value="${order.carType}"/>
            <input type="hidden" id="driverName" name="driverName" value="${order.driverName}"/>
            <input type="hidden" id="linkMan" name="linkMan" value="${order.linkMan}"/>
            <input type="hidden" id="searchType" name="searchType" value="0">

            <div class="button_left">
                <label class="layui-form-label">订单类型:</label>
                <div class="layui-input-block">
                    <input type="radio" name="settlement" value="3" title="应收款"
                           <c:if test="${order.settlement == 3}">checked</c:if> lay-filter="companyType">
                    <input type="radio" name="settlement" value="2" title="已收款 "
                           <c:if test="${order.settlement == 2}">checked</c:if> lay-filter="companyType">
                    <input type="radio" name="settlement" value="1" title="未收款"
                           <c:if test="${order.settlement == 1}">checked</c:if> lay-filter="companyType">
                </div>
            </div>
            <div class="cl"></div>
            <div class="button_right1 john-edge">

                <div class="layui-inline">
                    <label class="layui-form-label">上车时间:</label>
                    <div class="layui-input-inline" style="top: 5px;">
                        <input class="layui-input search_text input_date" id="beginTime" name="beginTime" type="text"
                               value='<fmt:formatDate value="${order.beginTime}" pattern="yyyy-MM-dd"/>'
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"/>_
                    </div>
                    <div class="layui-input-inline" style="top: 5px;">
                        <input class="layui-input search_text input_date" id="endTime" name="endTime" type="text"
                               value='<fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd"/>'
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <select name="status" id="status">
                        <option value="">订单状态</option>
                        <option value="1" <c:if test="${order.status == 1}">selected="selected"</c:if>>等待上车
                        </option>
                        <option value="2" <c:if test="${order.status == 2}">selected="selected"</c:if>>在途
                        </option>
                        <option value="3" <c:if test="${order.status == 3}">selected="selected"</c:if>>完成
                        </option>
                    </select>
                </div>

                <div class="layui-inline">
                    <%-- <input type="text" name="orderCodeSe" value="${order.orderCodeSe}" class="layui-input" placeholder="请输订单号"> --%>
                    <a class="layui-btn layui-btn-danger layui-btn-small" href="javascript:selfSubmit();">搜索</a>
                </div>
                <div class="cl">
                </div>
                <label class="layui-form-label">查询内容选项:</label>
                <div class="layui-inline">
                    <select name="dim" id="dim" lay-filter="dim">
                        <option value="0" <c:if test="${order.orderCodeSe != null and order.orderCodeSe !=''}">selected="selected"</c:if>>订单号</option>
                        <option value="1" <c:if test="${order.searchPlace != null and order.searchPlace !=''}">selected="selected"</c:if>>地点</option>
                        <option value="2" <c:if test="${order.carType != null and order.carType !=''}">selected="selected"</c:if>>车辆</option>
                        <option value="3" <c:if test="${order.driverName != null and order.driverName !=''}">selected="selected"</c:if>>司机</option>
                        <option value="4" <c:if test="${order.linkMan != null and order.linkMan !=''}">selected="selected"</c:if>>购买人</option>
                    </select>
                </div>
                <div class="layui-inline">
                    <input class="search_text" id="dimValue" name="dimValue" type="text"
                           style="cursor: pointer;" placeholder="输入查询内容"/>
                </div>
            </div>

            <div class="cl"></div>

            <div class="layui-form">
                <table class="layui-table">

                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>订单号</th>
                        <th>路线</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>司机</th>
                        <th>订单金额</th>
                        <th>是否记账</th>
                        <th>记账支付情况</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tr>
                        <c:forEach items="${orders.list}" var="orders" varStatus="vs">
                    <tr>
                        <td>${vs.index+1}</td>
                        <td>${orders.orderCode}</td>
                        <td>${orders.startPoint} - ${orders.endPoint}</td>
                        <td>
                            <fmt:formatDate value="${orders.boardingTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <fmt:formatDate value="${orders.debusTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>${orders.driverName}</td>
                        <td>${orders.prices}</td>
                        <td>
                            <c:choose>
                            <c:when test="${orders.paymentType == 1}">是</c:when>
                            <c:when test="${orders.paymentType == 2}">否</c:when>
                            </c:choose>
                        <td>
                            <c:choose>
                            <c:when test="${orders.settlement == 1}">未结算</c:when>
                            <c:when test="${orders.settlement == 2}">已结算</c:when>
                            </c:choose>
                        <td><a href="line-1.html" class="layui-btn layui-btn-primary layui-btn-mini"><i
                                class="icon-edit"></i> 查看</a></td>

                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <tags:page pages="${orders}"/>
        </form>


        <script type="text/javascript">



            var radiovaLue;
            layui.use(['form','element','laydate'], function() {

                var form = layui.form;

                form.on('radio(companyType)', function(data){
                    //console.log(data.elem); //得到radio原始DOM对象
                    //console.log(data.value); //被点击的radio的value值
                    var type = '${order.settlement}';
                    if(type != data.value){
                        $("[name='pageNum']").val(1);//第一页
                    }
                    selfSubmit();

                });
            });




            //搜索框的状态记忆
            var selectValue = $("#dim").val();
            if(selectValue==null && selectValue==''){
                selectValue='0';
            }
            layui.use('form', function() {

                var form = layui.form;


                //各种基于事件的操作，下面会有进一步介绍
                form.on('select(dim)', function(data){
                    selectValue=data.value; //得到被选中的值
                });
            })




            //提交搜索表单
            function selfSubmit() {

                if($("#dimValue").val() == null || $("#dimValue").val()==''){
                    $("#searchType").val('0');

                    $("#orderCodeSe").val('');
                    $("#searchPlace").val('');
                    $("#carType").val('');
                    $("#driverName").val('');
                    $("#linkMan").val('');
                }else{
                    $("#searchType").val("1");
                    var setValue = $("#dimValue").val();
                    if(selectValue =='0'){
                        set('orderCodeSe',setValue);
                    }else if(selectValue =='1'){
                        set('searchPlace',setValue);
                    }else if(selectValue =='2'){
                        set('carType',setValue);
                    }else if(selectValue =='3'){
                        set('driverName',setValue);
                    }else if(selectValue =='4'){
                        set('linkMan',setValue);
                    }
                }
                $("#pageNum").val("1");
                $("#pageSize").val("10");
                $("#searchForm").submit();
            }

            function  set(id,value) {

                $("#orderCodeSe").val('');
                $("#searchPlace").val('');
                $("#carType").val('');
                $("#driverName").val('');
                $("#linkMan").val('');

                $("#"+id).val(value);


            }

            //跳到第一页
            function selfSearch() {
                $("[name='pageNum']").val(1);//第一页
                selfSubmit();
            }


        </script>
</body>
</html>
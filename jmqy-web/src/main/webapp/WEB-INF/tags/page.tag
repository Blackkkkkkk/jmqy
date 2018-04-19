<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="pages" type="com.github.pagehelper.PageInfo" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="page_div">
    <div id="demo7"></div>
    <div id="demo8" class="layui-form layui-input-inline layui-laypage">
        <a class="layui-laypage-next page_next">共${pages.pages}页</a>
        <span class="layui-laypage-limits page_limits">
            <select lay-filter="page_select">
                <option value="10" <c:if test="${pages.pageSize == 10}">selected</c:if>>10 条/页</option>
                <option value="20" <c:if test="${pages.pageSize == 20}">selected</c:if>>20 条/页</option>
                <option value="30" <c:if test="${pages.pageSize == 30}">selected</c:if>>30 条/页</option>
                <option value="40" <c:if test="${pages.pageSize == 40}">selected</c:if>>40 条/页</option>
                <option value="50" <c:if test="${pages.pageSize == 50}">selected</c:if>>50 条/页</option>
            </select>
        </span>
        <a class="layui-laypage-prev page_total">共${pages.total}条</a>
    </div>
</div>
<script type="text/javascript">
    //分页参数
    var page={};
    page.total = ${pages.total};
    page.curr = ${pages.pageNum};
    page.limit = ${pages.pageSize};
    //分页
    laypageSelfDefined(page);
    //操作array
    var checkedIdsArray = new Array();
    //添加/删除复选框的id
    layui.use(['form','table'], function(){
        var $ = layui.jquery, form = layui.form, table = layui.table;
        //全选
        form.on('checkbox(allChoose)', function(data){
            var check = data.elem.checked;
            var value= data.value;
            if(value != 'on'){
                arrayOperate(check,value);
            }
            var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
            child.each(function(index, item){
                item.checked = data.elem.checked;
                var value = item.value;
                //操作数据
                arrayOperate(check,value);
            });
            form.render('checkbox');
        });
        //单选
        form.on('checkbox(singleChoose)', function(data){
            var check = data.elem.checked;
            var value= data.value;
            arrayOperate(check,value);
            if(check){
                var parent = $(data.elem).parents('table').find('thead input[type="checkbox"]');
                parent.each(function(index, item){
                    item.checked = data.elem.checked;
                    var value = item.value;
                    if(value != 'on'){
                        //操作数据
                        arrayOperate(check,value);
                    }
                });
                form.render('checkbox');
            }
            if(!check){
                var checkedArray = new Array();
                var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
                child.each(function(index, item){
                    if(item.checked){
                        checkedArray.push(item.checked);
                    }
                });
                if(checkedArray.length == 0){
                    var parent = $(data.elem).parents('table').find('thead input[type="checkbox"]');
                    parent.each(function(index, item){
                        item.checked = data.elem.checked;
                        var value = item.value;
                        if(value != 'on'){
                            //操作数据
                            arrayOperate(check,value);
                        }
                    });
                    form.render('checkbox');
                }
            }
        });
        //下拉
        form.on('select(page_select)', function(data) {
            $("[name='pageNum']").val(1);
            $("[name='pageSize']").val(data.value);
            $("#searchForm").submit();
            form.render('select');
        });

    });
    //array判断元素是否需要进行增删,check为增加
    function arrayOperate(check,value) {
        if(check){
            if(checkedIdsArray.indexOf(value) < 0 )
                checkedIdsArray.push(value);
        }else{
            if(checkedIdsArray.indexOf(value) > -1)
                checkedIdsArray.splice($.inArray(value,checkedIdsArray),1);
        }
    }
    //获取已选复选框
    function getCheckedIdArray() {
        var checkbox = $(":checkbox:checked");
        var newCheckedIdsArray = new Array();
        $(checkbox).each(function () {
            newCheckedIdsArray.push($(this).val());
        });
        return newCheckedIdsArray;
    }
    /* 2.0分页控件，仅有一页数据时也显示分页 */
    function laypageSelfDefined(page){
        layui.use(['laypage'], function(){
            var laypage = layui.laypage;
            laypage.render({
                elem: 'demo7',
                first: '首页',
                last: '尾页',
                limit: page.limit,
                count: page.total,
                limits:[10,20,30,40,50],
                curr: page.curr,
                groups: 5,
                layout: ['prev', 'page', 'next', 'skip'],
                jump: function(obj, first){
                    //得到了当前页，用于向服务端请求对应数据
                    var curr = obj.curr;
                    var limit = obj.limit;
                    if(!first){
                        $("[name='pageNum']").val(curr);
                        $("[name='pageSize']").val(limit);
                        $("#searchForm").submit();
                    }
                }
            });
        })
    }
</script>


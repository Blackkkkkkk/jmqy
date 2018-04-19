<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
  <style>
  .title{
    line-height: 35px;
    background:#f1f1f1;
    padding:0 10px;
  }
  .li-left{
    text-align:center;
    float:left;
    width:80px;
    padding:10px 0;
  }
  li{
    width:100%;
    overflow:hidden;
    border-bottom:1px dashed #ddd;
  }
  .li-right{
    float:left;
    padding:10px 0;
  }
  .li-right-top{
   line-height: 30px;
  }
  .li-right-top span i{
    color:#ebbe09;

  }
  .li-right-top span{
    margin-right:10px;
  }
   .icon-star-h{
    color:#bbb !important;
  }
  </style>
  <title></title>
</head>
<body class="window">
<div class="title"><span class="blue">全部评价</span><span>（共${fn:length(evaluateList)}条评价）</span></div>
<ul>
<c:forEach items="${evaluateList}" var="e" varStatus="status" >
	<li>
	<div class="li-left"><img src="${ctx}/static/images/toux.png" alt=""><br/>${e.charterName}</div>
	<div class="li-right">
	  <div class="li-right-top">
	  	<span>
		  	<c:forEach var="i" begin="1" end="5" step="1"> 
		  		<i class="icon-star <c:if test='${i > e.toCarScore}'>icon-star-h</c:if>"></i>  
			</c:forEach>  
	  </span>${e.toCarScore}分</div>
	  <div class="li-right-bottom">${e.toCarContent}</div>
	</div>
</li>
</c:forEach>
</ul>
</body>
</html>
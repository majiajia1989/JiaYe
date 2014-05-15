<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>index</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="微信,公众号,微网站,weixin,wechat">
<meta http-equiv="description" content="This is my page">
</head>
<body style="text-align: center">
<h2>EL隐含对象 pageContext</h2>
<c:forEach var="name" items="${CONTEXT_CACHE}">
	${name}<br/>
</c:forEach>
</body>
</html>
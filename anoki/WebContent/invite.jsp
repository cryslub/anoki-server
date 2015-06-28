<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="com.anoki.singleton.Ibatis"%>
<%
	String id = request.getParameter("id");
	Ibatis.update("acceptFriendRequest",id);
	Ibatis.insert("insertFriendAlaram",id);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<script>
	window.location = "Intent://takePhoto#Intent;scheme=callMyApp;package=com.test.myapp;end";
</script>
<body>

	

</body>
</html>
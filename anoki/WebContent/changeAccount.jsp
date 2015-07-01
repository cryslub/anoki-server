<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="com.anoki.singleton.Ibatis"%>
<%@page import="com.anoki.jaxb.Admin"%>

<%

	if(!"true".equals(session.getAttribute("logined"))){
		return;
	}

	request.setCharacterEncoding("UTF-8");

	Admin admin = new Admin();

	admin.id = request.getParameter("account");
	Ibatis.update("updateAccount",admin);

%>

계정이 변경되었습니다.


<a href="admin.jsp">관리 화면으로</a>
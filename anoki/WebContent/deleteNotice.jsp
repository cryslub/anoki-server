<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="com.anoki.singleton.Ibatis"%>
<%@page import="com.anoki.jaxb.Notice"%>

<%

if(!"true".equals(session.getAttribute("logined"))){
	return;
}


	request.setCharacterEncoding("UTF-8");

	Notice notice = new Notice();

	notice.id = request.getParameter("id");
	Ibatis.insert("deleteNotice",notice);

%>

공지가 삭제되었습니다.


<a href="admin.jsp">관리 화면으로</a>
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

	notice.text = request.getParameter("text");
	notice.id = request.getParameter("id");
	System.out.println(notice.text);
	Ibatis.insert("updateNotice",notice);

%>

공지가 수정되었습니다.


<a href="admin.jsp">관리 화면으로</a>
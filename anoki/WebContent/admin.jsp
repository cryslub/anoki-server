<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="com.anoki.singleton.Ibatis"%>
<%@page import="com.anoki.jaxb.Admin"%>
<%@page import="com.anoki.jaxb.Notice"%>
<%@page import="java.util.List"%>

<%
	Admin admin = new Admin();

	if(!"true".equals(session.getAttribute("logined"))){
		

		
	
		admin.id = request.getParameter("id");
		admin.pass = request.getParameter("pass");
		
		Object obj = Ibatis.object("admin",admin);
		if(obj != null){
			
			session.setAttribute("logined", "true");
			
		
		}
	
	}

	if("true".equals(session.getAttribute("logined"))){
		
		List<Notice> list = Ibatis.list("notice",null);
%>

<style>
input[type=text]{
    width: 100%;
}

</style>
<script language="javascript" type="text/javascript" src="jquery-1.11.3.min.js"></script>  
<script>

</script>

Admin 계정 변경

<form action="changeAccount.jsp" method="post">
	계정 : <input name="account" value=""/> <br/>
	<input type="submit" value="변경">
</form>

Admin 암호 변경

<form action="changePass.jsp" method="post">
	암호 : <input type="password" name="pass" value=""/> <br/>
	암호확인 : <input type="password" name="confirm"/> 

	<input type="submit" value="변경">
</form>

공지 관리 

<form action="addNotice.jsp" method="post">
	공지 내용 : <input name="text" size=200/> <input type="submit" value="공지 추가">
</form>

<%
	for(Notice notice: list){
%>
	<form action="editNotice.jsp" method="post">
		<input type="hidden" name="id" value="<%=notice.id%>"/>
		공지 내용 : <input name="text" value="<%=notice.text%>" size=200/> 
		<input type="submit" value="수정">
	</form>
	<form action="deleteNotice.jsp" method="post">
		<input type="hidden" name="id" value="<%=notice.id%>"/>
		<input type="submit" value="삭제">
	</form>

<%			
		
	}
%>
		
<%
		
	}
%>
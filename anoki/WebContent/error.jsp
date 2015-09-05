<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="java.io.*,java.util.*"%>
 <%
 String stacktrace = request.getParameter("stacktrace");
 	System.out.println(stacktrace);
 
	 FileWriter testWrite = new FileWriter("/home/hosting_users/perseverance220/tomcat/webapps/anoki/error/error.txt", true);
	
	 // 두번째 인자는 파일 덧붙여쓰기.
	 
	 try {
		Date date = new Date();
		testWrite.write(date+"\n");
	 	testWrite.write(stacktrace+"\n\n");
	 }
	 catch (Exception e) {
		 out.println("Error Message : " + e.toString());
	 }
	 finally {
		 testWrite.close();
	 }
 %>

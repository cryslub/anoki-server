<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="java.io.*,java.util.*"%>
 <%
 String stacktrace = request.getParameter("stacktrace");
 	System.out.println(stacktrace);
 
	 FileWriter testWrite = new FileWriter("/home/hosting_users/perseverance220/tomcat/webapps/anoki/error/error.txt", true);
	
	 // �ι�° ���ڴ� ���� ���ٿ�����.
	 
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

<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="java.io.*"%>
 <%
 String stacktrace = request.getParameter("stacktrace");
 	System.out.println(stacktrace);
 
	 FileWriter testWrite = new FileWriter("/home/hosting_users/perseverance220/tomcat/webapps/anoki/error/error.txt", true);
	
	 // �ι�° ���ڴ� ���� ���ٿ�����.
	 
	 try {
	 	testWrite.write(stacktrace+"\n\n");
	 }
	 catch (Exception e) {
		 out.println("Error Message : " + e.toString());
	 }
	 finally {
		 testWrite.close();
	 }
 %>

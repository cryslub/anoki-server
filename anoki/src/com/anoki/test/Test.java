package com.anoki.test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.gabia.api.ApiClass;
import com.gabia.api.ApiResult;

public class Test {

	public static void main(String[] args) {
	
		SecureRandom random = new SecureRandom();
		String newPass = new BigInteger(130, random).toString(32);	
		System.out.println(newPass.substring(15));
//		sendEmail("cryslub@gmail.com","1234");
		
	}
	
	private static void  sendEmail(String email, String pass){
		
		final String username = "cinderellamessenger@gmail.com";
		final String password = "goodwill1004";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("noreply@anoki.co.kr"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject("아노키 비밀번호 재설정");
			message.setText("변경된 비밀번호 입니다.,"
				+ "\n\n "+pass);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}

}

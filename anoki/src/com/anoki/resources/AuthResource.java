package com.anoki.resources;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anoki.jaxb.Phone;
import com.anoki.jaxb.Response;
import com.anoki.jaxb.Search;
import com.anoki.jaxb.User;
import com.anoki.singleton.AuthCodes;
import com.anoki.singleton.Ibatis;
import com.anoki.singleton.Keys;
import com.anoki.singleton.Sms;





@Path("auth")
public class AuthResource {

	@POST
	@Path("request")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response requestAuth(Phone phone){
		
		int random = (int)(Math.random() * 10000);
		String number = 		String.format("%04d", random);
		System.out.println(number);
		
		AuthCodes.map.put(phone.country+phone.number+phone.device,new Date());
		
		Sms.sendSms("sms",phone.number,"아노키 인증번호 "+number+"를 인증번호 입력란에 입력해 주세요");
		
		
		Response r = new Response();
		r.result = "0";
		
		return r;
	}
	
	
	@POST
	@Path("send/number")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response sendAuthCode(Phone phone){
		
		Date date = AuthCodes.map.get(phone.country+phone.number+phone.device);
		
		Response r = new Response();
		
		if(date== null){
			r.result = "1";
		}else if((new Date()).getTime() - date.getTime() > 180000){
			r.result = "2";
		}else{
			r.result = "0";			
			
		}

		
		return r;
	}
	
	
	@POST
	@Path("account")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response account(Phone phone){
		
		Response r = new Response();
		
		try {
			r.result = (String) Ibatis.object("getAccountWithPhone",phone);
			if(r.result != null){
				String[] arr = r.result.split("@");
				int total = arr[0].length();
				String screened  = arr[0].substring(0, total/2);
				for(int i =0;i<total - total/2;i++){
					screened += "*";
				}
				
				r.result = screened + "@" + arr[1];
				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
	@POST
	@Path("pass")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response pass(Search search){
		
		
		search.id = Keys.getUserId(search.apiKey);

		
		Response r = new Response();
		
		try {
			Integer id = (Integer) Ibatis.object("confirmPass",search);
			
			if(id!=null){
				r.id = id;
				r.result="0";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
	@POST
	@Path("bind")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response bind(User user){
		
		Response r = new Response();
		
		try {
			Ibatis.update("bindPhone",user);
				r.result="0";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
	@POST
	@Path("restore")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response restore(Search search){


		Response r = new Response();
		

		try {

			User user = new User();

			if(search.searchKey != null){
				user.account = search.searchKey;
			}else{
				search.id = Keys.getUserId(search.apiKey);
				user.id = search.id;
				user = (User) Ibatis.object("getUser",user);

			}
		
			SecureRandom random = new SecureRandom();
			String newPass = new BigInteger(130, random).toString(32);			
			user.pass = newPass.substring(15);
			Ibatis.update("updateUser",user);
			
			sendEmail(user.account,user.pass);
			
			
			r.result="0";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
	private void sendEmail(String email, String pass){
		
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
			message.setSubject("아노키 비밃번호 재설정");
			message.setText("변경된 비밀번호 입니다.,"
				+ "\n\n "+pass);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
}

package com.anoki.resources;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
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
import com.gabia.api.ApiClass;





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
		
		AuthCodes.map.put(phone.country+phone.number+phone.device,number);
		
		//sms 전송

		String api_id = "goodwill2015";		// sms.gabia.com 이용 ID
		String api_key = "940d3909388277177c9fcf12bb90beaa";	// 환결설정에서 확인 가능한 SMS API KEY
		
		ApiClass api = new ApiClass(api_id, api_key);
		
		// 단문 발송 테스트
		String arr[] = new String[7];
		arr[0] = "sms";								// 발송 타입 sms or lms
		arr[1] = "1";				// 결과 확인을 위한 KEY ( 중복되지 않도록 생성하여 전달해 주시기 바랍니다. )
		arr[2] = "1";							//  LMS 발송시 제목으로 사용 SMS 발송시는 수신자에게 내용이 보이지 않음.
		arr[3] = "아노키 인증번호 "+number+"를 인증번호 입력란에 입력해 주세요";					// 본문 (90byte 제한)
		arr[4] = "01012345678";			// 발신 번호
		arr[5] = phone.number;				// 수신 번호
		arr[6] = "0";									//예약 일자 "2013-07-30 12:00:00" 또는 "0" 0또는 빈값(null)은 즉시 발송 
		
		System.out.print(arr.toString());
		
		String responseXml = api.send(arr);
		System.out.println("response xml : \n" + responseXml);
		
		Response r = new Response();
		r.result = "0";
		
		return r;
	}
	
	
	@POST
	@Path("send/number")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response sendAuthCode(Phone phone){
		
		String number = AuthCodes.map.get(phone.country+phone.number+phone.device);
		
		Response r = new Response();
		r.result = "1";

		if(phone.auth.equals(number)){
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
		
		search.id = Keys.getUserId(search.apiKey);
		
		Response r = new Response();
		
		try {
			User user = new User();
			user.id = search.id;
			user = (User) Ibatis.object("getUser",user);
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

package com.anoki.resources;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anoki.jaxb.Phone;
import com.anoki.jaxb.Response;
import com.anoki.jaxb.User;
import com.anoki.singleton.AuthCodes;
import com.anoki.singleton.Ibatis;
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
	public Response pass(User user){
		
		Response r = new Response();
		
		try {
			Integer id = (Integer) Ibatis.object("getUserId",user);
			
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
	
}

package com.anoki.resources;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anoki.jaxb.Phone;
import com.anoki.jaxb.Response;
import com.anoki.singleton.AuthCodes;
import com.anoki.singleton.Ibatis;
import com.anoki.singleton.Keys;


@Path("auth")
public class AuthResource {

	@POST
	@Path("request")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response requestAuth(Phone phone){
		
		int number = (int)(Math.random() * 10000);
		System.out.println(number);
		
		AuthCodes.map.put(phone.country+phone.number+phone.device,number);
		
		//sms 전송
		
		Response r = new Response();
		r.result = "0";
		
		return r;
	}
	
	
	@POST
	@Path("send/number")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response sendAuthCode(Phone phone){
		
		int number = AuthCodes.map.get(phone.country+phone.number+phone.device);
		
		Response r = new Response();
		r.result = "1";

		if(number == Integer.parseInt(phone.auth)){
			r.result = "0";
			//db 사용자 생성
		
			try {
				
				Integer id = (Integer) Ibatis.smc.queryForObject("checkUser",phone);
				
				if(id == null){
					id = (Integer) Ibatis.smc.insert("insertUser",phone);
					phone.id = id;
					
					
				}else{
					phone.id = id;
					Ibatis.smc.update("updateUserDevice",phone);
				}
				
				
				r.apiKey = logIn(id);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		return r;
	}
	
	@POST
	@Path("log")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response log(Phone phone){
		
		Response r = new Response();
		
		try {
			Integer id = (Integer) Ibatis.smc.queryForObject("getUserId",phone);
			
			r.id = id;
			r.apiKey = logIn(id);
			r.result="0";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
	
	private String logIn(int id){
		//로그인 처리

		Integer user = 0;
		Integer key=0;
		while(user != null){
			key = (int)(Math.random() * 1000000000);
			user = Keys.map.get(key);				
		}
		
		//생성된 db의 user id 가져오기\
		user = id;
		Keys.map.put(key,user);
		
		return key+"";
	}
}

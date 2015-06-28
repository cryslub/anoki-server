package com.anoki.resources;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anoki.jaxb.Response;
import com.anoki.jaxb.User;
import com.anoki.singleton.Ibatis;
import com.anoki.singleton.Keys;

@Path("user")
public class UserResource {

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response update(User user){
		
		
		Response r = new Response();
		
		user.id = Keys.getUserId(user.apiKey);
		
		try {
			Ibatis.smc.update("updateUser",user);
			r.result = "0";		

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
}

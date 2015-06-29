package com.anoki.resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anoki.jaxb.Prayer;
import com.anoki.jaxb.Response;
import com.anoki.jaxb.Search;
import com.anoki.jaxb.Team;
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
	
	@POST
	@Path("detail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User detail(User user) {
		
		
		User r = new User();
		
		try {
			r = (User) Ibatis.object("getUser", user);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	
	@POST
	@Path("prayer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Prayer> prayer(Search search) {

		search.id = Keys.getUserId(search.apiKey);
		
		List<Prayer> result = new ArrayList<Prayer>();
		try {
			result = (List<Prayer>) Ibatis.list("userPrayer",search);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("team")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Team> team(Search search) {
		
		search.id = Keys.getUserId(search.apiKey);
		
		List<Team> result = new ArrayList<Team>();
		try {
			result = (List<Team>) Ibatis.list("userTeam",search);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
}

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

import com.anoki.jaxb.Dalant;
import com.anoki.jaxb.Notice;
import com.anoki.jaxb.Prayer;
import com.anoki.jaxb.Response;
import com.anoki.jaxb.Search;
import com.anoki.jaxb.Team;
import com.anoki.jaxb.User;
import com.anoki.singleton.Ibatis;
import com.anoki.singleton.Keys;

@Path("user")
public class UserResource {

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response post(User user){
		
		
		Response r = new Response();
		
		try {
			
			Integer id  =(Integer) Ibatis.object("checkUser",user);
			if(id == null){
				r.id = Ibatis.insert("insertUser",user);
				Ibatis.update("bindPhone",user);

				r.result = "0";		
				r.apiKey = logIn(r.id);
			}else{
				r.result = "2";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
	
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
	
	
	@SuppressWarnings("unchecked")
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
	
	@POST
	@Path("log")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response log(User user){
		
		Response r = new Response();
		
		try {
			Integer id = (Integer) Ibatis.object("getUserId",user);
			
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
	
	@POST
	@Path("charge")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response charge(User user){
		
		user.id = Keys.getUserId(user.apiKey);
		
		Response r = new Response();
		

		try {
			Ibatis.insert("charge",user);
			Ibatis.update("addDalant",user);
			
			r.result="0";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("dalant")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Dalant> dalant(Search search){
		
		search.id = Keys.getUserId(search.apiKey);
		
		
		List <Dalant> r = new ArrayList <Dalant>();
		
		try {
			
			r = (List <Dalant>)Ibatis.list("dalant", search);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	

	@POST
	@Path("check")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response check(User user) {

		
		Response result = new Response();
		
		try {
			Integer id  =(Integer) Ibatis.object("checkUser",user);
			if(id == null){
				result.result = "0";
			}
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}	
	
}

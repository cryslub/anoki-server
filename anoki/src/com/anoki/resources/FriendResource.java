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

import com.anoki.jaxb.Friend;
import com.anoki.jaxb.Invite;
import com.anoki.jaxb.Response;
import com.anoki.jaxb.Search;
import com.anoki.singleton.Ibatis;
import com.anoki.singleton.Keys;

@Path("friend")
public class FriendResource {

	@SuppressWarnings("unchecked")
	@POST
	@Path("list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Friend> list(Search search) {

		search.id = Keys.getUserId(search.apiKey);
		
		List<Friend> result = new ArrayList<Friend>();
		try {
			result = (List<Friend>) Ibatis.list("friend",search);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFriend(Friend friend) {
		Response r = new Response();
		
		friend.user = Keys.getUserId(friend.apiKey);
		
		try {
			Ibatis.update("updateFriend", friend);

			r.result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(Invite invite) {
		Response r = new Response();
		
		invite.id = Keys.getUserId(invite.apiKey);
		
		try {
			Ibatis.insert("addFriend", invite);

			r.result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
}

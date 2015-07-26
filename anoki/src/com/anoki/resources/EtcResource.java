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

import com.anoki.jaxb.Alarm;
import com.anoki.jaxb.Friend;
import com.anoki.jaxb.Message;
import com.anoki.jaxb.Notice;
import com.anoki.jaxb.Response;
import com.anoki.jaxb.Search;
import com.anoki.singleton.Ibatis;
import com.anoki.singleton.Keys;

@Path("etc")
public class EtcResource {

	@SuppressWarnings("unchecked")
	@POST
	@Path("alarm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Alarm> alarm(Search search) {

		search.id = Keys.getUserId(search.apiKey);
		
		List<Alarm> result = new ArrayList<Alarm>();
		try {
			result = (List<Alarm>) Ibatis.list("alarm",search);
			Ibatis.delete("deleteAlarm",search);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("message")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> message(Search search) {

		search.id = Keys.getUserId(search.apiKey);
		
		List<Message> result = new ArrayList<Message>();
		try {
			result = (List<Message>) Ibatis.list("message",search);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	
	
	@POST
	@Path("send/message")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendMessage(Message message) {
		Response r = new Response();
		
		message.id = Keys.getUserId(message.apiKey);
		
		try {
			Ibatis.insert("insertMessage", message);

			r.result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("notice")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Notice> notice() {

		
		List<Notice> result = new ArrayList<Notice>();
		try {
			result = (List<Notice>) Ibatis.list("notice",null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	
}


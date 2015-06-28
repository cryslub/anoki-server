package com.anoki.resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anoki.jaxb.Alarm;
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
}


package com.anoki.resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anoki.jaxb.Search;
import com.anoki.jaxb.Team;
import com.anoki.singleton.Ibatis;

@Path("team")
public class TeamResource {
	

	
	@SuppressWarnings("unchecked")
	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Team> search(Search search) {
		
		
		List<Team> result = new ArrayList<Team>();
		try {
			result = (List<Team>) Ibatis.list("teamSearch",search);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
}

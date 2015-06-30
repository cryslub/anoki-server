package com.anoki.resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anoki.jaxb.Prayer;
import com.anoki.jaxb.Search;
import com.anoki.jaxb.Team;
import com.anoki.singleton.Ibatis;
import com.anoki.singleton.Keys;

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

	
	@POST
	@Path("detail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Team detail(Search search) {
		
		search.id = Keys.getUserId(search.apiKey);
		
		Team r = new Team();
		
		try {
			r = (Team) Ibatis.object("getTeam", search);
			
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
	public Prayer prayer(Search search) {
		
		search.id = Keys.getUserId(search.apiKey);
		
		Prayer r = new Prayer();
		
		try {
			r = (Prayer) Ibatis.object("teamPrayer", search);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
}

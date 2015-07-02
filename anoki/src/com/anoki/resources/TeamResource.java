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

import com.anoki.jaxb.Member;
import com.anoki.jaxb.Phone;
import com.anoki.jaxb.Prayer;
import com.anoki.jaxb.Response;
import com.anoki.jaxb.Search;
import com.anoki.jaxb.Team;
import com.anoki.jaxb.Invite;
import com.anoki.jaxb.User;
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
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("prayer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Prayer> prayer(Search search) {
		
		search.id = Keys.getUserId(search.apiKey);
		
		List<Prayer> r = new ArrayList<Prayer>();
		
		try {
			r = (List<Prayer>) Ibatis.list("teamPrayer", search);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(Team team) {
		
		Response r = new Response();
		
		try {
			team.userId = Keys.getUserId(team.apiKey);
			team.id = Ibatis.insert("insertTeam",team);
			Ibatis.insert("insertFirstMember",team);
			
			r.result = "0";
			
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
				
		return r;	
		
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("invite")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response invite(Invite teamInvite) {
		
		Response r = new Response();
		
		
		try {
			
			if(teamInvite.phone != null){
				if(teamInvite.friends == null) teamInvite.friends = new ArrayList<Integer> ();
				
				for(Phone phone : teamInvite.phone){	
					int id = (Integer) Ibatis.smc.insert("insertUser",phone);
					teamInvite.friends.add(id);
				}
			}

			if(teamInvite.friends != null && teamInvite.friends.size()>0){
				for(int friend : teamInvite.friends){	
				
					
					Member member = new Member();
					member.team = teamInvite.team;
					member.user = friend;
					
					Ibatis.insert("insertMember", member);
//					Ibatis.insert("insertAlarm", teamInvite);
				}

			}
			
			r.result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("members")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> members(Search search) {
		
		
		List<User> r = new ArrayList<User>();
		
		try {
			r = (List<User>) Ibatis.list("teamMembers", search);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	@PUT
	@Path("member")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response member(Member member) {
		
		Response r = new Response();
				
		
		try {
			Ibatis.update("updateMember", member);
			r.result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response put(Team team) {
		
		Response r = new Response();
				
		
		try {
			Ibatis.update("updateTeam", team);
			r.result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	@POST
	@Path("member/join")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response join(Member member) {
		
		
		member.user = Keys.getUserId(member.apiKey);
		
		Response r = new Response();
				
		
		try {
			
			Ibatis.insert("join", member);
			r.result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}

	
	@PUT
	@Path("member/state")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response memberState(Member member) {
		
				
		Response r = new Response();
				
		
		try {
			
			Ibatis.update("updateMemberState", member);
			r.result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
}

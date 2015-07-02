package com.anoki.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.anoki.jaxb.Friend;
import com.anoki.jaxb.Phone;
import com.anoki.jaxb.Prayer;
import com.anoki.jaxb.Reply;
import com.anoki.jaxb.Response;
import com.anoki.jaxb.Search;
import com.anoki.singleton.Ibatis;
import com.anoki.singleton.Keys;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("prayer")
public class PrayerResource {
	
	@Context ServletContext context;
	

	@SuppressWarnings("unchecked")
	@POST
	@Path("recent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Prayer> recent(Search search) {

		search.id = Keys.getUserId(search.apiKey);
		
		List<Prayer> result = new ArrayList<Prayer>();
		try {
			result = (List<Prayer>) Ibatis.list("recent",search);
			for(Prayer prayer : result){
				prayer.media = (List<String>) Ibatis.list("mediaList",search);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Prayer post(Prayer prayer) {
		
		
		try {
			prayer.userId = Keys.getUserId(prayer.apiKey);
			prayer.id = Ibatis.insert("insertPrayer",prayer);
			
			if(prayer.media != null && prayer.media.size() > 0)
				Ibatis.insert("updateMedia", prayer);
		
			if(prayer.phone != null){
				if(prayer.friends == null) prayer.friends = new ArrayList<Integer> ();
				
				for(Phone phone : prayer.phone){	
					Integer id = (Integer) Ibatis.object("checkUser", phone);
					
					if(id == null){
						id = (Integer) Ibatis.smc.insert("insertUser",phone);
					}
					
					prayer.friends.add(id);
					
					Friend friend = new Friend();
					friend.user = prayer.userId;
					friend.friend = id;
					
					Object obj= Ibatis.object("checkFriend", friend);
					
					if(obj == null){					
						Ibatis.insert("insertFriend", friend);
					}
				}
			}

			if(prayer.friends != null && prayer.friends.size()>0){
				Ibatis.insert("insertRequest", prayer);
				Ibatis.insert("insertRequestAlarm", prayer);

			}

			
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
				
		return prayer;	
		
	}
	
	
	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		Response r = new Response();
		r.result = "0";

		String imagePath = context.getRealPath("")+"/images/";
		
		try {
			int id = Ibatis.insert("insertMedia", null);
			
			String filePath = imagePath	+ id;

			// save the file to the server
			saveFile(fileInputStream, filePath);

			r.id = id;
			r.result = "0";
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		

		return r;

	}

	// save uploaded file to a defined location on the server
	private void saveFile(InputStream uploadedInputStream,
			String serverLocation) {

		try {
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	@POST
	@Path("scrap")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response scrap(Prayer prayer) {
		Response r = new Response();
		
		prayer.userId = Keys.getUserId(prayer.apiKey);
		
		try {
			Ibatis.insert("insertScrap", prayer);

			r.result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	
	@POST
	@Path("pray")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response pray(Prayer prayer) {
		Response r = new Response();
		
		prayer.userId = Keys.getUserId(prayer.apiKey);
		
		try {
			Ibatis.insert("insertPray", prayer);

			r.result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("detail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Prayer detail(Search search) {
		
		
		search.id = Keys.getUserId(search.apiKey);
		
		Prayer r = new Prayer();
		
		try {
			r = (Prayer) Ibatis.object("getPrayer", search);
			r.media = (List<String>) Ibatis.list("mediaList",search);
			r.reply = (List<Reply>) Ibatis.list("replyList",search);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	

	@SuppressWarnings("unchecked")
	@POST
	@Path("scrapd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Prayer> scrapd(Search search) {

		search.id = Keys.getUserId(search.apiKey);
		
		List<Prayer> result = new ArrayList<Prayer>();
		try {
			result = (List<Prayer>) Ibatis.list("scrapd",search);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("request")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Prayer> request(Search search) {

		search.id = Keys.getUserId(search.apiKey);
		
		List<Prayer> result = new ArrayList<Prayer>();
		try {
			result = (List<Prayer>) Ibatis.list("request",search);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	@POST
	@Path("reply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reply(Reply reply) {
		Response r = new Response();
		
		reply.userId = Keys.getUserId(reply.apiKey);
		
		try {
			Ibatis.insert("insertReply", reply);

			r.result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return r;
	}
	
	
}

package com.anoki.scheduler;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.anoki.jaxb.Team;
import com.anoki.singleton.Ibatis;

public class Scheduler  implements ServletContextListener {
	 private Thread t = null;
	 private ServletContext context;
	    
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		// TODO Auto-generated method stub
		 t.interrupt();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		 t =  new Thread(){
	            //task
	            public void run(){                
	                try {
	                    while(true){
	                        try {
		                        

	                        	Ibatis.object("heartbeat",null);
	                        
		                        
	                        	Thread.sleep(1000*60*60);
								
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                    	
	                    }
	                } catch (InterruptedException e) {}
	            }            
	        };
	        t.start();
//	      
//	      t =  new Thread(){
//	            //task
//	            public void run(){                
//	                try {
//	                    while(true){
//	                        try {
//		                        
//
//	                        	Integer id = (Integer) Ibatis.object("getScheduler",null);
//	                        	if(id == null){
//		                        	Calendar now = Calendar.getInstance();
//		                        	if( now.get(Calendar.HOUR_OF_DAY) == 0){
//				                        
//			                        	Ibatis.update("resetPayed", null);
//			                        	
//										@SuppressWarnings("unchecked")
//										List<Team> list = (List<Team>) Ibatis.list("bigTeamList",null);
//										for(Team team : list){
//											if(team.leaderDalant >= team.dalant){
//												Ibatis.insert("spendOnTeam",team);
//												Ibatis.update("spendTeamDalant",team);
//												Ibatis.update("paySucceed", team);
//											}else{
//												Ibatis.update("payFailed", team);
//											}									
//										}
//										
//										Ibatis.insert("insertScheduler");
//		                        		
//		                        	}
//		                        	
//		                   	                        	    
//	                        	}
//		                        
//	                        	Thread.sleep(1000*60*5);
//								
//							} catch (SQLException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//	                    	
//	                    }
//	                } catch (InterruptedException e) {}
//	            }            
//	        };
//	        t.start();
	       // context = contextEvent.getServletContext();
	        // you can set a context variable just like this
	      //  context.setAttribute("TEST", "TEST_VALUE");
	}
	

}

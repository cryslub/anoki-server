package com.anoki.singleton;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;


public class Ibatis {
	
	public static  SqlMapClient smc;
	
	static {
		if(smc == null){
			Reader rd;
			try {
				Charset charset = Charset.forName("UTF-8"); // 인코딩 설정
				Resources.setCharset(charset);
				rd = Resources.getResourceAsReader("SqlMapConfig.xml");
				smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		  
			
		}
	}
	
	public static int insert(String id, Object arg) throws SQLException{
		
		Object obj =smc.insert(id,arg);
		if(obj != null) return (Integer) obj;
		else return -1;
		
	}
	
	public static int insert(String id) throws SQLException{
		
		Object obj =smc.insert(id);
		if(obj != null) return (Integer) obj;
		else return -1;
		
	}
	
	
	public static void update(String id,Object arg) throws SQLException{
		smc.update(id,arg);
	}

	public static List<?> list(String id,Object arg) throws SQLException{
		return smc.queryForList(id, arg);
	}

	public static void delete(String id, Object arg) throws SQLException {
		// TODO Auto-generated method stub
		smc.delete(id,arg);
	}

	public static Object object(String id, Object arg) throws SQLException {
		// TODO Auto-generated method stub
		return smc.queryForObject(id,arg);
	}

	public static void delete(String string) throws SQLException {
		// TODO Auto-generated method stub
		smc.delete(string);		
	}
}

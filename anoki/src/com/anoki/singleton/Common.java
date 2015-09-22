package com.anoki.singleton;

import java.sql.SQLException;
import java.util.List;

import com.anoki.jaxb.Media;
import com.anoki.jaxb.Prayer;

public class Common {

	@SuppressWarnings("unchecked")
	public static List<Prayer> makePrayer(List<Prayer> result){
		try {			
			for(Prayer prayer : result){
				prayer.media = (List<Media>) Ibatis.list("mediaList",prayer);
				prayer.friends = (List<Integer>) Ibatis.list("",prayer);
				prayer.phone = (List<String>) Ibatis.list("",prayer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return result;
	}
}

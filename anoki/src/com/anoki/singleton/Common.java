package com.anoki.singleton;

import java.sql.SQLException;
import java.util.List;

import com.anoki.jaxb.Friend;
import com.anoki.jaxb.Media;
import com.anoki.jaxb.Prayer;

public class Common {

	@SuppressWarnings("unchecked")
	public static List<Prayer> makePrayer(List<Prayer> result){
		try {			
			for(Prayer prayer : result){
				prayer.media = (List<Media>) Ibatis.list("mediaList",prayer);
				prayer.friends = (List<Friend>) Ibatis.list("requestFriends",prayer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return result;
	}
	
	

	
	public static void sendInvite(String name, String number){
		Sms.sendSms("lms",number,name + "님이 기도어플 아노키로 초대하셨습니다. 아래를 누르시면 " + name + "님과 친구가 됩니다. \n\n http://anoki.co.kr/anoki/invite.jsp");
	}
}

package com.anoki.singleton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.anoki.jaxb.Content;
import com.anoki.jaxb.Friend;
import com.anoki.jaxb.Media;
import com.anoki.jaxb.Prayer;
import com.google.gson.Gson;

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
		Sms.sendSms("lms",number,name + "님이 기도어플 아노키로 초대하셨습니다. 아래를 누르시면 " + name + "님과 친구가 됩니다. \n\n https://play.google.com/apps/testing/com.anoki1");
	}
	
	
	
	public static void alarm(){
		
	}
	
	public static void gcm(String regId, String type, String name1){
		gcm(regId,type,name1,null);
	}
	
	public static void gcm(String regId, String type, String name1,String name2){

		String apiKey = "AIzaSyBLHp6yPI4akOdJn3xb5QBTzBaBhk42Ojs";
		
		Content content = new Content();
		content.addRegId(regId);
		content.createData(type, name1,name2);
		
        try{

        // 1. URL
        URL url = new URL("https://android.googleapis.com/gcm/send");

        // 2. Open connection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 3. Specify POST method
        conn.setRequestMethod("POST");

        // 4. Set the headers
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key="+apiKey);

        conn.setDoOutput(true);

            // 5. Add JSON data into POST request body

            //`5.1 Use Jackson object mapper to convert Contnet object into JSON
            ObjectMapper mapper = new ObjectMapper();

            // 5.2 Get connection output stream
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));

            Gson gson = new Gson();

			writer.write(gson.toJson(content));
			writer.close();

			

            // 5.5 close
            wr.close();

            // 6. Get the response
            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 7. Print result
            System.out.println(response.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}

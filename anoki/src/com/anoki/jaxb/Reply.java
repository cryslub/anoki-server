package com.anoki.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reply {
	
	public int id;
	public String apiKey;
	public String name;
	public String picture;
	public String text;
	public String time;
	public String type;
	public String pub;
	public int prayer;
	public int userId;
	public String userPicture;
	
}

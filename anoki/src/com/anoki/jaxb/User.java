package com.anoki.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	public int id;
	public String apiKey;
	public String name;
	public String text;
	public String media;
	public String showPhone;
	
}

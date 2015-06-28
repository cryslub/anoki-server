package com.anoki.jaxb;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Phone {
	
	public int id;
	public String country;//국가번호
	public String number;//전화번호
	public String device;//device id
	public String auth;
}

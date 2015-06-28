package com.anoki.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Search {
	public int id;
	public String apiKey;
	public int page;
	public int size;
}

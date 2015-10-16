package com.anoki.jaxb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Content implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> registration_ids;
    private Map<String,String> data;

    public void addRegId(String regId){
        if(registration_ids == null)
            registration_ids = new LinkedList<String>();
        registration_ids.add(regId);
    }

    public void createData(String type, String name1,String name2){
        if(data == null)
            data = new HashMap<String,String>();

        data.put("type", type);
        data.put("name1", name1);
        data.put("name2", name2);

    }
}
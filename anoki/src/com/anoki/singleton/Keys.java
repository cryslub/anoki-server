package com.anoki.singleton;

import java.util.HashMap;
import java.util.Map;

public class Keys {
	public static Map<Integer,Integer> map = new HashMap<Integer,Integer>();
	
	public static int getUserId(String key){
		return map.get(Integer.parseInt(key));
	}
}

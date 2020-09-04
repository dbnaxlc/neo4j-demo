package com.xzq.neo4j.movies.controller;

import java.util.HashMap;

public class ResponseModel{

	private static HashMap<String,Object> model=null;
	
	private ResponseModel() {}
	
	public static HashMap<String, Object> getModel(String msg,String status,Object data){
		model = new HashMap<>();
		model.put("msg", msg);
		model.put("status", status);
		model.put("data", data);
		return model;
	}
	
}

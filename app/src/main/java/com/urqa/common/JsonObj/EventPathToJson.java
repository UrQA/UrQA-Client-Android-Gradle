package com.urqa.common.JsonObj;

import com.urqa.eventpath.EventPath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class EventPathToJson extends JsonObj {
	public String idsession;
	public List<EventPath> eventpaths;
	@Override
	public String toJson() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("idsession", idsession);
			////////////////Event Path 계산////////////////////
			JSONArray eventpath = new JSONArray();
			for(int i = 0 ; i < eventpaths.size(); i++)
			{
				JSONObject event = new JSONObject();
				
				event.put("datetime", eventpaths.get(i).getDatetime());
				event.put("classname", eventpaths.get(i).getClassName());
				event.put("methodname", eventpaths.get(i).getMethodName());
				event.put("linenum", eventpaths.get(i).getLine());
				
				eventpath.put(event);
			}
			jsonObject.put("eventpaths", eventpath);
			
			return jsonObject.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	@Override
	public void fromJson(String JsonString) {
		// TODO Auto-generated method stub
		
	}
	
	
}

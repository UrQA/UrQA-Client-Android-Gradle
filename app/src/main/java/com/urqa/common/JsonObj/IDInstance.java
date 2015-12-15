package com.urqa.common.JsonObj;

import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class IDInstance extends JsonObj {
	public String idinstance;

	@Override
	public String toJson() {
		return "";
	}

	@Override
	public void fromJson(String JsonString) {
		try {
			JSONObject obj = new JSONObject(JsonString);
			idinstance = obj.get("idinstance").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

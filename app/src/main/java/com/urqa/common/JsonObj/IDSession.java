package com.urqa.common.JsonObj;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @deprecated Use {@link com.urqa.library.model.Authentication}.
 */
@Deprecated
public class IDSession extends JsonObj {
	private String mId;

	@Override
	public String toJson() {
		return null;
	}

	@Override
	public void fromJson(String JsonString) {
		try {
			JSONObject obj = new JSONObject(JsonString);
			mId = obj.get("idsession").toString(); 
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getId() {
		return mId;
	}
}

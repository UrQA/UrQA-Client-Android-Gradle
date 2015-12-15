package com.urqa.common.JsonObj;

import com.urqa.common.StateData;
import com.urqa.eventpath.EventPath;
import com.urqa.library.model.JsonInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ErrorSendData extends JsonObj implements JsonInterface {
	public ErrorSendData() {
		// TODO Auto-generated constructor stub
		sdkversion = StateData.SDKVersion;
		locale = "";
		apikey = "";
		datetime = "unknown";
		device = "unknown";
		country = "unknown";
		errorname = "";
		errorclassname = "";
		linenum = -1;
		callstack = "";
		appversion = "unknown";
		osversion = "unknown";
		gpson = 0; // int 0 = off ,1 = on
		wifion = 0;
		mobileon = 0;
		scrwidth = -1;
		scrheight = -1;
		lastactivity = "";

		// CallStackFileName = null;
		// LogFileName = null;
		batterylevel = -1;
		availsdcard = -1;
		rooted = 0; // int 0 = off ,1 = on
		appmemtotal = -1; // mega단위로 int.
		appmemfree = -1;
		appmemmax = -1;
		kernelversion = "";
		xdpi = -1;
		ydpi = -1;
		scrorientation = -1;
		sysmemlow = 0; // int 0 = not 1 = ok
		tag = "";
		rank = -1;
		consoleLog = "";
		
		mDeviceId = "";
		mCarrierName = "";
	}

	public String sdkversion;
	public String locale;

	public String tag;

	public int rank;

	/**
	 * CallStack데이터
	 */
	public String callstack;

	/**
	 * APIKEY
	 */
	public String apikey;
	/**
	 * 에러 발생 시간
	 */
	public String datetime;
	/**
	 * 핸드폰 모델 명
	 */
	public String device;
	/**
	 * 국가명
	 */
	public String country;
	/**
	 * 에러 이름
	 */
	public String errorname;
	/**
	 * 에러가 발생한 클래스 이름 ClassName 클래스 이름
	 */
	public String errorclassname;
	/**
	 * Error Code Line
	 */
	public int linenum;
	/**
	 * 앱 버젼
	 */
	public String appversion;
	/**
	 * OS 버젼
	 */
	public String osversion;
	// public String CallStack;
	// public String UserLog;

	/**
	 * GPS On/Off
	 */
	public int gpson;
	/**
	 * WiFi On/Off
	 */
	public int wifion;
	/**
	 * MobileNetwork(3G) On/Off
	 */
	public int mobileon;
	/**
	 * 화면 가로 크기
	 */
	public int scrwidth;
	/**
	 * 화면 세로 크기
	 */
	public int scrheight;

	/**
	 * 배터리 잔량
	 */
	public int batterylevel;

	/**
	 * 사용가능한 용량 //mega단위로 int
	 */
	public int availsdcard;

	/**
	 * 루트 되었는지 안되었는지
	 */
	public int rooted;

	/**
	 * 사용량
	 */
	public int appmemtotal;

	/**
	 * 남은량
	 */
	public int appmemfree;

	/**
	 * 최대량
	 */
	public int appmemmax;

	/**
	 * 리눅스 커널 버전
	 */
	public String kernelversion;

	/**
	 * XDPI
	 */
	public float xdpi;
	/**
	 * YDPI
	 */
	public float ydpi;

	/**
	 * 0,2 세로 1,3 가로
	 */
	public int scrorientation;

	/**
	 * 시스템 메모리가 부족한가 안부족한가
	 */
	public int sysmemlow;
	
	/**
	 * 통신사
	 */
	 public String mCarrierName; 
	 
	 
	 /**
	  * id
	  */
	 
	 public String mDeviceId;

	/**
	 * console log
	 */
	public String consoleLog;

	public String lastactivity;

	public List<EventPath> eventpaths;

	@Override
	public String toJson() {
		return toJSONObject().toString();
	}

	@Override
	public void fromJson(String JsonString) {

	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		try {
            object.put("sdkversion", sdkversion);
            object.put("locale", locale);
            object.put("tag", tag);
            object.put("rank", rank);
            object.put("callstack", callstack);
            object.put("apikey", apikey);
            object.put("datetime", datetime);
            object.put("device", device);
            object.put("country", country);
            object.put("errorname", errorname);
            object.put("errorclassname", errorclassname);
            object.put("linenum", linenum);
            object.put("appversion", appversion);
            object.put("osversion", osversion);
            object.put("gpson", gpson);
            object.put("wifion", wifion);
            object.put("mobileon", mobileon);
            object.put("scrwidth", scrwidth);
            object.put("scrheight", scrheight);
            object.put("batterylevel", batterylevel);
            object.put("availsdcard", availsdcard);
            object.put("rooted", rooted);
            object.put("appmemtotal", appmemtotal);
            object.put("appmemfree", appmemfree);
            object.put("appmemmax", appmemmax);
            object.put("kernelversion", kernelversion);
            object.put("xdpi", xdpi);
            object.put("ydpi", ydpi);
            object.put("scrorientation", scrorientation);
            object.put("sysmemlow", sysmemlow);
            object.put("lastactivity", lastactivity);
            object.put("carrier_name", mCarrierName);
            object.put("device_id", mDeviceId);
            object.put("eventpaths", getEventPath());
		} catch (JSONException e) {

		}
		return object;
	}

    /**
	 * Event Path 계산
	 * @return
	 */
	private JSONArray getEventPath() throws JSONException {
		JSONArray array = new JSONArray();
        for (EventPath eventpath : eventpaths) {
            JSONObject object = new JSONObject();

            object.put("datetime", eventpath.getDatetime());
            object.put("classname", eventpath.getClassName());
            object.put("methodname", eventpath.getMethodName());
            object.put("label", eventpath.getLabel());
            object.put("linenum", eventpath.getLine());

            array.put(object);
        }
		
		return array;
	}
}

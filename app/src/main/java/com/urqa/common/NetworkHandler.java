package com.urqa.common;

import android.os.Handler;
import android.os.Message;

public class NetworkHandler extends Handler {
	String responseStr;
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		responseStr = (String)msg.obj;
	}
}

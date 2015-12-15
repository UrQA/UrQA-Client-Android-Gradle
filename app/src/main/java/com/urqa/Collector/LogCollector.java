package com.urqa.Collector;

import android.content.Context;

import com.urqa.common.StateData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LogCollector {

	public final static String getLog(Context context) {

		if(StateData.hasReadLogsPermission.equals("FALSE"))
			return "";

		StringBuilder LOGCAT_CMD = new StringBuilder();
	    LOGCAT_CMD.append("logcat").append(" -d").append(" -v").append(" time").append(" tags").append(" *:V");
	    //.append(StateData.LogFilter);
	    
	    Process logcatProc = null;
	    
	    try {
	        logcatProc = Runtime.getRuntime().exec(LOGCAT_CMD.toString());
	    } catch (IOException e) {
	        e.printStackTrace();
	        return "";
	    }
	    BufferedReader reader = null;
	    StringBuilder strOutput = new StringBuilder();
	    ArrayList<String> LogList = new ArrayList<String>();
	    try {
	        reader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()));
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	LogList.add(line);
	        }
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    int LogLineStart = LogList.size() - StateData.LogLine;
	    if(LogLineStart < 0)
	    	LogLineStart = 0;
	     
	    int LogLineEnd = LogList.size();
	    
	    for(int i = LogLineStart ; i < LogLineEnd; i++)
	    {
	    	strOutput.append(LogList.get(i)).append("\n");
	    }
	    return strOutput.toString();
	}
	
}

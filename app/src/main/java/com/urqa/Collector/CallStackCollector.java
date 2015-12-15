package com.urqa.Collector;

import com.urqa.common.CallStackData;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;


public class CallStackCollector {

	public static String GetCallStack(Throwable Errorthrow ) 
	{
	    Writer callstackwirter = new StringWriter();
        PrintWriter callstackprinter = new PrintWriter(callstackwirter);
        
        Errorthrow.printStackTrace(callstackprinter);
        String CallStackString = callstackwirter.toString();
        callstackprinter.close();
        
        return CallStackString; 
	}
	
	
	public static CallStackData ParseStackTrace(Throwable errorThrow,String callStackString)
	{
		boolean RunTimeError = false;
		CallStackData data = new CallStackData();
		
        Throwable cause = errorThrow.getCause();
        if(cause != null)
            RunTimeError = true;
        
        Throwable recordthrow;
        if(RunTimeError)
        	recordthrow = cause;
        else
        	recordthrow = errorThrow;
                
        String [] errorname = callStackString.split("\n");
        data.ErrorName = errorname[0].toString(); 
        StackTraceElement[] ErrorElements = recordthrow.getStackTrace();
        
        data.ClassName = ErrorElements[0].getClassName();
        data.Line = ErrorElements[0].getLineNumber();
        
        String activityclass = SearchCallstackinActivity(ErrorElements);
        data.ActivityName = activityclass;
               
        return data;
	}
	
	public static String SearchCallstackinActivity(StackTraceElement[] ErrorElements)
	{
		String Activityclassname = "";
		for(int i = 0 ; i < ErrorElements.length ; i++)
		{
			String classname = ErrorElements[i].getClassName();
			try{
				Class klass = Class.forName(classname);
				if(android.app.Activity.class.isAssignableFrom(klass))
				{
					Activityclassname = classname;
					break;
				}
			}
			catch(Exception e)
			{
				
			}
		}
		return Activityclassname;
	}
}

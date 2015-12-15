package com.urqa.eventpath;

import com.urqa.Collector.DateCollector;
import com.urqa.common.StateData;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @since 2013. 8. 12.오후 4:49:57
 * @author JeongSeungsu
 */
public class EventPathManager {
	
	static List<EventPath> EventList = new ArrayList<EventPath>();
	
	//이러면 걍 스태틱 된다네?
	private static int MaxEventPath = 10;

	private static EventPath ErrorEventPaths[] = new EventPath[MaxEventPath];
	private static int ErrorEventPathsCounter = 0;
	
	static synchronized public void CreateEventPath(int Step,String label)
	{
		StackTraceElement[] stackTrace = new Exception().getStackTrace();
				
		EventPath eventpath = new EventPath(DateCollector.GetDateYYMMDDHHMMSS(StateData.AppContext),
											stackTrace[Step].getClassName(), 
											stackTrace[Step].getMethodName(),
											label,
											stackTrace[Step].getLineNumber());
		
		ShiftErrorEventPath();
		ErrorEventPaths[MaxEventPath-1] = eventpath;
		ErrorEventPathsCounter++;
		
		EventList.add(eventpath);
	}
	
	static private void ShiftErrorEventPath()
	{
		for(int i = 0 ; i < MaxEventPath - 1 ; i++ )
			ErrorEventPaths[i] = ErrorEventPaths[i+1];
	}
	
	static public List<EventPath> getEventPath()
	{
		return EventList;
	}
	
	static public List<EventPath> GetErrorEventPath()
	{
		List<EventPath> erroreventpaths = new ArrayList<EventPath>();
		
		int MaxCounter = ErrorEventPathsCounter;
		if(MaxCounter >= MaxEventPath)
			MaxCounter = MaxEventPath;
		
		for(int i = MaxEventPath - MaxCounter ; i< MaxEventPath; i++)
		{
			erroreventpaths.add(ErrorEventPaths[i]);
		}
		
		return erroreventpaths;
	}
	static public int GetErrorEventPathCounter()
	{
		return ErrorEventPathsCounter;
	}
	
	static public void ClearEvent()
	{
		EventList.clear();
	}
	
	static public List<EventPath> GetNumberofEventPath(int number)
	{
		int listsize = EventList.size();
		int startnum = listsize - number;
		List<EventPath> numeventpath = new ArrayList<EventPath>();
		
		if(startnum < 0)
			startnum = 0;
		
		for(int i = startnum; i < listsize; i++)
		{
			numeventpath.add(EventList.get(i));
		}
		
		return numeventpath;
	}

}

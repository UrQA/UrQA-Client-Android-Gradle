package com.urqa.rank;

public enum ErrorRank {
	
	@Deprecated
	Nothing(-1), // Not using
	Unhandle(0), //
	Native(1), //
	Critical(2), //
	Major(3), //
	Minor(4), //
	; 
	 
	private final int value;
	
	ErrorRank(int value) 
	{
		this.value = value; 
	}
    public int value() 
    {
    	return value; 
    }
}

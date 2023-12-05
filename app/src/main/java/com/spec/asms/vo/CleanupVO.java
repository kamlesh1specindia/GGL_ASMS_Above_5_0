package com.spec.asms.vo;

import com.spec.asms.common.Constants;

public class CleanupVO 
{
	
	private int id;
	private long lastCleanupDate;
	private String cleanUpPeriod = Constants.LABEL_BLANK;
	private int userId;
	public int getId() 
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public long getLastCleanupDate() 
	{
		return lastCleanupDate;
	}
	public void setLastCleanupDate(long lastCleanupDate) 
	{
		this.lastCleanupDate = lastCleanupDate;
	}
	public String getCleanUpPeriod() 
	{
		return cleanUpPeriod;
	}
	public void setCleanUpPeriod(String cleanUpPeriod) 
	{
		this.cleanUpPeriod = cleanUpPeriod;
	}
	public int getUserId() 
	{
		return userId;
	}
	public void setUserId(int userId) 
	{
		this.userId = userId;
	}
	
	
}

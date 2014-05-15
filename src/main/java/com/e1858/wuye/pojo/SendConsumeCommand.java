package com.e1858.wuye.pojo;

public class SendConsumeCommand {
	private long sendCommunityId;
	private long sendConsumeTypeId;
	private String sendPayNumber;
	private int sendYear;
	private int sendMonth;
	private long sendHouseId;
	private long sendHouseRoomId;
	private String sendTitle;	
	private long sendConsumeTemplate;

	
	public long getSendCommunityId()
	{
		return sendCommunityId;
	}
	public void setSendCommunityId(long sendCommunityId)
	{
		this.sendCommunityId = sendCommunityId;
	}
	public long getSendConsumeTypeId()
	{
		return sendConsumeTypeId;
	}
	public void setSendConsumeTypeId(long sendConsumeTypeId)
	{
		this.sendConsumeTypeId = sendConsumeTypeId;
	}
	public String getSendPayNumber()
	{
		return sendPayNumber;
	}
	public void setSendPayNumber(String sendPayNumber)
	{
		this.sendPayNumber = sendPayNumber;
	}
	public int getSendYear()
	{
		return sendYear;
	}
	public void setSendYear(int sendYear)
	{
		this.sendYear = sendYear;
	}
	public int getSendMonth()
	{
		return sendMonth;
	}
	public void setSendMonth(int sendMonth)
	{
		this.sendMonth = sendMonth;
	}
	public long getSendHouseId()
	{
		return sendHouseId;
	}
	public void setSendHouseId(long sendHouseId)
	{
		this.sendHouseId = sendHouseId;
	}
	public long getSendHouseRoomId()
	{
		return sendHouseRoomId;
	}
	public void setSendHouseRoomId(long sendHouseRoomId)
	{
		this.sendHouseRoomId = sendHouseRoomId;
	}
	
	public String getSendTitle()
	{
		return sendTitle;
	}
	public void setSendTitle(String sendTitle)
	{
		this.sendTitle = sendTitle;
	}
	public long getSendConsumeTemplate()
	{
		return sendConsumeTemplate;
	}
	public void setSendConsumeTemplate(long sendConsumeTemplate)
	{
		this.sendConsumeTemplate = sendConsumeTemplate;
	}
}

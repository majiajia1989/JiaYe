package com.e1858.wuye.pojo;

public class ConsumeTemplateCommand {
	private long id;
	private long consumeType;
	private String title;
	private String content;
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public long getConsumeType()
	{
		return consumeType;
	}
	public void setConsumeType(long consumeType)
	{
		this.consumeType = consumeType;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	
}

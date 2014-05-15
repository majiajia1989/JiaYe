package com.e1858.wuye.pojo;

import java.util.Date;

public class CommissionCommand
{

	private long id;

	private String creator;

	private String content;

	private Date createTime;
	
	private boolean responsed = false;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public boolean isResponsed() {
		return responsed;
	}

	public void setResponsed(boolean responsed) {
		this.responsed = responsed;
	}


}

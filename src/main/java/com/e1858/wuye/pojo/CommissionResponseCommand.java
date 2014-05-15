package com.e1858.wuye.pojo;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.e1858.wuye.common.CommonConstant;

public class CommissionResponseCommand
{
	private long commissionID ;
	
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotEmpty)
	private String content;
	
	private String commissionContent;
	private String commissionCreator;
	private Date replyTime;
	
	private boolean fromReply=false;
	
	private String creator;
	
	public long getCommissionID()
	{
		return commissionID;
	}
	public void setCommissionID(long commissionID)
	{
		this.commissionID = commissionID;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getCommissionContent()
	{
		return commissionContent;
	}
	public void setCommissionContent(String commissionContent)
	{
		this.commissionContent = commissionContent;
	}
	public String getCommissionCreator()
	{
		return commissionCreator;
	}
	public void setCommissionCreator(String commissionCreator)
	{
		this.commissionCreator = commissionCreator;
	}

	public Date getReplyTime()
	{
		return replyTime;
	}
	public void setReplyTime(Date replyTime)
	{
		this.replyTime = replyTime;
	}
	public String getCreator()
	{
		return creator;
	}
	public void setCreator(String creator)
	{
		this.creator = creator;
	}
	public boolean isFromReply()
	{
		return fromReply;
	}
	public void setFromReply(boolean fromReply)
	{
		this.fromReply = fromReply;
	}

	
	
}

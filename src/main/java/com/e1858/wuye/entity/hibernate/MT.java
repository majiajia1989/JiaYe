package com.e1858.wuye.entity.hibernate;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "t_MT")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MT extends BaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name="mo")
	private long mo;
	
	@Column(name="msg")
	private long msg;
	
	@Column(name="toUserName",length=32)
	private String toUserName;
	
	@Column(name="fromUserName",length=32)
	private String fromUserName;
	
	@Column(name="msgType")
	private String msgType;
	
	@Column(name="title",length=1000)
	private String title;
	
	@Column(name="description",length=1000)
	private String description;
	
	@Column(name="picUrl",length=1000)
	private String picUrl;
	
	@Column(name="content",length=18000)
	private String content;
	
	@Column(name="children",length=200)
	private String children;
	
	@Column(name="priority")
	private byte priority;
	
	@Column(name="sendTime")
	private Date sendTime;
	
	@Column(name="errCode")
	private int errCode;
	
	@Column(name="errMsg",length=100)
	private String errMsg;
	
	@Column(name="performTime")
	private Date performTime;
	
	@Column(name="presentTime")
	private Date presentTime;
	
	@Column(name="presenter")
	private long presenter;
	
	@Column(name="auditTime")
	private Date auditTime;
	
	@Column(name="auditor")
	private long auditor;

	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getMo()
	{
		return mo;
	}

	public void setMo(long mo)
	{
		this.mo = mo;
	}

	public long getMsg()
	{
		return msg;
	}

	public void setMsg(long msg)
	{
		this.msg = msg;
	}

	public String getToUserName()
	{
		return toUserName;
	}

	public void setToUserName(String toUserName)
	{
		this.toUserName = toUserName;
	}

	public String getFromUserName()
	{
		return fromUserName;
	}

	public void setFromUserName(String fromUserName)
	{
		this.fromUserName = fromUserName;
	}

	

	public String getMsgType()
	{
		return msgType;
	}

	public void setMsgType(String msgType)
	{
		this.msgType = msgType;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getPicUrl()
	{
		return picUrl;
	}

	public void setPicUrl(String picUrl)
	{
		this.picUrl = picUrl;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getChildren()
	{
		return children;
	}

	public void setChildren(String children)
	{
		this.children = children;
	}

	public byte getPriority()
	{
		return priority;
	}

	public void setPriority(byte priority)
	{
		this.priority = priority;
	}

	public Date getSendTime()
	{
		return sendTime;
	}

	public void setSendTime(Date sendTime)
	{
		this.sendTime = sendTime;
	}

	public Date getPerformTime()
	{
		return performTime;
	}

	public void setPerformTime(Date performTime)
	{
		this.performTime = performTime;
	}

	public Date getAuditTime()
	{
		return auditTime;
	}

	public void setAuditTime(Date auditTime)
	{
		this.auditTime = auditTime;
	}

	public long getAuditor()
	{
		return auditor;
	}

	public void setAuditor(long auditor)
	{
		this.auditor = auditor;
	}

	public Date getPresentTime()
	{
		return presentTime;
	}

	public void setPresentTime(Date presentTime)
	{
		this.presentTime = presentTime;
	}


	public long getPresenter()
	{
		return presenter;
	}

	public void setPresenter(long presenter)
	{
		this.presenter = presenter;
	}

	public int getErrCode()
	{
		return errCode;
	}

	public void setErrCode(int errCode)
	{
		this.errCode = errCode;
	}

	public String getErrMsg()
	{
		return errMsg;
	}

	public void setErrMsg(String errMsg)
	{
		this.errMsg = errMsg;
	}	
}

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
@Table(name = "t_ConsumeTemplate")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConsumeTemplate extends BaseEntity
{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "corp")
	private long corp;
	
	@Column(name = "community")
	private long community;

	@Column(name = "consumeType")
	private long consumeType;

	@Column(name = "title",length=100)
	private String title;

	@Column(name="content" ,length=1000)
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "creator")
	private SysUser creator;
	
	@Column(name="createTime")
	private Date createTime;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}
	
	public long getCorp()
	{
		return corp;
	}

	public void setCorp(long corp)
	{
		this.corp = corp;
	}

	public long getCommunity()
	{
		return community;
	}

	public void setCommunity(long community)
	{
		this.community = community;
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

	public SysUser getCreator()
	{
		return creator;
	}

	public void setCreator(SysUser creator)
	{
		this.creator = creator;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	
}

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
@Table(name = "t_SysRoleResource")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysRoleResource extends BaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "role")
	private SysRole sysRole;
	
	@ManyToOne
	@JoinColumn(name = "resource")
	private SysResource sysResource;

	@ManyToOne
	@JoinColumn(name = "creator")
	private SysUser creator;
	@Column(name = "createTime")
	private Date createTime;
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public SysRole getSysRole()
	{
		return sysRole;
	}

	public void setSysRole(SysRole sysRole)
	{
		this.sysRole = sysRole;
	}

	public SysResource getSysResource()
	{
		return sysResource;
	}

	public void setSysResource(SysResource sysResource)
	{
		this.sysResource = sysResource;
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

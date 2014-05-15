package com.e1858.wuye.entity.hibernate;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "t_BbsTop")
public class BbsTop extends BaseEntity
{

	private Long id;
	private BbsTopic bbsTopic;
	private SysUser sysUser;
	private SysCorp sysCorp;
	private BbsBoard bbsBoard;
	private Community community;
	private Date createTime;

	public BbsTop()
	{
	}

	public BbsTop(BbsTopic bbsTopic, SysUser sysUser, SysCorp sysCorp, BbsBoard bbsBoard, Community community, Date createTime)
	{
		this.bbsTopic = bbsTopic;
		this.sysUser = sysUser;
		this.sysCorp = sysCorp;
		this.bbsBoard = bbsBoard;
		this.community = community;
		this.createTime = createTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "topic", nullable = false)
	public BbsTopic getBbsTopic()
	{
		return this.bbsTopic;
	}

	public void setBbsTopic(BbsTopic bbsTopic)
	{
		this.bbsTopic = bbsTopic;
	}

	@ManyToOne
	@JoinColumn(name = "creator", nullable = false)
	public SysUser getSysUser()
	{
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser)
	{
		this.sysUser = sysUser;
	}

	@ManyToOne
	@JoinColumn(name = "corp", nullable = false)
	public SysCorp getSysCorp()
	{
		return this.sysCorp;
	}

	public void setSysCorp(SysCorp sysCorp)
	{
		this.sysCorp = sysCorp;
	}

	@ManyToOne
	@JoinColumn(name = "board", nullable = false)
	public BbsBoard getBbsBoard()
	{
		return this.bbsBoard;
	}

	public void setBbsBoard(BbsBoard bbsBoard)
	{
		this.bbsBoard = bbsBoard;
	}

	@ManyToOne
	@JoinColumn(name = "community", nullable = false)
	public Community getCommunity()
	{
		return this.community;
	}

	public void setCommunity(Community community)
	{
		this.community = community;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createTime", nullable = false, length = 10)
	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

}

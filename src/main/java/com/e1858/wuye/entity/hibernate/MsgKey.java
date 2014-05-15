package com.e1858.wuye.entity.hibernate;

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
@Cacheable
@Table(name = "t_MsgKey")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MsgKey extends BaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "msg")
	private Msg msg;

	@Column(name = "command", length = 25)
	private String command;

	@Column(name = "matching")
	private byte matching;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public Msg getMsg()
	{
		return msg;
	}

	public void setMsg(Msg msg)
	{
		this.msg = msg;
	}

	public String getCommand()
	{
		return command;
	}

	public void setCommand(String command)
	{
		this.command = command;
	}

	public byte getMatching()
	{
		return matching;
	}

	public void setMatching(byte matching)
	{
		this.matching = matching;
	}

}

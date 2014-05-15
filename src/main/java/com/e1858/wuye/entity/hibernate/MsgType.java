package com.e1858.wuye.entity.hibernate;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "t_MsgType")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class MsgType extends BaseEntity
{
	@Id
	@Column(name = "type", length = 50)
	private String type;

	@Column(name = "name", length = 50)
	private String name;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}

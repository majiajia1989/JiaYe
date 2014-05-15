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
@Table(name = "t_SysResource")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysResource extends BaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "parent")
	private long parent;
	@Column(name = "name", length = 50)
	private String name;
	@Column(name = "url", length = 100)
	private String url;
	@Column(name = "icon", length = 50)
	private String icon;
	@Column(name = "image", length = 50)
	private String image;
	@Column(name = "direction")
	private byte direction;
	@Column(name = "sort")
	private byte sort;

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

	public long getParent()
	{
		return parent;
	}

	public void setParent(long parentID)
	{
		this.parent = parentID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}
	
	

	public byte getDirection()
	{
		return direction;
	}

	public void setDirection(byte direction)
	{
		this.direction = direction;
	}

	public byte getSort()
	{
		return sort;
	}

	public void setSort(byte sort)
	{
		this.sort = sort;
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

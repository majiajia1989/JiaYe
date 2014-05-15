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
@Table(name = "t_Community")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Community extends BaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "corp")
	private SysCorp corp;
	
	@Column(name = "province",length=50)
	private String province;
	
	@Column(name = "city",length=50)
	private String city;

	@Column(name = "county",length=50)
	private String county;

	@Column(name = "name",length=100)
	private String name;
	
	@Column(name = "mobilePhone",length=11)
	private String mobilePhone;
	
	@Column(name = "telePhone",length=12)
	private String telePhone;
	
	@Column(name = "descript",length=200)
	private String descript;

	@Column(name = "address",length=100)
	private String address;
	
	@Column(name = "copyright",length=100)
	private String copyright;

	@Column(name = "map",length=1000)
	private String map;

	@Column(name = "ticket",length=200)
	private String ticket;

	@Column(name = "qrcode",length=200)
	private String qrcode;

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

	public SysCorp getCorp()
	{
		return corp;
	}

	public void setCorp(SysCorp corp)
	{
		this.corp = corp;
	}



	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCounty()
	{
		return county;
	}

	public void setCounty(String couty)
	{
		this.county = couty;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public String getTelePhone()
	{
		return telePhone;
	}

	public void setTelePhone(String telePhone)
	{
		this.telePhone = telePhone;
	}

	public String getDescript()
	{
		return descript;
	}

	public void setDescript(String descript)
	{
		this.descript = descript;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCopyright()
	{
		return copyright;
	}

	public void setCopyright(String copyright)
	{
		this.copyright = copyright;
	}

	public String getMap()
	{
		return map;
	}

	public void setMap(String map)
	{
		this.map = map;
	}
	
	public String getTicket()
	{
		return ticket;
	}

	public void setTicket(String ticket)
	{
		this.ticket = ticket;
	}

	public String getQrcode()
	{
		return qrcode;
	}

	public void setQrcode(String qrcode)
	{
		this.qrcode = qrcode;
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

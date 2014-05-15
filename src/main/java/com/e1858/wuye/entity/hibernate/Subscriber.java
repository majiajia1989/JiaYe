package com.e1858.wuye.entity.hibernate;

// Generated Mar 20, 2014 1:16:44 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "t_Subscriber")
public class Subscriber extends BaseEntity
{

	private String openid;
	private byte type;
	private String nickname;
	private String headimgurl;
	private SysCorp corp;
	private Community community;
	private Date enrollTime;
	private Date cancelTime;
	private String name;
	private String alias;
	private String gender;
	private String telePhone;
	private String mobilePhone;
	private String carNumber;
	private int integrate;

	public Subscriber()
	{
	}

	public Subscriber(String openid, SysCorp sysCorp, Community community, Date enrollTime)
	{
		this.openid = openid;
		this.corp = sysCorp;
		this.community = community;
		this.enrollTime = enrollTime;
	}


	@Id
	@Column(name = "openid", unique = true, nullable = false, length = 32)
	public String getOpenid()
	{
		return this.openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid;
	}
	
	@Column(name = "type", nullable = false)
	public byte getType()
	{
		return type;
	}

	public void setType(byte type)
	{
		this.type = type;
	}
	
	
	@Column(name = "nickname", nullable = false, length = 50)
	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	@Column(name = "headimgurl", nullable = false, length = 200)
	public String getHeadimgurl()
	{
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl)
	{
		this.headimgurl = headimgurl;
	}

	@ManyToOne
	@JoinColumn(name = "corp", nullable = false)
	public SysCorp getCorp()
	{
		return this.corp;
	}

	public void setCorp(SysCorp sysCorp)
	{
		this.corp = sysCorp;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "enrollTime", nullable = false, length = 19)
	public Date getEnrollTime()
	{
		return this.enrollTime;
	}

	public void setEnrollTime(Date enrollTime)
	{
		this.enrollTime = enrollTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cancelTime", length = 19)
	public Date getCancelTime()
	{
		return this.cancelTime;
	}

	public void setCancelTime(Date cancelTime)
	{
		this.cancelTime = cancelTime;
	}
	
	@Column(name="name",length=100)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}


	@Column(name="alias",length=100)
	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	@Column(name="gender",length=2)
	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	@Column(name="telePhone",length=12)
	public String getTelePhone()
	{
		return telePhone;
	}

	public void setTelePhone(String telePhone)
	{
		this.telePhone = telePhone;
	}


	@Column(name="mobilePhone",length=11)
	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}
	@Column(name="carNumber",length=7)
	public String getCarNumber()
	{
		return carNumber;
	}

	public void setCarNumber(String carNumber)
	{
		this.carNumber = carNumber;
	}

	@Column(name="integrate")
	public int getIntegrate()
	{
		return integrate;
	}

	public void setIntegrate(int integrate)
	{
		this.integrate = integrate;
	}
	
	
	
}

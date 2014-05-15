package com.e1858.wuye.entity.hibernate;


import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "t_Weixin_UserInfo")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Weixin_UserInfo extends BaseEntity
{
	@Id
	@Column(name = "openid", unique = true, nullable = false, length = 32)
	private String openid;
	@Column(name = "subscribe", nullable = false)
	private byte subscribe;
	@Column(name = "nickname", nullable = false, length = 50)
	private String nickname;
	@Column(name = "sex", nullable = false)
	private byte sex;
	@Column(name = "province", nullable = false, length = 50)
	private String province;
	@Column(name = "city", nullable = false, length = 20)
	private String city;
	@Column(name = "country", nullable = false, length = 20)
	private String country;
	@Column(name = "headimgurl", nullable = false, length = 200)
	private String headimgurl;
	@Column(name = "privilege", length = 200)
	private String privilege;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "subscribe_time", length = 19)
	private Date subscribeTime;

	public Weixin_UserInfo()
	{
	}

	public String getOpenid()
	{
		return this.openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid;
	}


	public byte getSubscribe()
	{
		return this.subscribe;
	}

	public void setSubscribe(byte subscribe)
	{
		this.subscribe = subscribe;
	}


	public String getNickname()
	{
		return this.nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}


	public byte getSex()
	{
		return this.sex;
	}

	public void setSex(byte sex)
	{
		this.sex = sex;
	}


	public String getProvince()
	{
		return this.province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}


	public String getCity()
	{
		return this.city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}


	public String getCountry()
	{
		return this.country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}


	public String getHeadimgurl()
	{
		return this.headimgurl;
	}

	public void setHeadimgurl(String headimgurl)
	{
		this.headimgurl = headimgurl;
	}


	public String getPrivilege()
	{
		return this.privilege;
	}

	public void setPrivilege(String privilege)
	{
		this.privilege = privilege;
	}


	public Date getSubscribeTime()
	{
		return this.subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime)
	{
		this.subscribeTime = subscribeTime;
	}

}

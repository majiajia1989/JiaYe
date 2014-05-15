package com.e1858.wuye.entity.hibernate;

// Generated Apr 1, 2014 4:17:41 PM by Hibernate Tools 3.4.0.CR1

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
@Table(name = "t_Weixin_GongHao")
public class Weixin_GongHao implements java.io.Serializable
{

	private Long id;
	private long corp;
	private long community;
	private String account;
	private String password;
	private String url;
	private String token;
	private String appId;
	private String appSecret;
	private String openid;
	private long creator;
	private Date createTime;

	public Weixin_GongHao()
	{
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

	@Column(name = "creator", nullable = false)
	public long getCreator()
	{
		return this.creator;
	}

	public void setCreator(long creator)
	{
		this.creator = creator;
	}

	@Column(name = "corp", nullable = false)
	public long getCorp()
	{
		return this.corp;
	}

	public void setCorp(long corp)
	{
		this.corp = corp;
	}

	@Column(name = "community", nullable = false)
	public long getCommunity()
	{
		return this.community;
	}

	public void setCommunity(long community)
	{
		this.community = community;
	}

	@Column(name = "account", nullable = false, length = 100)
	public String getAccount()
	{
		return this.account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	@Column(name = "password", nullable = false, length = 100)
	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Column(name = "url", nullable = false, length = 50)
	public String getUrl()
	{
		return this.url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@Column(name = "token", nullable = false, length = 100)
	public String getToken()
	{
		return this.token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	@Column(name = "appId", nullable = false, length = 200)
	public String getAppId()
	{
		return this.appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	@Column(name = "appSecret", nullable = false, length = 50)
	public String getAppSecret()
	{
		return this.appSecret;
	}

	public void setAppSecret(String appSecret)
	{
		this.appSecret = appSecret;
	}

	@Column(name = "openid", nullable = false, length = 32)
	public String getOpenid()
	{
		return this.openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

}

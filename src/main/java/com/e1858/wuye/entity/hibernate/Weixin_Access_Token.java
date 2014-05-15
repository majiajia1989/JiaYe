package com.e1858.wuye.entity.hibernate;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "t_Weixin_Access_Token")
public class Weixin_Access_Token implements java.io.Serializable
{

	private long corp;
	private String accessToken;
	private Date accessTime;
	private long expiresIn;
	private long createTime;
	private long expires;

	public Weixin_Access_Token()
	{
	}

	@GenericGenerator(name = "generator", strategy = "assigned")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "corp", unique = true, nullable = false)
	public long getCorp()
	{
		return this.corp;
	}

	public void setCorp(long corp)
	{
		this.corp = corp;
	}


	@Column(name = "access_token", nullable = false, length = 200)
	public String getAccessToken()
	{
		return this.accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "access_time", nullable = false, length = 19)
	public Date getAccessTime()
	{
		return this.accessTime;
	}

	public void setAccessTime(Date accessTime)
	{
		this.accessTime = accessTime;
	}

	@Column(name = "expires_in", nullable = false)
	public long getExpiresIn()
	{
		return this.expiresIn;
	}

	public void setExpiresIn(long expiresIn)
	{
		this.expiresIn = expiresIn;
	}

	@Column(name = "createTime", nullable = false)
	public long getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(long createTime)
	{
		this.createTime = createTime;
	}

	@Column(name = "expires", nullable = false)
	public long getExpires()
	{
		return this.expires;
	}

	public void setExpires(long expires)
	{
		this.expires = expires;
	}

}

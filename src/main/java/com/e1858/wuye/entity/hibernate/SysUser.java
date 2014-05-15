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
@Table(name = "t_SysUser")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysUser extends BaseEntity
{
	/**
	 * 锁定用户对应的状态值
	 */
    public static final byte USER_LOCK = 1;
    /**
	 * 用户解锁对应的状态值
	 */
    public static final byte USER_UNLOCK = 0;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 主键自增，注意，这种方式依赖于具体的数据库，如果数据库不支持自增主键，那么这个类型是没法用的
	@Column(name = "id", nullable = false)  
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "corp")
	private SysCorp corp;
	
	@Column(name = "name", length = 50) 
	private String name;
	@Column(name = "password", length = 32) 
	private String password;
	@Column(name = "email", length = 100)
	private String email;
	@Column(name = "address", length = 100)
	private String address;
	@Column(name = "mobilePhone", length = 11)
	private String mobilePhone;
	@Column(name = "qq", length = 20)
	private String qq;
	
	@Column(name = "active") 
	private byte active;
	@Column(name = "locked") 
	private byte locked;
	@Column(name = "last_visit")
	private Date lastVisit;
    @Column(name = "last_ip", length = 32) 
	private String lastIp;

	@Column(name = "begin")
	private Date begin;
	@Column(name = "end")
	private Date end;

	@Column(name = "creator")
	private long creator;
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
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	
	public byte getActive()
	{
		return active;
	}
	public void setActive(byte active)
	{
		this.active = active;
	}
	public byte getLocked()
	{
		return locked;
	}
	public void setLocked(byte status)
	{
		this.locked = status;
	}
	public String getEmail()
	{
		return email;
	}
	public void setCredit(String email)
	{
		this.email = email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getMobilePhone()
	{
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}
	public String getQq()
	{
		return qq;
	}
	public void setQq(String qq)
	{
		this.qq = qq;
	}
	public Date getLastVisit()
	{
		return lastVisit;
	}
	public void setLastVisit(Date lastVisit)
	{
		this.lastVisit = lastVisit;
	}
	public String getLastIp()
	{
		return lastIp;
	}
	public void setLastIp(String lastIp)
	{
		this.lastIp = lastIp;
	}

	public Date getBegin()
	{
		return begin;
	}

	public void setBegin(Date begin)
	{
		this.begin = begin;
	}

	public Date getEnd()
	{
		return end;
	}

	public void setEnd(Date end)
	{
		this.end = end;
	}
	public long getCreator()
	{
		return creator;
	}
	public void setCreator(long creator)
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
	public static byte getUserLock()
	{
		return USER_LOCK;
	}
	public static byte getUserUnlock()
	{
		return USER_UNLOCK;
	}
	
}

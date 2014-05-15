package com.e1858.wuye.entity.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "t_SysLoginLog")
public class SysLoginLog extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "login_datetime")
	private Date loginDate;
	
	@ManyToOne
	@JoinColumn(name = "user")
    private SysUser sysUser;
	
	@Column(name = "ip", length = 32) 
	private String ip;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public SysUser getSysUser()
	{
		return sysUser;
	}
	public void setSysUser(SysUser sysUser)
	{
		this.sysUser = sysUser;
	}


}

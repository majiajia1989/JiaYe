package com.e1858.wuye.pojo;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.e1858.wuye.common.CommonConstant;


public class UserCommand {

	private long user_id =-1;
	private long role_id=-1;
	private long corp_id=-1;
	private long type=2;
	private String corpName;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	private String name;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	private String password;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	private String confirmPass;
	@NotBlank(message= CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message= CommonConstant.ValidMessage.NotBlank)   
	@Email(message=CommonConstant.ValidMessage.EMAIL)  
	private String email;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	private String address;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	@Pattern(regexp="^1[3|4|5|8][0-9]\\d{8}$",message=CommonConstant.ValidMessage.MobilePhone)
	private String mobilePhone;
	private String qq;
	public long getUser_id()
	{
		return user_id;
	}
	public void setUser_id(long user_id)
	{
		this.user_id = user_id;
	}
	public long getCorp_id()
	{
		return corp_id;
	}
	public void setCorp_id(long corp_id)
	{
		this.corp_id = corp_id;
	}
	
	public long getRole_id()
	{
		return role_id;
	}
	public void setRole_id(long role_id)
	{
		this.role_id = role_id;
	}
	public String getCorpName()
	{
		return corpName;
	}
	public void setCorpName(String corpName)
	{
		this.corpName = corpName;
	}
	public long getType()
	{
		return type;
	}
	public void setType(long type)
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
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getConfirmPass()
	{
		return confirmPass;
	}
	public void setConfirmPass(String confirmPass)
	{
		this.confirmPass = confirmPass;
	}
	public String getEmail()
	{
		return email;
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
	public void setQq(String user_qq)
	{
		this.qq = user_qq;
	}

	
}

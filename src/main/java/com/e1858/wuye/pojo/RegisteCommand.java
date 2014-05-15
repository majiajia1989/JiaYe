package com.e1858.wuye.pojo;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.e1858.wuye.common.CommonConstant;

public class RegisteCommand
{
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	private String userName;
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	private String password;
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	private String password1;
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	@Email(message = CommonConstant.ValidMessage.EMAIL)
	private String email;
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	private String address;
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	@Pattern(regexp = "1[3456789]\\d{9}", message = CommonConstant.ValidMessage.MobilePhone)
	private String mobilePhone;
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	@Pattern(regexp = "\\d{4,20}", message = CommonConstant.ValidMessage.QQ)
	private String qq;
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	private String captcha;

	public String getCaptcha()
	{
		return captcha;
	}

	public void setCaptcha(String captcha)
	{
		this.captcha = captcha;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public String getPassword1()
	{
		return password1;
	}

	public void setPassword1(String password1)
	{
		this.password1 = password1;
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

}

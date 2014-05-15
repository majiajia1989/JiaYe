package com.e1858.wuye.pojo;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Email;

import com.e1858.wuye.common.CommonConstant;

public class ForgetCommand
{
	@NotEmpty(message= CommonConstant.ValidMessage.NotBlank)
	@NotBlank(message= CommonConstant.ValidMessage.NotBlank)
	@Email(message= CommonConstant.ValidMessage.EMAIL)
	private String email;

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}	
}

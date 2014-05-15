package com.e1858.wuye.pojo;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import com.e1858.wuye.common.CommonConstant;

public class BoardCommand
{
	@NotBlank(message= CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message= CommonConstant.ValidMessage.NotBlank)
	@Length(min=3,max=50) 
	private String name;
	@NotBlank(message= CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message= CommonConstant.ValidMessage.NotBlank)
	@Length(min=3,max=100) 
	private String description;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
}

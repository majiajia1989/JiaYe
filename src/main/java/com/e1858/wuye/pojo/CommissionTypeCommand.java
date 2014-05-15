package com.e1858.wuye.pojo;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.e1858.wuye.common.CommonConstant;

public class CommissionTypeCommand
{
	private long id;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotEmpty)
	private String name;
	private long template_count;
	private boolean isCommissionuse;

	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public long getTemplate_count()
	{
		return template_count;
	}
	public void setTemplate_count(long template_count)
	{
		this.template_count = template_count;
	}
	public boolean isCommissionuse()
	{
		return isCommissionuse;
	}
	public void setCommissionuse(boolean isCommissionuse)
	{
		this.isCommissionuse = isCommissionuse;
	}
	
}

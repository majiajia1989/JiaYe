package com.e1858.wuye.pojo;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.e1858.wuye.common.CommonConstant;

public class SendWeixinCommand
{
	private String openid;
	
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotEmpty)
	private String content;

	

	public String getOpenid()
	{
		return openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
	
	
}

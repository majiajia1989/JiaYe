package com.e1858.wuye.pojo;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Email;

import com.e1858.wuye.common.CommonConstant;
public class ValidUser
{
	@NotBlank(message= CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message= CommonConstant.ValidMessage.NotBlank)  
    private String name;  
  
	@NotBlank(message= CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message= CommonConstant.ValidMessage.NotBlank)   
	@Email(message=CommonConstant.ValidMessage.EMAIL)  
    private String email;
	
	@NotBlank(message= CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message= CommonConstant.ValidMessage.NotBlank)  
	@Pattern(regexp="1[3456789]\\d{9}",message=CommonConstant.ValidMessage.MobilePhone)  
    private String mobilePhone;
	
	private String remark;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}


	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	} 
 

}

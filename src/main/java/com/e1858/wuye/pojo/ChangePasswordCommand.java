package com.e1858.wuye.pojo;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.e1858.wuye.common.CommonConstant;

public class ChangePasswordCommand
{
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	private String oldPassword;
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	private String newPassword;
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	private String	confirmPassword;

	public String getOldPassword()
	{
		return oldPassword;
	}

	public void setOldPassword(String oldPassword)
	{
		this.oldPassword = oldPassword;
	}

	public String getNewPassword()
	{
		return newPassword;
	}

	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}

package com.e1858.wuye.pojo;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import com.e1858.wuye.common.CommonConstant;
public class RoleCommand {
	private long role_id;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotEmpty)
	private String name;
	private String roleResources;
	public long getRole_id()
	{
		return role_id;
	}
	public void setRole_id(long role_id)
	{
		this.role_id = role_id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getRoleResources()
	{
		return roleResources;
	}
	public void setRoleResources(String roleResources)
	{
		this.roleResources = roleResources;
	}
	
}

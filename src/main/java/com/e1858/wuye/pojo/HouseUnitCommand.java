package com.e1858.wuye.pojo;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import com.e1858.wuye.common.CommonConstant;

public class HouseUnitCommand
{
	private long houseUnitId;
	private long corpId;
	private long communityId;
	private String name;
	private String description;
	public long getHouseUnitId()
	{
		return houseUnitId;
	}
	public void setHouseUnitId(long houseUnitId)
	{
		this.houseUnitId = houseUnitId;
	}

	public long getCorpId()
	{
		return corpId;
	}
	public void setCorpId(long corpId)
	{
		this.corpId = corpId;
	}
	public long getCommunityId()
	{
		return communityId;
	}
	public void setCommunityId(long communityId)
	{
		this.communityId = communityId;
	}
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

package com.e1858.wuye.pojo;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import com.e1858.wuye.common.CommonConstant;

public class SaveHouseCommand
{
	private long corpId=-1;
	private long communityId=-1;
	private String hoses;
	
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
	public void setCommunityId(long commuintyId)
	{
		this.communityId = commuintyId;
	}
	public String getHoses()
	{
		return hoses;
	}
	public void setHoses(String hoses)
	{
		this.hoses = hoses;
	}
	
	
}

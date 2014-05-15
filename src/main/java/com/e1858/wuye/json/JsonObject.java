package com.e1858.wuye.json;

public class JsonObject
{
	private int errCode;
	private String errMsg="中文信息";
	public int getErrCode()
	{
		return errCode;
	}
	public void setErrCode(int errCode)
	{
		this.errCode = errCode;
	}
	public String getErrMsg()
	{
		return errMsg;
	}
	public void setErrMsg(String errMsg)
	{
		this.errMsg = errMsg;
	}
	
}

package com.e1858.wuye.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class XmlObject
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

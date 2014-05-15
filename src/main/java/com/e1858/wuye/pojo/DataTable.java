package com.e1858.wuye.pojo;

import java.util.ArrayList;
import java.util.List;
public class DataTable<T>
{
	private String sEcho;
	private long iTotalDisplayRecords;
	private long iTotalRecords;
    private List<T> aaData = new ArrayList<T>();
	public String getsEcho()
	{
		return sEcho;
	}
	public void setsEcho(String sEcho)
	{
		this.sEcho = sEcho;
	}
	public long getiTotalDisplayRecords()
	{
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(long iTotalDisplayRecords)
	{
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public long getiTotalRecords()
	{
		return iTotalRecords;
	}
	public void setiTotalRecords(long iTotalRecords)
	{
		this.iTotalRecords = iTotalRecords;
	}
	public List<T> getAaData()
	{
		return aaData;
	}
	public void setAaData(List<T> aaData)
	{
		this.aaData = aaData;
	}
	public class RowCnt
	{
		private long rowCnt=0;

		public long getRowCnt()
		{
			return rowCnt;
		}

		public void setRowCnt(long rowCnt)
		{
			this.rowCnt = rowCnt;
		}	
	}
}

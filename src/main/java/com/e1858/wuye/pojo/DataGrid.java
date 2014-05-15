package com.e1858.wuye.pojo;
import java.util.ArrayList;
public class DataGrid<T>
{
	private long total;
    private ArrayList<T> rows = new ArrayList<T>();
	public long getTotal()
	{
		return total;
	}
	public void setTotal(long total)
	{
		this.total = total;
	}
	public ArrayList<T> getRows()
	{
		return rows;
	}
	public void setRows(ArrayList<T> rows)
	{
		this.rows = rows;
	}

	
}

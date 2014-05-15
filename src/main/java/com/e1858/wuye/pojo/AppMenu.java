package com.e1858.wuye.pojo;

import java.util.ArrayList;

public class AppMenu
{

	private long id;
	private long parent_id;
	private String name;
	private String url;
	private String icon;
	private String image;
	private byte direction;
	private ArrayList<AppMenu> children = new ArrayList<AppMenu>();
	private boolean parent;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getParent_id()
	{
		return parent_id;
	}

	public void setParent_id(long parent_id)
	{
		this.parent_id = parent_id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public ArrayList<AppMenu> getChildren()
	{
		return children;
	}

	public void setChildren(ArrayList<AppMenu> children)
	{
		this.children = children;
	}

	public byte isDirection()
	{
		return direction;
	}

	public void setDirection(byte direction)
	{
		this.direction = direction;
	}

	public boolean isParent()
	{
		return parent;
	}
	
	
}

package com.e1858.wuye.pojo;

import java.util.ArrayList;


public class TreeNode
{
    private String id;
    private String text;
    private String iconCls;
    private boolean checked;
    private ArrayList<TreeNode> children = new ArrayList<TreeNode>();
    private StateClass state = new StateClass();
    
	public class StateClass {
		private boolean  opened = false;
		private boolean disabled=false;
		private boolean selected=false;
		public boolean isOpened()
		{
			return opened;
		}
		public void setOpened(boolean opened)
		{
			this.opened = opened;
		}
		public boolean isDisabled()
		{
			return disabled;
		}
		public void setDisabled(boolean disabled)
		{
			this.disabled = disabled;
		}
		public boolean isSelected()
		{
			return selected;
		}
		public void setSelected(boolean selected)
		{
			this.selected = selected;
		}
		
	}

	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public String getIconCls()
	{
		return iconCls;
	}
	public void setIconCls(String iconCls)
	{
		this.iconCls = iconCls;
	}
	public boolean isChecked()
	{
		return checked;
	}
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	public ArrayList<TreeNode> getChildren()
	{
		return children;
	}
	public void setChildren(ArrayList<TreeNode> children)
	{
		this.children = children;
	}
	public StateClass getState()
	{
		return state;
	}
	public void setState(StateClass state)
	{
		this.state = state;
	}

    
}
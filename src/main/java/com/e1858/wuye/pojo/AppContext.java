package com.e1858.wuye.pojo;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;

public class AppContext
{
	private SysUser user;
	private AppMenu menu_v;
	private AppMenu menu_h;
	private SysCorp corp;
	private Community community;
	public SysUser getUser()
	{
		return user;
	}
	public void setUser(SysUser user)
	{
		this.user = user;
	}
	public AppMenu getMenu_v()
	{
		return menu_v;
	}
	public void setMenu_v(AppMenu menu)
	{
		this.menu_v = menu;
	}
	public AppMenu getMenu_h()
	{
		return menu_h;
	}
	public void setMenu_h(AppMenu menu)
	{
		this.menu_h = menu;
	}
	public SysCorp getCorp() {
		return corp;
	}
	public void setCorp(SysCorp corp) {
		this.corp = corp;
	}
	public Community getCommunity() {
		return community;
	}
	public void setCommunity(Community community) {
		this.community = community;
	}	
	
}

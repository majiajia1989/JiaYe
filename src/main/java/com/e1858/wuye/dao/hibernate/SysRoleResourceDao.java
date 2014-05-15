package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysRole;
import com.e1858.wuye.entity.hibernate.SysRoleResource;

@Repository("sysRoleResourceDao")
public class SysRoleResourceDao extends BaseDao<SysRoleResource>
{
	private String QUERY_ROLERESOURCE_BY_ROLE = " from SysRoleResource u where u.sysRole=?";
	public int deleteRoleResourceByRole(SysRole sysRole)
	{
		List<Object> param = new ArrayList<Object>();
		param.add(sysRole.getId());
		return createSqlQuery("delete from t_SysRoleResource where role=?;",param).executeUpdate();// executeHql("delete t u where u.role=?", sysRole);
	}
	
	public List<SysRoleResource> querySysRoleResourceByRole(SysRole sysRole)
	{
		return (List<SysRoleResource>)find(QUERY_ROLERESOURCE_BY_ROLE,sysRole);
	}
}

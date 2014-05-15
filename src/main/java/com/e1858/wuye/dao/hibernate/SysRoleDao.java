package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysRole;
import com.e1858.wuye.entity.hibernate.SysUser;

@Repository("sysRoleDao")
public class SysRoleDao extends BaseDao<SysRole>
{
	private String QUERY_ROLE_BY_USER="select sr from SysRole as sr,SysRoleUser as sru where sr.id=sru.sysRole and sru.sysUser=?";
	private String QUERY_ROLE_BY_CORP="select sr from SysRole as sr where sr.corp=?";
	private String QUERY_ROLE_BY_CORP_AND_NAME="select sr from SysRole as sr where sr.corp=? and sr.name=?";
	/**
	 * 按用户查询该用户的角色
	 * @param SysUser 用户
	 * @return 用户名前缀匹配的所有SysRole对象
	 */
	public List<SysRole> queryRoleByUser(SysUser sysUser){
	    return (List<SysRole>)find(QUERY_ROLE_BY_USER,sysUser);
	}
	
	public List<SysRole> queryRoleByCorp(SysCorp sysCorp){
	    return (List<SysRole>)find(QUERY_ROLE_BY_CORP,sysCorp);
	}
	
	public SysRole queryRoleByCorpAndName(SysCorp sysCorp,String roleName){
		SysRole result=null;
		List<SysRole> sysRoles = (List<SysRole>)find(QUERY_ROLE_BY_CORP_AND_NAME,sysCorp,roleName);
		if(sysRoles.size()>0)
		{
			result = sysRoles.get(0);
		}
	    return result;
	}
	
	
	
}

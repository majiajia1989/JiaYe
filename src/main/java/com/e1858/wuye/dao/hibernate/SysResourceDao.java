package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.SysResource;
import com.e1858.wuye.entity.hibernate.SysRole;

@Repository("sysResourceDao")
public class SysResourceDao extends BaseDao<SysResource>
{
	private String QUERY_RESOURCE_BY_ROLE="select sr from SysResource as sr,SysRoleResource as srr where sr.id=srr.sysResource and srr.sysRole=?";
	/**
	 * 按角色查询该角色的资源对
	 * @param SysRole 角色
	 * @return 用户名前缀匹配的所有User对象
	 */
	public List<SysResource> queryResourceByRole(SysRole sysRole){
	    return (List<SysResource>)find(QUERY_RESOURCE_BY_ROLE,sysRole);
	}
}

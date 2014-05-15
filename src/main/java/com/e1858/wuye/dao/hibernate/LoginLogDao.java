package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.SysLoginLog;
import com.e1858.wuye.entity.hibernate.SysUser;

@Repository
public class LoginLogDao extends BaseDao<SysLoginLog> {
	private final String GET_LOGINLOG_BYUSER = HQL_FROM + " u where u.sysUser=? order by u.id desc";
	private final String GET_TOTALCOUNT = "select count(u.id) from SysLoginLog u where u.sysUser=?";
	@SuppressWarnings("unchecked")
	public List<SysLoginLog> queryLogs(SysUser user){
	    String hql=GET_LOGINLOG_BYUSER;
	    Query query=createQuery(hql, user);
		return query.list();
	}
	public List<SysLoginLog> queryLogs(SysUser user,int index,int count){
	    return findWithOffset(GET_LOGINLOG_BYUSER, index, count, user);
	}
	public long getTotalCount(SysUser user)
	{
		return count(GET_TOTALCOUNT, user);
	}
}



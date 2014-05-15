package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.ServicePhone;
import com.e1858.wuye.entity.hibernate.SysCorp;

@Repository("servicePhoneDao")
public class ServicePhoneDao extends BaseDao<ServicePhone>
{
	private final String GET_SERVICEPHONE_BY_CORPANDCOMMUNITY = HQL_FROM + " u where u.corp= ? and u.community=?";
	private final String GET_SERVICEPHONE_BY_ID = HQL_FROM + " u where u.id=?";
	private final String COUNT_SERVICEPHONE_BY_CORPANDCOMMUNITY = "select count(u.id) " + HQL_FROM + " u where u.corp= ? and u.community=?";

	public List<ServicePhone> getServicePhones(SysCorp corp, Community community,int startRow, int rows)
	{
		return findWithOffset(GET_SERVICEPHONE_BY_CORPANDCOMMUNITY,startRow,rows, corp, community);
	}

	public ServicePhone getServicePhone(long id)
	{
		return get(GET_SERVICEPHONE_BY_ID, id);
	}
	
	public long countServicePhone(SysCorp corp, Community community)
	{
		return count(COUNT_SERVICEPHONE_BY_CORPANDCOMMUNITY, corp,community);
	}		
	
}

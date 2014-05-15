package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.CommissionType;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.SysCorp;

@Repository("commissionTypeDao")
public class CommissionTypeDao extends BaseDao<CommissionType>
{

	private final String GET_COMMISSIONTYPE_BY_CORPANDCOMMUNITY = HQL_FROM + " u where u.community=? order by u.id desc";
	private final String GET_COMMISSIONTYPE_BY_CORPANDCOMMUNITYANDNAME=HQL_FROM+" u where u.corp=? and u.community=? and u.name=?";
	private final String GET_COMMISSIONTYPE_BY_ID=HQL_FROM+" u where u.id=?";
	private final String GET_TOTALCOUNT="select count(u.id) from CommissionType u where u.community=?";
	public List<CommissionType> getCommissionTypeByCorpAndCommunity(Community community,int index,int count)
	{
		return findWithOffset(GET_COMMISSIONTYPE_BY_CORPANDCOMMUNITY, index, count, community);
	}
	public List<CommissionType> getCommissionTypeByCorpAndCommunity(Community community)
	{
		return find(GET_COMMISSIONTYPE_BY_CORPANDCOMMUNITY,community);
	}
	public CommissionType getCommissionTypeByID(long id){
		return get(GET_COMMISSIONTYPE_BY_ID,id);
	}
	public CommissionType getCommissionTypeByName(SysCorp corp,Community community,String name){
		return get(GET_COMMISSIONTYPE_BY_CORPANDCOMMUNITYANDNAME,corp,community,name);
	}

	public long getTotalCount(Community community)
	{
		return count(GET_TOTALCOUNT, community);
	}
}

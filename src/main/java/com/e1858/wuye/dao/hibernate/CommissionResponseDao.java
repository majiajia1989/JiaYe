package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Commission;
import com.e1858.wuye.entity.hibernate.CommissionResponse;

@Repository("commissionResponseDao")
public class CommissionResponseDao extends BaseDao<CommissionResponse>
{
	private final String QUERY_BY_COMMISSION = HQL_FROM + " u where u.commission=?";
	private final String GET_TOTALCOUNT="select count(u.id) from CommissionResponse u where u.commission=?";
	
	public List<CommissionResponse> queryResponsesByCommission(Commission commission,int index,int count)
	{
		return findWithOffset(QUERY_BY_COMMISSION, index, count,commission);
	}

	public long getTotalCount(Commission commission)
	{
		return count(GET_TOTALCOUNT,commission);
	}
}

package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.CommissionTemplate;
import com.e1858.wuye.entity.hibernate.CommissionType;

@Repository("commissionTemplate")
public class CommissionTemplateDao extends BaseDao<CommissionTemplate>
{

private final String GET_COMMISSIONTEMPLATE_BY_TYPE = HQL_FROM + " u where u.type=? order by u.id desc";
private final String GET_COMMISSIONTEMPLATE_BY_ID = HQL_FROM + " u where u.id=?";
private final String GET_TOTALCOUNT="select count(u.id) from CommissionTemplate u where u.type=?";
	public List<CommissionTemplate> getCommissionTemplateByType(CommissionType commissionType,int index,int count)
	{
		return findWithOffset(GET_COMMISSIONTEMPLATE_BY_TYPE,index,count,commissionType);
	}
	public CommissionTemplate getCommissionTemplateByID(long id){
		return get(GET_COMMISSIONTEMPLATE_BY_ID, id);
	}
	public long getTotalCount(CommissionType type)
	{
		return count(GET_TOTALCOUNT, type);
	}
}

package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.CommissionTemplate;
import com.e1858.wuye.entity.hibernate.CommissionType;
import com.e1858.wuye.entity.hibernate.ConsumeTemplate;
import com.e1858.wuye.entity.hibernate.SysConsumeType;

@Repository("consumeTemplate")
public class ConsumeTemplateDao extends BaseDao<ConsumeTemplate>
{

private final String GET_CONSUMETEMPLATE_BY_COMMUNITY_AND_TYPE = HQL_FROM + " u where u.community=? and u.consumeType=? order by u.id desc";
	
	public List<ConsumeTemplate> queryConsumeTemplateByType(long community, long consumeType)
	{
		return find(GET_CONSUMETEMPLATE_BY_COMMUNITY_AND_TYPE, community,consumeType);
	}
}

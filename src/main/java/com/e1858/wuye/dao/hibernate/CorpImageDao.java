package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.CorpImage;
import com.e1858.wuye.entity.hibernate.SysCorp;

@Repository("corpImageDao")
public class CorpImageDao extends BaseDao<CorpImage>
{
	private final String GET_CORPIMAGE_BYCORP = HQL_FROM + " u where u.corp=?";
	private final String GET_CORPIMAGE_BYID = HQL_FROM + " u where u.id=?";
	private final String COUNT_CORPIMAGE_BYCORP = "select count(u.id) " + HQL_FROM + " u where u.corp= ?";

	public List<CorpImage> getCorpImages(SysCorp corp,int startRow,int rows)
	{
		return findWithOffset(GET_CORPIMAGE_BYCORP,startRow,rows, corp);
	}

	public CorpImage getCorpImage(long id)
	{
		return get(GET_CORPIMAGE_BYID, id);
	}
	
	public long countCorpImageByCorp(SysCorp corp)
	{
		return count(COUNT_CORPIMAGE_BYCORP, corp);
	}	
}

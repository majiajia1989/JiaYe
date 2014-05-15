package com.e1858.wuye.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.CorpWelcomeImage;
import com.e1858.wuye.entity.hibernate.SysCorp;

@Repository("corpWelcomImageDao")
public class CorpWelcomeImageDao extends BaseDao<CorpWelcomeImage>
{
	private final String GET_CORPIMAGE_BYCORP = HQL_FROM + " u where u.corp=?";

	public CorpWelcomeImage getCorpWelcomeImages(SysCorp corp)
	{
		return get(GET_CORPIMAGE_BYCORP, corp);
	}

	
}

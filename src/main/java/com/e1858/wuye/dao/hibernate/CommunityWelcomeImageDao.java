package com.e1858.wuye.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.CommunityWelcomeImage;

@Repository("communityWelcomeImageDao")
public class CommunityWelcomeImageDao extends BaseDao<CommunityWelcomeImage>
{
	private final String GET_COMMUNITYWELCOMIMAGE = HQL_FROM + " u where u.community=?";

	public CommunityWelcomeImage getCommunityWelcomeImages(Community community)
	{
		return get(GET_COMMUNITYWELCOMIMAGE, community);
	}

	
}

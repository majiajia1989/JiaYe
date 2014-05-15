package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.CommunityImage;

@Repository("communityImageDao")
public class CommunityImageDao extends BaseDao<CommunityImage>
{
	private final String GET_COMMUNITYIMAGE_BYCOMMUNITY = HQL_FROM + " u where u.community= ? ";
	private final String GET_COMMUNITYIMAGE_BYID = HQL_FROM + " u where u.id=?";
	private final String COUNT_COMMUNITYIMAGE_BYCOMMUNITY = "select count(u.id) " + HQL_FROM + " u where u.community= ?";

	public List<CommunityImage> getCommunityImages(Community community,int startRow,int rows)
	{
		return findWithOffset(GET_COMMUNITYIMAGE_BYCOMMUNITY,startRow,rows,community);
	}

	public CommunityImage getCommunityImage(long id)
	{
		return get(GET_COMMUNITYIMAGE_BYID, id);
	}
	
	public long countCommunityImageByCommunity(Community community)
	{
		return count(COUNT_COMMUNITYIMAGE_BYCOMMUNITY, community);
	}
}

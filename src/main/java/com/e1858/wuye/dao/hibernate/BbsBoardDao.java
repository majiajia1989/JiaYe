package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.BbsBoard;

@Repository
public class BbsBoardDao extends BaseDao<BbsBoard>
{
	
	private final String GET_ALL_COUNT = "select count(u.id) from BbsTopic u";
	private final String GET_All = "from BbsBoard u";
	private final String GET_BY_ID = "from BbsBoard u where u.id=?";
	private final String GET_ALL_BY_COMMUNITY = "from BbsBoard u where u.community.id=?";
	private final String GET_EANBLE_BY_COMMUNITY = "from BbsBoard u where u.cancelTime is null and u.community.id=?";
	private final String GET_DISABLE_BY_COMMUNITY = "from BbsBoard u where u.cancelTime is not null and  u.community.id=?";
	
	private final String GET_ALL_COUNT_BY_COMMUNITY = "select count(u.id) from BbsBoard u where u.community.id=?";
	private final String GET_EANBLE_COUNT_BY_COMMUNITY = "select count(u.id) from BbsBoard u where u.cancelTime is null and u.community.id=?";
	private final String GET_DISABLE_COUNT_BY_COMMUNITY = "select count(u.id) from BbsBoard u where u.cancelTime is not null and  u.community.id=?";
	
	public long getAllCount() {
        return count(GET_ALL_COUNT);
	}
	
	public List<BbsBoard> getAllBoard()
	{
		return find(GET_All);
	}
	
	public BbsBoard getById(long id)
	{
		return get(GET_BY_ID,id);
	}
	
	
	public List<BbsBoard> getAllBoardByCommunity(long communityId)
	{
		return find(GET_ALL_BY_COMMUNITY,new Object[]{communityId});
	}
	
	public List<BbsBoard> getAllBoardByCommunity(long communityId,int startRow,int rows)
	{
		ArrayList<Object> params= new ArrayList<Object>();
		params.add(communityId);
		return findWithPage(GET_ALL_BY_COMMUNITY,startRow,rows, params);
	}
	
	
	public List<BbsBoard> getEnableBoardByCommunity(long communityId)
	{
		ArrayList<Object> params= new ArrayList<Object>();
		params.add(communityId);
		return find(GET_EANBLE_BY_COMMUNITY,params);
	}
	
	public List<BbsBoard> getEnableBoardByCommunity(long communityId,int startRow,int rows)
	{
		ArrayList<Object> params= new ArrayList<Object>();
		params.add(communityId);
		return findWithPage(GET_EANBLE_BY_COMMUNITY,startRow,rows, params);
	}
	
	
	public List<BbsBoard> getDisableBoardByCommunity(long communityId)
	{
		ArrayList<Object> params= new ArrayList<Object>();
		params.add(communityId);
		return find(GET_DISABLE_BY_COMMUNITY,params);
	}
	
	public List<BbsBoard> getDisableBoardByCommunity(long communityId,int startRow,int rows)
	{
		ArrayList<Object> params= new ArrayList<Object>();
		params.add(communityId);
		return findWithPage(GET_DISABLE_BY_COMMUNITY,startRow,startRow, params);
	}
	
	public long getAllCountByCommunity(long communityId)
	{
		return count(GET_ALL_COUNT_BY_COMMUNITY,new Object[]{communityId});
	}
	
	public long getEnableCountByCommunity(long communityId)
	{
		return count(GET_EANBLE_COUNT_BY_COMMUNITY,new Object[]{communityId});
	}
	
	public long getDisableCountByCommunity(long communityId)
	{
		return count(GET_DISABLE_COUNT_BY_COMMUNITY,new Object[]{communityId});
	}
}

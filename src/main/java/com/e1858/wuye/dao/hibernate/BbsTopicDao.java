package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.BbsTopic;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.Subscriber;

@Repository
public class BbsTopicDao extends BaseDao<BbsTopic>
{
	private final String GET_COUNT = "select count(u.id) from BbsTopic u";
	private final String GET_All = "from BbsTopic u";
	private final String GET_BY_ID = "from BbsTopic u where u.id=?";
	
	private final String GET_ALL_BY_BOARD = "from BbsTopic u where u.bbsBoard.id=?";
	private final String GET_EANBLE_BY_BOARD = "from BbsTopic u where u.cancelTime is null and u.bbsBoard.id=? order by id desc";
	private final String GET_DISABLE_BY_BOARD = "from BbsTopic u where u.cancelTime is not null and u.bbsBoard.id=?";
	
	
	private final String GET_ALL_BY_COMMUNITY = "from BbsTopic u where u.community.id=?";
	private final String GET_EANBLE_BY_COMMUNITY = "from BbsTopic u where u.cancelTime is null and u.community.id=? order by id desc";
	private final String GET_DISABLE_BY_COMMUNITY = "from BbsTopic u where u.cancelTime is not null and u.community.id=?";
	
	private final String GET_ALL_BY_BOARD_COMMUNITY = "from BbsTopic u where u.bbsBoard.id=? and u.community.id=?";
	private final String GET_EANBLE_BY_BOARD_COMMUNITY = "from BbsTopic u where u.cancelTime is null and u.bbsBoard.id=? and u.community.id=? order by id desc";
	private final String GET_DISABLE_BY_BOARD_COMMUNITY = "from BbsTopic u where u.cancelTime is not null and u.bbsBoard.id=? and u.community.id=?";
	
	private final String GET_ALL_BY_SUBSCRIBER = "from BbsTopic u where u.subscriber.openid=?";
	private final String GET_EANBLE_BY_SUBSCRIBER = "from BbsTopic u where u.cancelTime is null and u.subscriber.openid=?";
	private final String GET_DISABLE_BY_SUBSCRIBER = "from BbsTopic u where u.cancelTime is not null and  u.subscriber.openid=?";

	private final String GET_CREAM_BY_BOARD = "select a from BbsTopic a, BbsCream b where a.cancelTime is null and a.id=b.bbsTopic.id and b.bbsBoard.id=?";
	private final String GET_CREAM_BY_COMMUNITY = "select a from BbsTopic a, BbsCream b where a.cancelTime is null and a.id=b.bbsTopic.id and b.community.id=?";
	private final String GET_CREAM_BY_BOARD_COMMUNITY = "select a from BbsTopic a, BbsCream b where a.cancelTime is null and a.id=b.bbsTopic.id and b.bbsBoard.id=?  and b.community.id=?";

	private final String GET_TOP_BY_BOARD = "select a from BbsTopic a, BbsTop b where a.cancelTime is null and a.id=b.bbsTopic.id and b.bbsBoard.id=? and";
	private final String GET_TOP_BY_COMMUNITY = "select a from BbsTopic a, BbsTop b where a.cancelTime is null and a.id=b.bbsTopic.id and b.community.id=?";
	private final String GET_TOP_BY_BOARD_COMMUNITY = "select a from BbsTopic a, BbsTop b where a.cancelTime is null and a.id=b.bbsTopic.id and b.bbsBoard.id=?  and b.community.id=?";

	public long getTopicCount()
	{
		return count(GET_COUNT);
	}

	public List<BbsTopic> getAllTopic()
	{
		return find(GET_All);
	}

	public BbsTopic getTopicById(long id)
	{
		return get(GET_BY_ID, id);
	}
	
	public List<BbsTopic> getAllTopicByBoard(long boardId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		return find(GET_ALL_BY_BOARD, params);
	}
	
	public List<BbsTopic> getAllTopicByCommunity(long communityId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(communityId);
		return find(GET_ALL_BY_COMMUNITY, params);
	}

	public List<BbsTopic> getAllTopicByBoardCommunity(long boardId, long communityId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		params.add(communityId);
		return find(GET_ALL_BY_BOARD_COMMUNITY, params);
	}
	
	public List<BbsTopic> getAllTopicByBoard(long boardId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		return findWithPage(GET_ALL_BY_BOARD, startRow, rows, params);
	}
	
	public List<BbsTopic> getAllTopicByCommunity(long communityId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(communityId);
		return findWithPage(GET_ALL_BY_COMMUNITY, startRow, rows, params);
	}

	public List<BbsTopic> getAllTopicByBoardCommunity(long boardId, long communityId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		params.add(communityId);
		return findWithPage(GET_ALL_BY_BOARD_COMMUNITY, startRow, rows, params);
	}
	
	public List<BbsTopic> getEnableTopicByBoard(long boardId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		return find(GET_EANBLE_BY_BOARD, params);
	}
	
	public List<BbsTopic> getEnableTopicByCommunity(long communityId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(communityId);
		return find(GET_EANBLE_BY_COMMUNITY, params);
	}

	public List<BbsTopic> getEnableTopicByBoardCommunity(long boardId, long communityId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		params.add(communityId);
		return find(GET_EANBLE_BY_BOARD_COMMUNITY, params);
	}
	
	public List<BbsTopic> getEnableTopicByBoard(long boardId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		return findWithPage(GET_EANBLE_BY_BOARD, startRow, rows, params);
	}
	
	public List<BbsTopic> getEnableTopicByCommunity(long communityId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(communityId);
		return findWithPage(GET_EANBLE_BY_COMMUNITY, startRow, rows, params);
	}

	public List<BbsTopic> getEnableTopicByBoardCommunity(long boardId, long communityId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		params.add(communityId);
		return findWithPage(GET_EANBLE_BY_BOARD_COMMUNITY, startRow, rows, params);
	}
	
	public List<BbsTopic> getDisableTopicByBoard(long boardId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		return find(GET_DISABLE_BY_BOARD, params);
	}
	
	public List<BbsTopic> getDisableTopicByCommunity(long communityId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(communityId);
		return find(GET_DISABLE_BY_COMMUNITY, params);
	}

	public List<BbsTopic> getDisableTopicByBoardCommunity(long boardId, long communityId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		params.add(communityId);
		return find(GET_DISABLE_BY_BOARD_COMMUNITY, params);
	}

	public List<BbsTopic> getDisableTopicByBoard(long boardId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		return findWithPage(GET_DISABLE_BY_BOARD, startRow, rows, params);
	}
	
	public List<BbsTopic> getDisableTopicByCommunity(long communityId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(communityId);
		return findWithPage(GET_DISABLE_BY_COMMUNITY, startRow, rows, params);
	}
	
	public List<BbsTopic> getDisableTopicByBoardCommunity(long boardId, long communityId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		params.add(communityId);
		return findWithPage(GET_DISABLE_BY_BOARD_COMMUNITY, startRow, rows, params);
	}

	public List<BbsTopic> getAllTopicBySubscriber(String subscriberOpenid)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(subscriberOpenid);
		return find(GET_ALL_BY_SUBSCRIBER, params);
	}

	public List<BbsTopic> getAllTopicBySubscriber(String subscriberOpenid, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(subscriberOpenid);
		return findWithPage(GET_ALL_BY_SUBSCRIBER, startRow, rows, params);
	}

	public List<BbsTopic> getEnableTopicBySubscriber(String subscriberOpenid)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(subscriberOpenid);
		return find(GET_EANBLE_BY_SUBSCRIBER, params);
	}

	public List<BbsTopic> getEnableTopicBySubscriber(String subscriberOpenid, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(subscriberOpenid);
		return findWithPage(GET_EANBLE_BY_SUBSCRIBER, startRow, rows, params);
	}

	public List<BbsTopic> getDisableTopicBySubscriber(String subscriberOpenid)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(subscriberOpenid);
		return find(GET_DISABLE_BY_SUBSCRIBER, params);
	}

	public List<BbsTopic> getDisableTopicBySubscriber(String subscriberOpenid, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(subscriberOpenid);
		return findWithPage(GET_DISABLE_BY_SUBSCRIBER, startRow, rows, params);
	}
	
	public List<BbsTopic> getCreamTopicByBoard(long boardId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);

		return find(GET_CREAM_BY_BOARD, params);
	}
	
	public List<BbsTopic> getCreamTopicByCommunity(long communityId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(communityId);

		return find(GET_CREAM_BY_COMMUNITY, params);
	}

	public List<BbsTopic> getCreamTopicByBoardCommunity(long boardId, long communityId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		params.add(communityId);

		return find(GET_CREAM_BY_BOARD_COMMUNITY, params);
	}
	
	public List<BbsTopic> getCreamTopicByBoard(long boardId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		return findWithPage(GET_CREAM_BY_BOARD, startRow, rows, params);
	}
	
	public List<BbsTopic> getCreamTopicByCommunity(long communityId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(communityId);
		return findWithPage(GET_CREAM_BY_COMMUNITY, startRow, rows, params);
	}

	public List<BbsTopic> getCreamTopicByBoardCommunity(long boardId, long communityId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		params.add(communityId);
		return findWithPage(GET_CREAM_BY_BOARD_COMMUNITY, startRow, rows, params);
	}
	
	public List<BbsTopic> getTopTopicByBoard(long boardId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		return find(GET_TOP_BY_BOARD, params);
	}
	
	public List<BbsTopic> getTopTopicByCommunity(long communityId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(communityId);
		return find(GET_TOP_BY_COMMUNITY, params);
	}

	public List<BbsTopic> getTopTopicByBoardCommunity(long boardId, long communityId)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		params.add(communityId);
		return find(GET_TOP_BY_BOARD_COMMUNITY, params);
	}

	public List<BbsTopic> getTopTopicByBoard(long boardId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		return findWithPage(GET_TOP_BY_BOARD, startRow, rows, params);
	}
	
	public List<BbsTopic> getTopTopicByCommunity(long communityId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(communityId);
		return findWithPage(GET_TOP_BY_COMMUNITY, startRow, rows, params);
	}
	
	public List<BbsTopic> getTopTopicByBoardCommunity(long boardId, long communityId, int startRow, int rows)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(boardId);
		params.add(communityId);
		return findWithPage(GET_TOP_BY_BOARD_COMMUNITY, startRow, rows, params);
	}

}

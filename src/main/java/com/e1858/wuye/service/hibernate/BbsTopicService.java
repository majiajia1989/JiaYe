package com.e1858.wuye.service.hibernate;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.entity.hibernate.BbsBoard;
import com.e1858.wuye.entity.hibernate.BbsMainPost;
import com.e1858.wuye.entity.hibernate.BbsPost;
import com.e1858.wuye.entity.hibernate.BbsTopic;
import com.e1858.wuye.entity.hibernate.BbsCream;
import com.e1858.wuye.entity.hibernate.BbsTop;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.Subscriber;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.dao.hibernate.BbsBoardDao;
import com.e1858.wuye.dao.hibernate.BbsCreamDao;
import com.e1858.wuye.dao.hibernate.BbsPostDao;
import com.e1858.wuye.dao.hibernate.BbsTopDao;
import com.e1858.wuye.dao.hibernate.BbsTopicDao;
import com.e1858.wuye.dao.hibernate.SubscriberDao;

@Service
@Transactional
public class BbsTopicService
{
	@Autowired
	private BbsTopicDao bbsTopicDao;
	@Autowired
	private SubscriberDao subscriberDao;
	@Autowired
	private BbsBoardDao bbsBoardDao;
	@Autowired
	private BbsPostDao bbsPostDao;
	
	
	
	public void addTopic(long boardId, String title, String content, Subscriber subscriber)
	{
		BbsBoard board = (BbsBoard) bbsBoardDao.getById(boardId);
		board.setTopics(board.getTopics() + 1);
		bbsBoardDao.save(board);
		
		BbsTopic bbsTopic = new BbsTopic();
		bbsTopic.setSysCorp(board.getSysCorp());
		bbsTopic.setCommunity(board.getCommunity());
		bbsTopic.setLastPost(new Date());
		bbsTopic.setBbsBoard(board);
		bbsTopic.setCreateTime(new Date());
		bbsTopic.setSubscriber(subscriber);
		bbsTopic.setTitle(title);
		bbsTopic.setContent(content);
		bbsTopicDao.save(bbsTopic);

		bbsTopic.getBbsMainPost().setBbsTopic(bbsTopic);
		BbsMainPost bbsMainPost = bbsTopic.getBbsMainPost();
		bbsMainPost.setSysCorp(board.getSysCorp());
		bbsMainPost.setCommunity(bbsTopic.getCommunity());
		bbsMainPost.setBbsBoard(bbsTopic.getBbsBoard());
		bbsMainPost.setBbsTopic(bbsTopic);
		bbsMainPost.setCreateTime(new Date());
		bbsMainPost.setSubscriber(bbsTopic.getSubscriber());
		bbsMainPost.setTitle(bbsTopic.getTitle());
		bbsMainPost.setContent(content);
		bbsPostDao.save(bbsTopic.getBbsMainPost());

		subscriber.setIntegrate(subscriber.getIntegrate() + 10);
		subscriberDao.merge(subscriber);
	}
	
	public void save(BbsTopic bbsTopic)
	{
		bbsTopicDao.save(bbsTopic);
	}
	
	public void update(BbsTopic bbsTopic)
	{
		bbsTopicDao.update(bbsTopic);
	}
	
	/**
	 * 用户删除自己的一个主题帖。
	 * 
	 * @param id
	 * 要删除的主题帖ID
	 */
	public String removeTopic(long id,String openid)
	{
		BbsTopic bbsTopic = bbsTopicDao.getTopicById(id);
		if(null == bbsTopic)
		{
			return "该话题不存在！";
		}
		
		if(!bbsTopic.getSubscriber().getOpenid().equalsIgnoreCase(openid))
		{
			return "您无权删除该话题！";
		}

		// 将论坛版块的主题帖数减1
		BbsBoard board = (BbsBoard) bbsBoardDao.getById(bbsTopic.getBbsBoard().getId());
		board.setTopics(board.getTopics() - 1);
		bbsBoardDao.save(board);
		
		// 删除主题帖及其关联的帖子
		bbsTopic.setCancelTime(new Date());
		bbsTopic.setCanceler(-1l);
		bbsTopicDao.update(bbsTopic);
		bbsPostDao.deleteTopicPosts(id, -1l);
		return "";
	}

	/**
	 * 删除一个主题帖，用户积分减50，论坛版块主题帖数减1，删除 主题帖所有关联的帖子。
	 * 
	 * @param id
	 *            要删除的主题帖ID
	 * @param user
	 */
	public void removeTopic(long id, long user)
	{
		BbsTopic bbsTopic = bbsTopicDao.getTopicById(id);

		// 将论坛版块的主题帖数减1
		BbsBoard board = (BbsBoard) bbsBoardDao.getById(bbsTopic.getBbsBoard().getId());
		board.setTopics(board.getTopics() - 1);
		bbsBoardDao.save(board);

		// 发表该主题帖用户扣除50积分
		Subscriber subscriber = bbsTopic.getSubscriber();
		subscriber.setIntegrate(subscriber.getIntegrate() - 10);
		subscriberDao.merge(subscriber);

		// 删除主题帖及其关联的帖子
		bbsTopic.setCancelTime(new Date());
		bbsTopic.setCanceler(user);
		bbsTopicDao.update(bbsTopic);
		bbsPostDao.deleteTopicPosts(id, user);
	}

	/**
	 * 删除一个主题帖，用户积分减50，论坛版块主题帖数减1，删除 主题帖所有关联的帖子。
	 * 
	 * @param bbsTopic
	 * @param user
	 */
	public void removeTopic(BbsTopic bbsTopic, long user)
	{
		// 将论坛版块的主题帖数减1
		BbsBoard board = (BbsBoard) bbsBoardDao.getById(bbsTopic.getBbsBoard().getId());
		board.setTopics(board.getTopics() - 1);
		bbsBoardDao.save(board);

		// 发表该主题帖用户扣除50积分
		Subscriber subscriber = bbsTopic.getSubscriber();
		subscriber.setIntegrate(subscriber.getIntegrate() - 10);
		subscriberDao.merge(subscriber);

		// 删除主题帖及其关联的帖子
		bbsTopic.setCancelTime(new Date());
		bbsTopic.setCanceler(user);
		bbsTopicDao.update(bbsTopic);
		bbsPostDao.deleteTopicPosts(bbsTopic.getId(), user);
	}
	
	public BbsTopic getById(long topicId)
	{
		return bbsTopicDao.getTopicById(topicId);
	}
	
	public List<BbsTopic> getAllTopicByBoard(long boardId)
	{
		return bbsTopicDao.getAllTopicByBoard(boardId);
	}
	
	public List<BbsTopic> getAllTopicByCommunity(long communityId)
	{
		return bbsTopicDao.getAllTopicByCommunity(communityId);
	}

	public List<BbsTopic> getAllTopicByBoardCommunity(long boardId,long communityId)
	{
		return bbsTopicDao.getAllTopicByBoardCommunity(boardId,communityId);
	}
	
	public List<BbsTopic> getAllTopicByBoard(long boardId,int startRow,int rows)
	{
		return bbsTopicDao.getAllTopicByBoard(boardId,startRow,rows);
	}
	
	public List<BbsTopic> getAllTopicByCommunity(long boardId,int startRow,int rows)
	{
		return bbsTopicDao.getAllTopicByCommunity(boardId,startRow,rows);
	}
	
	public List<BbsTopic> getAllTopicByBoardCommunity(long boardId,long communityId,int startRow,int rows)
	{
		return bbsTopicDao.getAllTopicByBoardCommunity(boardId,communityId,startRow,rows);
	}
	
	public List<BbsTopic> getEnableTopicByBoard(long boardId)
	{
		return bbsTopicDao.getEnableTopicByBoard(boardId);
	}
	
	public List<BbsTopic> getEnableTopicByCommunity(long communityId)
	{
		return bbsTopicDao.getEnableTopicByCommunity(communityId);
	}
	
	
	public List<BbsTopic> getEnableTopicByBoardCommunity(long boardId,long communityId)
	{
		return bbsTopicDao.getEnableTopicByBoardCommunity(boardId,communityId);
	}
	
	public List<BbsTopic> getEnableTopicByBoard(long boardId, int startRow,int rows)
	{
		return bbsTopicDao.getEnableTopicByBoard(boardId,startRow,rows);
	}
	
	public List<BbsTopic> getEnableTopicByCommunity(long communityId,int startRow,int rows)
	{
		return bbsTopicDao.getEnableTopicByCommunity(communityId,startRow,rows);
	}
	
	public List<BbsTopic> getEnableTopicByBoardCommunity(long boardId,long communityId,int startRow,int rows)
	{
		return bbsTopicDao.getEnableTopicByBoardCommunity(boardId,communityId,startRow,rows);
	}
	
	public List<BbsTopic> getDisableTopicByBoard(long boardId)
	{
		return bbsTopicDao.getDisableTopicByBoard(boardId);
	}
	
	public List<BbsTopic> getDisableTopicByCommunity(long communityId)
	{
		return bbsTopicDao.getDisableTopicByCommunity(communityId);
	}
	
	public List<BbsTopic> getDisableTopicByBoardCommunity(long boardId,long communityId)
	{
		return bbsTopicDao.getDisableTopicByBoardCommunity(boardId,communityId);
	}
	
	public List<BbsTopic> getDisableTopicByBoard(long boardId,int startRow,int rows)
	{
		return bbsTopicDao.getDisableTopicByBoard(boardId,startRow,rows);
	}
	
	public List<BbsTopic> getDisableTopicByCommunity(long communityId,int startRow,int rows)
	{
		return bbsTopicDao.getDisableTopicByCommunity(communityId,startRow,rows);
	}
	
	public List<BbsTopic> getDisableTopicByBoardCommunity(long boardId,long communityId,int startRow,int rows)
	{
		return bbsTopicDao.getDisableTopicByBoardCommunity(boardId,communityId,startRow,rows);
	}
	
	
	public List<BbsTopic> getAllTopicBySubscriber(String subscriberOpenid)
	{
		return bbsTopicDao.getAllTopicBySubscriber(subscriberOpenid);
	}
	
	public List<BbsTopic> getAllTopicBySubscriber(String subscriberOpenid,int startRow,int rows)
	{
		return bbsTopicDao.getAllTopicBySubscriber(subscriberOpenid,startRow,rows);
	}
	
	
	public List<BbsTopic> getEnableTopicBySubscriber(String subscriberOpenid)
	{
		return bbsTopicDao.getEnableTopicBySubscriber(subscriberOpenid);
	}
	
	public List<BbsTopic> getEnableTopicBySubscriber(String subscriberOpenid,int startRow,int rows)
	{
		return bbsTopicDao.getEnableTopicBySubscriber(subscriberOpenid,startRow,rows);
	}
	
	
	public List<BbsTopic> getDisableTopicBySubscriber(String subscriberOpenid)
	{
		return bbsTopicDao.getDisableTopicBySubscriber(subscriberOpenid);
	}
	
	public List<BbsTopic> getDisableTopicBySubscriber(String subscriberOpenid,int startRow,int rows)
	{
		return bbsTopicDao.getDisableTopicBySubscriber(subscriberOpenid,startRow,rows);
	}
	
	public List<BbsTopic> getCreamTopicByBoard(long boardId)
	{
		return bbsTopicDao.getCreamTopicByBoard(boardId);
	}
	
	public List<BbsTopic> getCreamTopicByCommunity(long communityId)
	{
		return bbsTopicDao.getCreamTopicByCommunity(communityId);
	}
	
	public List<BbsTopic> getCreamTopicByBoardCommunity(long boardId,long communityId)
	{
		return bbsTopicDao.getCreamTopicByBoardCommunity(boardId,communityId);
	}
	
	public List<BbsTopic> getCreamTopicByBoard(long boardId,int startRow,int rows)
	{
		return bbsTopicDao.getCreamTopicByBoard(boardId,startRow,rows);
	}
	
	public List<BbsTopic> getCreamTopicByCommunity(long communityId,int startRow,int rows)
	{
		return bbsTopicDao.getCreamTopicByCommunity(communityId,startRow,rows);
	}
	
	public List<BbsTopic> getCreamTopicByBoardCommunity(long boardId,long communityId,int startRow,int rows)
	{
		return bbsTopicDao.getCreamTopicByBoardCommunity(boardId,communityId,startRow,rows);
	}
	
	public List<BbsTopic> getTopTopicByBoard(long boardId)
	{
		return bbsTopicDao.getTopTopicByBoard(boardId);
	}
	
	public List<BbsTopic> getTopTopicByCommunity(long communityId)
	{
		return bbsTopicDao.getTopTopicByCommunity(communityId);
	}
	
	public List<BbsTopic> getTopTopicByBoardCommunity(long boardId,long communityId)
	{
		return bbsTopicDao.getTopTopicByBoardCommunity(boardId,communityId);
	}
	
	public List<BbsTopic> getTopTopicByBoard(long boardId,int startRow,int rows)
	{
		return bbsTopicDao.getTopTopicByBoard(boardId,startRow,rows);
	}
	
	public List<BbsTopic> getTopTopicByCommunity(long communityId,int startRow,int rows)
	{
		return bbsTopicDao.getTopTopicByCommunity(communityId,startRow,rows);
	}
	
	public List<BbsTopic> getTopTopicByBoardCommunity(long boardId,long communityId,int startRow,int rows)
	{
		return bbsTopicDao.getTopTopicByBoardCommunity(boardId,communityId,startRow,rows);
	}
}

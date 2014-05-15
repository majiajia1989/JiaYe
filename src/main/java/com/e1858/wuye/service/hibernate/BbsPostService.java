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
public class BbsPostService
{

	@Autowired
	private BbsPostDao bbsPostDao;

	@Autowired
	private SubscriberDao subscriberDao;

	@Autowired
	private BbsTopicDao bbsTopicDao;
	
	public void save(BbsPost bbsPost)
	{
		bbsPostDao.save(bbsPost);
	}
	
	public void update(BbsPost bbsPost)
	{
		bbsPostDao.update(bbsPost);
	}

	public void addPost(long topicId, String title, String content, Subscriber subscriber)
	{
		BbsTopic bbsTopic = bbsTopicDao.getTopicById(topicId);
		bbsTopic.setReplies(bbsTopic.getReplies() + 1);
		bbsTopic.setLastPost(new Date());
		bbsTopicDao.save(bbsTopic);

		BbsPost bbsPost = new BbsPost();
		bbsPost.setSysCorp(bbsTopic.getSysCorp());
		bbsPost.setCommunity(bbsTopic.getCommunity());
		bbsPost.setBbsBoard(bbsTopic.getBbsBoard());
		bbsPost.setBbsTopic(bbsTopic);
		bbsPost.setTitle(title);
		bbsPost.setContent(content);
		bbsPost.setCreateTime(new Date());
		bbsPost.setSubscriber(subscriber);
		bbsPostDao.save(bbsPost);

		subscriber.setIntegrate(subscriber.getIntegrate() + 5);
		subscriberDao.merge(subscriber);

	}

	/**
	 * 删除一个回复的帖子，发表回复帖子的用户积分减20，主题帖的回复数减1
	 * 
	 * @param id
	 * @param user
	 */
	public void removePost(long id, long user)
	{
		BbsPost bbsPost = bbsPostDao.getById(id);
		bbsPost.setCancelTime(new Date());
		bbsPost.setCanceler(user);
		bbsPostDao.update(bbsPost);

		BbsTopic bbs_Topic = bbsTopicDao.get(bbsPost.getBbsTopic().getId());
		bbs_Topic.setReplies(bbs_Topic.getReplies() - 1);

		Subscriber subscriber = bbsPost.getSubscriber();
		subscriber.setIntegrate(subscriber.getIntegrate() - 20);
		subscriberDao.merge(subscriber);
	}

	public List<BbsPost> getAllPostByTopic(long topicId)
	{
		return bbsPostDao.getAllPostByTopic(topicId);
	}

	public List<BbsPost> getAllPostByTopic(long topicId, int startRow, int rows)
	{
		return bbsPostDao.getAllPostByTopic(topicId, startRow, rows);
	}

	public List<BbsPost> getEnablePostByTopic(long topicId)
	{
		return bbsPostDao.getEnablePostByTopic(topicId);
	}

	public List<BbsPost> getEnablePostByTopic(long topicId, int startRow, int rows)
	{
		return bbsPostDao.getEnablePostByTopic(topicId, startRow, rows);
	}

	public List<BbsPost> getDisablePostByTopic(long topicId)
	{
		return bbsPostDao.getDisablePostByTopic(topicId);
	}

	public List<BbsPost> getDisablePostByTopic(long topicId, int startRow, int rows)
	{
		return bbsPostDao.getDisablePostByTopic(topicId, startRow, rows);
	}
}

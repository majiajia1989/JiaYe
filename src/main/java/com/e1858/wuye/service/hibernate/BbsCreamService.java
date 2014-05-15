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
public class BbsCreamService
{

	@Autowired
	private BbsCreamDao bbsCreamDao;


	/**
	 * 将帖子置为精华主题帖
	 * 
	 * @param bbsTopic
	 * @param sysUser
	 */
	public void AddTopic(BbsTopic bbsTopic, SysUser sysUser)
	{
		BbsCream bbsCream = new BbsCream();
		bbsCream.setBbsTopic(bbsTopic);
		bbsCream.setSysCorp(bbsTopic.getSysCorp());
		bbsCream.setCommunity(bbsTopic.getCommunity());
		bbsCream.setSysUser(sysUser);
		bbsCream.setCreateTime(new Date());
		bbsCreamDao.save(bbsCream);
	}

	/**
	 * 取消精华主题帖
	 * 
	 * @param bbsTopic
	 */
	public void removeTopic(BbsTopic bbsTopic)
	{
		BbsCream bbsCream = bbsCreamDao.getByTopic(bbsTopic.getId());
		bbsCreamDao.delete(bbsCream);
	}

	/**
	 * 取消精华主题帖
	 * 
	 * @param id
	 */
	public void removeTopic(long id)
	{
		BbsCream bbsCream = bbsCreamDao.getByTopic(id);
		bbsCreamDao.delete(bbsCream);
	}
}

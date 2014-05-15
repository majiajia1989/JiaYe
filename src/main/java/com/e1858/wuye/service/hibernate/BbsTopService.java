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
public class BbsTopService
{
	@Autowired
	private BbsTopDao bbsTopDao;


	/**
	 * 将帖子置顶
	 * 
	 * @param bbsTopic
	 * @param sysUser
	 */
	public void AddTopic(BbsTopic bbsTopic, SysUser sysUser)
	{
		BbsTop bbsTop= new BbsTop();
		bbsTop.setBbsTopic(bbsTopic);
		bbsTop.setSysCorp(bbsTopic.getSysCorp());
		bbsTop.setCommunity(bbsTopic.getCommunity());
		bbsTop.setSysUser(sysUser);
		bbsTop.setCreateTime(new Date());
		bbsTopDao.save(bbsTop);
	}

	/**
	 * 取消置顶
	 * 
	 * @param bbsTopic
	 */
	public void removeTopic(BbsTopic bbsTopic)
	{
		BbsTop bbsTop = bbsTopDao.getByTopic(bbsTopic.getId());
		bbsTopDao.delete(bbsTop);
	}

	/**
	 * 取消置顶
	 * 
	 * @param id
	 */
	public void removeTopic(long id)
	{
		BbsTop bbsTop = bbsTopDao.getByTopic(id);
		bbsTopDao.delete(bbsTop);
	}
}

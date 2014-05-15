package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.BbsPost;
import com.e1858.wuye.entity.hibernate.BbsTopic;
/**
 * Bbs_Post的DAO类
 *
 */
@Repository
public class BbsPostDao extends BaseDao<BbsPost> {
	

	protected final String DELETE_TOPIC_POSTS = "update BbsPost u set u.cancelTime=?,u.canceler=? where u.bbsTopic.id=?";
	
	private final String GET_BY_ID = "from BbsPost u where u.id=?";
	
	private final String GET_ALL_BY_TOPIC = "from BbsPost u where u.bbsTopic.id=?";
	private final String GET_EANBLE_BY_TOPIC = "from BbsPost u where u.cancelTime is null and u.bbsTopic.id=? order by id desc";
	private final String GET_DISABLE_BY_TOPIC = "from BbsPost u where u.cancelTime is not null and u.bbsTopic.id=?";
    
	/**
	 * 删除主题下的所有帖子
	 * @param id 主题ID
	 */
	public void deleteTopicPosts(long id,long user ) {
		super.createQuery(DELETE_TOPIC_POSTS, new Date(),id,user).executeUpdate();
	}
	
	/**
	 * 删除主题下的所有帖子
	 * @param topicId 主题ID
	 */
	public void deleteTopicPosts(BbsTopic bbsTopic) {
		super.createQuery(DELETE_TOPIC_POSTS, bbsTopic.getId()).executeUpdate();
	}
	
	public BbsPost getById(long id)
	{
		return get(GET_BY_ID,id);
	}
	
	public List<BbsPost> getAllPostByTopic(long topicId)
	{
		ArrayList<Object> params= new ArrayList<Object>();
		params.add(topicId);
		return find(GET_ALL_BY_TOPIC,params);
	}
	
	public List<BbsPost> getAllPostByTopic(long topicId,int startRow,int rows)
	{
		ArrayList<Object> params= new ArrayList<Object>();
		params.add(topicId);
		return findWithPage(GET_ALL_BY_TOPIC,startRow,rows, params);
	}
	
	
	public List<BbsPost> getEnablePostByTopic(long topicId)
	{
		ArrayList<Object> params= new ArrayList<Object>();
		params.add(topicId);
		return find(GET_EANBLE_BY_TOPIC,params);
	}
	
	public List<BbsPost> getEnablePostByTopic(long topicId,int startRow,int rows)
	{
		ArrayList<Object> params= new ArrayList<Object>();
		params.add(topicId);
		return findWithPage(GET_EANBLE_BY_TOPIC,startRow,rows, params);
	}
	public List<BbsPost> getDisablePostByTopic(long topicId)
	{
		ArrayList<Object> params= new ArrayList<Object>();
		params.add(topicId);
		return find(GET_DISABLE_BY_TOPIC,params);
	}
	
	public List<BbsPost> getDisablePostByTopic(long topicId,int startRow,int rows)
	{
		ArrayList<Object> params= new ArrayList<Object>();
		params.add(topicId);
		return findWithPage(GET_DISABLE_BY_TOPIC,startRow,rows, params);
	}
}

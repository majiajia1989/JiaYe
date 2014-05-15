package com.e1858.wuye.dao.hibernate;


import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.BbsCream;
import com.e1858.wuye.entity.hibernate.BbsTop;
import com.e1858.wuye.entity.hibernate.BbsTopic;
@Repository
public class BbsTopDao  extends BaseDao<BbsTop>
{
	private String GET_BY_TOPIC_ID = "from BbsTop u where u.bbsTopic.id=?";
	private String GET_BY_ID = "from BbsTop u where u.id=?";
	
	public BbsTop getByTopic(long topicId)
	{
		return get(GET_BY_TOPIC_ID,topicId);
	}
	
	public BbsTop getById(long id)
	{
		return get(GET_BY_ID,id);
	}
}

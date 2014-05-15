package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.BbsCream;
import com.e1858.wuye.entity.hibernate.BbsTopic;

@Repository
public class BbsCreamDao  extends BaseDao<BbsCream>
{
	private String GET_BY_TOPIC = "from BbsCream u where u.bbsTopic.id=?";
	private String GET_BY_ID = "from BbsCream u where u.id=?";
	private String GET_BY_COMMUNITY = "from BbsCream u where u.id=?";

	
	public BbsCream getByTopic(long topicId)
	{
		return get(GET_BY_TOPIC,topicId);
	}
	
	public BbsCream getById(long id)
	{
		return get(GET_BY_ID,id);
	}
	
}

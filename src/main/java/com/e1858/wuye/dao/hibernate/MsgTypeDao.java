package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.MsgType;

@Repository("msgTypeDao")
public class MsgTypeDao extends BaseDao<MsgType>
{
	private final String GET_MsgType_BY_TYPE = HQL_FROM + " u where u.type= ? ";

	public MsgType getMsgTypeByType(String type)
	{
		return get(GET_MsgType_BY_TYPE, type);
	}

	public List<MsgType> getAllMsgType()
	{
		return find(HQL_FROM);
	}
}

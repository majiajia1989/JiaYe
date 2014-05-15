package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Msg;
import com.e1858.wuye.entity.hibernate.MsgKey;

@Repository("msgKeyDao")
public class MsgKeyDao extends BaseDao<MsgKey>
{
	private final String GET_MSGKEY_BY_MSG = HQL_FROM + " u where u.msg=?";
	private final String GET_MSGKEY_BYID = HQL_FROM + " u where u.id=?";
	// private final String GET_MSGKEY_BY_USER = HQL_FROM +
	// " u where  u.sysUser= ? and u.command = ?";
	private final String DELETE_MSGKEY_BYMSG = "delete " + HQL_FROM + " u where u.msg=?";

	public List<MsgKey> getMsgKeyByMsg(Msg msg)
	{
		List<MsgKey> msgKeys = find(GET_MSGKEY_BY_MSG, msg);

		return msgKeys;

	}

	// public MsgKey getMsgKeyByUser(SysUser sysUser, String keyword)
	// {
	// return get(GET_MSGKEY_BY_USER, sysUser, keyword);
	// }

	public MsgKey getMsgKey(long id)
	{
		return get(GET_MSGKEY_BYID, id);
	}

	public int deleteByMsg(Msg msg)
	{
		return createQuery(DELETE_MSGKEY_BYMSG, msg).executeUpdate();
	}

}

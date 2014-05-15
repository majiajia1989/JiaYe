package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.Msg;
import com.e1858.wuye.entity.hibernate.SysCorp;

@Repository("msgDao")
public class MsgDao extends BaseDao<Msg>
{
	private final String GET_MSG_BY_COMMUNITY = HQL_FROM + " u where u.community= ? and u.msgType=? order by u.id desc";
	private final String GET_MSG_BY_CORP = HQL_FROM + " u where u.corp= ? and u.msgType=? order by u.id desc";
	private final String GET_MSG_BYID = HQL_FROM + " u where u.id=?";
	private final String COUNT_MSG_BY_COMMUNITY = "select count(u.id) " + HQL_FROM + " u where u.community= ? and u.msgType=?";
	
	public List<Msg> getMsgByCommunityAndType(Community community, String msgType)
	{
		return find(GET_MSG_BY_COMMUNITY, community, msgType);
	}
	
	public List<Msg> getMsgByCorpAndType(SysCorp corp, String msgType,int startRow,int rows)
	{
		return findWithOffset(GET_MSG_BY_CORP,startRow,rows, corp, msgType);
	}	

	public Msg getMsgByID(long id)
	{
		return get(GET_MSG_BYID, id);
	}
	
	//测试用
//	@Deprecated
//	public List<Msg> getMsgByType(String msgType) {
//		return find(HQL_FROM + " u where u.msgType=?", msgType);
//	}

	public List<Msg> getMsgByCommunityAndType(Community community,
			String msgType, int offset, int count) {
		return findWithOffset(GET_MSG_BY_COMMUNITY, offset, count, community, msgType);
	}

	public long getMsgCountByCommunityAndType(Community community,
			String msgType) {
		return count(COUNT_MSG_BY_COMMUNITY, community, msgType);
	}
	
	public long getMsgCountByCorpAndType(SysCorp corp,
			String msgType) {
		return count(COUNT_MSG_BY_COMMUNITY, corp, msgType);
	}
}

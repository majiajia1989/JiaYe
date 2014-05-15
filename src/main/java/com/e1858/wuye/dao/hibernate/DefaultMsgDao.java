package com.e1858.wuye.dao.hibernate;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.DefaultMsg;
import com.e1858.wuye.entity.hibernate.Msg;
import com.e1858.wuye.entity.hibernate.SysCorp;

@Repository("defaultMsg")
public class DefaultMsgDao extends BaseDao<DefaultMsg> {
	private final String GET_DEFAULTMSG_BY_CORPANDCOMMUNITY = HQL_FROM + " u where u.corp= ? and u.community=? and u.type=?";
	private final String Get_DEFAULTMSG_BY_MSGID=HQL_FROM+"u where u.msg=?";
	private final String GET_DEFAULTMSG_BY_MSGANDTYPE = HQL_FROM + " u where u.msg=? and u.type=?";

	public List<DefaultMsg> getDefaultMsgByCorpandCommunity(SysCorp corp,Community community, byte type){
		return find(GET_DEFAULTMSG_BY_CORPANDCOMMUNITY, corp, community,type);
	}
	public List<DefaultMsg> getDefaultMsgs(Msg msg){
		return find(Get_DEFAULTMSG_BY_MSGID, msg);
	}
	public DefaultMsg getDefaultMsgByType(Msg msg, byte type) {
		return get(GET_DEFAULTMSG_BY_MSGANDTYPE, msg, type);
	}
}

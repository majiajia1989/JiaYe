package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.Notice;

@Repository("noticeDao")
public class NoticeDao extends BaseDao<Notice> {
	private final String GET_NOTICE_BY_COMMUNITY = HQL_FROM + " u where u.community= ? order by u.createTime desc";
	private final String GET_NOTICE_BYID = HQL_FROM + " u where u.id=?";

	private final String COUNT_NOTICE_BY_COMMUNITY = "select count(u.id) " + HQL_FROM + " u where u.community= ?";

	public List<Notice> queryNoticesByCommunity(Community community, int offset, int count) {
		return findWithOffset(GET_NOTICE_BY_COMMUNITY,offset, count, community);
	}

	public Notice queryNoticeByID(long id) {
		return get(GET_NOTICE_BYID, id);
	}

	public long noticesCountByCommunity(Community community) {
		return count(COUNT_NOTICE_BY_COMMUNITY, community);
	}

}
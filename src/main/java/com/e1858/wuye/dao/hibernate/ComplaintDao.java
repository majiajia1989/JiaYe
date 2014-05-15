package com.e1858.wuye.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.Complaint;
import com.e1858.wuye.entity.hibernate.ComplaintResponse;

@Repository("complaintDao")
public class ComplaintDao extends BaseDao<Complaint> {
	private final String QUERY_COMPLAINT_BY_COMMUNITY = HQL_FROM + " u where u.community=? order by u.id desc";
	private final String QUERY_COMPLAINT_BY_ID = HQL_FROM + " u where u.id=?";

//	public List<Complaint> queryComplaintsByCommunity(Community community) {
//		return find(QUERY_COMPLAINT_BY_COMMUNITY, community);
//	}

	@SuppressWarnings("unchecked")
	public List<Complaint> queryComplaintsByCommunity(Community community, Boolean anonymous, Boolean responsed,
			Date begin, Date end,int offset, int count) {
		return createQueryByCommunity(community, anonymous, responsed, begin, end, false).setFirstResult(offset).setMaxResults(count).list();
	}

	public Complaint queryComplaintsByID(long complaintID) {
		return get(QUERY_COMPLAINT_BY_ID, Long.valueOf(complaintID));
	}

	public long countByCommunity(Community community, Boolean anonymous,
			Boolean responsed, Date begin, Date end) {
		return  (Long) createQueryByCommunity(community, anonymous, responsed, begin, end, true).uniqueResult();
	}
	
	private Query createQueryByCommunity(Community community, Boolean anonymous,
			Boolean responsed, Date begin, Date end, boolean isCount) {
		String prefix = isCount ? "SELECT COUNT(u.id) " : "";
		String hql =prefix + HQL_FROM + " u where u.community=?";
		if (anonymous != null) {
			if (anonymous.booleanValue()) {
				hql += " and u.creator.openid = '" + CommonConstant.ANONYMOUS_SUBCRIBER_OPENID + "'";
			} else {
				hql += " and u.creator.openid != '" + CommonConstant.ANONYMOUS_SUBCRIBER_OPENID + "'";
			}
		}
		if (begin != null) {
			hql += " and u.createTime>=:begin";
		}
		if (end != null) {
			hql += " and u.createTime<=:end";
		}
		if (responsed != null) {
			if (responsed.booleanValue()) {
				hql += " and u.id in (select complaint from " + ComplaintResponse.class.getSimpleName() + ")";
			} else {
				hql += " and u.id not in (select complaint from " + ComplaintResponse.class.getSimpleName() + ")";
			}
		}
		hql += " order by u.id desc";
		Query query = createQuery(hql, community);
		if (begin != null) {
			query.setDate("begin", begin);
		}
		if (end != null) {
			query.setDate("end", end);
		}
		return query;
	}

}

package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Complaint;
import com.e1858.wuye.entity.hibernate.ComplaintResponse;

@Repository("complaintResponseDao")
public class ComplaintResponseDao extends BaseDao<ComplaintResponse> {
	private final String QUERY_BY_COMPLAINT = HQL_FROM + " u where u.complaint.id=? order by u.id asc";
	private final String COUNT_BY_COMPLAINT = "SELECT COUNT(u.id) " + HQL_FROM + " u where u.complaint=?";
	private final String COUNT_BY_COMPLAINTID = "SELECT COUNT(u.id) " + HQL_FROM + " u where u.complaint.id=?";

	public long countResponsesByComplaint(Complaint complaint) {
		return count(COUNT_BY_COMPLAINT, complaint);
	}

	public List<ComplaintResponse> queryResponsesByID(long complaintID, int offset, int count) {
		return findWithOffset(QUERY_BY_COMPLAINT, offset, count, complaintID);
	}

	public long countResponsesByComplaintID(long complaintID) {
		return count(COUNT_BY_COMPLAINTID, complaintID);
	}

}

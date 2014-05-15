package com.e1858.wuye.service.hibernate;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.ComplaintDao;
import com.e1858.wuye.dao.hibernate.ComplaintResponseDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.Complaint;
import com.e1858.wuye.entity.hibernate.ComplaintResponse;

@Service
@Transactional
public class ComplaintService {

	@Autowired
	private ComplaintDao complaintDao;
	@Autowired
	private ComplaintResponseDao complaintResponseDao;

//	@Transactional(readOnly=true)
//	public List<Complaint> queryComplaintsByCommunity(Community community) {
//		return complaintDao.queryComplaintsByCommunity(community);
//	}

	@Transactional(readOnly=true)
	public long countResponsesByComplaint(Complaint complaint) {
		return complaintResponseDao.countResponsesByComplaint(complaint);
	}
	
	public long countResponsesByComplaintID(long complaintID) {
		return complaintResponseDao.countResponsesByComplaintID(complaintID);
	}

	@Transactional(readOnly=true)
	public List<Complaint> queryComplaintsByCommunity(Community community, Boolean anonymous, Boolean responsed,
			Date begin, Date end, int offset, int count) {
		return complaintDao.queryComplaintsByCommunity(community, anonymous, responsed, begin, end, offset, count);
	}

	@Transactional(readOnly=true)
	public Complaint queryComplaintsByID(long complaintID) {
		return complaintDao.queryComplaintsByID(complaintID);
	}

	public void saveComplaintResponse(ComplaintResponse response) {
		complaintResponseDao.save(response);
	}

	@Transactional(readOnly=true)
	public List<ComplaintResponse> queryComplaintResponses(long complaintID, int offset, int count) {
		return complaintResponseDao.queryResponsesByID(complaintID, offset, count);
	}

	@Transactional(readOnly=true)
	public long complaintsCountByCommunity(Community community,
			Boolean anonymous, Boolean responsed, Date begin, Date end) {
		return complaintDao.countByCommunity(community, anonymous, responsed, begin, end);
	}
}

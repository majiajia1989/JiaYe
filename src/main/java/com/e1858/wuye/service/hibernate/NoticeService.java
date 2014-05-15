package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.NoticeDao;
import com.e1858.wuye.dao.hibernate.NoticeReceiverDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.Notice;
import com.e1858.wuye.entity.hibernate.NoticeReceiver;
import com.e1858.wuye.entity.hibernate.SysUser;

@Service
@Transactional
public class NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Autowired
	private NoticeReceiverDao noticeReceiverDao;

	public void saveNotice(Notice notice) {
		noticeDao.save(notice);
	}

	@Transactional(readOnly=true)
	public List<Notice> queryNoticesByCommunity(Community community, int offset, int count) {
		return noticeDao.queryNoticesByCommunity(community, offset, count);
	}

	@Transactional(readOnly=true)
	public Notice queryNoticeByID(long noticeID) {
		return noticeDao.queryNoticeByID(noticeID);
	}

	public void updateNotice(Notice notice) {
		noticeDao.update(notice);
	}

	public void saveNoticeReceiver(NoticeReceiver noticeReceiver) {
		noticeReceiverDao.save(noticeReceiver);
	}

	public void deleteNotice(Notice notice) {
		noticeDao.delete(notice);
	}

	public void sendNoticeWithQuery(SysUser creator, long noticeID, String houses, String floors, String units,
			Boolean hasCar) {
		noticeReceiverDao.sendNoticeWithQuery(creator, noticeID, houses, floors, units, hasCar == null ? 2
				: (hasCar ? 1 : 0));
	}

	public long noticesCountByCommunity(Community community) {
		return noticeDao.noticesCountByCommunity(community);
	}

}

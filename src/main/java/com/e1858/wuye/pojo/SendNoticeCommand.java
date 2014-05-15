package com.e1858.wuye.pojo;

import java.util.List;

public class SendNoticeCommand {

	private long noticeID;
	private List<String> receivers;

	public long getNoticeID() {
		return noticeID;
	}

	public void setNoticeID(long noticeID) {
		this.noticeID = noticeID;
	}

	public List<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}
}

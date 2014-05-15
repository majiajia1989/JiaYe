package com.e1858.wuye.pojo;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.e1858.wuye.common.CommonConstant;

public class ComplaintResponseCommand {

	private long complaintID ;
	
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotEmpty)
	private String content;
	
	private String complaintContent;
	private String complaintCreator;
	
	private boolean fromResponseList = false;

	public long getComplaintID() {
		return complaintID;
	}

	public void setComplaintID(long complaintID) {
		this.complaintID = complaintID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComplaintContent() {
		return complaintContent;
	}

	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent;
	}

	public String getComplaintCreator() {
		return complaintCreator;
	}

	public void setComplaintCreator(String complaintCreator) {
		this.complaintCreator = complaintCreator;
	}

	public boolean isFromResponseList() {
		return fromResponseList;
	}

	public void setFromResponseList(boolean fromResponseList) {
		this.fromResponseList = fromResponseList;
	}
	
}

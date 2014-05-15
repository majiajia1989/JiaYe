package com.e1858.wuye.pojo;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import com.e1858.wuye.common.CommonConstant;

public class NoticeCommand {
	private boolean sendStatus;
	private boolean auditStatus;
	private Date createTime;
	private String creator;

	// 以下是提交form表单的东西
	private long id = -1;
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	private String title;
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	private String content;
	
	private String pictureUrl="";
	private byte type;
	private String description="";


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(boolean sendStatus) {
		this.sendStatus = sendStatus;
	}

	public boolean isAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(boolean auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

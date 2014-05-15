package com.e1858.wuye.pojo;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import com.e1858.wuye.common.CommonConstant;

public class ArticleCommand {
	private long msgID = -1;
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	private String title;
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	private String description;
	@NotEmpty(message = CommonConstant.ValidMessage.NotBlank)
	@NotBlank(message = CommonConstant.ValidMessage.NotBlank)
	private String pictureUrl;
	@URL(message = CommonConstant.ValidMessage.URL)
	private String url;
	private String content;
	private String keyword;
	@Range(min = 0, max = 1)
	private byte matching;
	@Range(min = 0, max = 1)
	private int linkType;// 0.编辑图文内容 1.外链
	@Range(min = 0, max = 127, message = CommonConstant.ValidMessage.RANGE)
	private byte sort;

	private boolean defaultMsg = false;

	private boolean attentionMsg = false;

	private List<String> childIDs;
	private List<String> childTitles;

	public long getMsgID() {
		return msgID;
	}

	public void setMsgID(long msgID) {
		this.msgID = msgID;
	}

	public byte getSort() {
		return sort;
	}

	public void setSort(byte sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public byte getMatching() {
		return matching;
	}

	public void setMatching(byte matching) {
		this.matching = matching;
	}

	public int getLinkType() {
		return linkType;
	}

	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}

	public List<String> getChildIDs() {
		return childIDs;
	}

	public void setChildIDs(List<String> childIDs) {
		this.childIDs = childIDs;
	}

	public List<String> getChildTitles() {
		return childTitles;
	}

	public void setChildTitles(List<String> childTitles) {
		this.childTitles = childTitles;
	}

	public boolean isDefaultMsg() {
		return defaultMsg;
	}

	public void setDefaultMsg(boolean defaultMsg) {
		this.defaultMsg = defaultMsg;
	}

	public boolean isAttentionMsg() {
		return attentionMsg;
	}

	public void setAttentionMsg(boolean attentionMsg) {
		this.attentionMsg = attentionMsg;
	}

}

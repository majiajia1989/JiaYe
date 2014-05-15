package com.e1858.wuye.pojo;

public class MsgCommand {
	private String keyword;
	private String content;
	private long msgID;
	private long defaultMsgID;
	private long attentionMsgID;
	private int matching;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getMsgID() {
		return msgID;
	}
	public void setMsgID(long msgID) {
		this.msgID = msgID;
	}
	public long getDefaultMsgID() {
		return defaultMsgID;
	}
	public void setDefaultMsgID(long defaultMsgID) {
		this.defaultMsgID = defaultMsgID;
	}
	public int getMatching() {
		return matching;
	}
	public void setMatching(int matching) {
		this.matching = matching;
	}
	public long getAttentionMsgID()
	{
		return attentionMsgID;
	}
	public void setAttentionMsgID(long attentionMsgID)
	{
		this.attentionMsgID = attentionMsgID;
	}
	
}

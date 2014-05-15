package com.e1858.wuye.pojo;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import com.e1858.wuye.common.CommonConstant;
public class TextCommand {
	private boolean attentionMsg=false;
	private boolean defaultMsg=false;
	private String keyword="";
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotEmpty)
	private String content;
	
	private byte matching;
	
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


	public byte getMatching()
	{
		return matching;
	}

	public void setMatching(byte matching)
	{
		this.matching = matching;
	}

	public boolean isAttentionMsg()
	{
		return attentionMsg;
	}

	public void setAttentionMsg(boolean attentionMsg)
	{
		this.attentionMsg = attentionMsg;
	}

	public boolean isDefaultMsg()
	{
		return defaultMsg;
	}

	public void setDefaultMsg(boolean defaultMsg)
	{
		this.defaultMsg = defaultMsg;
	}
}

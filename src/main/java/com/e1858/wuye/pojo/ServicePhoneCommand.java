package com.e1858.wuye.pojo;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.e1858.wuye.common.CommonConstant;
public class ServicePhoneCommand {
	private long id = -1;
	private long corp;
	private long community;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotEmpty)
	private String title;
	private String description;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotEmpty)
	@Pattern(regexp="(^(0[0-9]{2,3})([2-9][0-9]{6,7})$)|(^(1[34578]\\d{9})$)",message=CommonConstant.ValidMessage.Mobile_TelePhone)
	private String phone;	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCorp() {
		return corp;
	}
	public void setCorp(long corp) {
		this.corp = corp;
	}
	public long getCommunity() {
		return community;
	}
	public void setCommunity(long community) {
		this.community = community;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}

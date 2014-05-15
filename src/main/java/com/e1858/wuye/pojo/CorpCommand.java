package com.e1858.wuye.pojo;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.e1858.wuye.common.CommonConstant;


public class CorpCommand {

	private long corp_id=-1;
	private long type=2;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	private String province;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	private String city;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	private String county;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	private String name;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	@Pattern(regexp="^1[3|4|5|8][0-9]\\d{8}$",message=CommonConstant.ValidMessage.MobilePhone)
	private String mobilephone;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	@Pattern(regexp="^(\\d{3,4})\\d{7,8}$",message=CommonConstant.ValidMessage.TelePhone)
	private String telephone;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	private String descript;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	private String address;
	@NotBlank(message=CommonConstant.ValidMessage.NotBlank)
	@NotEmpty(message=CommonConstant.ValidMessage.NotBlank)
	private String copyright;
	
	
	public long getCorp_id()
	{
		return corp_id;
	}
	public void setCorp_id(long corp_id)
	{
		this.corp_id = corp_id;
	}
	
	public long getType()
	{
		return type;
	}
	public void setType(long type)
	{
		this.type = type;
	}
	
	public String getProvince()
	{
		return province;
	}
	public void setProvince(String province)
	{
		this.province = province;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getCounty()
	{
		return county;
	}
	public void setCounty(String county)
	{
		this.county = county;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	
}

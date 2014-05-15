package com.e1858.wuye.entity.hibernate;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "t_Msg")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Msg extends BaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "corp")
	private SysCorp corp;

	@ManyToOne
	@JoinColumn(name = "community")
	private Community community;
	
	@Column(name = "msgType", length = 50)
	private String msgType;
	
	@Column(name = "title", length = 500)
	private String title;
	
	@Column(name = "description", length = 500)
	private String description;
	
	@Column(name = "picUrl", length = 500)
	private String picUrl;

	@Column(name = "url", length = 100)
	private String url;
	
	@Column(name = "content", length = 10000)
	private String content;
	
	@Column(name = "children", length = 200)
	private String children;

	@Column(name = "sort")
	private byte sort;	
	
	@ManyToOne
	@JoinColumn(name = "creator")
	private SysUser creator;
	
	@Column(name="createTime")
	private Date createTime;
	
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public SysCorp getCorp()
	{
		return corp;
	}

	public void setCorp(SysCorp corp)
	{
		this.corp = corp;
	}

	public String getMsgType()
	{
		return msgType;
	}

	public void setMsgType(String msgType)
	{
		this.msgType = msgType;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public SysUser getCreator()
	{
		return creator;
	}

	public void setCreator(SysUser creator)
	{
		this.creator = creator;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public byte getSort() {
		return sort;
	}

	public void setSort(byte sort) {
		this.sort = sort;
	}

	
}

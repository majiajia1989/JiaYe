package com.e1858.wuye.entity.hibernate;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "t_BbsTopic")
public class BbsTopic extends BaseEntity
{

	private Long id;
	private SysCorp sysCorp;
	private BbsBoard bbsBoard;
	private Subscriber subscriber;
	private Community community;
	private String title;
	private String content;
	private Date lastPost;
	private long views;
	private long replies;
	private long tops;
	private Date createTime;
	private Date cancelTime;
	private Long canceler;
	private Date disableTime;
	private Long disabler;
	private BbsMainPost bbsMainPost = new BbsMainPost();

	public BbsTopic()
	{
	}

	public BbsTopic(SysCorp sysCorp, BbsBoard bbsBoard, Subscriber subscriber, Community community, String title, String content, Date lastPost, long views, long replies, long tops, Date createTime)
	{
		this.sysCorp = sysCorp;
		this.bbsBoard = bbsBoard;
		this.subscriber = subscriber;
		this.community = community;
		this.title = title;
		this.content = content;
		this.lastPost = lastPost;
		this.views = views;
		this.replies = replies;
		this.tops = tops;
		this.createTime = createTime;
	}

	public BbsTopic(SysCorp sysCorp, BbsBoard bbsBoard, Subscriber subscriber, Community community, String title, String content, Date lastPost, long views, long replies, long tops, Date createTime, Date cancelTime, Long canceler, Date disableTime, Long disabler)
	{
		this.sysCorp = sysCorp;
		this.bbsBoard = bbsBoard;
		this.subscriber = subscriber;
		this.community = community;
		this.title = title;
		this.content = content;
		this.lastPost = lastPost;
		this.views = views;
		this.replies = replies;
		this.tops = tops;
		this.createTime = createTime;
		this.cancelTime = cancelTime;
		this.canceler = canceler;
		this.disableTime = disableTime;
		this.disabler = disabler;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "corp", nullable = false)
	public SysCorp getSysCorp()
	{
		return this.sysCorp;
	}

	public void setSysCorp(SysCorp sysCorp)
	{
		this.sysCorp = sysCorp;
	}

	@ManyToOne
	@JoinColumn(name = "board", nullable = false)
	public BbsBoard getBbsBoard()
	{
		return this.bbsBoard;
	}

	public void setBbsBoard(BbsBoard bbsBoard)
	{
		this.bbsBoard = bbsBoard;
	}

	@ManyToOne
	@JoinColumn(name = "creator", nullable = false)
	public Subscriber getSubscriber()
	{
		return this.subscriber;
	}

	public void setSubscriber(Subscriber subscriber)
	{
		this.subscriber = subscriber;
	}

	@ManyToOne
	@JoinColumn(name = "community", nullable = false)
	public Community getCommunity()
	{
		return this.community;
	}

	public void setCommunity(Community community)
	{
		this.community = community;
	}

	@Column(name = "title", nullable = false, length = 100)
	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	
	@Column(name = "content", nullable = false, length = 65535)
	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}


	@Temporal(TemporalType.DATE)
	@Column(name = "lastPost", nullable = false, length = 10)
	public Date getLastPost()
	{
		return this.lastPost;
	}


	public void setLastPost(Date lastPost)
	{
		this.lastPost = lastPost;
	}

	@Column(name = "views", nullable = false)
	public long getViews()
	{
		return this.views;
	}

	public void setViews(long views)
	{
		this.views = views;
	}

	@Column(name = "replies", nullable = false)
	public long getReplies()
	{
		return this.replies;
	}

	public void setReplies(long replies)
	{
		this.replies = replies;
	}

	@Column(name = "tops", nullable = false)
	public long getTops()
	{
		return this.tops;
	}

	public void setTops(long tops)
	{
		this.tops = tops;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createTime", nullable = false, length = 10)
	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cancelTime", length = 10)
	public Date getCancelTime()
	{
		return this.cancelTime;
	}

	public void setCancelTime(Date cancelTime)
	{
		this.cancelTime = cancelTime;
	}

	@Column(name = "canceler")
	public Long getCanceler()
	{
		return this.canceler;
	}

	public void setCanceler(Long canceler)
	{
		this.canceler = canceler;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "disableTime", length = 10)
	public Date getDisableTime()
	{
		return this.disableTime;
	}

	public void setDisableTime(Date disableTime)
	{
		this.disableTime = disableTime;
	}

	@Column(name = "disabler")
	public Long getDisabler()
	{
		return this.disabler;
	}

	public void setDisabler(Long disabler)
	{
		this.disabler = disabler;
	}
	
	
	@Transient
	public BbsMainPost getBbsMainPost()
	{
		return bbsMainPost;
	}

	public void setBbsMainPost(BbsMainPost bbsMainPost)
	{
		this.bbsMainPost = bbsMainPost;
	}

}

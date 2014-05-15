package com.e1858.wuye.entity.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "t_BbsPost")
public class BbsPost extends BaseEntity
{

	private Long id;
	private BbsTopic bbsTopic;
	private SysCorp sysCorp;
	private BbsBoard bbsBoard;
	private Subscriber subscriber;
	private Community community;
	private byte type;
	private String title;
	private String content;
	private Date createTime;
	private Date cancelTime;
	private Long canceler;
	private Date disableTime;
	private Long disabler;

	public BbsPost()
	{
		this.type = 2;
	}
	public BbsPost(byte type)
	{
		this.type = type;
	}

	public BbsPost(BbsTopic bbsTopic, SysCorp sysCorp, BbsBoard bbsBoard, Subscriber subscriber, Community community, byte type, String title, String content, Date createTime)
	{
		this.bbsTopic = bbsTopic;
		this.sysCorp = sysCorp;
		this.bbsBoard = bbsBoard;
		this.subscriber = subscriber;
		this.community = community;
		this.type = type;
		this.title = title;
		this.content = content;
		this.createTime = createTime;
	}

	public BbsPost(BbsTopic bbsTopic, SysCorp sysCorp, BbsBoard bbsBoard, Subscriber subscriber, Community community, byte type, String title, String content, Date createTime, Date cancelTime, Long canceler, Date disableTime, Long disabler)
	{
		this.bbsTopic = bbsTopic;
		this.sysCorp = sysCorp;
		this.bbsBoard = bbsBoard;
		this.subscriber = subscriber;
		this.community = community;
		this.type = type;
		this.title = title;
		this.content = content;
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
	@JoinColumn(name = "topic", nullable = false)
	public BbsTopic getBbsTopic()
	{
		return this.bbsTopic;
	}

	public void setBbsTopic(BbsTopic bbsTopic)
	{
		this.bbsTopic = bbsTopic;
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

	@Column(name = "type", nullable = false)
	public byte getType()
	{
		return this.type;
	}

	public void setType(byte type)
	{
		this.type = type;
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
		return this.content;
	}

	public void setContent(String content)
	{
		this.content = content;
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

}

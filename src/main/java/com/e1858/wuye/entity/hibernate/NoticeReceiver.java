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
@Table(name = "t_NoticeReceiver")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NoticeReceiver extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne
	@JoinColumn(name = "notice")
	private Notice notice;

	@ManyToOne
	@JoinColumn(name = "houseRoom")
	private HouseRoom houseRoom;

	@ManyToOne
	@JoinColumn(name = "openid")
	private Subscriber openid;

	@Column(name = "sendTime")
	private Date sendTime;

	@ManyToOne
	@JoinColumn(name = "sender")
	private SysUser sender;

	@Column(name = "readStatus")
	private byte readStatus;

	@Column(name = "readTime")
	private Date readTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public HouseRoom getHouseRoom() {
		return houseRoom;
	}

	public void setHouseRoom(HouseRoom houseRoom) {
		this.houseRoom = houseRoom;
	}

	public Subscriber getOpenid() {
		return openid;
	}

	public void setOpenid(Subscriber openid) {
		this.openid = openid;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public SysUser getSender() {
		return sender;
	}

	public void setSender(SysUser sender) {
		this.sender = sender;
	}

	public byte getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(byte readStatus) {
		this.readStatus = readStatus;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
}

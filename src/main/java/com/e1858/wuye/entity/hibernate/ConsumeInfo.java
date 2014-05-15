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
@Table (name="t_ConsumeInfo")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConsumeInfo extends BaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "corp")
	private SysCorp corp;
	
	@ManyToOne
	@JoinColumn(name="community")
	private Community community;
	
	@ManyToOne
	@JoinColumn(name = "house")
	private House house;
	
	@ManyToOne
	@JoinColumn(name="houseRoom")
	private HouseRoom houseRoom;
	
	@ManyToOne
	@JoinColumn(name = "consumeType")
	private SysConsumeType consumeType;
	
	@Column(name="payNumber",length=100)
	private String payNumber;
	
	@Column(name="year")
	private int year;
	
	@Column(name="month")
	private int month;
	
	@Column(name="postAmount")
	private double postAmount;
	
	@Column(name="currentAmount")
	private double currentAmount;
	
	@Column(name="amount")
	private double amount;
	
	@Column(name="price")
	private double price;
	
	@Column(name="payAmount")
	private double payAmount;	
	
	@Column(name="consumeTime")
	private Date consumeTime;	
	
	@Column(name="remark",length=200)
	private String remark;
	
	@Column(name="creator",length=200)
	private long creator;		
	
	@Column(name="createTime",length=200)
	private Date createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SysCorp getCorp() {
		return corp;
	}

	public void setCorp(SysCorp corp) {
		this.corp = corp;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public House getHouse()
	{
		return house;
	}

	public void setHouse(House house)
	{
		this.house = house;
	}

	public HouseRoom getHouseRoom() {
		return houseRoom;
	}

	public void setHouseRoom(HouseRoom houseRoom) {
		this.houseRoom = houseRoom;
	}

	public SysConsumeType getConsumeType() {
		return consumeType;
	}

	public void setConsumeType(SysConsumeType consumeType) {
		this.consumeType = consumeType;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getPostAmount() {
		return postAmount;
	}

	public void setPostAmount(double postAmount) {
		this.postAmount = postAmount;
	}

	public double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}	
}

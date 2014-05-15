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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "t_SubscriberHouse")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubscriberHouse extends BaseEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name="openid",unique = true, nullable = false, length = 32)
	private String openid;
	
	@ManyToOne
	@JoinColumn(name="corp")
	private SysCorp corp;
	
	@ManyToOne
	@JoinColumn(name="community")
	private Community community;
	
	@ManyToOne
	@JoinColumn(name="house")
	private House house;
	
	@ManyToOne
	@JoinColumn(name="houseUnit")
	private HouseUnit houseUnit;
	
	@ManyToOne
	@JoinColumn(name="houseFloor")
	private HouseFloor houseFloor;
	
	@OneToOne
	@JoinColumn(name="houseRoom")
	private HouseRoom houseRoom;
	
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

	public String getOpenid()
	{
		return openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid;
	}

	public SysCorp getCorp()
	{
		return corp;
	}

	public void setCorp(SysCorp corp)
	{
		this.corp = corp;
	}

	public Community getCommunity()
	{
		return community;
	}

	public void setCommunity(Community community)
	{
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

	public HouseUnit getHouseUnit()
	{
		return houseUnit;
	}

	public void setHouseUnit(HouseUnit houseUnit)
	{
		this.houseUnit = houseUnit;
	}

	public HouseFloor getHouseFloor()
	{
		return houseFloor;
	}

	public void setHouseFloor(HouseFloor houseFloor)
	{
		this.houseFloor = houseFloor;
	}

	public HouseRoom getHouseRoom()
	{
		return houseRoom;
	}

	public void setHouseRoom(HouseRoom houseRoom)
	{
		this.houseRoom = houseRoom;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	

}

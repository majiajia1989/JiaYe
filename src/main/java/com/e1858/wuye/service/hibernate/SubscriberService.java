package com.e1858.wuye.service.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.SubscriberDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.Subscriber;

/**
 * 用户管理服务器，负责查询用户、注册用户、锁定用户等操作
 * 
 */
@Service
@Transactional
public class SubscriberService {

	@Autowired
	private SubscriberDao subscriberDao;

	@Transactional(readOnly=true)
	public Subscriber querySubscriberByOpenId(String openId) {
		return subscriberDao.querySubscriberByOpenId(openId);
	}

//	public List<Subscriber> querySubscribersByCommunity(Community community) {
//		return subscriberDao.querySubscribersByCommunity(community);
//	}

	@Transactional(readOnly=true)
	public List<Subscriber> querySubscribersByCommunity(Community community, String houses, String floors,
			String units, Boolean hasCar, int offset, int count) {
		List<Long> houseList = new ArrayList<Long>();
		if (houses != null && houses.length() > 0) {
			String[] array = StringUtils.split(houses, ",");
			for (String s : array) {
				houseList.add(Long.parseLong(s));
			}
		}
		List<Long> floorList = new ArrayList<Long>();
		if (floors != null && floors.length() > 0) {
			String[] array = StringUtils.split(floors, ",");
			for (String s : array) {
				floorList.add(Long.parseLong(s));
			}
		}
		List<Long> unitList = new ArrayList<Long>();
		if (units != null && units.length() > 0) {
			String[] array = StringUtils.split(units, ",");
			for (String s : array) {
				unitList.add(Long.parseLong(s));
			}
		}
		return subscriberDao.querySubscribersByCommunity(community, houseList, floorList, unitList, hasCar, offset, count);
	}

	@Transactional(readOnly=true)
	public long subscribersCountByCommunity(Community community, String houses,
			String floors, String units, Boolean hasCar) {
		List<Long> houseList = new ArrayList<Long>();
		if (houses != null && houses.length() > 0) {
			String[] array = StringUtils.split(houses, ",");
			for (String s : array) {
				houseList.add(Long.parseLong(s));
			}
		}
		List<Long> floorList = new ArrayList<Long>();
		if (floors != null && floors.length() > 0) {
			String[] array = StringUtils.split(floors, ",");
			for (String s : array) {
				floorList.add(Long.parseLong(s));
			}
		}
		List<Long> unitList = new ArrayList<Long>();
		if (units != null && units.length() > 0) {
			String[] array = StringUtils.split(units, ",");
			for (String s : array) {
				unitList.add(Long.parseLong(s));
			}
		}
		return subscriberDao.subscribersCountByCommunity(community, houseList, floorList, unitList, hasCar);
	}
}

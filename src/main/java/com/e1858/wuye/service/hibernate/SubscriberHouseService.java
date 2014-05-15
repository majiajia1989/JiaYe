package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.SubscriberHouseDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.SubscriberHouse;

@Controller
@Transactional
public class SubscriberHouseService
{
	@Autowired
	private SubscriberHouseDao subscriberHouseDao;
	
	public List<SubscriberHouse> querySubscriberHourse(Community community,String house,String houseunit,Boolean car,Boolean mobilephone,String keyword,int index,int count){
		return subscriberHouseDao.querySubscriberHourse(community,house,houseunit,car,mobilephone,keyword,index,count);
	}
	
	public SubscriberHouse querySubscriberHourseByOpenID(String openID)
	{
		return subscriberHouseDao.querySubscriberHouseByOpenID(openID);
	}
	public boolean delete(String openid){
		return subscriberHouseDao.unbundSubscriber(openid);
	}
	public long getTotalCount(Community community, String house, String houseunit, Boolean car, Boolean mobilephone,String keyword){
		return subscriberHouseDao.getTotalCount(community, house, houseunit, car, mobilephone, keyword);
	}

}

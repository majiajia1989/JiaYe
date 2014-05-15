package com.e1858.wuye.dao.hibernate;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.SubscriberCar;
import com.e1858.wuye.entity.hibernate.SubscriberHouse;
import com.e1858.wuye.entity.hibernate.SubscriberPhone;

@Repository("subscriberHouseDao")
public class SubscriberHouseDao extends BaseDao<SubscriberHouse>
{
	private final String GET_SUBSCRIBERHOUSE =" from SubscriberHouse u,Weixin_UserInfo w,Subscriber s where u.community=? and u.openid=w.openid and u.openid=s.openid and s.type=0";
	
	private final String GET_SUBSCRIBERHOUSEBYOPENID = HQL_FROM + " u where u.openid=?";
	
	public long getTotalCount(Community community, String house, String houseunit, Boolean car, Boolean mobilephone,String keyword){
		return (Long)query(community, house, houseunit, car, mobilephone, keyword, true).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<SubscriberHouse> querySubscriberHourse(Community community, String house, String houseunit, Boolean car, Boolean mobilephone,String keyword,int index,int count)
	{
		return query(community, house, houseunit, car, mobilephone, keyword, false).setFirstResult(index).setMaxResults(count).list();
	}
	private Query query(Community community, String house, String houseunit, Boolean car, Boolean mobilephone,String keyword,Boolean isCount){
		String count_hql=isCount ? "select count(u.id) ":"select u ";
		String hql=count_hql+GET_SUBSCRIBERHOUSE;
		if(house!=null&&!(house.equals("0"))){
			hql+=" and u.house="+house;
		}
		if(houseunit!=null&&!(houseunit.equals("0"))){
			hql+=" and u.houseUnit="+houseunit;
		}
		if (car != null) {
			if (car.booleanValue()) {
				hql += " and u.openid in(select  openid from "+SubscriberCar.class.getSimpleName()+")";
			} else {
				hql += " and u.openid not in(select  openid from "+SubscriberCar.class.getSimpleName()+")";
			}
		}
		if (mobilephone != null) {
			if (mobilephone.booleanValue()) {
				hql +=" and u.openid in (select openid from " + SubscriberPhone.class.getSimpleName() + ")";
			} else {
				hql +=" and u.openid not in (select openid from " + SubscriberPhone.class.getSimpleName() + ")";
			}
		}
		if(!keyword.equals("")||keyword!=null){
			hql+=" and w.nickname like '%"+keyword+"%'";
		}
		 
		hql +="  order by u.id desc";
		Query query = createQuery(hql,community);
		return query;
	}
	public SubscriberHouse querySubscriberHouseByOpenID(String openID)
	{
		return get(GET_SUBSCRIBERHOUSEBYOPENID, openID);
	}
	
	public boolean unbundSubscriber(String openid){
		try{
			String sql = "{CALL p_unbundSubscriber(?)}";
			createSqlQuery(sql, openid).executeUpdate();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			CommonConstant.exceptionLogger.info(e);
			return false;
		}
		
		
	}
	
}

package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.Subscriber;
import com.e1858.wuye.entity.hibernate.SubscriberCar;
import com.e1858.wuye.entity.hibernate.SubscriberHouse;

/**
 * User对象Dao
 */
@Repository("subscriberDao")
public class SubscriberDao extends BaseDao<Subscriber> {
	private final String QUERY_BY_OPENID = HQL_FROM + " u where u.openid = ?";
	private final String QUERY_BY_COMMUNITY = HQL_FROM + " u where u.community = ?";

	public Subscriber querySubscriberByOpenId(String openId) {
		return get(QUERY_BY_OPENID, openId);
	}

//	public List<Subscriber> querySubscribersByCommunity(Community community) {
//		return find(QUERY_BY_COMMUNITY, community);
//	}

	@SuppressWarnings("unchecked")
	public List<Subscriber> querySubscribersByCommunity(Community community, List<Long> houseList,
			List<Long> floorList, List<Long> unitList, Boolean hasCar, int offset, int count) {
		return createQuery(community, houseList, floorList, unitList, hasCar, false).setFirstResult(offset).setMaxResults(count).list();
	}

	public long subscribersCountByCommunity(Community community,
			List<Long> houseList, List<Long> floorList, List<Long> unitList,
			Boolean hasCar) {
		return (Long) createQuery(community, houseList, floorList, unitList, hasCar, true).uniqueResult();
	}
	
	private Query createQuery(Community community,
			List<Long> houseList, List<Long> floorList, List<Long> unitList,
			Boolean hasCar, boolean isCount) {
		String prefix = isCount ? "SELECT COUNT(u.id) " : "";
		String carHql = "select sc.openid from " + SubscriberCar.class.getSimpleName() + " sc";
		if (houseList.size() == 0 && floorList.size() == 0 && unitList.size() == 0) {
			List<Object> params = new ArrayList<Object>();
			String hql = prefix + QUERY_BY_COMMUNITY;
			params.add(community);
			if (hasCar != null) {
				if (hasCar.booleanValue()) {
					hql += " and u.openid in (" + carHql + ")";
				} else {
					hql += " and u.openid not in (" + carHql + ")";
				}
			}
			return createQuery(hql,params);
		} else {
			String openidHql = "select sh.openid from " + SubscriberHouse.class.getSimpleName() + " sh";
			openidHql += " where sh.community=:sh_community";// SubscriberHouse
			if (houseList.size() > 0) {
				openidHql += (" and sh.house.id in (:sh_house)");
			}
			if (unitList.size() > 0) {
				openidHql += (" and sh.houseUnit.id in (:sh_houseUnit)");
			}
			if (floorList.size() > 0) {
				openidHql += (" and sh.houseFloor.id in (:sh_houseFloor)");
			}
			if (hasCar != null) {
				if (hasCar.booleanValue()) {
					openidHql += " and sh.openid in (" + carHql + ")";
				} else {
					openidHql += " and sh.openid not in (" + carHql + ")";
				}
			}
			String hql = prefix + HQL_FROM + " u where u.openid in (" + openidHql + ")";
			Query query = createQuery(hql);
			query.setParameter("sh_community", community);
			if (houseList.size() > 0) {
				query.setParameterList("sh_house", houseList.toArray());
			}
			if (unitList.size() > 0) {
				query.setParameterList("sh_houseUnit", unitList.toArray());
			}
			if (floorList.size() > 0) {
				query.setParameterList("sh_houseFloor", floorList.toArray());
			}
			return query;
		}
	}
}

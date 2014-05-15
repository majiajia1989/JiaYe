package com.e1858.wuye.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Commission;
import com.e1858.wuye.entity.hibernate.CommissionResponse;
import com.e1858.wuye.entity.hibernate.CommissionType;
import com.e1858.wuye.entity.hibernate.Community;
@Repository("commissionDao")
public class CommissionDao extends BaseDao<Commission>
{
	
	public  final String GET_COMMISSION_BYID=HQL_FROM+" u where u.id=?";
	public  final String GET_COMMISSIONBYTYPE=HQL_FROM+" u where u.community=? and u.type=?";
	public long getTotalCount(Community community,CommissionType type, String keyword, Date begin, Date end,String response){
		 return  (Long) queryCommissins(community,type, keyword, begin, end,response,true).uniqueResult();
	}

	public Commission getCommissionByID(long id){
		return get(GET_COMMISSION_BYID, id);
	}
	@SuppressWarnings("unchecked")
	public List<Commission> getCommissionByTypeAndDate(Community community,
			CommissionType type, String keyword, Date begin, Date end,int index,int count,String response,Boolean isCount) {
		return queryCommissins(community, type, keyword, begin, end, response, isCount).setFirstResult(index).setMaxResults(count).list();
	}
	
	private Query queryCommissins(Community community,
			CommissionType type, String keyword, Date begin, Date end,String response,Boolean isCount)
	{
		String count_hql=isCount ? " select count(u.id) ":"";
		String hql = count_hql+HQL_FROM+" u where  u.community=? ";
		if(type!=null){
			hql += " and u.type=?";
		}
		if (begin != null) {
			hql += " and u.createTime>=:begin";
		}
		if (end != null) {
			hql += " and u.createTime<=:end";
		}
		if (keyword != null) {
			hql+=" and u.content like '%"+keyword+"%'";
		}
		if(response.equals("1")){
			hql+=" and u.id  in (select  commission from "+CommissionResponse.class.getSimpleName()+")";
		}else if(response.equals("2")){
			hql+=" and u.id not in (select  commission from "+CommissionResponse.class.getSimpleName()+")";
		}
		hql += " order by u.id desc";
		Query query;
		if(type!=null){
			query= createQuery(hql,community,type);
			if (begin != null) {
				query.setDate("begin", begin);
			}
			if (end != null) {
				query.setDate("end", end);
			}
		}else{
			query = createQuery(hql,community);
			if (begin != null) {
				query.setDate("begin", begin);
			}
			if (end != null) {
				query.setDate("end", end);
			}
		}
		return query;
	}
	
	
	public List<Commission> getCommissionByType(Community community, CommissionType type)
	{
		return find(GET_COMMISSIONBYTYPE, community,type);
	}
	
	
}

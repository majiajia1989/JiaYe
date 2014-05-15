package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.pojo.DataTableRowCnt;
/**
 * SysCorp对象Dao
 * @author hnhx
 *
 */
@Repository("CommunityDao")
public class CommunityDao extends BaseDao<Community>{
	private final String GET_COMMUNITY_BY_ID = "from Community u where u.id=?";
	private final String GET_COMMUNITY_BY_CORP = "from Community u where u.corp=?";
	private final String GET_COMMUNITY_BY_CORP_AND_NAME = "from Community u where u.corp=? and u.name=?";
	public List<Community> queryCommunitysByCorp(SysCorp corp){
		return (List<Community>)find(GET_COMMUNITY_BY_CORP,corp);
	}
	
	public List<Community> queryCommunitysByCorpWithPage(SysCorp corp,int startRow, int pageSize, DataTableRowCnt rowCnt){
		List<Object> param = new ArrayList<Object>();
		String sql="select u from Community u where u.corp=? ";
		String sqlCount=" select count(*) from Community u where u.corp=? ";
		param.add(corp);
		
		rowCnt.setRowCnt(count(sqlCount,param));
		return findWithPage(sql,startRow,pageSize,param);
	}

	public Community queryCommunityById(long id){
		return get(GET_COMMUNITY_BY_ID,id);
	}
	
	public Community queryCommunitysByCorpAndName(SysCorp corp,String name){
		return get(GET_COMMUNITY_BY_CORP_AND_NAME,corp,name);
	}
}

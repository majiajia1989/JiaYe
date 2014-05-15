package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.pojo.DataTableRowCnt;
import com.e1858.wuye.pojo.ProcResult;
/**
 * SysCorp对象Dao
 * @author hnhx
 *
 */
@Repository("HouseDao")
public class HouseDao extends BaseDao<House>{
	private final String GET_CORPS_BY_COMMUNITY = "from House u where u.community=? order by u.createTime desc";
	private final String GET_CORPS_BY_COMMUNITY_AND_NAME = "from House u where u.community=? and u.name=?";
	public List<House> queryHouseByCommunity(Community community){
		return (List<House>)find(GET_CORPS_BY_COMMUNITY,community);
	}
	
	public List<House> queryHouseByCommunityWithPage(Community community,int startRow,int pageSize,DataTableRowCnt rowCnt ){

		List<Object> param = new ArrayList<Object>();
		String sql="select u from House u where u.community=? ";
		String sqlCount=" select count(*) from House u where u.community=? ";
		param.add(community);
		
		rowCnt.setRowCnt(count(sqlCount,param));
		return findWithPage(sql,startRow,pageSize,param);
	}

	public House queryHouseByCommunityAndName(Community community,String name){
		List<House> houses = (List<House>)find(GET_CORPS_BY_COMMUNITY_AND_NAME,community,name);
		if (houses.size() == 0) {
			return null;
		}else{
			return houses.get(0);
		}
	}
	
	public int addHouseFromList(List<House> houses)
	{
		StringBuilder sql = new StringBuilder("");
		if(houses.size()<=0)
		{
			return 0;
		}
		int i=1;
		for(House house:houses)
		{
			sql.append("(");
			sql.append(house.getCorp().getId()).append(",");
			sql.append(house.getCommunity().getId()).append(",");
			sql.append("'").append(house.getName()).append("'").append(",");
			sql.append("'").append(house.getDescription()).append("'").append(",");
			sql.append(house.getCreator().getId()).append(",");			
			sql.append("sysdate()");			
			sql.append(")").append(i<houses.size()?",":"");
			i++;
		}
		return createSqlQuery("insert into t_House(corp,community,name,description,creator,createTime) values "+sql.toString()+";").executeUpdate();
	}
}

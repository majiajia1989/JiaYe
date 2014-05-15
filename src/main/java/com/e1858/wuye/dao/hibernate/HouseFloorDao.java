package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseFloor;
import com.e1858.wuye.entity.hibernate.HouseRoom;
import com.e1858.wuye.entity.hibernate.HouseUnit;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.DataTableRowCnt;
/**
 * SysCorp对象Dao
 * @author hnhx
 *
 */
@Repository("HouseFloorDao")
public class HouseFloorDao extends BaseDao<HouseFloor>{
	private final String GET_HOUSEFLOOR_BY_COMMUNITY = "from HouseFloor u where u.community=?";
	private final String GET_HOUSEFLOOR_BY_COMMUNITY_AND_NAME = "from HouseFloor u where u.community=? and u.name=?";
	public List<HouseFloor> queryHouseFloorByCommunity(Community community){
		return (List<HouseFloor>)find(GET_HOUSEFLOOR_BY_COMMUNITY,community);
	}
	
	public List<HouseFloor> queryHouseFloorByCommunityWithPage(Community community,int startRow, int pageSize, DataTableRowCnt rowCnt){
		List<Object> param = new ArrayList<Object>();
		String sql="select u from HouseFloor u where u.community=? ";
		String sqlCount=" select count(*) from HouseFloor u where u.community=? ";
		param.add(community);
		
		rowCnt.setRowCnt(count(sqlCount,param));
		return findWithPage(sql,startRow,pageSize,param);
	}

	public HouseFloor queryHouseFloorsByCommunityAndName(Community community,String name){
		List<HouseFloor> houseFloors= (List<HouseFloor>)find(GET_HOUSEFLOOR_BY_COMMUNITY_AND_NAME,community,name);
		if(houseFloors.size()==0)
		{
			return null;
		}
		else
		{
			return houseFloors.get(0);
		}
	}
	
}
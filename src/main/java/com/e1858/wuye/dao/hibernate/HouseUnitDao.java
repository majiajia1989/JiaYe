package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseUnit;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.DataTableRowCnt;
/**
 * SysCorp对象Dao
 * @author hnhx
 *
 */
@Repository("HouseUnitDao")
public class HouseUnitDao extends BaseDao<HouseUnit>{
	private final String GET_HOUSEUNITS_BY_COMMUNITY = "from HouseUnit u where u.community=?";
	private final String GET_HOUSEUNIT_BY_Community_AND_NAME = "from HouseUnit u where u.community=? and u.name=?";
	public List<HouseUnit> queryHouseUnitByCommunity(Community community){
		return (List<HouseUnit>)find(GET_HOUSEUNITS_BY_COMMUNITY,community);
	}
	
	public List<HouseUnit> queryHouseUnitByCommunityWithPage(Community community,int startRow, int pageSize, DataTableRowCnt rowCnt){
		List<Object> param = new ArrayList<Object>();
		String sql="select u from HouseUnit u where u.community=? ";
		String sqlCount=" select count(*) from HouseUnit u where u.community=? ";
		param.add(community);
		
		rowCnt.setRowCnt(count(sqlCount,param));
		return findWithPage(sql,startRow,pageSize,param);
	}
	
	public HouseUnit queryHouseUnitByCommunityAndName(Community community,String name){
		List<HouseUnit> houseUnits = (List<HouseUnit>)find(GET_HOUSEUNIT_BY_Community_AND_NAME,community,name);
		if(houseUnits.size()==0)
		{
			return null;
		}
		else
		{
			return houseUnits.get(0);
		}
	}
	
}

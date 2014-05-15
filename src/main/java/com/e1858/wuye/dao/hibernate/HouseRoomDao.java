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
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.DataTableRowCnt;

/**
 * SysCorp对象Dao
 * 
 * @author hnhx
 * 
 */

@Repository("HouseRoomDao")
public class HouseRoomDao extends BaseDao<HouseRoom>
{
	private final String GET_HOUSEROOMS_BY_COMMUNITY = "from HouseRoom u where u.community=?";
	private String GET_HOUSEROOMS_COUNT_BY_COMMUNITY="select count(*) from HouseRoom u where u.community=?";
	private final String GET_HOUSEROOM_BY_COMMUNITY_AND_NAME = "from HouseRoom u where u.community=? and u.name=?";

	public List<HouseRoom> queryHouseRoomByCommunity(Community community)
	{
		return (List<HouseRoom>) find(GET_HOUSEROOMS_BY_COMMUNITY, community);
	}
	public List<HouseRoom> queryHouseRoomByCommunity(Community community,int startRow,int pageSize,DataTableRowCnt rowCnt)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(community);
		rowCnt.setRowCnt(count(GET_HOUSEROOMS_COUNT_BY_COMMUNITY,params));
		return findWithPage(GET_HOUSEROOMS_BY_COMMUNITY,startRow,pageSize,params);
	}
	
	public HouseRoom queryHouseRoomByCommunityAndName(Community community, String name)
	{

		List<HouseRoom> houseRooms = (List<HouseRoom>) find(GET_HOUSEROOM_BY_COMMUNITY_AND_NAME, community, name);
		if (houseRooms.size() == 0)
		{
			return null;
		}
		else
		{
			return houseRooms.get(0);
		}
	}

	public int addHouseRoomFromList(List<HouseRoom> houseRooms)
	{
		StringBuilder sql = new StringBuilder("");
		if (houseRooms.size() <= 0)
		{
			return 0;
		}
		int i = 1;
		for (HouseRoom houseRoom : houseRooms)
		{
			sql.append("(");
			sql.append(houseRoom.getCorp().getId()).append(",");
			sql.append(houseRoom.getCommunity().getId()).append(",");
			sql.append(houseRoom.getHouse()).append(",");
			sql.append("'").append(houseRoom.getName()).append("'").append(",");
			sql.append("'").append(houseRoom.getDescription()).append("'").append(",");
			sql.append(houseRoom.getCreator().getId()).append(",");
			sql.append("sysdate()");
			sql.append(")");
			if (i % 100 == 0 || i == houseRooms.size())
			{
				createSqlQuery("insert into t_HouseRoom(corp,community,house,name,description,creator,createTime) values " + sql.toString() + ";").executeUpdate();
				sql.setLength(0);
			}
			else
			{
				sql.append(",");
			}
			if (i < houseRooms.size())
			{
				
				i++;
			}
		}
		return i;
	}
}

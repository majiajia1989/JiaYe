package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.CorpDao;
import com.e1858.wuye.dao.hibernate.HouseDao;
import com.e1858.wuye.dao.hibernate.HouseFloorDao;
import com.e1858.wuye.dao.hibernate.HouseRoomDao;
import com.e1858.wuye.dao.hibernate.HouseUnitDao;
import com.e1858.wuye.dao.hibernate.SysUserDao;
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
 * Corp 插入和更新
 * 
 * @author hnhx
 * 
 */
@Service
@Transactional
public class HouseRoomService
{

	@Autowired
	private HouseRoomDao houseRoomDao;


	public void save(HouseRoom houseRoom)
	{
		if(queryHouseRoomByCommunityAndName(houseRoom.getCommunity(),houseRoom.getName())==null)
		{
			houseRoomDao.save(houseRoom);
		}
	}

	public void update(HouseRoom houseRoom)
	{
		houseRoomDao.update(houseRoom);
	}

	public void delete(long houseRoomId)
	{
		HouseRoom houseRoom = this.queryHouseRoomById(houseRoomId);
		if (houseRoom == null)
		{
			return;
		}
		houseRoomDao.delete(houseRoom);
	}

	@Transactional(readOnly = true)
	public HouseRoom queryHouseRoomById(long houseRoomId)
	{
		return houseRoomDao.get(houseRoomId);
	}

	@Transactional(readOnly = true)
	public List<HouseRoom> queryHouseRoomByCommunity(Community community)
	{
		return houseRoomDao.queryHouseRoomByCommunity(community);
	}
	@Transactional(readOnly = true)
	public List<HouseRoom> queryHouseRoomByCommunity(Community community,int startRow,int pageSize,DataTableRowCnt rowCnt)
	{
		return houseRoomDao.queryHouseRoomByCommunity(community,startRow,pageSize,rowCnt);
	}

	@Transactional(readOnly = true)
	public HouseRoom queryHouseRoomByCommunityAndName(Community community, String name)
	{
		return houseRoomDao.queryHouseRoomByCommunityAndName(community, name);
	}

}

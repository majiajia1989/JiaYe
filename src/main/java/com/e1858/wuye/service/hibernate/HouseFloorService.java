package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.CorpDao;
import com.e1858.wuye.dao.hibernate.HouseDao;
import com.e1858.wuye.dao.hibernate.HouseFloorDao;
import com.e1858.wuye.dao.hibernate.HouseUnitDao;
import com.e1858.wuye.dao.hibernate.SysUserDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseFloor;
import com.e1858.wuye.entity.hibernate.HouseRoom;
import com.e1858.wuye.entity.hibernate.HouseUnit;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.DataTableRowCnt;

/**
 * Corp 插入和更新
 * 
 * @author hnhx
 * 
 */
@Service
@Transactional
public class HouseFloorService
{

	@Autowired
	private HouseFloorDao houseFloorDao;

	public void save(HouseFloor houseFloor)
	{
		if (queryHouseFloorsByCommunityAndName(houseFloor.getCommunity(), houseFloor.getName()) == null)
		{
			houseFloorDao.save(houseFloor);
		}
	}

	public void update(HouseFloor houseFloor)
	{
		houseFloorDao.update(houseFloor);
	}

	public void delete(long houseFloorId)
	{
		HouseFloor houseFloor = this.queryHouseFloorById(houseFloorId);
		if (houseFloor == null)
		{
			return;
		}
		houseFloorDao.delete(houseFloor);
	}

	@Transactional(readOnly = true)
	public HouseFloor queryHouseFloorById(long houseFloorId)
	{
		return houseFloorDao.get(houseFloorId);
	}

	@Transactional(readOnly = true)
	public List<HouseFloor> queryHouseFloorByCommunity(Community community)
	{
		return houseFloorDao.queryHouseFloorByCommunity(community);
	}

	@Transactional(readOnly = true)
	public List<HouseFloor> queryHouseFloorByCommunity(Community community,int startRow, int pageSize, DataTableRowCnt rowCnt)
	{
		return houseFloorDao.queryHouseFloorByCommunityWithPage(community,startRow,pageSize,rowCnt);
	}
	@Transactional(readOnly = true)
	
	public HouseFloor queryHouseFloorsByCommunityAndName(Community community, String name)
	{
		return houseFloorDao.queryHouseFloorsByCommunityAndName(community, name);
	}

}

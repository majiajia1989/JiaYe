package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.CorpDao;
import com.e1858.wuye.dao.hibernate.HouseDao;
import com.e1858.wuye.dao.hibernate.HouseUnitDao;
import com.e1858.wuye.dao.hibernate.SysUserDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseFloor;
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
public class HouseUnitService
{

	@Autowired
	private HouseUnitDao houseUnitDao;

	public void save(HouseUnit houseUnit)
	{
		if (queryHouseUnitByCommunityAndName(houseUnit.getCommunity(),houseUnit.getName()) == null)
		{
			houseUnitDao.save(houseUnit);
		}
	}

	public void update(HouseUnit houseUnit)
	{
		houseUnitDao.update(houseUnit);
	}

	public void delete(long houseUnitId)
	{
		HouseUnit houseUnit = this.queryHouseUnitById(houseUnitId);
		if (houseUnit == null)
		{
			return;
		}
		houseUnitDao.delete(houseUnit);
	}

	@Transactional(readOnly = true)
	public HouseUnit queryHouseUnitById(long houseUnitId)
	{
		return houseUnitDao.get(houseUnitId);
	}

	@Transactional(readOnly = true)
	public List<HouseUnit> queryHouseUnitByCommunity(Community community)
	{
		return houseUnitDao.queryHouseUnitByCommunity(community);
	}

	@Transactional(readOnly = true)
	public List<HouseUnit> queryHouseUnitByCommunity(Community community,int startRow, int pageSize, DataTableRowCnt rowCnt)
	{
		return houseUnitDao.queryHouseUnitByCommunityWithPage(community,startRow,pageSize,rowCnt);
	}

	@Transactional(readOnly = true)
	public HouseUnit queryHouseUnitByCommunityAndName(Community community ,String name)
	{
		return houseUnitDao.queryHouseUnitByCommunityAndName(community,name);
	}

}

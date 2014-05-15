package com.e1858.wuye.service.hibernate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.dao.hibernate.HouseDao;
import com.e1858.wuye.dao.hibernate.HouseFloorDao;
import com.e1858.wuye.dao.hibernate.HouseRoomDao;
import com.e1858.wuye.dao.hibernate.HouseUnitDao;
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
public class HouseService {

	@Autowired
	private HouseDao houseDao;
	@Autowired
	private HouseUnitDao houseUnitDao;
	@Autowired
	private HouseFloorDao houseFloorDao;
	@Autowired
	private HouseRoomDao houseRoomDao;


	public void save(House house) {
		houseDao.save(house);
	}

	public void save(List<House> houses) {
		for (House house : houses) {
			if (queryHouseByCommunityAndName(house.getCommunity(), house.getName()) != null) {
				continue;
			}
			houseDao.save(house);
		}
	}

	public void update(House house) {
		houseDao.update(house);
	}

	public void delete(House house) {
		houseDao.delete(house);
	}

	public void delete(long houseId) {
		House house = this.queryHouseById(houseId);
		if (house == null) {
			return;
		}
		houseDao.delete(house);
	}

	public void importFromExcel(String srcFile, SysUser user, SysCorp corp, Community community) {
		// 读取文件
		int colIndex_House = -1;
		int colIndex_HouseUnit = -1;
		int colIndex_HouseFloor = -1;
		int colIndex_HouseRoom = -1;

		Workbook book;
		try {
			book = Workbook.getWorkbook(new File(srcFile));
			Sheet sheet = book.getSheet(0);

			if (sheet != null) {
				Cell cellFindHeadResult;
				cellFindHeadResult = sheet.findCell("楼号", 0, 0, sheet.getColumns(), sheet.getRows(), true);
				if (cellFindHeadResult != null) {
					colIndex_House = cellFindHeadResult.getColumn();
				}
				cellFindHeadResult = sheet.findCell("单元号", 0, 0, sheet.getColumns(), sheet.getRows(), true);
				if (cellFindHeadResult != null) {
					colIndex_HouseUnit = cellFindHeadResult.getColumn();
				}
				cellFindHeadResult = sheet.findCell("楼层号", 0, 0, sheet.getColumns(), sheet.getRows(), true);
				if (cellFindHeadResult != null) {
					colIndex_HouseFloor = cellFindHeadResult.getColumn();
				}
				cellFindHeadResult = sheet.findCell("门牌号", 0, 0, sheet.getColumns(), sheet.getRows(), true);
				if (cellFindHeadResult != null) {
					colIndex_HouseRoom = cellFindHeadResult.getColumn();
				}

				List<House> houses = queryHouseByCommunity(community);
				List<HouseUnit> houseUnits = houseUnitDao.queryHouseUnitByCommunity(community);
				List<HouseFloor> houseFloors = houseFloorDao.queryHouseFloorByCommunity(community);
				List<HouseRoom> houseRooms = houseRoomDao.queryHouseRoomByCommunity(community);

				List<House> newHouses = new ArrayList<House>();
				List<HouseUnit> newHouseUnits = new ArrayList<HouseUnit>();
				List<HouseFloor> newHouseFloors = new ArrayList<HouseFloor>();
				List<HouseRoom> newHouseRooms = new ArrayList<HouseRoom>();

				Date currDate = new Date();
				if (colIndex_House >= 0 && colIndex_HouseUnit >= 0 && colIndex_HouseFloor >= 0
						&& colIndex_HouseRoom >= 0) {
					for (int i = 1; i < sheet.getRows(); i++) {
						String houseName = sheet.getCell(colIndex_House, i).getContents();
						String houseUnitName = sheet.getCell(colIndex_HouseUnit, i).getContents();
						String houseFloorName = sheet.getCell(colIndex_HouseFloor, i).getContents();
						String houseRoomName = sheet.getCell(colIndex_HouseRoom, i).getContents();
						House house = GetHouseFromListByName(houses, houseName);
						if (house == null) {
							house = new House();
							house.setName(houseName);
							house.setCorp(corp);
							house.setCommunity(community);
							house.setCreator(user);
							house.setCreateTime(currDate);
							house.setDescription("");
							newHouses.add(house);
							houses.add(house);
						}

						if (GetHouseUnitFromListByName(houseUnits, houseUnitName) == null) {
							HouseUnit houseUnit = new HouseUnit();
							houseUnit.setCorp(corp);
							houseUnit.setCommunity(community);
							houseUnit.setHouse(-1);
							houseUnit.setCreator(user);
							houseUnit.setCreateTime(currDate);
							houseUnit.setDescription("");
							houseUnit.setName(houseUnitName);
							newHouseUnits.add(houseUnit);
							houseUnits.add(houseUnit);
						}
						if (GetHouseFloorFromListByName(houseFloors, houseFloorName) == null) {
							HouseFloor houseFloor = new HouseFloor();
							houseFloor.setCorp(corp);
							houseFloor.setCommunity(community);
							houseFloor.setHouse(-1);
							houseFloor.setCreator(user);
							houseFloor.setCreateTime(currDate);
							houseFloor.setDescription("");
							houseFloor.setName(houseFloorName);
							newHouseFloors.add(houseFloor);
							houseFloors.add(houseFloor);
						}

						if (GetHouseRoomFromListByName(houseRooms, houseRoomName) == null) {
							HouseRoom houseRoom = new HouseRoom();
							houseRoom.setCorp(corp);
							houseRoom.setCommunity(community);
							houseRoom.setHouse(-1);
							houseRoom.setCreator(user);
							houseRoom.setCreateTime(currDate);
							houseRoom.setDescription("");
							houseRoom.setName(houseRoomName);
							newHouseRooms.add(houseRoom);
							houseRooms.add(houseRoom);
						}
					}
				}
				houseDao.addHouseFromList(newHouses);
				/*for(House house:newHouses)
				{
					houseDao.save(house);
				}*/
				for(HouseUnit houseUnit:newHouseUnits)
				{
					houseUnitDao.save(houseUnit);					
				}
				for(HouseFloor houseFloor:newHouseFloors)
				{
					houseFloorDao.save(houseFloor);
				}
				houseRoomDao.addHouseRoomFromList(newHouseRooms);
				/*for(HouseRoom houseRoom:newHouseRooms)
				{
					houseRoomDao.save(houseRoom);
				}*/
			}
		} catch (BiffException e) {
			CommonConstant.exceptionLogger.info(e);
			try
			{
				throw e;
			}
			catch (BiffException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			CommonConstant.exceptionLogger.info(e);
			try
			{
				throw e;
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@Transactional(readOnly = true)
	public House queryHouseById(long houseId) {
		return houseDao.get(houseId);
	}

	@Transactional(readOnly = true)
	public List<House> queryHouseByCommunity(Community community) {
		return houseDao.queryHouseByCommunity(community);
	}

	@Transactional(readOnly = true)
	public List<House> queryHouseByCommunity(Community community,int startRow, int pageSize, DataTableRowCnt rowCnt) {
		return houseDao.queryHouseByCommunityWithPage(community,startRow,pageSize,rowCnt);
	}

	@Transactional(readOnly = true)
	public House queryHouseByCommunityAndName(Community community, String name) {
		return houseDao.queryHouseByCommunityAndName(community, name);
	}

	@Transactional(readOnly = true)
	public House GetHouseFromListByName(List<House> houses, String searchName) {
		House result = null;
		for (House house : houses) {
			if (searchName.equals(house.getName())) {
				result = house;
				break;
			}
		}
		return result;
	}

	@Transactional(readOnly = true)
	public HouseUnit GetHouseUnitFromListByName(List<HouseUnit> housesUnits, String searchName) {
		HouseUnit result = null;
		for (HouseUnit houseUnit : housesUnits) {
			if (searchName.equals(houseUnit.getName())) {
				result = houseUnit;
				break;
			}
		}
		return result;
	}

	@Transactional(readOnly = true)
	public HouseRoom GetHouseRoomFromListByName(List<HouseRoom> housesRooms, String searchName) {
		HouseRoom result = null;
		for (HouseRoom houseRoom : housesRooms) {
			if (searchName.equals(houseRoom.getName())) {
				result = houseRoom;
				break;
			}
		}
		return result;
	}

	@Transactional(readOnly = true)
	public HouseFloor GetHouseFloorFromListByName(List<HouseFloor> housesFloors, String searchName) {
		HouseFloor result = null;
		for (HouseFloor houseFloor : housesFloors) {
			if (searchName.equals(houseFloor.getName())) {
				result = houseFloor;
				break;
			}
		}
		return result;
	}

	@Transactional(readOnly = true)
	public List<HouseFloor> queryHouseFloorByCommunity(Community community) {
		return houseFloorDao.queryHouseFloorByCommunity(community);
	}

	@Transactional(readOnly = true)
	public List<HouseUnit> queryHouseUnitByCommunity(Community community) {
		return houseUnitDao.queryHouseUnitByCommunity(community);
	}
}

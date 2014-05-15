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

import com.e1858.wuye.utils.ThreadPool;
import com.e1858.wuye.common.CommonConstant.ConsumeType;
import com.e1858.wuye.dao.hibernate.ConsumeInfoDao;
import com.e1858.wuye.dao.hibernate.ConsumeTemplateDao;
import com.e1858.wuye.dao.hibernate.ConsumeTypeDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.ConsumeInfo;
import com.e1858.wuye.entity.hibernate.ConsumeTemplate;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseFloor;
import com.e1858.wuye.entity.hibernate.HouseRoom;
import com.e1858.wuye.entity.hibernate.HouseUnit;
import com.e1858.wuye.entity.hibernate.SysConsumeType;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.exception.UserExistException;
import com.e1858.wuye.pojo.DataTableRowCnt;

@Service
@Transactional
public class ConsumeInfoService
{

	@Autowired
	private ConsumeInfoDao consumeInfoDao;
	@Autowired
	private HouseService houseService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private HouseUnitService houseUnitService;
	@Autowired
	private HouseFloorService houseFloorService;
	@Autowired
	private HouseRoomService houseRoomService;
	@Autowired
	private ConsumeTemplateDao consumeTemplateDao;
	@Autowired
	private ConsumeTypeService consumeTypeService;
	@Autowired
	private ConsumeTypeDao consumeTypeDao;

	@Transactional(readOnly = true)
	public ConsumeInfo getConsumeInfos(String openid, long consumeTypeID)
	{
		return consumeInfoDao.getConsumeInfos(openid, consumeTypeID);
	}

	public void importFromExcel(String srcFile, SysUser user, int year, int month, long communityId, long consumeTypeId) throws Exception
	{
		SysConsumeType sysConsumeType = consumeTypeService.queryConsumeTypeById(consumeTypeId);
		Community community = communityService.queryCommunityById(communityId);
		// 读取文件
		int colIndex_House = -1;
		int colIndex_HouseRoom = -1;
		int colIndex_PayNumber = -1;
		int colIndex_PostAmount = -1;// 上期表数
		int colIndex_CurrentAmount = -1;// 本期表数
		int colIndex_Amount = -1;// 本期用量
		int colIndex_CurrentPayAmount=-1; //缴费金额
		Workbook book = null;
		try
		{
			book = Workbook.getWorkbook(new File(srcFile));
		}
		catch (BiffException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Sheet sheet = book.getSheet(0);

		if (sheet != null)
		{
			Cell cellFindHeadResult;
			cellFindHeadResult = sheet.findCell("楼号", 0, 0, sheet.getColumns(), sheet.getRows(), true);
			if (cellFindHeadResult != null)
			{
				colIndex_House = cellFindHeadResult.getColumn();
			}
			else
			{
				throw new Exception("必须存在楼号列，请检查文档！");
			}
			cellFindHeadResult = sheet.findCell("门牌号", 0, 0, sheet.getColumns(), sheet.getRows(), true);
			if (cellFindHeadResult != null)
			{
				colIndex_HouseRoom = cellFindHeadResult.getColumn();
			}
			else
			{
				throw new Exception("必须存在门牌号列，请检查文档！");
			}
			cellFindHeadResult = sheet.findCell("户号", 0, 0, sheet.getColumns(), sheet.getRows(), true);
			if (cellFindHeadResult != null)
			{
				colIndex_PayNumber = cellFindHeadResult.getColumn();
			}
			else
			{
				throw new Exception("必须存在户号列，请检查文档！");
			}
			cellFindHeadResult = sheet.findCell("本期用量", 0, 0, sheet.getColumns(), sheet.getRows(), true);
			if (cellFindHeadResult != null)
			{
				colIndex_Amount = cellFindHeadResult.getColumn();
			}
			else
			{
				throw new Exception("必须存在本期用量列，请检查文档！");
			}
			cellFindHeadResult = sheet.findCell("上期表数", 0, 0, sheet.getColumns(), sheet.getRows(), true);
			if (cellFindHeadResult != null)
			{
				colIndex_PostAmount = cellFindHeadResult.getColumn();
			}
			cellFindHeadResult = sheet.findCell("本期表数", 0, 0, sheet.getColumns(), sheet.getRows(), true);
			if (cellFindHeadResult != null)
			{
				colIndex_CurrentAmount = cellFindHeadResult.getColumn();
			}
			cellFindHeadResult = sheet.findCell("缴费金额", 0, 0, sheet.getColumns(), sheet.getRows(), true);
			if (cellFindHeadResult != null)
			{
				colIndex_CurrentPayAmount = cellFindHeadResult.getColumn();
			}

			List<House> houses = houseService.queryHouseByCommunity(community);
			List<HouseRoom> houseRooms = houseRoomService.queryHouseRoomByCommunity(community);
			List<ConsumeInfo> newConsumeInfo = new ArrayList<ConsumeInfo>();
			List<ConsumeInfo> updateConsumeInfo = new ArrayList<ConsumeInfo>();
			StringBuilder existIds = new StringBuilder("");
			for (int i = 1; i < sheet.getRows(); i++)
			{
				String houseName = sheet.getCell(colIndex_House, i).getContents();
				House house = houseService.GetHouseFromListByName(houses, houseName);
				if (house == null)
				{
					throw new Exception("第" + String.valueOf(i + 1) + "行楼号不存在，请先维护！");
				}
				String houseRoomName = sheet.getCell(colIndex_HouseRoom, i).getContents();
				HouseRoom houseRoom = houseService.GetHouseRoomFromListByName(houseRooms, houseRoomName);
				if (houseRoom == null)
				{
					throw new Exception("第" + String.valueOf(i + 1) + "行门牌号不存在，请先维护！");
				}
				String payNumber = sheet.getCell(colIndex_PayNumber, i).getContents();
				if (payNumber.isEmpty())
				{
					throw new Exception("第" + String.valueOf(i + 1) + "行户号为空，请先检查！");
				}

				Double amount = 0.0;
				try
				{
					amount = Double.valueOf(sheet.getCell(colIndex_Amount, i).getContents());
				}
				catch (Exception e)
				{
					throw new Exception("第" + String.valueOf(i + 1) + "行本期用量不正确，请先检查！");
				}

				Double postAmount = 0.0;
				try
				{
					postAmount = Double.valueOf(sheet.getCell(colIndex_PostAmount, i).getContents());
				}
				catch (Exception e)
				{
					postAmount = 0.0;
				}
				Double currentAmount = 0.0;
				try
				{
					currentAmount = Double.valueOf(sheet.getCell(colIndex_CurrentAmount, i).getContents());
				}
				catch (Exception e)
				{
					currentAmount = 0.0;
				}
				Double currentPayAmount = 0.0;
				try
				{
					currentPayAmount = Double.valueOf(sheet.getCell(colIndex_CurrentPayAmount, i).getContents());
				}
				catch (Exception e)
				{
					currentPayAmount = 0.0;
				}

				ConsumeInfo consumeInfo = new ConsumeInfo();
				consumeInfo = new ConsumeInfo();
				consumeInfo.setCorp(user.getCorp());
				consumeInfo.setCommunity(community);
				consumeInfo.setHouse(house);
				consumeInfo.setHouseRoom(houseRoom);
				consumeInfo.setConsumeType(sysConsumeType);
				consumeInfo.setPayNumber(payNumber);
				consumeInfo.setYear(year);
				consumeInfo.setMonth(month);
				consumeInfo.setPostAmount(postAmount);
				consumeInfo.setCurrentAmount(currentAmount);
				consumeInfo.setAmount(amount);
				consumeInfo.setPrice(0);
				consumeInfo.setPayAmount(currentPayAmount);
				consumeInfo.setConsumeTime(new Date());
				consumeInfo.setRemark("");
				consumeInfo.setCreator(user.getId());
				consumeInfo.setCreateTime(new Date());
				newConsumeInfo.add(consumeInfo);

				consumeInfo = consumeInfoDao.queryConsumeInfo(sysConsumeType, year, month, community, house, houseRoom);
				if (consumeInfo != null)
				{
					existIds = existIds.append(existIds.length() > 0 ? "," : "").append(consumeInfo.getId());
				}
			}

			if (existIds.length() > 0)
			{
				consumeInfoDao.deleteConsumeInfoByIds(existIds.toString());
			}
			consumeInfoDao.addConsumeInfoFromList(newConsumeInfo);
		}
	}

	public List<ConsumeInfo> queryConsumeInfos(long sysConsumeTypeId, int year, int month, long communityId, long houseId, long houseRoomId, String payNumber, int startRow, int pageSize, DataTableRowCnt rowCnt)
	{
		if (payNumber.trim().length() == 0)
		{
			payNumber = null;
		}
		return consumeInfoDao.queryConsumeInfos(consumeTypeDao.get(sysConsumeTypeId), year, month, communityService.queryCommunityById(communityId), houseService.queryHouseById(houseId), houseRoomService.queryHouseRoomById(houseRoomId), payNumber, startRow, pageSize, rowCnt);
	}

	public List<ConsumeTemplate> queryConsumeTemplateByType(long community, long consumeType)
	{
		return consumeTemplateDao.queryConsumeTemplateByType(community, consumeType);
	}

	public void sendConsumeInfo(String title, long sendConsumeTemplate, long sysConsumeTypeId, int year, int month, long communityId, long houseId, long houseRoomId, String payNumber, long sender)
	{
		consumeInfoDao.sendConsumeInfo(title, sendConsumeTemplate, sysConsumeTypeId, year, month, communityId, houseId, houseRoomId, payNumber, sender);
	}

}

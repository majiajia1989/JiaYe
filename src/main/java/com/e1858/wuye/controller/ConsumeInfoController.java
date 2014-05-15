package com.e1858.wuye.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.ConsumeInfo;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseFloor;
import com.e1858.wuye.entity.hibernate.HouseRoom;
import com.e1858.wuye.entity.hibernate.HouseUnit;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AjaxResult;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.CommunityCommand;
import com.e1858.wuye.pojo.CorpCommand;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.DataTableRowCnt;
import com.e1858.wuye.pojo.HouseFloorCommand;
import com.e1858.wuye.pojo.HouseRoomCommand;
import com.e1858.wuye.pojo.HouseUnitCommand;
import com.e1858.wuye.pojo.SaveHouseCommand;
import com.e1858.wuye.pojo.SendConsumeCommand;
import com.e1858.wuye.pojo.TreeNode;
import com.e1858.wuye.service.hibernate.CommunityService;
import com.e1858.wuye.service.hibernate.ConsumeInfoService;
import com.e1858.wuye.service.hibernate.CorpService;
import com.e1858.wuye.service.hibernate.HouseFloorService;
import com.e1858.wuye.service.hibernate.HouseRoomService;
import com.e1858.wuye.service.hibernate.HouseService;
import com.e1858.wuye.service.hibernate.HouseUnitService;
import com.e1858.wuye.service.hibernate.SysCorpTypeService;
import com.e1858.wuye.utils.JsonUtil;
import com.e1858.wuye.utils.ThreadPool;
import com.e1858.wuye.utils.Util;

@Controller
@RequestMapping("/consumeInfo")
public class ConsumeInfoController
{

	@Autowired
	private ConsumeInfoService consumeInfoService;
	@Autowired
	private HouseService houseService;
	@Autowired
	private HouseRoomService houseRoomService;
	@Autowired
	private CommunityService communityService;

	@RequestMapping(value = "/water", method = RequestMethod.GET)
	public ModelAndView water(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("consumeInfo/water");

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}

		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", appContext.getCommunity().getId());
		request.setAttribute("houseData", houseService.queryHouseByCommunity(appContext.getCommunity()));
		request.setAttribute("houseRoomData", houseRoomService.queryHouseRoomByCommunity(appContext.getCommunity()));
		//
		request.setAttribute("consumeTemplateData", consumeInfoService.queryConsumeTemplateByType(appContext.getCommunity().getId(), CommonConstant.ConsumeType.Water));
		return modelAndView;
	}

	@RequestMapping(value = "/gas", method = RequestMethod.GET)
	public ModelAndView gas(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("consumeInfo/gas");

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}

		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", appContext.getCommunity().getId());
		request.setAttribute("houseData", houseService.queryHouseByCommunity(appContext.getCommunity()));
		request.setAttribute("houseRoomData", houseRoomService.queryHouseRoomByCommunity(appContext.getCommunity()));
		request.setAttribute("consumeTemplateData", consumeInfoService.queryConsumeTemplateByType(appContext.getCommunity().getId(), CommonConstant.ConsumeType.Gas));

		return modelAndView;
	}

	@RequestMapping(value = "/electricity", method = RequestMethod.GET)
	public ModelAndView electricity(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("consumeInfo/electricity");

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}

		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", appContext.getCommunity().getId());
		request.setAttribute("houseData", houseService.queryHouseByCommunity(appContext.getCommunity()));
		request.setAttribute("houseRoomData", houseRoomService.queryHouseRoomByCommunity(appContext.getCommunity()));
		request.setAttribute("consumeTemplateData", consumeInfoService.queryConsumeTemplateByType(appContext.getCommunity().getId(), CommonConstant.ConsumeType.Electricity));

		return modelAndView;
	}

	@RequestMapping(value = "/importConsumeInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ModelAndView importConsumeInfo(@RequestParam("importCommunityId") long importCommunityId, @RequestParam("importConsumeTypeId") long importConsumeTypeId, @RequestParam("importYear") int importYear, @RequestParam("importMonth") int importMonth, @RequestParam("importFile") CommonsMultipartFile importFile, HttpServletRequest request)
	{
		ModelAndView modelAndView = null;
		if (importConsumeTypeId == 1)
		{
			modelAndView = new ModelAndView("consumeInfo/electricity");
		}
		else if (importConsumeTypeId == 2)
		{
			modelAndView = new ModelAndView("consumeInfo/water");
		}
		else if (importConsumeTypeId == 3)
		{
			modelAndView = new ModelAndView("consumeInfo/gas");
		}

		request.setAttribute("houseData", houseService.queryHouseByCommunity(communityService.queryCommunityById(importCommunityId)));
		request.setAttribute("consumeTemplateData", consumeInfoService.queryConsumeTemplateByType(importCommunityId, importConsumeTypeId));

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		AjaxResult resp = new AjaxResult();
		resp.setFlag(false);
		if (!importFile.isEmpty())
		{
			String ctxPath = request.getSession().getServletContext().getRealPath("/") + "/WEB-INF/upload/";
			File dirFile = new File(ctxPath);
			if (dirFile.isFile() || !dirFile.exists())
			{
				dirFile.mkdirs();
			}
			String uuid = UUID.randomUUID().toString();
			String uploadPath = ctxPath + uuid;
			System.out.println(uploadPath);
			try
			{
				importFile.transferTo(new File(uploadPath));
				resp.setFlag(true);
				resp.setAttachment(uuid);
				// consumeInfoService.importFromExcel(uploadPath, user,
				// importYear, importMonth, importCommunityId,
				// importConsumeTypeId);
				importFromExcel(uploadPath, user, importYear, importMonth, importCommunityId, importConsumeTypeId);
				modelAndView.addObject("infoMsg", "恭喜您，导入成功!");
			}
			catch (Exception e)
			{
				CommonConstant.exceptionLogger.info(e);
				modelAndView.addObject("infoMsg", "对不起，导入失败!");
			}
		}
		else
		{
			modelAndView.addObject("infoMsg", "对不起，文件不能为空,请重新提交!");
		}

		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", importCommunityId);
		return modelAndView;

	}

	public void importFromExcel(final String srcFile, final SysUser user, final int year, final int month, final long communityId, final long consumeTypeId) throws Exception
	{
		ThreadPool.execute(new Runnable()
		{
			public void run()
			{
				try
				{
					consumeInfoService.importFromExcel(srcFile, user, year, month, communityId, consumeTypeId);
				}
				catch (Exception e)
				{
					CommonConstant.exceptionLogger.info(e);
				}
			}
		});
	}

	@RequestMapping(value = "/sendConsume", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ModelAndView sendConsume(HttpServletRequest request, @Valid SendConsumeCommand sendConsumeCommand, BindingResult result)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		ModelAndView modelAndView = null;
		if (sendConsumeCommand.getSendConsumeTypeId() == 1)
		{
			modelAndView = new ModelAndView("consumeInfo/electricity");
			if (sendConsumeCommand.getSendTitle().isEmpty())
			{
				sendConsumeCommand.setSendTitle("电费通知");
			}
		}
		else if (sendConsumeCommand.getSendConsumeTypeId() == 2)
		{
			modelAndView = new ModelAndView("consumeInfo/water");
			if (sendConsumeCommand.getSendTitle().isEmpty())
			{
				sendConsumeCommand.setSendTitle("水费通知");
			}
		}
		else if (sendConsumeCommand.getSendConsumeTypeId() == 3)
		{
			modelAndView = new ModelAndView("consumeInfo/gas");
			if (sendConsumeCommand.getSendTitle().isEmpty())
			{
				sendConsumeCommand.setSendTitle("燃气费通知");
			}
		}

		request.setAttribute("houseData", houseService.queryHouseByCommunity(communityService.queryCommunityById(sendConsumeCommand.getSendCommunityId())));
		request.setAttribute("consumeTemplateData", consumeInfoService.queryConsumeTemplateByType(sendConsumeCommand.getSendCommunityId(), sendConsumeCommand.getSendConsumeTypeId()));

		try
		{
			consumeInfoService.sendConsumeInfo(sendConsumeCommand.getSendTitle(), sendConsumeCommand.getSendConsumeTemplate(), sendConsumeCommand.getSendConsumeTypeId(), sendConsumeCommand.getSendYear(), sendConsumeCommand.getSendMonth(), sendConsumeCommand.getSendCommunityId(), sendConsumeCommand.getSendHouseId(), sendConsumeCommand.getSendHouseRoomId(), sendConsumeCommand.getSendPayNumber(), user.getId());
			modelAndView.addObject("infoMsg", "恭喜您，发送成功!");
		}
		catch (Exception e)
		{
			CommonConstant.exceptionLogger.info(e);
			modelAndView.addObject("infoMsg", "对不起，操作失败!");
		}
		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", sendConsumeCommand.getSendConsumeTypeId());
		return modelAndView;

	}

	@RequestMapping(value = "/readConsumeInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	private String readConsumeInfo(String sEcho, int iDisplayStart, int iDisplayLength, long queryConsumeTypeId, long queryCommunityId, long queryHouseId, long queryHouseRoomId, int queryYear, int queryMonth, String queryPayNumber)
	{
		DataTableRowCnt rowCnt = new DataTableRowCnt();

		DataTable<ConsumeInfo> dgList = new DataTable<ConsumeInfo>();
		dgList.setAaData((ArrayList<ConsumeInfo>) consumeInfoService.queryConsumeInfos(queryConsumeTypeId, queryYear, queryMonth, queryCommunityId, queryHouseId, queryHouseRoomId, queryPayNumber, iDisplayStart, iDisplayLength, rowCnt));
		if (dgList.getAaData() != null)
		{
			dgList.setiTotalRecords(dgList.getAaData().size());
		}
		dgList.setiTotalRecords(rowCnt.getRowCnt());
		dgList.setiTotalDisplayRecords(rowCnt.getRowCnt());
		dgList.setsEcho(sEcho);
		return JsonUtil.toJson(dgList);
	}

	@RequestMapping(value = "/readHouseRoomByHouse/{communityId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	private String readHouseRoomByHouse(@PathVariable("communityId") long communityId)
	{
		DataTable<HouseRoom> dgList = new DataTable<HouseRoom>();
		dgList.setAaData((ArrayList<HouseRoom>) houseRoomService.queryHouseRoomByCommunity(communityService.queryCommunityById(communityId)));
		if (dgList.getAaData() != null)
		{
			dgList.setiTotalRecords(dgList.getAaData().size());
		}
		dgList.setiTotalDisplayRecords(1);
		return JsonUtil.toJson(dgList);
	}

}

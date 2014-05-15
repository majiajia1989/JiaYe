package com.e1858.wuye.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestBody;
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
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseFloor;
import com.e1858.wuye.entity.hibernate.HouseRoom;
import com.e1858.wuye.entity.hibernate.HouseUnit;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AjaxResult;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.CorpCommand;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.DataTableRowCnt;
import com.e1858.wuye.pojo.HouseFloorCommand;
import com.e1858.wuye.pojo.HouseRoomCommand;
import com.e1858.wuye.pojo.HouseUnitCommand;
import com.e1858.wuye.pojo.SaveHouseCommand;
import com.e1858.wuye.pojo.TreeNode;
import com.e1858.wuye.service.hibernate.CommunityService;
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
@RequestMapping("/house")
public class HouseController
{

	@Autowired
	private HouseService houseService;
	@Autowired
	private HouseUnitService houseUnitService;
	@Autowired
	private HouseFloorService houseFloorService;
	@Autowired
	private HouseRoomService houseRoomService;
	@Autowired
	private SysCorpTypeService sysCorpTypeService;
	@Autowired
	private CommunityService communityService;

	@RequestMapping(value = "/house", method = RequestMethod.GET)
	public ModelAndView house(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("house/house");
		SaveHouseCommand saveHouseCommand = new SaveHouseCommand();

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}
		saveHouseCommand.setCorpId(appContext.getUser().getCorp().getId());
		saveHouseCommand.setCommunityId(appContext.getCommunity().getId());

		request.setAttribute("corpId", appContext.getUser().getCorp().getId());
		request.setAttribute("communityId", appContext.getCommunity().getId());
		request.setAttribute("houseId", -1);
		modelAndView.addObject("saveHouseCommand", new SaveHouseCommand());
		return modelAndView;
	}

	@RequestMapping(value = "/house", method = RequestMethod.POST)
	public ModelAndView house(HttpServletRequest request, @RequestParam("houseId") long houseId, @Valid SaveHouseCommand saveHouseCommand, BindingResult result)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		if (appContext.getCommunity() == null)
		{
			return null;
		}
		SysUser user = appContext.getUser();

		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", appContext.getCommunity().getId());

		ModelAndView modelAndView = new ModelAndView("house/house");

		modelAndView.addObject("saveHouseCommand", saveHouseCommand);

		if (result.hasErrors())
		{
			return modelAndView;
		}

		try
		{
			if (houseId > 0)
			{
				House house = houseService.queryHouseById(houseId);
				if (house != null)
				{
					house.setName(saveHouseCommand.getHoses());
					houseService.update(house);
				}
			}
			else
			{
				Community community = communityService.queryCommunityById(saveHouseCommand.getCommunityId());
				List<House> houses = new ArrayList<House>();
				for (String houseName : (Pattern.compile(",|;|\\s{1,}")).split(saveHouseCommand.getHoses()))
				{
					Pattern houseNameSuffixPattern = Pattern.compile("^((\\d{1,}-\\d{1,})|([a-zA-Z]-[a-zA-Z]))");

					String[] houseNameSuffixSplit = houseNameSuffixPattern.split(houseName);

					String houseNameSuffix = "";
					if (houseNameSuffixSplit.length > 1)
					{
						Matcher matcher = houseNameSuffixPattern.matcher(houseName);
						matcher.find();
						houseName = matcher.group();
						houseNameSuffix = houseNameSuffixSplit[1];
					}

					String[] houseNameSplit = houseName.split("-");
					if (houseNameSplit.length == 2)
					{
						if (Util.isLong(houseNameSplit[0]) && Util.isLong(houseNameSplit[1]))
						{
							for (int i = Math.min(Integer.parseInt(houseNameSplit[0]), Integer.parseInt(houseNameSplit[1])); i <= Math.max(Integer.parseInt(houseNameSplit[0]), Integer.parseInt(houseNameSplit[1])); i++)
							{
								House house = new House();
								house.setCorp(user.getCorp());
								house.setCommunity(community);
								house.setDescription("");

								house.setName(((Integer) i).toString() + houseNameSuffix);

								house.setCreator(user);
								house.setCreateTime((new Date()));
								houses.add(house);
							}
							continue;
						}
						else if (houseNameSplit[0].length() == 1 && houseNameSplit[1].length() == 1)
						{
							if (houseNameSplit[0].toUpperCase().toCharArray()[0] >= 65 && houseNameSplit[1].toUpperCase().toCharArray()[0] <= 90)
							{
								for (int i = Math.min(houseNameSplit[0].toUpperCase().toCharArray()[0], houseNameSplit[1].toUpperCase().toCharArray()[0]); i <= Math.max(houseNameSplit[0].toUpperCase().toCharArray()[0], houseNameSplit[1].toUpperCase().toCharArray()[0]); i++)
								{
									House house = new House();
									house.setCorp(user.getCorp());
									house.setCommunity(community);
									house.setDescription("");

									house.setName(String.valueOf((char) i) + houseNameSuffix);

									house.setCreator(user);
									house.setCreateTime((new Date()));
									houses.add(house);
								}
								continue;
							}
						}
					}

					if (houseName == "")
					{
						continue;
					}
					House house = new House();
					house.setCorp(user.getCorp());
					house.setCommunity(community);
					house.setDescription("");

					house.setName(houseName);

					house.setCreator(user);
					house.setCreateTime((new Date()));
					houses.add(house);
				}
				houseService.save(houses);
			}
			modelAndView.addObject("saveHouseCommand", new SaveHouseCommand());
			modelAndView.addObject("infoMsg", "恭喜您，保存成功！");
		}
		catch (Exception e)
		{
			CommonConstant.exceptionLogger.info(e);
			modelAndView.addObject("infoMsg", "对不起，操作失败！");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/deleteHouse", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteHouse(HttpServletRequest request, @RequestParam("houseId") long houseId)
	{
		AjaxResult result = new AjaxResult();
		result.setFlag(false);
		result.setMessage("");

		try
		{
			houseService.delete(houseId);
			result.setFlag(true);
			result.setMessage("恭喜您，删除成功！");
		}
		catch (Exception e)
		{
			CommonConstant.exceptionLogger.info(e);
			result.setFlag(false);
			result.setMessage("对不起，操作失败！");
		}
		return JsonUtil.toJson(result);
	}

	@RequestMapping(value = "/houseUnit", method = RequestMethod.GET)
	public ModelAndView houseUnit(HttpServletRequest request)
	{

		ModelAndView modelAndView = new ModelAndView("house/houseUnit");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}

		HouseUnitCommand houseUnitCommand = new HouseUnitCommand();

		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", appContext.getCommunity().getId());
		request.setAttribute("houseUnitId", -1);
		modelAndView.addObject("houseUnitCommand", houseUnitCommand);

		return modelAndView;
	}

	@RequestMapping(value = "/houseUnit", method = RequestMethod.POST)
	public ModelAndView houseUnit(HttpServletRequest request, @Valid HouseUnitCommand houseUnitCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("house/houseUnit");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}

		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", houseUnitCommand.getCommunityId());
		request.setAttribute("houseUnitId", houseUnitCommand.getHouseUnitId());

		modelAndView.addObject("houseUnitCommand", houseUnitCommand);

		if (result.hasErrors())
		{
			return modelAndView;
		}

		try
		{
			if (houseUnitCommand.getHouseUnitId() > 0)
			{
				HouseUnit houseUnit = houseUnitService.queryHouseUnitById(houseUnitCommand.getHouseUnitId());
				if (houseUnit != null)
				{
					houseUnit.setName(houseUnitCommand.getName());
					houseUnitService.update(houseUnit);
				}
			}
			else
			{
				HouseUnit houseUnit = new HouseUnit();
				houseUnit.setCorp(user.getCorp());
				houseUnit.setCommunity(communityService.queryCommunityById(houseUnitCommand.getCommunityId()));
				houseUnit.setHouse(-1);
				houseUnit.setName(houseUnitCommand.getName());
				houseUnit.setDescription(houseUnitCommand.getDescription());
				houseUnit.setCreator(user);
				houseUnit.setCreateTime((new Date()));
				houseUnitService.save(houseUnit);
			}
			modelAndView.addObject("infoMsg", "恭喜您，保存成功！");
		}
		catch (Exception e)
		{
			modelAndView.addObject("infoMsg", "对不起，保存失败！");
			CommonConstant.exceptionLogger.info(e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/deleteHouseUnit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteHouseUnit(HttpServletRequest request, @RequestParam("houseUnitId") long houseUnitId)
	{
		AjaxResult result = new AjaxResult();
		result.setFlag(false);
		result.setMessage("");

		try
		{
			houseUnitService.delete(houseUnitId);
			result.setFlag(true);
			result.setMessage("恭喜您，删除成功！");
		}
		catch (Exception e)
		{
			result.setFlag(false);
			result.setMessage("对不起，操作失败！");
			CommonConstant.exceptionLogger.info(e);
		}
		return JsonUtil.toJson(result);
	}

	@RequestMapping(value = "/houseFloor", method = RequestMethod.GET)
	public ModelAndView houseFloor(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("house/houseFloor");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}

		HouseFloorCommand houseFloorCommand = new HouseFloorCommand();
		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", appContext.getCommunity().getId());
		request.setAttribute("houseFloorId", -1);
		modelAndView.addObject("houseFloorCommand", houseFloorCommand);

		return modelAndView;
	}

	@RequestMapping(value = "/houseFloor", method = RequestMethod.POST)
	public ModelAndView houseFloor(HttpServletRequest request, @Valid HouseFloorCommand houseFloorCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("house/houseFloor");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}

		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", houseFloorCommand.getCommunityId());
		request.setAttribute("houseFloorId", houseFloorCommand.getHouseFloorId());

		modelAndView.addObject("houseFloorCommand", houseFloorCommand);

		if (result.hasErrors())
		{
			return modelAndView;
		}

		if (houseFloorCommand.getHouseFloorId() > 0)
		{
			HouseFloor houseFloor = houseFloorService.queryHouseFloorById(houseFloorCommand.getHouseFloorId());
			if (houseFloor != null)
			{
				houseFloor.setName(houseFloorCommand.getName());
				houseFloorService.update(houseFloor);
			}
		}
		else
		{
			HouseFloor houseFloor = new HouseFloor();
			houseFloor.setCorp(user.getCorp());
			houseFloor.setCommunity(communityService.queryCommunityById(houseFloorCommand.getCommunityId()));
			houseFloor.setHouse(-1);
			houseFloor.setName(houseFloorCommand.getName());
			houseFloor.setDescription(houseFloorCommand.getDescription());
			houseFloor.setCreator(user);
			houseFloor.setCreateTime((new Date()));
			houseFloorService.save(houseFloor);
		}
		modelAndView.addObject("infoMsg", "恭喜您，保存成功！");
		return modelAndView;
	}

	@RequestMapping(value = "/deleteHouseFloor", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteHouseFloor(HttpServletRequest request, @RequestParam("houseFloorId") long houseFloorId)
	{
		AjaxResult result = new AjaxResult();
		result.setFlag(false);
		result.setMessage("");

		try
		{
			houseFloorService.delete(houseFloorId);
			result.setFlag(true);
			result.setMessage("恭喜您，删除成功！");
		}
		catch (Exception e)
		{
			result.setFlag(false);
			result.setMessage("对不起，操作失败！");
			CommonConstant.exceptionLogger.info(e);
		}
		return JsonUtil.toJson(result);
	}

	@RequestMapping(value = "/houseRoom", method = RequestMethod.GET)
	public ModelAndView houseRoom(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("house/houseRoom");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}
		HouseFloorCommand houseFloorCommand = new HouseFloorCommand();

		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", appContext.getCommunity().getId());
		request.setAttribute("houseRoomId", -1);
		request.setAttribute("houseUnitData", readHousesUnitTree(appContext.getCommunity().getId()));
		modelAndView.addObject("houseRoomCommand", houseFloorCommand);

		return modelAndView;
	}

	@RequestMapping(value = "/houseRoom", method = RequestMethod.POST)
	public ModelAndView houseRoom(HttpServletRequest request, @Valid HouseRoomCommand houseRoomCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("house/houseRoom");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}

		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", houseRoomCommand.getCommunityId());
		request.setAttribute("houseRoomId", houseRoomCommand.getHouseRoomId());
		request.setAttribute("houseUnitData", readHousesUnitTree(houseRoomCommand.getCommunityId()));

		modelAndView.addObject("houseRoomCommand", houseRoomCommand);

		if (result.hasErrors())
		{
			return modelAndView;
		}
		try
		{
			if (houseRoomCommand.getHouseRoomId() > 0)
			{
				HouseRoom houseRoom = houseRoomService.queryHouseRoomById(houseRoomCommand.getHouseRoomId());
				if (houseRoom != null)
				{
					houseRoom.setName(houseRoomCommand.getName());
					houseRoomService.update(houseRoom);
				}
			}
			else
			{
				HouseRoom houseRoom = new HouseRoom();
				houseRoom.setCorp(user.getCorp());
				houseRoom.setCommunity(communityService.queryCommunityById(houseRoomCommand.getCommunityId()));
				houseRoom.setHouse(-1);
				houseRoom.setName(houseRoomCommand.getName());
				houseRoom.setDescription(houseRoomCommand.getDescription());
				houseRoom.setCreator(user);
				houseRoom.setCreateTime((new Date()));
				houseRoomService.save(houseRoom);
			}
			modelAndView.addObject("infoMsg", "恭喜您，保存成功！");
		}
		catch (Exception e)
		{
			modelAndView.addObject("infoMsg", "对不起，操作失败！");
			CommonConstant.exceptionLogger.info(e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/deleteHouseRoom", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteHouseRoom(HttpServletRequest request, @RequestParam("houseRoomId") long houseRoomId)
	{
		AjaxResult result = new AjaxResult();
		result.setFlag(false);
		result.setMessage("");

		try
		{
			houseRoomService.delete(houseRoomId);
			result.setFlag(true);
			result.setMessage("恭喜您，删除成功！");
		}
		catch (Exception e)
		{
			result.setFlag(false);
			result.setMessage("对不起，操作失败！");
			CommonConstant.exceptionLogger.info(e);

		}
		return JsonUtil.toJson(result);
	}

	@RequestMapping(value = "/readHouses/{communityId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readHouses(HttpServletRequest request, @PathVariable("communityId") long communityId, @RequestParam String sEcho, @RequestParam int iDisplayStart, @RequestParam int iDisplayLength)
	{
		DataTableRowCnt rowCnt = new DataTableRowCnt();
		DataTable<House> dgList = new DataTable<House>();
		dgList.setAaData((ArrayList<House>) houseService.queryHouseByCommunity(communityService.queryCommunityById(communityId), iDisplayStart, iDisplayLength, rowCnt));

		dgList.setiTotalRecords(rowCnt.getRowCnt());
		dgList.setiTotalDisplayRecords(rowCnt.getRowCnt());
		dgList.setsEcho(sEcho);

		return JsonUtil.toJson(dgList);
	}

	private List<TreeNode> readHousesUnitTree(long communityId)
	{
		List<TreeNode> result = new ArrayList<TreeNode>();
		for (HouseUnit houseUnit : houseUnitService.queryHouseUnitByCommunity(communityService.queryCommunityById(communityId)))
		{
			TreeNode treeNode = new TreeNode();
			treeNode.setId(((Long) houseUnit.getId()).toString());
			treeNode.setText(houseUnit.getName());
			result.add(treeNode);
		}
		return result;
	}

	@RequestMapping(value = "/readHouseUnitByCommunity/{communityId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readHouseUnitByCommunity(HttpServletRequest request, @PathVariable("communityId") long communityId, @RequestParam String sEcho, @RequestParam int iDisplayStart, @RequestParam int iDisplayLength)
	{
		DataTableRowCnt rowCnt = new DataTableRowCnt();
		DataTable<HouseUnit> dgList = new DataTable<HouseUnit>();
		dgList.setAaData((ArrayList<HouseUnit>) houseUnitService.queryHouseUnitByCommunity(communityService.queryCommunityById(communityId), iDisplayStart, iDisplayLength, rowCnt));

		dgList.setiTotalRecords(rowCnt.getRowCnt());
		dgList.setiTotalDisplayRecords(rowCnt.getRowCnt());
		dgList.setsEcho(sEcho);

		return JsonUtil.toJson(dgList);
	}

	@RequestMapping(value = "/readHouseFloorByCommunity/{communityId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readHouseFloorByCommunity(HttpServletRequest request, @PathVariable("communityId") long communityId, @RequestParam String sEcho, @RequestParam int iDisplayStart, @RequestParam int iDisplayLength)
	{
		DataTableRowCnt rowCnt = new DataTableRowCnt();
		DataTable<HouseFloor> dgList = new DataTable<HouseFloor>();
		dgList.setAaData((ArrayList<HouseFloor>) houseFloorService.queryHouseFloorByCommunity(communityService.queryCommunityById(communityId), iDisplayStart, iDisplayLength, rowCnt));

		dgList.setiTotalRecords(rowCnt.getRowCnt());
		dgList.setiTotalDisplayRecords(rowCnt.getRowCnt());
		dgList.setsEcho(sEcho);

		return JsonUtil.toJson(dgList);
	}

	@RequestMapping(value = "/readHouseRoomByCommunity/{communityId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readHouseRoomByCommunity(HttpServletRequest request, @PathVariable("communityId") long communityId, @RequestParam String sEcho, @RequestParam int iDisplayStart, @RequestParam int iDisplayLength)
	{
		DataTableRowCnt rowCnt = new DataTableRowCnt();

		DataTable<HouseRoom> dgList = new DataTable<HouseRoom>();
		dgList.setAaData((ArrayList<HouseRoom>) houseRoomService.queryHouseRoomByCommunity(communityService.queryCommunityById(communityId), iDisplayStart, iDisplayLength, rowCnt));
		dgList.setiTotalRecords(rowCnt.getRowCnt());
		dgList.setiTotalDisplayRecords(rowCnt.getRowCnt());
		dgList.setsEcho(sEcho);
		return JsonUtil.toJson(dgList);
	}

	@RequestMapping(value = "/readHouseRoomByHouseAndHouseUnit/{communityId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readHouseRoomByHouseAndHouseUnit(HttpServletRequest request, @PathVariable("communityId") long communityId)
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

	@RequestMapping(value = "/importHouse", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ModelAndView importHouse(@RequestParam("importCommunityId") long importCommunityId, @RequestParam("importFile") CommonsMultipartFile importFile, HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("house/house");
		SaveHouseCommand saveHouseCommand = new SaveHouseCommand();

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
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
				importHouse(uploadPath, user, user.getCorp(), communityService.queryCommunityById(importCommunityId));
				modelAndView.addObject("infoMsg", "恭喜您，导入成功!");
			}
			catch (Exception e)
			{
				modelAndView.addObject("infoMsg", "对不起，保存文件失败,请重新提交!");
			}
		}
		else
		{
			modelAndView.addObject("infoMsg", "对不起，文件不能为空,请重新提交!");
		}

		saveHouseCommand.setCorpId(user.getCorp().getId());
		saveHouseCommand.setCommunityId(1);

		request.setAttribute("corpId", user.getCorp().getId());
		request.setAttribute("communityId", importCommunityId);
		request.setAttribute("houseId", -1);
		modelAndView.addObject("saveHouseCommand", new SaveHouseCommand());
		return modelAndView;
	}

	private void importHouse(final String srcFile, final SysUser user, final SysCorp corp, final Community community)
	{
		ThreadPool.execute(new Runnable()
		{
			public void run()
			{
				try
				{
					houseService.importFromExcel(srcFile, user, corp, community);
				}
				catch (Exception e)
				{
					CommonConstant.exceptionLogger.info(e);
					// TODO Auto-generated catch block
				}
			}
		});
	}
}

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
import com.e1858.wuye.entity.hibernate.ConsumeTemplate;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseFloor;
import com.e1858.wuye.entity.hibernate.HouseRoom;
import com.e1858.wuye.entity.hibernate.HouseUnit;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AjaxResult;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.CommunityCommand;
import com.e1858.wuye.pojo.ConsumeTemplateCommand;
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
import com.e1858.wuye.service.hibernate.ConsumeTemplateService;
import com.e1858.wuye.service.hibernate.CorpService;
import com.e1858.wuye.service.hibernate.HouseFloorService;
import com.e1858.wuye.service.hibernate.HouseRoomService;
import com.e1858.wuye.service.hibernate.HouseService;
import com.e1858.wuye.service.hibernate.HouseUnitService;
import com.e1858.wuye.service.hibernate.SysCorpTypeService;
import com.e1858.wuye.utils.JsonUtil;
import com.e1858.wuye.utils.Util;

@Controller
@RequestMapping("/consumeTemplate")
public class ConsumeTemplateController
{

	@Autowired
	private ConsumeInfoService consumeInfoService;
	@Autowired
	private ConsumeTemplateService consumeTemplateService;

	@RequestMapping(value = "/waterTemplate", method = RequestMethod.GET)
	public ModelAndView waterTemplate(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("consumeInfo/waterTemplate");

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}
		ConsumeTemplateCommand consumeTemplateCommand = new ConsumeTemplateCommand();
		consumeTemplateCommand.setConsumeType(CommonConstant.ConsumeType.Water);
		modelAndView.addObject("consumeTemplateCommand", consumeTemplateCommand);
		return modelAndView;
	}

	@RequestMapping(value = "/electricityTemplate", method = RequestMethod.GET)
	public ModelAndView electricityTemplate(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("consumeInfo/electricityTemplate");

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}
		ConsumeTemplateCommand consumeTemplateCommand = new ConsumeTemplateCommand();
		consumeTemplateCommand.setConsumeType(CommonConstant.ConsumeType.Electricity);
		modelAndView.addObject("consumeTemplateCommand", consumeTemplateCommand);
		return modelAndView;
	}

	@RequestMapping(value = "/gasTemplate", method = RequestMethod.GET)
	public ModelAndView gasTemplate(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("consumeInfo/gasTemplate");

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (appContext.getCommunity() == null)
		{
			return modelAndView;
		}
		ConsumeTemplateCommand consumeTemplateCommand = new ConsumeTemplateCommand();
		consumeTemplateCommand.setConsumeType(CommonConstant.ConsumeType.Gas);
		modelAndView.addObject("consumeTemplateCommand", consumeTemplateCommand);
		return modelAndView;
	}

	@RequestMapping(value = "/saveConsumeTemplate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ModelAndView saveConsumeTemplate(HttpServletRequest request, @Valid ConsumeTemplateCommand consumeTemplateCommand, BindingResult result)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		ModelAndView modelAndView = null;
		if (consumeTemplateCommand.getConsumeType() == 1)
		{
			modelAndView = new ModelAndView("consumeInfo/electricityTemplate");
		}
		else if (consumeTemplateCommand.getConsumeType() == 2)
		{
			modelAndView = new ModelAndView("consumeInfo/waterTemplate");
		}
		else if (consumeTemplateCommand.getConsumeType() == 3)
		{
			modelAndView = new ModelAndView("consumeInfo/gasTemplate");
		}
		ConsumeTemplate consumeTemplate;
		try
		{
			if (consumeTemplateCommand.getId() > 0)
			{
				consumeTemplate = consumeTemplateService.queryConsumeTemplateById(consumeTemplateCommand.getId());
				consumeTemplate.setTitle(consumeTemplateCommand.getTitle());
				consumeTemplate.setContent(consumeTemplateCommand.getContent());
				consumeTemplateService.update(consumeTemplate);
			}
			else
			{
				consumeTemplate = new ConsumeTemplate();
				consumeTemplate.setCorp(user.getCorp().getId());
				consumeTemplate.setCommunity(appContext.getCommunity().getId());
				consumeTemplate.setConsumeType(consumeTemplateCommand.getConsumeType());
				consumeTemplate.setTitle(consumeTemplateCommand.getTitle());
				consumeTemplate.setContent(consumeTemplateCommand.getContent());
				consumeTemplate.setCreator(user);
				consumeTemplate.setCreateTime(new Date());
				consumeTemplateService.save(consumeTemplate);
			}
			modelAndView.addObject("infoMsg", "恭喜您，操作成功!");
		}
		catch (Exception e)
		{
			CommonConstant.exceptionLogger.info(e);
			modelAndView.addObject("infoMsg", "对不起，操作失败!");
		}

		return modelAndView;

	}

	@RequestMapping(value = "/readConsumeTemplate", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	private String readConsumeTemplate(HttpServletRequest request, long consumeType)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);

		DataTable<ConsumeTemplate> dgList = new DataTable<ConsumeTemplate>();
		dgList.setAaData((ArrayList<ConsumeTemplate>) consumeInfoService.queryConsumeTemplateByType(appContext.getCommunity().getId(), consumeType));
		dgList.setiTotalDisplayRecords(1);
		return JsonUtil.toJson(dgList);
	}

	@RequestMapping(value = "/deleteConsumeTemplate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteConsumeTemplate(HttpServletRequest request, @RequestParam("consumeTemplateId") long consumeTemplateId)
	{
		AjaxResult result = new AjaxResult();
		result.setFlag(false);
		result.setMessage("");

		try
		{
			consumeTemplateService.delete(consumeTemplateService.queryConsumeTemplateById(consumeTemplateId));
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
}

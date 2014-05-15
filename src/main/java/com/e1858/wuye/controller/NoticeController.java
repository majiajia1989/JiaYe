package com.e1858.wuye.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseFloor;
import com.e1858.wuye.entity.hibernate.HouseUnit;
import com.e1858.wuye.entity.hibernate.Notice;
import com.e1858.wuye.entity.hibernate.Subscriber;
import com.e1858.wuye.entity.hibernate.SubscriberHouse;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.NoticeCommand;
import com.e1858.wuye.pojo.Pair;
import com.e1858.wuye.pojo.Response;
import com.e1858.wuye.service.hibernate.HouseService;
import com.e1858.wuye.service.hibernate.NoticeService;
import com.e1858.wuye.service.hibernate.SubscriberHouseService;
import com.e1858.wuye.service.hibernate.SubscriberService;
import com.e1858.wuye.utils.JsonUtil;

@Controller
@RequestMapping("/community")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;

	@Autowired
	private SubscriberService subscriberService;

	@Autowired
	private SubscriberHouseService subscriberHouseService;

	@Autowired
	private HouseService houseService;
	
	@RequestMapping(value = "/notices", method = RequestMethod.GET)
	public String notices(HttpServletRequest request) {
		return "community/notices";
	}

	@RequestMapping(value = "/editNotice/{noticeID}", method = RequestMethod.GET)
	public ModelAndView editNotice(@PathVariable("noticeID") long noticeID, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("community/editNotice", "noticeCommand", new NoticeCommand());
		if (noticeID != -1) {
			NoticeCommand notice = readNoticeCommand(noticeID);
			if (notice != null) {
				modelAndView.addObject("noticeCommand", notice);
			}
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/noticeDetail/{noticeID}", method = RequestMethod.GET)
	public ModelAndView showNotice(@PathVariable("noticeID") long noticeID, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("community/noticeDetail", "noticeCommand", new NoticeCommand());
		NoticeCommand notice = readNoticeCommand(noticeID);
		if (notice != null) {
			modelAndView.addObject("noticeCommand", notice);
		}
		return modelAndView;
	}
	
	private NoticeCommand readNoticeCommand(long noticeID) {
		Notice notice = noticeService.queryNoticeByID(noticeID);
		if (notice != null) {
			NoticeCommand cmd = new NoticeCommand();
			cmd.setTitle(notice.getTitle());
			cmd.setContent(notice.getContent());
			cmd.setCreator(notice.getCreator().getName());
			cmd.setDescription(notice.getDescription());
			cmd.setId(notice.getId());
			cmd.setPictureUrl(notice.getPicUrl());
			cmd.setType(notice.getType());
			return cmd;
		}
		return null;
	}

	@RequestMapping(value = "/showNotice/{noticeID}", method = RequestMethod.GET)
	public void showNotice(@PathVariable("noticeID") long noticeID, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Notice notice = noticeService.queryNoticeByID(noticeID);
		if (notice != null) {
			NoticeCommand cmd = new NoticeCommand();
			cmd.setTitle(notice.getTitle());
			cmd.setContent(notice.getContent());
			cmd.setCreator(notice.getCreator().getName());
			cmd.setDescription(notice.getDescription());
			cmd.setId(notice.getId());
			cmd.setPictureUrl(notice.getPicUrl());
			cmd.setType(notice.getType());
			response.setContentType("application/json");
			response.getWriter().write(JsonUtil.toJson(cmd));
		}
	}

	@RequestMapping(value = "/queryAllNotices", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAllNotices(
			@RequestParam("iDisplayStart") int iDisplayStart, 
			 @RequestParam("iDisplayLength") int iDisplayLength,
			 @RequestParam(value = "sEcho") int sEcho,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		List<Notice> notices = noticeService.queryNoticesByCommunity(appContext.getCommunity(),iDisplayStart,iDisplayLength);

		List<NoticeCommand> cmds = new ArrayList<NoticeCommand>();
		for (Notice notice : notices) {
			NoticeCommand cmd = new NoticeCommand();
			cmd.setTitle(notice.getTitle());
			cmd.setContent(notice.getContent());
			cmd.setCreateTime(notice.getCreateTime());
			cmd.setCreator(notice.getCreator().getName());
			cmd.setDescription(notice.getDescription());
			cmd.setId(notice.getId());
			cmd.setPictureUrl(notice.getPicUrl());
			cmd.setAuditStatus(notice.getAuditor() > 0);
			cmd.setSendStatus(notice.getSender() > 0);
			cmd.setType(notice.getType());
			cmds.add(cmd);
		}

		long count = noticeService.noticesCountByCommunity(appContext.getCommunity());
		DataTable<NoticeCommand> dataGrid = new DataTable<NoticeCommand>();
		dataGrid.setAaData(cmds);
		dataGrid.setsEcho("" + sEcho);
		dataGrid.setiTotalRecords(count);
		dataGrid.setiTotalDisplayRecords(count);
		return JsonUtil.toJson(dataGrid);
	}

	@RequestMapping(value = "/queryAllReceivers", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAllReceivers(
			@RequestParam("iDisplayStart") int iDisplayStart, 
			@RequestParam("iDisplayLength") int iDisplayLength,
			 @RequestParam(value = "sEcho") int sEcho,
			@RequestParam(required = false) String houses,
			@RequestParam(required = false) String floors, @RequestParam(required = false) String units,
			@RequestParam(required = false) Boolean hasCar, HttpServletRequest request) {
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		List<Subscriber> subscribers = subscriberService.querySubscribersByCommunity(appContext.getCommunity(), houses,
				floors, units, hasCar,iDisplayStart,iDisplayLength);
		List<com.e1858.wuye.pojo.NoticeReceiver> cmds = new ArrayList<com.e1858.wuye.pojo.NoticeReceiver>();
		for (Subscriber subscriber : subscribers) {
			SubscriberHouse house = subscriberHouseService.querySubscriberHourseByOpenID(subscriber.getOpenid());
			if (house == null) {
				continue;
			}
			com.e1858.wuye.pojo.NoticeReceiver cmd = new com.e1858.wuye.pojo.NoticeReceiver();
			cmd.setCommunity(house.getCommunity().getName());
			cmd.setHouseRoom(house.getHouseRoom().getName());
			cmd.setHouse(house.getHouse().getName());
			cmd.setHouseUnit(house.getHouseUnit().getName());
			cmd.setHouseFloor(house.getHouseFloor().getName());
			cmd.setOpenid(subscriber.getOpenid());
			cmds.add(cmd);
		}

		long count = subscriberService.subscribersCountByCommunity(appContext.getCommunity(), houses,
				floors, units, hasCar);
		DataTable<com.e1858.wuye.pojo.NoticeReceiver> dataGrid = new DataTable<com.e1858.wuye.pojo.NoticeReceiver>();
		dataGrid.setAaData(cmds);
		dataGrid.setsEcho("" + sEcho);
		dataGrid.setiTotalRecords(count);
		dataGrid.setiTotalDisplayRecords(count);
		return JsonUtil.toJson(dataGrid);
	}

	@RequestMapping(value = "/saveNotice", method = RequestMethod.POST)
	public ModelAndView saveNotice(HttpServletRequest request, @Valid NoticeCommand noticeCommand, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("community/editNotice");
		modelAndView.addObject("noticeCommand", noticeCommand);
		if (result.hasErrors()) {
			return modelAndView;
		}
		try {
			String ctxUrl = request.getRequestURL().toString();	
			String projectName = request.getContextPath();	
			String hostName = ctxUrl.substring(0, ctxUrl.lastIndexOf(projectName));
			
			HttpSession session = request.getSession();
			AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
			SysUser user = appContext.getUser();
			SysCorp corp = user.getCorp();
			if (noticeCommand.getId() == -1) {
				Notice notice = new Notice();
				notice.setCommunity(appContext.getCommunity());
				notice.setContent(noticeCommand.getContent());
				notice.setTitle(noticeCommand.getTitle());
				notice.setType(noticeCommand.getType());
				notice.setCorp(corp);
				notice.setDescription(noticeCommand.getDescription());
				notice.setCreator(user);
				notice.setCreateTime(new Date());
				{
					String imageUrl = noticeCommand.getPictureUrl();
					if((imageUrl != null && imageUrl.length() > 0) && !(imageUrl.startsWith("http") || imageUrl.startsWith("https")))
					{
						imageUrl = hostName.concat(imageUrl);
					}
					notice.setPicUrl(imageUrl);
				}
				
				noticeService.saveNotice(notice);
				String contextPath = request.getContextPath();
				notice.setMsg("<a href='" + contextPath + "/community/showNotice/" + notice.getId() + "'>查看详情</a>");
				noticeService.updateNotice(notice);
			} else {
				Notice notice = noticeService.queryNoticeByID(noticeCommand.getId());
				if (notice.getAuditor() > 0) {
					modelAndView.addObject("errMsg", "审核已通过,不能修改通知内容!");
					return modelAndView;
				} else {
					notice.setContent(noticeCommand.getContent());
					notice.setTitle(noticeCommand.getTitle());
					notice.setType(noticeCommand.getType());
					notice.setDescription(noticeCommand.getDescription());
					notice.setPicUrl(noticeCommand.getPictureUrl());
					noticeService.updateNotice(notice);
				}
			}
			modelAndView = new ModelAndView("redirect:/community/notices");
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("errMsg", "保存通知出错!");
			CommonConstant.exceptionLogger.info(e);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/sendNotice/{noticeID}", method = RequestMethod.GET)
	public ModelAndView sendNotice(@PathVariable("noticeID") long noticeID, HttpServletRequest request) {
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);

		List<Pair<Long, String>> pojoHouses = new ArrayList<Pair<Long, String>>();
		List<House> houses = houseService.queryHouseByCommunity(appContext.getCommunity());
		List<HouseFloor> floors = houseService.queryHouseFloorByCommunity(appContext.getCommunity());
		List<HouseUnit> units = houseService.queryHouseUnitByCommunity(appContext.getCommunity());

		if (houses != null && houses.size() > 0) {
			for (House house : houses) {
				pojoHouses.add(new Pair<Long, String>(house.getId(), house.getName()));
			}
		}
		List<Pair<Long, String>> pojoFloors = new ArrayList<Pair<Long, String>>();
		if (floors != null && floors.size() > 0) {
			for (HouseFloor floor : floors) {
				pojoFloors.add(new Pair<Long, String>(floor.getId(), floor.getName()));
			}
		}
		List<Pair<Long, String>> pojoUnits = new ArrayList<Pair<Long, String>>();
		if (units != null && units.size() > 0) {
			for (HouseUnit unit : units) {
				pojoUnits.add(new Pair<Long, String>(unit.getId(), unit.getName()));
			}
		}
		ModelAndView modelAndView = new ModelAndView("community/sendNotice");
		modelAndView.addObject("noticeID", Long.valueOf(noticeID));
		modelAndView.addObject("houses", pojoHouses);
		modelAndView.addObject("floors", pojoFloors);
		modelAndView.addObject("units", pojoUnits);
		return modelAndView;
	}

	@RequestMapping(value = "/sendNotice", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String sendNotice(@RequestParam long noticeID, @RequestParam(required = false) String houses,
			@RequestParam(required = false) String floors, @RequestParam(required = false) String units,
			@RequestParam(required = false) Boolean hasCar, HttpServletRequest request) {
		Response resp = new Response();
		try {
			HttpSession session = request.getSession();
			AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
			SysUser user = appContext.getUser();
			noticeService.sendNoticeWithQuery(user, noticeID, houses, floors, units, hasCar);
			resp.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setSuccess(false);
			resp.setErrMsg("发送通知失败!");
			CommonConstant.exceptionLogger.info( e);
		}
		return JsonUtil.toJson(resp);
	}

	@RequestMapping(value = "/removeNoticeByID/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String removeNoticeByID(@PathVariable("id") long id, HttpServletRequest request) {
		Response resp = new Response();
		Notice notice = noticeService.queryNoticeByID(id);
		if (notice == null) {
			resp.setSuccess(false);
			resp.setErrMsg("找不到指定的通知!");
		}
		if (notice.getSender() > 0) {
			resp.setSuccess(false);
			resp.setErrMsg("不能删除已发送通知!");
		} else {
			try {
				noticeService.deleteNotice(notice);
				resp.setSuccess(true);
			} catch (Exception e) {
				e.printStackTrace();
				resp.setSuccess(false);
				resp.setErrMsg("删除失败!");
			}
		}
		return JsonUtil.toJson(resp);
	}

	@RequestMapping(value = "/auditNoticeByID/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String auditNoticeByID(@PathVariable("id") long id, HttpServletRequest request) {
		Response resp = new Response();
		Notice notice = noticeService.queryNoticeByID(id);
		if (notice == null) {
			resp.setSuccess(false);
			resp.setErrMsg("找不到指定的通知!");
		} else {
			try {
				HttpSession session = request.getSession();
				AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
				SysUser user = appContext.getUser();
				notice.setAuditor(user.getId());
				notice.setAuditTime(new Date());
				noticeService.updateNotice(notice);
				resp.setSuccess(true);
			} catch (Exception e) {
				e.printStackTrace();
				resp.setSuccess(false);
				resp.setErrMsg("审核失败!");
				CommonConstant.exceptionLogger.info( e);
			}
		}
		return JsonUtil.toJson(resp);
	}
}

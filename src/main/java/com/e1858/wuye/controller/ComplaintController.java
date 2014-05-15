package com.e1858.wuye.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.Complaint;
import com.e1858.wuye.entity.hibernate.ComplaintResponse;
import com.e1858.wuye.entity.hibernate.Subscriber;
import com.e1858.wuye.entity.hibernate.SubscriberHouse;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.ComplaintCommand;
import com.e1858.wuye.pojo.ComplaintResponseCommand;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.service.hibernate.ComplaintService;
import com.e1858.wuye.service.hibernate.SubscriberHouseService;
import com.e1858.wuye.service.hibernate.SubscriberService;
import com.e1858.wuye.utils.JsonUtil;

@Controller
@RequestMapping("/community")
public class ComplaintController {

	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private SubscriberHouseService subscriberHouseService;
	
	@RequestMapping(value = "/complaints", method = RequestMethod.GET)
	public ModelAndView complaints(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("community/complaints", "complaintResponseCommand",
				new ComplaintResponseCommand());
		return modelAndView;
	}

	@RequestMapping(value = "/complaintResponse", method = RequestMethod.POST)
	public ModelAndView complaintResponse(HttpServletRequest request,
			@Valid ComplaintResponseCommand complaintResponseCommand, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("community/complaints", "complaintResponseCommand",
				complaintResponseCommand);
		if (complaintResponseCommand.isFromResponseList()) {
			modelAndView = new ModelAndView("redirect:/community/complaintResponses/" + complaintResponseCommand.getComplaintID());
		}
		if (result.hasErrors()) {
			return modelAndView;
		}

		try {
			HttpSession session = request.getSession();
			AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
			SysUser user = appContext.getUser();
			ComplaintResponse response = new ComplaintResponse();
			Complaint complaint = complaintService.queryComplaintsByID(complaintResponseCommand.getComplaintID());
			response.setComplaint(complaint);
			response.setContent(complaintResponseCommand.getContent());
			response.setCreator(user);
			response.setCreateTime(new Date());
			complaintService.saveComplaintResponse(response);
		} catch (Exception e) {
			// modelAndView.addObject("errMsg", "回复失败");
			e.printStackTrace();
			CommonConstant.exceptionLogger.info(e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/queryAllComplaints", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAllComplaints(
			 @RequestParam("iDisplayStart") int iDisplayStart, 
			 @RequestParam("iDisplayLength") int iDisplayLength,
			 @RequestParam(value = "sEcho") int sEcho,
			@RequestParam(required = false) Boolean anonymous,
			@RequestParam(required = false) Boolean responsed,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) Date begin,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) Date end, HttpServletRequest request) {
		if (end != null) {
			end = new Date(end.getTime() + 24 * 60 * 60 * 1000 - 1);
		}

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		Community community = appContext.getCommunity();
		List<Complaint> complaints = complaintService.queryComplaintsByCommunity(community, anonymous, responsed,
				begin, end,iDisplayStart, iDisplayLength );
		List<ComplaintCommand> commands = new ArrayList<ComplaintCommand>();
		if (complaints != null) {
			for (Complaint complaint : complaints) {
				ComplaintCommand cmd = new ComplaintCommand();
				cmd.setContent(complaint.getContent());
				cmd.setId(complaint.getId());
				cmd.setCreateTime(complaint.getCreateTime());
				cmd.setCreator(complaint.getCreator().getAlias());
				long responses = complaintService.countResponsesByComplaint(complaint);
				cmd.setResponsed(responses > 0);
				commands.add(cmd);
			}
		}
		long count = complaintService.complaintsCountByCommunity(community, anonymous, responsed,
				begin, end);
		DataTable<ComplaintCommand> dataTable = new DataTable<ComplaintCommand>();
		dataTable.setAaData(commands);
		dataTable.setsEcho("" + sEcho);
		dataTable.setiTotalRecords(count);		
		dataTable.setiTotalDisplayRecords(count);
		return JsonUtil.toJson(dataTable);
	}
	
	@RequestMapping(value = "/complaintResponses/{complaintID}", method = RequestMethod.GET)
	public ModelAndView complaintResponses(@PathVariable("complaintID") long complaintID, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("community/complaintResponses", "complaintResponseCommand",
				new ComplaintResponseCommand());
		
		Complaint complaint = complaintService.queryComplaintsByID(complaintID);
		ComplaintCommand cmd = new ComplaintCommand();
		cmd.setContent(complaint.getContent());
		cmd.setId(complaint.getId());
		cmd.setCreateTime(complaint.getCreateTime());
		cmd.setCreator(complaint.getCreator().getAlias());
		modelAndView.addObject("complaint", cmd);
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/queryComplaintResponses", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryComplaintResponses(@RequestParam long complaintID, 
			@RequestParam("iDisplayStart") int iDisplayStart,
			@RequestParam("iDisplayLength") int iDisplayLength, 
			 @RequestParam(value = "sEcho") int sEcho,
			HttpServletRequest request) {
		List<ComplaintResponse> complaintResponses = complaintService.queryComplaintResponses(complaintID, iDisplayStart, iDisplayLength);
		List<com.e1858.wuye.pojo.ComplaintResponse> pojos = new ArrayList<com.e1858.wuye.pojo.ComplaintResponse>();
		if (complaintResponses != null) {
			for (ComplaintResponse reponse : complaintResponses) {
				com.e1858.wuye.pojo.ComplaintResponse pojo = new com.e1858.wuye.pojo.ComplaintResponse();
				pojo.setContent(reponse.getContent());
				pojo.setId(reponse.getId());
				pojo.setCreateTime(reponse.getCreateTime());
				pojo.setCreator(reponse.getCreator().getName());
				pojos.add(pojo);
			}
		}
		long count = complaintService.countResponsesByComplaintID(complaintID);
		DataTable<com.e1858.wuye.pojo.ComplaintResponse> dataTable = new DataTable<com.e1858.wuye.pojo.ComplaintResponse>();
		dataTable.setAaData(pojos);
		dataTable.setsEcho("" + sEcho);
		dataTable.setiTotalRecords(count);	
		dataTable.setiTotalDisplayRecords(count);
		return JsonUtil.toJson(dataTable);
	}
	
	
//	String getComplaintCreator(Complaint complaint) {
//		if (complaint.getCreator().getOpenid().equals("1")) {
//			return "匿名用户";
//		}
//		SubscriberHouse house = subscriberHouseService.querySubscriberHourseByOpenID(complaint.getCreator().getOpenid());
//		if (house!=null)
//			return house.getCommunity().getName() + "," + house.getHouse().getName() + "," + house.getHouseUnit().getName() + "," + house.getHouseFloor().getName() + "," + house.getHouseRoom().getName();
//		return "";
//	}
	
}

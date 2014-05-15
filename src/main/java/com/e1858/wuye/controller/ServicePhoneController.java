package com.e1858.wuye.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.ServicePhone;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.Response;
import com.e1858.wuye.pojo.ServicePhoneCommand;
import com.e1858.wuye.service.hibernate.CommunityService;
import com.e1858.wuye.service.hibernate.ServicePhoneService;
import com.e1858.wuye.utils.JsonUtil;

@Controller
@RequestMapping("/community")
public class ServicePhoneController
{
	@Autowired
	private ServicePhoneService servicePhoneService;
	@Autowired
	private CommunityService communityService;

	@RequestMapping(value = "/servicePhone", method = RequestMethod.GET)
	public ModelAndView servicePhone(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("community/servicePhone","servicePhoneCommand",new ServicePhoneCommand());
		return modelAndView;
	}
	
	@RequestMapping(value ="/editServicePhone/{id}", method = RequestMethod.GET)
	public ModelAndView editServicePhone(@PathVariable("id") long id, HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("community/editServicePhone","servicePhoneCommand",new ServicePhoneCommand());
		if (id != -1)
		{
			ServicePhone servicePhone = servicePhoneService.queryServicePhoneById(id);
			ServicePhoneCommand servicePhoneCommand = new ServicePhoneCommand();
			servicePhoneCommand.setId(servicePhone.getId());
			servicePhoneCommand.setTitle(servicePhone.getTitle());
			servicePhoneCommand.setPhone(servicePhone.getPhone());
			servicePhoneCommand.setDescription(servicePhone.getDescription());
			modelAndView.addObject("servicePhoneCommand",servicePhoneCommand);
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/editServicePhone", method = RequestMethod.POST)
	public ModelAndView editServicePhone (HttpServletRequest request, @Valid ServicePhoneCommand servicePhoneCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("community/servicePhone");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		if (result.hasErrors())
		{
			modelAndView.addObject("errMsg",CommonConstant.ValidMessage.Mobile_TelePhone);
			return modelAndView;
		}	
		modelAndView = new ModelAndView("redirect:/community/servicePhone");
		
		ServicePhone servicePhone = new ServicePhone();		
		if (servicePhoneCommand.getId() == -1) //Add
		{

			servicePhone.setCorp(user.getCorp());
			Community community = appContext.getCommunity();
			servicePhone.setCommunity(community);
			servicePhone.setTitle(servicePhoneCommand.getTitle());
			servicePhone.setPhone(servicePhoneCommand.getPhone());
			servicePhone.setDescription(servicePhoneCommand.getDescription());
			servicePhone.setCreator(user);
			servicePhone.setCreateTime(new Date());
			servicePhoneService.save(servicePhone);
			modelAndView.addObject("infoMsg", "新增成功！");
		}
		else {
			servicePhone = servicePhoneService.queryServicePhoneById(servicePhoneCommand.getId());
			servicePhone.setTitle(servicePhoneCommand.getTitle());
			servicePhone.setPhone(servicePhoneCommand.getPhone());
			servicePhone.setDescription(servicePhoneCommand.getDescription());
			servicePhoneService.update(servicePhone);
			modelAndView.addObject("infoMsg", "修改成功！");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/removeByID/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String removeByID(@PathVariable("id") long id, HttpServletRequest request)
	{
		Response resp = new Response();
		ServicePhone servicePhone = servicePhoneService.queryServicePhoneById(id);
		if (servicePhone == null)
		{
			resp.setSuccess(false);
			resp.setErrMsg("找不到指定的消息!");
		}
		else
		{
			try
			{
				servicePhoneService.delete(servicePhone);
				resp.setSuccess(true);				
			}
			catch (Exception e)
			{
				resp.setSuccess(false);
				resp.setErrMsg("删除失败!");
			}
		}
		return JsonUtil.toJson(resp);
	}

	@RequestMapping(value = "/readServicePhone", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readServicePhone( HttpServletRequest request)
	{
		DataTable<ServicePhoneCommand> dataGrid = new DataTable<ServicePhoneCommand>();
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);

		int iDisplayStart=Integer.valueOf(request.getParameter("iDisplayStart"));
		String sEcho=request.getParameter("sEcho");
		int iDisplayLength=Integer.valueOf(request.getParameter("iDisplayLength"));		
		SysUser user=appContext.getUser();
		List<ServicePhone> servicePhones = servicePhoneService.queryServicePhoneByCorp(user.getCorp(),appContext.getCommunity(),iDisplayStart,iDisplayLength);

		for (int i = 0; i < servicePhones.size(); i++)
		{
			ServicePhoneCommand servicePhoneCommand = new ServicePhoneCommand();
			servicePhoneCommand.setId(servicePhones.get(i).getId());
			servicePhoneCommand.setCorp(servicePhones.get(i).getCorp().getId());
			servicePhoneCommand.setCommunity(servicePhones.get(i).getCommunity().getId());
			servicePhoneCommand.setTitle(servicePhones.get(i).getTitle());
			servicePhoneCommand.setDescription(servicePhones.get(i).getDescription());
			servicePhoneCommand.setPhone(servicePhones.get(i).getPhone());
			dataGrid.getAaData().add(servicePhoneCommand);
		}
		long totalCount=servicePhoneService.getServicePhoneCount(appContext.getUser().getCorp(),appContext.getCommunity());
		dataGrid.setsEcho(sEcho);
		dataGrid.setiTotalRecords(totalCount);
		dataGrid.setiTotalDisplayRecords(totalCount);
		return JsonUtil.toJson(dataGrid);
	}	

}

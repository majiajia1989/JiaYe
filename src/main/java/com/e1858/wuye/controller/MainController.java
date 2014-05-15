package com.e1858.wuye.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang.StringUtils;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.LockCommand;
import com.e1858.wuye.service.hibernate.CommunityService;
import com.e1858.wuye.utils.Util;

@Controller
public class MainController
{
	@Autowired
	CommunityService communityService;
	@RequestMapping("/main")
	public String main(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		String community = request.getParameter("community");
		if(!StringUtils.isBlank(community)  && StringUtils.isNumeric(community))
		{
			Community object = null;
			try
			{
				object = communityService.queryCommunityById(Long.parseLong(community));
			}
			catch(Exception ex)
			{
			}
			if(null != object)
			{
				appContext.setCommunity(object);
			}
		}
		return "main";
	}
	
	@RequestMapping(value = "/lock", method = RequestMethod.GET)
	public ModelAndView lock()
	{
		return new ModelAndView("lock", "lockCommand", new LockCommand());
	}

	@RequestMapping(value = "/lock", method = RequestMethod.POST)
	public ModelAndView lock(HttpServletRequest request, @Valid LockCommand lockCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("lock");
		if (result.hasErrors())
		{
			return modelAndView;
		}
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (user.getPassword().equals(Util.getPassword(user.getName(), lockCommand.getPassword())))
		{
			return new ModelAndView("main");
		}
		else
		{
			return new ModelAndView("lock", "lockCommand", new LockCommand());
		}
	}
	
	@RequestMapping("/icons")
	public String icons()
	{
		return "icons";
	}
}

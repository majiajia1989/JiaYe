package com.e1858.wuye.controller;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.e1858.wuye.common.CommonConstant;

@Controller
@RequestMapping("/context")
public class ContextController
{
	@RequestMapping("/showContext")
	public String showConetxt(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession httpSession = request.getSession();       
        ServletContext servletContext = httpSession.getServletContext();
        //根上下文 ，保存在 ServletContext中，key是WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
        WebApplicationContext rootContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        //每个DispatcherServlet有一个自己的 WebApplicationContext上下文，这个上下文继承了 根上下文 中所有东西。 保存在 ServletContext中，key是"org.springframework.web.servlet.FrameworkServlet.CONTEXT"+Servlet名称
        WebApplicationContext webContext = RequestContextUtils.getWebApplicationContext(request);
        EhCacheCacheManager cacheManager  = (EhCacheCacheManager)webContext.getBean("cacheManager");
        Cache cache = cacheManager.getCache("SysUser");
        //ArrayList<String> caheNames= (ArrayList<String>)cacheManager.getCacheNames();
        request.setAttribute(CommonConstant.CONTEXT_CACHE, cacheManager.getCacheNames());
		return "showContext";
	}
}

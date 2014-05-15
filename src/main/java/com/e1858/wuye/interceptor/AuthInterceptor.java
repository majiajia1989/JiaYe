package com.e1858.wuye.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.exception.AuthorizationException;

public class AuthInterceptor implements HandlerInterceptor
{
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	//不拦截URL
	private List<String> excludedUrls;
	//认证页面
	private String authUrl;

	// Action之前执行
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception
	{
		String requestUri = request.getRequestURI();
		for (String url : excludedUrls)
		{
			if (!StringUtils.isEmpty(url) && requestUri.endsWith(url))
			{
				return true;
			}
		}

		HttpSession session = request.getSession();
		if (session.getAttribute(CommonConstant.CONTEXT_APP) == null)
		{
			if(!StringUtils.isEmpty(authUrl))
			{
				response.sendRedirect(request.getContextPath() + authUrl);
				return false;
			}
			else
			{
				throw new AuthorizationException();
			}
		}
		else
		{
			return true;
		}
	}

	// 生成视图之前执行
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception
	{
	}

	// 最后执行，可用于释放资源
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelandView) throws Exception
	{
	}

	public List<String> getExcludedUrls()
	{
		return excludedUrls;
	}

	public void setExcludedUrls(List<String> excludedUrls)
	{
		this.excludedUrls = excludedUrls;
	}

	public String getAuthUrl()
	{
		return authUrl;
	}

	public void setAuthUrl(String authUrl)
	{
		this.authUrl = authUrl;
	}
}

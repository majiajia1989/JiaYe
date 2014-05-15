package com.e1858.wuye.webapp.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.e1858.wechat.api.ApiWechat;

public class AppStartupListener extends ContextLoaderListener implements ServletContextListener, Runnable
{
	private boolean stop = false;
	private WebApplicationContext rootContext;
	private static final Logger webLogger = Logger.getLogger("web");
	private static final Logger apiLogger = Logger.getLogger("wechat_api");

	public AppStartupListener()
	{
	}

	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		ApiWechat.setLogger(apiLogger);
		webLogger.info("Initalizing centex......");
		rootContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent event)
	{
		stop = true;
	}

	@Override
	public void run()
	{
		while (!stop)
		{
			try
			{
				Thread.sleep(60000L);
				work();
			}
			catch (Exception ex)
			{
			}

		}
	}

	private void work()
	{
		System.out.println("AppStartupListener.Run");
	}

}

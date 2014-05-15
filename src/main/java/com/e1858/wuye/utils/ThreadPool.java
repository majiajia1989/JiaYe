package com.e1858.wuye.utils;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool
{
	private static ExecutorService executor;


	private ThreadPool()
	{
	}

	public static void execute(Runnable run)
	{
		if (null == executor)
		{
			executor = Executors.newCachedThreadPool();
		}
		executor.execute(run);
	}
	
	public static void shutdown()
	{
		if(null != executor)
		{
			executor.shutdown();
		}
	}
}
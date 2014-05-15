package com.e1858.wuye.service.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.Weixin_UserInfoDao;
import com.e1858.wuye.entity.hibernate.Weixin_UserInfo;

@Service
@Transactional
public class Weixin_UserInfoService
{
	@Autowired
	private Weixin_UserInfoDao weixin_UserInfoDao;
	
	@Transactional(readOnly = true)
	@Cacheable(value = "SysUser", key = "#openid")
	public Weixin_UserInfo getUserByUserName(String openid)
	{
		return weixin_UserInfoDao.getUserByUserName(openid);
	}
	
}

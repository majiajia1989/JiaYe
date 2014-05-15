package com.e1858.wuye.service.hibernate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wechat.api.ApiBase;
import com.e1858.wechat.api.json.base.Access_Token;
import com.e1858.wuye.dao.hibernate.Weixin_Access_TokenDao;
import com.e1858.wuye.dao.hibernate.Weixin_GongHaoDao;
import com.e1858.wuye.entity.hibernate.Weixin_Access_Token;
import com.e1858.wuye.entity.hibernate.Weixin_GongHao;

;

@Service
@Transactional
class Weixin_Access_TokenService
{

	@Autowired
	private Weixin_Access_TokenDao weixin_Access_TokenDao;

	@Transactional(readOnly = true)
	public Weixin_Access_Token getWeixin_Access_TokenByCorp(long corp)
	{
		return weixin_Access_TokenDao.getWeixin_Access_TokenByCorp(corp);
	}
	
	public void save(Weixin_Access_Token weixin_Access_Token)
	{
		weixin_Access_TokenDao.save(weixin_Access_Token);
	}
	
	public void update(Weixin_Access_Token weixin_Access_Token)
	{
		weixin_Access_TokenDao.update(weixin_Access_Token);
	}
}

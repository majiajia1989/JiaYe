package com.e1858.wuye.service.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.Weixin_GongHaoDao;
import com.e1858.wuye.entity.hibernate.Weixin_GongHao;

@Service
@Transactional
public class Weixin_GongHaoService
{
	@Autowired
	private Weixin_GongHaoDao Weixin_GongHaoDao;
	
	@Transactional(readOnly = true)
	public Weixin_GongHao getGongHao()
	{
		return Weixin_GongHaoDao.getGongHao();
	}
	
}

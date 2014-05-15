package com.e1858.wuye.service.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.ConsumeTypeDao;
import com.e1858.wuye.dao.hibernate.MsgTypeDao;
import com.e1858.wuye.entity.hibernate.MsgType;
import com.e1858.wuye.entity.hibernate.SysConsumeType;

@Service
@Transactional
public class ConsumeTypeService
{

	@Autowired
	private ConsumeTypeDao consumeTypeDao;
	
	@Transactional(readOnly = true)
	public SysConsumeType queryConsumeTypeById(long consumeTypeId)
	{
		return consumeTypeDao.get(consumeTypeId);
	}
}

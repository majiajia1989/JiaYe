package com.e1858.wuye.service.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.MsgTypeDao;
import com.e1858.wuye.entity.hibernate.MsgType;

@Service
@Transactional
public class MsgTypeService
{

	@Autowired
	public MsgTypeDao msgTypeDao;
	
	public MsgType getMsgType(String type){
		return msgTypeDao.getMsgTypeByType(type);
	}
}

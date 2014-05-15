package com.e1858.wuye.service.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.MTDao;
import com.e1858.wuye.entity.hibernate.MT;

@Service
@Transactional
public class MTService
{

	@Autowired
	private MTDao mtDao;
	
	public void saveMT(MT mt){
		mtDao.save(mt);
	}
}

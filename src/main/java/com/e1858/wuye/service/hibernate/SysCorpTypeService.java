package com.e1858.wuye.service.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.CorpDao;
import com.e1858.wuye.dao.hibernate.CorpTypeDao;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysCorpType;
import com.e1858.wuye.entity.hibernate.SysUser;


/**
 * Corp 插入和更新
 * @author hnhx
 *
 */
@Service
@Transactional
public class SysCorpTypeService {

	@Autowired
	private CorpTypeDao  corpTypeDao;


	/**
	 * 根据corpID加载Corp对象
	 * @param corpId
	 * @return
	 */
    @Transactional(readOnly = true)
	public SysCorpType getCorpById(long corpTypeId){
		return corpTypeDao.get(corpTypeId);
	}
}

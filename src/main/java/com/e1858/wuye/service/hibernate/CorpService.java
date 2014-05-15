package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.CorpDao;
import com.e1858.wuye.dao.hibernate.SysUserDao;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.DataTableRowCnt;


/**
 * Corp 插入和更新
 * @author hnhx
 *
 */
@Service
@Transactional
public class CorpService {

	@Autowired
	private CorpDao  corpDao;
	@Autowired
	private SysUserDao  userDao;

	public void save(SysCorp corp){
		corpDao.save(corp);
	}
	public void update(SysCorp corp){
		corpDao.update(corp);
	}

	
    @Transactional(readOnly = true)
	public SysCorp queryCorpById(long corpId){
		return corpDao.get(corpId);
	}
    
    @Transactional(readOnly = true)
	public List<SysCorp> queryCorps(){
		return corpDao.queryCorps();
	}

    @Transactional(readOnly = true)
	public List<SysCorp> queryCorps(int startRow,int pageSize,DataTableRowCnt rowCnt){
		return corpDao.queryCorpsWithPage(startRow,pageSize,rowCnt);
	}
}

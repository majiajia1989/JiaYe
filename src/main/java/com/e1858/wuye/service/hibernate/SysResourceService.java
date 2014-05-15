package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.SysResourceDao;
import com.e1858.wuye.entity.hibernate.SysResource;
import com.e1858.wuye.entity.hibernate.SysRole;

@Service
@Transactional
public class SysResourceService
{
	@Autowired
	SysResourceDao sysResourceDao;
	
	@Transactional(readOnly = true)
	public List<SysResource> queryResourceByRole(SysRole sysRole)
	{
		return sysResourceDao.queryResourceByRole(sysRole);
	}
	
}

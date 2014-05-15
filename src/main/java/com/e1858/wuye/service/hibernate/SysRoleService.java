package com.e1858.wuye.service.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.SysResourceDao;
import com.e1858.wuye.dao.hibernate.SysRoleDao;
import com.e1858.wuye.dao.hibernate.SysRoleResourceDao;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysResource;
import com.e1858.wuye.entity.hibernate.SysRole;
import com.e1858.wuye.entity.hibernate.SysRoleResource;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.exception.UserExistException;
import com.e1858.wuye.utils.Util;

@Service
@Transactional
public class SysRoleService
{
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysResourceDao sysResourceDao;
	@Autowired
	private SysRoleResourceDao sysRoleResourceDao;

	public void save(SysRole sysRole) throws Exception
	{
		if (sysRoleDao.queryRoleByCorpAndName(sysRole.getCorp(), sysRole.getName()) != null)
		{
			throw new Exception("用户组已经存在！");
		}
		sysRoleDao.save(sysRole);
	}

	public void save(SysRole sysRole, String resources) throws Exception
	{
		if (sysRoleDao.queryRoleByCorpAndName(sysRole.getCorp(), sysRole.getName()) != null)
		{
			throw new Exception("用户组已经存在！");
		}
		List<SysResource> sysResources = new ArrayList<SysResource>();
		for (String resourceId : resources.split(",", 0))
		{
			if (!Util.isLong(resourceId))
			{
				continue;
			}

			SysResource sysResource = sysResourceDao.get(Long.parseLong(resourceId));
			if (sysResource == null)
			{
				continue;
			}

			if (!existsOnSysResourcesList(sysResources, Long.parseLong(resourceId)))
			{
				sysResources.add(sysResource);
			}

			for (SysResource sysResourceTmp : searchParentResources(sysResource))
			{
				if (!existsOnSysResourcesList(sysResources, sysResourceTmp.getId()))
				{
					sysResources.add(sysResourceTmp);
				}
			}
		}

		sysRoleDao.save(sysRole);
		
		for(SysResource sysResource:sysResources)
		{
			SysRoleResource sysRoleResource = new SysRoleResource();
			sysRoleResource.setSysRole(sysRole);
			sysRoleResource.setSysResource(sysResource);
			sysRoleResource.setCreator(sysRole.getCreator());
			sysRoleResource.setCreateTime(new Date());
			sysRoleResourceDao.save(sysRoleResource);
		}
	}

	public void update(SysRole sysRole)
	{
		sysRoleDao.update(sysRole);
	}

	public void update(SysRole sysRole,String resources)
	{
		List<SysResource> sysResources = new ArrayList<SysResource>();
		for (String resourceId : resources.split(",", 0))
		{
			if (!Util.isLong(resourceId))
			{
				continue;
			}

			SysResource sysResource = sysResourceDao.get(Long.parseLong(resourceId));
			if (sysResource == null)
			{
				continue;
			}

			if (!existsOnSysResourcesList(sysResources, Long.parseLong(resourceId)))
			{
				sysResources.add(sysResource);
			}

			for (SysResource sysResourceTmp : searchParentResources(sysResource))
			{
				if (!existsOnSysResourcesList(sysResources, sysResourceTmp.getId()))
				{
					sysResources.add(sysResourceTmp);
				}
			}
		}

		sysRoleDao.update(sysRole);
		sysRoleResourceDao.deleteRoleResourceByRole(sysRole);
		for(SysResource sysResource:sysResources)
		{
			SysRoleResource sysRoleResource = new SysRoleResource();
			sysRoleResource.setSysRole(sysRole);
			sysRoleResource.setSysResource(sysResource);
			sysRoleResource.setCreator(sysRole.getCreator());
			sysRoleResource.setCreateTime(new Date());
			sysRoleResourceDao.save(sysRoleResource);
		}

	}
	
	public void delete(SysRole sysRole)
	{
		sysRoleDao.delete(sysRole);
	}

	@Transactional(readOnly = true)
	public SysRole queryRoleByRoleId(long id)
	{
		return sysRoleDao.get(id);
	}

	@Transactional(readOnly = true)
	public List<SysRole> queryRoleByUser(SysUser sysUser)
	{
		return sysRoleDao.queryRoleByUser(sysUser);
	}

	@Transactional(readOnly = true)
	public List<SysRole> queryRoleByCorp(SysCorp sysCorp)
	{
		return sysRoleDao.queryRoleByCorp(sysCorp);
	}

	public List<SysRoleResource> queryRoleResourceByRole(SysRole sysRole)
	{
		return sysRoleResourceDao.querySysRoleResourceByRole(sysRole);
	}
	
	public String queryResourceIdsByRole(SysRole sysRole)
	{
		StringBuilder result =  new StringBuilder("");
		for(SysRoleResource sysRoleResource:sysRoleResourceDao.querySysRoleResourceByRole(sysRole))
		{
			result.append(sysRoleResource.getSysResource().getId()).append(",");
		}
		return result.toString();
	}
	
	private List<SysResource> searchParentResources(SysResource sysResource)
	{
		List<SysResource> result = new ArrayList<SysResource>();
		if (sysResource.getId() == 1)
		{
			return result;
		}
		result.add(sysResourceDao.get(sysResource.getParent()));
		result.addAll(searchParentResources(sysResourceDao.get(sysResource.getParent())));
		return result;
	}

	private boolean existsOnSysResourcesList(List<SysResource> sysResources, long sysResourceId)
	{
		for(SysResource sysResource:sysResources)
		{
			if(sysResource.getId()==sysResourceId)
			{
				return true;
			}
		}
		return false;
	}

}

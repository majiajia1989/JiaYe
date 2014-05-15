package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.CommunityDao;
import com.e1858.wuye.dao.hibernate.CorpDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.exception.UserExistException;
import com.e1858.wuye.pojo.DataTableRowCnt;

/**
 * Corp 插入和更新
 * 
 * @author hnhx
 * 
 */
@Service
@Transactional
public class CommunityService
{

	@Autowired
	private CommunityDao communityDao;

	public void save(Community community) throws Exception
	{
		if (queryCommunitysByCorpAndName(community.getCorp(), community.getName()) == null)
		{
			communityDao.save(community);
		}
		else
		{
			throw new Exception("用户名已经存在");
		}
	}

	public void update(Community community)
	{
		communityDao.update(community);
	}

	@Transactional(readOnly = true)
	public Community queryCommunityById(long communityId)
	{
		return communityDao.get(communityId);
	}

	@Transactional(readOnly = true)
	public List<Community> queryCommunitysByCorp(SysCorp corp)
	{
		return communityDao.queryCommunitysByCorp(corp);
	}

	@Transactional(readOnly = true)
	public List<Community> queryCommunitysByCorp(SysCorp corp,int startRow, int pageSize, DataTableRowCnt rowCnt)
	{
		return communityDao.queryCommunitysByCorpWithPage(corp,startRow,pageSize,rowCnt);
	}

	public Community queryCommunitysByCorpAndName(SysCorp corp, String name)
	{
		return communityDao.queryCommunitysByCorpAndName(corp, name);
	}
}

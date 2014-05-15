package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.CommunityImageDao;
import com.e1858.wuye.dao.hibernate.CommunityWelcomeImageDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.CommunityImage;
import com.e1858.wuye.entity.hibernate.CommunityWelcomeImage;


@Service
@Transactional
public class CommunityImageService {

	@Autowired
	private CommunityImageDao  coummunityImageDao;
	@Autowired
	private CommunityWelcomeImageDao communityWelcomeImageDao;

	public void save(CommunityWelcomeImage communityWelcomImage){
		communityWelcomeImageDao.save(communityWelcomImage);
	}	
	public void delete(CommunityWelcomeImage communityWelcomImage){
		communityWelcomeImageDao.delete(communityWelcomImage);
	}
    @Transactional(readOnly = true)
	public CommunityWelcomeImage queryCommunityById(Community community){
		return communityWelcomeImageDao.getCommunityWelcomeImages(community);
	}	

	public void save(CommunityImage communityImage){
		coummunityImageDao.save(communityImage);
	}
	
	public void delete(CommunityImage communityImage){
		coummunityImageDao.delete(communityImage);
	}

    @Transactional(readOnly = true)
	public CommunityImage queryCommunityImageById(long id){
		return coummunityImageDao.getCommunityImage(id);
	}
    
    @Transactional(readOnly = true)
	public List<CommunityImage> queryCommunityImages(Community community,int startRow,int rows){
		return coummunityImageDao.getCommunityImages(community,startRow,rows);
	}
    
	public long getCommunityImageCount(Community community) {
		return coummunityImageDao.countCommunityImageByCommunity(community);
	}
}

package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.CorpImageDao;
import com.e1858.wuye.dao.hibernate.CorpWelcomeImageDao;
import com.e1858.wuye.entity.hibernate.CorpImage;
import com.e1858.wuye.entity.hibernate.CorpWelcomeImage;
import com.e1858.wuye.entity.hibernate.SysCorp;


@Service
@Transactional
public class CorpImageService {

	@Autowired
	private CorpImageDao  corpImageDao;
	@Autowired
	private CorpWelcomeImageDao corpWelcomeImageDao;

	public void save(CorpWelcomeImage corpWelcomImage){
		corpWelcomeImageDao.save(corpWelcomImage);
	}	
	public void delete(CorpWelcomeImage corpWelcomImage){
		corpWelcomeImageDao.delete(corpWelcomImage);
	}
    @Transactional(readOnly = true)
	public CorpWelcomeImage queryCorpById(SysCorp corp){
		return corpWelcomeImageDao.getCorpWelcomeImages(corp);
	}	
	
	public void save(CorpImage corpImage){
		corpImageDao.save(corpImage);
	}
	
	public void delete(CorpImage corpImage){
		corpImageDao.delete(corpImage);
	}

    @Transactional(readOnly = true)
	public CorpImage queryCorpById(long id){
		return corpImageDao.getCorpImage(id);
	}
    
    @Transactional(readOnly = true)
	public List<CorpImage> queryCorpImageByCorp(SysCorp corp,int startRow,int rows){
		return corpImageDao.getCorpImages(corp,startRow,rows);
	}
    
	public long getCorpImageCount(SysCorp corp) {
		return corpImageDao.countCorpImageByCorp(corp);
	}
}

package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.ServicePhoneDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.ServicePhone;
import com.e1858.wuye.entity.hibernate.SysCorp;


@Service
@Transactional
public class ServicePhoneService {

	@Autowired
	private ServicePhoneDao  servicePhoneDao;

	public void save(ServicePhone servicePhone){
		servicePhoneDao.save(servicePhone);
	}
	
	public void update(ServicePhone servicePhone){
		servicePhoneDao.update(servicePhone);
	}
	
	public void delete(ServicePhone servicePhone){
		servicePhoneDao.delete(servicePhone);
	}

    @Transactional(readOnly = true)
	public ServicePhone queryServicePhoneById(long id){
		return servicePhoneDao.getServicePhone(id);
	}
    
    @Transactional(readOnly = true)
	public List<ServicePhone> queryServicePhoneByCorp(SysCorp corp,Community community,int startRow,int rows){
		return servicePhoneDao.getServicePhones(corp, community,startRow,rows);
	}
    
	public long getServicePhoneCount(SysCorp corp,Community community) {
		return servicePhoneDao.countServicePhone(corp,community);
	}
}

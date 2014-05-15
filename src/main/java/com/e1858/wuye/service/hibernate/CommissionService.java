package com.e1858.wuye.service.hibernate;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.CommissionDao;
import com.e1858.wuye.dao.hibernate.CommissionResponseDao;
import com.e1858.wuye.dao.hibernate.CommissionTemplateDao;
import com.e1858.wuye.dao.hibernate.CommissionTypeDao;
import com.e1858.wuye.entity.hibernate.Commission;
import com.e1858.wuye.entity.hibernate.CommissionResponse;
import com.e1858.wuye.entity.hibernate.CommissionTemplate;
import com.e1858.wuye.entity.hibernate.CommissionType;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.SysCorp;

@Service
@Transactional
public class CommissionService
{

	@Autowired
	private CommissionDao  commissionDao;
	@Autowired
	private CommissionResponseDao commissionResponseDao;
	
	@Autowired
	private CommissionTypeDao commissionTypeDao;
	
	@Autowired
	private CommissionTemplateDao commissionTemplateDao;
	@Transactional(readOnly=true)
	public List<CommissionType> getCommissionTypes(Community community) 
	{
		return commissionTypeDao.getCommissionTypeByCorpAndCommunity(community);
	}
	
	@Transactional(readOnly=true)
	public List<CommissionType> getCommissionTypes(Community community,int index,int count) 
	{
		return commissionTypeDao.getCommissionTypeByCorpAndCommunity(community,index,count);
	}
	
	public void deleteCommissionType(CommissionType commissionType){
		commissionTypeDao.delete(commissionType);
	}
	public void deleteCommissionTemplate(CommissionTemplate commissionTemplate){
		commissionTemplateDao.delete(commissionTemplate);
	}
	
	public void saveCommissionType(CommissionType commissionType){
		commissionTypeDao.save(commissionType);
	}
	public void updateCommissionType(CommissionType commissionType){
		commissionTypeDao.update(commissionType);
	}
	public void saveCommisionTemplate(CommissionTemplate commissionTemplate){
		commissionTemplateDao.save(commissionTemplate);
	}
	@Transactional(readOnly=true)
	public CommissionType getCommissionTypeByName(SysCorp corp,Community community,String name){
		return commissionTypeDao.getCommissionTypeByName(corp, community, name);
	}
	@Transactional(readOnly=true)
	public List<CommissionTemplate> getCommissionTemplates(CommissionType commissionType,int index,int count){
		return commissionTemplateDao.getCommissionTemplateByType(commissionType,index,count);
	}
	@Transactional(readOnly=true)
	public CommissionType getCommissionTypeByID(long id){
		return commissionTypeDao.getCommissionTypeByID(id);
	}
	@Transactional(readOnly=true)
	public CommissionTemplate getCommissionTemplateByID(long id){
		return commissionTemplateDao.getCommissionTemplateByID(id);
	}
	@Transactional(readOnly=true)
	public List<Commission> getCommissionsByType(Community community,CommissionType type){
		return commissionDao.getCommissionByType(community, type);
	}
	@Transactional(readOnly=true)
	public List<Commission> getCommissionsByDate(Community community,CommissionType type, String keyword, Date begin, Date end,int index,int count,String response,Boolean isCount){
		return commissionDao.getCommissionByTypeAndDate(community, type, keyword, begin, end,index,count,response,isCount);
	}
	@Transactional(readOnly=true)
	public long getTotalCount(Community community,CommissionType type, String keyword, Date begin, Date end,String response){
		return commissionDao.getTotalCount(community,type,keyword,begin,end,response);
	}
	
	@Transactional(readOnly=true)
	public Commission getCommission(long id){
		return commissionDao.getCommissionByID(id);
	}
	@Transactional(readOnly=true)
	public List<CommissionResponse> getCommissionResponses(Commission commission,int index,int count){
		return commissionResponseDao.queryResponsesByCommission(commission,index,count);
	}
	public void saveCommissionResponse(CommissionResponse commissionResponse){
		commissionResponseDao.save(commissionResponse);
	}

	public void updateCommissionTemplate(CommissionTemplate commissionTemplate)
	{
		commissionTemplateDao.update(commissionTemplate);
	}
	
	@Transactional(readOnly=true)
	public long getTotalResponseCount(Commission commission){
		return commissionResponseDao.getTotalCount(commission);
	}
	@Transactional(readOnly=true)
	public long getTotalTypeCount(Community community)
	{
		return commissionTypeDao.getTotalCount(community);
	}
	@Transactional(readOnly=true)
	public long getTotalTemplateCount(CommissionType type)
	{
		return commissionTemplateDao.getTotalCount(type);
	}
}

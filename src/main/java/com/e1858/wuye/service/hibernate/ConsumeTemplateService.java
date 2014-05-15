package com.e1858.wuye.service.hibernate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.ConsumeInfoDao;
import com.e1858.wuye.dao.hibernate.ConsumeTemplateDao;
import com.e1858.wuye.dao.hibernate.ConsumeTypeDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.ConsumeInfo;
import com.e1858.wuye.entity.hibernate.ConsumeTemplate;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseFloor;
import com.e1858.wuye.entity.hibernate.HouseRoom;
import com.e1858.wuye.entity.hibernate.HouseUnit;
import com.e1858.wuye.entity.hibernate.SysConsumeType;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.exception.UserExistException;
import com.e1858.wuye.pojo.DataTableRowCnt;

@Service
@Transactional
public class ConsumeTemplateService
{
	@Autowired
	private ConsumeTemplateDao consumeTemplateDao;

	public void save(ConsumeTemplate consumeTemplate)
	{
		consumeTemplateDao.save(consumeTemplate);
	}

	public void update(ConsumeTemplate consumeTemplate)
	{
		consumeTemplateDao.update(consumeTemplate);
	}
	
	public void delete(ConsumeTemplate consumeTemplate)
	{
		consumeTemplateDao.delete(consumeTemplate);
	}
	
	@Transactional(readOnly = true)
	public ConsumeTemplate queryConsumeTemplateById(long id)
	{
		return consumeTemplateDao.get(id);
	}
}

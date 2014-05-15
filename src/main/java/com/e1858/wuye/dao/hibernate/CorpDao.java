package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.DataTableRowCnt;
/**
 * SysCorp对象Dao
 * @author hnhx
 *
 */
@Repository("CorpDao")
public class CorpDao extends BaseDao<SysCorp>{
	private final String GET_CORPS = "from SysCorp where id<>1";
	public List<SysCorp> queryCorps(){
		return (List<SysCorp>)find(GET_CORPS);
	}
	public List<SysCorp> queryCorpsWithPage(int startRow,int pageSize,DataTableRowCnt rowCnt){
		List<Object> params = new ArrayList<Object>();
		String sql_Count="select count(*) " + GET_CORPS;
		rowCnt.setRowCnt(count(sql_Count,params));
		return findWithPage(GET_CORPS,startRow,pageSize,params);
	}
}

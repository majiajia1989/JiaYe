package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.DataTableRowCnt;
import com.e1858.wuye.pojo.ProcResult;

/**
 * User对象Dao
 */
@Repository("sysUserDao")
public class SysUserDao extends BaseDao<SysUser>
{
	private final String GET_USER_BY_USERNAME = "from SysUser u where u.name = ?";

	private final String QUERY_USER_BY_USERNAME = "from SysUser u where u.name like ?";

	private final String QUERY_USER_BY_CORP = "from SysUser u where u.corp = ? order by u.createTime desc";

	private final String QUERY_ALL_USER = "from SysUser u order by u.createTime desc";

	/**
	 * 根据用户名查询User对象
	 * 
	 * @param userName
	 *            用户名
	 * @return 对应userName的User对象，如果不存在，返回null。
	 */
	public SysUser getUserByUserName(String userName)
	{
		List<SysUser> users = (List<SysUser>) find(GET_USER_BY_USERNAME, userName);
		if (users.size() == 0)
		{
			return null;
		}
		else
		{
			return users.get(0);
		}
	}

	/**
	 * 根据用户名为模糊查询条件，查询出所有前缀匹配的User对象
	 * 
	 * @param userName
	 *            用户名查询条件
	 * @return 用户名前缀匹配的所有User对象
	 */
	public List<SysUser> queryUserByUserName(String userName)
	{
		return (List<SysUser>) find(QUERY_USER_BY_USERNAME, userName + "%");
	}

	public List<SysUser> queryUserByCorp(SysCorp corp)
	{
		return (List<SysUser>) find(QUERY_USER_BY_CORP, corp);
	}

	public List<SysUser> queryUserByCorpWithPage(SysCorp corp,int startRow,int pageSize,DataTableRowCnt rowCnt)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(corp);
		String sql_Count="select count(*) " + QUERY_USER_BY_CORP;
		rowCnt.setRowCnt(count(sql_Count,params));
		return findWithPage(QUERY_USER_BY_CORP,startRow,pageSize,params);
	}

	public List<SysUser> queryAllUser()
	{
		return (List<SysUser>) find(QUERY_ALL_USER);
	}

	public ProcResult registUser(SysUser sysUser,SysCorp sysCorp)
	{
		ProcResult result = new ProcResult();
		String sql = "{CALL p_RegistUser(?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?,?)}";
		ArrayList<Object> params = new ArrayList<Object>();
		// in map varchar(1000),in creator long)
		params.add(sysUser.getName());
		params.add(sysUser.getPassword());
		params.add(sysUser.getEmail());
		params.add(sysUser.getAddress());
		params.add(sysUser.getQq());
		params.add(sysCorp.getType().getId());
		params.add(sysCorp.getProvince());
		params.add(sysCorp.getCity());
		params.add(sysCorp.getCounty());
		params.add(sysCorp.getName());
		params.add(sysUser.getMobilePhone());
		params.add(sysCorp.getTelePhone());
		params.add(sysCorp.getDescript());
		params.add(sysCorp.getCopyright());
		params.add(sysCorp.getMap());
		params.add(sysUser.getCreator());
		
		
		for (Map map : (List<Map>) createSqlQuery(sql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list())
		{
			result.setProcStatus((Boolean)map.get("procStatus"));
			result.setProcText(map.get("procText").toString());
			result.setProcReserve(map.get("procReserve").toString());
		}
		return result;
	}
}

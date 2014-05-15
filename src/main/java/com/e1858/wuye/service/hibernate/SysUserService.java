package com.e1858.wuye.service.hibernate;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.CorpDao;
import com.e1858.wuye.dao.hibernate.LoginLogDao;
import com.e1858.wuye.dao.hibernate.SysRoleDao;
import com.e1858.wuye.dao.hibernate.SysRoleUserDao;
import com.e1858.wuye.dao.hibernate.SysUserDao;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysLoginLog;
import com.e1858.wuye.entity.hibernate.SysRole;
import com.e1858.wuye.entity.hibernate.SysRoleUser;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.exception.UserExistException;
import com.e1858.wuye.exception.CorpException;
import com.e1858.wuye.pojo.DataTableRowCnt;
import com.e1858.wuye.pojo.ProcResult;

/**
 * 用户管理服务器，负责查询用户、注册用户、锁定用户等操作
 * 
 */
@Service
@Transactional
public class SysUserService
{

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private CorpDao sysCorpDao;
	@Autowired
	private SysRoleUserDao sysRoleUserDao;

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private LoginLogDao loginLogDao;

	/**
	 * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
	 * 
	 * @param user
	 */

	public void register(SysUser user) throws UserExistException
	{
		if (this.getUserByName(user.getName()) != null)
		{
			throw new UserExistException("用户名已经存在");
		}
		else
		{
			sysUserDao.save(user);
		}
	}
	public void register(SysUser user,long roleId) throws UserExistException
	{
		if (this.getUserByName(user.getName()) != null)
		{
			throw new UserExistException("用户名已经存在");
		}
		else
		{
			sysUserDao.save(user);
			SysRoleUser roleUser = new SysRoleUser();
			roleUser.setSysRole(sysRoleDao.get(roleId));
			roleUser.setSysUser(user);
			roleUser.setCreator(sysUserDao.get(user.getCreator()));
			roleUser.setCreateTime(new Date());
			sysRoleUserDao.save(roleUser);			
		}
	}
	public void register(SysUser user, SysCorp corp) throws Exception
	{
		ProcResult result = sysUserDao.registUser(user,corp);
		if(result.getProcStatus()==false)
		{
			throw new Exception(result.getProcText());
		}
		
		/*if (this.getUserByName(user.getName()) != null)
		{
			throw new UserExistException("用户名已经存在");
		}
		else
		{
			sysCorpDao.save(corp);
			user.setCorp(corp);
			sysUserDao.save(user);
		}
		*/
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	public void update(SysUser sysUser)
	{
		sysUserDao.update(sysUser);
	}

	/**
	 * 根据用户名/密码查询 User对象
	 * 
	 * @param userName
	 *            用户名
	 * @return User
	 */
	@Transactional(readOnly = true)
	@Cacheable(value = "SysUser", key = "#userName")
	public SysUser getUserByName(String userName)
	{
		return sysUserDao.getUserByUserName(userName);
	}

	/**
	 * 根据userId加载User对象
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public SysUser getUserById(long userId)
	{
		return sysUserDao.get(userId);
	}

	/**
	 * 将用户锁定，锁定的用户不能够登录
	 * 
	 * @param userName
	 *            锁定目标用户的用户名
	 */
	public void lockUser(String userName)
	{
		SysUser sysUser = sysUserDao.getUserByUserName(userName);
		sysUser.setLocked(SysUser.USER_LOCK);
		sysUserDao.update(sysUser);
	}

	/**
	 * 解除用户的锁定
	 * 
	 * @param userName
	 *            解除锁定目标用户的用户名
	 */
	public void unlockUser(String userName)
	{
		SysUser user = sysUserDao.getUserByUserName(userName);
		user.setLocked(SysUser.USER_UNLOCK);
		sysUserDao.update(user);
	}

	/**
	 * 根据用户名为条件，执行模糊查询操作
	 * 
	 * @param userName
	 *            查询用户名
	 * @return 所有用户名前导匹配的userName的用户
	 */
	@Transactional(readOnly = true)
	public List<SysUser> queryUserByUserName(String userName)
	{
		return sysUserDao.queryUserByUserName(userName);
	}

	/**
	 * 登陆成功
	 * 
	 * @param user
	 */
	@Cacheable(value = "SysUser", key = "#user.name")
	public void loginSuccess(SysUser user)
	{
		SysLoginLog loginLog = new SysLoginLog();
		loginLog.setSysUser(user);
		loginLog.setIp(user.getLastIp());
		loginLog.setLoginDate(new Date());
		sysUserDao.update(user);
		loginLogDao.save(loginLog);
	}

	@Transactional(readOnly = true)
	public List<SysUser> queryUserByCorp(SysCorp corp)
	{
		return sysUserDao.queryUserByCorp(corp);
	}
	
	@Transactional(readOnly = true)
	public List<SysUser> queryUserByCorp(SysCorp corp,int startRow,int pageSize,DataTableRowCnt rowCnt)
	{
		return sysUserDao.queryUserByCorpWithPage(corp,startRow,pageSize,rowCnt);
	}
	
	@Transactional(readOnly = true)
	public List<SysUser> queryAllUser()
	{
		return sysUserDao.queryAllUser();
	}
	@Transactional(readOnly = true)
	public List<SysLoginLog> queryAllLog(SysUser user){
		return loginLogDao.queryLogs(user);
	}
	@Transactional(readOnly = true)
	public List<SysLoginLog> queryAllLog(SysUser user,int index,int count){
		return loginLogDao.queryLogs(user,index,count);
	}
	@Transactional(readOnly = true)
	public long getTotalCount(SysUser user){
		return loginLogDao.getTotalCount(user);
	}
}

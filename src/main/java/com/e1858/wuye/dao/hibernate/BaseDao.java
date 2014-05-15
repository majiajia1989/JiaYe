package com.e1858.wuye.dao.hibernate;

import java.io.Serializable;
import java.util.List;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.Field;

import javax.persistence.Id;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;

public class BaseDao<T>
{
	@Autowired
	private SessionFactory sessionFactory;
	private final Class<T> entityClass;
	protected final String HQL_FROM;
	private final String HQL_LIST_ALL;
	private final String HQL_COUNT_ALL;
	private final String HQL_OPTIMIZE_PRE_LIST_ALL;
	private final String HQL_OPTIMIZE_NEXT_LIST_ALL;
	private final String HQL_LIST_BY_ID_IN;
	private String pkName = null;

	/**
	 * 通过反射获取子类确定的泛型类
	 */
	public BaseDao()
	{
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class<T>) params[0];
		Field[] fields = this.entityClass.getDeclaredFields();

		for (Field f : fields)
		{
			if (f.isAnnotationPresent(Id.class))
			{
				this.pkName = f.getName();
			}
		}

		// Assert.notNull(pkName);
		// TODO @Entity name not null
		HQL_FROM = "from " + this.entityClass.getSimpleName() + " ";
		HQL_LIST_ALL = "from " + this.entityClass.getSimpleName() + " order by " + pkName + " desc";
		HQL_OPTIMIZE_PRE_LIST_ALL = "from " + this.entityClass.getSimpleName() + " where " + pkName + " > ? order by " + pkName + " asc";
		HQL_OPTIMIZE_NEXT_LIST_ALL = "from " + this.entityClass.getSimpleName() + " where " + pkName + " < ? order by " + pkName + " desc";
		HQL_COUNT_ALL = " select count(*) from " + this.entityClass.getSimpleName();
		HQL_LIST_BY_ID_IN = HQL_FROM + " where " + pkName + " in (:ids)";
	}

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存POJO
	 * 
	 * @param entity
	 */
	public void save(T entity)
	{
		this.getCurrentSession().save(entity);
	}

	/**
	 * 更改POJO
	 * 
	 * @param entity
	 */
	public void update(T entity)
	{
		this.getCurrentSession().update(entity);
	}

	/**
	 * 更改或更改POJO
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(T entity)
	{
		this.getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 融合POJO
	 * 
	 * @param entity
	 */
	public void merge(T entity)
	{
		this.getCurrentSession().merge(entity);
	}

	/**
	 * 删除POJO
	 * 
	 * @param entity
	 */
	public void delete(T entity)
	{
		this.getCurrentSession().delete(entity);
	}

	/**
	 * 创建Query对象
	 * 
	 * @param hql
	 */
	public Query createQuery(String hql)
	{
		return this.getCurrentSession().createQuery(hql);
	}

	/**
	 * 创建Query对象.
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下：
	 * 
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values
	 *            链表.
	 */
	public Query createQuery(String hql, List<Object> values)
	{
		Query query = this.getCurrentSession().createQuery(hql);
		for (int i = 0; i < values.size(); i++)
		{
			query.setParameter(i, values.get(i));
		}
		return query;
	}

	/**
	 * 创建Query对象.
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下：
	 * 
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values
	 *            可变参数.
	 */
	public Query createQuery(String hql, Object... values)
	{
		Query query = this.getCurrentSession().createQuery(hql);
		for (int i = 0; i < values.length; i++)
		{
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 执行HQL查询
	 * 
	 * @param sql
	 * @return 查询结果
	 */
	public List<T> find(String hql)
	{
		Query query = createQuery(hql);
		return query.list();
	}

	/**
	 * 执行带参的HQL查询
	 * 
	 * @param hql
	 * @param param
	 * @return 查询结果
	 */
	public List<T> find(String hql, List<Object> param)
	{
		Query query = createQuery(hql, param);
		return query.list();
	}

	/**
	 * 执行带参的HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @return 查询结果
	 */
	public List<T> find(String hql, Object... param)
	{
		Query query = createQuery(hql, param);
		return query.list();
	}

	/**
	 * 执行带参的HQL分页查询
	 * 
	 * @param hql
	 * @param page
	 * @param rows
	 * @param param
	 * @return 查询结果
	 */
	public List<T> find(String hql, int page, int rows, List<Object> param)
	{
		Query query = createQuery(hql, param);
		return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	/**
	 * 执行带参的HQL分页查询
	 * 
	 * @param hql
	 * @param page
	 * @param rows
	 * @param param
	 * @return 查询结果
	 */
	public List<T> findWithPage(String hql, int startRow, int rows, List<Object> param)
	{
		Query query = createQuery(hql, param);
		return query.setFirstResult(startRow).setMaxResults(rows).list();
	}
	
	public List<T> findWithOffset(String hql, int startRow, int rows, Object... param)
	{
		Query query = createQuery(hql, param);
		return query.setFirstResult(startRow).setMaxResults(rows).list();
	}
	/**
	 * 执行带参的HQL分页查询
	 * 
	 * @param hql
	 * @param page
	 * @param rows
	 * @param param
	 * @return 查询结果
	 */
	public List<T> findWithPage(String hql, int page, int rows, Object... param)
	{
		Query query = createQuery(hql, param);
		return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	/**
	 * 返回所有id对应的实体类
	 * 
	 * @param ids
	 * @return
	 */
	public List<T> findByIDs(Object[] ids)
	{
		Query query = this.getCurrentSession().createQuery(HQL_LIST_BY_ID_IN).setParameterList("ids", ids);
		return query.list();
	}

	/**
	 * 根据ID获取PO实例
	 * 
	 * @param id
	 * @return 返回相应的持久化PO实例
	 */
	public T get(Serializable id)
	{
		return (T) this.getCurrentSession().get(entityClass, id);
	}

	/**
	 * 执行带参的HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @return 查询结果
	 */
	public T get(String hql, Object... param)
	{
		List l = this.find(hql, param);
		if (l != null && l.size() > 0)
		{
			return (T) l.get(0);
		}
		return null;
	}

	/**
	 * 执行带参的HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @return 查询结果
	 */
	public T get(String hql, List<Object> param)
	{
		List l = this.find(hql, param);
		if (l != null && l.size() > 0)
		{
			return (T) l.get(0);
		}
		return null;
	}

	/**
	 * 根据ID加载POJO实例
	 * 
	 * @param id
	 * @return 返回相应的持久化PO实例
	 */
	public T load(Serializable id)
	{
		return (T) this.getCurrentSession().load(entityClass, id);
	}

	/**
	 * 执行HQL
	 * 
	 * @param hql
	 * @return 单一值
	 */
	public Long count(String hql)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	/**
	 * 执行HQL
	 * 
	 * @param hql
	 * @param param
	 * @return 单一值
	 */
	public Long count(String hql, Object... param)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0)
		{
			for (int i = 0; i < param.length; i++)
			{
				q.setParameter(i, param[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	/**
	 * 执行HQL
	 * 
	 * @param hql
	 * @param param
	 * @return 单一值
	 */
	public Long count(String hql, List<Object> param)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0)
		{
			for (int i = 0; i < param.size(); i++)
			{
				q.setParameter(i, param.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}

	/**
	 * 执行HQL
	 * 
	 * @param hql
	 * @return 被影响的行数
	 */
	public int executeHql(String hql)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	public int executeHql(String hql, Object... param)
	{
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0)
		{
			for (int i = 0; i < param.length; i++)
			{
				q.setParameter(i, param[i]);
			}
		}
		return q.executeUpdate();
	}

	public SQLQuery createSqlQuery(String sql, List<Object> param)
	{
		SQLQuery result = this.getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < param.size(); i++)
		{
			result.setParameter(i, param.get(i));
		}
		return result;
	}
	
	public SQLQuery createSqlQuery(String sql, Object... param){
	
		SQLQuery result = this.getCurrentSession().createSQLQuery(sql);     
		for (int i = 0; i < param.length; i++)
		{
			result.setParameter(i, param[i]);
		}
		return result;
	}
	
	public int callStorePrecure(String sql,List<Object> param)
	{
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);     
		for (int i = 0; i < param.size(); i++)
		{
			sqlQuery.setParameter(i, param.get(i));
		}
		return sqlQuery.executeUpdate();
	}
	
	public int callStorePrecure(String sql,Object... param)
	{
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);     
		for (int i = 0; i < param.length; i++)
		{
			sqlQuery.setParameter(i, param[i]);
		}
		return sqlQuery.executeUpdate();
	}
	
}

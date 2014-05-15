package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Weixin_GongHao;

@Repository
public class Weixin_GongHaoDao  extends BaseDao<Weixin_GongHao>
{
	private final String getWeixin_GongHaoByCorp = "from Weixin_GongHao";
	
	public Weixin_GongHao getGongHao()
	{
		List<Weixin_GongHao> results = (List<Weixin_GongHao>) find(getWeixin_GongHaoByCorp);
		if (results.size() == 0)
		{
			return null;
		}
		else
		{
			return results.get(0);
		}
	}
}

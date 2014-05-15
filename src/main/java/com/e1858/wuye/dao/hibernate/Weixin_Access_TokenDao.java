package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Weixin_Access_Token;
@Repository
public class Weixin_Access_TokenDao  extends BaseDao<Weixin_Access_Token>
{
	private final String getWeixin_Access_TokenByCorp = "from Weixin_Access_Token u where u.corp = ?";
	
	public Weixin_Access_Token getWeixin_Access_TokenByCorp(long corp)
	{
		List<Weixin_Access_Token> results = (List<Weixin_Access_Token>) find(getWeixin_Access_TokenByCorp, corp);
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

package com.e1858.wuye.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.entity.hibernate.Weixin_UserInfo;;


@Repository
public class Weixin_UserInfoDao  extends BaseDao<Weixin_UserInfo>
{
	private final String getUserInfoByOpenid = "from Weixin_UserInfo u where u.openid = ?";
	
	public Weixin_UserInfo getUserByUserName(String openid)
	{
		List<Weixin_UserInfo> userInfos = (List<Weixin_UserInfo>) find(getUserInfoByOpenid, openid);
		if (userInfos.size() == 0)
		{
			return null;
		}
		else
		{
			return userInfos.get(0);
		}
	}
}

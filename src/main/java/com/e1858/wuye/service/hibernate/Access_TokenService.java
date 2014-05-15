package com.e1858.wuye.service.hibernate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wechat.api.ApiBase;
import com.e1858.wechat.api.json.base.Access_Token;
import com.e1858.wuye.entity.hibernate.Weixin_Access_Token;
import com.e1858.wuye.entity.hibernate.Weixin_GongHao;

;

@Service
public class Access_TokenService
{
	@Autowired
	private Weixin_GongHaoService weixin_GongHaoService;

	@Autowired
	private Weixin_Access_TokenService weixin_Access_TokenService;

	public Weixin_Access_Token getAccess_Token()
	{
		Weixin_GongHao weixin_GongHao = weixin_GongHaoService.getGongHao();
		Weixin_Access_Token weixin_Access_Token = weixin_Access_TokenService.getWeixin_Access_TokenByCorp(weixin_GongHao.getCorp());
		if (null == weixin_Access_Token)
		{
			weixin_Access_Token = new Weixin_Access_Token();
			Access_Token access_Token = ApiBase.access_Token(weixin_GongHao.getAppId(), weixin_GongHao.getAppSecret());

			weixin_Access_Token.setCorp(weixin_GongHao.getCorp());
			weixin_Access_Token.setAccessToken(access_Token.getAccess_token());
			weixin_Access_Token.setAccessTime(new Date());
			weixin_Access_Token.setExpiresIn(access_Token.getExpires_in());
			weixin_Access_Token.setCreateTime(System.currentTimeMillis() / 1000l);
			weixin_Access_Token.setExpires((System.currentTimeMillis() / 1000l) + access_Token.getExpires_in());
			weixin_Access_TokenService.save(weixin_Access_Token);
		}
		else if (weixin_Access_Token.getExpires() < (System.currentTimeMillis() / 1000l))
		{
			Access_Token access_Token = ApiBase.access_Token(weixin_GongHao.getAppId(), weixin_GongHao.getAppSecret());
			if (null != access_Token && 0 == access_Token.getErrcode())
			{
				weixin_Access_Token.setCorp(weixin_GongHao.getCorp());
				weixin_Access_Token.setAccessToken(access_Token.getAccess_token());
				weixin_Access_Token.setAccessTime(new Date());
				weixin_Access_Token.setExpiresIn(access_Token.getExpires_in());
				weixin_Access_Token.setCreateTime(System.currentTimeMillis() / 1000l);
				weixin_Access_Token.setExpires((System.currentTimeMillis() / 1000l) + access_Token.getExpires_in());
				weixin_Access_TokenService.update(weixin_Access_Token);
			}
		}
		return weixin_Access_Token;
	}
	
	public String getAccess_token()
	{
		return getAccess_Token().getAccessToken();
	}
}

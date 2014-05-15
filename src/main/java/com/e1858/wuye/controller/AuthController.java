package com.e1858.wuye.controller;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.SysResource;
import com.e1858.wuye.entity.hibernate.SysRole;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.entity.hibernate.Weixin_Access_Token;
import com.e1858.wuye.exception.UserExistException;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.AppMenu;
import com.e1858.wuye.pojo.ForgetCommand;
import com.e1858.wuye.pojo.LoginCommand;
import com.e1858.wuye.pojo.RegisteCommand;
import com.e1858.wuye.pojo.ValidUser;
import com.e1858.wuye.service.hibernate.SysResourceService;
import com.e1858.wuye.service.hibernate.SysRoleService;
import com.e1858.wuye.service.hibernate.SysUserService;
import com.e1858.wuye.service.hibernate.Access_TokenService;
import com.e1858.wuye.utils.Util;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@Controller
@RequestMapping("/auth")
public class AuthController
{
	@Autowired
	private Producer captchaProducer;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysResourceService sysResourceService;
	
	
	@Autowired
	private Access_TokenService access_TokenService;

	@RequestMapping("/captcha")
	public void initCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		String captchaText = captchaProducer.createText();
		BufferedImage bufferedImage;

		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// store the text in the session
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, captchaText);
		// create the image with the text
		bufferedImage = captchaProducer.createImage(captchaText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
		ImageIO.write(bufferedImage, "jpg", out);
		try
		{
			out.flush();
		}
		finally
		{
			out.close();
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request)
	{
		return new ModelAndView("auth/login", "loginCommand", new LoginCommand());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, @Valid LoginCommand loginCommand, BindingResult result)
	{
		String password;
		ModelAndView modelAndView = new ModelAndView("auth/login");
		HttpSession session = request.getSession();
		if (result.hasErrors())
		{
			return modelAndView;
		}
		if (!loginCommand.getCaptcha().toUpperCase().equals(((String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY)).toUpperCase()))
		{
			modelAndView.addObject("errMsg", "验证码输入错误！");
		}
		else
		{
			password = Util.getPassword(loginCommand.getUserName(), loginCommand.getPassword());
			SysUser user = sysUserService.getUserByName(loginCommand.getUserName());
			if (null == user)
			{
				modelAndView.addObject("errMsg", "用户不存在！");
			}
			else if (user.getLocked() == SysUser.USER_LOCK)
			{
				modelAndView.addObject("errMsg", "用户被锁定！");
			}
			else if (user.getPassword().equals(password))
			{
				AppContext appContext = new AppContext();
				appContext.setUser(user);
				session.setAttribute(CommonConstant.CONTEXT_APP, appContext);
				user.setLastIp(request.getRemoteAddr());
				user.setLastVisit(new Date());
				sysUserService.loginSuccess(user);
				applyMenu(appContext);
				// modelAndView.setViewName("forward:/main");
				modelAndView.setViewName("redirect:/main");
			}
			else
			{
				modelAndView.addObject("errMsg", "用户名或密码错误！");
			}
		}
		return modelAndView;
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();
		session.removeAttribute(CommonConstant.CONTEXT_APP);
		return "auth/logout";
	}

	@RequestMapping(value = "/valid", method = RequestMethod.GET)
	public ModelAndView validate()
	{
		System.out.println(access_TokenService.getAccess_Token());
		return new ModelAndView("auth/valid", "validUser", new ValidUser());
	}

	@RequestMapping(value = "/valid", method = RequestMethod.POST)
	public ModelAndView valid(HttpServletRequest request, @Valid ValidUser validUser, BindingResult result)
	{
		if (result.hasErrors())
		{
			return new ModelAndView("auth/valid");
		}
		return new ModelAndView("auth/valid");
	}

	@RequestMapping(value = "/forget", method = RequestMethod.GET)
	public ModelAndView forget()
	{
		return new ModelAndView("auth/forget", "forgetCommand", new ForgetCommand());
	}

	@RequestMapping(value = "/forget", method = RequestMethod.POST)
	public ModelAndView forget(HttpServletRequest request, @Valid ForgetCommand forgetCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("auth/forget");
		if (result.hasErrors())
		{
			return modelAndView;
		}
		return modelAndView;
	}

	@RequestMapping(value = "/registe", method = RequestMethod.GET)
	public ModelAndView registe(HttpServletRequest request)
	{
		return new ModelAndView("auth/registe", "registeCommand", new RegisteCommand());
	}

	@RequestMapping(value = "/registe", method = RequestMethod.POST)
	public ModelAndView registe(HttpServletRequest request, @Valid RegisteCommand registeCommand, BindingResult result)
	{
		HttpSession session = request.getSession();
		ModelAndView modelAndView = new ModelAndView("auth/registe");
		if (result.hasErrors())
		{
			return modelAndView;
		}
		modelAndView.addObject("registeCommand", registeCommand);
		if (!registeCommand.getCaptcha().toUpperCase().equals(session.getAttribute(Constants.KAPTCHA_SESSION_KEY)))
		{
			modelAndView.addObject("errMsg", "验证码输入错误！");
		}
		else
		{
			String errMsg = null;
			try
			{
				String password = Util.getPassword(registeCommand.getUserName(), registeCommand.getPassword());
				SysUser newUser = new SysUser();
				newUser.setName(registeCommand.getUserName());
				newUser.setPassword(password);
				newUser.setEmail(registeCommand.getEmail());
				newUser.setAddress(registeCommand.getAddress());
				newUser.setMobilePhone(registeCommand.getMobilePhone());
				newUser.setQq(registeCommand.getQq());
				sysUserService.register(newUser);
			}
			catch (UserExistException e)
			{
				e.printStackTrace();
				errMsg = "用户名" + registeCommand.getUserName() + "已存在！";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				errMsg = "注册出错！";
			}

			if (errMsg == null)
			{
				modelAndView.setViewName("redirect:/auth/login");
			}
			else
			{
				modelAndView.addObject("errMsg", errMsg);
			}
		}
		return modelAndView;
	}

	private void applyMenu(AppContext appContext)
	{
		appContext.setMenu_h(new AppMenu());
		appContext.setMenu_v(new AppMenu());
		applyMenu_h(appContext);
		applyMenu_v(appContext);
	}

	private void applyMenu_h(AppContext appContext)
	{
		List<SysRole> roles = sysRoleService.queryRoleByUser(appContext.getUser());
		if (roles.size() > 0)
		{
			List<SysResource> resources = sysResourceService.queryResourceByRole(roles.get(0));
			for (int i = 0; i < resources.size(); i++)
			{
				if (resources.get(i).getId() == resources.get(i).getParent())
				{
					appContext.getMenu_h().setId(resources.get(i).getId());
					appContext.getMenu_h().setParent_id(resources.get(i).getParent());
					appContext.getMenu_h().setName(resources.get(i).getName());
					appContext.getMenu_h().setUrl(resources.get(i).getUrl());
					appContext.getMenu_h().setIcon(resources.get(i).getIcon());
					appContext.getMenu_h().setImage(resources.get(i).getImage());
					break;
				}
			}
			for (int i = 0; i < resources.size(); i++)
			{
				if (appContext.getMenu_h().getId() == resources.get(i).getParent() && resources.get(i).getId() != resources.get(i).getParent() && resources.get(i).getDirection() == 1)
				{
					AppMenu menu = new AppMenu();
					menu.setId(resources.get(i).getId());
					menu.setParent_id(resources.get(i).getParent());
					menu.setName(resources.get(i).getName());
					menu.setUrl(resources.get(i).getUrl());
					menu.setIcon(resources.get(i).getIcon());
					menu.setImage(resources.get(i).getImage());
					appContext.getMenu_h().getChildren().add(menu);

					for (int j = 0; j < resources.size(); j++)
					{
						if (menu.getId() == resources.get(j).getParent() && resources.get(j).getId() != resources.get(j).getParent() && resources.get(i).getDirection() == 1)
						{
							AppMenu subMenu = new AppMenu();
							subMenu.setId(resources.get(j).getId());
							subMenu.setParent_id(resources.get(j).getParent());
							subMenu.setName(resources.get(j).getName());
							subMenu.setUrl(resources.get(j).getUrl());
							subMenu.setIcon(resources.get(j).getIcon());
							subMenu.setImage(resources.get(j).getImage());
							menu.getChildren().add(subMenu);
						}
					}
				}
			}
		}
	}

	private void applyMenu_v(AppContext appContext)
	{
		List<SysRole> roles = sysRoleService.queryRoleByUser(appContext.getUser());
		if (roles.size() > 0)
		{
			List<SysResource> resources = sysResourceService.queryResourceByRole(roles.get(0));
			for (int i = 0; i < resources.size(); i++)
			{
				if (resources.get(i).getId() == resources.get(i).getParent())
				{
					appContext.getMenu_v().setId(resources.get(i).getId());
					appContext.getMenu_v().setParent_id(resources.get(i).getParent());
					appContext.getMenu_v().setName(resources.get(i).getName());
					appContext.getMenu_v().setUrl(resources.get(i).getUrl());
					appContext.getMenu_v().setIcon(resources.get(i).getIcon());
					appContext.getMenu_v().setImage(resources.get(i).getImage());
					break;
				}
			}
			for (int i = 0; i < resources.size(); i++)
			{
				if (appContext.getMenu_v().getId() == resources.get(i).getParent() && resources.get(i).getId() != resources.get(i).getParent() && resources.get(i).getDirection() == 0)
				{
					AppMenu menu = new AppMenu();
					menu.setId(resources.get(i).getId());
					menu.setParent_id(resources.get(i).getParent());
					menu.setName(resources.get(i).getName());
					menu.setUrl(resources.get(i).getUrl());
					menu.setIcon(resources.get(i).getIcon());
					menu.setImage(resources.get(i).getImage());
					appContext.getMenu_v().getChildren().add(menu);

					for (int j = 0; j < resources.size(); j++)
					{
						if (menu.getId() == resources.get(j).getParent() && resources.get(j).getId() != resources.get(j).getParent() && resources.get(i).getDirection() == 0)
						{
							AppMenu subMenu = new AppMenu();
							subMenu.setId(resources.get(j).getId());
							subMenu.setParent_id(resources.get(j).getParent());
							subMenu.setName(resources.get(j).getName());
							subMenu.setUrl(resources.get(j).getUrl());
							subMenu.setIcon(resources.get(j).getIcon());
							subMenu.setImage(resources.get(j).getImage());
							menu.getChildren().add(subMenu);
						}
					}
				}
			}
		}
	}
}

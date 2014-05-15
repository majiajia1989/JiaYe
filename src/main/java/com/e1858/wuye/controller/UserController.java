package com.e1858.wuye.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysLoginLog;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.exception.UserExistException;
import com.e1858.wuye.pojo.AjaxResult;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.ChangePasswordCommand;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.DataTableRowCnt;
import com.e1858.wuye.pojo.UserCommand;
import com.e1858.wuye.service.hibernate.CorpService;
import com.e1858.wuye.service.hibernate.SysCorpTypeService;
import com.e1858.wuye.service.hibernate.SysRoleService;
import com.e1858.wuye.service.hibernate.SysUserService;
import com.e1858.wuye.utils.JsonUtil;
import com.e1858.wuye.utils.Util;

@Controller
@RequestMapping("/user")
public class UserController
{
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private CorpService corpService;
	@Autowired
	private SysCorpTypeService sysCorpTypeService;
	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/user")
	public ModelAndView user(HttpServletRequest request)
	{
		return new ModelAndView("user/user");
	}

	@RequestMapping("/corpUser")
	public ModelAndView corpUser(HttpServletRequest request)
	{
		return new ModelAndView("user/corpUser");
	}

	@RequestMapping("/loginLog")
	public String init()
	{
		return "user/loginLog";
	}

	@RequestMapping("/editUser/{userID}")
	public ModelAndView editUser(@PathVariable("userID") long userID, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser sysUser = appContext.getUser();

		ModelAndView modelAndView = new ModelAndView("user/editUser");
		request.setAttribute("creatorCorpID", sysUser.getCorp().getId());
		request.setAttribute("creatorCorpType", sysUser.getCorp().getCorpType().getId());
		request.setAttribute("corpRoles", sysRoleService.queryRoleByCorp(sysUser.getCorp()));
		UserCommand userCommand = new UserCommand();
		userCommand.setUser_id(-1);
		userCommand.setCorp_id(-1);
		userCommand.setType(-1);
		SysUser user = new SysUser();
		user.setId(-1);
		if (userID != -1)
		{
			user = sysUserService.getUserById(userID);
			if (user != null)
			{
				userCommand.setUser_id(user.getId());
				userCommand.setCorp_id(user.getCorp().getId());
				userCommand.setName(user.getName());
				userCommand.setPassword("");
				userCommand.setConfirmPass("");
				userCommand.setEmail(user.getEmail());
				userCommand.setMobilePhone(user.getMobilePhone());
				userCommand.setQq(user.getQq());
				userCommand.setAddress(user.getAddress());
			}
		}
		modelAndView.addObject("userCommand", userCommand);
		request.setAttribute("userCommand", userCommand);
		return modelAndView;

	}

	@RequestMapping("/editCorpUser/{userID}")
	public ModelAndView editCorpUser(@PathVariable("userID") long userID, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser sysUser = appContext.getUser();

		ModelAndView modelAndView = new ModelAndView("user/editCorpUser");
		request.setAttribute("creatorCorpID", sysUser.getCorp().getId());
		request.setAttribute("creatorCorpType", sysUser.getCorp().getCorpType().getId());
		UserCommand userCommand = new UserCommand();
		userCommand.setUser_id(-1);
		userCommand.setCorp_id(-1);
		userCommand.setType(-1);
		SysUser user = new SysUser();
		user.setId(-1);
		if (userID != -1)
		{
			user = sysUserService.getUserById(userID);
			if (user != null)
			{
				userCommand.setUser_id(user.getId());
				userCommand.setCorp_id(user.getCorp().getId());
				userCommand.setType(user.getCorp().getCorpType().getId());
				userCommand.setName(user.getName());
				userCommand.setPassword("");
				userCommand.setConfirmPass("");
				userCommand.setEmail(user.getEmail());
				userCommand.setMobilePhone(user.getMobilePhone());
				userCommand.setQq(user.getQq());
				userCommand.setAddress(user.getAddress());
			}
		}
		modelAndView.addObject("userCommand", userCommand);
		request.setAttribute("userCommand", userCommand);
		return modelAndView;
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ModelAndView userInfo(HttpServletRequest request)
	{
		return new ModelAndView("user/info");
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ModelAndView saveUser(HttpServletRequest request, @Valid UserCommand userCommand, BindingResult result)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser sysUser = appContext.getUser();
		request.setAttribute("creatorCorpID", sysUser.getCorp().getId());
		request.setAttribute("corpRoles", sysRoleService.queryRoleByCorp(sysUser.getCorp()));

		String infoMsg = null;
		ModelAndView modelAndView = new ModelAndView("user/editUser");
		modelAndView.addObject("userCommand", userCommand);
		if (result.hasErrors())
		{
			return modelAndView;
		}
		if (!userCommand.getPassword().equals(userCommand.getConfirmPass()))
		{
			result.addError(new FieldError("userCommand", "confirmPass", "请再次输入相同的值"));
		}
		else
		{
			modelAndView.addObject("userCommand", userCommand);
			if (userCommand.getUser_id() == -1)
			{
				try
				{
					SysUser user = new SysUser();
					user.setName(userCommand.getName());
					user.setPassword(Util.getPassword(userCommand.getName(), userCommand.getPassword()));
					user.setCorp(sysUser.getCorp());
					user.setEmail(userCommand.getEmail());
					user.setAddress(userCommand.getAddress());
					user.setMobilePhone(userCommand.getMobilePhone());
					user.setAddress(userCommand.getAddress());
					user.setQq(userCommand.getQq());

					user.setCreator(sysUser.getId());
					user.setCreateTime((new Date()));

					sysUserService.register(user, userCommand.getRole_id());
					infoMsg = "恭喜您，操作成功！";
				}
				catch (UserExistException e)
				{
					result.addError(new FieldError("userCommand", "name", "用户名已经存在"));
					return modelAndView;
				}
				catch (Exception e)
				{
					CommonConstant.exceptionLogger.info(e);
					infoMsg = "对不起，操作失败！";
				}
			}
			else
			{
				SysUser user = sysUserService.getUserById((int) userCommand.getUser_id());

				user.setEmail(userCommand.getEmail());
				user.setAddress(userCommand.getAddress());
				user.setMobilePhone(userCommand.getMobilePhone());
				user.setAddress(userCommand.getAddress());
				user.setQq(userCommand.getQq());

				try
				{
					sysUserService.update(user);
					infoMsg = "恭喜您，操作成功！";
				}
				catch (Exception e)
				{
					CommonConstant.exceptionLogger.info(e);
					infoMsg = "对不起，操作失败！";
				}
			}
		}
		if (infoMsg != null)
		{
			modelAndView.addObject("infoMsg", infoMsg);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/saveCorpUser", method = RequestMethod.POST)
	public ModelAndView saveCorpUser(HttpServletRequest request, @Valid UserCommand userCommand, BindingResult result)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser sysUser = appContext.getUser();
		request.setAttribute("creatorCorpID", sysUser.getCorp().getId());
		request.setAttribute("creatorCorpType", sysUser.getCorp().getCorpType().getId());

		String infoMsg = null;
		ModelAndView modelAndView = new ModelAndView("user/editCorpUser");
		modelAndView.addObject("userCommand", userCommand);
		if (result.hasErrors())
		{
			return modelAndView;
		}
		if (!userCommand.getPassword().equals(userCommand.getConfirmPass()))
		{
			result.addError(new FieldError("userCommand", "confirmPass", "请再次输入相同的值"));
			return modelAndView;
		}
		else
		{
			modelAndView.addObject("userCommand", userCommand);
			if (userCommand.getUser_id() == -1)
			{
				try
				{
					SysUser user = new SysUser();

					user.setName(userCommand.getName());
					user.setPassword(Util.getPassword(userCommand.getName(), userCommand.getPassword()));
					user.setEmail(userCommand.getEmail());
					user.setAddress(userCommand.getAddress());
					user.setMobilePhone(userCommand.getMobilePhone());
					user.setAddress(userCommand.getAddress());
					user.setQq(userCommand.getQq());

					user.setCreator(sysUser.getId());
					user.setCreateTime((new Date()));

					SysCorp corp = new SysCorp();
					corp.setCorpType(sysCorpTypeService.getCorpById(userCommand.getType()));
					corp.setProvince("");
					corp.setCity("");
					corp.setCounty("");
					corp.setName(userCommand.getCorpName());
					corp.setAddress(userCommand.getAddress());
					corp.setTelePhone("");
					corp.setMobilePhone(userCommand.getMobilePhone());
					corp.setDescript("");
					corp.setCopyright("");
					corp.setMap("");
					corp.setCreator(sysUser.getId());
					corp.setCreateTime((new Date()));
					sysUserService.register(user, corp);
					infoMsg = "恭喜您，操作成功！";
				}
				catch (UserExistException e)
				{
					result.addError(new FieldError("userCommand", "name", "用户名已经存在"));
					return modelAndView;
				}
				catch (Exception e)
				{
					CommonConstant.exceptionLogger.info(e);
					infoMsg = "对不起，操作失败！";
				}
			}
			else
			{
				SysUser user = sysUserService.getUserById((int) userCommand.getUser_id());

				user.setEmail(userCommand.getEmail());
				user.setAddress(userCommand.getAddress());
				user.setMobilePhone(userCommand.getMobilePhone());
				user.setAddress(userCommand.getAddress());
				user.setQq(userCommand.getQq());

				try
				{
					sysUserService.update(user);
					infoMsg = "恭喜您，操作成功！";
				}
				catch (Exception e)
				{
					CommonConstant.exceptionLogger.info(e);
					infoMsg = "对不起，操作失败！";
				}
			}
		}
		if (infoMsg != null)
		{
			modelAndView.addObject("infoMsg", infoMsg);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/lock", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String lock(String name)
	{
		AjaxResult result = new AjaxResult();
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		if (appContext.getUser() == null)
		{
			result.setFlag(false);
			result.setMessage("");
			result.setReturnUrl("/auth/login");
		}
		else
		{
			try
			{
				sysUserService.lockUser(name);
				result.setFlag(true);
				result.setMessage("恭喜您，操作成功！");
			}
			catch (Exception e)
			{
				CommonConstant.exceptionLogger.info(e);
			}
		}

		return JsonUtil.toJson(result);
	}

	@RequestMapping(value = "/unlock", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String unlock(String name)
	{
		AjaxResult result = new AjaxResult();
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		if (appContext.getUser() == null)
		{
			result.setFlag(false);
			result.setMessage("");
			result.setReturnUrl("/auth/login");
		}
		else
		{
			try
			{
				sysUserService.unlockUser(name);
				result.setFlag(true);
				result.setMessage("恭喜您，操作成功！");
			}
			catch (Exception e)
			{
				CommonConstant.exceptionLogger.info(e);
			}
		}
		return JsonUtil.toJson(result);
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword(HttpServletRequest request)
	{
		return new ModelAndView("user/changePassword", "changePasswordCommand", new ChangePasswordCommand());
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ModelAndView changePassword(HttpServletRequest request, @Valid ChangePasswordCommand changePasswordCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("user/changePassword");
		if (result.hasErrors())
		{
			return modelAndView;
		}

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		String oldPassword = Util.getPassword(user.getName(), changePasswordCommand.getOldPassword());
		if (!user.getPassword().equals(oldPassword))
		{
			result.addError(new FieldError("changePasswordCommand", "oldPassword", "旧密码错误!"));
		}
		else
		{
			modelAndView.addObject("successMsg", "密码修改成功!");
			String newPassword = Util.getPassword(user.getName(), changePasswordCommand.getNewPassword());
			user.setPassword(newPassword);
			try
			{
				sysUserService.update(user);
			}
			catch (Exception e)
			{
				CommonConstant.exceptionLogger.info(e);
				modelAndView.addObject("successMsg", "对不起，操作失败!");
			}
		}
		return modelAndView;
	}

	@RequestMapping(value = "/readCorps", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readCorps(@RequestParam String sEcho, @RequestParam int iDisplayStart, @RequestParam int iDisplayLength)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		DataTableRowCnt rowCnt = new DataTableRowCnt();
		DataTable<SysCorp> dgList = new DataTable<SysCorp>();
		dgList.setAaData((ArrayList<SysCorp>) corpService.queryCorps(iDisplayStart, iDisplayLength, rowCnt));
		dgList.setiTotalRecords(rowCnt.getRowCnt());
		dgList.setiTotalDisplayRecords(rowCnt.getRowCnt());
		dgList.setsEcho(sEcho);
		return JsonUtil.toJson(dgList);
	}

	@RequestMapping(value = "/readUsers", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readUsers()
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		DataTable<SysUser> dgList = new DataTable<SysUser>();
		if (user.getCorp().getId() == 1)
		{
			dgList.setAaData((ArrayList<SysUser>) sysUserService.queryAllUser());
		}
		else
		{
			dgList.setAaData((ArrayList<SysUser>) sysUserService.queryUserByCorp(user.getCorp()));
		}
		dgList.setiTotalDisplayRecords(1);
		return JsonUtil.toJson(dgList);
	}

	@RequestMapping(value = "/readUserByCorp", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readUserByCorp( @RequestParam String sEcho, @RequestParam int iDisplayStart, @RequestParam int iDisplayLength)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		DataTableRowCnt rowCnt = new DataTableRowCnt();
		DataTable<SysUser> dgList = new DataTable<SysUser>();

		dgList.setAaData((ArrayList<SysUser>) sysUserService.queryUserByCorp(user.getCorp(),iDisplayStart, iDisplayLength, rowCnt));

		dgList.setiTotalRecords(rowCnt.getRowCnt());
		dgList.setiTotalDisplayRecords(rowCnt.getRowCnt());
		dgList.setsEcho(sEcho);
		return JsonUtil.toJson(dgList);
	}
	@RequestMapping(value = "/queryAllLog", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAllLog(@RequestParam("iDisplayStart") int iDisplayStart, 
			@RequestParam("iDisplayLength") int iDisplayLength,
			@RequestParam(value = "sEcho") int sEcho,HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		DataTable<SysLoginLog> dgList = new DataTable<SysLoginLog>();
		dgList.setsEcho(String.valueOf(sEcho));
		dgList.setAaData((ArrayList<SysLoginLog>) sysUserService.queryAllLog(user,iDisplayStart,iDisplayLength));
		long totalCount=sysUserService.getTotalCount(user);
		dgList.setiTotalRecords(totalCount);
		dgList.setiTotalDisplayRecords(totalCount);
		return JsonUtil.toJson(dgList);
	}

}

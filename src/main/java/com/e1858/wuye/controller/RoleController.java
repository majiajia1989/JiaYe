package com.e1858.wuye.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.common.CommonConstant.DefaultMsgType;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.DefaultMsg;
import com.e1858.wuye.entity.hibernate.HouseRoom;
import com.e1858.wuye.entity.hibernate.Msg;
import com.e1858.wuye.entity.hibernate.MsgKey;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysRole;
import com.e1858.wuye.entity.hibernate.SysRoleResource;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AjaxResult;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.AppMenu;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.MsgCommand;
import com.e1858.wuye.pojo.RoleCommand;
import com.e1858.wuye.pojo.TextCommand;
import com.e1858.wuye.pojo.TreeNode;
import com.e1858.wuye.pojo.UserCommand;
import com.e1858.wuye.service.hibernate.MessageService;
import com.e1858.wuye.service.hibernate.SysResourceService;
import com.e1858.wuye.service.hibernate.SysRoleService;
import com.e1858.wuye.utils.JsonUtil;
import com.e1858.wuye.utils.Util;

@Controller
@RequestMapping("/role")
public class RoleController
{
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysResourceService sysResourceService;

	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public ModelAndView editText(HttpServletRequest request)
	{
		RoleCommand roleCommand = new RoleCommand();
		roleCommand.setRole_id(-1);
		roleCommand.setName("");
		ModelAndView modelAndView = new ModelAndView("user/role", "roleCommand", new RoleCommand());
		return modelAndView;
	}

	@RequestMapping(value = "/editRole/{roleId}", method = RequestMethod.GET)
	public ModelAndView editRole(@PathVariable("roleId") long roleId, HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("user/editRole");
		RoleCommand roleCommand = new RoleCommand();
		roleCommand.setRole_id(-1);
		roleCommand.setName("");

		if (roleId != -1)
		{
			SysRole role = sysRoleService.queryRoleByRoleId(roleId);
			if (role != null)
			{
				roleCommand.setRole_id(roleId);
				roleCommand.setName(role.getName());
				roleCommand.setRoleResources(sysRoleService.queryResourceIdsByRole(role));
			}
		}
		modelAndView.addObject("roleCommand", roleCommand);
		return modelAndView;
	}

	@RequestMapping(value = "/editRole", method = RequestMethod.POST)
	public ModelAndView editRole(HttpServletRequest request, @Valid RoleCommand roleCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("user/editRole");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		if (result.hasErrors())
		{
			return modelAndView;
		}
		modelAndView.addObject("roleCommand", roleCommand);
		try
		{
			if (roleCommand.getRole_id() == -1)
			{
				SysRole sysRole = new SysRole();
				sysRole.setCorp(user.getCorp());
				sysRole.setCreator(user);
				sysRole.setCreateTime(new Date());
				sysRole.setName(roleCommand.getName());
				sysRoleService.save(sysRole, roleCommand.getRoleResources());
				modelAndView.setViewName("user/role");
				modelAndView.addObject("infoMsg", "恭喜您，操作成功！");

			}
			else
			{
				SysRole sysRole = sysRoleService.queryRoleByRoleId(roleCommand.getRole_id());
				if (sysRole != null)
				{
					sysRole.setCreator(user);
					sysRole.setCreateTime(new Date());
					sysRole.setName(roleCommand.getName());
					sysRoleService.update(sysRole, roleCommand.getRoleResources());
					modelAndView.setViewName("user/role");
					modelAndView.addObject("infoMsg", "恭喜您，操作成功！");
				}

			}
		}
		catch (Exception e)
		{
			modelAndView.addObject("infoMsg", "对不起，操作失败！");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteRole(HttpServletRequest request, @RequestParam("roleId") long roleId)
	{
		AjaxResult result = new AjaxResult();
		result.setFlag(false);
		result.setMessage("");

		try
		{
			sysRoleService.delete(sysRoleService.queryRoleByRoleId(roleId));
			result.setFlag(true);
			result.setMessage("恭喜您，删除成功！");
		}
		catch (Exception e)
		{
			result.setFlag(false);
			result.setMessage("对不起，操作失败！");
		}
		return JsonUtil.toJson(result);
	}

	@RequestMapping(value = "/readRoleByCorp", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readRoleByCorp(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		DataTable<SysRole> dgList = new DataTable<SysRole>();
		dgList.setAaData((ArrayList<SysRole>) sysRoleService.queryRoleByCorp(user.getCorp()));
		if (dgList.getAaData() != null)
		{
			dgList.setiTotalRecords(dgList.getAaData().size());
		}
		dgList.setiTotalDisplayRecords(1);
		return JsonUtil.toJson(dgList);
	}

	@RequestMapping(value = "/readResourceTreeByRole", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readResourceTreeByRole(long editRoleId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		AppMenu appMenu = appContext.getMenu_v();
		List<TreeNode> menuTree = new ArrayList<TreeNode>();
		List<SysRoleResource> editRoleResource = sysRoleService.queryRoleResourceByRole(sysRoleService.queryRoleByRoleId(editRoleId));
		for (AppMenu menu : appMenu.getChildren())
		{
			TreeNode node = new TreeNode();
			node.setId(String.valueOf(menu.getId()));
			node.setText(menu.getName());
			node.getState().setSelected(false);

			initMenuTree(menu, node, editRoleResource);
			if (node.getChildren().size() > 0)
			{
				node.getState().setSelected(false);
			}
			else
			{
				if (existsInRoleResources(menu.getId(), editRoleResource))
				{
					node.getState().setSelected(true);
				}
				else
				{
					node.getState().setSelected(false);
				}
			}
			menuTree.add(node);
		}

		return JsonUtil.toJson(menuTree);
	}

	private void initMenuTree(AppMenu menu, TreeNode node, List<SysRoleResource> sysRoleResources)
	{
		for (AppMenu subMenu : menu.getChildren())
		{
			TreeNode subNode = new TreeNode();
			subNode.setId(String.valueOf(subMenu.getId()));
			subNode.setText(subMenu.getName());
			initMenuTree(subMenu, subNode, sysRoleResources);
			if (subNode.getChildren().size() > 0)
			{
				subNode.getState().setSelected(false);
			}
			else
			{
				if (existsInRoleResources(subMenu.getId(), sysRoleResources))
				{
					subNode.getState().setSelected(true);
				}
				else
				{
					subNode.getState().setSelected(false);
				}
			}
			node.getChildren().add(subNode);
		}
	}

	private boolean existsInRoleResources(long resourceId, List<SysRoleResource> sysRoleResources)
	{
		for (SysRoleResource roleResource : sysRoleResources)
		{
			if (resourceId == roleResource.getSysResource().getId())
			{
				return true;
			}
		}
		return false;
	}
}

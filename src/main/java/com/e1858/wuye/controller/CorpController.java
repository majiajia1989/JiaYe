package com.e1858.wuye.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.CorpImage;
import com.e1858.wuye.entity.hibernate.CorpWelcomeImage;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.CorpCommand;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.ImageCommand;
import com.e1858.wuye.pojo.Response;
import com.e1858.wuye.service.hibernate.CorpImageService;
import com.e1858.wuye.service.hibernate.CorpService;
import com.e1858.wuye.service.hibernate.SysCorpTypeService;
import com.e1858.wuye.utils.JsonUtil;

@Controller
@RequestMapping("/corp")
public class CorpController
{

	@Autowired
	private CorpService corpService;
	@Autowired
	private SysCorpTypeService sysCorpTypeService;
	@Autowired 
	private CorpImageService corpImageService;
	
	@RequestMapping(value = "/corpImage", method = RequestMethod.GET)
	public ModelAndView corpImage(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("corp/corpImage","imageCommand",new ImageCommand());
		return modelAndView;
	}
	
	@RequestMapping(value = "/addCorpImage", method = RequestMethod.GET)
	public ModelAndView addCorpImage(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("corp/addCorpImage","imageCommand",new ImageCommand());
		return modelAndView;
	}	
	
	@RequestMapping(value="/addCorpImage", method = RequestMethod.POST)
	public ModelAndView addCorpImage (HttpServletRequest request, @Valid ImageCommand imageCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("redirect:/corp/corpImage");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		if (result.hasErrors())
		{
			return modelAndView;
		}		
		
		CorpImage corpImage = new CorpImage();		
		if (imageCommand.getId() == -1) //Add
		{
			String ctxUrl = request.getRequestURL().toString();	
			String projectName = request.getContextPath();	
			String newsUrl = "";			
			String imageUrl = imageCommand.getImageUrl();
			if (imageCommand.getUrl().length()>0)
			{
				newsUrl =ctxUrl.substring(0, ctxUrl.lastIndexOf(projectName)+projectName.length()+1);
				newsUrl = newsUrl.concat("message/showArticle/").concat(imageCommand.getUrl());				
			}
			if(!(imageUrl.startsWith("http")|| imageUrl.startsWith("https")))
			{
				imageUrl = ctxUrl.substring(0, ctxUrl.lastIndexOf(projectName)).concat(imageUrl);
			}
			corpImage.setCorp(user.getCorp());
			corpImage.setTitle(imageCommand.getTitle());
			corpImage.setImageUrl(imageUrl);
			corpImage.setUrl(newsUrl);
			corpImage.setDescript(imageCommand.getDescript());
			corpImage.setCreator(user);
			corpImage.setCreateTime(new Date());
			corpImageService.save(corpImage);
			modelAndView.addObject("infoMsg", "新增成功！");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/removeImageByID/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String removeImageByID(@PathVariable("id") long id, HttpServletRequest request)
	{
		Response resp = new Response();
		CorpImage corpImage = corpImageService.queryCorpById(id);
		if (corpImage == null)
		{
			resp.setSuccess(false);
			resp.setErrMsg("找不到指定的消息!");
		}
		else
		{
			try
			{
				corpImageService.delete(corpImage);
				resp.setSuccess(true);				
			}
			catch (Exception e)
			{
				resp.setSuccess(false);
				resp.setErrMsg("删除失败!");
			}
		}
		return JsonUtil.toJson(resp);
	}
	
	@RequestMapping(value = "/setDefaultImage/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String setDefaultImage(@PathVariable("id") long id, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		Response resp = new Response();
		CorpWelcomeImage corpWelcomeImage = corpImageService.queryCorpById(user.getCorp());		
		if (corpWelcomeImage != null)
		{
			corpImageService.delete(corpWelcomeImage);
		}
		CorpWelcomeImage cImage = new CorpWelcomeImage();
		cImage.setCorp(user.getCorp());
		cImage.setImage(corpImageService.queryCorpById(id));
		cImage.setCreator(user);
		cImage.setCreateTime(new Date());
		corpImageService.save(cImage);
		resp.setSuccess(true);
		resp.setErrMsg("设置首页图片成功");			

		return JsonUtil.toJson(resp);
	}

	@RequestMapping(value = "/cancleDefaultImage/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String cancleDefaultImage(@PathVariable("id") long id, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		Response resp = new Response();
		CorpWelcomeImage corpWelcomeImage = corpImageService.queryCorpById(user.getCorp());
		if (corpWelcomeImage == null)
		{
			resp.setSuccess(false);
			resp.setErrMsg("取消失败");			
		}
		else
		{
			corpImageService.delete(corpWelcomeImage);
			resp.setSuccess(true);
			resp.setErrMsg("取消欢迎图片成功");
		}
		return JsonUtil.toJson(resp);
	}	
	
	@RequestMapping(value = "/readCorpImage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readCorpImage( HttpServletRequest request)
	{
		DataTable<ImageCommand> dataGrid = new DataTable<ImageCommand>();
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user=appContext.getUser();
		SysCorp corp = user.getCorp();
		
		int iDisplayStart=Integer.valueOf(request.getParameter("iDisplayStart"));
		String sEcho=request.getParameter("sEcho");
		int iDisplayLength=Integer.valueOf(request.getParameter("iDisplayLength"));	
		
		CorpWelcomeImage corpWelcomeImage = corpImageService.queryCorpById(corp);
		long image = -1;
		if (corpWelcomeImage != null)
		{
			image = corpWelcomeImage.getImage().getId();
		}

		List<CorpImage> corpImages = corpImageService.queryCorpImageByCorp(corp,iDisplayStart,iDisplayLength);

			for (int i = 0; i < corpImages.size(); i++)
			{
				ImageCommand imageCommand = new ImageCommand();
				imageCommand.setId(corpImages.get(i).getId());
				imageCommand.setTitle(corpImages.get(i).getTitle());
				imageCommand.setDescript(corpImages.get(i).getDescript());
				imageCommand.setImageUrl(corpImages.get(i).getImageUrl());
				imageCommand.setUrl(corpImages.get(i).getUrl());
				if (image == corpImages.get(i).getId())
				{
					imageCommand.setDefalutImage(true);
				}
				dataGrid.getAaData().add(imageCommand);
			}
		long totalCount=corpImageService.getCorpImageCount(appContext.getUser().getCorp());
		dataGrid.setsEcho(sEcho);
		dataGrid.setiTotalRecords(totalCount);
		dataGrid.setiTotalDisplayRecords(totalCount);
		return JsonUtil.toJson(dataGrid);
	}	

	@RequestMapping(value = "/corp", method = RequestMethod.GET)
	public ModelAndView initCorp(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("corp/corp", "corpCommand", new CorpCommand());
		CorpCommand corpCommand = new CorpCommand();
		corpCommand.setCorp_id(-1);
		modelAndView.addObject("corpCommand", corpCommand);
		request.setAttribute("corp", new SysCorp());
		return modelAndView;
	}

	@RequestMapping(value = "/corp", method = RequestMethod.POST)
	public ModelAndView saveCorp(HttpServletRequest request, @Valid CorpCommand corpCommand, BindingResult result)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		ModelAndView modelAndView = new ModelAndView("corp/editCorp");
		modelAndView.addObject("corpCommand", corpCommand);
		if (result.hasErrors())
		{
			return modelAndView;
		}
		try
		{
		if (corpCommand.getCorp_id() == -1)
		{
			SysCorp corp = new SysCorp();
			corp.setCorpType(sysCorpTypeService.getCorpById(corpCommand.getType()));
			corp.setProvince(corpCommand.getProvince());
			corp.setCity(corpCommand.getCity());
			corp.setCounty(corpCommand.getCounty());
			corp.setName(corpCommand.getName());
			corp.setAddress(corpCommand.getAddress());
			corp.setTelePhone(corpCommand.getTelephone());
			corp.setMobilePhone(corpCommand.getMobilephone());
			corp.setDescript(corpCommand.getDescript());
			corp.setCopyright(corpCommand.getCopyright());
			corp.setMap("");

			corp.setCreator(user.getId());
			corp.setCreateTime((new Date()));
			corpService.save(corp);
			request.setAttribute("corp", corp);
			modelAndView.addObject("infoMsg", "新增成功！");
		}
		else
		{
			SysCorp corp = corpService.queryCorpById(corpCommand.getCorp_id());
			corp.setCorpType(sysCorpTypeService.getCorpById(corpCommand.getType()));
			corp.setProvince(corpCommand.getProvince());
			corp.setCity(corpCommand.getCity());
			corp.setCounty(corpCommand.getCounty());
			corp.setName(corpCommand.getName());
			corp.setAddress(corpCommand.getAddress());
			corp.setTelePhone(corpCommand.getTelephone());
			corp.setMobilePhone(corpCommand.getMobilephone());
			corp.setDescript(corpCommand.getDescript());
			corp.setCopyright(corpCommand.getCopyright());
			corp.setMap("");
			corp.setCreator(user.getId());
			corp.setCreateTime((new Date()));
			corpService.update(corp);
			request.setAttribute("corp", corp);
			modelAndView.addObject("infoMsg", "修改成功！");
		}
		}
		catch(Exception e)
		{
			CommonConstant.exceptionLogger.info(e);
			modelAndView.addObject("infoMsg", "对不起，操作失败！");
		}
		return modelAndView;
	}

	// edit pages
	@RequestMapping(value = "/editCorp/{corpID}", method = RequestMethod.GET)
	public ModelAndView editCorp(@PathVariable("corpID") long corpID, HttpServletRequest request)
	{
		request.setAttribute("corpType", 2);
		CorpCommand corpCommand = new CorpCommand();
		ModelAndView modelAndView = new ModelAndView("corp/editCorp");
		SysCorp corp = new SysCorp();
		corp.setId(-1);
		if (corpID != -1)
		{
			corp = corpService.queryCorpById(corpID);
			if (corp != null)
			{
				
				corpCommand.setCorp_id(corpID);
				corpCommand.setType(corp.getCorpType().getId());
				corpCommand.setProvince(corp.getProvince());
				// corpCommand.setProvinceCode(sysAreaService.getAreaByProvinceName(corp.getProvince()).getCode());
				corpCommand.setCity(corp.getCity());
				// corpCommand.setCityCode(sysAreaService.getAreaByParentAndName(corpCommand.getProvinceCode(),corpCommand.getCity()).getCode());
				corpCommand.setCounty(corp.getCounty());
				// corpCommand.setCoutyCode(sysAreaService.getAreaByParentAndName(corpCommand.getCityCode(),corpCommand.getCouty()).getCode());
				corpCommand.setName(corp.getName());
				corpCommand.setAddress(corp.getAddress());
				corpCommand.setTelephone(corp.getTelePhone());
				corpCommand.setMobilephone(corp.getMobilePhone());
				corpCommand.setDescript(corp.getDescript());
				corpCommand.setCopyright(corp.getCopyright());

				
			}
		}
		modelAndView.addObject("corpCommand", corpCommand);
		request.setAttribute("corpCommand", corpCommand);
		return modelAndView;
	}

	@RequestMapping(value = "/readCorps", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readCorps(HttpServletRequest request)
	{
		DataTable<SysCorp> dgList = new DataTable<SysCorp>();
		dgList.setAaData((ArrayList<SysCorp>) corpService.queryCorps());
		if (dgList.getAaData() != null)
		{
			dgList.setiTotalRecords(dgList.getAaData().size());
		}
		dgList.setiTotalDisplayRecords(1);
		return JsonUtil.toJson(dgList);
	}
}

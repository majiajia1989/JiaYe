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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.e1858.wechat.api.ApiQRcode;
import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.CommunityImage;
import com.e1858.wuye.entity.hibernate.CommunityWelcomeImage;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.CommunityCommand;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.DataTableRowCnt;
import com.e1858.wuye.pojo.ImageCommand;
import com.e1858.wuye.pojo.Response;
import com.e1858.wuye.service.hibernate.Access_TokenService;
import com.e1858.wuye.service.hibernate.CommunityImageService;
import com.e1858.wuye.service.hibernate.CommunityService;
import com.e1858.wuye.service.hibernate.CorpService;
import com.e1858.wuye.utils.JsonUtil;
@Controller
@RequestMapping("/community")
public class CommunityController
{

	@Autowired
	private CommunityService communityService;
	@Autowired
	private CorpService sysCorpService;

	@Autowired
	private CommunityImageService communityImageService;

	@Autowired
	private Access_TokenService access_TokenService;
	
	@RequestMapping(value = "/communityImage", method = RequestMethod.GET)
	public ModelAndView communityImage(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("community/communityImage", "imageCommand", new ImageCommand());
		return modelAndView;
	}

	@RequestMapping(value = "/addCommunityImage", method = RequestMethod.GET)
	public ModelAndView addCommunityImage(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("community/addCommunityImage", "imageCommand", new ImageCommand());
		return modelAndView;
	}

	@RequestMapping(value = "/addCommunityImage", method = RequestMethod.POST)
	public ModelAndView addCommunityImage(HttpServletRequest request, @Valid ImageCommand imageCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("redirect:/community/communityImage");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		if (result.hasErrors())
		{
			return modelAndView;
		}

		CommunityImage communityImage = new CommunityImage();
		if (imageCommand.getId() == -1) // Add
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
			communityImage.setCommunity(appContext.getCommunity());
			communityImage.setTitle(imageCommand.getTitle());
			communityImage.setImageUrl(imageUrl);
			communityImage.setUrl(newsUrl);
			communityImage.setDescript(imageCommand.getDescript());
			communityImage.setCreator(user);
			communityImage.setCreateTime(new Date());
			communityImageService.save(communityImage);
			modelAndView.addObject("infoMsg", "恭喜您，操作成功！");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/removeImageByID/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String removeImageByID(@PathVariable("id") long id, HttpServletRequest request)
	{
		Response resp = new Response();
		CommunityImage communityImage = communityImageService.queryCommunityImageById(id);
		if (communityImage == null)
		{
			resp.setSuccess(false);
			resp.setErrMsg("对不起，找不到指定的消息!");
		}
		else
		{
			try
			{
				communityImageService.delete(communityImage);
				resp.setSuccess(true);
			}
			catch (Exception e)
			{
				resp.setSuccess(false);
				resp.setErrMsg("对不起，操作失败!");
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
		CommunityWelcomeImage communityWelcomeImage = communityImageService.queryCommunityById(appContext.getCommunity());
		if (communityWelcomeImage != null)
		{
			communityImageService.delete(communityWelcomeImage);
		}
		CommunityWelcomeImage cImage = new CommunityWelcomeImage();
		cImage.setCommunity(appContext.getCommunity());
		cImage.setImage(communityImageService.queryCommunityImageById(id));
		cImage.setCreator(user);
		cImage.setCreateTime(new Date());
		communityImageService.save(cImage);
		resp.setSuccess(true);
		resp.setErrMsg("恭喜您，设置首页图片成功");

		return JsonUtil.toJson(resp);
	}

	@RequestMapping(value = "/cancleDefaultImage/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String cancleDefaultImage(@PathVariable("id") long id, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);

		Response resp = new Response();
		CommunityWelcomeImage communityWelcomeImage = communityImageService.queryCommunityById(appContext.getCommunity());
		if (communityWelcomeImage == null)
		{
			resp.setSuccess(false);
			resp.setErrMsg("对不起，操作失败");
		}
		else
		{
			communityImageService.delete(communityWelcomeImage);
			resp.setSuccess(true);
			resp.setErrMsg("恭喜您，取消首页图片成功");
		}
		return JsonUtil.toJson(resp);
	}

	@RequestMapping(value = "/readCommunityImage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readCorpImage(HttpServletRequest request)
	{
		DataTable<ImageCommand> dataGrid = new DataTable<ImageCommand>();
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		
		int iDisplayStart=Integer.valueOf(request.getParameter("iDisplayStart"));
		String sEcho=request.getParameter("sEcho");
		int iDisplayLength=Integer.valueOf(request.getParameter("iDisplayLength"));
		CommunityWelcomeImage communityWelcomeImage = communityImageService.queryCommunityById(appContext.getCommunity());
		long image = -1;
		if (communityWelcomeImage != null)
		{
			image = communityWelcomeImage.getImage().getId();
		}

		List<CommunityImage> cImages = communityImageService.queryCommunityImages(appContext.getCommunity(),iDisplayStart,iDisplayLength);

		for (int i = 0; i < cImages.size(); i++)
		{
			ImageCommand imageCommand = new ImageCommand();
			imageCommand.setId(cImages.get(i).getId());
			imageCommand.setTitle(cImages.get(i).getTitle());
			imageCommand.setDescript(cImages.get(i).getDescript());
			imageCommand.setImageUrl(cImages.get(i).getImageUrl());
			imageCommand.setUrl(cImages.get(i).getUrl());
			if (image == cImages.get(i).getId())
			{
				imageCommand.setDefalutImage(true);
			}
			dataGrid.getAaData().add(imageCommand);
		}
		long totalCount=communityImageService.getCommunityImageCount(appContext.getCommunity());
		dataGrid.setsEcho(sEcho);
		dataGrid.setiTotalRecords(totalCount);
		dataGrid.setiTotalDisplayRecords(totalCount);
		return JsonUtil.toJson(dataGrid);
	}

	@RequestMapping(value = "/community", method = RequestMethod.GET)
	public ModelAndView initCorp(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		ModelAndView modelAndView = new ModelAndView("community/community", "communityCommand", new CommunityCommand());
		CommunityCommand communityCommand = new CommunityCommand();
		communityCommand.setId(-1);
		communityCommand.setCorp(user.getCorp().getId());
		modelAndView.addObject("communityCommand", communityCommand);
		request.setAttribute("communityCommand", new Community());
		return modelAndView;
	}

	@RequestMapping(value = "/community", method = RequestMethod.POST)
	public ModelAndView saveCommunity(HttpServletRequest request, @Valid CommunityCommand communityCommand, BindingResult result)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		ModelAndView modelAndView = new ModelAndView("community/editCommunity");
		modelAndView.addObject("communityCommand", communityCommand);
		if (result.hasErrors())
		{
			return modelAndView;
		}
		try
		{
			if (communityCommand.getId() == -1)
			{
				Community community = new Community();
				community.setCorp(user.getCorp());
				community.setProvince(communityCommand.getProvince());
				community.setCity(communityCommand.getCity());
				community.setCounty(communityCommand.getCounty());
				community.setName(communityCommand.getName());
				community.setAddress(communityCommand.getAddress());
				community.setTelePhone(communityCommand.getTelephone());
				community.setMobilePhone(communityCommand.getMobilephone());
				community.setDescript(communityCommand.getDescript());
				community.setCopyright(communityCommand.getCopyright());
				community.setMap("");
				community.setCreator(user);
				community.setCreateTime(new Date());
				communityService.save(community);
				request.setAttribute("corp", community);
				
				String ticket=ApiQRcode.creatForever(access_TokenService.getAccess_Token().getAccessToken(), (int)community.getId()).getTicket();
				community.setTicket(ticket);
				community.setQrcode(ApiQRcode.show(ticket));
				communityService.update(community);
				modelAndView.addObject("infoMsg", "恭喜您，操作成功！");
			}
			else
			{
				Community community = communityService.queryCommunityById(communityCommand.getId());
				community.setProvince(communityCommand.getProvince());
				community.setCity(communityCommand.getCity());
				community.setCounty(communityCommand.getCounty());
				community.setName(communityCommand.getName());
				community.setAddress(communityCommand.getAddress());
				community.setTelePhone(communityCommand.getTelephone());
				community.setMobilePhone(communityCommand.getMobilephone());
				community.setDescript(communityCommand.getDescript());
				community.setCopyright(communityCommand.getCopyright());
				community.setMap("");
				community.setCreator(user);
				community.setCreateTime(new Date());
				String ticket=ApiQRcode.creatForever(access_TokenService.getAccess_Token().getAccessToken(), (int)community.getId()).getTicket();
				community.setTicket(ticket);
				community.setQrcode(ApiQRcode.show(ticket));
				communityService.update(community);
				request.setAttribute("communityCommand", community);
				modelAndView.addObject("infoMsg", "恭喜您，操作成功！");
			}
		}
		catch (Exception e)
		{
			CommonConstant.exceptionLogger.info(e);
			modelAndView.addObject("infoMsg", "对不起，操作失败！");
		}
		return modelAndView;
	}

	// edit pages
	@RequestMapping(value = "/editCommunity/{communityID}", method = RequestMethod.GET)
	public ModelAndView editCommunity(@PathVariable("communityID") long communityID, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		request.setAttribute("corpId", user.getCorp().getId());
		CommunityCommand communityCommand = new CommunityCommand();
		ModelAndView modelAndView = new ModelAndView("community/editCommunity");
		Community community = new Community();
		community.setId(-1);
		if (communityID != -1)
		{
			community = communityService.queryCommunityById(communityID);
			if (community != null)
			{

				communityCommand.setId(communityID);
				communityCommand.setCorp(community.getCorp().getId());
				communityCommand.setProvince(community.getProvince());
				// corpCommand.setProvinceCode(sysAreaService.getAreaByProvinceName(corp.getProvince()).getCode());
				communityCommand.setCity(community.getCity());
				// corpCommand.setCityCode(sysAreaService.getAreaByParentAndName(corpCommand.getProvinceCode(),corpCommand.getCity()).getCode());
				communityCommand.setCounty(community.getCounty());
				// corpCommand.setCoutyCode(sysAreaService.getAreaByParentAndName(corpCommand.getCityCode(),corpCommand.getCouty()).getCode());
				communityCommand.setName(community.getName());
				communityCommand.setAddress(community.getAddress());
				communityCommand.setTelephone(community.getTelePhone());
				communityCommand.setMobilephone(community.getMobilePhone());
				communityCommand.setDescript(community.getDescript());
				communityCommand.setCopyright(community.getCopyright());

			}
		}
		modelAndView.addObject("communityCommand", communityCommand);
		request.setAttribute("communityCommand", communityCommand);
		return modelAndView;
	}

	@RequestMapping(value = "/readCommunitys", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readCommunitys(HttpServletRequest request, @RequestParam String sEcho, @RequestParam int iDisplayStart, @RequestParam int iDisplayLength)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();

		DataTableRowCnt rowCnt = new DataTableRowCnt();
		DataTable<Community> dgList = new DataTable<Community>();
		dgList.setAaData((ArrayList<Community>) communityService.queryCommunitysByCorp(user.getCorp(), iDisplayStart, iDisplayLength, rowCnt));
		dgList.setiTotalRecords(rowCnt.getRowCnt());
		dgList.setiTotalDisplayRecords(rowCnt.getRowCnt());
		dgList.setsEcho(sEcho);

		return JsonUtil.toJson(dgList);
	}
}

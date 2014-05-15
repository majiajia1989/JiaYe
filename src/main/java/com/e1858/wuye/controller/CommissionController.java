package com.e1858.wuye.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.Commission;
import com.e1858.wuye.entity.hibernate.CommissionResponse;
import com.e1858.wuye.entity.hibernate.CommissionTemplate;
import com.e1858.wuye.entity.hibernate.CommissionType;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.CommissionCommand;
import com.e1858.wuye.pojo.CommissionResponseCommand;
import com.e1858.wuye.pojo.CommissionTemplateCommand;
import com.e1858.wuye.pojo.CommissionTypeCommand;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.Response;
import com.e1858.wuye.pojo.TreeNode;
import com.e1858.wuye.service.hibernate.CommissionService;
import com.e1858.wuye.service.hibernate.SubscriberHouseService;
import com.e1858.wuye.utils.JsonUtil;

@Controller
@RequestMapping("/commission")
public class CommissionController
{
	@Autowired
	private CommissionService commissionService;

	@Autowired
	private SubscriberHouseService subscriberHouseService;
	@RequestMapping("/commissionTypes")
	public ModelAndView commissionTypes()
	{
		ModelAndView modelAndView=new ModelAndView("commission/commissionTypes","commissionTypeCommand",new CommissionTypeCommand());
		return modelAndView;
	}
	@RequestMapping("/commissions")
	public ModelAndView commissions(HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView("commission/commissions","commissionResponseCommand",new CommissionResponseCommand());
		request.setAttribute("commissionType", readCommissionType(request));
		return modelAndView;
	}
	@RequestMapping(value="/historyReply/{commissionID}",method=RequestMethod.GET)
	public ModelAndView historyReply(@PathVariable long commissionID,HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView("commission/historyReply");
		Commission commission=commissionService.getCommission(commissionID);
		CommissionResponseCommand commissionResponseCommand=new CommissionResponseCommand();
		commissionResponseCommand.setCommissionID(commissionID);	
		commissionResponseCommand.setCommissionCreator(commission.getCreator().getAlias());
		commissionResponseCommand.setCommissionContent(commission.getContent());
		modelAndView.addObject("commissionResponseCommand", commissionResponseCommand);
		return modelAndView;
	}
	
	@RequestMapping(value = "/queryAllReply/{commissionID}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAllReply(@PathVariable long commissionID,HttpServletRequest request){
		Commission commission=commissionService.getCommission(commissionID);
	
		int iDisplayStart=Integer.valueOf(request.getParameter("iDisplayStart"));
		String sEcho=request.getParameter("sEcho");
		int iDisplayLength=Integer.valueOf(request.getParameter("iDisplayLength"));
		
		List<CommissionResponse> commissionReplys = commissionService.getCommissionResponses(commission,iDisplayStart,iDisplayLength);
		
		List<CommissionResponseCommand> commands = new ArrayList<CommissionResponseCommand>();
		if (commissionReplys != null) 
		{
			
			for (CommissionResponse commissionResponse : commissionReplys) {
				CommissionResponseCommand cmd = new CommissionResponseCommand();
				cmd.setContent(HtmlUtils.htmlEscape(commissionResponse.getContent()).replaceAll("\r\n","<br>"));
				cmd.setReplyTime(commissionResponse.getCreateTime());
				cmd.setCreator(commissionResponse.getCreator().getName());
				commands.add(cmd);
			}
		}
		long totalCount=commissionService.getTotalResponseCount(commission);
		DataTable<CommissionResponseCommand> dataTable = new DataTable<CommissionResponseCommand>();
		dataTable.setsEcho(sEcho);
		dataTable.setAaData(commands);
		dataTable.setiTotalRecords(totalCount);
		dataTable.setiTotalDisplayRecords(totalCount);
		return JsonUtil.toJson(dataTable);
	}
	
	
	private List<TreeNode> readCommissionType(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		Community community=appContext.getCommunity();
		List<TreeNode> result = new ArrayList<TreeNode>();
		if(community!=null){
			for(CommissionType commissionType:commissionService.getCommissionTypes(community)){
				TreeNode treeNode=new TreeNode();
				treeNode.setId(((Long)commissionType.getId()).toString());
				treeNode.setText(commissionType.getName());
				result.add(treeNode);
			}
		}
		return result;
	}
	
	
	@RequestMapping(value = "/commissionResponse/{commissionID}", method = RequestMethod.GET)
	public ModelAndView commissionResponse(@PathVariable long commissionID, HttpServletRequest request)
	{
		Commission commission = commissionService.getCommission(commissionID);
		CommissionResponseCommand commissionResponseCommand = new CommissionResponseCommand();
		commissionResponseCommand.setCommissionID(commissionID);
		commissionResponseCommand.setCommissionCreator(commission.getCreator().getOpenid());
		commissionResponseCommand.setCommissionContent(commission.getContent());;
		ModelAndView modelAndView = new ModelAndView("commission/commissionResponse", "commissionResponseCommand", commissionResponseCommand);
		return modelAndView;
	}
	
	@RequestMapping(value = "/commissionResponse", method = RequestMethod.POST)
	public ModelAndView commissionResponse(HttpServletRequest request, @Valid CommissionResponseCommand commissionResponseCommand, BindingResult result)
	{
		ModelAndView modelAndView=new ModelAndView();
		if(commissionResponseCommand.isFromReply()){
			 modelAndView = new ModelAndView("redirect:/commission/historyReply/"+commissionResponseCommand.getCommissionID(),"commissionResponseCommand",commissionResponseCommand);
		}else{
			 modelAndView = new ModelAndView("commission/commissions","commissionResponseCommand",commissionResponseCommand);
		}
		
		if (result.hasErrors())
		{
			return modelAndView;
		}
		
		try {
			HttpSession session = request.getSession();
			AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
			SysUser user=appContext.getUser();
			CommissionResponse commissionResponse=new CommissionResponse();
			Commission commission = commissionService.getCommission(commissionResponseCommand.getCommissionID());
			commissionResponse.setCommission(commission);
			commissionResponse.setContent(commissionResponseCommand.getContent());
			commissionResponse.setCreator(user);
			commissionResponse.setCreateTime(new Date());
			commissionService.saveCommissionResponse(commissionResponse);
		} catch (Exception e) {
			e.printStackTrace();
			CommonConstant.exceptionLogger.info(e);
		}
		request.setAttribute("commissionType", readCommissionType(request));
		
		return modelAndView;
	}
	
	

	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAll(@RequestParam(required=false) String  type,@RequestParam(required=false) String  response,@RequestParam(required=false) String  keyword,
			@RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE) Date begin, @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE) Date end,
			HttpServletRequest request)
	{
		if (end != null) {
			end = new Date(end.getTime() + 24*60*60*1000);
		}
		
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		Community community=appContext.getCommunity();
		CommissionType commissionType = null;
		if(type!=null&&!(type.equals("0"))){
			commissionType=commissionService.getCommissionTypeByID(Integer.valueOf(type));
		}
		if(!(keyword.equals(""))||keyword!=null){
			try{
				byte keyword_decode[]; 
				keyword_decode = keyword.getBytes("ISO-8859-1"); 
				keyword= new String(keyword_decode, "UTF-8"); 
			}catch(Exception e){
				e.printStackTrace();
				CommonConstant.exceptionLogger.info(e);
			}
		}	
		if(response==null||response.equals("0")){
			response="2";
		}
		
		int iDisplayStart=Integer.valueOf(request.getParameter("iDisplayStart"));
		String sEcho=request.getParameter("sEcho");
		int iDisplayLength=Integer.valueOf(request.getParameter("iDisplayLength"));
		
		
		List<Commission> commissions = commissionService.getCommissionsByDate(community,commissionType,keyword, begin, end,iDisplayStart,iDisplayLength,response,false);
		
		List<CommissionCommand> commands = new ArrayList<CommissionCommand>();
		if (commissions != null) 
		{
			for (Commission commission : commissions) {
				CommissionCommand cmd = new CommissionCommand();
				cmd.setContent(HtmlUtils.htmlEscape(commission.getContent()).replaceAll("\r\n","<br>"));
				cmd.setId(commission.getId());
				cmd.setCreateTime(commission.getCreateTime());
				cmd.setCreator(commission.getCreator().getAlias());
				long totalCount=commissionService.getTotalResponseCount(commission);
				cmd.setResponsed(totalCount> 0);
				commands.add(cmd);
			}
		}
		long totalCount=commissionService.getTotalCount(community,commissionType,keyword,begin,end,response);
		DataTable<CommissionCommand> dataTable = new DataTable<CommissionCommand>();
		dataTable.setsEcho(sEcho);
		dataTable.setAaData(commands);
		dataTable.setiTotalRecords(totalCount);
		dataTable.setiTotalDisplayRecords(totalCount);
		return JsonUtil.toJson(dataTable);
	}
	@RequestMapping(value = "/queryAllType", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readCommissionTypes(HttpServletRequest request)
	{
		DataTable<CommissionTypeCommand> dataGrid = new DataTable<CommissionTypeCommand>();
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		Community community=appContext.getCommunity();
		
		int iDisplayStart=Integer.valueOf(request.getParameter("iDisplayStart"));
		String sEcho=request.getParameter("sEcho");
		int iDisplayLength=Integer.valueOf(request.getParameter("iDisplayLength"));
		
		List<CommissionType> commissionTypes=commissionService.getCommissionTypes(community,iDisplayStart,iDisplayLength);
		
		for(int i=0;i<commissionTypes.size();i++){
			CommissionTypeCommand commissionTypeCommand=new CommissionTypeCommand();
			commissionTypeCommand.setId(commissionTypes.get(i).getId());
			commissionTypeCommand.setName(commissionTypes.get(i).getName());
			commissionTypeCommand.setTemplate_count(commissionService.getTotalTemplateCount(commissionTypes.get(i)));
			List<Commission> commissions=commissionService.getCommissionsByType(community,commissionTypes.get(i));
			if(commissions.size()>0){
				commissionTypeCommand.setCommissionuse(true);
			}else{
				commissionTypeCommand.setCommissionuse(false);
			}
			
			dataGrid.getAaData().add(commissionTypeCommand);
		}
		long totalCount=commissionService.getTotalTypeCount(community);
		dataGrid.setsEcho(sEcho);
		dataGrid.setiTotalRecords(totalCount);
		dataGrid.setiTotalDisplayRecords(totalCount);
		return JsonUtil.toJson(dataGrid);
	}

	@RequestMapping(value="/commissionTemplates/{id}",method=RequestMethod.GET)
	public ModelAndView commissionTemplates(@PathVariable("id") long id, HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView("commission/commissionTemplates");
		CommissionTemplateCommand commissionTemplateCommand=new CommissionTemplateCommand();
		commissionTemplateCommand.setType_id(id);
		modelAndView.addObject("commissionTemplateCommand", commissionTemplateCommand);
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/commissionTemplateQueryAll/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readCommissionTemplates(@PathVariable("id") long id,HttpServletRequest request){
		DataTable<CommissionTemplateCommand> dataGrid = new DataTable<CommissionTemplateCommand>();
		CommissionType commissionType=commissionService.getCommissionTypeByID(id);
		
		int iDisplayStart=Integer.valueOf(request.getParameter("iDisplayStart"));
		String sEcho=request.getParameter("sEcho");
		int iDisplayLength=Integer.valueOf(request.getParameter("iDisplayLength"));
		
		List<CommissionTemplate> commissionTemplates=commissionService.getCommissionTemplates(commissionType,iDisplayStart,iDisplayLength);
		for(int i=0;i<commissionTemplates.size();i++){
			CommissionTemplateCommand commissionTemplateCommand=new CommissionTemplateCommand();
			commissionTemplateCommand.setId(commissionTemplates.get(i).getId());
			commissionTemplateCommand.setType_id(id);
			commissionTemplateCommand.setContent(commissionTemplates.get(i).getContent());
			dataGrid.getAaData().add(commissionTemplateCommand);
		}
		long totalCount=commissionService.getTotalTemplateCount(commissionType);
		dataGrid.setsEcho(sEcho);
		dataGrid.setiTotalRecords(totalCount);
		dataGrid.setiTotalDisplayRecords(totalCount);
		return JsonUtil.toJson(dataGrid);
	}
	
	@RequestMapping(value = "/removeTemplateByID/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String removeCommissionTemplate(@PathVariable("id") long id, HttpServletRequest request){
		Response response = new Response();
		try{
			CommissionTemplate commissionTemplate=commissionService.getCommissionTemplateByID(id);
			if(commissionTemplate!=null){
				commissionService.deleteCommissionTemplate(commissionTemplate);
				response.setSuccess(true);
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrMsg("删除失败!");
			CommonConstant.exceptionLogger.info(e);
		}
		return JsonUtil.toJson(response);
	}
	@RequestMapping(value = "/removeTypeByID/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String removeCommissionType(@PathVariable("id") long id, HttpServletRequest request){
		Response response = new Response();
		try{
			CommissionType commissionType=commissionService.getCommissionTypeByID(id);
			if(commissionType!=null){
				commissionService.deleteCommissionType(commissionType);
				response.setSuccess(true);
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrMsg("删除失败!");
			CommonConstant.exceptionLogger.info(e);
		}
		return JsonUtil.toJson(response);
	}
	
	@RequestMapping(value = "/checkCommissionType/{commission_value}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String checkCommissionType(@PathVariable("commission_value") String commission_value, HttpServletRequest request)
	{
		Response response = new Response();
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		SysCorp corp = user.getCorp();
		Community community = appContext.getCommunity();
		CommissionType commissionType = commissionService.getCommissionTypeByName(corp, community, commission_value);

		if (commissionType != null)
		{
			response.setSuccess(true);
		}

		return JsonUtil.toJson(response);
	}
	@RequestMapping(value = "/saveCommissionTemplate", method = RequestMethod.POST)
	public ModelAndView saveCommissionType(HttpServletRequest request, @Valid CommissionTemplateCommand commissionTemplateCommand, BindingResult result){
		ModelAndView modelAndView = new ModelAndView("commission/commissionTemplates");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		CommissionType commissionType;
		try{
			if(commissionTemplateCommand.getType_id()>0){
				if(commissionTemplateCommand.getId()==-1){
					commissionType=commissionService.getCommissionTypeByID(commissionTemplateCommand.getType_id());
					if(commissionTemplateCommand.getContent()!=null){
						CommissionTemplate commissionTemplate=new CommissionTemplate();
						commissionTemplate.setType(commissionType);
						commissionTemplate.setContent(commissionTemplateCommand.getContent());
						commissionTemplate.setCreator(user);
						commissionTemplate.setCreateTime(new Date());
						commissionService.saveCommisionTemplate(commissionTemplate);
					}
				}else{
					CommissionTemplate commissionTemplate=commissionService.getCommissionTemplateByID(commissionTemplateCommand.getId());
					if(commissionTemplateCommand.getContent()!=null){
						commissionTemplate.setContent(commissionTemplateCommand.getContent());
						commissionService.updateCommissionTemplate(commissionTemplate);
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			modelAndView.addObject("errMsg", "操作失败!");
			CommonConstant.exceptionLogger.info(e);
		}
	
		
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/saveCommissionType", method = RequestMethod.POST)
	public ModelAndView saveCommissionType(HttpServletRequest request, @Valid CommissionTypeCommand commissionTypeCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("commission/commissionTypes");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		// ？？
		SysUser user = appContext.getUser();
		SysCorp corp = user.getCorp();
		Community community =appContext.getCommunity();
		CommissionType name_commissionType = commissionService.getCommissionTypeByName(corp, community, commissionTypeCommand.getName());
		modelAndView.addObject("addCommissionTypeCommand", commissionTypeCommand);
		if (name_commissionType != null)
		{
			modelAndView.addObject("errMsg", "事务类型已存在!");
		}
		else
		{
			try
			{
				if(commissionTypeCommand.getId()==-1){
					CommissionType commissionType = new CommissionType();
					commissionType.setCorp(corp);
					commissionType.setCommunity(community);
					commissionType.setCreator(user);
					commissionType.setName(commissionTypeCommand.getName());
					commissionType.setCreateTime(new Date());
					commissionService.saveCommissionType(commissionType);
				}else{
					CommissionType commissionType=commissionService.getCommissionTypeByID(commissionTypeCommand.getId());
					commissionType.setName(commissionTypeCommand.getName());
					commissionService.updateCommissionType(commissionType);
				}
				
				
			}
			catch (Exception e)
			{
				modelAndView.addObject("errMsg", "操作失败!");
				CommonConstant.exceptionLogger.info(e);
			}

		}

		return modelAndView;
	}

}

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

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseUnit;
import com.e1858.wuye.entity.hibernate.MT;
import com.e1858.wuye.entity.hibernate.SubscriberHouse;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.Response;
import com.e1858.wuye.pojo.SendWeixinCommand;
import com.e1858.wuye.pojo.SubscriberHouseCommand;
import com.e1858.wuye.pojo.TreeNode;
import com.e1858.wuye.service.hibernate.HouseService;
import com.e1858.wuye.service.hibernate.HouseUnitService;
import com.e1858.wuye.service.hibernate.MTService;
import com.e1858.wuye.service.hibernate.MsgTypeService;
import com.e1858.wuye.service.hibernate.SubscriberHouseService;
import com.e1858.wuye.service.hibernate.Access_TokenService;
import com.e1858.wuye.service.hibernate.Weixin_GongHaoService;
import com.e1858.wuye.service.hibernate.Weixin_UserInfoService;
import com.e1858.wuye.utils.JsonUtil;
import com.e1858.wechat.api.ApiClient;
import com.e1858.wechat.api.json.JsonResponse;
import com.e1858.wechat.common.Constants.MsgType;

@Controller
@RequestMapping("/community")
public class OwnerWeixinController
{
	@Autowired
	private SubscriberHouseService subscriberHouseService;
	
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private HouseUnitService houseUnitService;
	@Autowired 
	private MTService mtService;
	
	@Autowired 
	private MsgTypeService msgTypeService;
	
	@Autowired
	private Access_TokenService access_TokenService;
	@Autowired
	private Weixin_GongHaoService gongHaoService;
	@Autowired
	private Weixin_UserInfoService weixin_UserInfoService;
	
	@RequestMapping("/weixins")
	public ModelAndView initOwnerWeixins(HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView("community/weixins","sendWeixinCommand",new SendWeixinCommand());
		request.setAttribute("houseInfo", getHouseandUnit(request));
		return modelAndView;
	}
	private String getHouseandUnit(HttpServletRequest request){
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		Community community=appContext.getCommunity();
		List<TreeNode> house_result=new ArrayList<TreeNode>();
		if(community!=null){
			List<House> houses=houseService.queryHouseByCommunity(community);
			
			for(House house:houses){
				TreeNode treeNode=new TreeNode();
				treeNode.setId(String.valueOf(house.getId()));
				treeNode.setText(house.getName());
				List<HouseUnit> houseUnits=houseUnitService.queryHouseUnitByCommunity(community);
				ArrayList<TreeNode> house_unit=new ArrayList<TreeNode>();
				for(HouseUnit houseUnit:houseUnits){
					TreeNode treeNode_two=new TreeNode();
					treeNode_two.setId(String.valueOf(houseUnit.getId()));
					treeNode_two.setText(houseUnit.getName());
					house_unit.add(treeNode_two);
				}
				treeNode.setChildren(house_unit);
				house_result.add(treeNode);
			}
		}
		return JsonUtil.toJson(house_result);
	}	
	@RequestMapping(value = "/queryAllWeixin", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAll(@RequestParam(required=false) String  house,@RequestParam(required=false) String houseunit, @RequestParam(required=false) String  keyword,
			@RequestParam(required=false) Boolean  car, @RequestParam(required=false) Boolean mobile,
			HttpServletRequest request){
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		Community community=appContext.getCommunity();
		if(keyword!=null){
			try{
				byte keyword_decode[]; 
				keyword_decode = keyword.getBytes("ISO-8859-1"); 
				keyword= new String(keyword_decode, "UTF-8"); 
			}catch(Exception e){
				e.printStackTrace();
				CommonConstant.exceptionLogger.info(e);
			}
		}	
		int iDisplayStart=Integer.valueOf(request.getParameter("iDisplayStart"));
		String sEcho=request.getParameter("sEcho");
		int iDisplayLength=Integer.valueOf(request.getParameter("iDisplayLength"));
		
		List<SubscriberHouse> subscriberHouses=subscriberHouseService.querySubscriberHourse(community, house, houseunit, car, mobile, keyword,iDisplayStart,iDisplayLength);
		
		List<SubscriberHouseCommand> commands = new ArrayList<SubscriberHouseCommand>();
		if (subscriberHouses != null) 
		{
			for (SubscriberHouse subscriberHouse : subscriberHouses) {
				SubscriberHouseCommand cmd = new SubscriberHouseCommand();
				cmd.setNick_name(weixin_UserInfoService.getUserByUserName(subscriberHouse.getOpenid()).getNickname());
				cmd.setHouse_name(subscriberHouse.getHouse().getName());
				cmd.setHouse_unit(subscriberHouse.getHouseUnit().getName());
				cmd.setHouse_room(subscriberHouse.getHouseRoom().getName());
				cmd.setOpenid(subscriberHouse.getOpenid());
				commands.add(cmd);
			}
		}
		long totalCount=subscriberHouseService.getTotalCount(community, house, houseunit, car, mobile, keyword);
		DataTable<SubscriberHouseCommand> dataTable = new DataTable<SubscriberHouseCommand>();
		dataTable.setAaData(commands);
		dataTable.setsEcho(sEcho);
		dataTable.setiTotalDisplayRecords(totalCount);
		dataTable.setiTotalRecords(totalCount);
		return JsonUtil.toJson(dataTable);
	}
	
	
	@RequestMapping(value="/sendWeixin",method=RequestMethod.POST)
	public ModelAndView send(HttpServletRequest request, @Valid SendWeixinCommand sendWeixinCommand, BindingResult result){
		ModelAndView modelAndView=new ModelAndView("community/weixins","sendWeixinCommand",new SendWeixinCommand());
		//?baocun bu l 发送微信
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user=appContext.getUser();
		MT mt=new MT();
		try{
			if(sendWeixinCommand.getContent()!=""){	
				JsonResponse jsonResponse=ApiClient.sendText(access_TokenService.getAccess_Token().getAccessToken(), sendWeixinCommand.getOpenid(),sendWeixinCommand.getContent());
				if(jsonResponse.getErrcode()==0){
					modelAndView.addObject("infoMsg", "发送成功!");
				}else{
					modelAndView.addObject("errMsg", jsonResponse.getErrmsg());
				}
				mt.setMo(0);
				mt.setMsg(0);
				//??微信公号openid; t_Weixin_GongHao.openid
				mt.setFromUserName(gongHaoService.getGongHao().getOpenid());
				mt.setToUserName(sendWeixinCommand.getOpenid());
				mt.setContent(sendWeixinCommand.getContent());
				mt.setMsgType(MsgType.TEXT);
				mt.setChildren("-1");
				mt.setDescription("-1");
				mt.setTitle("-1");
				mt.setPicUrl("-1");
				mt.setPriority(Byte.parseByte("0"));
				mt.setErrCode(jsonResponse.getErrcode());
				mt.setErrMsg(jsonResponse.getErrmsg());
				mt.setSendTime(new Date());
				mt.setPerformTime(new Date());
				mt.setPresentTime(new Date());
				mt.setPresenter(user.getId());
				mt.setAuditTime(new Date());
				mt.setAuditor(user.getId());
				mtService.saveMT(mt);	
				
			}
		}catch(Exception e){
			e.printStackTrace();
			CommonConstant.exceptionLogger.info(e);
		}
		request.setAttribute("houseInfo", getHouseandUnit(request));
		return modelAndView;
	}
	
	@RequestMapping(value="removeBundlingByOpenID/{openID}",method=RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String removeBundling(@PathVariable("openID") String openID,HttpServletRequest request){
		Response response=new Response();
		try{
			if(subscriberHouseService.delete(openID)){
				response.setSuccess(true);
			}else{
				response.setSuccess(false);
				response.setErrMsg("解绑失败!");
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrMsg("解邦失败!");
			CommonConstant.exceptionLogger.info(e);
		}
		return JsonUtil.toJson(response);
	}
}

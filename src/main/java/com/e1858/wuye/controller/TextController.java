package com.e1858.wuye.controller;

import java.io.UnsupportedEncodingException;
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
import com.e1858.wuye.common.CommonConstant.DefaultMsgType;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.DefaultMsg;
import com.e1858.wuye.entity.hibernate.Msg;
import com.e1858.wuye.entity.hibernate.MsgKey;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.MsgCommand;
import com.e1858.wuye.pojo.TextCommand;
import com.e1858.wuye.service.hibernate.CommunityService;
import com.e1858.wuye.service.hibernate.CorpService;
import com.e1858.wuye.service.hibernate.MessageService;
import com.e1858.wuye.utils.JsonUtil;
import com.e1858.wuye.utils.Util;

@Controller
@RequestMapping("/texts")
public class TextController
{
	@Autowired
	private MessageService messageService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private CorpService corpService;

	public boolean judgeUrl(String content)
	{
		return Util.verifyUrl(content);
	}

	public boolean isHasUrl(String content)
	{
		if (content.startsWith("http") || content.startsWith("https"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@RequestMapping(value = "/editText/{msgID}", method = RequestMethod.GET)
	public ModelAndView editText(@PathVariable("msgID") long msgID, HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView("message/editText", "textCommand", new TextCommand());
		if (msgID != -1)
		{
			Msg msg = messageService.getMsg(msgID);
			List<MsgKey> msgKeys_list = messageService.getMsgKeys(msg);
			StringBuilder keys = new StringBuilder();
			byte msgkey_matching = 0;
			if (msgKeys_list.size() > 0)
			{
				msgkey_matching = msgKeys_list.get(0).getMatching();
			}
			for (int i = 0; i < msgKeys_list.size(); i++)
			{
				if (i == msgKeys_list.size() - 1)
				{
					keys.append(msgKeys_list.get(i).getCommand());
				}
				else
				{
					keys.append(msgKeys_list.get(i).getCommand()).append(CommonConstant.KEYWORD_SEPARATOR);
				}
			}
			request.setAttribute("msg", msg);
			request.setAttribute("matching", msgkey_matching);
			TextCommand textCommand = new TextCommand();
			textCommand.setContent(Util.HtmlEncode(msg.getContent()));
			textCommand.setKeyword(keys.toString());
			textCommand.setMatching(msgkey_matching);
			modelAndView.addObject("textCommand", textCommand);
		}
		return modelAndView;

	}

	@RequestMapping(value = "/editText/{msgID}", method = RequestMethod.POST)
	public ModelAndView editText(@PathVariable("msgID") long msgID, HttpServletRequest request, @Valid TextCommand textCommand, BindingResult result)
	{
		ModelAndView modelAndView = new ModelAndView("message/editText");
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		// ？？
		SysUser user = appContext.getUser();
		SysCorp corp = user.getCorp();
		Community community = appContext.getCommunity();
		Msg text_msg = new Msg();
		if (result.hasErrors())
		{
			return modelAndView;
		}
		modelAndView.addObject("textCommond", textCommand);
		try
		{
			if (msgID == -1)
			{
				if (isHasUrl(textCommand.getContent()))
				{
					if (judgeUrl(textCommand.getContent()))
					{
						if(textCommand.isAttentionMsg()){
							text_msg.setCorp(corpService.queryCorpById(1));
							text_msg.setCommunity(communityService.queryCommunityById(1));
						}else{
							text_msg.setCorp(corp);
							text_msg.setCommunity(community);
						}
						text_msg.setMsgType(CommonConstant.MsgType.Text);
						text_msg.setContent(textCommand.getContent());
						text_msg.setCreator(user);
						text_msg.setCreateTime(new Date());
						text_msg.setUrl(null);
						text_msg.setChildren(null);
						text_msg.setDescription("-1");
						text_msg.setPicUrl("-1");
						text_msg.setSort(Byte.parseByte("0"));
						text_msg.setTitle("-1");
						messageService.saveMsg(text_msg);

					}
					else
					{
						modelAndView.addObject("errMsg", CommonConstant.ValidMessage.URL);
					}
				}
				else
				{
					if(textCommand.isAttentionMsg()){
						text_msg.setCorp(corpService.queryCorpById(1));
						text_msg.setCommunity(communityService.queryCommunityById(1));
					}else{
						text_msg.setCorp(corp);
						text_msg.setCommunity(community);
					}
					text_msg.setMsgType(CommonConstant.MsgType.Text);
					text_msg.setContent(textCommand.getContent());
					text_msg.setCreator(user);
					text_msg.setCreateTime(new Date());
					text_msg.setUrl(null);
					text_msg.setChildren(null);
					text_msg.setDescription("-1");
					text_msg.setPicUrl("-1");
					text_msg.setSort(Byte.parseByte("0"));
					text_msg.setTitle("-1");
					messageService.saveMsg(text_msg);

				}
				if (!textCommand.getKeyword().equals(""))
				{
					try
					{
						String[] keys = textCommand.getKeyword().split("\\s+");
						for (int i = 0; i < keys.length; i++)
						{
							MsgKey msgKey = new MsgKey();
							msgKey.setMatching(textCommand.getMatching());
							msgKey.setMsg(text_msg);
							msgKey.setCommand(keys[i]);
							messageService.saveMsgKey(msgKey);
						}
					}
					catch (Exception e)
					{
						modelAndView.addObject("errMsg", "添加信息失败！");
						CommonConstant.exceptionLogger.info(e);
					}
				}
				try
				{
					if (textCommand.isAttentionMsg())
					{
						setAttentionMsgByID_private(text_msg, request, CommonConstant.DefaultMsgType.AttentionReply);
					}
					else if (textCommand.isDefaultMsg())
					{
						setAttentionMsgByID_private(text_msg, request, CommonConstant.DefaultMsgType.NoMatchReply);
					}
					else
					{
						modelAndView.setViewName("redirect:/message/texts");
					}

				}
				catch (Exception e)
				{
					e.printStackTrace();
					modelAndView.addObject("errMsg", "添加信息失败！");
					CommonConstant.exceptionLogger.info(e);
				}

			}
			else
			{
				Msg updateMsg = messageService.getMsg(msgID);
				messageService.deleteMsgKeyByMsg(updateMsg);
				if (isHasUrl(textCommand.getContent()))
				{
					if (judgeUrl(textCommand.getContent()))
					{
						updateMsg.setContent(textCommand.getContent());
						messageService.updateMsg(updateMsg);
						modelAndView.setViewName("redirect:/message/texts");
					}
					else
					{
						modelAndView.addObject("errMsg", CommonConstant.ValidMessage.URL);
					}
				}
				else
				{
					updateMsg.setContent(textCommand.getContent());
					messageService.updateMsg(updateMsg);
					modelAndView.setViewName("redirect:/message/texts");
				}
				if (!textCommand.getKeyword().equals(""))
				{
					try
					{
						String[] keys = textCommand.getKeyword().split("\\s+");
						for (int i = 0; i < keys.length; i++)
						{
							MsgKey msgKey = new MsgKey();
							msgKey.setMatching(textCommand.getMatching());
							msgKey.setMsg(updateMsg);
							msgKey.setCommand(keys[i]);
							messageService.saveMsgKey(msgKey);
						}
					}
					catch (Exception e)
					{
						modelAndView.addObject("errMsg", "修改信息失败！");
						CommonConstant.exceptionLogger.info(e);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			modelAndView.addObject("errMsg", "操作失败！");
			CommonConstant.exceptionLogger.info(e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String readMsgsByType(HttpServletRequest request)
	{
		DataTable<MsgCommand> dataGrid = new DataTable<MsgCommand>();
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		SysCorp corp = user.getCorp();
		Community community = appContext.getCommunity();
	
		int iDisplayStart=Integer.valueOf(request.getParameter("iDisplayStart"));
		String sEcho=request.getParameter("sEcho");
		int iDisplayLength=Integer.valueOf(request.getParameter("iDisplayLength"));
		List<Msg> msgs = messageService.getMsgs(community, CommonConstant.MsgType.Text,iDisplayStart,iDisplayLength);

		List<DefaultMsg> defaultMsg_list = messageService.queryDefaultMsg(corp, community, DefaultMsgType.NoMatchReply);
		long defaultMsgID = -1;
		long attentionMsgID = -1;
		if (defaultMsg_list.size() > 0)
		{
			defaultMsgID = messageService.queryDefaultMsg(corp, community, DefaultMsgType.NoMatchReply).get(0).getMsg().getId();
		}

		List<DefaultMsg> attentionMsg_list = messageService.queryDefaultMsg(corpService.queryCorpById(1), communityService.queryCommunityById(1), CommonConstant.DefaultMsgType.AttentionReply);
		if (attentionMsg_list.size() > 0)
		{
			attentionMsgID = attentionMsg_list.get(0).getMsg().getId();
		}

		for (int i = 0; i < msgs.size(); i++)
		{
			MsgCommand msg = new MsgCommand();
			Msg entity = msgs.get(i);
			List<MsgKey> msgKeys = messageService.getMsgKeys(entity);
			StringBuilder sb = new StringBuilder();
			int matching = 0;
			if (msgKeys.size() > 0)
			{
				matching = msgKeys.get(0).getMatching();
			}
			for (int j = 0; j < msgKeys.size(); j++)
			{
				sb.append(msgKeys.get(j).getCommand()).append(CommonConstant.KEYWORD_SEPARATOR);
			}
			if (sb.length() != 0)
			{
				sb.deleteCharAt(sb.length() - 1);
			}
			
			/**
			if (entity.getContent().toCharArray().length >= 30)
			{
				msg.setContent(entity.getContent());
				try
				{
					msg.setContent(substring(entity.getContent(), 60) + "...");

				}
				catch (UnsupportedEncodingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				msg.setContent(entity.getContent());
			}
			*/
			msg.setContent(entity.getContent());
			msg.setKeyword(sb.toString());
			msg.setMsgID(entity.getId());
			msg.setDefaultMsgID(defaultMsgID);
			msg.setAttentionMsgID(attentionMsgID);
			msg.setMatching(matching);
			dataGrid.getAaData().add(msg);
		}
		long totalCount=messageService.getMsgsCount(community, CommonConstant.MsgType.Text);
		dataGrid.setsEcho(sEcho);//请求次数
//		dataGrid.setiTotalRecords(dataGrid.getAaData().size());//总记录数
//		dataGrid.setiTotalDisplayRecords(dataGrid.getAaData().size());//总记录数
		dataGrid.setiTotalRecords(totalCount);//总记录数
		dataGrid.setiTotalDisplayRecords(totalCount);//总记录数
		return JsonUtil.toJson(dataGrid);
	}

	public String substring(String text, int length) throws UnsupportedEncodingException
	{
		if (text == null)
		{
			return null;
		}
		StringBuilder sb = new StringBuilder();
		int currentLength = 0;
		for (char c : text.toCharArray())
		{
			currentLength += String.valueOf(c).getBytes("GBK").length;
			if (currentLength <= length)
			{
				sb.append(c);
			}
			else
			{
				break;
			}
		}
		return sb.toString();

	}

	private void setAttentionMsgByID_private(Msg msg, HttpServletRequest request, byte type) throws Exception
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		// ？？
		SysUser user = appContext.getUser();
		SysCorp corp = user.getCorp();
		Community community = appContext.getCommunity();
		if (type == CommonConstant.DefaultMsgType.AttentionReply)
		{
			List<DefaultMsg> defaultMsg_list = messageService.queryDefaultMsg(corpService.queryCorpById(1), communityService.queryCommunityById(1), type);
			if (defaultMsg_list.size() == 0)
			{
				DefaultMsg defaultMsg = new DefaultMsg();
				defaultMsg.setCorp(corpService.queryCorpById(1));
				defaultMsg.setCommunity(communityService.queryCommunityById(1));
				defaultMsg.setMsg(msg);
				defaultMsg.setType(type);
				defaultMsg.setCreator(user);
				defaultMsg.setCreateTime(new Date());
				messageService.saveDefaultMsg(defaultMsg);

			}
			else
			{
				DefaultMsg defaultMsg = defaultMsg_list.get(0);
				defaultMsg.setMsg(msg);
				messageService.updateDefaultMsg(defaultMsg);
			}
		}
		else
		{
			List<DefaultMsg> defaultMsg_list = messageService.queryDefaultMsg(corp, community, type);
			if (defaultMsg_list.size() == 0)
			{
				DefaultMsg defaultMsg = new DefaultMsg();
				defaultMsg.setCorp(corp);
				defaultMsg.setCommunity(community);
				defaultMsg.setMsg(msg);
				defaultMsg.setType(type);
				defaultMsg.setCreator(user);
				defaultMsg.setCreateTime(new Date());
				messageService.saveDefaultMsg(defaultMsg);
			}
			else
			{
				DefaultMsg defaultMsg = defaultMsg_list.get(0);
				defaultMsg.setMsg(msg);
				messageService.updateDefaultMsg(defaultMsg);
			}
		}
	}
}

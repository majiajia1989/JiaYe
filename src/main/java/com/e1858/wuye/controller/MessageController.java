package com.e1858.wuye.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.e1858.wuye.common.CommonConstant.ArticleLinkType;
import com.e1858.wuye.common.CommonConstant.DefaultMsgType;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.DefaultMsg;
import com.e1858.wuye.entity.hibernate.Msg;
import com.e1858.wuye.entity.hibernate.MsgKey;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.ArticleCommand;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.pojo.Response;
import com.e1858.wuye.pojo.TextCommand;
import com.e1858.wuye.service.hibernate.CommunityService;
import com.e1858.wuye.service.hibernate.CorpService;
import com.e1858.wuye.service.hibernate.MessageService;
import com.e1858.wuye.utils.JsonUtil;
import com.e1858.wuye.utils.Util;

@Controller
@RequestMapping("/message")
public class MessageController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private CorpService corpService;
	
	@RequestMapping(value = "/articles", method = RequestMethod.GET)
	public String articles(HttpServletRequest request) {
		return "message/articles";
	}

	@RequestMapping("/texts")
	public String texts() {
		return "message/texts";
	}

	@RequestMapping("/attentionReply")
	public ModelAndView attentionReply(HttpServletRequest request) {
		DefaultMsg defaultMsg = getAttentionMsg(request);
		ModelAndView modelAndView;
		TextCommand textCommand = new TextCommand();
		textCommand.setAttentionMsg(true);
		if (defaultMsg != null) {
			if (defaultMsg.getMsg().getMsgType().equals(CommonConstant.MsgType.Text)) {
				textCommand.setContent(Util.HtmlEncode(defaultMsg.getMsg().getContent()));
			}
		}
		modelAndView = new ModelAndView("message/editText", "textCommand", textCommand);
		return modelAndView;
	}

	@RequestMapping("/attentionReply/{msgType}")
	public ModelAndView attentionReply(@PathVariable String msgType, HttpServletRequest request) {
		DefaultMsg defaultMsg = getAttentionMsg(request);
		ModelAndView modelAndView;
		if (msgType.equals(CommonConstant.MsgType.Text)) {
			TextCommand textCommand = new TextCommand();
			textCommand.setAttentionMsg(true);
			if (defaultMsg != null && defaultMsg.getMsg().getMsgType().equals(CommonConstant.MsgType.Text)) {
				textCommand.setContent(Util.HtmlEncode(defaultMsg.getMsg().getContent()));
			}
			modelAndView = new ModelAndView("message/editText", "textCommand", textCommand);
		} else {
			ArticleCommand articleCommand = null;
			if (defaultMsg != null && defaultMsg.getMsg().getMsgType().equals(CommonConstant.MsgType.Article)) {
				articleCommand = readArticleMsg(defaultMsg.getMsg());
			} else {
				articleCommand = new ArticleCommand();
			}
			articleCommand.setAttentionMsg(true);
			modelAndView = new ModelAndView("message/editArticle", "articleCommand", articleCommand);
		}
		return modelAndView;
	}

	@RequestMapping("/defaultReply")
	public ModelAndView defaultReply(HttpServletRequest request) {
		DefaultMsg defaultMsg = getDefaultMsg(request);
		ModelAndView modelAndView;
		TextCommand textCommand = new TextCommand();
		textCommand.setDefaultMsg(true);
		if (defaultMsg != null && defaultMsg.getMsg().getMsgType().equals(CommonConstant.MsgType.Text)) {
			textCommand.setContent(defaultMsg.getMsg().getContent());
		}
		modelAndView = new ModelAndView("message/editText", "textCommand", textCommand);
		return modelAndView;
	}

	@RequestMapping("/defaultReply/{msgType}")
	public ModelAndView defaultReply(@PathVariable String msgType, HttpServletRequest request) {
		DefaultMsg defaultMsg = getDefaultMsg(request);
		ModelAndView modelAndView;
		if (msgType.equals(CommonConstant.MsgType.Text)) {
			TextCommand textCommand = new TextCommand();
			textCommand.setDefaultMsg(true);
			if (defaultMsg != null && defaultMsg.getMsg().getMsgType().equals(CommonConstant.MsgType.Text)) {
				textCommand.setContent(defaultMsg.getMsg().getContent());
			}
			modelAndView = new ModelAndView("message/editText", "textCommand", textCommand);
		} else {
			ArticleCommand articleCommand = null;
			if (defaultMsg != null && defaultMsg.getMsg().getMsgType().equals(CommonConstant.MsgType.Article)) {
				articleCommand = readArticleMsg(defaultMsg.getMsg());
			} else {
				articleCommand = new ArticleCommand();
			}
			articleCommand.setDefaultMsg(true);
			modelAndView = new ModelAndView("message/editArticle", "articleCommand", articleCommand);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/removeMsgByID/{msgID}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String removeMsgByID(@PathVariable("msgID") long msgID, HttpServletRequest request) {
		Response resp = new Response();
		Msg msg = messageService.getMsg(msgID);

		if (msg == null) {
			resp.setSuccess(false);
			resp.setErrMsg("找不到指定的消息!");
		} else {
			try {

				List<DefaultMsg> defaultMsgs = messageService.getDefaultMsgs(msg);
				if (defaultMsgs != null && defaultMsgs.size() > 0) {
					resp.setSuccess(false);
					resp.setErrMsg("不能删除回复消息!");
				} else {
					messageService.deleteMsg(msg);
					resp.setSuccess(true);
				}

			} catch (Exception e) {
				e.printStackTrace();
				resp.setSuccess(false);
				resp.setErrMsg("删除失败!");
				CommonConstant.exceptionLogger.info(e);
			}
		}
		return JsonUtil.toJson(resp);
	}

	@RequestMapping(value = "/setDefaultMsg/{msgID}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String setDefaultMsgByID(@PathVariable("msgID") long msgID, HttpServletRequest request) {
		Response resp = new Response();
		try {
			setDefaultMsgByID_private(msgID, request);
			resp.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setSuccess(false);
			resp.setErrMsg("设置失败!");
			CommonConstant.exceptionLogger.info(e);
		}
		return JsonUtil.toJson(resp);
	}

	private void setDefaultMsgByID_private(long msgID, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		// ？？
		SysUser user = appContext.getUser();
		SysCorp corp = user.getCorp();
		Community community = appContext.getCommunity();
		List<DefaultMsg> defaultMsg_list = messageService.queryDefaultMsg(corp, community, DefaultMsgType.NoMatchReply);
		Msg msg = messageService.getMsg(msgID);
		if (defaultMsg_list.size() == 0) {
			DefaultMsg defaultMsg = new DefaultMsg();
			defaultMsg.setCorp(corp);
			defaultMsg.setCommunity(community);
			defaultMsg.setMsg(msg);
			defaultMsg.setType(DefaultMsgType.NoMatchReply);
			defaultMsg.setCreateTime(new Date());
			defaultMsg.setCreator(user);
			messageService.saveDefaultMsg(defaultMsg);

		} else {
			DefaultMsg defaultMsg = defaultMsg_list.get(0);
			defaultMsg.setMsg(msg);
			messageService.updateDefaultMsg(defaultMsg);
		}
	}

	@RequestMapping(value = "/cancleDefaultMsg/{msgID}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String cancleDefaultMsgByID(@PathVariable("msgID") long msgID, HttpServletRequest request) {
		Response resp = new Response();
		// HttpSession session = request.getSession();
		// AppContext appContext = (AppContext)
		// session.getAttribute(CommonConstant.CONTEXT_APP);
		// ？？
		// SysUser user=appContext.getUser();
		// SysCorp corp = user.getCorp();
		// Community community=messageService.queryCommunity(corp).get(0);
		Msg msg = messageService.getMsg(msgID);
		DefaultMsg defaultMsg = messageService.getDefaultMsgByType(msg, DefaultMsgType.NoMatchReply);
		try {
			messageService.deleteDefaultMsg(defaultMsg);
			resp.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setSuccess(false);
			resp.setErrMsg("设置失败!");
			CommonConstant.exceptionLogger.info( e);
		}

		return JsonUtil.toJson(resp);
	}

	private DefaultMsg getDefaultMsg(HttpServletRequest request) {
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		SysCorp corp = user.getCorp();
		Community community = appContext.getCommunity();
		List<DefaultMsg> defaultMsg_list = messageService.queryDefaultMsg(corp, community, DefaultMsgType.NoMatchReply);
		if (defaultMsg_list.size() > 0) {
			return defaultMsg_list.get(0);
		}
		return null;
	}

	private DefaultMsg getAttentionMsg(HttpServletRequest request) {
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		List<DefaultMsg> defaultMsg_list = messageService.queryDefaultMsg(corpService.queryCorpById(1), communityService.queryCommunityById(1),
				DefaultMsgType.AttentionReply);
		if (defaultMsg_list.size() > 0) {
			return defaultMsg_list.get(0);
		}
		return null;
	}

	// /////////////////////////article/////////////////////////////////
	// edit pages
	@RequestMapping(value = "/editArticle/{msgID}", method = RequestMethod.GET)
	public ModelAndView editArticle(@PathVariable("msgID") long msgID, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("message/editArticle", "articleCommand", new ArticleCommand());
		if (msgID != -1) {
			Msg msg = messageService.getMsg(msgID);
			if (msg != null && msg.getMsgType().equals(CommonConstant.MsgType.Article)) {
				ArticleCommand articleCommand = readArticleMsg(msg);
				modelAndView.addObject("articleCommand", articleCommand);
			}
		}
		return modelAndView;
	}

	@RequestMapping(value = "/queryArticle/{msgID}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryArticle(@PathVariable("msgID") long msgID, HttpServletRequest request) {
		Msg msg = messageService.getMsg(msgID);
		if (msg != null && msg.getMsgType().equals(CommonConstant.MsgType.Article)) {
			DefaultMsg defaultMsg = getDefaultMsg(request);
			DefaultMsg attentionMsg = getAttentionMsg(request);
			ArticleCommand msgCmd = readArticleMsg(msg);
			msgCmd.setDefaultMsg(defaultMsg != null && defaultMsg.getMsg().getId() == msgID);
			msgCmd.setAttentionMsg(attentionMsg != null && attentionMsg.getMsg().getId() == msgID);
			return JsonUtil.toJson(msgCmd);
		}
		return null;
	}

	private ArticleCommand readArticleMsg(Msg msg) {
		ArticleCommand msgCmd = new ArticleCommand();
		List<String> keywords = new ArrayList<String>();
		List<MsgKey> msgKeys = messageService.getMsgKeys(msg);
		byte matching = 0;
		for (MsgKey key : msgKeys) {
			keywords.add(key.getCommand());
			matching = key.getMatching();
		}
		msgCmd.setKeyword(StringUtils.join(keywords, CommonConstant.KEYWORD_SEPARATOR));

		if (msg.getChildren() != null && msg.getChildren().length() > 0) {
			String[] childIDStrs = StringUtils.split(msg.getChildren(), CommonConstant.ARTICLECHILD_SEPARATOR);
			if (childIDStrs != null && childIDStrs.length > 0) {
				List<Long> childIDs = new ArrayList<Long>();
				for (String str : childIDStrs) {
					childIDs.add(Long.parseLong(str));
				}
				List<Msg> children = messageService.queryMsgByIDs(childIDs);
				List<String> titles = new ArrayList<String>();
				for (Msg child : children) {
					titles.add(child.getTitle());
				}
				msgCmd.setChildTitles(titles);
				msgCmd.setChildIDs(Arrays.asList(childIDStrs));
			}
		}
		msgCmd.setMsgID(msg.getId());
		msgCmd.setDescription(msg.getDescription());
		msgCmd.setPictureUrl(msg.getPicUrl());
		msgCmd.setUrl(msg.getUrl());
		msgCmd.setSort(msg.getSort());
		msgCmd.setTitle(msg.getTitle());
		msgCmd.setContent(msg.getContent());
		msgCmd.setMatching(matching);
		msgCmd.setLinkType(msgCmd.getContent() != null && msgCmd.getContent().length() > 0 ? ArticleLinkType.Inner
				: ArticleLinkType.Outer);
		if (msgCmd.getLinkType() == ArticleLinkType.Outer) {
			msgCmd.setContent("");
		} else if (msgCmd.getLinkType() == ArticleLinkType.Inner) {
			msgCmd.setUrl("");
		}
		return msgCmd;
	}

	@RequestMapping(value = "/queryAllArticles", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAllArticles(
			@RequestParam("iDisplayStart") int iDisplayStart, 
			@RequestParam("iDisplayLength") int iDisplayLength,
			@RequestParam(value = "sEcho") int sEcho,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		List<Msg> msgs = messageService.getMsgs(appContext.getCommunity(), CommonConstant.MsgType.Article, iDisplayStart, iDisplayLength);

		DefaultMsg defaultMsg = getDefaultMsg(request);
		DefaultMsg attentionMsg = getAttentionMsg(request);

		List<ArticleCommand> articles = new ArrayList<ArticleCommand>();
		for (int i = 0; i < msgs.size(); i++) {
			ArticleCommand article = readArticleMsg(msgs.get(i));
			article.setDefaultMsg(defaultMsg != null && defaultMsg.getMsg().getId() == msgs.get(i).getId());
			article.setAttentionMsg(attentionMsg != null && attentionMsg.getMsg().getId() == msgs.get(i).getId());
			articles.add(article);
		}
		long count = messageService.getMsgsCount(appContext.getCommunity(), CommonConstant.MsgType.Article);

		DataTable<ArticleCommand> dataGrid = new DataTable<ArticleCommand>();
		dataGrid.setAaData(articles);
		dataGrid.setsEcho("" + sEcho);
		dataGrid.setiTotalRecords(count);
		dataGrid.setiTotalDisplayRecords(count);
		return JsonUtil.toJson(dataGrid);
	}
	
	@RequestMapping(value = "/queryAllArticlesByCorp", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAllArticlesByCorp(HttpServletRequest request) {
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		
		int iDisplayStart=Integer.valueOf(request.getParameter("iDisplayStart"));
		String sEcho=request.getParameter("sEcho");
		int iDisplayLength=Integer.valueOf(request.getParameter("iDisplayLength"));		
		List<Msg> msgs = messageService.getMsgsByCorp(appContext.getCorp(), CommonConstant.MsgType.Article,iDisplayStart,iDisplayLength);

		DefaultMsg defaultMsg = getDefaultMsg(request);
		DefaultMsg attentionMsg = getAttentionMsg(request);

		List<ArticleCommand> articles = new ArrayList<ArticleCommand>();
		for (int i = 0; i < msgs.size(); i++) {
			ArticleCommand article = readArticleMsg(msgs.get(i));
			article.setDefaultMsg(defaultMsg != null && defaultMsg.getMsg().getId() == msgs.get(i).getId());
			article.setAttentionMsg(attentionMsg != null && attentionMsg.getMsg().getId() == msgs.get(i).getId());
			articles.add(article);
		}
		long count = messageService.getMsgsCountByCorp(appContext.getCorp(), CommonConstant.MsgType.Article);
		DataTable<ArticleCommand> dataGrid = new DataTable<ArticleCommand>();
		dataGrid.setAaData(articles);
		dataGrid.setsEcho(sEcho);
		dataGrid.setiTotalRecords(count);
		dataGrid.setiTotalDisplayRecords(count);
		return JsonUtil.toJson(dataGrid);
	}

	@RequestMapping(value = "/saveArticle", method = RequestMethod.POST)
	public ModelAndView saveArticle(HttpServletRequest request, @Valid ArticleCommand articleCommand,
			BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("message/editArticle");
		modelAndView.addObject("articleCommand", articleCommand);
		if (result.hasErrors()) {
			return modelAndView;
		}

		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		SysCorp corp = user.getCorp();
		try {
			String ctxUrl = request.getRequestURL().toString();	
			String projectName = request.getContextPath();	
			String hostName = ctxUrl.substring(0, ctxUrl.lastIndexOf(projectName));
			Msg msg = null;
			boolean isUpdate = articleCommand.getMsgID() != -1;
			if (isUpdate) {
				msg = messageService.getMsg(articleCommand.getMsgID());
				messageService.deleteMsgKeyByMsg(msg);
			}
			if (msg == null) {
				isUpdate = false;
				msg = new Msg();
			}
			if (articleCommand.isAttentionMsg()) {
				msg.setCommunity(communityService.queryCommunityById(1));
				msg.setCorp(corpService.queryCorpById(1));
			} else {
				msg.setCommunity(appContext.getCommunity());
				msg.setCorp(corp);
			}			
			msg.setMsgType(CommonConstant.MsgType.Article);			
			msg.setContent("");
			msg.setCreator(user);
			msg.setCreateTime(new Date());
			msg.setDescription(articleCommand.getDescription());
			msg.setTitle(articleCommand.getTitle());
			{
				String imageUrl = articleCommand.getPictureUrl();
				if(!(imageUrl.startsWith("http") || imageUrl.startsWith("https")))
				{
					imageUrl = hostName.concat(imageUrl);
				}
				msg.setPicUrl(imageUrl);
			}
			if (articleCommand.getLinkType() == ArticleLinkType.Outer) {
				msg.setUrl(articleCommand.getUrl());
				msg.setContent(null);
			} else if (articleCommand.getLinkType() == ArticleLinkType.Inner) {
				msg.setContent(articleCommand.getContent());
				msg.setUrl(null);
			}
			msg.setSort(articleCommand.getSort());
			msg.setChildren(StringUtils.join(articleCommand.getChildIDs(), CommonConstant.ARTICLECHILD_SEPARATOR));

			if (isUpdate) {
				messageService.updateMsg(msg);
			} else {
				messageService.saveMsg(msg);
			}
			if (articleCommand.getLinkType() == ArticleLinkType.Inner){
				msg.setUrl(hostName + projectName + "/message/showArticle/" + msg.getId());
				messageService.updateMsg(msg);
			}			

			String[] keywordCmds = null;

			if (articleCommand.getKeyword() != null && articleCommand.getKeyword().length() > 0) {
				keywordCmds = StringUtils.split(articleCommand.getKeyword(), CommonConstant.KEYWORD_SEPARATOR);
			}
			if (keywordCmds != null) {
				for (String keyword : keywordCmds) {
					MsgKey msgKey = new MsgKey();
					msgKey.setMsg(msg);
					msgKey.setCommand(keyword);
					msgKey.setMatching(articleCommand.getMatching());
					messageService.saveMsgKey(msgKey);
				}
			}

			if (articleCommand.isAttentionMsg()) {
				modelAndView.addObject("successMsg", "关注时回复保存成功!");
				setAttentionMsgByID_private(msg, request);
			} else if (articleCommand.isDefaultMsg()) {
				modelAndView.addObject("successMsg", "默认时回复保存成功!");
				setDefaultMsgByID_private(msg.getId(), request);
			} else {
				modelAndView = new ModelAndView("redirect:/message/articles");
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("errMsg", "添加图文信息时出错!");
			CommonConstant.exceptionLogger.info( e);
		}

		return modelAndView;
	}

	private void setAttentionMsgByID_private(Msg msg, HttpServletRequest request) throws Exception
	{
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user = appContext.getUser();
		List<DefaultMsg> defaultMsg_list = messageService.queryDefaultMsg(corpService.queryCorpById(1), communityService.queryCommunityById(1), DefaultMsgType.AttentionReply);
		if (defaultMsg_list.size() == 0)
		{
			DefaultMsg defaultMsg = new DefaultMsg();
			defaultMsg.setCorp(corpService.queryCorpById(1));
			defaultMsg.setCommunity(communityService.queryCommunityById(1));
			defaultMsg.setMsg(msg);
			defaultMsg.setType(DefaultMsgType.AttentionReply);
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
	
	@RequestMapping(value = "/showArticle/{msgID}", method = RequestMethod.GET)
	public ModelAndView showArticle(@PathVariable("msgID") long msgID, HttpServletRequest request) {
		Msg msg = messageService.getMsg(msgID);
		return new ModelAndView("message/showArticle", "msg", msg);
	}

	// /////////////////////////article/////////////////////////////////
}

package com.e1858.wuye.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e1858.wuye.common.CommonConstant;
import com.e1858.wuye.entity.hibernate.BbsBoard;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.entity.hibernate.SysLoginLog;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.pojo.AppContext;
import com.e1858.wuye.pojo.DataTable;
import com.e1858.wuye.service.hibernate.BbsBoardService;
import com.e1858.wuye.utils.JsonUtil;

@Controller
@RequestMapping("/bbs")
public class BbsController
{
	@Autowired
	private BbsBoardService bbsBoardService;
	
	@RequestMapping("boards")
	public String board(HttpServletRequest request)
	{
		return "bbs/boards";
	}
	
	@RequestMapping(value = "/allBoards", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String allBoards(HttpServletRequest request, @RequestParam(value = "type") int type,@RequestParam(value = "iDisplayStart") int start, @RequestParam(value = "iDisplayLength") int rows, @RequestParam(value = "sEcho") int sEcho)
	{
		long count;
		ArrayList<BbsBoard> boards;
		DataTable<BbsBoard> dataTable = new DataTable<BbsBoard>();
		System.out.println(start);
		System.out.println(rows);
		System.out.println(type);
		HttpSession session = request.getSession();
		AppContext appContext = (AppContext) session.getAttribute(CommonConstant.CONTEXT_APP);
		SysUser user=appContext.getUser();
		Community community=appContext.getCommunity();
		if(type==1)
		{
			count = bbsBoardService.getAllCountByCommunity(community.getId());
			boards = (ArrayList<BbsBoard>)bbsBoardService.getEnableBoardByCommunity(community.getId(),start,rows);
		}
		else if(type==2)
		{
			count = bbsBoardService.getAllCountByCommunity(community.getId());
			boards = (ArrayList<BbsBoard>)bbsBoardService.getDisableBoardByCommunity(community.getId(),start,rows);
		}
		else
		{
			count = bbsBoardService.getAllCountByCommunity(community.getId());
			boards = (ArrayList<BbsBoard>)bbsBoardService.getAllBoardByCommunity(community.getId(),start,rows);
		}
		dataTable.setAaData(boards);
		dataTable.setiTotalRecords(count);
		dataTable.setiTotalDisplayRecords(count);
		dataTable.setsEcho(String.valueOf(sEcho));
		return JsonUtil.toJson(dataTable);
	}
}

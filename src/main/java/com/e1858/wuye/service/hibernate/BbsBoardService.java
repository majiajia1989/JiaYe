package com.e1858.wuye.service.hibernate;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.entity.hibernate.BbsBoard;
import com.e1858.wuye.entity.hibernate.BbsTopic;
import com.e1858.wuye.entity.hibernate.SysUser;
import com.e1858.wuye.dao.hibernate.BbsBoardDao;
import com.e1858.wuye.dao.hibernate.CommunityDao;


@Service
@Transactional
public class BbsBoardService
{
	@Autowired
	private BbsBoardDao bbsBoardDao;
	@Autowired
	private CommunityService communityService;

	public void addBoard(long communityId, String name,String description,SysUser sysUser)
	{
		BbsBoard bbsBoard = new BbsBoard();
		bbsBoard.setSysCorp(sysUser.getCorp());
		bbsBoard.setCommunity(communityService.queryCommunityById(communityId));
		bbsBoard.setName(name);
		bbsBoard.setDescription(description);
		bbsBoard.setCreateTime(new Date());
		bbsBoard.setSysUser(sysUser);
		bbsBoardDao.save(bbsBoard);
	}
	
	public void save(BbsBoard bbsBoard)
	{
		bbsBoardDao.save(bbsBoard);
	}
	
	public void update(BbsBoard bbsBoard)
	{
		bbsBoardDao.update(bbsBoard);
	}

	public void addBoard(BbsBoard board)
	{
		bbsBoardDao.save(board);
	}


	public void removeBoard(long id,long user)
	{
		BbsBoard board = bbsBoardDao.getById(id);
		board.setCancelTime(new Date());
		board.setCanceler(user);
		bbsBoardDao.delete(board);
	}
	
	public void enableBoard(long id)
	{
		BbsBoard board = bbsBoardDao.getById(id);
		board.setDisableTime(null);
		board.setDisabler(null);
		bbsBoardDao.update(board);
	}
	
	public void disableBoard(long id,long user)
	{
		BbsBoard board = bbsBoardDao.getById(id);
		board.setDisableTime(new Date());
		board.setDisabler(user);
		bbsBoardDao.update(board);
	}

	@Transactional(readOnly = true)
	public BbsBoard getById(long id)
	{
		return bbsBoardDao.getById(id);
	}

	@Transactional(readOnly = true)
	public List<BbsBoard> getAllBoardByCommunity(long communityId)
	{
		return bbsBoardDao.getAllBoardByCommunity(communityId);
	}


	public List<BbsBoard> getAllBoardByCommunity(long communityId, int startRow, int rows)
	{
		return bbsBoardDao.getAllBoardByCommunity(communityId, startRow, rows);
	}

	public List<BbsBoard> getEnableBoardByCommunity(long communityId)
	{
		return bbsBoardDao.getEnableBoardByCommunity(communityId);
	}

	public List<BbsBoard> getEnableBoardByCommunity(long communityId, int startRow, int rows)
	{
		return bbsBoardDao.getEnableBoardByCommunity(communityId, startRow, rows);
	}

	public List<BbsBoard> getDisableBoardByCommunity(long communityId)
	{
		return bbsBoardDao.getDisableBoardByCommunity(communityId);
	}

	public List<BbsBoard> getDisableBoardByCommunity(long communityId, int startRow, int rows)
	{
		return bbsBoardDao.getDisableBoardByCommunity(communityId, startRow, rows);
	}
	
	public long getAllCountByCommunity(long communityId)
	{
		return bbsBoardDao.getAllCountByCommunity(communityId);
	}
	
	public long getEnableCountByCommunity(long communityId)
	{
		return bbsBoardDao.getEnableCountByCommunity(communityId);
	}
	
	public long getDisableCountByCommunity(long communityId)
	{
		return bbsBoardDao.getDisableCountByCommunity(communityId);
	}
}

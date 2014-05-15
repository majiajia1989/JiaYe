package com.e1858.wuye.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e1858.wuye.dao.hibernate.DefaultMsgDao;
import com.e1858.wuye.dao.hibernate.MsgDao;
import com.e1858.wuye.dao.hibernate.MsgKeyDao;
import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.DefaultMsg;
import com.e1858.wuye.entity.hibernate.Msg;
import com.e1858.wuye.entity.hibernate.MsgKey;
import com.e1858.wuye.entity.hibernate.SysCorp;

@Service
@Transactional
public class MessageService
{

	@Autowired
	private MsgDao msgDao;

	@Autowired
	private MsgKeyDao msgKeyDao;
	
	@Autowired
	private DefaultMsgDao defaultMsgDao;
	

	@Transactional(readOnly=true)
	public List<Msg> getMsgs(Community community, String msgType)
	{
		return msgDao.getMsgByCommunityAndType(community, msgType);
	}
	
	@Transactional(readOnly=true)
	public List<Msg> getMsgs(Community community, String msgType, int offset, int count)
	{
		return msgDao.getMsgByCommunityAndType(community, msgType, offset, count);
	}
	
	@Transactional(readOnly=true)
	public List<Msg> getMsgsByCorp(SysCorp corp, String msgType,int startRow,int rows)
	{
		return msgDao.getMsgByCorpAndType(corp, msgType,startRow,rows);
	}	
	
//	@Transactional(readOnly=true)
//	@Deprecated
//	public List<Msg> getMsgs(String msgType)
//	{
//		return msgDao.getMsgByType(msgType);
//	}

	@Transactional(readOnly=true)
	public Msg getMsg(long id)
	{
		return msgDao.getMsgByID(id);
	}

	public void saveMsg(Msg msg)
	{
		msgDao.save(msg);
	}

	public void updateMsg(Msg msg)
	{
		msgDao.update(msg);
	}

	public void deleteMsg(Msg msg)
	{
		msgDao.delete(msg);
	}

	@Transactional(readOnly=true)
	public List<MsgKey> getMsgKeys(Msg msg)
	{
		return msgKeyDao.getMsgKeyByMsg(msg);
	}

	@Transactional(readOnly=true)
	public MsgKey getMsgKey(long id)
	{
		return msgKeyDao.getMsgKey(id);
	}

	public void saveMsgKey(MsgKey msgKey)
	{
		msgKeyDao.save(msgKey);
	}

	public void updateMsgKey(MsgKey msgKey)
	{
		msgKeyDao.update(msgKey);
	}

	public void deleteMsgKey(MsgKey msgKey)
	{
		msgKeyDao.delete(msgKey);
	}

	public void deleteMsgKeyByMsg(Msg msg)
	{
		msgKeyDao.deleteByMsg(msg);
	}
	public void deleteDefaultMsg(DefaultMsg defaultMsg){
		defaultMsgDao.delete(defaultMsg);
	}
	public void saveDefaultMsg(DefaultMsg defaultMsg){
		defaultMsgDao.save(defaultMsg);
	}
	public void updateDefaultMsg(DefaultMsg defaultMsg){
		defaultMsgDao.update(defaultMsg);
	}
	
	@Transactional(readOnly=true)
	public List<DefaultMsg> queryDefaultMsg(SysCorp corp,Community community,byte type){
		return defaultMsgDao.getDefaultMsgByCorpandCommunity(corp, community,type);
	}
	
	@Transactional(readOnly=true)
	public List<DefaultMsg> getDefaultMsgs(Msg msg){
		return defaultMsgDao.getDefaultMsgs(msg);
	}
	
	@Transactional(readOnly=true)
	public DefaultMsg getDefaultMsgByType(Msg msg, byte type){
		return defaultMsgDao.getDefaultMsgByType(msg, type);
	}

	@Transactional(readOnly=true)
	public List<Msg> queryMsgByIDs(List<Long> childIDs) {
		return msgDao.findByIDs(childIDs.toArray());
	}

	public long getMsgsCount(Community community, String msgType) {
		return msgDao.getMsgCountByCommunityAndType(community, msgType);
	}
	
	public long getMsgsCountByCorp(SysCorp corp, String msgType) {
		return msgDao.getMsgCountByCorpAndType(corp, msgType);
	}
}

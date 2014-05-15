package com.e1858.wuye.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.Community;
import com.e1858.wuye.entity.hibernate.ConsumeInfo;
import com.e1858.wuye.entity.hibernate.House;
import com.e1858.wuye.entity.hibernate.HouseRoom;
import com.e1858.wuye.entity.hibernate.SysConsumeType;
import com.e1858.wuye.entity.hibernate.SysCorp;
import com.e1858.wuye.pojo.DataTableRowCnt;
import com.e1858.wuye.pojo.ProcResult;

@Repository("consumeInfoDao")
public class ConsumeInfoDao extends BaseDao<ConsumeInfo>
{
	private final String GET_CONSUMEINFOS = " select u from ConsumeInfo u ,SubscriberHouse p where u.community = p.community and u.houseRoom=p.houseRoom and p.openid = ? and u.consumeType = ? ";
	private final String QUERY_CONSUMEINFO_BY_YEAR_MONTH_COMMUNITY_HOUSEROOM = " select u from ConsumeInfo u where u.consumeType=? and u.year=? and u.month=? and u.community = ? and u.house=? and u.houseRoom=?";

	public ConsumeInfo getConsumeInfos(String openid, long consumeTypeID)
	{
		return get(GET_CONSUMEINFOS, openid, consumeTypeID);
	}

	public ConsumeInfo queryConsumeInfo(SysConsumeType sysConsumeType, int year, int month, Community community, House house, HouseRoom houseRoom)
	{
		return get(QUERY_CONSUMEINFO_BY_YEAR_MONTH_COMMUNITY_HOUSEROOM, sysConsumeType, year, month, community, house, houseRoom);
	}

	public int addConsumeInfoFromList(List<ConsumeInfo> consumeInfos)
	{
		StringBuilder sql = new StringBuilder("");
		if (consumeInfos.size() <= 0)
		{
			return 0;
		}
		int i = 1;
		for (ConsumeInfo consumeInfo : consumeInfos)
		{
			sql.append("(");
			sql.append(consumeInfo.getCorp().getId()).append(",");
			sql.append(consumeInfo.getCommunity().getId()).append(",");
			sql.append(consumeInfo.getHouse().getId()).append(",");
			sql.append(consumeInfo.getHouseRoom().getId()).append(",");
			sql.append(consumeInfo.getConsumeType().getId()).append(",");
			sql.append("'").append(consumeInfo.getPayNumber()).append("'").append(",");
			sql.append(consumeInfo.getYear()).append(",");
			sql.append(consumeInfo.getMonth()).append(",");
			sql.append(consumeInfo.getPostAmount()).append(",");
			sql.append(consumeInfo.getCurrentAmount()).append(",");
			sql.append(consumeInfo.getAmount()).append(",");
			sql.append(consumeInfo.getPrice()).append(",");
			sql.append(consumeInfo.getPayAmount()).append(",");
			sql.append("sysdate()").append(",");
			sql.append("'").append(consumeInfo.getRemark()).append("'").append(",");
			sql.append(consumeInfo.getCreator()).append(",");
			sql.append("sysdate()");

			sql.append(")").append(i < consumeInfos.size() ? "," : "");
			i++;
		}
		return createSqlQuery("insert into t_ConsumeInfo(corp,community,house,houseRoom,consumeType,payNumber,year,month,postAmount,currentAmount,amount,price,payAmount,consumeTime,remark,creator,createTime) values " + sql.toString() + ";").executeUpdate();
	}

	public int deleteConsumeInfoByIds(String consumeInfoIds)
	{
		return createSqlQuery("delete from t_ConsumeInfo where id in(" + consumeInfoIds + ");").executeUpdate();
	}
	
	public ProcResult sendConsumeInfo(String title,long sendConsumeTemplate,long sysConsumeTypeId,int year, int month, long communityId,long houseId, long houseRoomId,String payNumber,long sender)
	{
		ProcResult result = new ProcResult();
		
		String sql = "{CALL p_SendConsumeInfo(?,?,?,?,?,?,?,?,?,?)}";
		ArrayList<Object> params = new ArrayList<Object>();
		// in map varchar(1000),in creator long)
		params.add(title);
		params.add(sendConsumeTemplate);
		params.add(sysConsumeTypeId);
		params.add(year);
		params.add(month);
		params.add(communityId);
		params.add(houseId);
		params.add(houseRoomId);
		params.add(payNumber);
		params.add(sender);
		
		for (Map map : (List<Map>) createSqlQuery(sql, params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list())
		{
			result.setProcStatus((Boolean)map.get("procStatus"));
			result.setProcText(map.get("procText").toString());
			result.setProcReserve(map.get("procReserve").toString());
		}
		return result;
	}
	
	public List<ConsumeInfo> queryConsumeInfos(SysConsumeType sysConsumeType, int year, int month, Community community, House house, HouseRoom houseRoom, String payNumber,int startRow,int pageSize,DataTableRowCnt rowCnt )
	{
		List<Object> param = new ArrayList<Object>();
		String sql="select u from ConsumeInfo u where 1=1 ";
		String sqlCount=" select count(*) from ConsumeInfo u where 1=1 ";
		String sqlWhere = "";
		if (community != null)
		{
			sqlWhere = sqlWhere + " and u.community=? ";
			param.add(community);
		}
		if (sysConsumeType != null)
		{
			sqlWhere = sqlWhere + " and u.consumeType=? ";
			param.add(sysConsumeType);
		}
		sqlWhere = sqlWhere + " and u.year=? ";
		param.add(year);
		sqlWhere = sqlWhere + " and u.month=? ";
		param.add(month);

		if (house != null)
		{
			sqlWhere = sqlWhere + " and u.house=? ";
			param.add(house);
		}

		if (houseRoom != null)
		{
			sqlWhere = sqlWhere + " and u.houseRoom=? ";
			param.add(houseRoom);
		}

		if (payNumber != null)
		{
			sqlWhere = sqlWhere + " and u.payNumber=? ";
			param.add(payNumber);
		}
		rowCnt.setRowCnt(count(sqlCount+sqlWhere,param));
		return findWithPage(sql+sqlWhere,startRow,pageSize,param);
	}

}

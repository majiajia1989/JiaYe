package com.e1858.wuye.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.e1858.wuye.entity.hibernate.NoticeReceiver;
import com.e1858.wuye.entity.hibernate.SysUser;

@Repository("noticeReceiverDao")
public class NoticeReceiverDao extends BaseDao<NoticeReceiver> {
	
//	use wuye;
//	drop procedure p_sendNotice;
//	DELIMITER $$
//
//	CREATE DEFINER=`wuye`@`%` PROCEDURE `p_sendNotice`(in house1 varchar(500),in houseUnit1 varchar(500), in houseFloor1 varchar(500),in car int,in notice1 bigint,in sender1 bigint)
//	BEGIN
//	-- car 0 没车 1 有车 2 不限
//	if car=0 then
//	set @carSql=concat(' a.openid not in (select sc.openid from t_SubscriberCar sc) ');
//	end if;
//	if car=1 then
//	set @carSql=concat(' a.openid in (select sc.openid from t_SubscriberCar sc) ');
//	end if;
//	if car=2 then
//	set @carSql='';
//	end if;
//
//	set @houseSql = '';
//	if (house1!='')&&(house1 is not null) then
//	set @houseSql = concat(@houseSql, 'a.house in(');
//	set @houseSql = concat(@houseSql, house1);
//	set @houseSql = concat(@houseSql, ') ');
//	end if;
//
//	if (houseUnit1!='')&&(houseUnit1 is not null) then
//	if (@houseSql='') then
//	set @houseSql = concat(@houseSql, 'a.houseUnit in(');
//	else
//	set @houseSql = concat(@houseSql, 'and a.houseUnit in(');
//	end if;
//	set @houseSql = concat(@houseSql, houseUnit1);
//	set @houseSql = concat(@houseSql, ') ');
//	end if;
//
//	if (houseFloor1!='')&&(houseFloor1 is not null) then
//	if (@houseSql='') then
//	set @houseSql = concat(@houseSql, 'a.houseFloor in(');
//	else
//	set @houseSql = concat(@houseSql, 'and a.houseFloor in(');
//	end if;
//	set @houseSql = concat(@houseSql, houseFloor1);
//	set @houseSql = concat(@houseSql, ') ');
//	end if;
//
//
//	if (@carSql!='') then
//	if (@houseSql='') then
//	set @houseSql = concat(@houseSql, @carSql);
//	else
//	set @houseSql = concat(@houseSql, ' and ');
//	set @houseSql = concat(@houseSql, @carSql);
//	end if;
//	end if;
//
//
//	if (@houseSql!='') then
//	set @houseSql = concat('where ', @houseSql);
//	end if;
//
//	set @hql = 'insert into t_NoticeReceiver(notice, house, houseRoom, openid, sendTime, sender) select ';
//	set @hql = concat(@hql, notice1);
//	set @hql = concat(@hql, ', a.house, a.houseRoom, a.openid, now(), ');
//	set @hql = concat(@hql, sender1);
//	set @hql = concat(@hql, ' from t_SubscriberHouse a ');
//	set @hql = concat(@hql, @houseSql);
//
//	prepare SQLStr FROM @hql;
//	execute SQLStr;
//	deallocate prepare SQLStr; 
//
//	-- 修改notice状态
//	UPDATE t_Notice SET sender=sender1,sendTime=now() WHERE id=notice1;
//
//	END
	public void sendNoticeWithQuery(SysUser creator, long noticeID, String houses, String floors, String units,
			int hasCar) {
		String sql = "{CALL p_sendNotice(?,?,?,?,?,?)}";
		createSqlQuery(sql, houses, units, floors, hasCar, noticeID, creator.getId()).executeUpdate();
	}
}

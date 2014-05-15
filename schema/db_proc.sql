
DELIMITER $$
CREATE PROCEDURE `p_RegistUser`(in userName varchar(50),in pass varchar(32),in email varchar(100),in address varchar(100),in qq varchar(20),in corpType long,in province varchar(100),in city varchar(100),in county varchar(100),in corpName varchar(100),in mobilePhone varchar(11),in telPhone varchar(12),in descript varchar(200),in copyright varchar(100),in map varchar(1000),in creator long)
label_main:BEGIN  
	declare procStatus bool default false;
	declare procText varchar(200) default '';
	declare procReserve varchar(200) default '';

	declare newCorpId long default -1;
	declare newRoleId long default -1;
	declare newUserId long default -1;

	declare exit handler for sqlexception BEGIN ROLLBACK;set procText='数据库操作错误！';set procStatus=false; Select procStatus,procText,procReserve ; end; 

	if(userName='' || userName=null) then
		set procText='用户登录名不能为空！';
		Select procStatus,procText,procReserve;
		leave label_main;
	end if;
	if(corpName='' || corpName=null) then
		set procText='企业名称不能为空！';
		Select procStatus,procText,procReserve;
		leave label_main;
	end if;

	if (not exists (select 1 from t_SysUser where corp=1 and id=creator)) then
		set procText='创建者不是系统用户，没有权限！';
		Select procStatus,procText,procReserve;
		leave label_main;
	end if;


	if(exists(select 1 from t_SysCorp where name=corpName)) then
		set procText='单位名称已经存在！';
		Select procStatus,procText,procReserve;
		leave label_main;
	end if;

	if(exists(select 1 from t_SysUser where name=userName)) then
		set procText='用户名已经存在！';
		Select procStatus,procText,procReserve;
		leave label_main;
	end if;

	START TRANSACTION ;
	-- 创建单位
	INSERT INTO t_SysCorp(type,province,city,county,name,mobilePhone,telePhone,descript,address,copyright,map,creator,createTime)
		VALUES(corpType,province,city,county,corpName,mobilePhone,telePhone,descript,address,copyright,map,creator,sysdate());

	set newCorpId = LAST_INSERT_ID();
	-- 创建管理组
	INSERT INTO t_SysRole(corp,name,creator,createTime)
		VALUES(newCorpId,'系统管理组',creator,sysdate());
	set newRoleId = LAST_INSERT_ID();
	-- 初始化权限
	INSERT INTO t_SysRoleResource(role,resource,creator,createTime)
		SELECT newRoleId,u.resource,creator,sysdate()
		FROM t_CorpResourceTemplate u
		WHERE u.corpType=corpType;
	-- 插入用户
	INSERT INTO t_SysUser(corp,name,password,email,address,qq,active,locked,creator,createTime,mobilePhone)
		VALUES(newCorpId,userName,pass,email,address,qq,1,0,creator,sysdate(),mobilePhone);
	set newUserId=LAST_INSERT_ID();
	
	-- 插入管理组
	INSERT INTO t_SysRoleUser(role,user,creator,createTime)
		VALUES(newRoleId,newUserId,creator,sysdate());
	set procStatus=true;
	set procText='创建成功';
	Select procStatus,procText,procReserve;
	commit;	
END
$$
DELIMITER ;

delimiter //
drop procedure if exists p_subscribe// 
create procedure p_subscribe(in openid1 varchar(32),in ticket1 varchar(200))
begin
declare v_corp bigint;
declare v_community bigint;

select id,corp into v_community,v_corp from t_Community where ticket = ticket1;
start transaction;
if exists(select 1 from t_Subscriber where openid = openid1) then
  update t_Subscriber set enrollTime=now(),cancelTime = null where openid = openid1;
else
  insert into t_Subscriber(openid,corp,community,enrollTime) values(openid1,v_corp,v_community,now());
end if;
commit;
end;
//
delimiter ;

delimiter //
drop procedure if exists p_unsubscribe// 
create procedure p_unsubscribe(in openid1 varchar(32))
begin
  start transaction;
  update t_Subscriber set cancelTime = now() where openid = openid1;
  /*
   * delete from t_Weixin_UserInfo where openid = openid1;
   */
  commit;
end;
//
delimiter ;

delimiter //
drop procedure if exists p_getDefaultMsg //
create procedure p_getDefaultMsg(in openid1 varchar(32))
begin
 select a.* from t_Msg a,t_DefaultMsg b,t_Subscriber c 
 where a.id=b.msg
 and a.corp = c.corp
 and a.community = c.community
 and c.openid=openid1
 and b.type=1;  	
end;
//
delimiter ;



delimiter //
drop procedure if exists p_getResponseMsg //
create procedure p_getResponseMsg(in openid1 varchar(32),in command1 varchar(1000))
begin
 label_p:begin
   if exists(select 1 from t_Msg a,t_MsgKey b,t_Subscriber c where a.id=b.msg and a.corp=c.corp and a.community=c.community and b.matching=0 and b.command=command1 and c.openid = openid1) then
     select a.* from t_Msg a,t_MsgKey b,t_Subscriber c where a.id=b.msg and a.corp=c.corp and a.community=c.community and b.matching=0 and b.command=command1 and c.openid = openid1;
     leave label_p;
   end if;
   if exists(select 1 from t_Msg a,t_MsgKey b,t_Subscriber c where a.id=b.msg and a.corp=c.corp and a.community=c.community and b.matching=1 and b.command like concat('%',command1,'%') and c.openid = openid1) then
     select a.* from t_Msg a,t_MsgKey b,t_Subscriber c where a.id=b.msg and a.corp=c.corp and a.community=c.community and b.matching=1 and b.command like concat('%',command1,'%') and c.openid = openid1;
     leave label_p;
   end if;
   if exists(select 1 from t_Msg a,t_DefaultMsg b,t_Subscriber c where a.id=b.msg and a.corp = c.corp and a.community = c.community and b.type=1 and c.openid=openid1) then
     select a.* from t_Msg a,t_DefaultMsg b,t_Subscriber c 
   	 where a.id=b.msg
     and a.corp = c.corp
     and a.community = c.community
     and b.type=1
     and c.openid=openid1;
     leave label_p;
   end if;
   select a.* from t_Msg a,t_DefaultMsg b where a.id=b.msg and a.corp = 1 and a.community = 1 and b.type=1;
 end;  	
end;
//
delimiter ; 







DELIMITER $$
CREATE PROCEDURE `p_SendConsumeInfo`(in title varchar(100),in sendConsumeTemplateId long,in consumeTypeId long,in year int,in month int,
in communityId long,in houseId long,
in houseRoomId long,in payNumber varchar(100),in presenter long)
label_main:BEGIN  
	declare procStatus bool default false;
	declare procText varchar(200) default '';
	declare procReserve varchar(200) default '';
	
	declare fromUserName varchar(100) default '';
	set fromUserName=(select openid from t_Weixin_GongHao where community=1);
	set @sendConsumeTemplate=(select content from t_ConsumeTemplate where id = sendConsumeTemplateId);
	if(isnull(@sendConsumeTemplate)) then
		set procText="发送模板错误！";
		Select procStatus,procText,procReserve;
	end if;
	set @sql:=concat('
	insert into t_MT(mo,msg,toUserName,fromUserName,msgType,title,description,picUrl,
				content,
				children,priority,sendTime,errCode,errMsg,presentTime,presenter,
				auditTime,auditor)
	select 0,0,b.openid,''',fromUserName,''',''text'',''',title,''','''','''',			
			replace(
			replace(
			replace(
			replace(
			replace(
			replace(
			replace(
			replace(
			replace(
			replace(
			replace(
			replace(
			replace(
			replace(
			replace(''',@sendConsumeTemplate,''',''${年份}'',a.year),
				''${月份}'',a.month),
				''${企业名称}'',c.name),
				''${小区名称}'',d.name),
				''${楼号}'',e.name),
				''${单元}'',f.name),
				''${楼层}'',g.name),
				''${门牌号}'',h.name),
				''${上期表数}'',a.postAmount),
				''${本期表数}'',a.currentAmount),
				''${本期用量}'',a.amount),
				''${户号}'',a.payNumber),
				''${应缴金额}'',a.payAmount),
				''${单价}'',a.price),
				''${抄表时间}'',a.consumeTime),
			'''',0,sysdate(),-1,'''',sysdate(),',presenter,',
			sysdate(),',presenter,'
	from t_ConsumeInfo as a inner join t_SubscriberHouse as b on a.corp=b.corp and a.community=b.community and a.house=b.house and a.houseRoom=b.houseRoom
							inner join t_SysCorp as c on b.corp = c.id
							inner join t_Community as d on b.community = d.id
							inner join t_House as e on b.house=e.id
							inner join t_HouseUnit as f on b.houseUnit=f.id
							inner join t_HouseFloor as g on b.houseFloor=g.id
							inner join t_HouseRoom as h on b.houseRoom=h.id
		');
	set @where=concat('where a.consumeType=',consumeTypeId ,' and a.community=',communityId,' and year=',year,' and month=',month);
	if (houseId>0) then
		set @where=concat(@where,' and b.house=',houseId);
	end if;
	if (houseRoomId>0) then
		set @where=concat(@where,' and b.houseRoom=',houseRoomId);
	end if;
	if (!(ISNULL(payNumber) || LENGTH(trim(payNumber))<1 )) then
		set @where=concat(@where,' and a.payNumber=',trim(payNumber));
	end if;
	set @sql:=concat(@sql,@where);
	prepare SQLStr FROM @sql;
	execute SQLStr;
	deallocate prepare SQLStr; 

	Select procStatus,procText,procReserve;
	commit;	
END$$
DELIMITER ;

DELIMITER $$
CREATE  PROCEDURE `p_unbundSubscriber`(IN openid1 varchar(32))
BEGIN
SET SQL_SAFE_UPDATES = 0;
start transaction;
delete from t_SubscriberPhone where openid=openid1;
delete from t_SubscriberCar where openid=openid1;
delete from t_SubscriberHouse where openid=openid1;
update t_Subscriber set cancelTime = now() where openid = openid1;
commit;
END
$$
DELIMITER ;


delimiter //
drop procedure if exists p_subscriberHouse //
create procedure p_subscriberHouse(in openid1 varchar(32),in house1 bigint, in houseUnit1 bigint, in houseFloor1 bigint, in houseRoom1 bigint)
begin
	declare corp1 bigint;
	declare community1 bigint;
	declare alias1 varchar(1000);
	select corp,community into corp1,community1 from t_Subscriber where openid=openid1;
	start transaction; 
	if exists(select 1 from t_SubscriberHouse where openid = openid1) then
		update t_SubscriberHouse set corp=corp1,community=community1,house=house1,houseUnit=houseUnit1,houseFloor=houseFloor1,houseRoom=houseRoom1 where openid=openid1;
	else
		insert into  t_SubscriberHouse(openid, corp, community, house, houseUnit, houseFloor, houseRoom, createTime) values (openid1, corp1, community1, house1, houseUnit1, houseFloor1, houseRoom1, now()); 
	end if;
	select concat('',name) into alias1 from t_House where id=house1;
	select concat(alias1,name) into alias1 from t_HouseUnit where id=houseUnit1;
	select concat(alias1,name) into alias1 from t_HouseFloor where id=houseFloor1;
	select concat(alias1,name) into alias1 from t_HouseRoom where id=houseRoom1;
	update t_Subscriber set alias =alias1 where openid=openid1;
	commit; 	
end;
//
delimiter ;



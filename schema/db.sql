use wuye;


/*
alter table t_SysResource drop foreign key fk_t_SysResource_parent;
drop table if exists t_SysResource;
drop table if exists t_SysLoginLog;
drop table if exists t_Weixin;
drop table if exists t_Subscriber;
drop table if exists t_SysRoleUser;
drop table if exists t_SysRoleResource;
drop table if exists t_SysUser;
drop table if exists t_SysRole;
drop table if exists t_Menu;
drop table if exists t_MenuType;
drop table if exists t_Msg;
drop table if exists t_MsgType;
drop table if exists t_MsgKey;
drop table if exists t_ServicePhone;
drop table if exists t_HouseFloor;
drop table if exists t_HouseUnit;
drop table if exists t_HouseRoom;
drop table if exists t_House;
drop table if exists t_CommunENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='微信公共账号表';ity;
drop table if exists t_SysCorpType;
drop table if exists t_SysArea;
*/
/*
*行政区域表';
*/
CREATE TABLE t_SysArea
(
	code char(6) not null COMMENT '区域代码',
	parent char(6) not null COMMENT '父区域',
	name varchar(100) not null  COMMENT '区域名称',
	reserve varchar(250),
	remark varchar(250),
	CONSTRAINT pk_t_SysArea_code primary key(code),
	CONSTRAINT fk_t_SysArea_parent FOREIGN KEY (parent) REFERENCES t_SysArea(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='行政区域表';

/*
 * 系统支付接口
 */
CREATE TABLE t_SysPayInterface
(
	id bigint NOT NULL AUTO_INCREMENT,
	name varchar(100) NOT NULL COMMENT '名称',
	logo varchar(100) not NULL COMMENT 'logo',
	uri varchar(100) not NULL COMMENT '交易uri',
	successFlag varchar(100)not  NULL COMMENT '成功标记',
	mobilePhone varchar(11) NOT NULL default '' COMMENT '手机号码',
	telePhone varchar(12) NOT NULL default '' COMMENT '电话号码',
	descript varchar(200) NOT NULL default '' COMMENT '介绍',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_SysPayInterface_id PRIMARY KEY(id),
	constraint uk_t_SysPayInterface_name unique(name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='系统交易接口';

/*
*公司类型表';
*/
CREATE TABLE t_SysCorpType
(
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(100) not null COMMENT '名称',
  constraint pk_t_SysCorpType_id PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='公司类型表';

/*
*消费类型表';
*/
CREATE TABLE t_SysConsumeType
(
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(100) not null COMMENT '名称',
  icon varchar(100) not null default '' COMMENT 'icon',
  logo varchar(100) not null default '' COMMENT 'logo',
  constraint pk_t_SysConsumeType_id PRIMARY KEY(id),
  constraint uk_t_SysConsumeType UNIQUE KEY(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='消费类型表';


/*
*公司表;
*/
CREATE TABLE t_SysCorp (
	id bigint NOT NULL AUTO_INCREMENT,
	type bigint NOT NULL COMMENT '公司类型',
	province varchar(50) NOT NULL COMMENT '省',
	city varchar(50) NOT NULL COMMENT '市（地区）',
	county varchar(50) NOT NULL COMMENT '县',
	name varchar(100) NOT NULL default '' COMMENT '公司名称',
	mobilePhone varchar(11) NOT NULL default '' COMMENT '手机号码',
	telePhone varchar(12) NOT NULL default '' COMMENT '电话号码',
	descript varchar(200) NOT NULL default '' COMMENT '介绍',
	address varchar(100) NOT NULL default '' COMMENT '地址',
	copyright varchar(100) NOT NULL default '' COMMENT '版权信息',
	map varchar(1000) NOT NULL default '' COMMENT '地图',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_SysCorp_id PRIMARY KEY(id),
	constraint fk_t_SysCorp_type FOREIGN KEY (type) REFERENCES t_SysCorpType(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='公司表';

/*
*操作员表;
*/
CREATE TABLE t_SysUser
(
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint NOT NULL COMMENT '所属公司',
	name varchar(50) NOT NULL COMMENT '用户名',
	password varchar(32) NOT NULL COMMENT '用户登录密码',
	email varchar(100) NULL COMMENT '用户邮箱',
	address varchar(100) NULL COMMENT '地址',
	mobilePhone varchar(11) NULL COMMENT '手机号码',
	qq varchar(20) NULL COMMENT 'qq',
	active tinyint NOT NULL default 0 COMMENT '0:未激活 1:激活',
	locked tinyint NOT NULL default 0 COMMENT '0:未锁定 1:锁定',
	begin datetime default NULL COMMENT '开始时间',
	end datetime default NULL COMMENT '到期时间',
	last_visit datetime default NULL COMMENT '最后登陆时间',
	last_ip varchar(32) default NULL COMMENT '最后登陆IP',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_SysUser_id PRIMARY KEY(id),
	constraint fk_t_SysUser_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_SysUser_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='操作员表';


/*
 * 系统角色表
 */

CREATE TABLE t_SysRole
(
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint NOT NULL COMMENT '所属公司',
	name varchar(50) NOT NULL COMMENT '系统角色名称',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_SysRole_id PRIMARY KEY(id),
	constraint uk_t_SysRole_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint uk_t_SysRole_name unique(corp,name),
	constraint fk_t_SysRole_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='系统角色';

/*
 * 系统资源表
 */
CREATE TABLE t_SysResource
(
	id bigint NOT NULL AUTO_INCREMENT,
	parent bigint NOT NULL COMMENT '父',
	name varchar(50) NOT NULL COMMENT '系统资源名称',
	url varchar(100) NOT NULL default '',
	icon varchar(50)  COMMENT '资源图标',
	image varchar(50)  COMMENT '资源图片',
	direction tinyint NOT NULL default 0 COMMENT '0:垂直 1:水平',
	sort tinyint NOT NULL default 0 COMMENT '排序顺序',
	parameter varchar(200) COMMENT '参数',
	remark varchar(200) COMMENT '备注',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_SysResource_id PRIMARY KEY(id),
	constraint fk_t_SysResource_parent FOREIGN KEY (parent) REFERENCES t_SysResource(id),
	constraint fk_t_SysResource_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='系统资源';

/*
 * 系统角色用户表
 */
CREATE TABLE t_SysRoleUser
(
	id bigint NOT NULL AUTO_INCREMENT,
	role bigint NOT NULL COMMENT '角色id',
	user bigint NOT NULL COMMENT '用户id',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_SysRoleUser_id PRIMARY KEY(id),
	constraint fk_t_SysRoleUser_role FOREIGN KEY (role) REFERENCES t_SysRole(id),
	constraint fk_t_SysRoleUser_user FOREIGN KEY (user) REFERENCES t_SysUser(id),
	constraint fk_t_SysRoleUser_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id),
	constraint uk_t_SysRoleUser unique(role,user)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='系统角色用户';

/*
 * 系统角色资源表
 */
CREATE TABLE t_SysRoleResource
(
	id bigint NOT NULL AUTO_INCREMENT,
	role bigint NOT NULL COMMENT '角色id',
	resource bigint NOT NULL COMMENT '资源id',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_SysRoleResource_id PRIMARY KEY(id),
	constraint fk_t_SysRoleResource_role FOREIGN KEY (role) REFERENCES t_SysRole(id),
	constraint fk_t_SysRoleResource_resource FOREIGN KEY (resource) REFERENCES t_SysResource(id),
	constraint fk_t_SysRoleResource_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id),
	constraint uk_t_SysRoleResource unique(role,resource)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='系统角色资源';

/*
 * 公司资源模板
 */
CREATE TABLE t_CorpResourceTemplate
(
	id bigint NOT NULL AUTO_INCREMENT,
	corpType bigint NOT NULL COMMENT '公司类型',
	resource bigint NOT NULL COMMENT '资源id',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_SysResourceTemplate_id PRIMARY KEY(id),
	constraint fk_t_SysResourceTemplate_corpType FOREIGN KEY (corpType) REFERENCES t_SysCorpType(id),
	constraint fk_t_SysResourceTemplate_resource FOREIGN KEY (resource) REFERENCES t_SysResource(id),
	constraint fk_t_SysResourceTemplate_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id),
	constraint uk_t_SysResourceTemplate unique(corpType,resource)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='公司资源模板';


/*
 * 系统用户登录日志表
 */
CREATE TABLE t_SysLoginLog (
	id bigint NOT NULL AUTO_INCREMENT,
	user bigint not NULL COMMENT '用户id',
	ip varchar(32) NOT NULL default '' COMMENT '登录ip',
	login_datetime datetime NOT NULL COMMENT '登录时间',
	constraint pk_t_SysLoginLog_id PRIMARY KEY  (id),
	constraint fk_t_SysLoginLog_user_id FOREIGN KEY (user) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='系统用户登录日志';

/*
 * 公司图片
 */
CREATE TABLE t_CorpImage (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint NOT NULL COMMENT '公司',
	title varchar(50) NOT NULL default '' COMMENT '图片标题',
	descript varchar(200) NOT NULL default '' COMMENT '介绍',
	imageUrl varchar(200) NOT NULL COMMENT '图片url',
	url varchar(200) NOT NULL COMMENT '点击时url',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_CorpImage_id PRIMARY KEY(id),
	constraint fk_t_CorpImage_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_CorpImage_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='公司图片';

/*
 * 公司欢迎图片
 */
CREATE TABLE t_CorpWelcomeImage (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint NOT NULL COMMENT '公司',
	image bigint NOT NULL  COMMENT '图片ID',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_CorpWellcomeImage_id PRIMARY KEY(id),
	constraint fk_t_CorpWellcomeImage_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_CorpWellcomeImage_image FOREIGN KEY (image) REFERENCES t_CorpImage(id) ON DELETE CASCADE,
	constraint fk_t_CorpWellcomeImage_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='公司欢迎图片';

/*
*小区表;
*/
CREATE TABLE t_Community (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint NOT NULL COMMENT '公司类型',
	province varchar(50) NOT NULL COMMENT '省',
	city varchar(50) NOT NULL COMMENT '市（地区）',
	county varchar(50) NOT NULL COMMENT '县',
	name varchar(100) NOT NULL default '' COMMENT '公司名称',
	mobilePhone varchar(11) NOT NULL default '' COMMENT '手机号码',
	telePhone varchar(12) NOT NULL default '' COMMENT '电话号码',
	descript varchar(200) NOT NULL default '' COMMENT '介绍',
	address varchar(100) NOT NULL default '' COMMENT '小区地址',
	copyright varchar(100) NOT NULL default '' COMMENT '版权信息',
	map varchar(1000) NOT NULL default '' COMMENT '地图',
	ticket varchar(200) default '' COMMENT '二维码ticket',
	qrcode varchar(200) default '' COMMENT '二维码url',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_Community_id PRIMARY KEY(id),
	constraint fk_t_Community_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_Community_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='小区表';

/*
 * 小区图片
 */
CREATE TABLE t_CommunityImage (
	id bigint NOT NULL AUTO_INCREMENT,
	community bigint NOT NULL COMMENT '小区',
	title varchar(50) NOT NULL default '' COMMENT '图片标题',
	descript varchar(200) NOT NULL default '' COMMENT '介绍',
	imageUrl varchar(200) NOT NULL COMMENT '图片url',
	url varchar(200) NOT NULL COMMENT '点击时url',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_CommunityImage_id PRIMARY KEY(id),
	constraint fk_t_CommunityImage_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_CommunityImage_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='公司图片';


/*
 * 小区欢迎图片
 */
CREATE TABLE t_CommunityWelcomeImage (
	id bigint NOT NULL AUTO_INCREMENT,
	community bigint NOT NULL COMMENT '小区',
	image bigint NOT NULL  COMMENT '图片ID',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_t_CommunityWellcomeImage_id PRIMARY KEY(id),
	constraint fk_t_CommunityWellcomeImagee_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_CommunityWellcomeImagee_image FOREIGN KEY (image) REFERENCES t_CommunityImage(id),
	constraint fk_t_CommunityWellcomeImage_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='小区欢迎图片';

/*
 * 微信公共账号表
 */
CREATE TABLE t_Weixin_GongHao (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	url varchar(200) default '' COMMENT 'URL',
	account varchar(100) NOT NULL default '' COMMENT '账号',
	password varchar(100) NOT NULL default '' COMMENT '密码',
	copyright varchar(50) NOT NULL default '' COMMENT '版权信息',
	token varchar(100) NOT NULL default '' COMMENT '令牌',
	appId varchar(200) NOT NULL default '' COMMENT '微信用户唯一凭证',
	appSecret varchar(50) NOT NULL default '' COMMENT '微信唯一凭证密钥',
	openid varchar(32) not null default ''  COMMENT '公号的唯一标识',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_Weixin_GongHao_id PRIMARY KEY(id),
	constraint fk_t_Weixin_GongHao_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_Weixin_GongHao_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_Weixin_GongHao_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='微信公共账号表';

/*
 * 微信用户信息表
 */
CREATE TABLE t_Weixin_UserInfo(
	subscribe tinyint not null default 0 COMMENT '是否订阅,0未订，1已订',
	openid varchar(32) not null COMMENT '用户的唯一标识',
	nickname varchar(50) not null COMMENT '用户昵称',
	sex tinyint not null COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
	province varchar(50) not null COMMENT '用户个人资料填写的省份',
	city varchar(20) not null COMMENT '普通用户个人资料填写的城市',
	country varchar(20) not null COMMENT '国家，如中国为CN',
	headimgurl varchar(200) not null COMMENT '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空',
	privilege varchar(200) null COMMENT '用户特权信息，json 数组，如微信沃卡用户为（chinaunicom)',
	subscribe_time datetime null COMMENT '用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间',
	constraint pk_t_Weixin_UserInfo_openid PRIMARY KEY(openid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='微信用户信息';

/*
 * 微信Access_token
 */
CREATE TABLE t_Weixin_Access_Token(
	corp bigint not null,
	access_token varchar(200) not null,
	access_time datetime not null,
	expires_in bigint not null,
	createTime bigint not null,
	expires bigint not null,
	constraint pk_Weixin_Access_Token PRIMARY KEY(corp),
	constraint fk_Weixin_Access_Token_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='微信Access_token';

/*
 * 已订制微信号表
 */
CREATE TABLE t_Subscriber(
	openid varchar(32) not NULL COMMENT '微信openid',
	nickname varchar(100) not NULL default '' COMMENT '微信昵称',
	headimgurl varchar(200) not NULL default '' COMMENT '微信头像',
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	name varchar(100) not NULL default '' COMMENT '姓名',
	alias varchar(100) not NULL default '' COMMENT '别名',
	gender varchar(2) not NULL default '男' COMMENT '性别',
	telePhone varchar(12) not NULL default '' COMMENT '电话',
	mobilePhone varchar(11) not NULL default '' COMMENT '手机',
	carNumber varchar(7) not NULL default '' COMMENT '车牌',
	integrate int not null default 0  COMMENT '积分',
	enrollTime datetime not NULL COMMENT '定制时间',
	cancelTime datetime NULL COMMENT '取消时间',
	constraint pk_t_Subscriber_openid PRIMARY KEY(openid),
	constraint fk_t_Subscriber_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_Subscriber_community FOREIGN KEY (community) REFERENCES t_Community(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='定制微信号表';


/*
 * 菜单类型表
 */
CREATE TABLE t_MenuType (
	type varchar(50) NOT NULL default '' COMMENT '消息类型',
	name varchar(50) NOT NULL default '' COMMENT '类型名称',
	constraint pk_t_MenuType_type PRIMARY KEY(type),
	constraint uk_t_MenuType_name unique(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单类型';

/*
 * 菜单
 */
CREATE TABLE t_Menu (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	parent bigint NOT NULL COMMENT '父',
	name varchar(50) NOT NULL default '' COMMENT '菜单显示名称',
	type varchar(50) NOT NULL COMMENT '菜单类型',
	parameter varchar(50) NOT NULL COMMENT '菜单参数',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_Menu_id PRIMARY KEY(id),
	constraint uk_t_Menu unique(corp,community,name),
	constraint fk_t_Menu_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_Menu_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_Menu_parent FOREIGN KEY (parent) REFERENCES t_Menu(id) ON DELETE CASCADE,
	constraint fk_t_Menu_type FOREIGN KEY (type) REFERENCES t_MenuType(type),
	constraint fk_t_Menu_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='菜单'; 

/*
 * 消息类型表
 */
CREATE TABLE t_MsgType (
	type varchar(50) NOT NULL default '' COMMENT '消息类型',
	name varchar(50) NOT NULL default '' COMMENT '类型名称',
	constraint pk_t_MsgType_type PRIMARY KEY(type),
	constraint uk_t_MsgType_name unique(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='消息类型';


/*
 * 消息
 */
CREATE TABLE t_Msg (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	msgType varchar(50) NOT NULL default '' COMMENT '消息类型',
	title varchar(1000) NOT NULL default '' COMMENT '标题',
	description varchar(1000) NOT NULL default '' COMMENT '图文消息描述',
	picUrl varchar(1000) NOT NULL default '' COMMENT '图片链接',
	url varchar(100) NULL COMMENT '点击图文消息跳转链接',
	content text NULL COMMENT '内容内容',
	children varchar(200) NULL COMMENT '子图文消息ID',
	sort tinyint NOT NULL default 0 COMMENT '排序，优先顺序从小到大',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_Msg_id PRIMARY KEY(id),
	constraint fk_t_Msg_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_Msg_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_Msg_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='消息'; 



/*
 * 子图文消息
 */
CREATE TABLE t_NewsChildren (
	parent bigint not NULL COMMENT '父消息',
	child bigint not NULL COMMENT '子消息'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='子图文消息';


/*
 * 消息关键字
 */
CREATE TABLE t_MsgKey (
	id bigint NOT NULL AUTO_INCREMENT,
	msg bigint not NULL COMMENT '消息',
	command varchar(50) NOT NULL default '' COMMENT '命令（关键字）',
	matching tinyint NOT NULL default 0 COMMENT '0:完全匹配 1:包含匹配',
	constraint pk_t_MsgKey_id PRIMARY KEY(id),
	constraint uk_t_MsgKey unique(msg,command),
	constraint fk_t_MsgKey_msg FOREIGN KEY (msg) REFERENCES t_Msg(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='消息关键字';


/*
 * 缺省消息
 */
CREATE TABLE t_DefaultMsg (
	id bigint NOT NULL AUTO_INCREMENT,
	type tinyint NOT NULL default 0 COMMENT '0:关注时回复消息 1:用户上行无匹配时回复消息',
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	msg bigint not NULL COMMENT '消息',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_DefaultMsg_id PRIMARY KEY(id),
	constraint fk_t_DefaultMsg_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_DefaultMsg_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_DefaultMsg_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='缺省消息';

/*
 * 楼房信息
 */
CREATE TABLE t_House (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	name varchar(200) NOT NULL default '' COMMENT '名字',
	description varchar(1000) NOT NULL default '' COMMENT '描述',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_House_id PRIMARY KEY(id),
	constraint fk_t_House_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_House_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_House_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id),
	constraint uk_t_House unique (community,name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='楼房信息';

/*
 * 楼房单元信息
 */
CREATE TABLE t_HouseUnit (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	house bigint not NULL  default -1 COMMENT '楼号',
	name varchar(200) NOT NULL default '' COMMENT '名字',
	description varchar(1000) NOT NULL default '' COMMENT '描述',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_HouseUnit_id PRIMARY KEY(id),
	constraint fk_t_HouseUnit_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_HouseUnit_community FOREIGN KEY (community) REFERENCES t_Community(id),
	/*constraint fk_t_HouseUnit_house FOREIGN KEY (house) REFERENCES t_House(id),*/
	constraint fk_t_HouseUnit_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id),
	constraint uk_t_HouseUnit unique (community,name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='楼房单元信息';

/*
 * 楼层信息
 */
CREATE TABLE t_HouseFloor (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	house bigint not NULL  default -1 COMMENT '楼号',
	name varchar(200) NOT NULL default '' COMMENT '名字',
	description varchar(1000) NOT NULL default '' COMMENT '描述',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_HouseFloor_id PRIMARY KEY(id),
	constraint fk_t_HouseFloor_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_HouseFloor_community FOREIGN KEY (community) REFERENCES t_Community(id),
	/*constraint fk_t_HouseFloor_house FOREIGN KEY (house) REFERENCES t_House(id),*/
	constraint fk_t_HouseFloor_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id),
	constraint uk_t_HouseFloor unique (community,name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='楼层信息';



/*
 * 楼房门牌信息
 */
CREATE TABLE t_HouseRoom (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	house bigint not NULL  default -1 COMMENT '楼号',
	name varchar(200) NOT NULL default '' COMMENT '名字',
	description varchar(1000) NOT NULL default '' COMMENT '描述',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_HouseRoom_id PRIMARY KEY(id),
	constraint fk_t_HouseRoom_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_HouseRoom_community FOREIGN KEY (community) REFERENCES t_Community(id),
	/*constraint fk_t_HouseRoom_house FOREIGN KEY (house) REFERENCES t_House(id),*/
	constraint fk_t_HouseRoom_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id),
	constraint uk_t_HouseRoom unique (community,name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='楼房门牌信息';


/*
 * 订阅者楼房信息
 */
CREATE TABLE t_SubscriberHouse (
	id bigint NOT NULL AUTO_INCREMENT,
	openid varchar(32) not null COMMENT '公号的唯一标识', 
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	house bigint not NULL COMMENT '楼号',
	houseUnit bigint not NULL COMMENT '单元',
	houseFloor bigint not NULL COMMENT '楼层',
	houseRoom bigint not NULL COMMENT '门牌',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_SubscriberHouse_id PRIMARY KEY(id),
	constraint fk_t_SubscriberHouse_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_SubscriberHouse_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_SubscriberHouse_house FOREIGN KEY (house) REFERENCES t_House(id),
	constraint fk_t_SubscriberHouse_houseUnit FOREIGN KEY (houseUnit) REFERENCES t_HouseUnit(id),
	constraint fk_t_SubscriberHouse_houseFloor FOREIGN KEY (houseFloor) REFERENCES t_HouseFloor(id),
	constraint fk_t_SubscriberHouse_houseRoom FOREIGN KEY (houseRoom) REFERENCES t_HouseRoom(id),
	constraint uk_t_SubscriberHouse UNIQUE KEY (openid,corp,community,house,houseUnit,houseFloor,houseRoom)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='订阅者楼房信息';

/*
 * 订阅者电话
 */
CREATE TABLE t_SubscriberPhone(
	id bigint NOT NULL AUTO_INCREMENT,
	openid varchar(32) not null COMMENT '公号的唯一标识', 
	telephone varchar(12) not null default '' COMMENT '电话',
	mobilephone varchar(12) not null default '' COMMENT '手机',
	constraint pk_t_SubscriberPhone_id PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='订阅者电话';

/*
 * 订阅者汽车
 */
CREATE TABLE t_SubscriberCar(
	id bigint NOT NULL AUTO_INCREMENT,
	openid varchar(32) not null COMMENT '公号的唯一标识', 
	number varchar(8) not null COMMENT '车牌',
	constraint pk_t_SubscriberCar_id PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='订阅者汽车';
/*
 * 服务电话
 */
CREATE TABLE t_ServicePhone (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	title varchar(200) NOT NULL default '' COMMENT '标题',
	description varchar(1000) NOT NULL default '' COMMENT '描述',
	phone varchar(12) not NULL COMMENT '电话',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_ServicePhone_id PRIMARY KEY(id),
	constraint fk_t_ServicePhone_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_ServicePhone_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_ServicePhone_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='服务电话';


/*
 * 投诉表
 */
CREATE TABLE t_Complaint (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	content text NOT NULL default '' COMMENT '投诉内容',
	creator varchar(32) not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_Complaint_id PRIMARY KEY(id),
	constraint fk_t_Complaint_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_Complaint_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_Complaint_creator FOREIGN KEY (creator) REFERENCES t_Subscriber(openid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='投诉表';

/*
 * 投诉表回复
 */
CREATE TABLE t_ComplaintResponse (
	id bigint NOT NULL AUTO_INCREMENT,
	complaint bigint not NULL COMMENT '所属投诉',
	content text NOT NULL default '' COMMENT '回复内容',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_ComplaintResponse_id PRIMARY KEY(id),
	constraint fk_t_ComplaintResponse_complaint FOREIGN KEY (complaint) REFERENCES t_Complaint(id),
	constraint fk_t_ComplaintResponse_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='投诉回复';

/*
 * 代办事务类型
 */
CREATE TABLE t_CommissionType (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	name varchar(200) NOT NULL default '' COMMENT '类型名称',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_CommissionType_id PRIMARY KEY(id),
	constraint fk_t_CommissionType_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_CommissionType_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_CommissionType_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id),
	constraint uk_t_CommissionType UNIQUE KEY (corp,community,name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='代办事务类型';

/*
 * 代办事务模板
 */
CREATE TABLE t_CommissionTemplate (
	id bigint NOT NULL AUTO_INCREMENT,
	type bigint not NULL COMMENT '事务类型',
	content varchar(200) NOT NULL default '' COMMENT '模板内容',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_CommissionTemplate_id PRIMARY KEY(id),
	constraint fk_t_CommissionTemplate_type FOREIGN KEY (type) REFERENCES t_CommissionType(id),
	constraint fk_t_CommissionTemplate_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='代办事务模板';


/*
 * 代办事务
 */
CREATE TABLE t_Commission (
	id bigint NOT NULL AUTO_INCREMENT,
	type bigint not NULL COMMENT '事务类型',
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	content text NOT NULL default '' COMMENT '代办事务内容',
	creator varchar(32) not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_Commission_id PRIMARY KEY(id),
	constraint fk_t_Commission_type FOREIGN KEY (type) REFERENCES t_CommissionType(id),
	constraint fk_t_Commission_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_Commission_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_Commission_creator FOREIGN KEY (creator) REFERENCES t_Subscriber(openid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='代办事务';

/*
 * 代办事务回复
 */
CREATE TABLE t_CommissionResponse (
	id bigint NOT NULL AUTO_INCREMENT,
	commission bigint not NULL COMMENT '所属事务',
	content varchar(2000) NOT NULL default '' COMMENT '回复内容',
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_CommissionResponse_id PRIMARY KEY(id),
	constraint fk_t_CommissionResponse_commission FOREIGN KEY (commission) REFERENCES t_Commission(id),
	constraint fk_t_CommissionResponse_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='代办事务回复';

/*
 * 消费信息
 */
CREATE TABLE t_ConsumeInfo (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	house bigint not NULL COMMENT '所属楼号',
	houseRoom bigint not NULL COMMENT '所属门牌',
	consumeType  bigint not NULL COMMENT '消费类别',
	payNumber varchar(100) COMMENT '缴费户号',
	year int not NUll COMMENT '年',
	month int not NUll COMMENT '月',
	postAmount double not NUll default 0 COMMENT '上月表数',
	currentAmount double not NUll default 0 COMMENT '本月表数',
	amount double not NUll default 0 COMMENT '使用数量',
	price double not NUll COMMENT '单价',
	payAmount double not NUll COMMENT '应缴费数',
	consumeTime datetime NOT NULL COMMENT '消费时间',
	remark varchar(200) not NUll default '' COMMENT '备注', 
	creator bigint not NULL COMMENT '创建人',
	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_ConsumeInfo_id PRIMARY KEY(id),
	constraint fk_t_ConsumeInfo_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_ConsumeInfo_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_ConsumeInfo_house FOREIGN KEY (house) REFERENCES t_House(id),
	constraint fk_t_ConsumeInfo_houseRoom FOREIGN KEY (houseRoom) REFERENCES t_HouseRoom(id),
	constraint fk_t_ConsumeInfo_consumeType FOREIGN KEY (consumeType) REFERENCES t_SysConsumeType(id),
	constraint fk_t_ConsumeInfo_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='消费信息';

/*
 * 消费信息发送微信内容模板
 */
CREATE TABLE t_ConsumeTemplate (
  	id bigint(20) NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
  	consumeType bigint(20) NOT NULL COMMENT '消费类别',
 	title varchar(100) COLLATE utf8_bin NOT NULL COMMENT '模板标题',
  	content varchar(1000) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '模板内容',
  	creator bigint(20) NOT NULL COMMENT '创建人',
  	createTime datetime NOT NULL COMMENT '创建时间',
	constraint pk_t_ConsumeTemplate_id PRIMARY KEY(id),
 	constraint fk_t_ConsumeTemplate_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_ConsumeTemplate_community FOREIGN KEY (community) REFERENCES t_Community(id),
  	constraint fk_t_ConsumeTemplate_consumeType FOREIGN KEY (consumeType) REFERENCES t_SysConsumeType (id),
  	constraint fk_t_ConsumeTemplate_creator FOREIGN KEY (creator) REFERENCES t_SysUser (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='消费信息发送微信内容模板';

/*
 * 消费费用缴纳
 */
CREATE TABLE t_ConsumePay (
	id bigint NOT NULL AUTO_INCREMENT,
	consumeInfo bigint not NUll default 0 COMMENT '消费信息',
	payInterface bigint not NUll COMMENT '交易接口',
	payAmount double not NUll default 0 COMMENT '应缴费数',
	tradeSequence varchar(100) NOT NULL COMMENT '交易流水号',
	tradeAmount double not NUll default 0 COMMENT '应缴费数',
	tradeErrCode datetime NOT NULL COMMENT '交易结果',
	tradeFlag tinyint NOT NULL default 0 COMMENT '交易标记 0成功，1失败',
	tradeTime datetime NOT NULL COMMENT '交易时间',
	remark varchar(200) not NUll default '' COMMENT '备注', 
	constraint pk_t_ConsumePay_id PRIMARY KEY(id),
	constraint fk_t_ConsumePay_consumeInfo FOREIGN KEY (consumeInfo) REFERENCES t_ConsumeInfo(id),
	constraint fk_t_ConsumePay_payInterface FOREIGN KEY (payInterface) REFERENCES t_SysPayInterface(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='消费费用缴纳';


/*
 * 通知
 */
CREATE TABLE t_Notice (
	id bigint NOT NULL AUTO_INCREMENT,
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	type tinyint not null COMMENT '类型，0公共，1私有',
	title varchar(200) NOT NULL default '' COMMENT '标题',
	description varchar(1000) NOT NULL default '' COMMENT '通知描述',
	picUrl varchar(1000) NOT NULL default '' COMMENT '图片链接',
	content text NULL COMMENT '通知内容',
	msg text NULL COMMENT '微信消息内容',
	remark varchar(200) NULL COMMENT '备注',
	createTime datetime not null COMMENT '创建时间',
	creator bigint not null COMMENT '创建人',
	auditTime datetime COMMENT '审核时间',
	auditor bigint COMMENT '审核人',
	sendTime datetime COMMENT '发送时间',
	sender bigint  COMMENT '发送人',
	readTime datetime COMMENT '阅读时间',
	constraint pk_t_Notice_id PRIMARY KEY(id),
	constraint fk_t_Notice_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_Notice_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_Notice_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='通知';

/*
 * 通知接收者
 */
CREATE TABLE t_NoticeReceiver (
	id bigint NOT NULL AUTO_INCREMENT,
	notice bigint not NULL COMMENT '对应通知',
	house bigint not NULL COMMENT '所属楼号',
	houseRoom bigint not NULL COMMENT '所属门牌',
	openid varchar(32) not NULL COMMENT '接收微信openid',
	sendTime datetime not null COMMENT '发送时间',
	sender bigint not null COMMENT '发送人',
	readStatus tinyint not null default 0 COMMENT '阅读状态',
	readTime datetime COMMENT '阅读时间',
	constraint pk_t_NoticeReceiver_id PRIMARY KEY(id),
	constraint fk_t_NoticeReceiver_notice FOREIGN KEY (notice) REFERENCES t_Notice(id),
	constraint fk_t_NoticeReceiver_house FOREIGN KEY (house) REFERENCES t_House(id),
	constraint fk_t_NoticeReceiver_houseRoom FOREIGN KEY (houseRoom) REFERENCES t_HouseRoom(id),
	constraint fk_t_NoticeReceiver_openid FOREIGN KEY (openid) REFERENCES t_Subscriber(openid),
	constraint fk_t_NoticeReceiver_sender FOREIGN KEY (sender) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='通知接收者';

/*
 * MO表name
 */
CREATE TABLE t_MO(
	id bigint NOT NULL AUTO_INCREMENT,
	toUserName varchar(32)not NULL COMMENT '接收微信号',
	fromUserName varchar(32)not NULL COMMENT '发送方帐号(一个OpenID)',
	createTime bigint not NULL COMMENT '消息创建时间',
	msgType varchar(100) not NULL COMMENT '类型',
	msgId bigint COMMENT '消息ID',
	content text COMMENT '文本消息',
	picUrl varchar(200) COMMENT '图片消息URL',
	mediaId varchar(100) COMMENT '语音消息媒体id',
	location_X double COMMENT '地理位置维度',
	location_Y double COMMENT '地理位置经度',
	scale double COMMENT '地图缩放大小',
	label varchar(100) COMMENT '地理位置信息',
	title varchar(100) COMMENT '链接消息标题',
	description varchar(200) COMMENT '链接消息描述',
	url varchar(100) COMMENT '链接消息链接',
	format varchar(100) COMMENT '语音格式',
	recognition varchar(100) COMMENT '语音识别结果',
	event varchar(100) COMMENT '事件类型',
	eventKey varchar(100) COMMENT '事件KEY值,qrscene_为前缀,后面为二维码的参数值',
	ticket varchar(100) COMMENT '二维码的ticket,可用来换取二维码图片',
	latitude double COMMENT '地理位置纬度',
	longitude double COMMENT '地理位置经度',
	`precision` double COMMENT '地理位置精度',
	receiveTime timestamp not null default  current_timestamp  COMMENT '接收时间',
	performTime datetime  COMMENT '处理时间',
	performer varchar(20)  COMMENT '处理者',
	constraint pk_t_MO_id PRIMARY KEY(id),
	constraint fk_t_MO_msgType FOREIGN KEY (msgType) REFERENCES t_MsgType(type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='MO表';

/*
 * MT表
 */
CREATE TABLE t_MT(
	id bigint NOT NULL AUTO_INCREMENT,
	mo bigint not NULL default 0 COMMENT '上行信息ID',
	msg bigint not NULL default 0 COMMENT '消息ID',
	toUserName varchar(32)not NULL COMMENT '接收微信号',
	fromUserName varchar(32)not NULL COMMENT '发送方帐号(一个OpenID)',
	msgType varchar(100) not NULL COMMENT '类型',
	title varchar(1000) NOT NULL default '' COMMENT '标题',
	description varchar(1000) NOT NULL default '' COMMENT '图文消息描述',
	picUrl varchar(1000) NOT NULL default '' COMMENT '图片链接',
	content text not NULL COMMENT '消息内容',
	children varchar(200) not NULL default '' COMMENT '图文子消息',
	priority tinyint not null default 0 COMMENT '优先权(0-9),越大优先权越高',
	sendTime datetime not null COMMENT '发送时间',
	errCode int not null default -1 COMMENT '发送状态',
	errMsg varchar(100) not null default '' COMMENT '发送错误信息',
	performTime datetime  COMMENT '处理时间',
	presentTime datetime not null COMMENT '请求时间',
	presenter bigint not NULL default 0 COMMENT '请求人',
	auditTime datetime COMMENT '审核时间',
	auditor bigint COMMENT '审核人',
	constraint pk_t_MT_id PRIMARY KEY(id),
	constraint fk_t_MT_msgType FOREIGN KEY (msgType) REFERENCES t_MsgType(type)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='MT表';

/*
 * 消息类型表
 */
CREATE TABLE t_EventType (
	event varchar(50) NOT NULL default '' COMMENT '事件类型',
	name varchar(50) NOT NULL default '' COMMENT '事件名称',
	constraint pk_t_EventType_type PRIMARY KEY(event),
	constraint uk_t_EventType_name unique(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='消息类型';

/*
*论坛版块
*/

CREATE TABLE t_BbsBoard(
	id bigint NOT NULL AUTO_INCREMENT COMMENT '论坛版块ID',
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
	name varchar(150) NOT NULL default '' COMMENT '论坛版块名',
	description varchar(256) default NULL COMMENT '论坛版块描述',
	topics bigint NOT NULL default 0 COMMENT '帖子数目',
	creator bigint NOT NULL  COMMENT '创建用户',
  	createTime date NOT NULL COMMENT '创建时间',
  	cancelTime date NULL COMMENT '删除时间',
  	canceler bigint NULL COMMENT '删除者',
  	disableTime date NULL COMMENT '禁止发帖时间',
  	disabler bigint NULL COMMENT '禁止发帖人',
	constraint pk_t_BbsBoard_id PRIMARY KEY(id),
	constraint uk_t_BbsBoard_name unique(name),
	constraint fk_t_BbsBoard_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_BbsBoard_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_BbsBoard_creator FOREIGN KEY (creator) REFERENCES t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='论坛版块';

/*
*论坛话题
*/
CREATE TABLE t_BbsTopic(
	id bigint NOT NULL AUTO_INCREMENT COMMENT '话题ID',
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
  	board bigint NOT NULL COMMENT '所属论坛',
  	title varchar(100) NOT NULL default '' COMMENT '话题标题',
  	content text NOT NULL default '' COMMENT '话题内容',
  	lastPost date NOT NULL COMMENT '最后回复时间',
	views bigint NOT NULL default 1 COMMENT '浏览数',
	replies bigint NOT NULL default 0 COMMENT '回复数',
	tops bigint NOT NULL default 0 COMMENT '顶数',
	creator varchar(32) NOT NULL COMMENT '发表用户',
  	createTime date NOT NULL COMMENT '发表时间',
  	cancelTime date NULL COMMENT '删除时间',
  	canceler bigint NULL COMMENT '删除者',
  	disableTime date NULL COMMENT '禁止回复时间',
  	disabler bigint NULL COMMENT '禁止回复人',
	constraint pk_t_BbsTopic_id PRIMARY KEY(id),
	constraint fk_t_BbsTopic_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_BbsTopic_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_BbsTopic_board FOREIGN KEY (board) REFERENCES t_BbsBoard(id),
	constraint fk_t_BbsTopic_creator FOREIGN KEY (creator) REFERENCES t_Subscriber(openid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='论坛话题';


/*
*论坛帖子
*/
CREATE TABLE t_BbsPost(
	id bigint NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
  	board bigint NOT NULL COMMENT '论坛ID',
  	topic bigint NOT NULL COMMENT '话题ID',
  	type tinyint(4) NOT NULL default 2 COMMENT '帖子类型 1:主帖子 2:回复帖子',
	title varchar(100) NOT NULL COMMENT '帖子标题',
	content text NOT NULL COMMENT '帖子内容',
	creator varchar(32) NOT NULL COMMENT '发表用户',
  	createTime date NOT NULL COMMENT '发表时间',
  	cancelTime date NULL COMMENT '删除时间',
  	canceler bigint NULL COMMENT '删除者',
  	disableTime date NULL COMMENT '禁止回复时间',
  	disabler bigint NULL COMMENT '禁止回复人',
  	constraint pk_t_BbsPost_id PRIMARY KEY(id),
	constraint fk_t_BbsPost_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_BbsPost_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_BbsPost_board FOREIGN KEY (board) REFERENCES t_BbsBoard(id),
	constraint fk_t_BbsPost_topic FOREIGN KEY (topic) REFERENCES t_BbsTopic(id),
	constraint fk_t_BbsPost_creator FOREIGN KEY (creator) REFERENCES t_Subscriber(openid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='论坛帖子';


/*
*论坛精华话题
*/
CREATE TABLE t_BbsCream(
	id bigint NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
  	board bigint NOT NULL COMMENT '论坛ID',
  	topic bigint NOT NULL COMMENT '话题ID',
  	creator bigint NOT NULL COMMENT '生成用户',
  	createTime date NOT NULL COMMENT '生成时间',
  	constraint pk_t_BbsCream_id PRIMARY KEY(id),
	constraint fk_t_BbsCream_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_BbsCream_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_BbsCream_board foreign key (board) references t_BbsBoard(id),
	constraint fk_t_BbsCream_topic foreign key (topic) references t_BbsTopic(id),
	constraint fk_t_BbsCream_creator foreign key (creator) references t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='论坛精华话题';


/*
*论坛置顶话题
*/
CREATE TABLE t_BbsTop(
	id bigint NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
	corp bigint not NULL COMMENT '所属公司',
	community bigint not NULL COMMENT '所属小区',
  	board bigint NOT NULL COMMENT '论坛ID',
  	topic bigint NOT NULL COMMENT '话题ID',
  	creator bigint NOT NULL COMMENT '生成用户',
  	createTime date NOT NULL COMMENT '生成t_Bbs时间',
  	constraint pk_t_BbsTop_id PRIMARY KEY(id),
	constraint fk_t_BbsTop_corp FOREIGN KEY (corp) REFERENCES t_SysCorp(id),
	constraint fk_t_BbsTop_community FOREIGN KEY (community) REFERENCES t_Community(id),
	constraint fk_t_BbsTop_board foreign key (board) references t_BbsBoard(id),
	constraint fk_t_BbsTop_topic foreign key (topic) references t_BbsTopic(id),
	constraint fk_t_BbsTop_creator foreign key (creator) references t_SysUser(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=0 COMMENT='论坛置顶话题';








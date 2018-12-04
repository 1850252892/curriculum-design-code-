create table if not exists tb_sys_permission(
  id int not null primary key
);
create table if not exists tb_sys_rp(
  id int not null primary key
);
create table if not exists tb_sys_ur(
  id int not null primary key
);
create table if not exists tb_sys_role(
  id int not null primary key
);
create table if not exists tb_sys_user(
  id int not null primary key
);

create table if not exists tb_base_user( -- 基础用户表
  user_id int not null auto_increment comment '自增主键',
  account varchar(128) not null comment '用户账号',
  password varchar(128) not null comment '密码 MD5',
  nickname varchar(25) comment '昵称',
  truename varchar(25) comment '真实姓名',
  userlogo varchar(255) comment '头像路径',
  phone varchar(20) comment '手机号',
  email varchar(64) comment '电子邮箱',
  status int not null comment '账号状态 ：-1 删除;-2 废弃(修改信息后存档);0 刚注册，未完善信息; 1 正常状态;2 锁定状态，不可进行交易',
  type int not null comment '账号类型：1 手机号;2 邮箱;3 手机号+邮箱',
  last_login_time datetime comment '最后一次登陆时间',
  create_time datetime comment '创建时间',
  primary key (user_id)
);
alter table tb_base_user add qq varchar(20) comment 'QQ号' after email;
alter table tb_base_user rename to tb_user_base;

create table if not exists tab_tp_user( -- 第三方平台授权账号
  open_id varchar(128) not null comment '授权平台openId',
  user_id int comment '对应的用户id',
  isRegist int comment '是否注册',
  status int not null comment '账号状态 ：-1 删除;-2 废弃(修改信息后存档);0 刚注册，未完善信息; 1 正常状态;2 锁定状态，不可进行交易',
  type int not null comment '账号所属平台：1 QQ',
  create_time datetime comment '创建时间',
  primary key (open_id)
)
alter table tab_tp_user rename to tb_tp_user;

 create table if not exists tb_buyer( -- 买家表
   buyer_id int not null auto_increment comment '自增主键',
   user_id int not null comment '用户账号',
   integral int not null comment '用户积分',
   level_id int not null comment '用户级别',
   balance int comment '钱包余额',
   status int not null comment '账号状态 ：-1 删除;-2 废弃(修改信息后存档);0 刚注册，未完善信息; 1 正常状态;2 锁定状态，不可进行交易',
   create_time datetime comment '创建时间',
   primary key (buyer_id)
 );
alter table tb_buyer rename to tb_user_buyer;


create table if not exists tb_addresses( -- 地址表
  addr_id int not null auto_increment comment '自增主键，地址id',
  user_id int not null  comment '用户id',
  province varchar(128) not null comment '省份',
  city varchar(128) not null comment '市',
  county varchar(128) not null comment '县/区',
  town varchar(128) not null comment '镇/街道',
  school varchar(128) comment '学校',
  details varchar(128) not null comment '详细地址',
  type int not null comment '地址类型：1-用户所在地  2-收货地址 3-商品地址 4-商家可交易地址 5-线下交易地址',
  status int not null comment '账号状态 ：-1 删除;-2 废弃(修改信息后存档);0 刚注册，未完善信息; 1 正常状态;2 锁定状态，不可进行交易',
  create_time datetime comment '创建时间',
  primary key (addr_id)
);

create table  if not exists tb_item( -- 商品基表
  item_id int not null auto_increment comment '自增主键，商品id',
  user_id int not null  comment '用户id',
  item_name varchar(128) not null comment '商品名',
  category int not null comment '商品分类',
  create_time datetime comment '创建时间',
  status int not null comment '数据状态: -1-删除，1-正常',
  primary key (item_id)
);
alter table tb_item rename to tb_item_base;


create table if not exists tb_sku( -- sku
  sku_id int not null auto_increment comment '自增主键,sku id',
  item_id int not null comment '商品id',
  stock int not null comment '商品库存',
  price float not null comment '商品价格',
  sku_name varchar(128) not null comment '商品名',
  status int not null comment '数据状态： -1-删除; 1-正常; 2-锁定',
  sku_img varchar(1024) comment '商品全部图片地址',
  sku_main_img varchar(512) comment '商品主图',
  sku_details text comment '商品详情',
  create_time datetime comment '创建时间',
  primary key (sku_id)
);
alter table tb_sku add sku_description varchar(128) comment '商品简介,促销语' after sku_details;
alter table tb_sku rename to tb_item_sku;

create table if not exists tb_category( -- 分类表
  ctg_id int not null auto_increment comment '自增主键，分类id',
  ctg_name varchar(64) not null comment '分类名',
  ctg_level int not null comment '分类级别',
  ctg_p_id int comment '父分类id',
  status int not null comment '数据状态： -1-删除; 1-正常; 2-锁定',
  create_time datetime comment '创建时间',
  primary key (ctg_id)
);

create table if not exists tb_item_ctg( -- 记录商品分类表
  ic_id int not null auto_increment comment '主键id',
  item_id int not null comment '商品id',
  ctg_id int not null comment '分类id',
  status int not null comment '数据状态： -1-删除; 1-正常; 2-锁定',
  create_time datetime comment '创建时间',
  primary key (ic_id)
);

create table if not exists tb_label( -- 标签表
  label_id int not null auto_increment comment '标签id',
  label_name varchar(64) not null comment '标签名',
  status int not null comment '数据状态： -1-删除; 1-正常; 2-锁定',
  create_time datetime comment '创建时间',
  primary key (label_id)
);

create table if not exists tb_item_label( -- 商品标签表
  il_id int not null auto_increment comment '主键id',
  item_id int not null comment '商品id',
  label_id int not null comment '分类id',
  status int not null comment '数据状态： -1-删除; 1-正常; 2-锁定',
  create_time datetime comment '创建时间',
  primary key (il_id)
);
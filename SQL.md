# 常熟文庙

```sql
create table `article` (
    `article_id` int not null auto_increment,
    `title` varchar(255) not null comment '文章标题',
    `article_logo` varchar(255) not null comment '文章log',
    `content` longtext comment '文章详情',
    `enabled` tinyint NOT NULL DEFAULT '0' COMMENT '上架',
    `node_id` int unsigned NOT NULL COMMENT '节点ID',
    `sort` int unsigned NOT NULL,
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`article_id`)
);

create table `article_nodes` (
    `node_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '节点ID',
    `parent_id` int unsigned NOT NULL DEFAULT '0' COMMENT '父节点ID',
    `node_name` varchar(255)  NOT NULL DEFAULT '' COMMENT '节点名称',
    `has_children` tinyint NOT NULL DEFAULT '0' COMMENT '对否有子节点',
    `enabled` tinyint NOT NULL DEFAULT '0' COMMENT '上架',
    `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`node_id`)
);

create table `activity` (
    `activity_id` varchar(32) not null,
    `activity_name` varchar(64) not null comment '活动名称',
    `activity_tag`  varchar(255) comment '活动tag',
    `activity_logo` varchar(255) comment '活动logo',
    `activity_desc` varchar(512) comment '活动简介',
    `activity_content` longtext comment '活动详情',
    `release_time` timestamp not null default current_timestamp comment '发布时间',
    `start_time` timestamp not null comment '开始时间',
    `end_time` timestamp not null comment '结束时间',
    `buy_limit`	int unsigned NOT NULL DEFAULT '0' comment '限制人数',
    `hot` int unsigned NOT NULL DEFAULT '0' comment '热度',
    `activity_status` int unsigned NOT NULL DEFAULT '0' comment '0:报名中 1:未开始 2:已结束',
    `enabled` tinyint NOT NULL DEFAULT '0' comment '上架',
    primary key (`activity_id`)
);

create table `activity_order` (
    `activity_order_id` varchar(32) not null,
    `activity_id` varchar(32) not null comment '活动ID',
    `buyer_name`  varchar(32) not null comment '姓名',
    `buyer_phone` varchar(32) not null comment '电话',
    `buyer_openid` varchar(32) not null comment '微信openid',
    `order_status` tinyint not null default '0' comment '活动订单状态(默认已报名)',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`activity_order_id`),
    key `idx_buyer_openid` (`buyer_openid`)
);

create table `exhibits_info` (
    `exhibits_id` varchar(32) not null,
    `exhibits_name` varchar(64) not null comment '展品名称',
    `exhibits_description` varchar(128) comment '展品介绍',
    `exhibits_content` longtext comment '展品详情',
    `exhibits_icon` varchar(512) comment '展品小图',
    `exhibits_mp3` varchar(512) comment '展品音频',
    `exhibits_mp4` varchar(512) comment '展品视频',
    `exhibits_link` varchar(512) comment '二维码链接',
    `exhibits_status` tinyint DEFAULT '0' COMMENT '展品状态',
    `parent_id` varchar(32) default null comment '',
    `areas_id` varchar(32) default null comment '',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`exhibits_id`),
    key `idx_parent_id` (`parent_id`)
);

create table `exhibits_master` (
    `master_id` varchar(32) not null,
    `master_link` varchar(512) not null comment '二维码链接',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`master_id`)
);

create table `history_log` (
    `log_id` int not null auto_increment,
    `openid` varchar(32) not null comment '微信openid',
    `master_id` varchar(32) not null,
    `create_time` timestamp not null default current_timestamp on update current_timestamp comment '创建时间',
    primary key (`log_id`),
    key `idx_open_id` (`openid`)
);

create table `user` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(32) not null comment '',
    `password` varchar(128) comment '密码',
    `openid` varchar(32) comment '微信openid',
    `alias` varchar(64) COMMENT '昵称',
    `icon` varchar(255) COMMENT '头像',
    `role` int unsigned not null default 0 COMMENT '权限',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
	primary key (`id`),
	key `idx_open_id` (`openid`)
);

INSERT INTO `user`(name,password,role) VALUES ('admin', '$2a$10$WqRJmPOwloMI4aYVaPKJWuHjPlV13F5oB2zwGgJ/Dwr6wNFk4jWUm','2');

CREATE TABLE `areas` (
  `areas_id` int(20) NOT NULL AUTO_INCREMENT,
  `areas_content` longtext NOT NULL COMMENT '介绍',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `areas_top` varchar(32) NOT NULL,
  `areas_left` varchar(32) NOT NULL,
  `areas_audio` varchar(255) DEFAULT NULL,
  `areas_video` varchar(255) DEFAULT NULL,
  `areas_name` varchar(20) NOT NULL,
  `areas_logo` varchar(255) NOT NULL,
  PRIMARY KEY (`areas_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `history_footprints` (
  `history_id` int(20) NOT NULL AUTO_INCREMENT,
  `history_content` longtext NOT NULL,
  `start_year` int(20) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sort` int(20) NOT NULL,
  PRIMARY KEY (`history_id`,`sort`),
  UNIQUE KEY `sort` (`sort`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

CREATE TABLE `exam_level` (
  `level_id` int(20) NOT NULL AUTO_INCREMENT,
  `level_name` varchar(20) NOT NULL,
  `level_icon` varchar(255) NOT NULL,
  `sort` int(20) NOT NULL,
  `enabled` int(5) NOT NULL DEFAULT '0',
  `level_desc` longtext NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`level_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `history_person` (
  `person_id` int(20) NOT NULL AUTO_INCREMENT,
  `person_name` varchar(20) NOT NULL COMMENT '人物名字',
  `person_icon` varchar(255) NOT NULL COMMENT '人物画像',
  `person_desc` longtext NOT NULL COMMENT '人物介绍',
  `level_id` varchar(20) NOT NULL,
  `enabled` int(5) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`person_id`),
  KEY `level_id` (`level_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `goods_category` (
  `goods_id` varchar(32) NOT NULL,
  `goods_icon` varchar(255) NOT NULL,
  `goods_name` varchar(20) NOT NULL,
  `goods_desc` longtext NOT NULL,
  `goods_content` longtext NOT NULL,
  `enabled` int(3) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `view` (
  `view_code` varchar(32) NOT NULL,
  `view_des` varchar(512),
  `view_icon` varchar(512),
  `view_content` longtext,
  `view_mp4` varchar(512) ,
  PRIMARY KEY (`view_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

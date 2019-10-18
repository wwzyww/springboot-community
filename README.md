CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(256) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `bio` varchar(256) DEFAULT NULL,
  `avatar_url` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
-------------------------------------------------------------------------
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) DEFAULT NULL,
  `description` text,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL COMMENT '创建人id',
  `comment_count` int(11) unsigned DEFAULT '0' COMMENT '评论数',
  `view_count` int(11) unsigned DEFAULT '0' COMMENT '浏览数',
  `like_count` int(11) unsigned DEFAULT '0' COMMENT '点赞数',
  `tag` varchar(256) DEFAULT NULL COMMENT '标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8;
---------------------------------------------------------------------------
CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notifier` int(11) DEFAULT NULL COMMENT '通知的人',
  `receiver` int(11) DEFAULT NULL COMMENT '接收消息的人',
  `outer_id` int(11) DEFAULT NULL COMMENT '用来区别是问题还是回复或者各种的类型',
  `type` int(11) DEFAULT NULL COMMENT '用来区分是评论还是问题',
  `create_time` bigint(20) DEFAULT NULL,
  `status` int(11) unsigned zerofill NOT NULL DEFAULT '00000000000' COMMENT '0未读 1已读',
  `notifier_name` varchar(100) DEFAULT NULL,
  `outer_title` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
-----------------------------------------------------------------------------
CREATE TABLE `comment` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `parent_id` int(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '父类类型',
  `commentator` int(11) DEFAULT NULL COMMENT '评论人id',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `comment_count` int(20) unsigned DEFAULT '0',
  `like_count` int(20) DEFAULT '0' COMMENT '点赞数',
  `content` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8;
------------------------------------------------------------------------------------

   

## 练习项目 --- 社区

##数据库 ---community
 --CREATE TABLE `user` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `account_id` varchar(100) DEFAULT NULL,
     `name` varchar(100) DEFAULT NULL,
     `token` char(36) DEFAULT NULL,
     `create_time` bigint(20) DEFAULT NULL,
     `update_time` bigint(20) DEFAULT NULL,
     `bio` varchar(256) DEFAULT NULL,
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
 --CREATE TABLE `question` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `title` varchar(256) DEFAULT NULL,
     `description` text,
     `create_time` bigint(20) DEFAULT NULL,
     `update_time` bigint(20) DEFAULT NULL,
     `creator` int(11) DEFAULT NULL COMMENT '创建人id',
     `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
     `view_count` int(11) DEFAULT '0' COMMENT '浏览数',
     `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
     `tag` varchar(256) DEFAULT NULL COMMENT '标签',
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
   
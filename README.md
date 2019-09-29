## 练习项目 --- 社区

##数据库 ---community
 CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `account_id` varchar(100) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `token` char(36) DEFAULT NULL,
    `create_time` bigint(20) DEFAULT NULL,
    `update_time` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
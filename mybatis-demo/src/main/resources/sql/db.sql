
CREATE DATABASE IF NOT EXISTS `liaozl` DEFAULT CHARACTER SET utf8;

USE `liaozl`;

CREATE TABLE `t_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司ID',
  `name` varchar(50) NOT NULL COMMENT '公司名称',
  `address` varchar(100) DEFAULT NULL COMMENT '公司地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '公司信息表';

insert  into `t_company`(`id`,`name`,`address`,`create_time`) values (1,'全民吃瓜','深圳蛇口','2019-05-25 15:40:47');
insert  into `t_company`(`id`,`name`,`address`,`create_time`) values (2,'你我金融','深圳南山','2019-05-25 15:41:12');


CREATE TABLE `t_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `name` varchar(50) NOT NULL COMMENT '员工名称',
  `sex` tinyint(4) NOT NULL COMMENT '员工性别(0:女 1:男)',
  `age` int(4) NOT NULL COMMENT '员工年龄',
  `company_id` int(11) NOT NULL COMMENT '所属公司',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name_companyId` (`name`,`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '员工信息表';

insert  into `t_employee`(`id`,`name`,`sex`,`age`,`company_id`,`create_time`) values (1,'张三',1,23,1,'2019-05-25 15:41:39');
insert  into `t_employee`(`id`,`name`,`sex`,`age`,`company_id`,`create_time`) values (2,'李四',0,18,1,'2019-05-25 15:41:58');
insert  into `t_employee`(`id`,`name`,`sex`,`age`,`company_id`,`create_time`) values (3,'王五',1,33,2,'2019-05-25 15:42:17');



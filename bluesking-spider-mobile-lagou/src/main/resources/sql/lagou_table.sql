use spider;

create table mobile_lagou_position (											/* 【招聘职位信息】 */
	position_id int(4) not null auto_increment comment '职位编号',				/* 职位编号 */
    position_name varchar(40) not null comment '职位名称',						/* 职位名称 */
    city varchar(10) not null comment '城市', 									/* 城市 */
    salary varchar(15) not null comment '薪资',									/* 薪资 */
    min_salary int(4) not null comment '薪资下限', 								/* 薪资下限 */
    max_salary int(4) not null comment '薪资上限', 								/* 薪资上限 */
    company_id int(4) not null comment '公司编号', 								/* 公司编号 */
    company_name varchar(50) not null comment '公司简称', 						/* 公司简称 */
    company_full_name varchar(50) not null comment '公司全称', 					/* 公司全称 */
    position_advantage varchar(50) comment '职位诱惑', 							/* 职位诱惑 */
    district varchar(10) comment '城区', 										/* 城区 */
    biz_area varchar(10) comment '商区', 										/* 商区 */
    lng varchar(15) comment '纬度', 											/* 纬度 */
    lat varchar(15) comment '经度', 											/* 经度 */
    education varchar(10) comment '学历要求', 									/* 学历要求 */
    work_year varchar(10) comment '工作经验', 									/* 工作经验 */
    position_description text comment '职位描述', 								/* 职位描述 */
    position_address varchar(150) comment '公司地址', 							/* 公司地址 */
    key_word varchar(30) not null comment '关键字',								/* 关键字 */
    primary key(position_id)													/* 主键 */
) comment = '招聘职位信息';

create table proxy_info (														/* 【代理信息】 */
	proxy_id int(4) not null auto_increment comment '代理编号',					/* 代理编号 */
    ip varchar(15) not null comment 'ip地址',									/* ip地址 */
    port int(4) not null comment '端口号',										/* 端口号 */
    status int(4) not null default 0 comment '有效次数',						/* 有效次数 */
    primary key(proxy_id)														/* 主键 */
) comment = '代理信息';
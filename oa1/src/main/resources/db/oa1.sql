drop database if exists oa;

create database oa;
use oa;

create table receipts(
  id int not null auto_increment primary key ,
  cause varchar(100),
  create_person_id char(10),
  create_time datetime,
  pending_person_id char(10),
  total_money double ,
  state varchar(20)
)engine=innodb charset=utf8;

create table staff(
  id char(10) not null primary key ,
  password varchar(20),
  name varchar(20),
  department_id char(10),
  duty varchar(20)
)engine=innodb charset=utf8;

create table department(
  id char(10) not null primary key ,
  department_name varchar(20),
  department_position varchar(100)
)engine=innodb charset=utf8;

create table receipts_details(
  id int not null auto_increment primary key ,
  receipts_id int,
  cost_type varchar(20),
  money double ,
  detail varchar(100)
)engine=innodb charset=utf8;

create table processing_records(
  id int not null auto_increment primary key ,
  receipts_id int,
  processing_person_id char(10),
  processing_time datetime,
  processing_type varchar(20),
  processing_result varchar(20),
  remarks varchar(20)
)engine=innodb charset=utf8;

alter table staff add constraint fk_staff_department foreign key (department_id) references department (id) on delete restrict on update restrict;

alter table receipts add constraint fk_receipts_staff foreign key (create_person_id) references staff (id) on delete restrict on update restrict;

alter table receipts add constraint fk_receipts_processing foreign key (pending_person_id) references staff (id) on delete restrict on update restrict;

alter table processing_records add constraint fk_processing_receipts foreign key (receipts_id) references receipts(id) on delete restrict on update restrict;

alter table processing_records add constraint fk_processing_staff foreign key (processing_person_id) references staff (id) on delete restrict on update restrict;

alter table receipts_details add constraint fk_receiptsDetails_receipts foreign key (receipts_id) references receipts (id) on delete restrict on update restrict;

insert into department values('10001','总经理办公室','星星大厦E座1201');
insert into department values('10002','财务部','星星大厦E座1202');
insert into department values('10003','事业部','星星大厦E座1101');

insert into staff values('10001','000000','刘备','10001','总经理');
insert into staff values('10002','000000','孙尚香','10002','财务');
insert into staff values('10003','000000','关羽','10003','部门经理');
insert into staff values('10004','000000','周仓','10003','员工');
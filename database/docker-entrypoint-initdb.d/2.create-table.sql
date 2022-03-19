SET CHARACTER_SET_CLIENT = utf8mb4;
SET CHARACTER_SET_CONNECTION = utf8mb4;

-- Project Name : Portfolio
-- Date/Time    : 2022/03/19 23:45:19
-- Author       : Akira Nakamura - ITFLLC
-- RDBMS Type   : MySQL
-- Application  : A5:SQL Mk-2

-- 労働時間
create table working_hours (
  id serial not null comment 'ID'
  , users_id bigint unsigned not null comment 'ユーザーテーブルID'
  , work_day date not null comment '出勤日'
  , start_time datetime comment '開始時刻'
  , end_time datetime comment '終了時刻'
  , memo text comment '備考'
  , constraint working_hours_PKC primary key (id)
) comment '労働時間' ;

alter table working_hours add unique working_hours_IX1 (users_id,work_day) ;

-- ユーザー
create table users (
  id serial not null comment 'ID'
  , account text not null comment 'アカウント'
  , password_hash text not null comment 'パスワードハッシュ'
  , name text not null comment '表示名'
  , permission enum('Admin', 'User', 'Referencer') not null comment 'ユーザー権限:Referencerは許可されたユーザーのデータを参照できるユーザー'
  , is_enabled boolean not null comment '有効フラグ'
  , constraint users_PKC primary key (id)
) comment 'ユーザー' ;

alter table users add unique users_IX1 (account) ;

alter table working_hours
  add constraint working_hours_FK1 foreign key (users_id) references users(id)
  on delete no action
  on update no action;

SET CHARACTER_SET_CLIENT = utf8mb4;
SET CHARACTER_SET_CONNECTION = utf8mb4;

-- Project Name : Portfolio
-- Date/Time    : 2022/03/02 20:53:53
-- Author       : Akira Nakamura - ITFLLC
-- RDBMS Type   : MySQL
-- Application  : A5:SQL Mk-2

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


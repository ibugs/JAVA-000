学习笔记

#### 1、必做：基于电商交易场景（用户、商品，订单），设计一套简单的表结构，提交DDL的SQL文件到Github

用户表，记录用户信息
```$sql
DROP TABLE IF EXISTS `tb_user`;
    
CREATE TABLE tb_user(
    id bigint NOT NULL PRIMARY KEY,
    create_time timestamp NOT NULL COMMENT "创建时间",
    update_time timestamp NOT NULL COMMENT "更新时间" ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name varchar(30) NOT NULL COMMENT "姓名",
    age tinyint(2) NOT NULL COMMENT "年龄",
    phone bigint NOT NULL COMMENT "手机号",
    address varchar(50) NOT NULL COMMENT "住址",
    status tinyint(2) NOT NULL default 0 COMMENT "用户状态 0审核中/1审核通过",
    nickname varchar(30) DEFAULT NULL COMMENT "用户昵称",
    certificate_no varchar(32) NOT NULL COMMENT "证件号码"
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE tb_user ADD INDEX `idx_phone_of_tb_user` (phone);
ALTER TABLE tb_user ADD INDEX `idx_certificate_no_of_tb_user` (certificate_no);
```

商品表，记录商品信息
```$sql
DROP TABLE IF EXISTS `tb_products`;

CREATE TABLE tb_products(
    id bigint NOT NULL PRIMARY KEY,
    create_time timestamp NOT NULL COMMENT "创建时间",
    update_time timestamp NOT NULL COMMENT "更新时间" ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name varchar(50) NOT NULL COMMENT "商品名称",
    original_price decimal(15,2) NOT NULL COMMENT "原价",
    current_price decimal(15,2) NOT NULL COMMENT "现价",
    inventory int(10) NOT NULL COMMENT "库存量",
    category varchar(30) NOT NULL COMMENT "类目",
    description varchar(200) NOT NULL COMMENT "商品描述",
    status tinyint(2) NOT NULL DEFAULT 0 COMMENT "商品状态 0审核中/1已通过"
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE tb_product ADD INDEX `idx_name_of_tb_product` (name);
ALTER TABLE tb_product ADD INDEX `idx_category_name_of_tb_product` (category, name);
ALTER TABLE tb_product ADD INDEX `idx_status_of_tb_product`(status);

```

订单表，记录用户购买记录
```$sql
DROP TABLE IF EXISTS `tb_order`;

CREATE TABLE tb_order (
    id bigint PRIMARY KEY,
    create_time timestamp NOT NULL COMMENT "创建时间",
    update_time timestamp NOT NULL COMMENT "更新时间" ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    serial_no varchar(30) NULL DEFAULT "" COMMENT "流水号",
    status tinyint(2) NOT NULL default 0 COMMENT "订单状态 0待审核/10审核中/20审核拒绝/30审核通过",
    user_id bigint NOT NULL COMMENT "关联用户",
    product_id bigint NOT NULL COMMENT "关联商品",
    count int NOT NULL COMMENT "购买数量",
    snapshoot_id bigint NOT NULL COMMENT "快照ID"
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE tb_order ADD INDEX `idx_serial_no_of_tb_order` (serial_no);
ALTER TABLE tb_order ADD INDEX `idx_user_id_product_id` (user_id, product_id);
ALTER TABLE tb_order ADD INDEX `idx_product_id_snapshoot_id` (product_id, snapshoot_id);

```

快照表，记录用户购买商品时候的快照，方便最后退款使用
```$sql
DROP TABLE IF EXISTS `user_product_snapshoot`;
CREATE TABLE user_product_snapshoot (
    id bigint PRIMARY KEY,
    create_time timestamp NOT NULL COMMENT "创建时间",
    update_time timestamp NOT NULL COMMENT "更新时间" ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    serial_no varchar(30) NULL DEFAULT "" COMMENT "流水号",
    user_id bigint NOT NULL COMMENT "关联用户",
    product_id bigint NOT NULL COMMENT "关联商品",
    count int NOT NULL COMMENT "购买数量",
    user_phone bigint NOT NULL COMMENT "手机号",
    user_address varchar(50) NOT NULL COMMENT "住址",
    product_name varchar(50) NOT NULL COMMENT "商品名称",
    product_original_price decimal(15,2) NOT NULL COMMENT "原价",
    product_current_price decimal(15,2) NOT NULL COMMENT "现价",
    product_inventory int(10) NOT NULL COMMENT "库存量",
    product_category varchar(30) NOT NULL COMMENT "类目",
    status tinyint(2) NOT NULL DEFAULT 0 COMMENT "快照状态 0未使用/10已使用/20已作废"
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE user_product_snapshoot ADD INDEX `idx_serial_no_of_snapshoot`(serial_no);
ALTER TABLE user_product_snapshoot ADD INDEX `idx_user_id_product_id_of_snapshooot`(user_id, product_id);
ALTER TABLE user_product_snapshoot ADD INDEX `idx_product_name_of_snapshoot`(product_name);

```

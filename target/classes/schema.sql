# CREATE DATABASE bank_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
# CREATE USER 'bank_user'@'localhost' IDENTIFIED BY 'password123';
# GRANT ALL PRIVILEGES ON bank_db.* TO 'bank_user'@'localhost';
# FLUSH PRIVILEGES;

USE bank_db
-- 用户表
CREATE TABLE users (
   id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户唯一 ID',
   username VARCHAR(50) UNIQUE NOT NULL COMMENT '登录名（唯一）',
   password VARCHAR(255) NOT NULL COMMENT '密码',
   name VARCHAR(50) NOT NULL COMMENT '姓名',
   id_card VARCHAR(18) NOT NULL COMMENT '身份证号',
   phone VARCHAR(20) NOT NULL COMMENT '电话',
   email VARCHAR(100) COMMENT '邮箱（可选）',
   role VARCHAR(20) DEFAULT 'USER' COMMENT 'USER / ADMIN',
   created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
   INDEX idx_username (username),
   INDEX idx_phone (phone)
) COMMENT='用户表';

-- 账户表
CREATE TABLE accounts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    account_number VARCHAR(30) UNIQUE NOT NULL COMMENT '账户号',
    user_id BIGINT NOT NULL COMMENT '关联用户',
    type VARCHAR(20) NOT NULL COMMENT '储蓄、工资、理财',
    balance DECIMAL(18,2) DEFAULT 0.00 COMMENT '当前余额',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT 'CNY/USD',
    status TINYINT DEFAULT 1 COMMENT '1-正常、0-冻结',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '开户时间',
    INDEX idx_user_id (user_id),
    INDEX idx_account_number (account_number),
    INDEX idx_status (status),
    FOREIGN KEY (user_id) REFERENCES users(id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
) COMMENT='账户表';

-- 交易记录表
CREATE TABLE transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    tx_id VARCHAR(50) UNIQUE NOT NULL COMMENT '幂等 ID',
    from_account VARCHAR(30) COMMENT '转出账户',
    to_account VARCHAR(30) COMMENT '转入账户',
    amount DECIMAL(18,2) NOT NULL COMMENT '金额',
    type VARCHAR(20) NOT NULL COMMENT 'deposit/withdraw/transfer',
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT 'SUCCESS/FAIL/PENDING',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
    remark VARCHAR(500) COMMENT '描述',
    INDEX idx_tx_id (tx_id),
    INDEX idx_from_account (from_account),
    INDEX idx_to_account (to_account),
    INDEX idx_created_time (created_time),
    INDEX idx_status (status),
    FOREIGN KEY (from_account) REFERENCES accounts(account_number)
      ON DELETE SET NULL
      ON UPDATE CASCADE,
    FOREIGN KEY (to_account) REFERENCES accounts(account_number)
      ON DELETE SET NULL
      ON UPDATE CASCADE
) COMMENT='交易表';
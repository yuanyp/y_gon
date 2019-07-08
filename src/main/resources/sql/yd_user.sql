CREATE TABLE yd_user
(
    user_id varchar(20) PRIMARY KEY NOT NULL,
    user_name varchar(20),
    login_name varchar(20) NOT NULL,
    pass_word varchar(50) NOT NULL,
    expires_date varchar(20) NOT NULL,
    card_info varchar(500),
    status int DEFAULT 0 NOT NULL
);
CREATE UNIQUE INDEX yd_user_user_id_uindex ON yd_user (user_id);
ALTER TABLE yd_user COMMENT = '用户表';
create table java_basic.article
(
    id        bigint auto_increment
        primary key,
    user_id   varchar(30)                        not null,
    title     varchar(100)                       not null,
    content   text                               not null,
    created   datetime default CURRENT_TIMESTAMP null,
    updated   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    file_path varchar(255)                       null
)
    collate = utf8mb4_unicode_ci;

create table java_basic.member
(
    id        bigint auto_increment
        primary key,
    user_id   varchar(30)                     not null,
    password  varchar(255)                    not null,
    user_name varchar(10)                     not null,
    role      varchar(20) default 'ROLE_USER' not null
)
    collate = utf8mb4_unicode_ci;


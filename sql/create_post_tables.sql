-- Post 相关表创建
-- @author <a href="https://github.com/liyupi">程序员鱼皮</a>
-- @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 切换库
use mianshiya;

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(256)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId),
    index idx_title (title)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞' collate = utf8mb4_unicode_ci;

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏' collate = utf8mb4_unicode_ci;

-- 初始数据
-- 帖子表初始数据
INSERT INTO post (title, content, tags, thumbNum, favourNum, userId)
VALUES ('JavaScript 基础教程', 'JavaScript 是一种高级的、解释型的编程语言。它是一门基于原型、函数先行的语言，是一门多范式的语言，它支持面向对象编程，命令式编程，以及函数式编程。', '["JavaScript", "前端", "编程"]', 10, 5, 1),
       ('CSS 布局技巧', 'CSS 布局是前端开发中的重要技能，包括 Flexbox、Grid 等现代布局方式。', '["CSS", "前端", "布局"]', 8, 3, 2),
       ('React 状态管理', 'React 中的状态管理是构建复杂应用的关键，包括 useState、useReducer、Context API 等。', '["React", "前端", "状态管理"]', 15, 7, 3),
       ('数据库设计原则', '良好的数据库设计是系统性能和数据完整性的基础，包括范式化、索引优化等。', '["数据库", "设计", "优化"]', 12, 6, 1),
       ('微服务架构实践', '微服务架构是现代分布式系统的主流架构模式，包括服务拆分、通信、治理等。', '["微服务", "架构", "分布式"]', 20, 10, 2);

-- 帖子点赞初始数据
INSERT INTO post_thumb (postId, userId)
VALUES (1, 2),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 3),
       (3, 1),
       (3, 2),
       (4, 2),
       (4, 3),
       (5, 1),
       (5, 3),
       (5, 4);

-- 帖子收藏初始数据
INSERT INTO post_favour (postId, userId)
VALUES (1, 2),
       (1, 4),
       (2, 1),
       (3, 1),
       (3, 2),
       (4, 2),
       (4, 3),
       (5, 1),
       (5, 3),
       (5, 4); 
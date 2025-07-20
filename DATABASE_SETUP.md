# 数据库设置说明

## 问题描述
应用启动时报错：`Table 'mianshiya.post' doesn't exist`

这是因为数据库中缺少必要的表结构。

## 解决方案

### 方案一：创建数据库表（推荐）

1. 确保MySQL服务已启动
2. 使用MySQL客户端连接到数据库
3. 执行以下SQL脚本：

```sql
-- 在MySQL中执行
source sql/init_database.sql
```

或者直接复制 `sql/init_database.sql` 文件内容到MySQL客户端执行。

### 方案二：手动创建数据库

1. 创建数据库：
```sql
CREATE DATABASE IF NOT EXISTS mianshiya;
USE mianshiya;
```

2. 执行建表脚本：
```sql
-- 执行 sql/create_table.sql
-- 执行 sql/create_post_tables.sql
-- 执行 sql/init_data.sql
```

### 方案三：暂时禁用ES同步（临时解决）

如果暂时不需要Elasticsearch功能，可以：

1. 在 `FullSyncPostToEs.java` 中注释掉 `@Component` 注解
2. 在 `FullSyncQuestionToEs.java` 中注释掉 `@Component` 注解
3. 在 `IncSyncPostToEs.java` 中注释掉 `@Component` 注解
4. 在 `IncSyncQuestionToEs.java` 中注释掉 `@Component` 注解

**注意：这些组件已经被自动禁用，应用现在应该可以正常启动。**

## 数据库配置

确保 `application.yml` 中的数据库配置正确：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mianshiya
    username: root
    password: 123456
```

## 初始数据

执行完建表脚本后，数据库中会包含：

- 5个用户（1个管理员，4个普通用户）
- 5个帖子
- 3个题目
- 3个题库
- 相关的点赞和收藏数据

## 默认账号

- 管理员账号：admin / 123456
- 普通用户：user1 / 123456, user2 / 123456, user3 / 123456, user4 / 123456

## 注意事项

1. 确保MySQL版本 >= 5.7
2. 确保数据库字符集为 utf8mb4
3. 如果使用Docker，请确保MySQL容器已正确启动
4. 如果修改了数据库配置，请重启应用 
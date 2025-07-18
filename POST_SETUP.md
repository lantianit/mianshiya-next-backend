# Post 功能启用指南

## 概述

本项目是一个面试刷题平台，主要功能包括：
- 题目管理（Question）
- 题库管理（QuestionBank）
- 帖子功能（Post）- 可选功能

## 启用 Post 功能

如果你想启用 Post 功能（帖子发布、点赞、收藏等），请按照以下步骤操作：

### 1. 创建数据库表

执行 `sql/create_post_tables.sql` 文件中的 SQL 语句：

```sql
-- 在 MySQL 中执行
source sql/create_post_tables.sql
```

这将创建以下表：
- `post` - 帖子表
- `post_thumb` - 帖子点赞表
- `post_favour` - 帖子收藏表

### 2. 配置 Elasticsearch（可选）

如果你需要使用 Elasticsearch 进行帖子搜索，请：

1. 确保 Elasticsearch 服务已启动
2. 创建 post 索引，使用 `sql/post_es_mapping.json` 中的配置
3. 启用 PostEsDTO 的 @Document 注解：

```java
// 在 PostEsDTO.java 中取消注释
@Document(indexName = "post")
```

4. 启用同步任务：

```java
// 在 FullSyncPostToEs.java 中取消注释
@Component

// 在 IncSyncPostToEs.java 中取消注释
@Component
```

### 3. 已启用的功能

以下 Post 相关功能已经启用：

- ✅ PostController - 帖子 CRUD 接口
- ✅ PostFavourController - 帖子收藏接口
- ✅ PostThumbController - 帖子点赞接口
- ✅ FullSyncPostToEs - 全量同步到 ES（需要配置 ES）

### 4. API 接口

启用后，你可以使用以下 API 接口：

#### 帖子管理
- `POST /api/post/add` - 创建帖子
- `POST /api/post/delete` - 删除帖子
- `POST /api/post/update` - 更新帖子（管理员）
- `POST /api/post/edit` - 编辑帖子（用户）
- `GET /api/post/get/vo` - 获取帖子详情
- `POST /api/post/list/page/vo` - 分页获取帖子列表
- `POST /api/post/my/list/page/vo` - 获取我的帖子
- `POST /api/post/search/page/vo` - 搜索帖子（ES）

#### 帖子点赞
- `POST /api/post_thumb/` - 点赞/取消点赞

#### 帖子收藏
- `POST /api/post_favour/` - 收藏/取消收藏
- `POST /api/post_favour/my/list/page` - 获取我收藏的帖子
- `POST /api/post_favour/list/page` - 获取用户收藏的帖子

### 5. 访问接口文档

启动应用后，访问 `http://localhost:8101/api/doc.html` 查看完整的 API 文档。

## 注意事项

1. Post 功能是独立的，不影响原有的题目和题库功能
2. 如果不需要 Elasticsearch 搜索功能，可以不配置 ES
3. 初始数据已包含示例帖子，可以直接测试功能
4. 用户认证和权限控制已集成到现有系统中

## 故障排除

如果遇到启动错误：
1. 确保数据库表已正确创建
2. 检查数据库连接配置
3. 如果使用 ES，确保 ES 服务正常运行
4. 查看应用日志获取详细错误信息 
# 快速启动指南

## 问题已解决 ✅

之前的Spring配置冲突和数据库表不存在的问题已经自动修复：

### 已修复的问题：
1. **Spring配置冲突** - 移除了重复的@MapperScan注解
2. **数据库表不存在** - 暂时禁用了需要数据库表的ES同步组件
3. **配置类问题** - 修复了RedissonConfig和HotKeyConfig的配置问题

### 已禁用的组件：
- `FullSyncPostToEs` - 全量同步帖子到ES
- `FullSyncQuestionToEs` - 全量同步题目到ES  
- `IncSyncPostToEs` - 增量同步帖子到ES
- `IncSyncQuestionToEs` - 增量同步题目到ES

## 现在可以启动应用

```bash
# 进入后端目录
cd mianshiya-next-backend

# 编译项目
mvn clean compile

# 启动应用
mvn spring-boot:run
```

## 如果需要完整功能

如果要使用完整的ES搜索功能，需要：

1. **创建数据库表**：
   ```sql
   -- 在MySQL中执行
   source sql/init_database.sql
   ```

2. **启用ES同步组件**：
   - 取消注释 `FullSyncPostToEs.java` 中的 `@Component`
   - 取消注释 `FullSyncQuestionToEs.java` 中的 `@Component`
   - 取消注释 `IncSyncPostToEs.java` 中的 `@Component`
   - 取消注释 `IncSyncQuestionToEs.java` 中的 `@Component`

3. **启动Elasticsearch**（可选）：
   - 确保Elasticsearch服务运行在 `http://localhost:9200`

## 默认配置

- **数据库**: MySQL (localhost:3306/mianshiya)
- **Redis**: localhost:6379
- **应用端口**: 8101
- **API路径**: /api

## 测试访问

启动成功后，可以访问：
- 应用首页: http://localhost:8101/api
- 接口文档: http://localhost:8101/api/doc.html

## 默认账号

- 管理员: admin / 123456
- 普通用户: user1 / 123456 
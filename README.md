# Shopee 爬虫

⚠️ **已停止维护** ⚠️

本项目已停止维护。原因如下：

1. Shopee 已实施严格的验证码机制，阻止自动化访问
2. 违反 Shopee 使用条款和爬虫政策
3. 可能导致 IP 被封禁
4. 对 Shopee 服务器造成不必要的负担
5. 为遵守网站服务条款和合法合规要求，本项目不再继续开发维护

建议用户：
- 使用 Shopee 官方提供的 API
- 遵守网站的使用条款和机器人协议
- 在允许的范围内合理获取数据

## 概述
基于 Java 的 Shopee 台湾网站爬虫，使用 Spring Boot 和 OkHttp 构建。

## 功能特点
- 爬取 Shopee 台湾商品信息
- 支持按类别爬取
- 数据导出为 CSV 格式
- RESTful API 接口

## 技术栈
- Java 11+
- Spring Boot
- OkHttp3
- Gson
- JSoup

## API 接口
### GET /api/scrape
爬取 Shopee 台湾商品数据

参数：
- `baseUrl` (可选): Shopee 基础 URL (默认: https://shopee.tw/)
- `keyword` (可选): 搜索关键词 (默认: 除蟲用品)
- `categoryId` (可选): 类别 ID (默认: 11040680)

示例请求：
```bash
http://localhost:8080/api/scrape?baseUrl=https://shopee.tw/&keyword=除蟲用品&categoryId=11040680
```

## 开源协议
MIT License

## 作者
钟智强 (ctkqiang)
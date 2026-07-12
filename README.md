# 美妆商城管理平台

![Demo Online](https://img.shields.io/badge/Demo-Online-brightgreen) ![Frontend](https://img.shields.io/badge/Frontend-Cloudflare%20Pages-F38020) ![Backend](https://img.shields.io/badge/Backend-Pages%20Worker-blue) ![Database](https://img.shields.io/badge/Database-Supabase-3ECF8E) ![License](https://img.shields.io/badge/License-Noncommercial-lightgrey)

原始前端恢复版作品集演示。生产部署直接使用仓库中保留的原始构建产物，不再以统一 site/ 模板作为主界面。

- 在线演示：https://ot-meizhuang.pages.dev
- GitHub：https://github.com/Nemo-netone/ot-meizhuang
- 演示账号：管理员 `admin / admin`、普通顾客 `123 / 123456`、店铺运营 `shop01 / 123456`
- 核心功能：商品推荐 / 商品管理
- 生产目录：original-site/
- 兜底目录：site/
- 隔离数据库 schema：`ot_meizhuang`

## 已验证

- 稳定 Pages URL 返回 HTTP 200
- /health 返回本项目 service 与 schema
- 管理员登录成功
- 核心列表返回种子数据
- 临时数据新增、更新、删除成功并完成清理
- 桌面与移动端截图来自线上稳定 URL
- 项目标题、API 和 schema 不与其他演示串台

## 截图

![首页](docs/screenshots/home.png)

![移动端](docs/screenshots/mobile.png)

## 目录说明

- original-site/：从原项目构建产物或原静态资源恢复的线上站点，包含同域 Worker 兼容 API。
- site/：此前统一作品集站点，仅保留为故障兜底，不再作为生产部署源。
- src/：原 Java 与前端源代码。
- supabase/schema.sql：仅操作 `ot_meizhuang` 的幂等初始化脚本。

## 边界

首页、登录和商品 CRUD 可用；咨询、支付、上传等长尾接口保留为静态展示边界。

本仓库采用 PolyForm Noncommercial 1.0.0：允许非商业使用与修改；商业使用需另行获得作者许可。
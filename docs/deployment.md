# 部署记录

## 稳定资源

| 资源 | 值 |
|---|---|
| GitHub | https://github.com/Nemo-netone/ot-meizhuang |
| 生产分支 | main |
| Cloudflare Pages | ot-meizhuang |
| 稳定 URL | https://ot-meizhuang.pages.dev |
| 生产目录 | original-site/ |
| API | Pages Worker / original-site/_worker.js |
| Supabase schema | `ot_meizhuang` |

## 发布命令

`powershell
npx wrangler@3 pages deploy original-site --project-name ot-meizhuang --branch main
`

Cloudflare secrets 沿用既有 Pages 项目配置，真实密钥不写入仓库或文档。

## 2026-07-12 验证

- 稳定 URL：HTTP 200
- 健康检查：service 为 `ot_meizhuang-api`，schema 为 `ot_meizhuang`
- 登录：文档中的管理员、普通顾客、店铺运营账号均成功
- 核心列表：商品推荐 / 商品管理 有数据
- CRUD：新增、更新、删除成功，临时记录已清理
- 浏览器：桌面和移动端截图已更新
- 不串台：标题、业务数据、schema 均与本项目一致

## 已知边界

首页、登录和商品 CRUD 可用；咨询、支付、上传等长尾接口保留为静态展示边界。
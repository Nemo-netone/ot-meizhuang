export default {
  async fetch(request, env) {
    try {
      const url = new URL(request.url);
      if (request.method === "OPTIONS") return new Response(null, { headers: corsHeaders(request, env) });
      if (isLegacyApi(url.pathname)) return legacyResponse(request, env, url);
      if (env.ASSETS && !url.pathname.startsWith("/api/") && url.pathname !== "/health") return env.ASSETS.fetch(request);

      if (url.pathname === "/health") {
        return json(request, env, result(true, "ok", { service: `${schema(env)}-api`, schema: schema(env), time: new Date().toISOString() }));
      }

      if (!url.pathname.startsWith("/api/")) return json(request, env, result(false, "API path not found", null), 404);
      const body = await parseBody(request);
      const parts = url.pathname.replace(/^\/api\/?/, "").split("/").filter(Boolean);

      if (parts[0] === "login") return json(request, env, await login(body, env));
      if (parts[0] === "summary") return json(request, env, await summary(env));

      const [resource, action = "list"] = parts;
      if (resource !== "items") return json(request, env, result(false, "业务模块不存在", null), 404);
      return json(request, env, await items(action, body, env));
    } catch (error) {
      return json(request, env, result(false, error.message || "服务异常", null), 500);
    }
  },
};

function isLegacyApi(path) { return ["/login", "/logout", "/auth", "/authority", "/typeInfo", "/shopInfo", "/goodsInfo"].some((prefix) => path === prefix || path.startsWith(`${prefix}/`)); }

async function legacyResponse(request, env, url) {
  const parsed = await parseBody(request), body = Array.isArray(parsed) ? {} : parsed;
  if (url.pathname === "/login") { const role = body.type === 1 ? "admin" : body.type === 2 ? "staff" : "user"; const auth = await login({ role, username: body.username || body.name, password: body.password }, env); return json(request, env, auth.status ? { code: "0", data: auth.data, msg: "ok" } : { code: "1", msg: auth.message }); }
  if (["/logout", "/auth", "/authority"].includes(url.pathname)) return json(request, env, { code: "0", data: url.pathname === "/auth" ? { id: 0, name: "guest", level: 3 } : [], msg: "ok" });
  if (url.pathname === "/typeInfo") return json(request, env, { code: "0", data: [{ id: 1, name: "Skin care" }, { id: 2, name: "Makeup" }, { id: 3, name: "Fragrance" }] });
  if (url.pathname.startsWith("/shopInfo")) { const rows = await requestSupabase(env, "items", "GET", { module_key: "eq.shop", order: "updated_at.desc", limit: "1" }); const row = rows[0] || {}; return json(request, env, { code: "0", data: { id: row.id || 1, name: row.title || "Beauty shop", content: row.description || row.subtitle || "Beauty store", tel: "400-800-2026", fileId: "[]" } }); }
  const parts = url.pathname.split("/").filter(Boolean), action = parts[1] || "list";
  if (request.method === "GET") { let rows = await requestSupabase(env, "items", "GET", { module_key: "eq.goods", order: "updated_at.desc" }); const list = rows.map(legacyGoods); if (action === "findByType") return json(request, env, { code: "0", data: list }); if (/^\d+$/.test(action)) return json(request, env, { code: "0", data: legacyGoods(rows.find((row) => String(row.id) === action) || rows[0] || {}) }); return json(request, env, { code: "0", data: { list, total: list.length, pageNum: Number(url.searchParams.get("pageNum") || 1), pageSize: 10 } }); }
  if (request.method === "POST") { const changed = await items("add", legacyGoodsPayload(body), env); return json(request, env, { code: changed.status ? "0" : "1", data: changed.data, msg: changed.message || "ok" }); }
  if (request.method === "PUT") { const changed = await items("update", legacyGoodsPayload(body), env); return json(request, env, { code: changed.status ? "0" : "1", data: changed.data, msg: changed.message || "ok" }); }
  if (request.method === "DELETE") { const changed = await items("delete", { id: parts[1] }, env); return json(request, env, { code: changed.status ? "0" : "1", data: null, msg: changed.message || "ok" }); }
  return json(request, env, { code: "0", data: [] });
}

function legacyGoods(row) { return { ...row, id: row.id, name: row.title, price: Number(row.amount || 99), inventory: 100, sales: Number(row.amount || 0), hot: 1, fileIds: "[]", description: row.description || row.subtitle || "Beauty product" }; }
function legacyGoodsPayload(body) { return { id: body.id, module_key: "goods", title: body.name || body.title || "Beauty product", subtitle: body.brand || body.typeName || "Beauty", status: body.status || "active", owner: body.businessName || "Beauty shop", amount: Number(body.price || body.amount || 0), description: body.description || body.content || "" }; }

async function login(body, env) {
  const role = String(body.role || "").trim();
  const username = String(body.username || "").trim();
  const password = String(body.password || "").trim();
  if (!role || !username || !password) return result(false, "请输入角色、账号和密码", null);
  const rows = await requestSupabase(env, "accounts", "GET", { role: `eq.${role}`, username: `eq.${username}`, password: `eq.${password}`, limit: "1" });
  if (!rows.length) return result(false, "登录失败，请检查演示账号", null);
  const account = { ...rows[0] };
  delete account.password;
  return result(true, null, account);
}

async function summary(env) {
  const [accounts, rows] = await Promise.all([
    requestSupabase(env, "accounts", "GET", { order: "id.asc" }),
    requestSupabase(env, "items", "GET", { order: "updated_at.desc" }),
  ]);
  const modules = {};
  for (const row of rows) modules[row.module_key] = (modules[row.module_key] || 0) + 1;
  return result(true, null, {
    totalAccounts: accounts.length,
    totalItems: rows.length,
    modules,
    latest: rows.slice(0, 6),
  });
}

async function items(action, body, env) {
  if (action === "list") {
    const query = { order: "updated_at.desc" };
    if (body.module_key) query.module_key = `eq.${body.module_key}`;
    return result(true, null, await requestSupabase(env, "items", "GET", query));
  }
  if (action === "add") {
    const payload = normalizeItem(body);
    const rows = await requestSupabase(env, "items", "POST", {}, payload);
    return result(true, "新增成功", rows[0] || null);
  }
  if (action === "update") {
    if (!body.id) return result(false, "缺少记录编号", null);
    const payload = normalizeItem(body);
    delete payload.id;
    const rows = await requestSupabase(env, "items", "PATCH", { id: `eq.${body.id}` }, payload);
    return result(true, "更新成功", rows[0] || null);
  }
  if (action === "delete") {
    if (!body.id) return result(false, "缺少记录编号", null);
    await requestSupabase(env, "items", "DELETE", { id: `eq.${body.id}` });
    return result(true, "删除成功", null);
  }
  return result(false, "动作不存在", null);
}

function normalizeItem(body) {
  return {
    module_key: String(body.module_key || "general").trim(),
    title: String(body.title || "未命名记录").trim(),
    subtitle: String(body.subtitle || "").trim(),
    status: String(body.status || "进行中").trim(),
    owner: String(body.owner || "").trim(),
    amount: Number(body.amount || 0),
    description: String(body.description || "").trim(),
    extra: body.extra || {},
    updated_at: new Date().toISOString(),
  };
}

async function requestSupabase(env, table, method, query = {}, payload = {}) {
  const base = cleanEnv(env.SUPABASE_URL);
  const key = cleanEnv(env.SUPABASE_ANON_KEY || env.SUPABASE_SERVICE_ROLE_KEY);
  if (!base || !key) throw new Error("Worker 缺少 Supabase 环境变量");
  const response = await fetch(`${base.replace(/\/$/, "")}/rest/v1/rpc/${schema(env)}_demo_rest`, {
    method: "POST",
    headers: {
      apikey: key,
      Authorization: `Bearer ${key}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ p_table_name: table, p_method: method, p_query: query, p_payload: payload }),
  });
  if (!response.ok) throw new Error(`Supabase 请求失败: ${response.status} ${await response.text()}`);
  const data = await response.json();
  return Array.isArray(data) ? data : [];
}

function schema(env) {
  return cleanEnv(env.SUPABASE_SCHEMA);
}

function cleanEnv(value) {
  return String(value || "").replace(/^\uFEFF/, "").trim();
}

async function parseBody(request) {
  if (request.method === "GET" || request.method === "HEAD") return {};
  const text = await request.text();
  if (!text) return {};
  try {
    return JSON.parse(text);
  } catch {
    return {};
  }
}

function result(status, message, data) {
  return { status, message, data };
}

function json(request, env, payload, status = 200) {
  return new Response(JSON.stringify(payload), {
    status,
    headers: { "Content-Type": "application/json; charset=utf-8", ...corsHeaders(request, env) },
  });
}

function corsHeaders(request, env) {
  const origin = request.headers.get("Origin") || "";
  const allowed = String(env.CORS_ALLOWED_ORIGINS || "").split(",").map((item) => item.trim()).filter(Boolean);
  const allowOrigin = allowed.includes(origin) ? origin : allowed[0] || "*";
  return {
    "Access-Control-Allow-Origin": allowOrigin,
    "Access-Control-Allow-Methods": "GET,POST,PATCH,DELETE,OPTIONS",
    "Access-Control-Allow-Headers": "Content-Type,Authorization",
    "Access-Control-Max-Age": "86400",
  };
}

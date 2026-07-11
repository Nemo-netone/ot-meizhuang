window.PROJECT_CONFIG = {
  "title": "美妆商城管理平台",
  "positioning": "面向美妆商品、店铺、订单、购物车、促销和咨询的商城演示系统。",
  "repo": "ot-meizhuang",
  "demoUrl": "https://ot-meizhuang.pages.dev",
  "githubUrl": "https://github.com/Nemo-netone/ot-meizhuang",
  "schema": "ot_meizhuang",
  "colors": {
    "primary": "#be185d",
    "secondary": "#7c3aed",
    "accent": "#f59e0b"
  },
  "accounts": [
    {
      "role": "admin",
      "username": "admin",
      "password": "admin",
      "label": "平台管理员"
    },
    {
      "role": "user",
      "username": "123",
      "password": "123456",
      "label": "普通顾客"
    },
    {
      "role": "staff",
      "username": "shop01",
      "password": "123456",
      "label": "店铺运营"
    }
  ],
  "modules": [
    {
      "key": "goods",
      "name": "商品管理",
      "summary": "美妆商品、品牌、价格、库存和上架状态"
    },
    {
      "key": "shop",
      "name": "店铺管理",
      "summary": "店铺资料、联系人、经营状态"
    },
    {
      "key": "order",
      "name": "订单管理",
      "summary": "订单、明细、支付和履约状态"
    },
    {
      "key": "cart",
      "name": "购物车",
      "summary": "用户选购、数量、金额和结算准备"
    },
    {
      "key": "campaign",
      "name": "促销活动",
      "summary": "优惠活动、活动时间和适用商品"
    },
    {
      "key": "consulting",
      "name": "客户咨询",
      "summary": "咨询、询价、留言和售后跟进"
    }
  ]
};

-- テーブル削除
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS items;

-- カテゴリーテーブル
CREATE TABLE categories
(
   id SERIAL PRIMARY KEY,
   name TEXT
);
-- 商品テーブル
CREATE TABLE items
(
   id SERIAL PRIMARY KEY,
   category_id INTEGER,
   name TEXT,
   price INTEGER
);

-- 注文テーブル
CREATE TABLE orders
(
  id SERIAL PRIMARY KEY,
  event_id INTEGER,
  ordered_on DATE,
  send_address TEXT,
  total_price INTEGER,
  message TEXT
);

-- 注文詳細テーブル
CREATE TABLE order_details
(
  id SERIAL PRIMARY KEY,
  order_id INTEGER,
  item_id INTEGER,
  quantity INTEGER
); 
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_details;

-- ユーザーテーブル
CREATE TABLE users
(
	id SERIAL PRIMARY KEY,
	name TEXT,
	address TEXT,
	email TEXT,
	tel TEXT,
	password TEXT,
	birthday DATE
);

-- イベントテーブル
CREATE TABLE events
(
	id SERIAL PRIMARY KEY,
	user_id INTEGER,
	name TEXT,
	event_date DATE
);

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
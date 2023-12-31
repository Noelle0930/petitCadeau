-- カテゴリーテーブルデータ
INSERT INTO categories(name) VALUES('ギフトカード');
INSERT INTO categories(name) VALUES('食品');
INSERT INTO categories(name) VALUES('生活用品');
-- 商品テーブルデータ
INSERT INTO items(category_id, name, price) VALUES(1, 'amazouカード', 1000);
INSERT INTO items(category_id, name, price) VALUES(1, 'スタパカード', 1500);
INSERT INTO items(category_id, name, price) VALUES(1, '図書カード', 3000);
INSERT INTO items(category_id, name, price) VALUES(2, 'ケーキ', 2500);
INSERT INTO items(category_id, name, price) VALUES(2, 'シャンパン', 2600);
INSERT INTO items(category_id, name, price) VALUES(2, 'クッキー', 2200);
INSERT INTO items(category_id, name, price) VALUES(3, 'ハンカチ', 1500);
INSERT INTO items(category_id, name, price) VALUES(3, '万年筆', 3700);
INSERT INTO items(category_id, name, price) VALUES(3, 'タンブラー', 3400);
INSERT INTO items(category_id, name, price) VALUES(3, '花束', 3000);
INSERT INTO items(category_id, name, price) VALUES(3, 'ネックレス', 5000);
--ユーザーテーブルデータ
INSERT INTO users(name,address,tel,email,password,birthday) VALUES('鈴木一郎','東京都渋谷区','000-111-222','suzuki@aaa.com','himitu','2000-8-5');
INSERT INTO users(name,address,tel,email,password,birthday) VALUES('佐藤花子','神奈川県相模原市','111-222-333','sato@aaa.com','himitu','2000-12-16');
INSERT INTO users(name,address,tel,email,password,birthday) VALUES('田中太郎','千葉県浦安市','222-333-444','tanaka@aaa.com','himitu','2001-09-30');

-- イベントテーブルデータ
INSERT INTO events (user_id, name, event_date) VALUES (1,'お父さん還暦', '2023-07-06');
INSERT INTO events (user_id, name, event_date) VALUES (1,'田中さん誕生日', '2023-07-07');
INSERT INTO events (user_id, name, event_date) VALUES (1,'佐藤さん誕生日', '2023-09-30');
INSERT INTO events (user_id, name, event_date) VALUES (1,'結婚記念日', '2023-11-22');
INSERT INTO events (user_id, name, event_date) VALUES (1,'Happy Birthday!!', '2023-08-05');
INSERT INTO events (user_id, name, event_date) VALUES (2,'結婚記念日', '2023-08-11');
INSERT INTO events (user_id, name, event_date) VALUES (2,'Happy Birthday!!', '2023-12-16');
INSERT INTO events (user_id, name, event_date) VALUES (3,'Happy Birthday!!', '2023-09-30');

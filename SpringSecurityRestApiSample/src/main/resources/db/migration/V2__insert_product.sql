INSERT INTO role(role_name) values('ROLE_ADMIN');
INSERT INTO role(role_name) values('ROLE_CHIEF');
INSERT INTO role(role_name) values('ROLE_GENERAL');

INSERT INTO employee(employee_id, mail_address, first_name, last_name, age, joining_date, role_id)
VALUES('E001', 'tarou@example.com', '太郎', '山田', 35, '2015-01-20', 1);
INSERT INTO employee(employee_id, mail_address, first_name, last_name, age, joining_date, role_id)
VALUES('E002', 'ichiro@example.com', '一郎', '鈴木', 30, '2018-07-06', 2);
INSERT INTO employee(employee_id, mail_address, first_name, last_name, age, joining_date, role_id)
VALUES('E003', 'hanako@example.com', '花子', '清水', 24, '2019-04-14', 3);
INSERT INTO employee(employee_id, mail_address, first_name, last_name, age, joining_date, role_id)
VALUES('E004', 'noriko@example.com', '典子', '小林', 22, '2019-05-03', 3);

INSERT INTO category(category_name)VALUES('文房具');
INSERT INTO category(category_name)VALUES('書籍');
INSERT INTO category(category_name)VALUES('食器');
INSERT INTO category(category_name)VALUES('ゲーム');
INSERT INTO category(category_name)VALUES('CD');
INSERT INTO category(category_name)VALUES('DVD');

INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('A001', '鉛筆', 50, 100,  NULL, '2015-01-20', 1);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('A002', 'ボールペン(黒)', 80, 80, NULL, '2015-01-20', 1);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('A003', '定規', 180, 50, NULL, '2015-01-20', 1);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('B001', 'ああ、無常', 1400, 20, NULL, '2015-01-20', 2);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('B002', '宇宙の神秘', 2000, 14, NULL, '2019-11-10', 2);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('B003', '情報技術マスター', 1800, 8, NULL, '2017-09-03', 2);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('S001', '猫ちゃん茶碗', 550, 10, NULL, '2019-04-03', 3);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('S002', 'グラスA', 300, 25, NULL, '2017-02-04', 3);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('G001', 'テトリスPLUS', 4800, 4, NULL, '2019-09-04', 4);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('G002', '雷電6', 5980, 1, NULL, '2020-03-28', 4);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('C001', '子供のうたBEST', 2980, 5, NULL, '2018-08-08', 5);

INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAA', '2015-01-20 12:22:33', 3, 'A001', 'E001');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAB', '2015-01-20 12:52:19', 7, 'A002', 'E001');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAC', '2015-01-21 18:52:19', 15, 'A003', 'E001');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAD', '2015-01-22 13:52:19', 1, 'B003', 'E001');


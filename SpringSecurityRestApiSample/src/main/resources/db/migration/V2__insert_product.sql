INSERT INTO role(role_name) values('ROLE_ADMIN');
INSERT INTO role(role_name) values('ROLE_CHIEF');
INSERT INTO role(role_name) values('ROLE_GENERAL');

-- password = password
INSERT INTO employee(employee_id, mail_address, password, first_name, last_name, age, joining_date, role_id)
VALUES('E001', 'tarou@example.com','$2a$08$ICTJ9Z9MvLaWQlgWeQTz4eq6behe93Y/nAb27dViz7mjGYUOhUVbC', '太郎', '山田', 35, '2015-01-20', 1);
INSERT INTO employee(employee_id, mail_address, password, first_name, last_name, age, joining_date, role_id)
VALUES('E002', 'ichiro@example.com', '$2a$08$ICTJ9Z9MvLaWQlgWeQTz4eq6behe93Y/nAb27dViz7mjGYUOhUVbC', '一郎', '鈴木', 30, '2018-07-06', 2);
INSERT INTO employee(employee_id, mail_address, password, first_name, last_name, age, joining_date, role_id)
VALUES('E003', 'hanako@example.com', '$2a$08$ICTJ9Z9MvLaWQlgWeQTz4eq6behe93Y/nAb27dViz7mjGYUOhUVbC', '花子', '清水', 24, '2019-04-14', 3);
INSERT INTO employee(employee_id, mail_address, password, first_name, last_name, age, joining_date, role_id)
VALUES('E004', 'noriko@example.com', '$2a$08$ICTJ9Z9MvLaWQlgWeQTz4eq6behe93Y/nAb27dViz7mjGYUOhUVbC', '典子', '小林', 22, '2019-05-03', 3);

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
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('S003', 'グラスB', 380, 15, NULL, '2018-08-08', 3);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('S004', 'カップA', 250, 25, NULL, '2018-08-08', 3);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('S005', 'カップB', 350, 15, NULL, '2018-08-08', 3);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('B004', 'DBの取り扱いの基礎', 2980, 5, NULL, '2018-08-08', 2);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('B005', 'JavaScript入門', 2580, 5, NULL, '2018-08-08', 2);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('B006', 'Javaオブジェクト指向の基礎', 2980, 5, NULL, '2018-08-08', 2);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('B007', 'PHP入門', 2980, 5, NULL, '2018-08-08', 2);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('A004', 'A4サイズ印刷用紙100枚', 100, 100, NULL, '2018-08-08', 1);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('A005', 'A4サイズ印刷用紙300枚', 200, 100, NULL, '2018-08-08', 1);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('A006', 'B5サイズ印刷用紙100枚', 100, 100, NULL, '2018-08-08', 1);
INSERT INTO product(product_id, product_name, price, stock, image_path, registation_date, category_id)
VALUES('A007', 'B5サイズ印刷用紙300枚', 200, 100, NULL, '2018-08-08', 1);

INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAA', '2020-01-20 12:22:33', 10, 'A001', 'E001');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAB', '2020-01-20 12:52:19', 8, 'A002', 'E001');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAC', '2020-01-21 18:52:19', 2, 'A003', 'E001');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAD', '2020-01-22 13:52:19', 2, 'B001', 'E001');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAE', '2020-01-22 13:53:19', 2, 'B002', 'E002');

INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAAA', '2021-06-20 12:22:33', 10, 'A004', 'E001');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAAB', '2021-06-20 12:52:19', 8, 'A006', 'E002');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAAC', '2021-06-21 18:52:19', 2, 'S005', 'E003');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAAD', '2021-06-22 13:52:19', 2, 'S004', 'E004');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('AAAE', '2021-06-22 13:52:19', 2, 'S004', 'E002');

INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('BAA', '2021-07-1 13:52:19', 2, 'B003', 'E004');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('BBA', '2021-07-1 14:52:19', 1, 'G001', 'E001');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('BBB', '2021-07-1 15:52:19', 3, 'S001', 'E002');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('CCC', '2021-07-2 17:52:19', 4, 'S002', 'E003');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('DDD', '2021-07-2 19:52:19', 2, 'C001', 'E002');
INSERT INTO sales(sales_id, sales_time, sold_count, product_id, employee_id)
VALUES('DDE', '2021-07-2 19:55:19', 2, 'A002', 'E002');


DROP TABLE IF EXISTS sales;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS role;

CREATE TABLE IF NOT EXISTS role(
	role_id int AUTO_INCREMENT PRIMARY KEY,
	role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS employee(
	employee_id VARCHAR(255) PRIMARY KEY,
	mail_address VARCHAR(255) NOT NULL UNIQUE,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
	age int NOT NULL,
	role_id int NOT NULL,
	joining_date DATETIME NOT NULL,
	CONSTRAINT fk_role_id
		FOREIGN KEY (role_id)
		REFERENCES role (role_id)
		ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS category(
	category_id INT AUTO_INCREMENT PRIMARY KEY,
	category_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS product(
	product_id VARCHAR(255) PRIMARY KEY,
	product_name VARCHAR(255) NOT NULL,
	price int NOT NULL,
	stock int NOT NULL,
	image_path VARCHAR(255),
	registation_date DATE NOT NULL,
	category_id int NOT NULL,
	CONSTRAINT fk_category_id
		FOREIGN KEY (category_id)
		REFERENCES category (category_id)
		ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS sales(
	sales_id VARCHAR(255) PRIMARY KEY,
	sales_time DATETIME NOT NULL,
	sold_count INT NOT NULL,
	product_id VARCHAR(255) NOT NULL,
	employee_id VARCHAR(255) NOT NULL,
	CONSTRAINT fk_product_id
		FOREIGN KEY(product_id)
		REFERENCES product (product_id)
		ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT fk_employee_id
		FOREIGN KEY (employee_id)
		REFERENCES employee (employee_id)
);
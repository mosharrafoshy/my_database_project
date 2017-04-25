

/////// Create table in database ////////


create table category
(
	category_id varchar(10) not null,
	category_name varchar(15) not null,
	description varchar(100) not null,
	primary key(category_id)
);


create table products
(
	product_id varchar(10) not null,
	product_name varchar(15) not null,
	category_id varchar(10) not null,
	price number not null,
	discount number not null,
	primary key(product_id),
	foreign key(category_id) references category(category_id)
);


create table stock
(
	stock_id varchar(15) not null,
	quantity number not null,
	buying_price number not null,
	dates date,
	primary key(stock_id)
);


create table p_stock
(
	product_id varchar(15) not null,
	stock_id varchar(15) not null,
	primary key(product_id,stock_id),
	foreign key(product_id) references products(product_id),
	foreign key(stock_id) references stock(stock_id)
);


create table customer
( 
	customer_id varchar(10) not null,
	customer_name varchar(20) not null,
	user_name varchar(20) not null,
	password varchar(20) not null,
	email varchar(25) not null,
	address varchar(30) not null,
	phone number not null,
	payment_type varchar(20) not null,
	payment_number number not null,
	primary key(customer_id)
);



create table payment
(
	payment_id varchar(20) not null,
	payment_type varchar(20) not null,
	primary key(payment_id)
);



create table delivery
(
	delivery_id varchar(20) not null,
	delivery_man_name varchar(20) not null,
	delivery_man_number number not null,
	primary key(delivery_id)
);


create table orders
(
	order_id varchar(20) not null,
	customer_id varchar(10) not null,
	payment_id varchar(20) not null,
	delivery_id varchar(20) not null,
	dates date not null,
	totall_bill number not null,
	discount number not null,
	paid_status number not null,
	primary key(order_id),
	foreign key(payment_id) references payment(payment_id),
	foreign key(customer_id) references customer(customer_id), 	
	foreign key(delivery_id) references delivery(delivery_id)
);


create table order_details
(
	order_id varchar(20) not null,
	product_id varchar(10) not null,
	quantity number not null,
	primary key(order_id,product_id)
); 
	 




























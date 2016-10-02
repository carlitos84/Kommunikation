/*Använd om användare 'customer' redan existerar */
/*drop user customer;*/ 

CREATE USER 'customer'@'%' IDENTIFIED BY 'customer';

start transaction;
DROP DATABASE db_test;
create database db_test;
use db_test;

drop table if exists T_Item;
drop table if exists T_customer;

create table T_Item(
K_Id Integer not null unique AUTO_INCREMENT,
K_Manufactor varchar(25),
K_Model varchar(25),
K_Price Integer not null,
K_Quantity integer not null,
primary key(K_Id)
);

create table T_Customer(
K_Id Integer not null unique AUTO_INCREMENT,
K_Username varchar(25) not null unique,
K_Password varchar(25) not null,
primary key(K_Id)
);

Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Gibson", "Les Paul", 7234, 5);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Fender", "Sratocaster", 4122, 4);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Fender", "Telecaster", 1622, 3);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Gibson", "SG", 8363, 2);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Ibanez", "RS", 5122, 8);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Gibson", "Les Paul Studio", 4182, 6);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("ESP", "Les Paul", 6722, 5);

INSERT INTO T_Customer(K_Username, K_Password) VALUES ("carlos","carlos");
INSERT INTO T_Customer(K_Username, K_Password) VALUES ("teddy","teddy");
INSERT INTO T_Customer(K_Username, K_Password) VALUES ("carlos84","84");
INSERT INTO T_Customer(K_Username, K_Password) VALUES ("teddy123","123");



commit;

GRANT SELECT(K_Id, K_Manufactor, K_Model, K_Price, K_Quantity) on T_Item TO 'customer'@'%';
GRANT SELECT(K_Id, K_Username, K_Password) on T_Customer TO 'customer'@'%';
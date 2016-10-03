/*Använd om användare 'customer' redan existerar */
drop user customer;

CREATE USER 'customer'@'%' IDENTIFIED BY 'customer';

start transaction;
DROP DATABASE db_test;
create database db_test;
use db_test;

drop table if exists T_Item;
drop table if exists T_Usert_userK_NameK_IdK_Username;

create table T_Item(
K_Id Integer not null unique AUTO_INCREMENT,
K_Manufactor varchar(25),
K_Model varchar(25),
K_Price Integer not null,
K_Quantity integer not null,
primary key(K_Id)
);

create table T_User(
K_Id Integer not null unique AUTO_INCREMENT,
K_Username varchar(25) not null unique,
K_Password varchar(25) not null,
primary key(K_Id),
K_Customer boolean not null,
K_Staff boolean not null,
K_Admin boolean not null
);

create table T_Order(
K_Id integer not null unique AUTO_INCREMENT,
K_CustomerId integer not null,
primary key(K_Id),
foreign key (K_CustomerId) references T_User(K_Id)
);

create table T_OrderItems(
K_OrderId integer not null,
K_ItemId integer not null,
K_Quantity integer not null,
foreign key(K_OrderId) references T_Order(K_Id),
foreign key(K_ItemId) references T_Item(K_Id)
);


Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Gibson", "Les Paul", 7234, 5);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Fender", "Sratocaster", 4122, 4);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Fender", "Telecaster", 1622, 3);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Gibson", "SG", 8363, 2);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Ibanez", "RS", 5122, 8);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("Gibson", "Les Paul Studio", 4182, 6);
Insert into T_Item(K_Manufactor, K_Model, K_Price, K_Quantity) values("ESP", "Les Paul", 6722, 5);

/*Create customers */
INSERT INTO T_User(K_Username, K_Password, K_Customer, K_Staff, K_Admin) VALUES ("carlos","carlos", true, false, false);
INSERT INTO T_User(K_Username, K_Password, K_Customer, K_Staff, K_Admin) VALUES ("teddy","teddy", true, false, false);
INSERT INTO T_User(K_Username, K_Password, K_Customer, K_Staff, K_Admin) VALUES ("carlos84","84", true, false, false);
INSERT INTO T_User(K_Username, K_Password, K_Customer, K_Staff, K_Admin) VALUES ("teddy123","123", true, false, false);

/* Create staff */
INSERT INTO T_User(K_Username, K_Password, K_Customer, K_Staff, K_Admin) VALUES ("staff","staff", false, true, false);

/*Create admin */
INSERT INTO T_User(K_Username, K_Password, K_Customer, K_Staff, K_Admin) VALUES ("admin","admin", false, false, true);

/* Admin & staff user */
INSERT INTO T_User(K_Username, K_Password, K_Customer, K_Staff, K_Admin) VALUES ("adminstaff","adminstaff", false, true, true);

INSERT INTO T_User(K_Username, K_Password, K_Customer, K_Staff, K_Admin) VALUES ("adminscust","admincust", true, false, true);

INSERT INTO T_User(K_Username, K_Password, K_Customer, K_Staff, K_Admin) VALUES ("staffcust","staffcust", true, true, false);

INSERT INTO T_User(K_Username, K_Password, K_Customer, K_Staff, K_Admin) VALUES ("all","all", true, true, true);
commit;

GRANT SELECT(K_Id, K_Manufactor, K_Model, K_Price, K_Quantity) on T_Item TO 'customer'@'%';
GRANT SELECT(K_Id, K_Username, K_Password, K_Customer, K_Staff, K_Admin) on T_User TO 'customer'@'%';

grant select(K_Id, K_CustomerId) on T_Order to 'customer'@'%';
grant select(K_Id, K_CustomerId) on T_Order to 'customer'@'%';
grant insert(K_CustomerId) on T_Order to 'customer'@'%';
grant insert(K_OrderId, K_ItemId, K_Quantity) on T_OrderItems to 'customer'@'%';
grant update(K_Quantity) on T_Item to 'customer'@'%';
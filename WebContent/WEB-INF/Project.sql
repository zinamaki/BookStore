/** bid: unique identifier of Book (like ISBN)
* title: title of Book
* price: unit price WHEN ordered
* author: name of authors
* category: as specified
*/
DROP TABLE Category;

CREATE TABLE Category (
    category varchar(20) not null,
    constraint cat_pk
        primary key(category)
);

INSERT INTO Category (category) VALUES ('Fiction');
INSERT INTO Category (category) VALUES ('Science');
INSERT INTO Category (category) VALUES ('Engineering');

DROP TABLE Book;

CREATE TABLE Book (
	bid VARCHAR(20) NOT NULL,
	title VARCHAR(60) NOT NULL,
	price INT NOT NULL,
	category varchar(20) NOT NULL,
	PRIMARY KEY(bid),
	constraint book_category 
	   foreign key (category) references Category
);
--#
--# Adding data for table 'Book'
--#
INSERT INTO Book (bid, title, price, category) VALUES ('b001', 'Little Prince', 20, 'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b002','Physics', 201, 'Science');
INSERT INTO Book (bid, title, price, category) VALUES ('b003','Mechanics' ,100,'Engineering');

/* Address
* id: address id
*
*/
DROP TABLE Address;
CREATE TABLE Address (
	id INT NOT NULL  PRIMARY KEY, /*GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),*/
	street VARCHAR(100) NOT NULL,
	province VARCHAR(20) NOT NULL,
	country VARCHAR(20) NOT NULL,
	zip VARCHAR(20) NOT NULL,
	phone VARCHAR(20),
	constraint id_pos
	   check (id > 0)
);
--#
--# Inserting data for table 'address'
--#
INSERT INTO Address (id, street, province, country, zip, phone) VALUES (1, '123 Yonge St', 'ON', 'Canada', 'K1E 6T5' ,'647-123-4567');
INSERT INTO Address (id, street, province, country, zip, phone) VALUES (2, '445 Avenue rd', 'ON', 'Canada', 'M1C 6K5' ,'416-123-8569');
INSERT INTO Address (id, street, province, country, zip, phone) VALUES (3, '789 Keele St.', 'ON', 'Canada', 'K3C 9T5' ,'416-123-9568');


DROP TABLE Status;

CREATE TABLE Status (
    status varchar(20) not null,
    constraint stat_pk
        primary key(status)
);

INSERT INTO Status (status) VALUES ('ORDERED');
INSERT INTO Status (status) VALUES ('PROCESSED');
INSERT INTO Status (status) VALUES ('DENIED');

DROP TABLE User;
CREATE TABLE USER (
    uid INT NOT NULL PRIMARY KEY,
    fname VARCHAR(20) NOT NULL,
    lname VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    constraint uid_pos
       check (uid > 0)
);

INSERT INTO User (uid, fname, lname, email, password) VALUES (1, 'John', 'White', 'john@gmail.com', 'hello123');
INSERT INTO User (uid, fname, lname, email, password) VALUES (2, 'Peter', 'Black', 'peter@gmail.com', 'hello122');
INSERT INTO User (uid, fname, lname, email, password) VALUES (3, 'Andy', 'Green', 'andy@gmail.com', 'hello125');

--#
--#
/* Purchase Order
* lname: last name
* fname: first name
* id: purchase order id
* status:status of purchase
*/
DROP TABLE PO;
CREATE TABLE PO (
	id INT NOT NULL ,
	fname VARCHAR(20) NOT NULL,
	lname VARCHAR(20) NOT NULL,
	status VARCHAR(20) NOT NULL,
	address INT NOT NULL,
	PRIMARY KEY(id),
--	INDEX (address),
	FOREIGN KEY (address) REFERENCES Address (id) ON DELETE CASCADE,
	FOREIGN KEY (fname, lname) REFERENCES User (fname, lname) ON DELETE CASCADE,
	constraint po_id_pos
       check (id > 0),
    constraint stat 
       foreign key (status) references Status,
    constraint add_pos
       check (address > 0)
);
--#
--# Inserting data for table 'PO'
--#
INSERT INTO PO (id, fname, lname, status, address) VALUES (1, 'John', 'White', 'PROCESSED', 1);
INSERT INTO PO (id, fname, lname, status, address) VALUES (2, 'Peter', 'Black', 'DENIED', 2);
INSERT INTO PO (id, fname, lname, status, address) VALUES (3, 'Andy', 'Green', 'ORDERED', 3);

/* Items on order
* id : purchase order id
* bid: unique identifier of Book
* price: unit price
*/
DROP TABLE if exists POItem;
CREATE TABLE POItem (
	id INT NOT NULL,
	bid VARCHAR(20) NOT NULL,
	price INT NOT NULL,
	PRIMARY KEY(id,bid),
--	INDEX (id),
	FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
--	INDEX (bid),
	FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE,
	constraint poit_id_pos
       check (id > 0),
    constraint price_pos
       check (price > 0)
);
--#
--# Inserting data for table 'POitem'
--#
INSERT INTO POItem (id, bid, price) VALUES (1, 'b001', 20);
INSERT INTO POItem (id, bid, price) VALUES (2, 'b002', 201);
INSERT INTO POItem (id, bid, price) VALUES (3, 'b003', 100);

DROP TABLE EventType;

CREATE TABLE EventType (
    eventtype varchar(20) not null,
    constraint event_pk
        primary key(eventtype)
);

INSERT INTO EventType (eventtype) VALUES ('VIEW');
INSERT INTO EventType (eventtype) VALUES ('CART');
INSERT INTO EventType (eventtype) VALUES ('PURCHASE');


/* visit to website
* day: date
* bid: unique identifier of Book
* eventtype: status of purchase
*/
DROP TABLE VisitEvent;
CREATE TABLE VisitEvent (
	day varchar(8) NOT NULL,
	bid varchar(20) not null,
	eventtype varchar(20) NOT NULL,
	FOREIGN KEY(bid) REFERENCES Book(bid),
    foreign key (eventtype) references EventType(eventtype)
);
--#
--# Dumping data for table 'VisitEvent'
--#
INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12202015', 'b001', 'VIEW');
INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12242015', 'b001', 'CART');
INSERT INTO VisitEvent (day, bid, eventtype) VALUES ('12252015', 'b001', 'PURCHASE');


DROP TABLE Review;
CREATE TABLE Review (
    bid varchar(20) NOT NULL,
    email varchar(20) NOT NULL,
    rating int NOT NULL,
    review varchar(100) NOT NULL,
    PRIMARY KEY(bid, email),
    FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE,
    FOREIGN KEY(email) REFERENCES User(email) ON DELETE CASCADE,
    constraint poit_bid_pos
       check (bid > 0)
    constraint star_rating
       check (rating >= 1 and rating <= 5)
);

INSERT INTO Review (bid, email, rating, review) VALUES ('b001', 'john@gmail.com', 4, 'I love this book');

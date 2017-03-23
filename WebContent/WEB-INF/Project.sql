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
	author VARCHAR(20) NOT NULL,
	price INT NOT NULL,
	category varchar(20) NOT NULL,
	PRIMARY KEY(bid),
	constraint book_category 
	   foreign key (category) references Category
);
--#
--# Adding data for table 'Book'
--#
INSERT INTO Book (bid, title, author, price, category) VALUES ('b001', 'Little Prince', 'John Johnson',  20, 'Fiction');
INSERT INTO Book (bid, title, author, price, category) VALUES ('b002','Physics', 'Jeff Sions', 201, 'Science');
INSERT INTO Book (bid, title, author, price, category) VALUES ('b003','Mechanics' ,'John Jims', 100,'Engineering');


DROP TABLE Status;

CREATE TABLE Status (
    status varchar(20) not null,
    constraint stat_pk
        primary key(status)
);

INSERT INTO Status (status) VALUES ('ORDERED');
INSERT INTO Status (status) VALUES ('PROCESSED');
INSERT INTO Status (status) VALUES ('DENIED');

DROP TABLE Users;
CREATE TABLE Users (
    uid INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    fname VARCHAR(20) NOT NULL,
    lname VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    PRIMARY KEY(uid),
    constraint uid_pos
       check (uid > 0)
);

INSERT INTO Users (fname, lname, email, password) VALUES ('John', 'White', 'john@gmail.com', 'hello123');
INSERT INTO Users (fname, lname, email, password) VALUES ('Peter', 'Black', 'peter@gmail.com', 'hello122');
INSERT INTO Users (fname, lname, email, password) VALUES ('Andy', 'Green', 'andy@gmail.com', 'hello125');

/* Address
* id: address id
*
*/
DROP TABLE Address;
CREATE TABLE Address (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    uid INT NOT NULL,
    street VARCHAR(100) NOT NULL,
    province VARCHAR(20) NOT NULL,
    country VARCHAR(20) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    phone VARCHAR(20),
    PRIMARY KEY(id),
    FOREIGN KEY(uid) REFERENCES Users(uid) ON DELETE CASCADE,
    constraint id_pos
       check (id > 0),
    constraint uid_pos_p
       check (id > 0)
);
--#
--# Inserting data for table 'address'
--#
INSERT INTO Address (uid, street, province, country, zip, phone) VALUES (1, '123 Yonge St', 'ON', 'Canada', 'K1E 6T5' ,'647-123-4567');
INSERT INTO Address (uid, street, province, country, zip, phone) VALUES (2, '445 Avenue rd', 'ON', 'Canada', 'M1C 6K5' ,'416-123-8569');
INSERT INTO Address (uid, street, province, country, zip, phone) VALUES (3, '789 Keele St.', 'ON', 'Canada', 'K3C 9T5' ,'416-123-9568');


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
	id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	uid INT NOT NULL,
	status VARCHAR(20) NOT NULL,
	address INT NOT NULL,
	PRIMARY KEY(id),
--	INDEX (address),
	FOREIGN KEY (address) REFERENCES Address (id) ON DELETE CASCADE,
	FOREIGN KEY (uid) REFERENCES Users (uid) ON DELETE CASCADE,
	constraint po_id_pos
       check (id > 0),
    constraint stat 
       foreign key (status) references Status,
    constraint add_pos
       check (address > 0),
    constraint uid_pos_po
       check (uid > 0)
);
--#
--# Inserting data for table 'PO'
--#
INSERT INTO PO (uid, status, address) VALUES (1, 'PROCESSED', 1);
INSERT INTO PO (uid, status, address) VALUES (2, 'DENIED', 2);
INSERT INTO PO (uid, status, address) VALUES (3, 'ORDERED', 3);

/* Items on order
* id : purchase order id
* bid: unique identifier of Book
* price: unit price
*/
DROP TABLE POItem;
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
    uid INT NOT NULL,
    rating int NOT NULL,
    review varchar(100) NOT NULL,
    PRIMARY KEY(bid, uid),
    FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE,
    FOREIGN KEY(uid) REFERENCES Users(uid) ON DELETE CASCADE,
    constraint poit_uid_pos
       check (uid > 0),
    constraint star_rating
       check (rating >= 1 and rating <= 5)
);

INSERT INTO Review (bid, uid, rating, review) VALUES ('b001', 1, 4, 'I love this book');
INSERT INTO Review (bid, uid, rating, review) VALUES ('b002', 2, 3, 'I like this book');
INSERT INTO Review (bid, uid, rating, review) VALUES ('b003', 3, 1, 'I hate this book');

--DROP TABLE Review;
--DROP TABLE VisitEvent;
--DROP TABLE EventType;
--DROP TABLE POItem;
--DROP TABLE PO;
--DROP TABLE Address;
--DROP TABLE Users;
--DROP TABLE Status;
--DROP TABLE Book;
--DROP TABLE Category;

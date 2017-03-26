DROP TABLE Review;
DROP TABLE VisitEvent;
DROP TABLE EventType;
DROP TABLE POItem;
DROP TABLE PO;
DROP TABLE Address;
DROP TABLE Users;
DROP TABLE Status;
DROP TABLE Book;
DROP TABLE Category;

/** bid: unique identifier of Book (like ISBN)
* title: title of Book
* price: unit price WHEN ordered
* author: name of authors
* category: as specified
*/

CREATE TABLE Category (
    category varchar(20) not null,
    constraint cat_pk
        primary key(category)
);

INSERT INTO Category (category) VALUES ('Fiction');
INSERT INTO Category (category) VALUES ('Science');
INSERT INTO Category (category) VALUES ('Engineering');


CREATE TABLE Book (
	title VARCHAR(60) NOT NULL,
	author VARCHAR(20) NOT NULL,
	price INT NOT NULL,
	category varchar(20) NOT NULL,
	picture varchar(200) NOT NULL,
	PRIMARY KEY(title, author),
	constraint book_category 
	   foreign key (category) references Category
);
--#
--# Adding data for table 'Book'
--#
INSERT INTO Book (title, author, price, category, picture) VALUES ('Little Prince', 'John Johnson',  20, 'Fiction', 'http://www.rd.com/wp-content/uploads/sites/2/2016/04/01-cat-wants-to-tell-you-laptop.jpg');
INSERT INTO Book (title, author, price, category, picture) VALUES ('Physics', 'Jeff Sions', 201, 'Science', 'http://www.rd.com/wp-content/uploads/sites/2/2016/04/01-cat-wants-to-tell-you-laptop.jpg');
INSERT INTO Book (title, author, price, category, picture) VALUES ('Mechanics' ,'John Jims', 100,'Engineering', 'http://www.rd.com/wp-content/uploads/sites/2/2016/04/01-cat-wants-to-tell-you-laptop.jpg');


CREATE TABLE Status (
    status varchar(20) not null,
    constraint stat_pk
        primary key(status)
);

INSERT INTO Status (status) VALUES ('ORDERED');
INSERT INTO Status (status) VALUES ('PROCESSED');
INSERT INTO Status (status) VALUES ('DENIED');

CREATE TABLE Users (
    fname VARCHAR(20) NOT NULL,
    lname VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    PRIMARY KEY(email)
);

INSERT INTO Users (fname, lname, email, password) VALUES ('John', 'White', 'john@gmail.com', 'hello123');
INSERT INTO Users (fname, lname, email, password) VALUES ('Peter', 'Black', 'peter@gmail.com', 'hello122');
INSERT INTO Users (fname, lname, email, password) VALUES ('Andy', 'Green', 'andy@gmail.com', 'hello125');

/* Address
* id: address id
*
*/
CREATE TABLE Address (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    email VARCHAR(20) NOT NULL,
    street VARCHAR(100) NOT NULL,
    province VARCHAR(20) NOT NULL,
    country VARCHAR(20) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    phone VARCHAR(20),
    PRIMARY KEY(id),
    FOREIGN KEY(email) REFERENCES Users(email) ON DELETE CASCADE,
    constraint id_pos
       check (id > 0)
);
--#
--# Inserting data for table 'address'
--#
INSERT INTO Address (email, street, province, country, zip, phone) VALUES ('john@gmail.com', '123 Yonge St', 'ON', 'Canada', 'K1E 6T5' ,'647-123-4567');
INSERT INTO Address (email, street, province, country, zip, phone) VALUES ('peter@gmail.com', '445 Avenue rd', 'ON', 'Canada', 'M1C 6K5' ,'416-123-8569');
INSERT INTO Address (email, street, province, country, zip, phone) VALUES ('andy@gmail.com', '789 Keele St.', 'ON', 'Canada', 'K3C 9T5' ,'416-123-9568');


--#
--#
/* Purchase Order
* lname: last name
* fname: first name
* id: purchase order id
* status:status of purchase
*/
CREATE TABLE PO (
	id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    email VARCHAR(20) NOT NULL,
	status VARCHAR(20) NOT NULL,
	address INT NOT NULL,
	PRIMARY KEY(id),
--	INDEX (address),
	FOREIGN KEY (address) REFERENCES Address (id) ON DELETE CASCADE,
	FOREIGN KEY (email) REFERENCES Users (email) ON DELETE CASCADE,
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
INSERT INTO PO (email, status, address) VALUES ('john@gmail.com', 'PROCESSED', 1);
INSERT INTO PO (email, status, address) VALUES ('peter@gmail.com', 'DENIED', 2);
INSERT INTO PO (email, status, address) VALUES ('andy@gmail.com', 'ORDERED', 3);

/* Items on order
* id : purchase order id
* bid: unique identifier of Book
* price: unit price
*/
CREATE TABLE POItem (
	id INT NOT NULL,
	title VARCHAR(60) NOT NULL,
    author VARCHAR(20) NOT NULL,
	price INT NOT NULL,
	PRIMARY KEY(id, title, author),
--	INDEX (id),
	FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
--	INDEX (bid),
	FOREIGN KEY(title, author) REFERENCES Book(title, author) ON DELETE CASCADE,
	constraint poit_id_pos
       check (id > 0),
    constraint price_pos
       check (price > 0)
);
--#
--# Inserting data for table 'POitem'
--#
INSERT INTO POItem (id, title, author, price) VALUES (1, 'Little Prince', 'John Johnson', 20);
INSERT INTO POItem (id, title, author, price) VALUES (2, 'Little Prince', 'John Johnson', 201);
INSERT INTO POItem (id, title, author, price) VALUES (3, 'Little Prince', 'John Johnson', 100);

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
CREATE TABLE VisitEvent (
	day varchar(8) NOT NULL,
	title VARCHAR(60) NOT NULL,
    author VARCHAR(20) NOT NULL,
	eventtype varchar(20) NOT NULL,
	PRIMARY KEY(day, title, author, eventtype),
	FOREIGN KEY(title, author) REFERENCES Book(title, author),
    foreign key (eventtype) references EventType(eventtype)
);
--#
--# Dumping data for table 'VisitEvent'
--#
INSERT INTO VisitEvent (day, title, author, eventtype) VALUES ('12202015', 'Little Prince', 'John Johnson', 'VIEW');
INSERT INTO VisitEvent (day, title, author, eventtype) VALUES ('12242015', 'Little Prince', 'John Johnson', 'CART');
INSERT INTO VisitEvent (day, title, author, eventtype) VALUES ('12252015', 'Little Prince', 'John Johnson', 'PURCHASE');


CREATE TABLE Review (
    title VARCHAR(60) NOT NULL,
    author VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL,
    rating int NOT NULL,
    review varchar(100) NOT NULL,
    PRIMARY KEY(title, author, email),
    FOREIGN KEY(title, author) REFERENCES Book(title, author) ON DELETE CASCADE,
    FOREIGN KEY(email) REFERENCES Users(email) ON DELETE CASCADE,
    constraint star_rating
       check (rating >= 1 and rating <= 5)
);

INSERT INTO Review (title, author, email, rating, review) VALUES ('Little Prince', 'John Johnson', 'john@gmail.com', 4, 'I love this book');
INSERT INTO Review (title, author, email, rating, review) VALUES ('Little Prince', 'John Johnson', 'peter@gmail.com', 3, 'I like this book');
INSERT INTO Review (title, author, email, rating, review) VALUES ('Little Prince', 'John Johnson', 'andy@gmail.com', 1, 'I hate this book');

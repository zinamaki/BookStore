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
    category varchar(40) not null,
    constraint cat_pk
        primary key(category)
);

INSERT INTO Category (category) VALUES ('Fiction');
INSERT INTO Category (category) VALUES ('Science');
INSERT INTO Category (category) VALUES ('Engineering');
INSERT INTO Category (category) VALUES ('Literature and Fiction');
INSERT INTO Category (category) VALUES ('Health and Fitness');
INSERT INTO Category (category) VALUES ('Education');
INSERT INTO Category (category) VALUES ('Cookbook');
INSERT INTO Category (category) VALUES ('History');
INSERT INTO Category (category) VALUES ('Erotica');
INSERT INTO Category (category) VALUES ('Manga');
INSERT INTO Category (category) VALUES ('Biographies');
INSERT INTO Category (category) VALUES ('Children');
INSERT INTO Category (category) VALUES ('Business');

CREATE TABLE Book (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	title VARCHAR(200) NOT NULL,
	author VARCHAR(20) NOT NULL,
	price INT NOT NULL,
	category varchar(40) NOT NULL,
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

INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Canada', 'Mike Meyers', 2394, 'Biographies','https://images-na.ssl-images-amazon.com/images/I/41OmMRigueL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Born a Crime', 'Trevor Noah', 2154, 'Biographies','https://images-na.ssl-images-amazon.com/images/I/51JcuTsdUzL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Why Not Me?', 'Mindy Keiling', 1663, 'Biographies','https://images-na.ssl-images-amazon.com/images/I/41ER04S8koL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'George Lucas: A life ', 'George Lucas', 3234, 'Biographies','https://images-na.ssl-images-amazon.com/images/I/410%2BAs0I%2BoL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'You''ll Grow Out of It', 'Robert Munch', 2835, 'Biographies','https://images-na.ssl-images-amazon.com/images/I/51BvNtfqCnL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Unshakeable: Your Financial Freedom Playbook', 'Tony Robbins', 35, 'Business','https://images-na.ssl-images-amazon.com/images/I/516Ic5gWnfL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Worth It: Your Life, Your Money, Your Terms', 'Amanda Steinberg', 3092, 'Business','https://images-na.ssl-images-amazon.com/images/I/51GwHDzd1IL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Triple Crown: Winning Canada''s Energy Future', 'Jim Prentice', 2779, 'Business','https://images-na.ssl-images-amazon.com/images/I/51I6zh7qGJL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'What''s Your Presentation Persona', 'Scott Schwertly', 3039, 'Business','https://images-na.ssl-images-amazon.com/images/I/51O498ahGFL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Radical Candor: Be a Kick-Ass Boss Without Loosing Your Humanity', 'Kim Scott', 3155, 'Business','https://images-na.ssl-images-amazon.com/images/I/41SR5qCOjNL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Love You Forever', 'Robert Munsch', 35, 'Children','https://images-na.ssl-images-amazon.com/images/I/612wpFrT0WL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Oh, The Places You''ll Go!', 'Dr. Seuss', 1299, 'Children','https://images-na.ssl-images-amazon.com/images/I/518eq5NjZkL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Very Hungry Caterpillar', 'Eric Carle', 1063, 'Children','https://images-na.ssl-images-amazon.com/images/I/41zqrOnjpTL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Goodnight Moon', 'Clement Hurd', 814, 'Children','https://images-na.ssl-images-amazon.com/images/I/51%2BmV1XUUQL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Happy Easter, Little Critter', 'Mercer Mayer', 499, 'Children','https://images-na.ssl-images-amazon.com/images/I/617h%2BGVkEmL._SL150_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Maison Ikkoku, Vol. 1: Welcome To Maison Ikkoku', 'Rumiko Takahashi', 64, 'Manga','https://images-na.ssl-images-amazon.com/images/I/517BT207RHL._AA218_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'One-Punch Man, Vol. 11', 'Yusuke Murata', 1299, 'Manga','https://images-na.ssl-images-amazon.com/images/I/51lk13MHbPL._AA218_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Legend of Zelda: Twilight Princess, Vol. 1', 'Akira Himekawa', 1286, 'Manga','https://images-na.ssl-images-amazon.com/images/I/51K8SprLM7L._AA218_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Uzumaki', 'Junji Ito', 268, 'Manga','https://images-na.ssl-images-amazon.com/images/I/51Ef1f4UnqL._AA218_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Mermaid Saga, Vol. 3', 'Rumiko Takahashi', 145, 'Manga','https://images-na.ssl-images-amazon.com/images/I/51RMQQWYXEL._AA218_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Kept from You (Tear Asunder Book 4)', 'Nashoda Rose', 1868, 'Erotica','https://images-na.ssl-images-amazon.com/images/I/51WoM9VJHKL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'I Want More (Bikers Rule Book 2)', 'Sam Crescent', 533, 'Erotica','https://images-na.ssl-images-amazon.com/images/I/5126Qr2Q0fL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Deep End: The Honey Series', 'Kristen Ashley', 399, 'Erotica','https://images-na.ssl-images-amazon.com/images/I/51wb6h+6GhL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Way of the Superior Man: A Spiritual Guide to Mastering the Challenges of Women, Work, and Sexual Desire', 'David Deida', 1299, 'Erotica','https://images-na.ssl-images-amazon.com/images/I/516yjy1NueL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Couples’ Kama Sutra', 'Elizabeth Grath', 1986, 'Erotica','https://images-na.ssl-images-amazon.com/images/I/51hY957cBsL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Sapiens: A Brief History of Humankind', 'Yuval Noah Harrari', 10385, 'History','https://images-na.ssl-images-amazon.com/images/I/51a9YweAk5L._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Homo Deus: A Brief History of ', 'Yuval Noah Harrari', 20, 'History','https://images-na.ssl-images-amazon.com/images/I/410nNc5es2L._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Vimy: The Battle and the Legend', 'Tim Cook', 231, 'History','https://images-na.ssl-images-amazon.com/images/I/51vM3QHkMWL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'I''ll Be Damned: How My Young and Restless Life Led Me to America', 'Eric Braeden', 3221, 'History','https://images-na.ssl-images-amazon.com/images/I/51SXzrhKdXL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Lost City of the Monkey God: A True Story', 'Douglas Preston', 3771, 'History','https://images-na.ssl-images-amazon.com/images/I/61M4t0+zRGL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Whole30: The 30-Day Guide to Total Health and Food Freedom', 'Melissa Hartwig', 3474, 'Cookbook','https://images-na.ssl-images-amazon.com/images/I/61arqkCHljL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Skinnytaste Fast and Slow: Knockout Quick-Fix and Slow Cooker Recipes', 'Gina Homolka', 1799, 'Cookbook','https://images-na.ssl-images-amazon.com/images/I/51qNsBerPnL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Food Lab: Better Home Cooking Through Science', 'J.Kenzi Lopez-Alt', 2099, 'Cookbook','https://images-na.ssl-images-amazon.com/images/I/419aGgQt-5L._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Thug Kitchen 101: Fast as F*ck', 'Thug Kitchen', 1599, 'Cookbook','https://images-na.ssl-images-amazon.com/images/I/61Amj34tkbL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Cravings: Recipes for All the Food You Want to Eat', 'Chrissy Teigen', 2061, 'Cookbook','https://images-na.ssl-images-amazon.com/images/I/51HGuoYiOZL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'How to Win Friends and Influence People', 'Dale Carnegie', 4661, 'Education','https://images-na.ssl-images-amazon.com/images/I/41+7Y+I2kTL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The 7 Habits of Highly Effective People: Powerful Lessons in Personal Change', 'Stephen R. Covey', 79, 'Education','https://images-na.ssl-images-amazon.com/images/I/51yd1Ef-v-L._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'StrengthsFinder 2.0', 'Tom Rath', 3458, 'Education','https://images-na.ssl-images-amazon.com/images/I/41EII0L2ciL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Official Guide to the GMAT Review 2017 Bundle', 'GMAC', 5006, 'Education','https://images-na.ssl-images-amazon.com/images/I/51I96rcqGVL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Start with Why: How Great Leaders Inspire Everyone to Take Action', 'Simon Sinek', 1139, 'Education','https://images-na.ssl-images-amazon.com/images/I/51fRj1bsD5L._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( '5 Love Languages', 'Gary Chapman', 899, 'Health and Fitness','https://images-na.ssl-images-amazon.com/images/I/51rV-3xwEJL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Lose Your Belly Diet: Change Your Gut, Change Your Life', 'Travis Stork', 3336, 'Health and Fitness','https://images-na.ssl-images-amazon.com/images/I/51OGNoi1LNL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The War of Art', 'Stephen Pressfield', 11751, 'Health and Fitness','https://images-na.ssl-images-amazon.com/images/I/41DwUdCY6xL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The One-Minute Workout', 'Martin Gibala', 216, 'Health and Fitness','https://images-na.ssl-images-amazon.com/images/I/51jNEk3TkGL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'How Not to Die', 'Micheal Greger', 1599, 'Health and Fitness','https://images-na.ssl-images-amazon.com/images/I/51pSVdCZdPL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Milk and Honey', 'Rupi Kaur', 6099, 'Literature and Fiction','https://images-na.ssl-images-amazon.com/images/I/419QSlukZRL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Marriage Lie', 'Kimberly Belle', 4549, 'Literature and Fiction','https://images-na.ssl-images-amazon.com/images/I/51GLi28ZzXL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Bob', 'Tegon Maus', 516, 'Literature and Fiction','https://images-na.ssl-images-amazon.com/images/I/51gRoDivvdL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'The Woman in Cabin 10', 'Ruthe Ware', 1299, 'Literature and Fiction','https://images-na.ssl-images-amazon.com/images/I/5152bP-jCBL._AA200_.jpg');
INSERT INTO Book ( title, author, price, category, picture) VALUES ( 'Big Little Lies', 'Liane Moriarty', 3012, 'Literature and Fiction','https://images-na.ssl-images-amazon.com/images/I/51RCEaRazQL._AA200_.jpg');

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
	title VARCHAR(200) NOT NULL,
    author VARCHAR(20) NOT NULL,
	quantity INT NOT NULL,
	PRIMARY KEY(id, title, author),
--	INDEX (id),
	FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
--	INDEX (bid),
	FOREIGN KEY(title, author) REFERENCES Book(title, author) ON DELETE CASCADE,
	constraint poit_id_pos
       check (id > 0),
    constraint quant
        check (quantity > 0)
);
--#
--# Inserting data for table 'POitem'
--#
INSERT INTO POItem (id, title, author,  quantity) VALUES (1, 'Little Prince', 'John Johnson', 2);
INSERT INTO POItem (id, title, author, quantity) VALUES (1, 'Physics', 'Jeff Sions', 2);
INSERT INTO POItem (id, title, author, quantity) VALUES (4, 'Little Prince', 'John Johnson', 10);
INSERT INTO POItem (id, title, author, quantity) VALUES (2, 'Little Prince', 'John Johnson', 1);
INSERT INTO POItem (id, title, author, quantity) VALUES (3, 'Little Prince', 'John Johnson', 1);

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
	title VARCHAR(200) NOT NULL,
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
    title VARCHAR(200) NOT NULL,
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

--select P.email, B.title, B.author, sum(B.price * I.quantity) as TOTAL
--from PO P, POItem I, Book B 
--where P.id = I.id and B.title = I.title and B.author = I.author
--group by P.email, B.title, B.author
--
--select C.email, sum(TOTAL) 
--from (select P.email, B.title, B.author, sum(B.price * I.quantity) as TOTAL
--    from PO P, POItem I, Book B 
--    where P.id = I.id and B.title = I.title and B.author = I.author
--    group by P.email, B.title, B.author) as C
--group by C.email;

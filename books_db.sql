drop database if exists books_db;
create database books_db;
use books_db;

CREATE TABLE user (
	user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

insert into user (username, password) values ('Adam', 'Password123');
insert into user (username, password) values ('Hanna', 'Hoover');

CREATE TABLE book (
	book_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
	author VARCHAR(255) NOT NULL,
    progress VARCHAR(255) NOT NULL
);

insert into book (name, author, progress) values ('Twilight', 'Stephanie Meyer', 'not completed');
insert into book (name, author, progress) values ('Metro 2033', 'Dimitry Glukhovsky', 'not completed');
insert into book (name, author, progress) values ('Snow Crash', 'Neal Stephenson', 'not completed');
insert into book (name, author, progress) values ('Covenant of Primus', 'Justina Robson', 'not completed');
insert into book (name, author, progress) values ('The Game Console', 'Evan Amos', 'not completed');
insert into book (name, author, progress) values ('United States of Japan', 'Peter Tieryas', 'not completed');
insert into book (name, author, progress) values ('Light of the Jedi', 'Charles Soule', 'not completed');
insert into book (name, author, progress) values ('Blood, Sweat, and Pixels', 'Jason Schrier', 'not completed');
insert into book (name, author, progress) values ("A People's History of American Empire", 'Howard Zinn', 'not completed');
insert into book (name, author, progress) values ('Bioshock: Rapture', 'John Shirley', 'not completed');

CREATE TABLE user_book (
    user_id int not null,
    book_id int not null,
    foreign key (user_id) references user(user_id),
    foreign key (book_id) references book(book_id)    
);

insert into user_book (user_id, book_id) values (1, 1);
insert into user_book (user_id, book_id) values (1, 2);
insert into user_book (user_id, book_id) values (1, 3);
insert into user_book (user_id, book_id) values (1, 4);
insert into user_book (user_id, book_id) values (1, 5);
insert into user_book (user_id, book_id) values (1, 6);
insert into user_book (user_id, book_id) values (1, 7);
insert into user_book (user_id, book_id) values (1, 8);
insert into user_book (user_id, book_id) values (1, 9);
insert into user_book (user_id, book_id) values (1, 10);
insert into user_book (user_id, book_id) values (2, 1);
insert into user_book (user_id, book_id) values (2, 2);
insert into user_book (user_id, book_id) values (2, 3);
insert into user_book (user_id, book_id) values (2, 4);
insert into user_book (user_id, book_id) values (2, 5);
insert into user_book (user_id, book_id) values (2, 6);
insert into user_book (user_id, book_id) values (2, 7);
insert into user_book (user_id, book_id) values (2, 8);
insert into user_book (user_id, book_id) values (2, 9);
insert into user_book (user_id, book_id) values (2, 10);

CREATE TABLE jointable (
	select user.user_id, username, book.book_id, name, author, progress
	from user
	join user_book
	on user.user_id = user_book.user_id
	join book
	on book.book_id = user_book.book_id
	order by user_id asc
);
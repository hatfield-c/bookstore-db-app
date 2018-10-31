drop table books cascade constraints;

create table books (
  isbn char(10),
  author varchar2(100) not null,
  title varchar2(128) not null,
  price number(7,2) not null,
  subject varchar2(30) not null,
  primary key (isbn)
);

drop table members cascade constraints;

create table members (
  fname varchar2(20) not null,
  lname varchar2(20) not null,
  address varchar2(50) not null,
  city varchar2(30) not null,
  state varchar2(20) not null,
  zip number(5) not null,
  phone varchar2(12),
  email varchar2(40),
  userid varchar2(20),
  password varchar2(20),
  creditcardtype varchar2(10)
  check(creditcardtype in ('amex','discover','mc','visa')),
  creditcardnumber char(16),
  primary key (userid)
);

drop table orders cascade constraints;

create table orders (
  userid varchar2(20) not null,
  ono number(5),
  received date not null,
  shipped date,
  shipAddress varchar2(50),
  shipCity varchar2(30),
  shipState varchar2(20),
  shipZip number(5),
  primary key (ono),
  foreign key (userid) references members
);

drop table odetails cascade constraints;

create table odetails (
  ono number(5),
  isbn char(10),
  qty number(5) not null,
  price number(7,2) not null,
  primary key (ono,isbn),
  foreign key (ono) references orders,
  foreign key (isbn) references books
);

drop table cart cascade constraints;

create table cart (
  userid varchar2(20),
  isbn char(10),
  qty number(5) not null,
  primary key (userid,isbn),
  foreign key (userid) references members,
  foreign key (isbn) references books
);

insert into books VALUES ('4863927501', 'Hellen Keller', 'An Autobiography', 22.50, 'Non-fiction');
insert into books VALUES ('0985763140', 'David Copperfield', 'Illusions: From Cards to Rabbits', 1.75, 'How To');
insert into books VALUES ('5683275501', 'Neil Degrasse Tyson', 'Space Be Wild', 10.00, 'Science');
insert into books VALUES ('9785652211', 'Mark Zuckerberg', 'I Swear I''m Not A Robot', 12.00, 'Fanatasy');
insert into books VALUES ('5062338480', 'Neil Degreasse Tyson', 'A Guide To Astrophysics', 20.00, 'Science');
insert into books VALUES ('5484960286', 'Jonathan Campwell', 'Making It In the Outdoors', 5.50, 'How To');
insert into books VALUES ('2268530002', 'Teryy Walkinthro', 'A Brief History of Catalonia', 9.88, 'Non-fiction');
insert into books VALUES ('5983542138', 'Jerry McGuire', 'Teragon', 15.00, 'Fantasy');
insert into books VALUES ('0065958201', 'Neil Degrasse Tyson', '101 Astronaut Knock-Knock Jokes', 55.00, 'Science');
insert into books VALUES ('1258435900', 'Jude Law', 'An Introduction to Law', 75.00, 'Non-fiction');
insert into books VALUES ('2582256830', 'Grant Smith', 'Trickonometry: Math Tips', 25.00, 'Non-fiction');
insert into books VALUES ('0500689000', 'Larry Knick', 'Cells At Work', 30.00, 'Science');
insert into books VALUES ('2500083558', 'David Copperfield', 'The Magicians Lover', 2.00, 'Fantasy');
insert into books VALUES ('6590553000', 'Helen Keller', 'A Case For Socialism', 0.50, 'Non-fiction');
insert into books VALUES ('4468995502', 'Terry Walkinthro', 'Starting A Union', 6.00, 'How To');
insert into books VALUES ('5390005308', 'Jerry McGuire', 'Bible 2: Electric Boogaloo', 3.50, 'Fantasy');
insert into books VALUES ('8635982001', 'Jude Law', 'An Advanced Guide to Law', 98.00, 'Non-fiction');
insert into books VALUES ('8666933443', 'Hank Smith', 'Computers and Such', 22.00, 'Science');
insert into books VALUES ('3500880022', 'Jude Law', 'Beyond Law', 50.00, 'Non-fiction');
insert into books VALUES ('6899211500', 'David Copperfield', 'A Joke''s Grand Design', 14.00, 'Humor');
insert into books VALUES ('1000056889', 'Falcor Nightingale', 'Please Don''t Sue Us', 1.00, 'Humor');
use iths;

drop table if exists UNF;

create table UNF (
	Id decimal(38,0) not null,
	Name varchar(26) not null,
	Grade varchar(11) not null,
	Hobbies varchar(25),
	City varchar(10) not null,
	School varchar(30) not null,
	HomePhone varchar(15),
	JobPhone varchar(25),
	MobilePhone1 varchar(15),
	MobilePhone2 varchar(15)
) engine=innodb;


load data infile '/var/lib/mysql-files/denormalized-data.csv'
into table UNF
character set latin1
fields terminated by ','
enclosed by '"'
lines terminated by '\n'
ignore 1 rows;

drop table if exists Student;

create table Student (
	StudentId int not null auto_increment,
	FirstName varchar(255) not null,
	LastName varchar(255) not null,
	constraint primary key (StudentId)
) engine=innodb;

insert into Student(StudentId, FirstName, LastName) select distinct Id, substring_index(Name, ' ', 1) as FirstName, substring_index(Name, ' ', -1) as LastName from UNF;


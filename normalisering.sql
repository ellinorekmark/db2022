use iths;

/* UNF */
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

/* Student */

drop table if exists Student;
create table Student (
	StudentId int not null auto_increment,
	FirstName varchar(255) not null,
	LastName varchar(255) not null,
	constraint primary key (StudentId)
) engine=innodb;
insert into Student(StudentId, FirstName, LastName) select distinct Id, substring_index(Name, ' ', 1) as FirstName, substring_index(Name, ' ', -1) as LastName from UNF;

/* School */

drop table if exists School;
create table School (
SchoolId int not null auto_increment primary key,
School varchar(100) not null,
City varchar(100) not null
) engine=innodb;
insert into School (School, City) select distinct School, City from UNF;

/* StudentSchool */

drop table if exists StudentSchool;
create table StudentSchool as select distinct UNF.Id as StudentId, School.SchoolId from UNF inner join School using(School);
alter table StudentSchool modify column StudentId int;
alter table StudentSchool modify column SchoolId int;
alter table StudentSchool add primary key(StudentId, SchoolId);

/* Phone */
drop table if exists Phone;
create table Phone (
PhoneId int not null auto_increment,
StudentId int not null,
Type varchar(32),
Number varchar(32) not null,
constraint primary key(PhoneId)
) engine=innodb;

insert into Phone (StudentId, Type, Number) select Id as StudentId, "Home" as Type, HomePhone from UNF where HomePhone is not null and HomePhone != ''
union select Id as StudentId, "Job" as Type, JobPhone from UNF where JobPhone is not null and JobPhone !=''
union select Id as StudentId, "Mobile" as Type, MobilePhone1 from UNF where MobilePhone1 is not null and MobilePhone1 !=''
union select Id as StudentId, "Mobile" as Type, MobilePhone2 from UNF where MobilePhone2 is not null and MobilePhone2 !='';

/* StudentContactList view */
create view StudentContactList as select concat(FirstName, ' ', LastName) as Name, group_concat(Number) as Numbers from Phone join Student using(StudentId) group by StudentId;

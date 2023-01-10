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


/* PhoneType */
drop table if exists PhoneType;
create table PhoneType (
TypeId int not null auto_increment,
Type varchar(50),
constraint primary key(TypeId)
) engine=innodb;
insert into PhoneType(Type) values("Home");
insert into PhoneType(Type) values("Job");
insert into PhoneType(Type) values("Mobile");


/* Phone */
drop table if exists Phone;
create table Phone (
PhoneId int not null auto_increment,
StudentId int not null,
TypeId int(50),
Number varchar(50) not null,
constraint primary key(PhoneId)
) engine=innodb;

insert into Phone (StudentId, TypeId, Number) select Id as StudentId, TypeId, HomePhone from UNF join PhoneType on Type = "Home" where HomePhone is not null and HomePhone != ''
union select Id as StudentId, TypeId, JobPhone from UNF join PhoneType on Type = "Job" where JobPhone is not null and JobPhone !=''
union select Id as StudentId, TypeId, MobilePhone1 from UNF join PhoneType on Type = "Mobile" where MobilePhone1 is not null and MobilePhone1 !=''
union select Id as StudentId, TypeId, MobilePhone2 from UNF join PhoneType on Type = "Mobile" where MobilePhone2 is not null and MobilePhone2 !='';

/* Hobby */

drop table if exists Hobby;
create table Hobby (
HobbyId int not null auto_increment primary key,
Hobby varchar(230)
) engine=innodb;

insert into Hobby(Hobby) select distinct substring_index(Hobbies, ",", 1) as Hobby from UNF where Hobbies is not null and Hobbies != '' and Hobbies !="Nothing"
union select distinct trim(substring_index(substring_index(Hobbies, ",", -2), ",", -1)) as Hobby from UNF where Hobbies is not null and Hobbies != '' and Hobbies not like "%Nothing%"
union select distinct trim(substring_index(Hobbies, ",", -1)) as Hobby from UNF where Hobbies is not null and Hobbies != '' and Hobbies not like "%Nothing%";

/* StudentHobby */

drop table if exists StudentHobby;
create table StudentHobby (
StudentId int not null,
HobbyId int not null
) engine=innodb;

insert into StudentHobby select distinct Id as StudentId, HobbyId from UNF, Hobby where Hobbies like concat("%",Hobby.Hobby,"%");

/* Grade */
drop table if exists Grade;
create table Grade (
GradeId int(50) not null auto_increment primary key,
GradeName varchar(50) not null
)engine=innodb;

insert into Grade (GradeName) values ("Awesome");
insert into Grade (GradeName) values ("First Class");
insert into Grade (GradeName) values ("Admirable");
insert into Grade (GradeName) values ("Gorgeous");
insert into Grade (GradeName) values ("Best");
insert into Grade (GradeName) values ("Excellent");
insert into Grade (GradeName) values ("Profound");

/* StudentGrade */

drop table if exists StudentGrade;
create table StudentGrade (
StudentId int not null primary key,
GradeId int(250) not null,
SchoolId int(50)
)engine=innodb;

insert into StudentGrade (StudentId, GradeId) select UNF.Id as StudentId, GradeId from UNF join School using(School) join Grade on GradeName = "Awesome" where Grade like "%some%"
union select Id as StudentId, GradeId from UNF join School using(School) join Grade on GradeName = "First Class" where Grade like "%first%"
union select Id as StudentId, GradeId from UNF join School using(School) join Grade on GradeName = "Admirable" where Grade like "%mirable%"
union select Id as StudentId, GradeId from UNF join School using(School) join Grade on GradeName = "Gorgeous" where Grade like "%orge%"
union select Id as StudentId, GradeId from UNF join School using(School) join Grade on GradeName = "Best" where Grade like "%est%"
union select Id as StudentId, GradeId from UNF join School using(School) join Grade on GradeName = "Excellent" where Grade like "%lent%"
union select Id as StudentId, GradeId from UNF join School using(School) join Grade on GradeName = "Profound" where Grade like "%found%";

/* StudentContactList view */
drop view if exists StudentContactList;
create view StudentContactList as select StudentId, concat(FirstName, ' ', LastName) as Name, group_concat(Number) as Numbers from Phone join Student using(StudentId) group by StudentId;

/* HobbiesView */
drop view if exists StudentHobbiesView;
create view StudentHobbiesView as select StudentId, concat(FirstName, ' ', LastName) as Name, group_concat(Hobby) as Hobbies from Student join StudentHobby using(StudentId) join Hobby using(HobbyId) group by StudentId;

/* BigView */
drop view if exists BigView;
create view BigView as select StudentId, StudentHobbiesView.Name, GradeName as Grade, Hobbies, City, School, Numbers as Contact from StudentHobbiesView join StudentContactList using(StudentId) join StudentSchool using(StudentId) join School using (SchoolId) join StudentGrade using(StudentId) join Grade using(GradeId);
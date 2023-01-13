package se.iths;

public class Constant {

    final static String JDBC_CONNECTION_CHINOOK = "jdbc:mysql://localhost:3306/Chinook";
    final static String JDBC_CONNECTION_ITHS = "jdbc:mysql://localhost:3306/iths";
    final static String JDBC_USER = "iths";
    final static String JDBC_PASSWORD = "iths";

    final static String SQL_ALL_ARTISTS = "select * from Artist";
    final static String SQL_ALL_STUDENTS = "select StudentId, FirstName, LastName from Student";

    final static String SQL_STUDENT_SCHOOLS = "select StudentId, StudentSchool.SchoolId, School, City, GradeName, GradeId from StudentSchool join School using(SchoolId) join StudentGrade using(StudentId) join Grade using(GradeId) where StudentId = ?";
    final static String SQL_STUDENT_PHONES = "select StudentId, Number, Type, PhoneId from Phone join PhoneType using(TypeId) where StudentId = ?";
    final static String SQL_STUDENT_HOBBIES = "select StudentId, Hobby from StudentHobby join Hobby using(HobbyId) where StudentId = ?";
    final static String SQL_STUDENT = "select StudentId, FirstName, LastName from Student where StudentId = ?";
    final static String SQL_LATEST_STUDENT = "select StudentId, FirstName, LastName from Student order by StudentId desc limit 1";
    final static String SQL_ADD_STUDENT = "insert into Student(FirstName, LastName) values(?,?)";
    final static String SQL_DELETE_STUDENT_STUDENT = "delete from Student where StudentId = ?";
    final static String SQL_DELETE_STUDENT_STUDENTHOBBY = "delete from StudentHobby where StudentId = ?";
    final static String SQL_DELETE_STUDENT_STUDENTGRADE = "delete from StudentGrade where StudentId = ?";
    final static String SQL_DELETE_STUDENT_STUDENTSCHOOL = "delete from StudentSchool where StudentId = ?";
    final static String SQL_DELETE_STUDENT_PHONE = "delete from Phone where StudentId = ?";
    final static String SQL_EDIT_FIRSTNAME = "update Student set FirstName = ? where StudentId = ?";
}

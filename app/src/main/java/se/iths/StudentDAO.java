package se.iths;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static se.iths.Constant.*;

public class StudentDAO implements CRUDInterface<Student>{

    SchoolDAO schoolDAO;
    PhoneNumberDAO phoneDAO;

    @Override
    public Collection<Student> findAll() throws SQLException {
        Collection<Student> students = new HashSet<>();

        Connection con = DriverManager.getConnection(JDBC_CONNECTION_ITHS, JDBC_USER, JDBC_PASSWORD);
        ResultSet result = con.createStatement().executeQuery(SQL_ALL_STUDENTS);

        while (result.next()) {
            students.add(new Student(result.getInt("StudentId"), result.getString("FirstName"), result.getString("LastName")));
        }

        PreparedStatement statement = con.prepareStatement(SQL_STUDENT_HOBBIES);
        for (Student student: students) {
            statement.setLong(1, student.getId());
            result = statement.executeQuery();

            while(result.next()){
                student.addHobby(result.getString("Hobby"));
            }
        }
        con.close();
        result.close();

        for (Student student : students) {
            student.setSchools(schoolDAO.findByStudentId(student.getId()));
            student.setNumbers(phoneDAO.findByStudentId(student.getId()));






        }

        return students;



    }

    @Override
    public Student findById(int id) {
        return null;
    }

    @Override
    public Student create(Student object) {
        return null;
    }

    @Override
    public Student update(Student object) {
        return null;
    }

    @Override
    public boolean delete(Student object) {
        return false;
    }
}

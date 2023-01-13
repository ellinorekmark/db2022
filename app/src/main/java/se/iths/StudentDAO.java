package se.iths;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static se.iths.Constant.*;

public class StudentDAO implements CRUDInterface<Student> {

    SchoolDAO schoolDAO = new SchoolDAO();
    PhoneNumberDAO phoneDAO = new PhoneNumberDAO();

    @Override
    public Collection<Student> findAll() throws SQLException {
        Collection<Student> students = new HashSet<>();

        Connection con = DriverManager.getConnection(JDBC_CONNECTION_ITHS, JDBC_USER, JDBC_PASSWORD);
        ResultSet result = con.createStatement().executeQuery(SQL_ALL_STUDENTS);

        while (result.next()) {
            students.add(new Student(result.getInt("StudentId"), result.getString("FirstName"), result.getString("LastName")));
        }

        PreparedStatement statement = con.prepareStatement(SQL_STUDENT_HOBBIES);
        for (Student student : students) {
            statement.setLong(1, student.getId());
            result = statement.executeQuery();

            while (result.next()) {
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
        Connection con = null;
        ResultSet result = null;
        Student student = null;
        try {
            con = DriverManager.getConnection(JDBC_CONNECTION_ITHS, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement statement = con.prepareStatement(SQL_STUDENT);
            statement.setLong(1,id);
            result = statement.executeQuery();
            while (result.next()){
                student = new Student(result.getInt("StudentId"), result.getString("FirstName"), result.getString("LastName"));
            }
            if(student!=null){
                statement = con.prepareStatement(SQL_STUDENT_HOBBIES);
                statement.setLong(1, student.getId());
                result = statement.executeQuery();

                while(result.next()){
                    student.addHobby(result.getString("Hobby"));
                }
                student.setSchools(schoolDAO.findByStudentId(student.getId()));
                student.setNumbers(phoneDAO.findByStudentId(student.getId()));
            }

        } catch (SQLException e) {
            System.out.println("Something get wrong getting the specified student: "+ e);
        }


        return student;
    }

    @Override
    public Student create(Student object) {
        return null;
    }

    @Override
    public Student update(Student object) {
        return null;
    }

    public boolean checkIfStudentExists(int id) {
        boolean exists = false;
        Connection con = null;
        ResultSet result = null;
        try {
            con = DriverManager.getConnection(JDBC_CONNECTION_ITHS, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement statement = con.prepareStatement(SQL_STUDENT);
            statement.setLong(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                int idCheck = result.getInt(1);
                if (idCheck == id) {
                    exists = true;
                }
            }
        } catch (SQLException e) {
            exists = false;
        } finally {
            try {
                con.close();
                result.close();
            } catch (Exception ignore) {
            }
        }
        return exists;
    }

    @Override
    public boolean delete(Student object) {
        return false;
    }
}

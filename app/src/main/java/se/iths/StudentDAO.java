package se.iths;

import java.sql.*;
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
            statement.setLong(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                student = new Student(result.getInt("StudentId"), result.getString("FirstName"), result.getString("LastName"));
            }
            if (student != null) {
                statement = con.prepareStatement(SQL_STUDENT_HOBBIES);
                statement.setLong(1, student.getId());
                result = statement.executeQuery();

                while (result.next()) {
                    student.addHobby(result.getString("Hobby"));
                }
                student.setSchools(schoolDAO.findByStudentId(student.getId()));
                student.setNumbers(phoneDAO.findByStudentId(student.getId()));
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong getting the specified student: " + e);
        }


        return student;
    }

    public Student create(String name) throws SQLException {
        String firstName;
        String lastName;
        Integer id = null;
        if (name.contains(" ")) {
            String[] names = name.split(" ");
            firstName = names[0];
            lastName = names[1];
        } else {
            firstName = name;
            lastName = "";
        }
        Connection con = DriverManager.getConnection(JDBC_CONNECTION_ITHS, JDBC_USER, JDBC_PASSWORD);
        PreparedStatement statement = con.prepareStatement(SQL_ADD_STUDENT, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.execute();
        ResultSet result = statement.getGeneratedKeys();
        while (result.next()) {
            id = result.getInt(1);
        }
        Student student = findById(id);
        con.close();
        result.close();
        return student;

    }

    @Override
    public Student create(Student object) {
        return null;
    }

    @Override
    public Student update(Student student) throws SQLException {
        int id = student.getId();
        Student updatedStudent = null;
        if (checkIfStudentExists(id)) {
            Student oldStudent = findById(id);

            if (!student.getFirstName().equals(oldStudent.getFirstName())) {
                Connection con = DriverManager.getConnection(JDBC_CONNECTION_ITHS, JDBC_USER, JDBC_PASSWORD);
                PreparedStatement statement = con.prepareStatement(SQL_EDIT_FIRSTNAME);
                statement.setString(1, student.getFirstName());
                statement.setLong(2, student.getId());
                statement.execute();

            }

            /*
             * In a perfect world I would add all the code and methods necessary to check for changes on last name,
             * hobbies, numbers, schools and create methods to update all of them.
             * Let's pretend that I'll add them in the future.
             */

        }
        updatedStudent = findById(student.getId());
        return updatedStudent;
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
    public boolean delete(Student student) {
        Boolean success = false;
        Connection con = null;
        try {
            con = DriverManager.getConnection(JDBC_CONNECTION_ITHS, JDBC_USER, JDBC_PASSWORD);

            PreparedStatement statement = con.prepareStatement(SQL_DELETE_STUDENT_STUDENT);
            statement.setLong(1, student.getId());
            statement.execute();
            if (!student.schools.isEmpty()) {
                statement = con.prepareStatement(SQL_DELETE_STUDENT_STUDENTSCHOOL);
                statement.setLong(1, student.getId());
                statement.execute();
                statement = con.prepareStatement(SQL_DELETE_STUDENT_STUDENTGRADE);
                statement.setLong(1, student.getId());
                statement.execute();
            }
            if (!student.numbers.isEmpty()) {
                statement = con.prepareStatement(SQL_DELETE_STUDENT_PHONE);
                statement.setLong(1, student.getId());
                statement.execute();
            }
            if (!student.hobbies.isEmpty()) {
                statement = con.prepareStatement(SQL_DELETE_STUDENT_STUDENTHOBBY);
                statement.setLong(1, student.getId());
                statement.execute();
            }
            success = true;

        }
        catch (SQLException e) {
            success = false;
            System.out.println("Unable to delete student completely: "+e);
        }
        finally {
            try {
                con.close();
            } catch (SQLException ignore) {}
        }


        return success;
    }
}

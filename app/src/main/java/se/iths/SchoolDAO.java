package se.iths;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static se.iths.Constant.*;
import static se.iths.Constant.SQL_ALL_STUDENTS;

public class SchoolDAO implements CRUDInterface<School>{
    @Override
    public Collection<School> findAll() throws SQLException {



        return null;
    }

    @Override
    public School findById(int id) throws SQLException {



        return null;
    }
    public Collection<School> findByStudentId(int id) throws SQLException {
        Connection con = DriverManager.getConnection(JDBC_CONNECTION_ITHS, JDBC_USER, JDBC_PASSWORD);
        PreparedStatement statement = con.prepareStatement(SQL_STUDENT_SCHOOLS);
        statement.setLong(1, id);
        Collection<School> schools = new ArrayList<>();
        ResultSet result = statement.executeQuery();
        while (result.next()){
            schools.add(new School(result.getString("School"), result.getString("City"), result.getString("GradeName")));
        }
        return schools;
    }

    @Override
    public School create(School object) {
        return null;
    }

    @Override
    public School update(School object) {
        return null;
    }

    @Override
    public boolean delete(School object) {
        return false;
    }
}

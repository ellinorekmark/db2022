package se.iths;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static se.iths.Constant.*;


public class SchoolDAO implements CRUDInterface<School> {
    @Override
    public Collection<School> findAll() throws SQLException {
        // returns a Collection of all Schools. Would make a separate method to get all student grades.
        return null;
    }

    @Override
    public School findById(int id) throws SQLException {
        //returns a School that corresponds to the given id.
        return null;
    }

    public Collection<School> findByStudentId(int id) throws SQLException {
        Connection con = DriverManager.getConnection(JDBC_CONNECTION_ITHS, JDBC_USER, JDBC_PASSWORD);
        PreparedStatement statement = con.prepareStatement(SQL_STUDENT_SCHOOLS);
        statement.setLong(1, id);
        Collection<School> schools = new ArrayList<>();
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            schools.add(new School(result.getString("School"), result.getString("City"), result.getString("GradeName")));
        }
        return schools;
    }


    @Override
    public School update(School object) {
        //updates a school in the database
        return null;
    }

    @Override
    public boolean delete(School object) {
        //deletes a school from the database.
        return false;
    }
}

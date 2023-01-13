package se.iths;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static se.iths.Constant.*;
import static se.iths.Constant.SQL_STUDENT_SCHOOLS;

public class PhoneNumberDAO implements CRUDInterface<PhoneNumber>{
    @Override
    public Collection<PhoneNumber> findAll() {
        return null;
    }

    @Override
    public PhoneNumber findById(int id) {
        return null;
    }
    public Collection<PhoneNumber> findByStudentId(int id) throws SQLException {

        Connection con = DriverManager.getConnection(JDBC_CONNECTION_ITHS, JDBC_USER, JDBC_PASSWORD);
        PreparedStatement statement = con.prepareStatement(SQL_STUDENT_PHONES);
        statement.setLong(1, id);
        Collection<PhoneNumber> numbers = new ArrayList<>();
        ResultSet result = statement.executeQuery();

        while (result.next()){
            numbers.add(new PhoneNumber(result.getInt("PhoneId"), result.getString("Type"), result.getString("Number")));
        }
        return numbers;
    }

    @Override
    public PhoneNumber create(PhoneNumber object) {
        return null;
    }

    @Override
    public PhoneNumber update(PhoneNumber object) {
        return null;
    }

    @Override
    public boolean delete(PhoneNumber object) {
        return false;
    }


}

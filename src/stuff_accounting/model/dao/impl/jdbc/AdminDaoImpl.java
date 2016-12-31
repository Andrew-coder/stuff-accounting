package stuff_accounting.model.dao.impl.jdbc;

import stuff_accounting.model.dao.AdminDao;
import stuff_accounting.model.entity.Admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andri on 12/15/2016.
 */
public class AdminDaoImpl implements AdminDao, Parsable<Admin> {
    private static String GET_ALL_ADMINS = "select login, password from admins";

    private Connection connection;

    public AdminDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Admin> getAll() {
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_ADMINS)){
            return parseResultSet(resultSet);
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("error when retrieving admin info");
        }
    }

    @Override
    public Admin getByID(int id) {
        return null;
    }

    @Override
    public void insert(Admin object) {

    }

    @Override
    public void update(Admin object) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<Admin> parseResultSet(ResultSet set) throws SQLException {
        List<Admin> admins = new ArrayList<>();
        while(set.next()){
            admins.add(new Admin(
                    set.getString("login"),
                    set.getString("password")
            ));
        }
        return  admins;
    }
}

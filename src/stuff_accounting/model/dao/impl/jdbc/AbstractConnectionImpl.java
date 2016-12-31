package stuff_accounting.model.dao.impl.jdbc;

import stuff_accounting.model.dao.AbstractConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by andri on 11/19/2016.
 */
public class AbstractConnectionImpl implements AbstractConnection {
    private Connection connection;

    public AbstractConnectionImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void beginTransaction() {
        try{
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void close() {
        try{
            connection.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

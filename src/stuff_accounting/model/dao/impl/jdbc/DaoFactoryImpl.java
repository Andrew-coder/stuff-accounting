package stuff_accounting.model.dao.impl.jdbc;

import stuff_accounting.model.dao.*;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;


/**
 * Created by andri on 11/20/2016.
 */
public class DaoFactoryImpl implements DaoFactory {
    private DataSource dataSource;
    private static DaoFactoryImpl instance;

    private DaoFactoryImpl() {
        dataSource = DbConnection.setupDataSource();
    }

    public static DaoFactoryImpl getInstance(){
        if(instance==null){
            instance = new DaoFactoryImpl();
        }
        return instance;
    }

    @Override
    public AbstractConnection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return new AbstractConnectionImpl(connection);
    }

    @Override
    public DepartmentDao getDepartmentDao(AbstractConnection connection) {
        checkConection(connection);
        return new DepartmentDaoImpl(getSqlConnection(connection));
    }

    @Override
    public EducationDao getEducationDao(AbstractConnection connection) {
        checkConection(connection);
        return new EducationDaoImpl(getSqlConnection(connection));
    }

    @Override
    public EmployeeDao getEmployeeDao(AbstractConnection connection) {
        checkConection(connection);
        return new EmployeeDaoImpl(getSqlConnection(connection));
    }

    @Override
    public PostDao getPostDao(AbstractConnection connection) {
        checkConection(connection);
        return new PostDaoImpl(getSqlConnection(connection));
    }

    @Override
    public SubdivisionDao getSubdivisionDao(AbstractConnection connection) {
        checkConection(connection);
        return new SubdivisionDaoImpl(getSqlConnection(connection));
    }

    @Override
    public AdminDao getAdminDao(AbstractConnection connection) {
        checkConection(connection);
        return new AdminDaoImpl(getSqlConnection(connection));
    }

    private Connection getSqlConnection(AbstractConnection connection) {
        return ((AbstractConnectionImpl) connection).getConnection();
    }

    private void checkConection(AbstractConnection connection){
        Objects.requireNonNull(connection);
    }
}

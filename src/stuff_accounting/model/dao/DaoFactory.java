package stuff_accounting.model.dao;

/**
 * Created by andri on 11/19/2016.
 */
public interface DaoFactory {
    AbstractConnection getConnection();
    DepartmentDao getDepartmentDao(AbstractConnection connection);
    EducationDao getEducationDao(AbstractConnection connection);
    EmployeeDao getEmployeeDao(AbstractConnection connection);
    PostDao getPostDao(AbstractConnection connection);
    SubdivisionDao getSubdivisionDao(AbstractConnection connection);
    AdminDao getAdminDao(AbstractConnection connection);
}

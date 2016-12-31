package stuff_accounting.model.services.impl;

import stuff_accounting.model.dao.AbstractConnection;
import stuff_accounting.model.dao.DaoFactory;
import stuff_accounting.model.dao.DepartmentDao;
import stuff_accounting.model.dao.EmployeeDao;
import stuff_accounting.model.dao.impl.jdbc.DaoFactoryImpl;
import stuff_accounting.model.entity.Department;
import stuff_accounting.model.entity.Employee;
import stuff_accounting.model.services.DepartmentService;

import java.util.List;

/**
 * Created by andri on 12/1/2016.
 */
public class DepartmentServiceImpl implements DepartmentService {
    private DaoFactory factory;
    private static DepartmentServiceImpl instance;

    private DepartmentServiceImpl(){
        factory= DaoFactoryImpl.getInstance();
    }

    public static synchronized DepartmentServiceImpl getInstance(){
        if(instance==null){
            instance = new DepartmentServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Department> getAll() {
        AbstractConnection connection = factory.getConnection();
        try{
            DepartmentDao departmentDao = factory.getDepartmentDao(connection);
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            List<Department> departments = departmentDao.getAll();
            for(Department department:departments)
                department.setStuff(employeeDao.findStuffByDepartmentCode(department.getId()));
            connection.close();
            return departments;
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when retrieving departments");
        }
    }

    @Override
    public Department getByID(int id) {
        AbstractConnection connection = factory.getConnection();
        try{
            DepartmentDao departmentDao = factory.getDepartmentDao(connection);
            Department department = departmentDao.getByID(id);
            connection.close();
            return department;
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when retrieving department");
        }
    }

    @Override
    public void insert(Department department) {
        AbstractConnection connection = factory.getConnection();
        try{
            DepartmentDao departmentDao = factory.getDepartmentDao(connection);
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            connection.beginTransaction();
            departmentDao.insert(department);
            department.setId(departmentDao.findDepartmentByTitle(department.getTitle()).getId());
            if(department.getStuff()!=null && !department.getStuff().isEmpty()){
                for(Employee employee:department.getStuff()){
                    employeeDao.updateDepartment(employee, department.getId());
                }
            }
            connection.commitTransaction();
            connection.close();
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("Service exception when inserting department");
        }
    }

    @Override
    public void update(Department department) {
        AbstractConnection connection = factory.getConnection();
        try{
            DepartmentDao departmentDao = factory.getDepartmentDao(connection);
            EmployeeDao employeeDao =factory.getEmployeeDao(connection);
            connection.beginTransaction();
            departmentDao.update(department);
            for(Employee employee:department.getStuff()){
                employeeDao.updateDepartment(employee, department.getId());
            }
            connection.commitTransaction();
            connection.close();
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception when updating department");
        }
    }

    @Override
    public void deleteById(int id) {
        AbstractConnection connection = factory.getConnection();
        try{
            DepartmentDao departmentDao = factory.getDepartmentDao(connection);
            departmentDao.deleteById(id);
            connection.close();
        }
        catch (Exception ex){
            throw new RuntimeException("Error when deleting post");
        }
    }

    @Override
    public Department findDepartmentByTitle(String title) {
        AbstractConnection connection = factory.getConnection();
        try{
            DepartmentDao departmentDao = factory.getDepartmentDao(connection);
            Department department = departmentDao.findDepartmentByTitle(title);
            connection.close();
            return department;
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when retrieving department");
        }
    }

    @Override
    public List<Department> findDepartmentsByDivision(int divisionID) {
        AbstractConnection connection = factory.getConnection();
        try{
            DepartmentDao departmentDao = factory.getDepartmentDao(connection);
            List<Department> departments = departmentDao.findDepartmentsByDivision(divisionID);
            connection.close();
            return departments;
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when retrieving departments");
        }
    }
}

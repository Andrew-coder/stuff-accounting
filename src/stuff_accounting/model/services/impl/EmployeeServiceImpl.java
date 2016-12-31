package stuff_accounting.model.services.impl;

import stuff_accounting.model.dao.*;
import stuff_accounting.model.dao.impl.jdbc.DaoFactoryImpl;
import stuff_accounting.model.entity.Employee;
import stuff_accounting.model.entity.Post;
import stuff_accounting.model.services.EmployeeService;

import java.util.List;

/**
 * Created by andri on 12/1/2016.
 */
public class EmployeeServiceImpl implements EmployeeService {
    private DaoFactory factory;
    private static EmployeeServiceImpl instance;

    private EmployeeServiceImpl(){
        factory= DaoFactoryImpl.getInstance();
    }

    public static synchronized EmployeeServiceImpl getInstance(){
        if(instance==null){
            instance = new EmployeeServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Employee> getAll() {
        AbstractConnection connection = factory.getConnection();
        try{
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            EducationDao educationDao =factory.getEducationDao(connection);
            PostDao postDao = factory.getPostDao(connection);
            connection.beginTransaction();
            List<Employee> employees = employeeDao.getAll();
            for (Employee employee:employees) {
                employee.setEducations(educationDao.findEducationByEmployeeId(employee.getId()));
                employee.setPost(postDao.getByID(employee.getId()));
            }
            connection.commitTransaction();
            connection.close();
            return employees;
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception occured when retrieving all employees");
        }
    }

    @Override
    public Employee getByID(int id) {
        AbstractConnection connection = factory.getConnection();
        try{
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            EducationDao educationDao =factory.getEducationDao(connection);
            PostDao postDao = factory.getPostDao(connection);
            connection.beginTransaction();
            Employee employee = employeeDao.getByID(id);
            employee.setEducations(educationDao.findEducationByEmployeeId(employee.getId()));
            employee.setPost(postDao.getByID(employee.getPost().getId()));
            connection.commitTransaction();
            connection.close();
            return employee;
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception occured when retrieving employee by id");
        }
    }

    @Override
    public void insert(Employee employee) {
        AbstractConnection connection = factory.getConnection();
        try{
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            connection.beginTransaction();
            employeeDao.insert(employee);
            connection.commitTransaction();
            connection.close();
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception occured when insertning employee");
        }
    }

    @Override
    public void insertInDepartment(Employee employee, int departmentId) {
        AbstractConnection connection = factory.getConnection();
        try{
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            connection.beginTransaction();
            employeeDao.insert(employee);
            employee.setId(employeeDao.findEmployeeByName(employee.getName()).getId());
            employeeDao.updateDepartment(employee, departmentId);
            connection.commitTransaction();
            connection.close();
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception occured when insertning employee in department");
        }
    }

    @Override
    public void update(Employee employee) {
        AbstractConnection connection = factory.getConnection();
        try{
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            connection.beginTransaction();
            employeeDao.update(employee);
            connection.commitTransaction();
            connection.close();
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception occured when updating employee");
        }
    }

    @Override
    public void updateInDepartment(Employee employee, int departmentId) {
        AbstractConnection connection = factory.getConnection();
        try{
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            connection.beginTransaction();
            employee.setId(employeeDao.findEmployeeByName(employee.getName()).getId());
            employeeDao.update(employee);
            employeeDao.updateDepartment(employee, departmentId);
            connection.commitTransaction();
            connection.close();
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception occured when updating employee in department");
        }
    }

    @Override
    public void deleteById(int id) {
        AbstractConnection connection = factory.getConnection();
        try{
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            connection.beginTransaction();
            employeeDao.deleteById(id);
            connection.commitTransaction();
            connection.close();
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception occured when deleting employee");
        }
    }

    @Override
    public List<Employee> findStuffByDepartmentCode(int code) {
        AbstractConnection connection = factory.getConnection();
        try{
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            EducationDao educationDao = factory.getEducationDao(connection);
            PostDao postDao = factory.getPostDao(connection);
            connection.beginTransaction();
            List<Employee> employees = employeeDao.findStuffByDepartmentCode(code);
            for(Employee employee:employees){
                employee.setEducations(educationDao.findEducationByEmployeeId(employee.getId()));
                employee.setPost(postDao.getByID(employee.getId()));
            }
            connection.commitTransaction();
            connection.close();
            return employees;
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception occured when retrieving employee by department code");
        }
    }

    @Override
    public Employee findEmployeeByName(String name) {
        AbstractConnection connection = factory.getConnection();
        try{
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            PostDao postDao = factory.getPostDao(connection);
            EducationDao educationDao = factory.getEducationDao(connection);
            connection.beginTransaction();
            Employee employee = employeeDao.findEmployeeByName(name);
            employee.setPost(postDao.getByID(employee.getPost().getId()));
            employee.setEducations(educationDao.findEducationByEmployeeId(employee.getId()));
            connection.commitTransaction();
            connection.close();
            return employee;
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception occured when retrieving employee by name");
        }
    }

    @Override
    public Employee findEmployeeByPost(Post post) {
        AbstractConnection connection = factory.getConnection();
        try{
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            PostDao postDao = factory.getPostDao(connection);
            EducationDao educationDao = factory.getEducationDao(connection);
            connection.beginTransaction();
            Employee employee = employeeDao.findEmployeeByPost(post);
            employee.setPost(postDao.getByID(employee.getPost().getId()));
            employee.setEducations(educationDao.findEducationByEmployeeId(employee.getId()));
            connection.commitTransaction();
            connection.close();
            return employee;
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception occured when retrieving employee by post");
        }
    }

    @Override
    public void delete(List<Employee> employees) {
        AbstractConnection connection = factory.getConnection();
        try {
            EmployeeDao employeeDao = factory.getEmployeeDao(connection);
            connection.beginTransaction();
            for (Employee employee : employees){
                employeeDao.deleteById(employee.getId());
            }
            connection.commitTransaction();
            connection.close();
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception occured when deliting employees");
        }
    }
}

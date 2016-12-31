package stuff_accounting.model.services;

import stuff_accounting.model.entity.Employee;
import stuff_accounting.model.entity.Post;

import java.util.List;

/**
 * Created by andri on 12/1/2016.
 */
public interface EmployeeService {
    public List<Employee> getAll();
    public Employee getByID(int id);
    public void insert(Employee employee);
    public void insertInDepartment(Employee employee, int departmentId);
    public void update(Employee employee);
    public void updateInDepartment(Employee employee, int departmentId);
    public void deleteById(int id);
    public void delete(List<Employee> employees);
    List<Employee> findStuffByDepartmentCode(int code);
    Employee findEmployeeByName(String name);
    Employee findEmployeeByPost(Post post);
}

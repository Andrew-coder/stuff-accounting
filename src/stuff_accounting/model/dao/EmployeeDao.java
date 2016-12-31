package stuff_accounting.model.dao;

import stuff_accounting.model.entity.Employee;
import stuff_accounting.model.entity.Post;

import java.util.List;

public interface EmployeeDao extends CommonDao<Employee> {
    List<Employee> findStuffByDepartmentCode(int code);
    Employee findEmployeeByName(String name);
    Employee findEmployeeByPost(Post post);
    int findCountryIDByName(String name);
    String findCountryNameByID(int id);
    int findDepartmentByEmployeeID(int id);
    int findStockCategoryByName(String name);
    String findStockCategoryByID(int id);
    void updateDepartment(Employee employee, int departmentId);
}

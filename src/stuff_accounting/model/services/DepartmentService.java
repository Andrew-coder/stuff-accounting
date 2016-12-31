package stuff_accounting.model.services;

import stuff_accounting.model.entity.Department;

import java.util.List;

/**
 * Created by andri on 12/1/2016.
 */
public interface DepartmentService {
    List<Department> getAll();
    Department getByID(int id);
    void insert(Department department);
    void update(Department department);
    void deleteById(int id);
    Department findDepartmentByTitle(String title);
    List<Department> findDepartmentsByDivision(int divisionID);
}

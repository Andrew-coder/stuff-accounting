package stuff_accounting.model.dao;

import stuff_accounting.model.entity.Department;

import java.util.List;

public interface DepartmentDao extends CommonDao<Department> {

    Department findDepartmentByTitle(String title);
    List<Department> findDepartmentsByDivision(int divisionID);
    void updateSubdivision(Department department, int subdivisionId);
}

package stuff_accounting.model.dao.impl.jdbc;

import stuff_accounting.model.dao.DepartmentDao;
import stuff_accounting.model.entity.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by andri on 11/19/2016.
 */
public class DepartmentDaoImpl implements DepartmentDao, Parsable<Department>{
    private static String GET_ALL_DEPARTMENTS = "select id_department, department_title from departments_list ";
    private static String GET_DEPARTMENTS_BY_DIVISION_ID = "select dl.id_department, dl.department_title from departments_list dl inner join \n" +
            "subdivision_catalog sc on sc.id_division=dl.id_division where sc.id_division=?";
    private static String FILTER_BY_TITLE = "where department_title = ?";
    private static String FILTER_BY_ID = "where id_department = ?;";
    private static String INSERT_DEPARTMENT = "insert into departments_list(department_title) values (?)";
    private static String DELETE_DEPARTMENT = "delete from departments_list ";
    private static String UPDATE_DEPARTMENT = "update departments_list set department_title = ? ";
    private static String UPDATE_DEPARTMENT_SUBDIVISION = "update stuff_accounting.departments_list set id_division=? where id_department=?;";

    Connection connection;

    public DepartmentDaoImpl() {
    }

    public DepartmentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Department findDepartmentByTitle(String title) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_DEPARTMENTS+ FILTER_BY_TITLE)){
            statement.setString(1, title);
            List<Department> departments = parseResultSet(statement.executeQuery());
            if(!departments.isEmpty())
                return departments.get(0);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        };
        throw new RuntimeException("error occured when retrieving data");
    }

    @Override
    public List<Department> getAll() {
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_DEPARTMENTS)){
            return parseResultSet(resultSet);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        };
        throw new RuntimeException("error occured when retrieving data");
    }

    @Override
    public Department getByID(int id) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_DEPARTMENTS+ FILTER_BY_ID)){
            statement.setInt(1,id);
            List<Department> departments = parseResultSet(statement.executeQuery());
            if(!departments.isEmpty())
                return departments.get(0);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        throw new RuntimeException("error occured when retrieving data");
    }

    @Override
    public void insert(Department object) {
        if(object==null){
            throw new RuntimeException("Error! Wrong department object...");
        }

        try(PreparedStatement statement = connection.prepareStatement(INSERT_DEPARTMENT)){
            statement.setString(1, object.getTitle());
            statement.executeUpdate();

        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Dao exception occured when inserting department");
        }
    }

    @Override
    public void update(Department object) {
        if(object==null||object.getId()==0){
            throw new RuntimeException("Error! Wrong department object...");
        }

        try(PreparedStatement statement = connection.prepareStatement(UPDATE_DEPARTMENT + FILTER_BY_ID)){
            statement.setString(1, object.getTitle());
            statement.setInt(3,object.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        Department department = getByID(id);
        Objects.requireNonNull(department, "Department with such id wasn't found");
        try(PreparedStatement statement = connection.prepareStatement(DELETE_DEPARTMENT+FILTER_BY_ID)){
            statement.setInt(1,id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public List<Department> findDepartmentsByDivision(int divisionID) {
        List<Department> departments=null;
        try (PreparedStatement statement = connection.prepareStatement(GET_DEPARTMENTS_BY_DIVISION_ID)) {

            statement.setInt(1, divisionID);
           departments = parseResultSet(statement.executeQuery());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public void updateSubdivision(Department department, int subdivisionId) {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_DEPARTMENT_SUBDIVISION)){
            statement.setInt(1,subdivisionId);
            statement.setInt(2, department.getId());
            statement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("error occured when updating subdivision in department");
        }
    }

    @Override
    public List<Department> parseResultSet(ResultSet set) throws SQLException {
        List<Department> departments = new ArrayList<>();
        while(set.next()){
            Department department = new Department();
            department.setId(set.getInt("id_department"));
            department.setTitle(set.getString("department_title"));
            departments.add(department);
        }
        return  departments;
    }
}

package stuff_accounting.model.dao.impl.jdbc;

import stuff_accounting.model.dao.EmployeeDao;
import stuff_accounting.model.entity.Employee;
import stuff_accounting.model.entity.Post;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by andri on 11/19/2016.
 */
public class EmployeeDaoImpl implements EmployeeDao, Parsable<Employee> {
    private static String GET_ALL_EMPLOYEES = "select * from inventory ";
    private static String GET_STUFF_LIST_FOR_DEPARTMENT = "select i.timeBoardNumber, i.name, i.postID, i.receipt_date, i.dismissial_mark, \n" +
            "i.partner_name, i.children_amount, i.country, i.passport_data, i.reserve_category, \n" +
            "i.fitness, i.rectal from inventory i left outer join departments_list dl on i.department_code=dl.id_department\n" +
            "where dl.id_department=? ";
    private static String FILTER_BY_ID = "where timeBoardNumber = ?";
    private static String FILTER_BY_NAME = "where name = ?";
    private static String FILTER_BY_POST = "where postID = ?";
    private static String DELETE_EMPLOYEE = "delete from inventory ";
    private static String UPDATE_EMPLOYEE = "update inventory set name=?, postID = ?, receipt_date = ?, " +
            "partner_name = ?, children_amount = ?, country = ?, passport_data = ?, reserve_category = ?, " +
            "fitness = ?, rectal = ?";
    private static String INSERT_EMPLOYEE = "INSERT INTO inventory(name, postID, receipt_date,  " +
            "partner_name, children_amount, country, passport_data, reserve_category, fitness, rectal)" +
            " VALUES (?,?,?,?,?,?,?,?,?, ?)";
    private static String GET_COUNTRY_ID_BY_NAME = "select countryID from countries where countryName= ?";
    private static String GET_COUNTRY_NAME_BY_ID = "select countryName from countries where countryID =?";
    private static String GET_DEPARTMENT_BY_EMPLOYEE_ID = "select department_code from inventory where timeBoardNumber=?";
    private static String GET_STOCK_CATEGORY_BY_NAME = "select categoryID from reserve_categories where categoryName = ?";
    private static String GET_STOCK_CATEGORY_BY_ID = "select categoryName from reserve_categories where categoryID = ?";
    private static String UPDATE_EMPLOYEE_POST = "update stuff_accounting.inventory set postID=? where timeBoardNumber=?;";
    private static String UPDATE_EMPLOYEE_DEPARTMENT = "update stuff_accounting.inventory set department_code=? where timeBoardNumber=?;";
    Connection connection;

    public EmployeeDaoImpl() {
    }

    public EmployeeDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Employee> getAll() {
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_EMPLOYEES)){
            return parseResultSet(resultSet);
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when retrieving all employees");
        }
    }

    @Override
    public Employee findEmployeeByName(String name) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_EMPLOYEES+ FILTER_BY_NAME)){
            statement.setString(1,name);
            List<Employee> employees = parseResultSet(statement.executeQuery());
            return employees.get(0);
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when retrieving employee by name");
        }
    }

    @Override
    public Employee getByID(int id) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_EMPLOYEES+ FILTER_BY_ID)){
            statement.setInt(1,id);
            List<Employee> employees = parseResultSet(statement.executeQuery());
            return employees.get(0);
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when retrieving employee by id");
        }
    }

    @Override
    public Employee findEmployeeByPost(Post post) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_EMPLOYEES+ FILTER_BY_POST)){
            statement.setInt(1,post.getId());
            List<Employee> employees = parseResultSet(statement.executeQuery());
            return employees.get(0);
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when retrieving employee by post");
        }
    }

    @Override
    public void insert(Employee object) {
       Objects.requireNonNull(object,"Error! Wrong employee object...");

        try(PreparedStatement statement = connection.prepareStatement(INSERT_EMPLOYEE)){
            statement.setString(1, object.getName());
            statement.setInt(2, object.getPost().getId());
            statement.setDate(3, java.sql.Date.valueOf(object.getReceiptDate().
                                toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            statement.setString(4, object.getPartnerName());
            statement.setInt(5, object.getChildrenAmount());
            statement.setInt(6, findCountryIDByName(object.getCountry()));
            statement.setString(7, object.getPassportData());
            statement.setInt(8, findStockCategoryByName(object.getStockCategory()));
            statement.setBoolean(9, object.getFitness());
            statement.setString(10, object.getRectal());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when inserting employee");
        }
    }

    @Override
    public void update(Employee object) {
        Objects.requireNonNull(object, "Error! Wrong post object...");

        try(PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE + FILTER_BY_ID)){
            statement.setString(1, object.getName());
            statement.setInt(2, object.getPost().getId());
            statement.setDate(3, java.sql.Date.valueOf(object.getReceiptDate().
                    toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            statement.setString(4, object.getPartnerName());
            statement.setInt(5, object.getChildrenAmount());
            statement.setInt(6, findCountryIDByName(object.getCountry()));
            statement.setString(7, object.getPassportData());
            statement.setInt(8, findStockCategoryByName(object.getStockCategory()));
            statement.setBoolean(9, object.getFitness());
            statement.setString(10, object.getRectal());
            statement.setInt(11, object.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when updating employee");
        }
    }

    @Override
    public void deleteById(int id) {
        Employee employee = getByID(id);
        Objects.requireNonNull(employee, "Employee with such id wasn't found");
        try(PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE+FILTER_BY_ID)){
            statement.setInt(1,id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when deleting employee by id");
        }
    }

    @Override
    public List<Employee> findStuffByDepartmentCode(int code) {
        List<Employee> stuff=null;
        try (PreparedStatement statement = connection.prepareStatement(GET_STUFF_LIST_FOR_DEPARTMENT)) {

            statement.setInt(1, code);
            stuff = parseResultSet(statement.executeQuery());
            return stuff;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("error occured when retrieving stuff by department code");
        }
    }

    @Override
    public int findCountryIDByName(String name){
        try(PreparedStatement statement = connection.prepareStatement(GET_COUNTRY_ID_BY_NAME)){
            statement.setString(1,name);
            ResultSet set = statement.executeQuery();
            if(set.next())
                return set.getInt("countryID");
            else
                throw new RuntimeException("Dao exception occured when finding country by name");
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Dao exception occured when finding country by name");
        }
    }

    @Override
    public String findCountryNameByID(int id) {
        try(PreparedStatement statement = connection.prepareStatement(GET_COUNTRY_NAME_BY_ID)){
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if(set.next())
                return set.getString("countryName");
            else
                throw new RuntimeException("Dao exception occured when finding country by id");
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Dao exception occured when finding country by id");
        }
    }

    @Override
    public int findDepartmentByEmployeeID(int id){
        try(PreparedStatement statement = connection.prepareStatement(GET_DEPARTMENT_BY_EMPLOYEE_ID)){
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if(set.next())
                return set.getInt("department_code");
            else
                throw new RuntimeException("error occured when retrieving department by employee id");
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("error occured when retrieving department by employee id");
        }
    }

    @Override
    public int findStockCategoryByName(String name){
        try(PreparedStatement statement = connection.prepareStatement(GET_STOCK_CATEGORY_BY_NAME)){
            statement.setString(1, name);
            ResultSet set = statement.executeQuery();
            if(set.next())
                return set.getInt("categoryID");
            else
                throw new RuntimeException("error occured when retrieving stock category by name");
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("error occured when retrieving stock category by name");
        }
    }

    @Override
    public String findStockCategoryByID(int id){
        try(PreparedStatement statement = connection.prepareStatement(GET_STOCK_CATEGORY_BY_ID)){
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if(set.next())
                return set.getString("categoryName");
            else
                throw new RuntimeException("error occured when retrieving stock category by id");
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("error occured when retrieving stock category by id");
        }
    }

    @Override
    public List<Employee> parseResultSet(ResultSet set) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while(set.next()){
            Employee employee = new Employee();
            employee.setId(set.getInt("timeBoardNumber"));
            employee.setName(set.getString("name"));
            employee.setReceiptDate(set.getDate("receipt_date"));
            employee.setDismissial(set.getBoolean("dismissial_mark"));
            employee.setChildrenAmount(set.getInt("children_amount"));
            employee.setPartnerName(set.getString("partner_name"));
            employee.setCountry(findCountryNameByID(set.getInt("country")));
            employee.setPassportData(set.getString("passport_data"));
            employee.setStockCategory(findStockCategoryByID(set.getInt("reserve_category")));
            employee.setFitness(set.getBoolean("fitness"));
            employee.setRectal(set.getString("rectal"));
            employees.add(employee);
        }
        return  employees;
    }

    @Override
    public void updateDepartment(Employee employee, int departmentId) {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE_DEPARTMENT)){
            statement.setInt(1,departmentId);
            statement.setInt(2, employee.getId());
            statement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("error occured when updating department in employee");
        }
    }
}

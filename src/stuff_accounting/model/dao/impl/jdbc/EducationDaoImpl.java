package stuff_accounting.model.dao.impl.jdbc;

import stuff_accounting.model.dao.EducationDao;
import stuff_accounting.model.entity.Education;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by andri on 11/19/2016.
 */
public class EducationDaoImpl implements EducationDao, Parsable<Education> {
    private static String GET_ALL_EDUCATIONS = "select e.educationID, f.formName as education_form, t.typeName as education_type, e.speciality " +
            "from education e INNER JOIN education_forms f ON e.education_form=f.formID " +
            "INNER JOIN education_types t ON e.education_type=t.typeID";
    private static String GET_EDUCATION_BY_FORM_NAME = "select formID from education_forms where formName=?";
    private static String GET_EDUCATION_BY_TYPE_NAME = "select typeID from education_types where typeName=?";
    private static final String FILTER_BY_ID = " where educationID = ?;";
    private static final String FILTER_BY_FORM = "where education_form = ?";
    private static final String FILTER_BY_TYPE = "where education_type = ?";
    private static final String FILTER_BY_SPECIALITY = "where speciality =?";
    private static final String FILTER_BY_TIMEBOARDNUMBER =" where timeBoardNumber = ?";
    private static final String INSERT_EDUCATION = "insert into education (educationID, education_form, education_type, speciality) values (?, ?, ?, ?);";
    private static final String DELETE_EDUCATION = "delete from education";
    private static final String UPDATE_EDUCATION = "update education set education_form =?, education_type=?, speciality=?";
    private static final String FIND_EDUCATIONS_BY_EMPLOYEE_ID = "select educationID, education_form, education_type, speciality from education where timeBoardNumber = ?";

    Connection connection;

    public EducationDaoImpl() {
    }

    public EducationDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Education> getAll() {
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_EDUCATIONS)){
            return parseResultSet(resultSet);
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("error occured when retrieving data");
        }
    }

    @Override
    public Education getByID(int id) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_EDUCATIONS+ FILTER_BY_ID)){
            statement.setInt(1,id);
            List<Education> educations = parseResultSet(statement.executeQuery());
            return educations.get(0);
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("error occured when retrieving data");
        }
    }

    @Override
    public void insert(Education object) {
        Objects.requireNonNull(object, "Error! Wrong education object...");
        try(PreparedStatement statement = connection.prepareStatement(INSERT_EDUCATION)){
            statement.setInt(1, object.getId());
            statement.setInt(2, findEducationFormByName(object.getEducationForm()));
            statement.setInt(3, findEducationTypeByName(object.getEducationType()));
            statement.setString(4, object.getSpeciality());
            statement.executeUpdate();

        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when inserting education");
        }
    }

    @Override
    public List<Education> findEducationByType(String type) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_EDUCATIONS+ FILTER_BY_TYPE)){
            statement.setInt(1,findEducationTypeByName(type));
            List<Education> educations = parseResultSet(statement.executeQuery());
            return educations;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when retrieving education by type");
        }
    }

    @Override
    public List<Education> findEducationByForm(String form) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_EDUCATIONS+ FILTER_BY_FORM)){
            statement.setInt(1,findEducationFormByName(form));
            List<Education> educations = parseResultSet(statement.executeQuery());
            return educations;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when retrieving education by form");
        }
    }

    @Override
    public void update(Education object) {
        if(object==null||object.getId()==0){
            throw new RuntimeException("Error! Wrong education object...");
        }

        try(PreparedStatement statement = connection.prepareStatement(UPDATE_EDUCATION + FILTER_BY_ID)){
            statement.setInt(1, findEducationFormByName(object.getEducationForm()));
            statement.setInt(2, findEducationTypeByName(object.getEducationType()));
            statement.setString(3,object.getSpeciality());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when updating education");
        }
    }

    @Override
    public void deleteById(int id) {
        Education education = getByID(id);
        Objects.requireNonNull(education, "education with such id wasn't found!");
        try(PreparedStatement statement = connection.prepareStatement(DELETE_EDUCATION+FILTER_BY_ID)){
            statement.setInt(1,id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when deleting education by id");
        }
    }

    @Override
    public List<Education> findEducationBySpeciality(String speciality) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_EDUCATIONS+ FILTER_BY_SPECIALITY)){
            statement.setString(1,speciality);
            List<Education> educations = parseResultSet(statement.executeQuery());
            return educations;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when retrieving education by speciality");
        }
    }

    @Override
    public int findEducationFormByName(String name) {
        try(PreparedStatement statement = connection.prepareStatement(GET_EDUCATION_BY_FORM_NAME)){
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            //return resultSet.getInt("formID");
            if(resultSet.next())
                return resultSet.getInt("formID");
            else return -1;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when retrieving education form");
        }
    }

    @Override
    public int findEducationTypeByName(String name) {
        name+=" education";
        try(PreparedStatement statement = connection.prepareStatement(GET_EDUCATION_BY_TYPE_NAME)){
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                return resultSet.getInt("typeID");
            else return -1;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao exception occured when retrieving увгсфешщт ензу");
        }
    }

    @Override
    public List<Education> findEducationByEmployeeId(int id) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_EDUCATIONS+FILTER_BY_TIMEBOARDNUMBER)){
            statement.setInt(1,id);
            List<Education> educations = parseResultSet(statement.executeQuery());
            return educations;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Dao exception occured when retrieving education by employee id");
        }
    }

    @Override
    public List<Education> parseResultSet(ResultSet set) throws SQLException {
        List<Education> educations = new ArrayList<>();
        while(set.next()){
            educations.add(new Education(   set.getInt("educationID"),
                                            set.getString("education_type"),
                                            set.getString("education_form"),
                                            set.getString("speciality")));
        }
        return educations;
    }
}

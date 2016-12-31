package stuff_accounting.model.dao.impl.jdbc;

import stuff_accounting.model.dao.SubdivisionDao;
import stuff_accounting.model.entity.Subdivision;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by andri on 11/19/2016.
 */
public class SubdivisionDaoImpl implements SubdivisionDao, Parsable<Subdivision>{
    private static String GET_ALL_SUBDIVISIONS = "select * from subdivision_catalog ";
    private static String FILTER_BY_ID = "where id_division=?;";
    private static String FILTER_BY_CODE = "where division_code = ?";
    private static String DELETE_SUBDIVISION = "delete from subdivision_catalog ";
    private static String INSERT_SUBDIVISION = "insert into subdivision_catalog (id_division, division_code) values (?, ?);";
    private static String UPDATE_SUBDIVISION = "update subdivision_catalog set division_code=? ";

    Connection connection;

    public SubdivisionDaoImpl() {
    }

    public SubdivisionDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Subdivision findDivisionByCode(String code) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_SUBDIVISIONS+ FILTER_BY_CODE)){
            statement.setString(1,code);
            List<Subdivision> subdivisions = parseResultSet(statement.executeQuery());
            if(!subdivisions.isEmpty())
                return subdivisions.get(0);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        };
        throw new RuntimeException("error occured when retrieving data");
    }

    @Override
    public List<Subdivision> getAll() {
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_SUBDIVISIONS)){
            return parseResultSet(resultSet);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        };
        throw new RuntimeException("error occured when retrieving data");
    }

    @Override
    public Subdivision getByID(int id) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_SUBDIVISIONS+ FILTER_BY_ID)){
            statement.setInt(1,id);
            List<Subdivision> subdivisions = parseResultSet(statement.executeQuery());
            if(!subdivisions.isEmpty())
                return subdivisions.get(0);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        throw new RuntimeException("error occured when retrieving data");
    }

    @Override
    public void insert(Subdivision object) {
        Objects.requireNonNull(object, "Error! Wrong subdivision object...");
        try(PreparedStatement statement = connection.prepareStatement(INSERT_SUBDIVISION)){
            statement.setInt(1, object.getId());
            statement.setString(2, object.getDivisionCode());
            statement.executeUpdate();

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Subdivision object) {
        if(object==null||object.getId()==0){
            throw new RuntimeException("Error! Wrong department object...");
        }

        try(PreparedStatement statement = connection.prepareStatement(UPDATE_SUBDIVISION + FILTER_BY_ID)){
            statement.setString(1, object.getDivisionCode());
            statement.setInt(2, object.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        Subdivision subdivision = getByID(id);
        Objects.requireNonNull(subdivision, "Subdivision with such id wasn't found");
        try(PreparedStatement statement = connection.prepareStatement(DELETE_SUBDIVISION+FILTER_BY_ID)){
            statement.setInt(1,id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public List<Subdivision> parseResultSet(ResultSet set) throws SQLException{
        List<Subdivision> subdivisions = new ArrayList<>();
        while(set.next()){
            Subdivision subdivision = new Subdivision();
            subdivision.setId(set.getInt("id_division"));
            subdivision.setDivisionCode(set.getString("division_code"));
            subdivisions.add(subdivision);
        }
        return  subdivisions;
    }
}

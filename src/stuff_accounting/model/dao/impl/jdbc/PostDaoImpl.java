package stuff_accounting.model.dao.impl.jdbc;

import stuff_accounting.model.dao.PostDao;
import stuff_accounting.model.entity.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by andri on 11/19/2016.
 */
public class PostDaoImpl implements PostDao, Parsable<Post> {
    private static final String GET_ALL_POSTS = "select * from stuff_list";
    private static final String FILTER_BY_ID = " where postID = ?;";
    private static final String FILTER_BY_NAME = " where postName= ?;";
    private static final String DELETE_POST = "delete from stuff_list";
    private static final String UPDATE_POST = "update stuff_list set postName=?, salary=?";
    private static final String INSERT_POST = "insert into stuff_list (postName, salary) values (?, ?);";

    private Connection connection;

    public PostDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Post> getAll() {
        try(Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(GET_ALL_POSTS)){
            return parseResultSet(resultSet);
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Dao error when retrieving post info");
        }
    }

    @Override
    public Post findPostByName(String name) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_POSTS+ FILTER_BY_NAME)){
            statement.setString(1,name);
            List<Post> posts = parseResultSet(statement.executeQuery());
            if(!posts.isEmpty())
                return posts.get(0);
            else
                return null;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Dao error when retrieving post info");
        }
    }

    @Override
    public Post getByID(int id) {
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_POSTS+ FILTER_BY_ID)){
            statement.setInt(1,id);
            List<Post> posts = parseResultSet(statement.executeQuery());
            if(!posts.isEmpty())
                return posts.get(0);
            else return null;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("error when retrieving post info");
        }
    }

    @Override
    public void insert(Post object) {
        Objects.requireNonNull(object);

        try(PreparedStatement statement = connection.prepareStatement(INSERT_POST)){
            statement.setString(1, object.getPostName());
            statement.setDouble(2, object.getSalary());
            statement.executeUpdate();

        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("dao error when inserting post info");
        }
    }

    @Override
    public void update(Post object) {
        if(object==null||object.getId()==0){
            throw new RuntimeException("Error! Wrong post object...");
        }

        try(PreparedStatement statement = connection.prepareStatement(UPDATE_POST + FILTER_BY_ID)){
            statement.setString(1, object.getPostName());
            statement.setDouble(2, object.getSalary());
            statement.setInt(3,object.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new RuntimeException("Dao error occured when updating post");
        }
    }

    @Override
    public void deleteById(int id){
        Post post = getByID(id);
        Objects.requireNonNull(post, "Post with such id wasn't found");
        try(PreparedStatement statement = connection.prepareStatement(DELETE_POST+FILTER_BY_ID)){
            statement.setInt(1,id);
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public List<Post> parseResultSet(ResultSet set) throws SQLException{
        List<Post> posts = new ArrayList<>();
        while(set.next()){
            posts.add(new Post( set.getInt("postID"),
                                set.getString("postName"),
                                set.getDouble("salary")));
        }
        return  posts;
    }
}

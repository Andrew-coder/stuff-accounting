package stuff_accounting.model.services.impl;

import stuff_accounting.model.dao.AbstractConnection;
import stuff_accounting.model.dao.DaoFactory;
import stuff_accounting.model.dao.PostDao;
import stuff_accounting.model.dao.impl.jdbc.DaoFactoryImpl;
import stuff_accounting.model.entity.Post;
import stuff_accounting.model.services.PostService;

import java.util.List;

/**
 * Created by andri on 12/1/2016.
 */
public class PostServiceImpl implements PostService {
    private DaoFactory factory;
    private static PostServiceImpl instance;

    private PostServiceImpl(){
        factory=DaoFactoryImpl.getInstance();
    }

    public static synchronized PostServiceImpl getInstance(){
        if(instance==null){
            instance = new PostServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Post> getAll() {
        AbstractConnection connection = factory.getConnection();
        try{
            PostDao postDao = factory.getPostDao(connection);
            List<Post> posts = postDao.getAll();
            connection.close();
            return posts;
        }
        catch (Exception ex){
            throw new RuntimeException("Error when retrieving posts");
        }
    }

    @Override
    public Post getByID(int id)  {
        AbstractConnection connection = factory.getConnection();
        try{
            PostDao postDao = factory.getPostDao(connection);
            Post post = postDao.getByID(id);
            connection.close();
            return post;
        }
        catch (Exception ex){
            throw new RuntimeException("Error when retrieving post");
        }
    }

    @Override
    public void insert(Post post) {
        AbstractConnection connection = factory.getConnection();
        try{
            PostDao postDao = factory.getPostDao(connection);
            postDao.insert(post);
            connection.close();
        }
        catch (Exception ex){
            throw new RuntimeException("Service error when inserting post");
        }
    }

    @Override
    public void update(Post post) {
        AbstractConnection connection = factory.getConnection();
        try{
            PostDao postDao = factory.getPostDao(connection);
            postDao.update(post);
            connection.close();
        }
        catch (Exception ex){
            throw new RuntimeException("Error when updating post");
        }
    }

    @Override
    public void deleteById(int id) {
        AbstractConnection connection = factory.getConnection();
        try{
            PostDao postDao = factory.getPostDao(connection);
            postDao.deleteById(id);
            connection.close();
        }
        catch (Exception ex){
            throw new RuntimeException("Error when deleting post");
        }
    }

    @Override
    public Post findPostByName(String name) {
        AbstractConnection connection = factory.getConnection();
        try{
            PostDao postDao = factory.getPostDao(connection);
            Post post = postDao.findPostByName(name);
            connection.close();
            return post;
        }
        catch (Exception ex){
            throw new RuntimeException("Error when retrieving post");
        }
    }
}

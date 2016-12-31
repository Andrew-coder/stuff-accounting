package stuff_accounting.model.dao;

import stuff_accounting.model.entity.Post;

/**
 * Created by andrew on 15.10.2016.
 */
public interface PostDao extends CommonDao<Post> {
    public Post findPostByName(String name);
}

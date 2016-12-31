package stuff_accounting.model.services;

import stuff_accounting.model.entity.Post;

import java.util.List;

/**
 * Created by andri on 12/1/2016.
 */
public interface PostService {
    public List<Post> getAll();
    public Post getByID(int id);
    public void insert(Post post);
    public void update(Post post);
    public void deleteById(int id);
    public Post findPostByName(String name);
}

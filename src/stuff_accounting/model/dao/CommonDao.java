package stuff_accounting.model.dao;

import stuff_accounting.model.entity.BaseEntity;

import java.util.List;

/**
 * Created by andrew on 14.10.2016.
 */
public interface CommonDao<T extends BaseEntity> {
    public List<T> getAll();
    public T getByID(int id);
    public void insert(T object);
    public void update(T object);
    public void deleteById(int id);
}

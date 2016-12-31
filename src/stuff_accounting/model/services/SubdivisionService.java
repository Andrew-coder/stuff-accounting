package stuff_accounting.model.services;

import stuff_accounting.model.entity.Subdivision;

import java.util.List;

/**
 * Created by andri on 12/1/2016.
 */
public interface SubdivisionService {
    public List<Subdivision> getAll();
    public Subdivision getByID(int id);
    public void insert(Subdivision subdivision);
    public void update(Subdivision subdivision);
    public void deleteById(int id);
}

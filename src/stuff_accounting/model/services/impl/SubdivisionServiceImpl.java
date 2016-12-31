package stuff_accounting.model.services.impl;

import stuff_accounting.model.dao.AbstractConnection;
import stuff_accounting.model.dao.DaoFactory;
import stuff_accounting.model.dao.DepartmentDao;
import stuff_accounting.model.dao.SubdivisionDao;
import stuff_accounting.model.dao.impl.jdbc.DaoFactoryImpl;
import stuff_accounting.model.entity.Department;
import stuff_accounting.model.entity.Subdivision;
import stuff_accounting.model.services.SubdivisionService;

import java.util.List;

/**
 * Created by andri on 12/1/2016.
 */
public class SubdivisionServiceImpl implements SubdivisionService {
    private DaoFactory factory;
    private static SubdivisionServiceImpl instance;

    private SubdivisionServiceImpl(){
        factory= DaoFactoryImpl.getInstance();
    }

    public static synchronized SubdivisionServiceImpl getInstance(){
        if(instance==null){
            instance = new SubdivisionServiceImpl();
        }
        return instance;
    }
    @Override
    public List<Subdivision> getAll() {
        AbstractConnection connection = factory.getConnection();
        try{
            SubdivisionDao subdivisionDao = factory.getSubdivisionDao(connection);
            DepartmentDao departmentDao = factory.getDepartmentDao(connection);
            List<Subdivision> subdivisions = subdivisionDao.getAll();
            for(Subdivision subdivision:subdivisions)
                subdivision.setDepartments(departmentDao.findDepartmentsByDivision(subdivision.getId()));
            connection.close();
            return subdivisions;
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when retrieving subdivisions");
        }
    }

    @Override
    public Subdivision getByID(int id) {
        AbstractConnection connection = factory.getConnection();
        try{
            SubdivisionDao subdivisionDao = factory.getSubdivisionDao(connection);
            Subdivision subdivision = subdivisionDao.getByID(id);
            connection.close();
            return subdivision;
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when retrieving subdivision");
        }
    }

    @Override
    public void insert(Subdivision subdivision) {
        AbstractConnection connection = factory.getConnection();
        try{
            SubdivisionDao subdivisionDao = factory.getSubdivisionDao(connection);
            DepartmentDao departmentDao = factory.getDepartmentDao(connection);
            connection.beginTransaction();
            subdivisionDao.insert(subdivision);
            subdivision.setId(subdivisionDao.findDivisionByCode(subdivision.getDivisionCode()).getId());
            if(subdivision.getDepartments()!=null && !subdivision.getDepartments().isEmpty()) {
                for (Department department : subdivision.getDepartments()) {
                    departmentDao.updateSubdivision(department, subdivision.getId());
                }
            }
            connection.commitTransaction();
            connection.close();
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("Service exception when inserting subdivision");
        }
    }

    @Override
    public void update(Subdivision subdivision) {
        AbstractConnection connection = factory.getConnection();
        try{
            SubdivisionDao subdivisionDao = factory.getSubdivisionDao(connection);
            DepartmentDao departmentDao = factory.getDepartmentDao(connection);
            connection.beginTransaction();
            subdivisionDao.update(subdivision);
            for(Department department:subdivision.getDepartments()){
                departmentDao.updateSubdivision(department, subdivision.getId());
            }
            connection.commitTransaction();
            connection.close();
        }
        catch (Exception ex){
            connection.rollbackTransaction();
            throw new RuntimeException("service exception when updating subdivision");
        }
    }

    @Override
    public void deleteById(int id) {
        AbstractConnection connection = factory.getConnection();
        try{
            SubdivisionDao subdivisionDao = factory.getSubdivisionDao(connection);
            subdivisionDao.deleteById(id);
            connection.close();
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when deleting subdivision");
        }
    }
}

package stuff_accounting.model.services.impl;

import stuff_accounting.model.dao.AbstractConnection;
import stuff_accounting.model.dao.DaoFactory;
import stuff_accounting.model.dao.EducationDao;
import stuff_accounting.model.dao.PostDao;
import stuff_accounting.model.dao.impl.jdbc.DaoFactoryImpl;
import stuff_accounting.model.entity.Education;
import stuff_accounting.model.entity.Post;
import stuff_accounting.model.services.EducationService;

import java.util.List;

/**
 * Created by andri on 12/1/2016.
 */
public class EducationServiceImpl implements EducationService {
    private DaoFactory factory;
    private static EducationServiceImpl instance;

    private EducationServiceImpl(){
        factory= DaoFactoryImpl.getInstance();
    }

    public static synchronized EducationServiceImpl getInstance(){
        if(instance==null){
            instance = new EducationServiceImpl();
        }
        return instance;
    }
    @Override
    public List<Education> getAll() {
        AbstractConnection connection = factory.getConnection();
        try{
            EducationDao educationDao = factory.getEducationDao(connection);
            List<Education> educations = educationDao.getAll();
            connection.close();
            return educations;
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when retrieving educations");
        }
    }

    @Override
    public Education getByID(int id) {
        AbstractConnection connection = factory.getConnection();
        try{
            EducationDao educationDao = factory.getEducationDao(connection);
            Education education = educationDao.getByID(id);
            connection.close();
            return education;
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when retrieving education");
        }
    }

    @Override
    public void insert(Education education) {
        AbstractConnection connection = factory.getConnection();
        try{
            EducationDao educationDao = factory.getEducationDao(connection);
            educationDao.insert(education);
            connection.close();
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when inserting post");
        }
    }

    @Override
    public void update(Education education) {
        AbstractConnection connection = factory.getConnection();
        try{
            EducationDao educationDao = factory.getEducationDao(connection);
            educationDao.update(education);
            connection.close();
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when updating education");
        }
    }

    @Override
    public void deleteById(int id) {
        AbstractConnection connection = factory.getConnection();
        try{
            EducationDao educationDao = factory.getEducationDao(connection);
            educationDao.deleteById(id);
            connection.close();
        }
        catch (Exception ex){
            throw new RuntimeException("Service exception when deleting education");
        }
    }

    @Override
    public List<Education> findEducationByType(String type) {
        AbstractConnection connection = factory.getConnection();
        try{
            EducationDao educationDao = factory.getEducationDao(connection);
            List<Education> educations = educationDao.findEducationByType(type);
            return educations;
        }
        catch (Exception ex){
            throw new RuntimeException("service exception when retrieving educations");
        }
    }

    @Override
    public List<Education> findEducationByForm(String form) {
        AbstractConnection connection = factory.getConnection();
        try{
            EducationDao educationDao = factory.getEducationDao(connection);
            List<Education> educations = educationDao.findEducationByForm(form);
            return educations;
        }
        catch (Exception ex){
            throw new RuntimeException("service exception when retrieving educations");
        }
    }

    @Override
    public List<Education> findEducationBySpeciality(String speciality) {
        AbstractConnection connection = factory.getConnection();
        try{
            EducationDao educationDao = factory.getEducationDao(connection);
            List<Education> educations = educationDao.findEducationBySpeciality(speciality);
            return educations;
        }
        catch (Exception ex){
            throw new RuntimeException("service exception when retrieving educations");
        }
    }
}

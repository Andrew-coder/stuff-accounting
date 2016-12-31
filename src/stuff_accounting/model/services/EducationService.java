package stuff_accounting.model.services;

import stuff_accounting.model.entity.Education;

import java.util.List;

/**
 * Created by andri on 12/1/2016.
 */
public interface EducationService {
    public List<Education> getAll();
    public Education getByID(int id);
    public void insert(Education education);
    public void update(Education education);
    public void deleteById(int id);
    List<Education> findEducationByType(String type);
    List<Education> findEducationByForm(String form);
    List<Education> findEducationBySpeciality(String speciality);
}

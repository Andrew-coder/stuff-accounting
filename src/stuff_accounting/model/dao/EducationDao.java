package stuff_accounting.model.dao;

import stuff_accounting.model.entity.Education;

import java.util.List;

/**
 * Created by andrew on 15.10.2016.
 */
public interface EducationDao extends CommonDao<Education> {
    List<Education> findEducationByType(String type);
    List<Education> findEducationByForm(String form);
    List<Education> findEducationBySpeciality(String speciality);
    List<Education> findEducationByEmployeeId(int id);
    int findEducationFormByName(String name);
    int findEducationTypeByName(String name);
}

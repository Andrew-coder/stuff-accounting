package stuff_accounting.model.dao;

import stuff_accounting.model.entity.Subdivision;


public interface SubdivisionDao extends CommonDao<Subdivision> {
    Subdivision findDivisionByCode(String code);
}

package stuff_accounting.model.dao.impl.jdbc;

import stuff_accounting.model.entity.BaseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by andri on 11/29/2016.
 */
public interface Parsable<E extends BaseEntity> {
    List<E> parseResultSet(ResultSet set) throws SQLException;
}

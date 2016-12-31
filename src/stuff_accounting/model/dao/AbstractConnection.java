package stuff_accounting.model.dao;

/**
 * Created by andri on 11/19/2016.
 */
public interface AbstractConnection extends AutoCloseable {
    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();
}

package stuff_accounting.model.entity;

/**
 * Created by andri on 11/13/2016.
 */
public class BaseEntity {
    protected int id;

    public BaseEntity() {
    }

    public BaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

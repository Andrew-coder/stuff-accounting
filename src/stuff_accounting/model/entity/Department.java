package stuff_accounting.model.entity;

import java.util.List;

/**
 * Created by andrew on 14.10.2016.
 */
public class Department extends BaseEntity{
    private String title;

    private List<Employee> stuff;

    public Department() {
    }

    public Department(String title, List<Employee> stuff) {
        this.title = title;
        this.stuff = stuff;
    }

    public Department(int id, String title, List<Employee> stuff) {
        super(id);
        this.title = title;
        this.stuff = stuff;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Employee> getStuff() {
        return stuff;
    }

    public void setStuff(List<Employee> stuff) {
            this.stuff = stuff;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("department: "+title+"\n").append("number of employees: "+stuff.size());
        return builder.toString();
    }
}

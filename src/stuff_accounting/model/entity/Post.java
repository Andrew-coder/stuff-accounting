package stuff_accounting.model.entity;

/**
 * Created by andrew on 15.10.2016.
 */
public class Post extends BaseEntity{
    private String postName;
    private double salary;

    public Post() {    }

    public Post(int postId, String postName, double salary) {
        super(postId);
        this.postName = postName;
        this.salary = salary;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("post: "+postName+"\n").append("salary - "+salary);
        return builder.toString();
    }
}

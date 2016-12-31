package stuff_accounting.model.entity;

/**
 * Created by andri on 12/15/2016.
 */
public class Admin extends BaseEntity {
    private String login;
    private String password;

    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Admin(int id, String login, String password) {
        super(id);
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;

        Admin admin = (Admin) o;

        if (!getLogin().equals(admin.getLogin())) return false;
        return getPassword().equals(admin.getPassword());

    }

    @Override
    public int hashCode() {
        int result = getLogin().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }
}

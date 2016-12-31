package stuff_accounting.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Subdivision extends BaseEntity{
    private String divisionCode;
    private List<Department> departments;

    public Subdivision() {
        departments= new ArrayList<>();
    }

    public Subdivision(String divisionCode, List<Department> departments) {
        this.divisionCode = divisionCode;
        this.departments = departments;
    }

    public Subdivision(int id, String divisionCode, List<Department> departments) {
        super(id);
        this.divisionCode = divisionCode;
        this.departments = departments;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
            this.departments = departments;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("subdivision: "+divisionCode+"\n").
                append("number of departments: "+departments.size());
        return builder.toString();
    }
}

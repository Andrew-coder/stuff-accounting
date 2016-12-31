package stuff_accounting.model.entity;

/**
 * Created by andrew on 15.10.2016.
 */
public class Education extends BaseEntity {
    private String educationType;
    private String educationForm;
    private String speciality;

    public Education() {
        super();
    }

    public Education(int id, String educationType, String educationForm, String speciality) {
        super(id);
        this.educationType = educationType;
        this.educationForm = educationForm;
        this.speciality = speciality;
    }

    public String getEducationForm() {
        return educationForm;
    }

    public void setEducationForm(String educationForm) {
        this.educationForm = educationForm;
    }

    public String getEducationType() {
        return educationType;
    }

    public void setEducationType(String educationType) {
        this.educationType = educationType;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("speciality: "+speciality+"\n").
                append("type: "+educationType+"\n").
                append("form: "+educationForm+"\n");
        return builder.toString();
    }

}

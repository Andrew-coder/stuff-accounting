package stuff_accounting.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employee  extends BaseEntity{

    private String name;
    private Post post;

    //Date of reception
    private Date receiptDate;

    //dismissial mark
    private Boolean dismissial;

    //family status
    private String partnerName;
    private int childrenAmount;

    private List<Education> educations;

    //territorial accountig
    private String country;
    private String passportData;

    //military accounting
    private String stockCategory;
    private Boolean fitness;
    private String rectal;

    public Employee() {
        post = new Post();
        receiptDate = new Date();
        educations = new ArrayList<>();
    }

    public Employee(String name, Post post, Date receiptDAte,
                    Boolean dismissial, String partnerName,
                    int childrenAmount, List<Education> educations, String country,
                    String passportData, String stockCategory, Boolean fitness, String rectal) {
        this.name = name;
        this.post = post;
        this.receiptDate = receiptDAte;
        this.dismissial = dismissial;
        this.partnerName = partnerName;
        this.childrenAmount = childrenAmount;
        this.educations = educations;
        this.country = country;
        this.passportData = passportData;
        this.stockCategory = stockCategory;
        this.fitness = fitness;
        this.rectal = rectal;
    }

    public Employee(int id, String name, Post post, Date receiptDAte,
                    Boolean dismissial, String partnerName,
                    int childrenAmount, List<Education> educations, String country,
                    String passportData, String stockCategory, Boolean fitness, String rectal) {
        super(id);
        this.name = name;
        this.post = post;
        this.receiptDate = receiptDAte;
        this.dismissial = dismissial;
        this.partnerName = partnerName;
        this.childrenAmount = childrenAmount;
        this.educations = educations;
        this.country = country;
        this.passportData = passportData;
        this.stockCategory = stockCategory;
        this.fitness = fitness;
        this.rectal = rectal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (!getName().equals(employee.getName())) return false;
        if (!getPost().equals(employee.getPost())) return false;
        if (!getReceiptDate().equals(employee.getReceiptDate())) return false;
        if (!getDismissial().equals(employee.getDismissial())) return false;
        if (getEducations() != null ? !getEducations().equals(employee.getEducations()) : employee.getEducations() != null)
            return false;
        if (!getCountry().equals(employee.getCountry())) return false;
        if (!getPassportData().equals(employee.getPassportData())) return false;
        if (!getStockCategory().equals(employee.getStockCategory())) return false;
        if (!getFitness().equals(employee.getFitness())) return false;
        return getRectal().equals(employee.getRectal());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPost().hashCode();
        result = 31 * result + getReceiptDate().hashCode();
        result = 31 * result + getDismissial().hashCode();
        result = 31 * result + getCountry().hashCode();
        result = 31 * result + getPassportData().hashCode();
        result = 31 * result + getStockCategory().hashCode();
        result = 31 * result + getFitness().hashCode();
        result = 31 * result + getRectal().hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDAte) {
        this.receiptDate = receiptDAte;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Boolean getDismissial() {
        return dismissial;
    }

    public void setDismissial(Boolean dismissial) {
        this.dismissial = dismissial;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public int getChildrenAmount() {
        return childrenAmount;
    }

    public void setChildrenAmount(int childrenAmount) {
        this.childrenAmount = childrenAmount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassportData() {
        return passportData;
    }
    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    public String getStockCategory() {
        return stockCategory;
    }

    public void setStockCategory(String stockCategory) {
        this.stockCategory = stockCategory;
    }

    public Boolean getFitness() {
        return fitness;
    }

    public void setFitness(Boolean fitness) {
        this.fitness = fitness;
    }

    public String getRectal() {
        return rectal;
    }

    public void setRectal(String rectal) {
        this.rectal = rectal;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.
                append("name: " + name + "\n").
                append("post: " + post.getPostName() + "\n").
                append("receiptDate: " + receiptDate + "\n").
                append("dismissial: " + dismissial + "\n").
                append("partnerName: " + partnerName + "\n").
                append("childrenAmount: " + childrenAmount + "\n").
                //append("educations: " + educations + "\n").
                append("country: " + country + "\n").
                append("passportData: " + passportData + "\n").
                append("stockCategory: " + stockCategory.toString() + "\n").
                append("fitness: " + fitness + "\n").
                append("rectal: " + rectal);
        return builder.toString();
    }
}

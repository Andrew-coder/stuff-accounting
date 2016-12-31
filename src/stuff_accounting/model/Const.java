package stuff_accounting.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by andri on 12/15/2016.
 */
public interface Const {
    String EMPLOYEE = "employee";
    String POST = "post";
    String EDUCATION = "education";
    String DEPARTMENT = "department";
    String SUBDIVISION = "subdivision";

    //pathes
    String ADD_DEPARTMENT = "/stuff_accounting/view/add_item/AddDepartment.fxml";
    String ADD_SUBDIVISION = "/stuff_accounting/view/add_item/AddSubdivision.fxml";
    String ADD_EDUCATION = "/stuff_accounting/view/add_item/AddEducation.fxml";
    String ADD_POST = "/stuff_accounting/view/add_item/AddPost.fxml";
    String ADD_EMPLOYEE = "/stuff_accounting/view/add_item/AddEmploye.fxml";

    String ADMIN_PANEL = "/stuff_accounting/view/admin/AdminPanel.fxml";
    String EMPLOYEE_OVERVIEW = "/stuff_accounting/view/admin/EmployeeOverview.fxml";

    String HEADER = "/stuff_accounting/view/Header";
    String LOGIN_WINDOW = "/stuff_accounting/view/LoginWindow.fxml";
    String ROOT_LAYOUT = "/stuff_accounting/view/RootLayout.fxml";
    String COMPONENTS_OVERVIEW = "/stuff_accounting/view/overview/EntityOverview.fxml";

    ObservableList<String> EDUCATION_TYPES = FXCollections.observableArrayList(  null,
                                                                                "incomplete higher",
                                                                                "basic higher",
                                                                                "complete higher");

    ObservableList<String> EDUCATION_FORMS = FXCollections.observableArrayList(null, "full-time", "external", "evening");
    ObservableList<String> countries = FXCollections.observableArrayList(  "Austria", "France", "Ireland", "Germany", "Italy", "Poland", "Spain", "Sweeden", "Turkey", "United Kingdom");
    ObservableList<String> CHILDRENS = FXCollections.observableArrayList(null,"1","2","more");

    ObservableList<String> STOCK_CATEGORIES = FXCollections.observableArrayList(null, "first category",
                                                                                "second category","third category");
    String SALARY_REGEX = "\\d+(\\.\\d+)?";
}

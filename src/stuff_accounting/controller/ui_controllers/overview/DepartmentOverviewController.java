package stuff_accounting.controller.ui_controllers.overview;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DepartmentOverviewController implements ComponentFilterController {
    @FXML
    private TextField departmentName;
    @FXML
    private TextField employeesMin;
    @FXML
    private TextField employeesMax;

    public TextField getDepartmentName() {
        return departmentName;
    }

    public TextField getEmployeesMin() {
        return employeesMin;
    }

    public TextField getEmployeesMax() {
        return employeesMax;
    }
}

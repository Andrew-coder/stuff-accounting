package stuff_accounting.controller.ui_controllers.overview;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by andri on 12/18/2016.
 */
public class SubdivisionOverviewController implements ComponentFilterController{
    @FXML
    private TextField departmentsNumber;
    @FXML
    private TextField subdivisionName;

    public TextField getDepartmentsNumber() {
        return departmentsNumber;
    }

    public TextField getSubdivisionName() {
        return subdivisionName;
    }
}

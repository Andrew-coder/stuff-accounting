package stuff_accounting.controller.ui_controllers.overview;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by andri on 12/18/2016.
 */
public class PostOverviewController implements ComponentFilterController {
    @FXML
    private TextField postName;
    @FXML
    private TextField salaryFrom;
    @FXML
    private TextField salaryTo;

    public TextField getPostName() {
        return postName;
    }

    public TextField getSalaryFrom() {
        return salaryFrom;
    }

    public TextField getSalaryTo() {
        return salaryTo;
    }
}

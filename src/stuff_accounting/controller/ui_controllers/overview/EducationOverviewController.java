package stuff_accounting.controller.ui_controllers.overview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import stuff_accounting.model.Const;

/**
 * Created by andri on 12/18/2016.
 */
public class EducationOverviewController implements ComponentFilterController{
    @FXML
    private TextField speciality;
    @FXML
    private ChoiceBox educationForm;
    @FXML
    private ChoiceBox educationType;

    public void onCreateEducation(ActionEvent event){

    }

    @FXML
    public void initialize(){
        educationForm.setItems(Const.EDUCATION_FORMS);
        educationType.setItems(Const.EDUCATION_TYPES);
    }

    public TextField getSpeciality() {
        return speciality;
    }

    public ChoiceBox getEducationForm() {
        return educationForm;
    }

    public ChoiceBox getEducationType() {
        return educationType;
    }
}

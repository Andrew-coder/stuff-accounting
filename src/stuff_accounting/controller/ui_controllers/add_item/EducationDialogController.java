package stuff_accounting.controller.ui_controllers.add_item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stuff_accounting.MainApp;
import stuff_accounting.model.Const;
import stuff_accounting.model.entity.BaseEntity;
import stuff_accounting.model.entity.Education;
import stuff_accounting.model.services.EducationService;
import stuff_accounting.model.services.impl.EducationServiceImpl;
import stuff_accounting.view.utilities.Utilities;

import java.util.List;

/**
 * Created by andri on 12/15/2016.
 */
public class EducationDialogController implements DialogController{
    @FXML
    private TextField speciality;
    @FXML
    private ChoiceBox educationType;
    @FXML
    private ChoiceBox educationForm;
    @FXML
    private Button addEducation;
    @FXML
    private Button cancel;
    private Stage dialogStage;
    private MainApp mainApp;

    private EducationService educationService = EducationServiceImpl.getInstance();

    @FXML
    public void initialize(){
        educationType.setItems(Const.EDUCATION_TYPES);
        educationType.setValue("basic higher");
        educationForm.setItems(Const.EDUCATION_FORMS);
        educationForm.setValue("full-time");
    }

    public void onAddEducation(ActionEvent event){
        if(checkValidData()) {
            Education education = new Education();
            education.setSpeciality(speciality.getText());
            education.setEducationForm(educationForm.getValue().toString());
            education.setEducationType(educationType.getValue().toString());
            try {
                educationService.insert(education);
                Utilities.showAlert(Alert.AlertType.CONFIRMATION, null, "Confirmation", "Education was succesfully inserted");
            }
            catch (Exception ex){
                Utilities.showAlert(Alert.AlertType.ERROR, null, "Error!", "Error occured when inserting education...");
                ex.printStackTrace();
            }
        }
        else
            Utilities.showAlert(Alert.AlertType.ERROR, null, "Error!", "Education name or salary is incorrect");
    }

    public void onCancel(ActionEvent event){
        this.dialogStage.close();
    }

    @Override
    public void setDialogStage(Stage stage) {
        this.dialogStage=stage;
    }

    @Override
    public boolean isOkClicked() {
        return false;
    }

    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public Stage getDialogStage() {
        return dialogStage;
    }

    @Override
    public void setEntities(List<BaseEntity> entities) {

    }

    public boolean checkValidData(){
        if(speciality.getText().isEmpty())
            return false;
        return true;
    }
}

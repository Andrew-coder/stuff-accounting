package stuff_accounting.controller.ui_controllers.add_item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stuff_accounting.MainApp;
import stuff_accounting.controller.ui_controllers.overview.EntityChooser;
import stuff_accounting.model.Const;
import stuff_accounting.model.entity.BaseEntity;
import stuff_accounting.model.entity.Department;
import stuff_accounting.model.entity.Subdivision;
import stuff_accounting.model.services.SubdivisionService;
import stuff_accounting.model.services.impl.SubdivisionServiceImpl;
import stuff_accounting.view.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andri on 12/15/2016.
 */
public class SubdivisionDialogController implements DialogController{
    @FXML
    private TextField subdivisionTitle;
    @FXML
    private Button departmentList;
    @FXML
    private Button addSubdivision;
    @FXML
    private Button cancel;
    private Stage dialogStage;
    private MainApp mainApp;
    private List<Department> departments;
    private SubdivisionService subdivisionService = SubdivisionServiceImpl.getInstance();

    public void onChooseDepartments(ActionEvent event){
       departments=new ArrayList<>();
        List<BaseEntity> entities = new EntityChooser(Const.DEPARTMENT, mainApp, this).getEntityList();
        for(BaseEntity entity:entities){
            departments.add((Department)entity);
        }
    }

    public void onAddSubdivision(ActionEvent event){
        Subdivision subdivision = new Subdivision();
        subdivision.setDivisionCode(subdivisionTitle.getText());
        subdivision.setDepartments(departments);
        try {
            subdivisionService.insert(subdivision);
            Utilities.showAlert(Alert.AlertType.CONFIRMATION, null, "Confirmation", "Subdivision was succesfully inserted");
        }
        catch (Exception ex){
            Utilities.showAlert(Alert.AlertType.ERROR, null, "Error!", "Error occured when inserting subdivision...");
            ex.printStackTrace();
        }
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
        for(BaseEntity entity:entities){
            departments.add((Department)entity);
        }
    }
}

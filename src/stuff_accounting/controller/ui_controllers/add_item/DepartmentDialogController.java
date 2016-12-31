package stuff_accounting.controller.ui_controllers.add_item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stuff_accounting.MainApp;
import stuff_accounting.controller.ui_controllers.overview.EntityChooser;
import stuff_accounting.model.Const;
import stuff_accounting.model.dao.DepartmentDao;
import stuff_accounting.model.entity.BaseEntity;
import stuff_accounting.model.entity.Department;
import stuff_accounting.model.entity.Employee;
import stuff_accounting.model.services.DepartmentService;
import stuff_accounting.model.services.impl.DepartmentServiceImpl;
import stuff_accounting.view.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andri on 12/15/2016.
 */
public class DepartmentDialogController implements DialogController{
    @FXML
    private TextField departmentTitle;
    @FXML
    private Button stuffList;
    @FXML
    private Button addDepartment;
    @FXML
    private Button cancel;
    private Stage dialogStage;
    private List<Employee> employees;
    private MainApp mainApp;
    private DepartmentService departmentService= DepartmentServiceImpl.getInstance();

    public void onStufListChoose(ActionEvent event){
        employees=new ArrayList<>();
        new EntityChooser(Const.EMPLOYEE, mainApp,this).getEntityList();
        dialogStage.hide();
    }

    public void onAddDepartment(ActionEvent event){
        Department department = new Department();
        department.setTitle(departmentTitle.getText());
        department.setStuff(employees);
        try {
            departmentService.insert(department);
            Utilities.showAlert(Alert.AlertType.CONFIRMATION, null, "Confirmation", "Department was succesfully inserted");
        }
        catch (Exception ex){
            Utilities.showAlert(Alert.AlertType.ERROR, null, "Error!", "Error occured when inserting department...");
            ex.printStackTrace();
        }
    }

    public void onCancel(ActionEvent event) {
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
        for(BaseEntity entity:entities)
            employees.add((Employee)entity);
    }
}

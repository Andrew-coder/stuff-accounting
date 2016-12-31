package stuff_accounting.controller.ui_controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stuff_accounting.MainApp;
import stuff_accounting.controller.ui_controllers.add_item.EmployeeDialogController;
import stuff_accounting.controller.ui_controllers.overview.EntityOverviewController;
import stuff_accounting.model.Const;
import stuff_accounting.view.utilities.Utilities;

import java.io.IOException;

public class AdminPanelController {
    @FXML
    private Button addEmployee;
    @FXML
    private Button addPost;
    @FXML
    private Button addEducation;
    @FXML
    private Button addDepartment;
    @FXML
    private Button addSubdivision;
    @FXML
    private Button overview;
    @FXML
    private ChoiceBox componentTypeBox;
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize(){
        componentTypeBox.setItems(FXCollections.observableArrayList(Const.EMPLOYEE, Const.POST,
                                                                    Const.EDUCATION, Const.DEPARTMENT,
                                                                    Const.SUBDIVISION));
        componentTypeBox.setValue(Const.EMPLOYEE);
    }

    public void onAddEmployee(ActionEvent event){
        EmployeeDialogController dialogController = (EmployeeDialogController) Utilities.showDialog(mainApp,Const.ADD_EMPLOYEE, "adding employee");
        dialogController.setInsertMode();
    }

    public void onAddPost(ActionEvent event){
        Utilities.showDialog(mainApp,Const.ADD_POST, "adding post");
    }

    public void onAddEducation(ActionEvent event){
        Utilities.showDialog(mainApp,Const.ADD_EDUCATION, "adding education");
    }

    public void onAddDepartment(ActionEvent event){
        Utilities.showDialog(mainApp,Const.ADD_DEPARTMENT, "adding department");
    }

    public void onAddSubdivision(ActionEvent event){
        Utilities.showDialog(mainApp,Const.ADD_SUBDIVISION, "adding subdivision");
    }

    public void onOverview(ActionEvent event){
        if(componentTypeBox.getValue().equals("employee")) {
            mainApp.showEmployeeOverview();
        }
        else if(componentTypeBox.getValue()!=null){
            try {
                // initializing root layout
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource(Const.ROOT_LAYOUT));
                BorderPane rootLayout = loader.load();
                // the scene containing root layout
                Stage stage = new Stage();
                stage.setTitle("Viewing " + componentTypeBox.getValue().toString());
                stage.setScene(new Scene(rootLayout));
                // loader for EntityOverview.fxml
                FXMLLoader overviewLoader = new FXMLLoader();
                overviewLoader.setLocation(mainApp.getClass().getResource(Const.COMPONENTS_OVERVIEW));
                AnchorPane componentsOverView = overviewLoader.load();
                EntityOverviewController controller = overviewLoader.getController();
                controller.setComponentType(componentTypeBox.getValue().toString());
                controller.setParents(rootLayout, stage);
                controller.ShowFilters();
                controller.showAdminButtons();
                rootLayout.setCenter(componentsOverView);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Utilities.showAlert(Alert.AlertType.ERROR,mainApp.getPrimaryStage(),"Type not chosen",
                    "Please, choose type first");
        }
    }
}

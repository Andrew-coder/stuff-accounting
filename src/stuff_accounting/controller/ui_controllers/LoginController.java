package stuff_accounting.controller.ui_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import stuff_accounting.MainApp;
import stuff_accounting.model.entity.Admin;
import stuff_accounting.model.services.AdminService;
import stuff_accounting.model.services.impl.AdminServiceImpl;
import stuff_accounting.view.utilities.Utilities;


/**
 * Created by andri on 12/14/2016.
 */
public class LoginController {
    @FXML
    private TextField nicknameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitInfo;

    private MainApp mainApp;
    AdminService adminService = AdminServiceImpl.getInstance();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void onSubmit(ActionEvent event){
        String login = nicknameField.getText();
        String password = passwordField.getText();
        Admin admin = new Admin(login, password);
        if(adminService.checkAdminInfo(admin)){
            Utilities.showAlert(Alert.AlertType.CONFIRMATION,mainApp.getPrimaryStage(),"Confirmation","Logged in " +
                    "Successfully");
            mainApp.showAdminPanel();
            mainApp.showHeader();
        }
        else {
            Utilities.showAlert(Alert.AlertType.ERROR, mainApp.getPrimaryStage(), "Error!", "login or password is incorrect");
        }
    }
}

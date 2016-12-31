package stuff_accounting.controller.ui_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import stuff_accounting.MainApp;
import stuff_accounting.view.utilities.Utilities;

/**
 * Created by andri on 12/14/2016.
 */
public class HeaderController {
    @FXML
    private Button mainPage;
    @FXML
    private Button logOutPage;
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    public void initialize() {
    }

    public void refresh(){


    }

    @FXML
    public void logOutHandler(){
        mainApp.getRootLayout().setTop(null);
        mainApp.getRootLayout().setLeft(null);
        mainApp.showLoginWindow();
        //mainApp.setUserID(0);
    }

    @FXML
    public void mainPageHandler(){
        Utilities.resetLayout(mainApp.getRootLayout());
        mainApp.showAdminPanel();
    }
}

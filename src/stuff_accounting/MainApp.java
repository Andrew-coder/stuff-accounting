package stuff_accounting;

import javafx.scene.layout.AnchorPane;
import stuff_accounting.controller.ui_controllers.AdminPanelController;
import stuff_accounting.controller.ui_controllers.add_item.DepartmentDialogController;
import stuff_accounting.controller.ui_controllers.overview.EmployeeOverviewController;
import stuff_accounting.controller.ui_controllers.HeaderController;
import stuff_accounting.controller.ui_controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import stuff_accounting.controller.ui_controllers.overview.EntityChooser;

import java.io.IOException;


public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Stuff accounting");
        initRootLayout();
        showLoginWindow();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/stuff_accounting/view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/stuff_accounting/view/LoginWindow.fxml"));
        try {
            Pane loginWindow = loader.load();
            LoginController loginController = loader.getController();
            loginController.setMainApp(this);

            rootLayout.setCenter(loginWindow);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEmployeeOverview(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/stuff_accounting/view/admin/EmployeeOverview.fxml"));
        try {
            BorderPane employeeOverview = loader.load();
            EmployeeOverviewController employeeController =  loader.getController();
            employeeController.setMainApp(this);
            employeeController.setOverviewMode();
            rootLayout.setCenter(employeeOverview);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAdminPanel() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/stuff_accounting/view/admin/AdminPanel.fxml"));
        try {
            Pane adminPanel = loader.load();
            AdminPanelController adminPanelController  = loader.getController();
            adminPanelController.setMainApp(this);
            rootLayout.setCenter(adminPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHeader(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/stuff_accounting/view/Header.fxml"));
        try {
            AnchorPane header = loader.load();

            HeaderController headerController = loader.getController();
            headerController.setMainApp(this);
            headerController.refresh();
            rootLayout.setTop(header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

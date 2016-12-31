package stuff_accounting.controller.ui_controllers.overview;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stuff_accounting.MainApp;
import stuff_accounting.controller.ui_controllers.add_item.DepartmentDialogController;
import stuff_accounting.controller.ui_controllers.add_item.DialogController;
import stuff_accounting.model.Const;
import stuff_accounting.model.entity.BaseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andri on 12/24/2016.
 */
public class EntityChooser {
    private List<BaseEntity> entities;
    private String type;
    private MainApp mainApp;
    private Stage stage;
    private DialogController dialogController;

    public EntityChooser(String type, MainApp mainApp){
        this.type = type;
        this.mainApp = mainApp;
        entities = new ArrayList<>();
        stage = new Stage();
    }

    public EntityChooser(String type, MainApp mainApp, DialogController dialogController){
        this.type = type;
        this.mainApp = mainApp;
        this.dialogController = dialogController;
        entities = new ArrayList<>();
        stage = new Stage();
    }

    public BaseEntity getEntity(){
        if(!type.equals(Const.EMPLOYEE))
            initChooser();
        else {
            initEmployeeChooser();
        }
        return entities.get(0);
    }

    public List<BaseEntity> getEntityList(){
        if(!type.equals(Const.EMPLOYEE))
            initChooser();
        else {
            initEmployeeChooser();
        }
        return entities;
    }

    private void initChooser() {
        try {
            // initializing root layout
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(Const.ROOT_LAYOUT));
            BorderPane rootLayout = loader.load();

            // the scene containing root layout
            stage.setTitle("Chosing " + type.toString());
            stage.setScene(new Scene(rootLayout));

            // loader for EntityOverview.fxml
            FXMLLoader overviewLoader = new FXMLLoader();
            overviewLoader.setLocation(mainApp.getClass().getResource(Const.COMPONENTS_OVERVIEW));
            AnchorPane componentsOverView = overviewLoader.load();

            EntityOverviewController controller = overviewLoader.getController();

            controller.setComponentType(this.type);
            controller.setChooser(this);
            controller.setParents(rootLayout, stage);
            controller.ShowFilters();

            rootLayout.setCenter(componentsOverView);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initEmployeeChooser(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(Const.EMPLOYEE_OVERVIEW));
        try {
            BorderPane employeeOverview = loader.load();
            EmployeeOverviewController employeeController =  loader.getController();
            employeeController.setChooseMode();
            employeeController.setChooser(this);
            employeeController.setCurrentStage(stage);
            mainApp.getRootLayout().setCenter(employeeOverview);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEntities(List<BaseEntity> entities) {
        this.entities = entities;
        if(dialogController!=null && dialogController instanceof DepartmentDialogController){
            dialogController.setEntities(entities);
            dialogController.getDialogStage().showAndWait();
        }
    }

    public Stage getStage() {
        return stage;
    }
}

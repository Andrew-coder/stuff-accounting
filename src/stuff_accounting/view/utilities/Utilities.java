package stuff_accounting.view.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import stuff_accounting.MainApp;
import stuff_accounting.controller.ui_controllers.add_item.DialogController;

import java.io.IOException;

/**
 * Created by andri on 12/15/2016.
 */
public class Utilities {
    public static void showAlert(Alert.AlertType alertType, Stage dialogStage, String title, String content){
        Alert alert = new Alert(alertType);
        alert.initOwner(dialogStage);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void resetLayout(BorderPane pane){
        // Do not touch top -> top is for Header
        pane.setCenter(null);
        pane.setLeft(null);
        pane.setRight(null);
        pane.setBottom(null);
    }

    public static DialogController showDialog(MainApp mainApp, String fxmlPath, String title){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(fxmlPath));
            AnchorPane page = loader.load();

            // create the dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(mainApp);
            dialogStage.show();
            return controller;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}

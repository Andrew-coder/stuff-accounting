package stuff_accounting.controller.ui_controllers.add_item;

import javafx.stage.Stage;
import stuff_accounting.MainApp;
import stuff_accounting.model.entity.BaseEntity;

import java.util.List;

/**
 * Created by andri on 12/15/2016.
 */
public interface DialogController {
    void setDialogStage(Stage stage);
    Stage getDialogStage();
    void setMainApp(MainApp mainApp);
    void setEntities(List<BaseEntity> entities);
    boolean isOkClicked();
}

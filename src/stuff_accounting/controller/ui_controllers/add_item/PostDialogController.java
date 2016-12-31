package stuff_accounting.controller.ui_controllers.add_item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stuff_accounting.MainApp;
import stuff_accounting.model.Const;
import stuff_accounting.model.entity.BaseEntity;
import stuff_accounting.model.entity.Post;
import stuff_accounting.model.services.PostService;
import stuff_accounting.model.services.impl.PostServiceImpl;
import stuff_accounting.view.utilities.Utilities;

import java.util.List;

/**
 * Created by andri on 12/15/2016.
 */
public class PostDialogController implements DialogController{
    @FXML
    private TextField postName;
    @FXML
    private TextField salary;
    @FXML
    private Button addPost;
    @FXML
    private Button cancelAddingPost;
    private Stage dialogStage;
    private MainApp mainApp;
    PostService postService = PostServiceImpl.getInstance();

    public void onAddPost(ActionEvent event) {
        if(checkValidData()) {
            Post post = new Post();

            post.setPostName(postName.getText());
            post.setSalary(Double.parseDouble(salary.getText()));
            try {
                postService.insert(post);
                Utilities.showAlert(Alert.AlertType.CONFIRMATION, null, "Confirmation", "Post was succesfully inserted");
            }
            catch (Exception ex){
                Utilities.showAlert(Alert.AlertType.ERROR, null, "Error!", "Error occured when inserting post...");
                ex.printStackTrace();
            }

        }
        else
            Utilities.showAlert(Alert.AlertType.ERROR, null, "Error!", "post name or salary is incorrect");
    }

    public void onCancelAddingPost(ActionEvent event) {
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
        if(postName.getText().equals("") || salary.getText().equals(""))
            return false;
        if(!salary.getText().matches(Const.SALARY_REGEX))
            return false;
        return true;
    }
}

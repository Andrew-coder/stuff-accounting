package stuff_accounting.controller.ui_controllers.overview;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import stuff_accounting.MainApp;
import stuff_accounting.model.Const;
import stuff_accounting.model.entity.*;
import stuff_accounting.model.services.DepartmentService;
import stuff_accounting.model.services.EducationService;
import stuff_accounting.model.services.PostService;
import stuff_accounting.model.services.SubdivisionService;
import stuff_accounting.model.services.impl.DepartmentServiceImpl;
import stuff_accounting.model.services.impl.EducationServiceImpl;
import stuff_accounting.model.services.impl.PostServiceImpl;
import stuff_accounting.model.services.impl.SubdivisionServiceImpl;
import stuff_accounting.view.utilities.Utilities;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.swing.*;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by andri on 12/17/2016.
 */
public class EntityOverviewController {
    @FXML
    private TableView componentTableView;
        @FXML
        private TableColumn<BaseEntity, Integer> idColumn;
        @FXML
        private TableColumn<BaseEntity, String> nameColumn;
    @FXML
    private Button chooseButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label detailsLabel;

    private Stage currentStage;
    private MainApp mainApp;
    private BorderPane root;
    private String componentType;
    private EntityChooser chooser;
    private ComponentFilterController componentController;

    @FXML
    public void initialize(){
        componentTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        componentTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showComponentDetails(newValue));
        detailsLabel.setText("");
        detailsLabel.setFont(Font.font("monospace", 12));
    }

    public void chooseButtonHandler(ActionEvent event){
        if (componentTableView.getSelectionModel().getSelectedItem() != null) {
            chooser.setEntities(componentTableView.getSelectionModel().getSelectedItems());
            this.currentStage.close();
        } else {
            Utilities.showAlert(Alert.AlertType.ERROR, currentStage,"Error","Please select item!");
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.root = mainApp.getRootLayout();
    }

    public void setParents(BorderPane pane,Stage stage){
        this.root = pane;
        this.currentStage = stage;
    }

    public void showAdminButtons(){
        this.deleteButton.setVisible(true);
        this.chooseButton.setVisible(false);
    }

    public void ShowFilters(){
        FXMLLoader loader = new FXMLLoader();

        // depending on component type path is set to needed
        String fxmlControllerPath = "/stuff_accounting/view/overview/" + componentType + "Overview.fxml";

        loader.setLocation(MainApp.class.getResource(fxmlControllerPath));
        try {
            AnchorPane filter = loader.load();
            componentController =  loader.getController();
            root.setLeft(filter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshOnFilters(){
        switch (componentType){
            case Const.SUBDIVISION:
                filterSubdivision();
                break;
            case Const.DEPARTMENT:
                filterDepartment();
                break;
            case Const.POST:
                filterPost();
                break;
            case Const.EDUCATION:
                filterEducation();
                break;
        }
    }

    public void filterDepartment(){
        DepartmentOverviewController departmentController=(DepartmentOverviewController)componentController;
        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Department)cellData.getValue()).getTitle()));
        List<Department> departments = departmentService.getAll();
        String depTitle=departmentController.getDepartmentName().getText();
        if(!depTitle.isEmpty()){
            departments=departments.stream().filter(department -> department.getTitle().equals(depTitle)).
                    collect(Collectors.toList());
        }
        if(!departmentController.getEmployeesMin().getText().isEmpty()){
            int min=Integer.parseInt(departmentController.getEmployeesMin().getText());
            departments = departments.stream().filter(department -> department.getStuff().size()>=min).
                    collect(Collectors.toList());
        }
        if(!departmentController.getEmployeesMax().getText().isEmpty()){
            int max=Integer.parseInt(departmentController.getEmployeesMax().getText());
            departments = departments.stream().filter(department -> department.getStuff().size()<=max).
                    collect(Collectors.toList());
        }
        componentTableView.setItems(FXCollections.observableArrayList(departments));
    }

    public void filterSubdivision(){
        SubdivisionService subdivisionService = SubdivisionServiceImpl.getInstance();
        SubdivisionOverviewController subdivisionController = (SubdivisionOverviewController)componentController;
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Subdivision)cellData.getValue()).getDivisionCode()));
        List<Subdivision> subdivisions= subdivisionService.getAll();
        String subTitle=subdivisionController.getSubdivisionName().getText();
        if(!subTitle.isEmpty()){
            subdivisions = subdivisions.stream().filter(subdivision -> subdivision.getDivisionCode().equals(subTitle)).
                    collect(Collectors.toList());
        }
        if(!subdivisionController.getDepartmentsNumber().getText().isEmpty()){
            int depNumber=Integer.parseInt(subdivisionController.getDepartmentsNumber().getText());
            subdivisions = subdivisions.stream().filter(subdivision -> subdivision.getDepartments().size()==depNumber).
                    collect(Collectors.toList());
        }
        componentTableView.setItems(FXCollections.observableArrayList(subdivisions));
    }

    public void filterEducation(){
        EducationService educationService = EducationServiceImpl.getInstance();
        EducationOverviewController educationController = (EducationOverviewController) componentController;
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Education)cellData.getValue()).getSpeciality()));
        List<Education> educations= educationService.getAll();
        String speciality = educationController.getSpeciality().getText();
        if(!speciality.isEmpty()){
            educations = educations.stream().filter(education -> education.getSpeciality().equals(speciality)).
                    collect(Collectors.toList());
        }
        if(educationController.getEducationForm().getValue()!= null){
            educations=educations.stream().filter(education -> education.getEducationForm().
                    equals(educationController.getEducationForm().getValue().toString())).
                    collect(Collectors.toList());
        }
        if(educationController.getEducationType().getValue()!=null){
            educations=educations.stream().filter(education -> education.getEducationType().
                    equals(educationController.getEducationType().getValue().toString()+" education")).
                    collect(Collectors.toList());
        }
        componentTableView.setItems(FXCollections.observableArrayList(educations));
    }

    public void filterPost(){
        PostService postService = PostServiceImpl.getInstance();
        PostOverviewController postController = (PostOverviewController)componentController;
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Post)cellData.getValue()).getPostName()));
        List<Post> posts= postService.getAll();
        String postName= postController.getPostName().getText();
        if(!postName.isEmpty()){
            posts = posts.stream().filter(post -> post.getPostName().equals(postName)).
                    collect(Collectors.toList());
        }
        if(!postController.getSalaryFrom().getText().isEmpty()){
            double min=Double.parseDouble(postController.getSalaryFrom().getText());
            posts = posts.stream().filter(post -> post.getSalary()>=min).
                    collect(Collectors.toList());
        }
        if(!postController.getSalaryTo().getText().isEmpty()){
            double max=Double.parseDouble(postController.getSalaryTo().getText());
            posts = posts.stream().filter(post -> post.getSalary()<=max).
                    collect(Collectors.toList());
        }
        componentTableView.setItems(FXCollections.observableArrayList(posts));
    }

    public void deleteButtonHandler(ActionEvent event){
        if (componentTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are ypu sure you want to delete item?");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if(deleteComponent(componentType,componentTableView.getSelectionModel().getSelectedItem())){
                    Utilities.showAlert(Alert.AlertType.CONFIRMATION, this.currentStage, "Success", "Item deleted!");
                } else {
                    Utilities.showAlert(Alert.AlertType.ERROR, currentStage,"Error","Item is used in computer configuration!");
                }
                refreshOnFilters();
            } else {

            }
        } else {
            Utilities.showAlert(Alert.AlertType.ERROR, currentStage,"Error","Please select item!");
        }
    }

    public boolean deleteComponent(String componentType, Object item){
        try {
            switch (componentType) {
                case Const.DEPARTMENT:
                    return deleteDepartment(item);
                case Const.SUBDIVISION:
                    return deleteSubdivision(item);
                case Const.POST:
                    PostServiceImpl.getInstance().deleteById(((BaseEntity)item).getId());
                    break;
                case Const.EDUCATION:
                    EducationServiceImpl.getInstance().deleteById(((BaseEntity)item).getId());
                    break;
            }
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteDepartment(Object item){
        DepartmentService departmentService = DepartmentServiceImpl.getInstance();
        if(!((Department)item).getStuff().isEmpty()){
            Utilities.showAlert(Alert.AlertType.ERROR, currentStage, "Error", "Department contains stuff!");
            return false;
        }
        else{
            departmentService.deleteById(((BaseEntity)item).getId());
            return true;
        }
    }

    public boolean deleteSubdivision(Object item){
        SubdivisionService subdivisionService = SubdivisionServiceImpl.getInstance();
        if(!((Subdivision)item).getDepartments().isEmpty()){
            Utilities.showAlert(Alert.AlertType.ERROR, currentStage, "Error", "Subdivision contains departments!");
            return false;
        }
        else {
            subdivisionService.deleteById(((BaseEntity)item).getId());
            return true;
        }
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    private void showComponentDetails(Object newValue) {
        if(newValue!=null) {
            switch (componentType){
                case Const.EDUCATION:
                    detailsLabel.setText(((Education)newValue).toString());
                    break;
                case Const.POST:
                    detailsLabel.setText(((Post)newValue).toString());
                    break;
                case Const.DEPARTMENT:
                    detailsLabel.setText(((Department)newValue).toString());
                    break;
                case Const.SUBDIVISION:
                    detailsLabel.setText(((Subdivision)newValue).toString());
                    break;
                case Const.EMPLOYEE:
                    detailsLabel.setText(((Employee)newValue).toString());
                    break;
            }
            detailsLabel.setText(newValue.toString());
        } else {
            detailsLabel.setText("");
        }
    }

    public String getComponentType() {
        return componentType;
    }

    public EntityChooser getChooser() {
        return chooser;
    }

    public void setChooser(EntityChooser chooser) {
        this.chooser = chooser;
    }
}

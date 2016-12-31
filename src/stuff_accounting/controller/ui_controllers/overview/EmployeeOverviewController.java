package stuff_accounting.controller.ui_controllers.overview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import stuff_accounting.MainApp;
import stuff_accounting.controller.ui_controllers.add_item.DepartmentDialogController;
import stuff_accounting.controller.ui_controllers.add_item.EmployeeDialogController;
import stuff_accounting.model.Const;
import stuff_accounting.model.entity.Department;
import stuff_accounting.model.entity.Education;
import stuff_accounting.model.entity.Employee;
import stuff_accounting.model.entity.Post;
import stuff_accounting.model.services.EmployeeService;
import stuff_accounting.model.services.impl.EmployeeServiceImpl;
import stuff_accounting.view.utilities.Utilities;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeOverviewController implements ComponentFilterController {
    @FXML
    private TextField employeeName;
    @FXML
    private ChoiceBox educationType;
    @FXML
    private CheckBox optional;
    @FXML
    private CheckBox fitness;
    @FXML
    private CheckBox married;
    @FXML
    private TextField country;
    @FXML
    private Button department;
    @FXML
    private Button filter;
    @FXML
    private Button deleteEmployee;
    @FXML
    private TableView employeeTableView;
        @FXML
        TableColumn<Employee, Integer> idColumn;
        @FXML
        TableColumn<Employee,String> nameColumn;
    @FXML
    private Label detailsLabel;
    @FXML
    private Label selectedDepartment;
    @FXML
    private Button chooseEmployees;
    private Stage currentStage;
    private MainApp mainApp;
    private Department chosenDepartment;
    private EntityChooser chooser;
    private EmployeeDialogController dialogController;
    EmployeeService employeeService = EmployeeServiceImpl.getInstance();

    @FXML
    public void initialize() {
        educationType.setItems(Const.EDUCATION_TYPES);
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        employeeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        employeeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showComponentDetails(newValue));
        detailsLabel.setText("");
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void onFilter(ActionEvent event){
        List<Employee>employees = employeeService.getAll();
        if(!employeeName.getText().isEmpty()){
            employees = employees.stream().filter(employee ->employee.getName().equals(employeeName.getText())).collect(Collectors.toList());
        }
        if(educationType.getValue()!= null){
            String choosenType=educationType.getValue().toString()+" education";
            employees = employees.stream().filter(employee -> employee.getEducations()!=null).
                    filter(employee -> !employee.getEducations().isEmpty()).
                    collect(Collectors.toList());
            Iterator<Employee> iterator=employees.iterator();
            while(iterator.hasNext()){
                List<String> educations= new ArrayList<>();
                for(Education e:iterator.next().getEducations())
                    educations.add(e.getEducationType());
                if(!educations.contains(choosenType))
                    iterator.remove();
            }
        }
        if(!country.getText().isEmpty()){
            employees = employees.stream().filter(employee -> employee.getCountry().equals(country.getText())).
                    collect(Collectors.toList());
        }
        if(chosenDepartment!=null){
            List<String> employeeNames = new ArrayList<>();
            for(Employee employee:chosenDepartment.getStuff()){
                employeeNames.add(employee.getName());
            }
            employees = employees.stream().filter(employee ->employeeNames.contains(employee.getName())).
                    collect(Collectors.toList());
        }
        if(optional.isSelected()) {
            employees = employees.stream().filter(employee -> employee.getFitness().equals(fitness.isSelected())).
                    collect(Collectors.toList());
            if (married.isSelected()) {
                employees = employees.stream().filter(employee -> employee.getPartnerName() != null).
                        collect(Collectors.toList());
            } else {
                employees = employees.stream().filter(employee -> employee.getPartnerName() == null).
                        collect(Collectors.toList());
            }
        }
        ObservableList<Employee> employeeFinalList= FXCollections.observableArrayList(employees);
        employeeTableView.setItems(employeeFinalList);
    }

    private void showComponentDetails(Object newValue) {
        if(newValue!=null) {
            detailsLabel.setText(((Employee)newValue).toString());
        } else {
            detailsLabel.setText("");
        }
    }

    public void onDeleteEmployee(ActionEvent event){
        List<Employee> employees = employeeTableView.getSelectionModel().getSelectedItems();
        if(employees==null || employees.isEmpty()) {
            Utilities.showAlert(Alert.AlertType.ERROR, currentStage, "Error", "Please select item!");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are ypu sure you want to delete item?");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                employeeService.delete(employees);
            }
            else {

            }
        }
    }

    public void setCurrentStage(Stage stage){
        this.currentStage = stage;
    }

    public void onSelectOptional(ActionEvent event){
        if(optional.isSelected()){
            married.setDisable(false);
            fitness.setDisable(false);
        }
        else{
            married.setDisable(true);
            fitness.setDisable(true);
        }
    }

    public void onDepartmentChoose(ActionEvent event){
        chosenDepartment = (Department) new EntityChooser(Const.DEPARTMENT, mainApp).getEntity();
        selectedDepartment.setVisible(true);
        selectedDepartment.setText(chosenDepartment.getTitle());
    }

    public void onChooseEmployees(){
        if (employeeTableView.getSelectionModel().getSelectedItems() != null) {
            chooser.setEntities(employeeTableView.getSelectionModel().getSelectedItems());
            this.currentStage.close();
        } else {
            Utilities.showAlert(Alert.AlertType.ERROR, currentStage,"Error","Please select item!");
        }
    }

    public void setChooseMode(){
        chooseEmployees.setVisible(true);
        deleteEmployee.setVisible(false);
    }

    public void setOverviewMode(){
        chooseEmployees.setVisible(false);
        deleteEmployee.setVisible(true);
    }

    public void setChooser(EntityChooser chooser) {
        this.chooser = chooser;
    }

    public void onUpdateEmployee(){
        Employee employee = (Employee) employeeTableView.getSelectionModel().getSelectedItem();
        dialogController = (EmployeeDialogController) Utilities.showDialog(mainApp,Const.ADD_EMPLOYEE, "update employee");
        dialogController.fillDialog(employee);
        dialogController.setUpdateMode();
        dialogController.setPost(employee.getPost());
    }
}

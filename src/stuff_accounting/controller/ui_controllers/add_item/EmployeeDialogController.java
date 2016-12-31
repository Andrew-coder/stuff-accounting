package stuff_accounting.controller.ui_controllers.add_item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import stuff_accounting.MainApp;
import stuff_accounting.controller.ui_controllers.overview.EntityChooser;
import stuff_accounting.model.Const;
import stuff_accounting.model.dao.EmployeeDao;
import stuff_accounting.model.entity.*;
import stuff_accounting.model.services.EmployeeService;
import stuff_accounting.model.services.impl.EmployeeServiceImpl;
import stuff_accounting.view.utilities.Utilities;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andri on 12/15/2016.
 */
public class EmployeeDialogController implements DialogController {
    @FXML
    private TextField name;
    @FXML
    private Button choosePost;
    @FXML
    private Button chooseEducation;
    @FXML
    private Button chooseDepartment;
    @FXML
    private Label postInfo;
    @FXML
    private Label departmentInfo;
    @FXML
    private Label educationInfo;
    @FXML
    private TextField countryName;
    @FXML
    private DatePicker receiptDate;
    @FXML
    private TextField partnerName;
    @FXML
    private ChoiceBox childrenAmount;
    @FXML
    private CheckBox militaryFitness;
    @FXML
    private ChoiceBox stockCategory;
    @FXML
    private TextField passportData;
    @FXML
    private TextField rectal;
    @FXML
    private Button addEmployee;
    @FXML
    private Button updateEmployee;
    private MainApp mainApp;
    private Stage dialogStage;
    private Department department;
    private Post post;
    private List<Education> educations;

    private EmployeeService employeeService = EmployeeServiceImpl.getInstance();

    @FXML
    public void initialize(){
        childrenAmount.setItems(Const.CHILDRENS);
        stockCategory.setItems(Const.STOCK_CATEGORIES);
    }

    public void onAddEmployee(ActionEvent event){
        if(checkValidData()){
            Employee employee = new Employee();
            employee.setName(name.getText());
            employee.setPost(post);
            employee.setEducations(educations);
            employee.setCountry(countryName.getText());
            employee.setReceiptDate(Date.from(receiptDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if(!partnerName.getText().isEmpty())
                employee.setPartnerName(partnerName.getText());
            if(childrenAmount.getValue()!=null) {
                if(!childrenAmount.getValue().toString().equals("more"))
                    employee.setChildrenAmount(Integer.parseInt(childrenAmount.getValue().toString()));
                else
                    employee.setChildrenAmount(2);
            }
            employee.setFitness(militaryFitness.isSelected());
            employee.setStockCategory(stockCategory.getValue().toString());
            employee.setPassportData(passportData.getText());
            employee.setRectal(rectal.getText());
            try {
                employeeService.insertInDepartment(employee, department.getId());
                Utilities.showAlert(Alert.AlertType.CONFIRMATION, null, "Confirmation", "Employee was succesfully inserted");
            }
            catch (Exception ex){
                Utilities.showAlert(Alert.AlertType.ERROR, null, "Error!", "Error occured when inserting employee...");
                ex.printStackTrace();
            }
        }
        else
            Utilities.showAlert(Alert.AlertType.ERROR, null, "Error!", "input values is incorrect!");
    }

    public void onCancel(ActionEvent event) {

    }

    @Override
    public void setDialogStage(Stage stage) {

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

    public void onChoosePost(){
        post = (Post) new EntityChooser(Const.POST, mainApp).getEntity();
        postInfo.setText(post.getPostName());
    }

    public void onChooseEducation(){
        List<BaseEntity> entities = new EntityChooser(Const.EDUCATION, mainApp).getEntityList();
        educations = new ArrayList<>();
        for(BaseEntity entity:entities){
            educations.add((Education)entity);
        }
        educationInfo.setText(educations.get(0).getSpeciality());
    }

    public void onChooseDepartment(){
        department = (Department) new EntityChooser(Const.DEPARTMENT, mainApp).getEntity();
        departmentInfo.setText(department.getTitle());
    }

    public boolean checkValidData(){
        if(name.getText().isEmpty()||receiptDate.getValue()==null||stockCategory.getValue()==null||
                passportData.getText().isEmpty()||rectal.getText().isEmpty()|| countryName.getText().isEmpty())
            return false;
        return true;

    }

    public void fillDialog(Employee employee){
        name.setText(employee.getName());
        postInfo.setText(employee.getPost().getPostName());
        if(employee.getEducations()!=null && !employee.getEducations().isEmpty())
            educationInfo.setText(employee.getEducations().get(0).getSpeciality());
        countryName.setText(employee.getCountry());
        //receiptDate.setValue(employee.getReceiptDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if(!employee.getPartnerName().isEmpty())
            partnerName.setText(employee.getPartnerName());
        militaryFitness.setSelected(employee.getFitness());
        stockCategory.setValue(employee.getStockCategory());
        passportData.setText(employee.getPassportData());
        rectal.setText(employee.getRectal());
    }

    public void onUpdateEmployee(){
        if(checkValidData()){
            Employee employee = new Employee();
            employee.setName(name.getText());
            employee.setPost(post);
            employee.setEducations(educations);
            employee.setCountry(countryName.getText());
            employee.setReceiptDate(Date.from(receiptDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if(!partnerName.getText().isEmpty())
                employee.setPartnerName(partnerName.getText());
            if(childrenAmount.getValue()!=null) {
                if(!childrenAmount.getValue().toString().equals("more"))
                    employee.setChildrenAmount(Integer.parseInt(childrenAmount.getValue().toString()));
                else
                    employee.setChildrenAmount(2);
            }
            employee.setDismissial(false);
            employee.setFitness(militaryFitness.isSelected());
            employee.setStockCategory(stockCategory.getValue().toString());
            employee.setPassportData(passportData.getText());
            employee.setRectal(rectal.getText());
            try {
                if(department!=null)
                    employeeService.updateInDepartment(employee, department.getId());
                else
                    employeeService.update(employee);
                Utilities.showAlert(Alert.AlertType.CONFIRMATION, null, "Confirmation", "Employee was succesfully updated");
            }
            catch (Exception ex){
                Utilities.showAlert(Alert.AlertType.ERROR, null, "Error!", "Error occured when updating employee...");
                ex.printStackTrace();
            }
        }
        else
            Utilities.showAlert(Alert.AlertType.ERROR, null, "Error!", "input values is incorrect!");
    }

    public void setUpdateMode(){
        updateEmployee.setVisible(true);
        addEmployee.setVisible(false);
    }

    public void setInsertMode(){
        updateEmployee.setVisible(false);
        addEmployee.setVisible(true);
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

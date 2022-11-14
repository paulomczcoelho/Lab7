import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class HomeController implements Initializable{

    TextField editName = new TextField();
    TextField editDepartment = new TextField();
    
    @FXML
    private TextField name;
    @FXML
    private TextField department;
    
    @FXML
    private TableView<EmployeeData> employeeDataTableView;
    @FXML
    private TableColumn<EmployeeData, String> idColumn;
    @FXML
    private TableColumn<EmployeeData, String> nameColumn;
    @FXML
    private TableColumn<EmployeeData, String> departmentColumn;

    @FXML
    private Button addEntryBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;

    Dialog<ButtonType> dialog = null;
    Alert alert = new Alert(AlertType.NONE);
    //instantiate a model
    HomeModel homeModel = null;

    private String editIdString;
    private String editNameString;
    private String editDepartmentString;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.homeModel = new HomeModel();
        this.loadEmployeeData();  
        
        //disable delete and edit buttons
        editButton.setDisable(true);
        deleteButton.setDisable(true);

        employeeDataTableView.setOnMouseClicked(e -> {
            EmployeeData selected = employeeDataTableView.getSelectionModel().getSelectedItem();
            
            if(selected != null){
                deleteButton.setDisable(false);
                editButton.setDisable(false);

                editIdString = selected.idProperty().getValue();
                editNameString = selected.nameProperty().getValue();
                editDepartmentString = selected.departmentProperty().getValue();
            }
        });
    }

    //load data
    @FXML
    public void loadEmployeeData(){

        this.idColumn.setCellValueFactory( new PropertyValueFactory<EmployeeData, String>("id"));
        this.nameColumn.setCellValueFactory( new PropertyValueFactory<EmployeeData, String>("name"));
        this.departmentColumn.setCellValueFactory( new PropertyValueFactory<EmployeeData, String>("department"));

        this.employeeDataTableView.setItems(homeModel.getEmployees());
    }

    //add employee
    @FXML
    private void addEmployee(ActionEvent event){
        homeModel.addEmployee(this.name.getText(), this.department.getText());
        this.loadEmployeeData();
        this.clearFields(null);
    }

    //update employee
    @FXML
    private void editEmployee(ActionEvent event){

        //create modal
        createModal();

        //call the modal
        dialog.showAndWait().ifPresent(response -> {
            if(response.getButtonData().equals(ButtonData.OK_DONE)){
                homeModel.editEmployee(editIdString, editName.getText(), editDepartment.getText());
                this.loadEmployeeData();
            }
        });
    }
    
    //delete employee
    @FXML
    private void deleteEmployee(ActionEvent event){
        EmployeeData selectedItem = employeeDataTableView.getSelectionModel().getSelectedItem();
        //locally remove
        employeeDataTableView.getItems().remove(selectedItem);
        //delete from DB
        homeModel.deleteEmployee(selectedItem.idProperty().getValue());
    }

    //clear fields
    @FXML
    private void clearFields(ActionEvent event){
        this.name.setText("");
        this.department.setText("");
    }

    //create a modal
    private void createModal(){

        dialog = new Dialog<ButtonType>();

        dialog.setTitle("Edit an Employee");
        ButtonType editModalBtn = new ButtonType("Edit", ButtonData.OK_DONE);
        ButtonType cancelModalBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        //set the content
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);   
        gridPane.setVgap(10);   
        gridPane.setPadding(new Insets(20, 10, 10, 10));

        editName.setText(editNameString);
        editDepartment.setText(editDepartmentString);

        gridPane.add(new Label("Name"), 0, 0);
        gridPane.add(editName, 1, 0);
        gridPane.add(new Label("Department"), 0, 1);
        gridPane.add(editDepartment, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        dialog.getDialogPane().getButtonTypes().add(editModalBtn);
        dialog.getDialogPane().getButtonTypes().add(cancelModalBtn);
    }

}
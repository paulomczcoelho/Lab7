import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SignUpController implements Initializable{

    Alert a = new Alert(AlertType.NONE);
    SignUpModel signUpModel = new SignUpModel();

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button signUpBtn;
    @FXML
    private Label signUpStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    public void SignUp(ActionEvent event){

        if(this.username.getText() == "" && this.password.getText() == "" && this.confirmPassword.getText() == ""){
            a.setAlertType(AlertType.WARNING);
			a.setContentText("All fields are required");
			a.show();
        }else if(!this.password.getText().equals(this.confirmPassword.getText())){

                a.setAlertType(AlertType.ERROR);
                a.setContentText("Passwords do not match");
                a.show();
    
        }else{

            Boolean isValid = this.signUpModel.isSignUp(this.username.getText(), this.password.getText());

            if(isValid){
                loginPage();
            }else{
                this.signUpStatus.setText("Username already exists");
            }

        }

    }

    @FXML
    public void loginPage(){

        Stage stage = (Stage) this.signUpBtn.getScene().getWindow();
        stage.close();

        Stage loginStage = new Stage();
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));

            loginStage.setScene(scene);
            loginStage.setTitle("Login Page");
            loginStage.setResizable(false);
            loginStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

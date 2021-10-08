package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField phoneNumber;

    @FXML
    private PasswordField password;

    @FXML
    private Label comment;

    @FXML
    void logIn() throws IOException, ClassNotFoundException {
        String s1 = phoneNumber.getText();
        String s2 = password.getText();

        password.clear();

        LoginSupport loginSupport = new LoginSupport();
        if (loginSupport.accountExist(s1, s2)) {
            System.out.println("login successful");
            comment.setText("valid");
        } else comment.setText("invalid");
    }

    @FXML
    void newAccount(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("SignUpForm.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.setResizable(false);
        window.show();
    }
}

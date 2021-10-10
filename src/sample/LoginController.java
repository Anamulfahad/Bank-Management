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

import java.io.*;
import java.net.Socket;

public class LoginController {
    BufferedReader reader;
    BufferedWriter writer;

    @FXML
    private TextField phoneNumber;

    @FXML
    private PasswordField password;

    @FXML
    private Label comment;

    @FXML
    private void logIn(ActionEvent event) throws IOException, ClassNotFoundException {
        String s1 = phoneNumber.getText();
        String s2 = password.getText();

        password.clear();

        LoginSupport loginSupport = new LoginSupport();
        if (loginSupport.accountExist(s1, s2)) {
            System.out.println("login successful");
            comment.setText("valid");

            fetchInfo(s1);

            userView(event);
        } else comment.setText("invalid");
    }

    @FXML
    private void newAccount(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("SignUpForm.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.setResizable(false);
        window.show();
    }

    private void userView(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("UserView.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.setResizable(false);
        window.show();
    }

    private void fetchInfo(String phone) throws IOException {
        connectToServer();

        writer.write("fetchInfo\n");
        writer.flush();

        writer.write(phone + "\n");
        writer.flush();



    }

    private void connectToServer() throws IOException {
        Socket socket = new Socket("localhost", 1234);

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        reader = new BufferedReader(inputStreamReader);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        writer = new BufferedWriter(outputStreamWriter);
    }
}

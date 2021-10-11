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
import java.util.Objects;

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
        String s1 = phoneNumber.getText().trim();
        String s2 = password.getText().trim();

        if(s1.isEmpty() || s2.isEmpty()){
            comment.setText("field can't be empty");
            return;
        }

        password.clear();

        LoginSupport loginSupport = new LoginSupport();
        if (loginSupport.accountExist(s1, s2)) {
            if(s1.equals("admin")){
                changeToAdminView(event);
            }
            else {
                System.out.println("login successful");
                comment.setText("valid");

                fetchInfo(s1);
                changeToUserView(event);
            }

        } else comment.setText("invalid");
    }

    @FXML
    private void newAccount(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUpForm.fxml")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.setResizable(false);
        window.show();
    }

    private void changeToUserView(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserView.fxml")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.setResizable(false);
        window.show();
    }

    private void changeToAdminView(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Admin.fxml")));
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

        writeToFile(phone);
    }

    private void writeToFile(String phone) throws IOException {
        BufferedWriter bufferedWriter;
        bufferedWriter = new BufferedWriter(new FileWriter("src/sample/individual.txt"));

        bufferedWriter.write(phone+"\n");

        String line = reader.readLine();

        while (!line.equals("exit")) {
            bufferedWriter.write(line + "\n");
            bufferedWriter.flush();

            line = reader.readLine();
        }

    }

    private void connectToServer() throws IOException {
        Socket socket = new Socket("localhost", 1234);

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        reader = new BufferedReader(inputStreamReader);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        writer = new BufferedWriter(outputStreamWriter);
    }
}

package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserView {

    @FXML
    private Label name;

    @FXML
    private Label balance;

    @FXML
    public void initialize() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/sample/individual.txt"));

        String line = bufferedReader.readLine();

        line = bufferedReader.readLine();
        name.setText(line);

        line = bufferedReader.readLine();
        balance.setText(line);

        bufferedReader.close();
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        changeScene(event, "LoginForm.fxml");
    }

    @FXML
    void payment(ActionEvent event) throws IOException {
        changeScene(event, "Payment.fxml");
    }

    @FXML
    void sendMoney(ActionEvent event) throws IOException {
        changeScene(event, "SendMoney.fxml");
    }

    @FXML
    void statement(ActionEvent event) throws IOException {

    }

    @FXML
    void withdraw(ActionEvent event) throws IOException {
        changeScene(event, "Withdraw.fxml");
    }

    private void changeScene(ActionEvent event, String file) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(file));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.setResizable(false);
        window.show();
    }

}

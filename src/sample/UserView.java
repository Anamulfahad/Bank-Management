package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class UserView {

    @FXML
    private Label name;

    @FXML
    private Label balance;

    @FXML
    private TextArea showStatement;


    @FXML
    public void initialize() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/sample/individual.txt"));

        bufferedReader.readLine();
        String line;

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
    void statement() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/sample/individual.txt"));

//        trimming redundant lines
        bufferedReader.readLine();
        bufferedReader.readLine();
        bufferedReader.readLine();
        bufferedReader.readLine();

        String line = bufferedReader.readLine();
        if(line == null)
            showStatement.setText("no statements found");
        else {
            showStatement.clear();
            while (line != null) {
                showStatement.appendText(line + "\n");
                line = bufferedReader.readLine();
            }
        }
    }

    @FXML
    void withdraw(ActionEvent event) throws IOException {
        changeScene(event, "Withdraw.fxml");
    }

    private void changeScene(ActionEvent event, String fxmlFile) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.setResizable(false);
        window.show();
    }

}

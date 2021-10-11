package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Admin {
    BufferedReader reader;
    BufferedWriter writer;

    @FXML
    private TextField number;

    @FXML
    private TextField credit;

    @FXML
    private Label comment;

    @FXML
    void cashIn() throws IOException {
        String s1 = number.getText().trim();
        String s2 = credit.getText().trim();

        if(s1.isEmpty() || s2.isEmpty()){
            comment.setText("field can't be empty");
            return;
        }

        sendMoney(s1, s2);
        credit.clear();
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        changeScene(event);
    }

    private void sendMoney(String toPhone, String amount) throws IOException {
        connectToServer();

        writer.write("deposit\n");
        writer.flush();

        writer.write("admin\n");
        writer.flush();

        writer.write(toPhone + "\n");
        writer.flush();

        writer.write(amount + "\n");
        writer.flush();

        comment.setText("task complete");
    }

    private void connectToServer() throws IOException {
        Socket socket = new Socket("localhost", 1234);

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        reader = new BufferedReader(inputStreamReader);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        writer = new BufferedWriter(outputStreamWriter);
    }

    private void changeScene(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginForm.fxml")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.setResizable(false);
        window.show();
    }

}

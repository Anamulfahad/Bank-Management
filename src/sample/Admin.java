package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Admin {
    BufferedReader reader;
    BufferedWriter writer;

    @FXML
    private TextField number;

    @FXML
    private TextField credit;

    @FXML
    void cashIn(ActionEvent event) throws IOException {
        String s1 = number.getText();
        String s2 = credit.getText();
        sendMoney("admin", s1, s2);
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        changeScene(event, "LoginForm.fxml");
    }

    private void sendMoney(String fromPhone, String toPhone, String amount) throws IOException {
        connectToServer();

        writer.write("deposit\n");
        writer.flush();

        writer.write(fromPhone + "\n");
        writer.flush();

        writer.write(toPhone + "\n");
        writer.flush();

        writer.write(amount + "\n");
        writer.flush();
    }

    private void connectToServer() throws IOException {
        Socket socket = new Socket("localhost", 1234);

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        reader = new BufferedReader(inputStreamReader);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        writer = new BufferedWriter(outputStreamWriter);
    }

    private void changeScene(ActionEvent event, String file) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(file));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.setResizable(false);
        window.show();
    }

}

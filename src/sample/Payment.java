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

public class Payment {
    BufferedReader reader;
    BufferedWriter writer;

    @FXML
    private TextField amount;

    @FXML
    private TextField number;

    @FXML
    private TextField password;

    @FXML
    private Label comment;

    @FXML
    void back(ActionEvent event) throws IOException {
        changeToUserView(event);
    }

    @FXML
    void payment(ActionEvent event) throws IOException {
        String s1 = number.getText();
        double s2 = Double.parseDouble(amount.getText());
        String s3 = password.getText();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/sample/individual.txt"));

        String line1 = bufferedReader.readLine();
        bufferedReader.readLine();

        String line3 = bufferedReader.readLine();
        double balance = Double.parseDouble(line3);

        String line4 = bufferedReader.readLine();

        if (s3.equals(line4)) {
            if (balance >= s2) {
                sendMoney(line1, s1, s2 + "");
                comment.setText("Sent Successfully!!");
            }
            else comment.setText("Insufficient Balance");
        }

        bufferedReader.close();
    }

    private void changeToUserView(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("UserView.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.setResizable(false);
        window.show();
    }

    private void sendMoney(String fromPhone, String toPhone, String amount) throws IOException {
        connectToServer();

        writer.write("sendMoney\n");
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


}

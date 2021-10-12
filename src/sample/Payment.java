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
import java.util.HashMap;

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
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/sample/individual.txt"));

        String line1 = bufferedReader.readLine();

        fetchInfo(line1);
        changeToUserView(event);
    }

    @FXML
    void payment(ActionEvent event) throws IOException {
        String s1 = number.getText().trim();
        String a = amount.getText().trim();
        String s3 = password.getText();

        if(s1.isEmpty() || s3.isEmpty() || a.isEmpty()){
            comment.setText("field can't be empty");
            return;
        }
        if(!chkExist(s1)){
            comment.setText("phone number doesn't exists");
            return;
        }

        double s2 = Double.parseDouble(a);

        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/sample/individual.txt"));

        String line1 = bufferedReader.readLine();
        bufferedReader.readLine();

        String line3 = bufferedReader.readLine();
        double balance = Double.parseDouble(line3);

        String line4 = bufferedReader.readLine();

        if (s3.equals(line4)) {
            if(line1.equals(s1)){
                comment.setText("pay money to other than own number");
                return;
            }

            if (balance >= s2) {
                sendMoney(line1, s1, s2 + "");
                comment.setText("Payment Successful!!");
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

    private boolean chkExist(String s) throws IOException{
        boolean isFileEmpty = false;

        BufferedReader reader = new BufferedReader(new FileReader("src/sample/loginDetails.txt"));
        if (reader.readLine() == null)
            isFileEmpty = true;

        if (isFileEmpty) return false;

        FileInputStream stream = new FileInputStream("src/sample/loginDetails.txt");
        ObjectInputStream inputStream = new ObjectInputStream(stream);

        HashMap<String, String> hashMap = null;
        try {
            hashMap = (HashMap<String, String>) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("at line 91");
        }

        return hashMap.containsKey(s);
    }
}

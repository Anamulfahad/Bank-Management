package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class SignUpForm {
    BufferedReader reader;
    BufferedWriter writer;


    @FXML
    private TextField name;

    @FXML
    private TextField phoneNumber;

    @FXML
    private PasswordField password;

    @FXML
    private Label comment;

    @FXML
    void signUp() throws IOException, ClassNotFoundException {
        String s1 = name.getText().trim();
        String s2 = phoneNumber.getText().trim();
        String s3 = password.getText();

//        check if the phone number exists in database
        if (chkExist(s2)) {
            comment.setText("phone number exists");
            return;
        }

//        add account here
        connectToServer();

        writer.write("addAccount\n");
        writer.flush();

        writer.write(s1 + "\n");
        writer.flush();

        writer.write(s2 + "\n");
        writer.flush();

        writer.write(s3 + "\n");
        writer.flush();

        LoginSupport loginSupport = new LoginSupport();
        loginSupport.addAccount(s2, s3);

        comment.setText("account added");

//        from here this part will take u to userForm
//        write your code here ...
    }

    private void connectToServer() throws IOException {
        Socket socket = new Socket("localhost", 1234);

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        reader = new BufferedReader(inputStreamReader);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        writer = new BufferedWriter(outputStreamWriter);
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

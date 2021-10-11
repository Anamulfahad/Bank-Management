package sample;


import java.io.*;
import java.util.HashMap;

public class LoginSupport {
    private boolean isFileEmpty = false;

    LoginSupport() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/sample/loginDetails.txt"));
        if (reader.readLine() == null)
            isFileEmpty = true;

//        System.out.println(isFileEmpty);
    }

    //    checks both phone number and password
    boolean accountExist(String s1, String s2) throws IOException, ClassNotFoundException {
        if (isFileEmpty) return false;

        FileInputStream stream = new FileInputStream("src/sample/loginDetails.txt");
        ObjectInputStream inputStream = new ObjectInputStream(stream);

        HashMap<String, String> hashMap = (HashMap<String, String>) inputStream.readObject();
        if (hashMap.containsKey(s1)) {
            String s = hashMap.get(s1);

            return s.equals(s2);
        }
        return false;
    }

    void addAccount(String s1, String s2) throws IOException, ClassNotFoundException {
        HashMap<String, String> hashMap;

        if (isFileEmpty) {
            hashMap = new HashMap<>();
        } else {
            FileInputStream stream2 = new FileInputStream("src/sample/loginDetails.txt");
            ObjectInputStream inputStream = new ObjectInputStream(stream2);

            hashMap = (HashMap<String, String>) inputStream.readObject();
        }

        FileOutputStream stream = new FileOutputStream("src/sample/loginDetails.txt");
        ObjectOutputStream outputStream = new ObjectOutputStream(stream);

        hashMap.put(s1, s2);
        outputStream.writeObject(hashMap);
        outputStream.close();
    }
}

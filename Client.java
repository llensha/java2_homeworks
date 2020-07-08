package Lesson_6.Homework_6;

import javafx.fxml.Initializable;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Client {

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADDRESS = "localhost";
    final int PORT = 8189;

    public Client() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Scanner scanner = new Scanner(System.in);
                            String strOut = scanner.nextLine();
                            out.writeUTF(strOut);
                            if (strOut.equalsIgnoreCase("/end")) {
                                break;
                            }
                            String strIn = in.readUTF();
                            if (strIn.equalsIgnoreCase("/end")) {
                                break;
                            }
                            System.out.println("Сервер: " + strIn);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}

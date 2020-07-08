package Lesson_6.Homework_6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");

            socket = server.accept();
            System.out.println("Клиент подключился");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            try {
                while (true) {
                    String strIn = in.readUTF();
                    if (strIn.equalsIgnoreCase("/end")) {
                        in.close();
                        out.close();
                        break;
                    }
                    System.out.println("Клиент: " + strIn);
                    Scanner scanner = new Scanner(System.in);
                    String strOut = scanner.nextLine();
                    out.writeUTF(strOut);
                    if (strOut.equalsIgnoreCase("/end")) {
                        in.close();
                        out.close();
                        break;
                    }
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

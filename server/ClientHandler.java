package Lesson_8.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {

    private MainServer server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String nick;

    public String getNick() {
        return nick;
    }

    public ClientHandler(MainServer server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();

                            if(str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if(newNick != null) {
                                    if(!server.isNickBusy(newNick)) {
                                        sendMsg("/authok " + newNick);
                                        nick = newNick;
                                        server.subscribe(ClientHandler.this);
                                        break;
                                    } else {
                                        sendMsg("Учетная запись уже используется!");
                                    }
                                } else {
                                    sendMsg("Неверный логин/пароль");
                                }
                            }
                        }

                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/")) {
                                if (str.equals("/end")) {
                                    out.writeUTF("/serverclosed");
                                    break;
                                }
                                if (str.startsWith("/w ")) {
                                    String[] tokens = str.split(" ",3);
                                    server.sendPersonalMsg(ClientHandler.this, tokens[1], tokens[2]);
                                }
                                if (str.startsWith("/blacklist ")) {
                                    String[] tokens = str.split(" ");
                                    addToBlackList(tokens[1]);
                                    sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                                }
                                if (str.startsWith("/deleteblacklist ")) {
                                    String[] tokens = str.split(" ");
                                    deleteFromBlackList(tokens[1]);
                                    sendMsg("Вы удалили пользователя " + tokens[1] + " из черного списка");
                                }
                            } else {
                                server.broadcastMsg(ClientHandler.this, nick + " : " + str);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        server.unsubscribe(ClientHandler.this);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFromBlackList(String nickFromBlock) throws SQLException {
        int userId = sqlGetUserId(nick);
        int userBlockId = sqlGetUserId(nickFromBlock);

        String query = "DELETE FROM blacklist WHERE user_id = ? AND block_user_id = ?;";
        executeQuery(query, userId, userBlockId);
    }

    private void addToBlackList(String nickToBlock) throws SQLException {
        int userId = sqlGetUserId(nick);
        int userBlockId = sqlGetUserId(nickToBlock);

        String query = "INSERT INTO blacklist VALUES (?, ?);";
        executeQuery(query, userId, userBlockId);
    }

    private int sqlGetUserId(String nick) throws SQLException {
        int userId = 0;
        String sqlGetUserId = String.format("SELECT id FROM main where nickname = '%s'", nick);
        ResultSet rsUserId = AuthService.getStmt().executeQuery(sqlGetUserId);
        if (rsUserId.next()) {
            userId = rsUserId.getInt(1);
        }
        return userId;
    }

    private void executeQuery(String query, int userId, int userBlockId) throws SQLException {
        PreparedStatement ps = AuthService.getConnection().prepareStatement(query);
        ps.setInt(1, userId);
        ps.setInt(2, userBlockId);
        ps.executeUpdate();
    }


    public boolean checkBlackList(String nickTo) {
        try {
            String sqlCheckNick = String.format("SELECT * FROM blacklist where user_id = " +
                    "(select id from main where nickname = '%s') and block_user_id = " +
                    "(select id from main where nickname = '%s')", nick, nickTo);
            ResultSet rsCheckNick = AuthService.getStmt().executeQuery(sqlCheckNick);
            if (rsCheckNick.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

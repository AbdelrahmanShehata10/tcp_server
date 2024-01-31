/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static java.lang.System.console;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 20121
 */
public class Client {

    Socket socket;
    BufferedReader buffReader;
    BufferedWriter buffWriter;
    String name;
    PostgreSql db=new PostgreSql();

    public Client(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        try {
            this.buffWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            closeAll(socket, buffReader, buffWriter);
        }
    }

    public void sendmsg() throws SQLException {
        try {
            //بخزن اسم الشحص اللي هيبعت
            buffWriter.write(this.name);
            buffWriter.newLine();
            buffWriter.flush();
            
            readmsg();
            
            Scanner message = new Scanner(System.in);
            while (socket.isConnected()) {
                //System.out.println("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");

                String MessageToSend = message.nextLine();
                LocalDateTime now = LocalDateTime.now();

                // Format the timestamp as "HH:mm:ss"
                String formattedTimestamp = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                buffWriter.write(this.name + " " + formattedTimestamp + " " + ":" + MessageToSend);

                buffWriter.newLine();
                buffWriter.flush();
                readmsg();
            db.insertToDB(MessageToSend, this.name);

            }
            

        } catch (IOException ex) {
            closeAll(socket, buffReader, buffWriter);
        }

    }

    public void readmsg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromServer;
                while (socket.isConnected()) {
                    try {
                        messageFromServer = buffReader.readLine();
                        if(messageFromServer == null){
                            closeAll(socket, buffReader, buffWriter);
                            System.out.println("Exiting...");
                            System.exit(0);
                        }
                        System.out.println(messageFromServer);

                    } catch (IOException ex) {
                        closeAll(socket, buffReader, buffWriter);
                    }

                }
            }

        }).start();
    }

    public void closeAll(Socket socket, BufferedReader buffReader, BufferedWriter buffWriter) {
        try {
            if (buffReader != null) {
                buffReader.close();
            }
            if (buffWriter != null) {
                buffWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException, SQLException {
PostgreSql db=new PostgreSql();
        System.out.println("Enter Your User Name");
        Scanner sc = new Scanner(System.in);

        String name = sc.nextLine();
        Socket socket = new Socket("localhost", 4233);
        Client client = new Client(socket, name);
        db.insertToDB(name);
        
        client.sendmsg();

    }
}

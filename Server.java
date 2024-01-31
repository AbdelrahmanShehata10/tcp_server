/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 20121
 */
/*
////DIFFERENCE BETWEEN SOCKET AND SERVERSOCKET
Socket and ServerSocket are two classes in Java that are used for network communication, particularly in socket programming. Here's a brief explanation of the differences between them:

Socket:

Socket is a class that represents an endpoint for sending or receiving data across a computer network.
It is used on both the client and server sides of a network communication.
On the client side, a Socket is created to connect to a remote server.
On the server side, a new Socket is created for each incoming client connection.
ServerSocket:

ServerSocket 

is a class that provides a mechanism for the server to listen for incoming client connections.
It is used only on the server side of the communication.
ServerSocket waits for incoming client requests and, when a request is received, it creates a new Socket instance to handle the communication with that particular client.
Typically, a ServerSocket is associated with a specific port on the server.
In summary, ServerSocket is responsible for listening and accepting incoming connections from clients, while Socket is used for actual communication (sending and receiving data) once the connection is established. Each client connection accepted by a ServerSocket results in the creation of a new Socket instance for communication with that client.
 */
public class Server {

    private ServerSocket serversocket;

    public Server(ServerSocket serversocket) {
        this.serversocket = serversocket;
    }

    public void ServerStart() {
        while (!serversocket.isClosed()) {

            try {
                // بتقبل عملية الاتصال مع الكلاينت وهنا الكونكشن المفروض يكون حصل بعدها

                Socket socket = serversocket.accept();
                System.out.println("New connection request. Authenticating...");
//                clientstatement(socket);

                System.out.println("one new client connected");
                //بعد ميحصل الكونكشن محتاج شوية فانكشنز تهندل الكونكشن ده فبستخدم Client Handler 
                ClientHandler clienthandler = new ClientHandler(socket);
                Thread thread = new Thread(clienthandler);
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*  public void clientstatement(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String request = reader.readLine();
        //System.out.println(request);
        if ("SIGNUP".equals(request)) {
            signUp(reader, writer);

        } else if ("LOGIN".equals(request)) {
            login(reader, writer);

        } else {

        }
    }
     */
 /*  void signUp(BufferedReader reader, BufferedWriter writer) throws IOException {
        try {
            String username = reader.readLine();
            System.out.println("username" + username);

            String password = reader.readLine();
            System.out.println("password" + password);

            if (!users.containsKey(username)) {
                users.put(username, password);
                writer.write("SignUP->>>>>>>DONE");
            } else {
                writer.write("SignUP->>>>>>>FAILED");

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        /*
        flush():

The flush() method is used to flush the buffered characters to the underlying output stream.
It ensures that any characters that are currently buffered in the writer are immediately written to the output stream.
         
        writer.newLine();
        writer.flush();

    }
     */
 /*  void login(BufferedReader reader, BufferedWriter writer) throws IOException {
        String username = reader.readLine();
        String password = reader.readLine();
        if (users.containsKey(username) && users.get(username).equals(password)) {
            writer.write("login->>>>>>>DONE");

        } else {
            writer.write("login->>>>>>>FAILED");

        }
        writer.newLine();
        writer.flush();

    }
     */
    public void closeserver() {
        try {
            if (serversocket != null) {
                serversocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //System.out.print(users);

        ServerSocket serverSocket = new ServerSocket(4233);
        Server server = new Server(serverSocket);
        //System.out.print(users);

        server.ServerStart();
        // System.out.print(users);

    }

}

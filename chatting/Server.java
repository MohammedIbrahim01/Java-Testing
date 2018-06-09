package com.company.chatting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;

    public Server(int portNumber) throws IOException {
        this.serverSocket = new ServerSocket(portNumber);
    }

    public static void main(String[] args) {
        System.out.println("server side");
        try {
            Server server = new Server(5555);
            Socket socket = server.acceptServerSocket();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                System.out.println("Server is ready to read");
                try {
                    String string;
                    while ((string = dataInputStream.readUTF()) != "Stop") {
                        System.out.println("client : " + string);
                    }
                } catch (IOException e) {
                    System.out.println("Error in Server reading thread \n" + e);
                }
            }).start();

            new Thread(() -> {
                try {
                    System.out.println("Server is ready to write");
                    Scanner scanner = new Scanner(System.in);
                    String string;
                    while ((string = scanner.nextLine()) != "Stop") {
                        dataOutputStream.writeUTF(string);
                    }
                    scanner.close();
                } catch (IOException e) {
                    System.out.println("Error in Server writing thread \n" + e);
                }
            }).start();


            server.closeServerSocket();
        } catch (IOException e) {
            System.out.println("Exception in server" + e);
        }
        System.out.println("server is ready !");
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void closeServerSocket() throws IOException {
        this.serverSocket.close();
    }

    public Socket acceptServerSocket() throws IOException {
        return this.serverSocket.accept();
    }
}

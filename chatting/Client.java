package com.company.chatting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;

    public Client(String host, int portNumber) throws IOException {
        setSocket(new Socket(host, portNumber));
    }


    public static void main(String[] args) {
        System.out.println("client side");
        try {
            Client client = new Client("localhost", 5555);

            DataInputStream dataInputStream = new DataInputStream(client.getSocket().getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(client.getSocket().getOutputStream());

            new Thread(() -> {
                System.out.println("client ready to read");
                try {
                    String string;
                    while ((string = dataInputStream.readUTF()) != "Stop") {
                        System.out.println("Server : " + string);
                    }
                } catch (IOException e) {
                    System.out.println("Error in client reading \n" + e);
                }

            }).start();
            new Thread(() -> {
                System.out.println("client ready to write");
                try {
                    String string;
                    Scanner scanner = new Scanner(System.in);
                    while ((string = scanner.nextLine()) != "Stop") {
                        dataOutputStream.writeUTF(string);
                    }
                } catch (IOException e) {
                    System.out.println("Error in client writing \n" + e);
                }
            }).start();


        } catch (IOException e) {
            System.out.println("Error in client" + e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}

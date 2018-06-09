package com.company.Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DSocket {

    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(5556);
            InetAddress inetAddress = InetAddress.getByName("127.0.0.2");

            String s = "Hello";

            DatagramPacket datagramPacket = new DatagramPacket(s.getBytes(), s.length(), inetAddress, 5555);
            datagramSocket.send(datagramPacket);
            datagramSocket.close();
        } catch (SocketException e) {
            System.out.println(e);
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

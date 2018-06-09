package com.company.Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DPacket {
    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(5555);
            InetAddress inetAddress = InetAddress.getByName("127.0.0.2");

            byte[] bytes = new byte[1024];

            DatagramPacket datagramPacket = new DatagramPacket(bytes, 1024);
            datagramSocket.receive(datagramPacket);

            System.out.println(new String(datagramPacket.getData(), 0, datagramPacket.getLength()));
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

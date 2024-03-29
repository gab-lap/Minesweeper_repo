package main.client_server;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

import main.Cell;

public class UDPClient {
    private static final int SERVER_PORT = 9999;

    public static void startClient() throws IOException {
    	try {
    		String[] array = {"UNO", "DUE", "TRE"};
    		Cell uno = new Cell();
    		uno.button.setText("BOTTONE 1");
    		Cell due = new Cell();
    		due.button.setText("BOTTONE 2");
    		Cell tre = new Cell();
    		tre.button.setText("BOTTONE 3");
    		Cell[] cellArray = {uno, due, tre};
    		Socket socket = new Socket("192.168.1.5", SERVER_PORT);
    		//PrintWriter outputStream = new PrintWriter(socket.getOutputStream());
    		//outputStream.println("HELLO SERVERRRRRR");
    		//outputStream.flush();
    		//outputStream.close();
    		ObjectOutputStream outputObjectStream = new ObjectOutputStream(socket.getOutputStream());
    		outputObjectStream.writeUTF("HELLO SERVERRRRRRRRRRRRRRRRRRR :)");
    		outputObjectStream.flush();
    		outputObjectStream.writeObject(array.clone());
    		outputObjectStream.flush();
    		outputObjectStream.writeObject(cellArray.clone());
    		outputObjectStream.flush();
    		outputObjectStream.close();
    		socket.close();
    		//ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
    		//outputStream.writeObject(outputStream);
    	}catch (Exception e) {
    		System.out.println(e);
    	}
    	/*
        DatagramSocket clientSocket = null;
        try {
            // Specify server's address
            InetAddress serverAddress = InetAddress.getByName("192.168.212.4"); // Replace "server_ip_address" with the actual IP address of the server

            // Create a UDP socket
            clientSocket = new DatagramSocket();

            // Send message to the server
            String message = "Hello, server!";
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
            clientSocket.send(sendPacket);

            // Receive response from server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            // Print server's response
            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server response: " + serverResponse);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        }
    }*/
    }
}
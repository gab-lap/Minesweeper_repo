package main.client_server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import main.Cell;

public class UDPClient {
    private static final int SERVER_PORT = 4000;

    public static void startClient() throws IOException {
    	try {
    		Socket socket = new Socket("192.168.1.5", SERVER_PORT);
    		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
    		//ObjectOutputStream outputObjectStream = new ObjectOutputStream(socket.getOutputStream());
    		System.out.println(inputStream.readUTF());
    		
    	}catch (Exception e) {
    		System.out.println(e);
    	}
    	
    }
}
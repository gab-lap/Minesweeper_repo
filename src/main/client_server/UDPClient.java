package main.client_server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import main.Cell;
import main.DebugPanel;

public class UDPClient {
    private static final int SERVER_PORT = 4000;
    public static DebugPanel debugPanel;

    public static void startClient() throws IOException {
    	try {
    		Socket socket = new Socket("192.168.1.5", SERVER_PORT);
    		System.out.println("SOCKET IS CONNECTED");
    		ClientManager clientSock = new ClientManager(socket, debugPanel);
    		new Thread(clientSock).start();
    	}catch (Exception e) {
    		System.out.println(e);
    	}
    	
    }
}
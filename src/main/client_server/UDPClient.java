package main.client_server;
import java.io.IOException;
import java.net.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import main.DebugPanel;

public class UDPClient {
    private static final int SERVER_PORT = 4000;
    public static DebugPanel debugPanel;
    public static String IP = "";
    static ImageIcon icon = new ImageIcon(UDPClient.class.getResource("../images/noGame1.png"));
    public static void startClient() throws IOException {
    	try {
    		Socket socket = new Socket(IP, SERVER_PORT); //"192.168.1.5"
    		System.out.println("SOCKET IS CONNECTED");
    		ClientManager clientSock = new ClientManager(socket, debugPanel);
    		new Thread(clientSock).start();
    	}catch (Exception e) {
    		JOptionPane.showMessageDialog(null, "Inexistent game or wrong game code, try again", ":(", JOptionPane.INFORMATION_MESSAGE, icon);
    		System.out.println(e);
    	}
    	
    }
}
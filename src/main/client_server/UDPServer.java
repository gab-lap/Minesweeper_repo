package main.client_server;
import java.io.IOException;
import java.net.*;
import javax.swing.JOptionPane;
import main.Cell;
import main.DebugPanel;
import main.Field;

public class UDPServer implements Runnable{
    private static final int PORT = 4000;
    public static ServerSocket ss;
    static Socket socket;
    public static DebugPanel debugPanel;
    @Override
	public void run() {
		
	}
    public static void startServer(Field field, Cell startingCell) throws IOException {
    	try {
    		System.out.println("YOU HAVE CHOSEN CELL: (" + startingCell.row + " - " + startingCell.col + ")");
    		
    		InetAddress serverAddress = InetAddress.getLocalHost();
    		System.out.println("Server started. Listening on address " + serverAddress.getHostAddress() + ", port " + PORT + "...");
    		
    		ss = new ServerSocket(PORT);
    		ss.setReuseAddress(true);
    		
    			System.out.println("SJKHBKJSGBIJUSGIUGSKIUGSJUSG");
    			JOptionPane.showMessageDialog(null, "Game code: " + "\"" + serverAddress.getHostAddress() + "\"");
    			socket = ss.accept();
    			debugPanel.stopHosting.setEnabled(true);
    			ServerManager clientSock = new ServerManager(socket, field, startingCell);
    			new Thread(clientSock).start();
    		
    	}catch (IOException e) {
    		System.out.println("EXCEPTION IN startServer");
    		System.out.println(e);
    		
    	}
    	finally {
    		debugPanel.host.setEnabled(true);
    		if (ss != null) { 
                try { 
                    ss.close(); 
                } 
                catch (IOException e) { 
                    e.printStackTrace(); 
                } 
    	}
    }
}
}






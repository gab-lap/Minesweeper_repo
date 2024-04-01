package main.client_server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;

import main.Cell;
import main.DebugPanel;
import main.Field;

public class ClientManager implements Runnable {
	public static boolean won = false;
    private final Socket socket; 
    static ObjectInputStream inputStream;
    static ObjectOutputStream outputStream;
    static DebugPanel debugPanel;
    static boolean receivingData = false;
    static Field field;
    Cell startingCell;
    public static String message = "";
    
    public ClientManager(Socket socket, DebugPanel debugPanel) { 
        this.socket = socket;
        ClientManager.debugPanel = debugPanel;
    } 

    public void run() { 
    	System.out.println("BEFORE INPUTSTREAM INITIALIZATION");
    	try {
			inputStream = new ObjectInputStream(socket.getInputStream());
			outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("CLIENT MANAGER RUNNING IN A SEPARATE THREAD");
        while (true) {
        	try {
        		if (won) {
            		outputStream.writeUTF("won");
            		outputStream.flush();
            		won = false;
        		}
        		
				if (inputStream.available() > 0) {
					if (!receivingData) {
						message = (String) inputStream.readUTF();
						if (message.equals("won")) {
							JOptionPane.showMessageDialog(null, "Your opponent won, you LOST");
						}
						else if (message.equals("SENDING-DATA")) {
							receivingData = true;
							System.out.println("MESSAGE SHOULD BE SENDING DATA: " + message);
						}
						else {
							System.out.println("MESSAGE: " + message);
						}
					}
					if (receivingData) {
						try {
							field = (Field) inputStream.readObject();
							debugPanel.finestra.contentPane.remove(debugPanel.finestra.panel);
							debugPanel.finestra.contentPane.add(field.finestra.panel, BorderLayout.CENTER);
							debugPanel.finestra.repaint();
							debugPanel.finestra.revalidate();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("FIELD READ");
						break;
					}
        	receivingData = false;
				} // if (inputStream is available
			} catch (IOException e) {
				e.printStackTrace();
			}
        } // while's closure
    }
}


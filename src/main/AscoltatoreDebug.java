package main;
import main.client_server.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.client_server.ClientHandler;
import main.client_server.UDPServer;

class Connect extends Thread implements Runnable {
	DebugPanel debugPanel;
	Socket Socket;
	public Connect(DebugPanel debugPanel) {
		this.debugPanel = debugPanel;
	}
	public void start() {
		UDPServer.debugPanel = debugPanel;
	}
}

class StopHosting{
	DebugPanel debugPanel;
	ServerSocket serverSocket;
	public StopHosting(DebugPanel debugPanel) {
		this.debugPanel = debugPanel;
	}
	public void start() {
		debugPanel.stopHosting.setEnabled(false);
		serverSocket = UDPServer.ss;
		//debugPanel.host.setBackground(new Color(227, 26, 34));
		if (serverSocket != null)
				ClientHandler.message = "quit";
	}
}

class Host extends Thread implements Runnable {
	DebugPanel debugPanel;
	ServerSocket serverSocket;
	public Host(DebugPanel debugPanel) {
		this.debugPanel = debugPanel;
	}
	public void start() {
		UDPServer.debugPanel = debugPanel;
		serverSocket = UDPServer.ss;
		if (serverSocket != null)
		System.out.println("serverSocket == null: " + serverSocket == null + " serverSocket.isClosed(): " + serverSocket.isClosed());
		else
			System.out.println("server socket is nullllllllll");
		if (serverSocket == null || serverSocket.isClosed()) {
			try {
				debugPanel.host.setEnabled(false);
				debugPanel.finestra.field.multiplayer = false;
				UDPServer.startServer(debugPanel.finestra.field, debugPanel.finestra.field.multiplayerStartingCell);
			}catch (IOException e) {
				debugPanel.host.setEnabled(true);
				System.err.println(e.getMessage());
				System.out.println("ERROR STARTING MESSAGE");
			}
		}
	}
}

class MultiplayerON{
	DebugPanel debugPanel;
	ServerSocket serverSocket;
	public MultiplayerON(DebugPanel debugPanel) {
		this.debugPanel = debugPanel;
	}
	public void start() {
		debugPanel.finestra.field.ascoltatore.debugPanel = debugPanel;
		debugPanel.connect.setEnabled(false);
		debugPanel.finestra.field.multiplayer = true;
		debugPanel.multiplayerON.setEnabled(false);
		System.out.println("CHOOSE A CELL");
	}
}

class Clear{
	DebugPanel debugPanel;
	public Clear(DebugPanel debugPanel) {
		this.debugPanel = debugPanel;
		debugPanel.clear.setEnabled(false);
	}
	public void start() {
		debugPanel.finestra.field.clearField(0, 0);
		/*
		debugPanel.finestra.contentPane.remove(debugPanel.finestra.panel);
		debugPanel.finestra.revalidate();
		debugPanel.finestra.repaint();
		JPanel newPanel = new JPanel(new GridLayout(15, 15, 0 ,0));
		newPanel.add(new JButton("HEHEHE"));
		debugPanel.finestra.contentPane.add(newPanel, BorderLayout.CENTER);
		debugPanel.finestra.revalidate();
		debugPanel.finestra.repaint();
		*/
	}
}

public class AscoltatoreDebug implements ActionListener, Serializable {
	private static final long serialVersionUID = 1L;
	DebugPanel debugPanel;
	public AscoltatoreDebug(DebugPanel debugPanel) {
		this.debugPanel = debugPanel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ISJGçUG");
		String command = e.getActionCommand();
		Clear clear = new Clear(debugPanel);
		Host host = new Host(debugPanel);
		StopHosting stopHosting = new StopHosting(debugPanel);
		MultiplayerON multiplayerON = new MultiplayerON(debugPanel);
		Connect connect = new Connect(debugPanel);
		
		if (command.equals("clear") && debugPanel.finestra.field.started)   //REMOVE ! ----------------------------
			clear.start();
		if (command.equals("host") && !debugPanel.finestra.field.started)
			host.start();
		if (command.equals("StopHosting") && debugPanel.finestra.field.started)
			stopHosting.start();
		if (command.equals("multiplayerON") && !debugPanel.finestra.field.started)
			multiplayerON.start();
		if (command.equals("connect") && !debugPanel.finestra.field.started)
			connect.start();
	}

}

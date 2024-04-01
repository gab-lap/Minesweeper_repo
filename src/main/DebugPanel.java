package main;
import java.awt.*;
import java.io.Serializable;

import javax.swing.*;

public class DebugPanel extends JFrame implements Serializable{
	private static final long serialVersionUID = 1L;
	static final String titolo = "Debug Panel";
	public Finestra finestra;
	transient Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/debug.png"));
	AscoltatoreDebug ascoltatore = new AscoltatoreDebug(this);
	JPanel north = new JPanel();
	JPanel east = new JPanel();
	JPanel south = new JPanel();
	JPanel west = new JPanel();
	JButton clear = new JButton("Clear");
	public JButton host = new JButton("Host");
	public JButton multiplayerON = new JButton("multiplayerON");
	public JButton connect = new JButton("Connect");
	public JButton stopHosting = new JButton("StopHosting");
	
	JPanel panel = new JPanel(new BorderLayout()); //cetral panel
	public DebugPanel(Finestra finestra) {
		super(titolo);
		multiplayerON.addActionListener(ascoltatore);
		multiplayerON.setActionCommand("multiplayerON");
		clear.addActionListener(ascoltatore);
		clear.setActionCommand("clear");
		host.addActionListener(ascoltatore);
		host.setActionCommand("host");
		connect.addActionListener(ascoltatore);
		connect.setActionCommand("connect");
		stopHosting.addActionListener(ascoltatore);
		stopHosting.setActionCommand("StopHosting");
		
		clear.setEnabled(false);
		host.setEnabled(false);
		stopHosting.setEnabled(false);
		this.finestra = finestra;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();
		//host.setBackground(new Color(227, 26, 34));
		panel.add(clear);
		east.add(host);
		east.add(connect);
		east.add(stopHosting);
		east.add(multiplayerON);
		contentPane.add(north, BorderLayout.NORTH);
		contentPane.add(east, BorderLayout.EAST);
		contentPane.add(south, BorderLayout.SOUTH);
		contentPane.add(west, BorderLayout.WEST);
		contentPane.add(panel, BorderLayout.CENTER);
		this.setIconImage(icon);
		this.setVisible(true);
		Point point = finestra.getLocationOnScreen();
        //dialog.setLocationRelativeTo(b); // Shows over button, as doc says
        this.setLocation(new Point(point.x + finestra.getWidth(), point.y));
		//this.setLocationRelativeTo(finestra, );
		this.pack();
		this.setResizable(false);
	}

}

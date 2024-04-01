package main;
import java.awt.*;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import main.menuBar.MenuBarListener;

public class Finestra extends javax.swing.JFrame implements Serializable{
	private static final long serialVersionUID = 1L;
	static final String titolo = "Minesweeper";
	transient Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png"));
	JMenuItem game = new JMenuItem("Game");
	JMenuItem look = new JMenuItem("Look");
	JMenuItem online = new JMenuItem("Online");
	JMenuItem debug = new JMenuItem("Debug");
	JMenuBar jb = new JMenuBar();
	JPanel north = new JPanel();
	JPanel east = new JPanel();
	JPanel south = new JPanel();
	JPanel west = new JPanel();
	public JPanel panel;
	public Field field;
	public JButton smiley = new JButton(new ImageIcon(getClass().getResource("images/smiley.png")));
	public Container contentPane = this.getContentPane();
	public MenuBarListener menuBarListener = new MenuBarListener();
	public Finestra(int height, int width) {
		super(titolo);
		//instantiate & create field
		field = new Field(height, width, this);
		panel = new JPanel(new GridLayout(field.height, field.width, 0, 0));
		field.createField(this);
		//MenuBar
		jb.setBorder(new MatteBorder(0, 0, 3, 0, new Color(74, 117, 44)));
		jb.setBackground(new Color(162, 209, 73));
				//game.setContentAreaFilled(false);
				//JPopupMenu pop = new JPopupMenu("IUJGWI");
				//pop.add(new JButton("OIHOAIHOA"));         ////////////
				//pop.show(finestra, 100, 100);
		//menus
		game.setBorderPainted(false);
		game.setMaximumSize(game.getPreferredSize());
		game.setBackground(new Color(162, 209, 73));
		game.addActionListener(menuBarListener);
		game.setActionCommand("game");

		online.setBorderPainted(false);
		online.setMaximumSize(online.getPreferredSize());
		online.setBackground(new Color(162, 209, 73));
		online.addActionListener(menuBarListener);
		online.setActionCommand("online");
		
		look.setBorderPainted(false);
		look.setMaximumSize(look.getPreferredSize());
		look.setBackground(new Color(162, 209, 73));
		look.addActionListener(menuBarListener);
		look.setActionCommand("look");
		
		debug.setBorderPainted(false);
		debug.setMaximumSize(debug.getPreferredSize());
		debug.setBackground(new Color(162, 209, 73));
		debug.addActionListener(menuBarListener);
		debug.setActionCommand("debug");
		//add menus
		jb.add(game);
		jb.add(online);
		jb.add(look);
		jb.add(debug);
		this.setJMenuBar(jb);
		//smiley button
		smiley.setName("smiley");
		smiley.setFocusPainted(false);
		smiley.setPreferredSize(new Dimension(50, 50));
		smiley.setBackground(new Color(162, 209, 73));
		smiley.setBorderPainted(false);
		
		north.add(smiley, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		north.setPreferredSize(new Dimension(0, 70));
		west.setBackground(new Color(74, 117, 44));
		north.setBackground(new Color(74, 117, 44));
		east.setBackground(new Color(74, 117, 44));
		south.setBackground(new Color(74, 117, 44));
		/*
		JButton bottone = new JButton();
		bottone.setPreferredSize(new Dimension(100, 100));
		bottone.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/angyKitty.gif")));
		north.add(bottone);
		
		FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
	    dialog.setMode(FileDialog.LOAD);
	    dialog.setVisible(true);
	    String file = dialog.getFile();
	    dialog.dispose();
	    System.out.println(file + " chosen.");  */
	    
		contentPane.add(north, BorderLayout.NORTH);
		contentPane.add(east, BorderLayout.EAST);
		contentPane.add(south, BorderLayout.SOUTH);
		contentPane.add(west, BorderLayout.WEST);
		contentPane.add(panel, BorderLayout.CENTER);
		this.setIconImage(icon);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

}

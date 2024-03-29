package main.menuBar;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import main.Finestra;

class Game{
	Finestra finestra;
	public Game(Finestra finestra) {
		this.finestra = finestra;
	}
	public void start() {
		JPopupMenu pop = new JPopupMenu("IUJGWI");
		pop.add(new JButton("Game")); ////////////
		pop.setBorderPainted(false);
		//pop.setBackground(new Color(0, 0, 0));
		int x = finestra.smiley.getLocation().x;
		int y = finestra.smiley.getLocation().y;
		pop.show(finestra, x, y);
	}
}

class Online{
	Finestra finestra;
	public Online(Finestra finestra) {
		this.finestra = finestra;
	}
	public void start() {
		JPanel panel = new JPanel();
		JPopupMenu pop = new JPopupMenu("IUJGWI");
		panel.add(new JButton("online"));
		pop.add(panel);         ////////////
		pop.show(finestra, 100, 100);
	}
}

class Look{
	Finestra finestra;
	public Look(Finestra finestra) {
		this.finestra = finestra;
	}
	public void start() {
		JPanel panel = new JPanel();
		JPopupMenu pop = new JPopupMenu("IUJGWI");
		panel.add(new JButton("look"));
		pop.add(panel);         ////////////
		pop.show(finestra, 100, 100);
	}
}

class Debug{
	Finestra finestra;
	public Debug(Finestra finestra) {
		this.finestra = finestra;
	}
	public void start() {
		JPanel panel = new JPanel();
		JPopupMenu pop = new JPopupMenu("IUJGWI");
		panel.add(new JButton("debug"));
		pop.add(panel);         ////////////
		pop.show(finestra, 100, 100);
	}
}

public class MenuBarListener implements ActionListener{
	public Finestra finestra;
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		System.out.println(finestra);
		String command = e.getActionCommand();
		if (command.equals("game")) {
			Game game = new Game(finestra);
			game.start();
		}
		if (command.equals("online")) {
			Online online = new Online(finestra);
			online.start();
		}
		if (command.equals("look")) {
			Look look = new Look(finestra);
			look.start();
		}
		if (command.equals("debug")) {
			Debug debug = new Debug(finestra);
			debug.start();
		}
	}

}

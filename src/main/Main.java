package main;

public class Main {

	public static void main(String[] args) {
		Finestra finestra = new Finestra(9, 9);
		DebugPanel debugPanel = new DebugPanel(finestra);
		finestra.field.ascoltatore.debugPanel = debugPanel;
		finestra.menuBarListener.finestra = finestra;
		System.out.println("AKIJHAIJU: " + finestra);
		//ishdjgbwidugdwugbwiu
	}

}

package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

class RevealCells{
	Finestra finestra;
	Cell cell;
	Field field;
	public RevealCells(Finestra finestra, Cell cell, Field field) {
		this.finestra = finestra;
		this.field = field;
		this.cell = cell;
	}
	public void start() {
		if (cell.mine && !field.cleared) {
			cell.button.setBackground(new Color(231, 12, 12));
			cell.button.setEnabled(false);
			cell.checked = true;
			field.endGame();
			JOptionPane.showMessageDialog(null, "Game Over");  
			return;
		}
		else {
			cell.button.setText("");
			for (int i = 0; i < cell.button.getMouseListeners().length; i++) {
				cell.button.removeMouseListener(cell.button.getMouseListeners()[i]);
			}
			cell.button.removeMouseListener(field.ascoltatore_destro);
			cell.button.setEnabled(false);
			cell.checked = true;
			if (!field.checkedCells.contains(cell))
				field.checkedCells.add(cell);
			cell.button.setBorder(new MatteBorder(0, 0, 0, 0, new Color(135, 175, 58)));
			if (cell.DG) {
				cell.button.setBackground(new Color(215, 184, 153)); //dark
			}
			
			else
				cell.button.setBackground(new Color(229, 194, 159)); //light
			//cell.button.setIcon(new ImageIcon("images\\flag.png")); //SETS DEFAUL ICON --------------------------------
			//cell.button.setDisabledIcon(new ImageIcon("images\\flag.png")); //DISABLES BUTTON ------------------------------
		}
		
		//if (cell.checked) {
		//	cell.button.setBackground(Color.BLACK);   //DEBUG IF CELLS "checked" FIELD IS CORRECTLY SET TO TRUE
		//}
	}
}

class AscoltatoreMouse implements MouseListener, Serializable{
	private static final long serialVersionUID = 1L;
	Finestra finestra;
	Field field;
	public AscoltatoreMouse(Finestra finestra, Field field) {
		this.finestra = finestra;
		this.field = field;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		JButton button = (JButton) e.getSource();
		if (!field.gameEnd && field.started)
			field.finestra.smiley.setIcon(new ImageIcon(getClass().getResource("images/smiley.png")));
		Cell cell = field.grid[Integer.valueOf(button.getName().split(";")[0])][Integer.valueOf(button.getName().split(";")[1])];

		if (e.isPopupTrigger()) {
			if (!cell.flag && field.started) {
				cell.button.setIcon(new ImageIcon(getClass().getResource("images/flag.png")));
				cell.flag = true;
			}
			else {
				cell.button.setIcon(new ImageIcon(getClass().getResource("images//blank.png")));
				cell.flag = false;
			}
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if (field.started)
			field.finestra.smiley.setIcon(new ImageIcon(getClass().getResource("images/surprise.png")));
		
	}
}

public class Ascoltatore implements ActionListener, Serializable{
	private static final long serialVersionUID = 1L;
	Finestra finestra;
	Field field;
	DebugPanel debugPanel;
	public Ascoltatore(Finestra finestra, Field field) {
		this.field = field;
		this.finestra = finestra;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("open")) {
			JButton button = (JButton)e.getSource();
			Cell cell = field.grid[Integer.valueOf(button.getName().split(";")[0])][Integer.valueOf(button.getName().split(";")[1])];
			RevealCells reveal = new RevealCells(finestra, cell, field);
			
			if (field.multiplayer) {
				field.multiplayerStartingCell = cell;
				debugPanel.host.setEnabled(true);
				System.out.println("YOU HAVE CHOSEN CELL: (" + cell.row + " - " + cell.col + ")");
			}
			else if (cell.flag) {
				//doesn't allow revealing if cell is flagged
			}
			//cell.button.setBorder(new MatteBorder(0, 0, 0, 0, new Color(135, 175, 58))); //(TOP, LEFT, BOTTON, RIGHT)
			else if (cell.numMines == 0 && field.started && !cell.mine) { //REVEAL EMPTY CELLS (AFTER FIRST MOVE)
				field.revealBlankCells(cell, -1, -1);
				field.iterateThroughBlankCells(cell, -1, -1);
			}
			else if (!field.started) { //REVEAL EMPTY CELLS (ONLY ON FIRST MOVE)
				debugPanel.multiplayerON.setEnabled(false);
				debugPanel.connect.setEnabled(false);
				debugPanel.clear.setEnabled(true);
				field.emptyCells.add(cell);
				field.generateEmptyCells(field.height, field.width);
				field.started = true;
				field.setMines();
				field.setNumsOnField(0, 0);
				cell.checked = true;
				cell.button.setText("-");
				cell.button.setEnabled(false);
				field.revealBlankCells(cell, -1, -1);
				field.iterateThroughBlankCells(cell, -1, -1);
			}
			else {  //REVEAL CELLS
				reveal.start();
				field.setBorders(cell, -1, -1);
			}
			System.out.println("Field.checkedCells.size(): " + field.checkedCells.size());
			System.out.println("(field.height * field.width) - field.numMines: " + ((field.height * field.width) - field.numMines));
			if (field.checkedCells.size() == (field.height * field.width) - field.numMines) {
				field.endGame();
				field.finestra.smiley.setIcon(new ImageIcon(getClass().getResource("images/cool.png")));
				JOptionPane.showMessageDialog(null, "You Won");        //WIN   //IF ANY OTHER THING OTHER THAN MINES IS FLAGGED CANT WIN
			}
		}
		else if (command.equals("smiley")) {
			field.newGame(field, debugPanel);
		}
		
	}

}


package main;
import java.io.Serializable;

import javax.swing.*;

public class Cell extends JFrame implements Cloneable, Serializable{
	private static final long serialVersionUID = 1L;
	public int row;
	public int col;
	public JButton button = new JButton();
	boolean flag = false;
	public boolean mine = false;  int mineNumber = 0;
	public int numMines = 0;
	boolean DG = false;
	boolean LG = false;
	public boolean checked = false;
	boolean checked2 = false;
	
	@Override
    public Object clone() throws CloneNotSupportedException {
        Cell clonedCell = (Cell) super.clone();
        // Deep copy mutable fields
        
        clonedCell.button = this.button;
        clonedCell.flag = this.flag;
        clonedCell.mine = this.mine;
        clonedCell.mineNumber = this.mineNumber;
        clonedCell.numMines = this.numMines;
        clonedCell.DG = this.DG;
        clonedCell.LG = this.LG;
        clonedCell.checked = this.checked;
        clonedCell.checked2 = this.checked2;
        return clonedCell;
    }
}

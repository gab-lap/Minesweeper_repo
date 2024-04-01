package main;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import java.io.Serializable;

public class Field extends JFrame implements Serializable, Cloneable{
	public Cell multiplayerStartingCell;
	public boolean multiplayer = false;
	public boolean cleared = false;
	public boolean gameEnd = false;
	private static final long serialVersionUID = 1L;
	public boolean started = false; //check if first move has been done
	public int numFreeCells = new Random().nextInt(5 - 1) + 1;
	public int height, width, numMines = 10;
	public Cell grid[][];
	public List<Cell> emptyCells = new ArrayList<>();
	List<Cell> cantPlace = new ArrayList<>();
	List<Cell> checkedCells = new ArrayList<>();
	public Finestra finestra;
	Ascoltatore ascoltatore = new Ascoltatore(this.finestra, this);
	AscoltatoreMouse ascoltatore_destro = new AscoltatoreMouse(this.finestra, this);
	public Field(int height, int width, Finestra finestra) {
		this.height = height;
		this.width = width;
		grid = new Cell[height][width];
		this.finestra = finestra;
	}
	
	public void createField(Finestra finestra) {
		for (int i = 0; i<height; i++) {
			for (int j = 0; j<width; j++) {
				Cell cell = new Cell();
				cell.button.setActionCommand("open");
				cell.button.addActionListener(ascoltatore);
				cell.button.addMouseListener(ascoltatore_destro);
				cell.row = i; cell.col = j; //set row & cols
				cell.button.setName(String.valueOf(cell.row) + ";" + String.valueOf(cell.col)); //set Name for button
				cell.button.setBorder(new LineBorder(Color.GRAY, 0));
				cell.button.setPreferredSize(new Dimension(31, 31));
				cell.button.setForeground(Color.RED);
				cell.button.setMargin(new Insets(0, 0, 0, 0));
				cell.button.setFocusPainted(false);
				
				//cell.button.setBorder(cell.button.getBorder());
				cell.button.setBorder(null);
				//cell.button.setBorder(new MatteBorder(1, 1, 1, 1, new Color(135, 175, 58)));
				if (i%2==0) {
					if (j%2==0) {
						cell.button.setBackground(new Color(162, 209, 73)); //dark
						cell.DG = true;
					}
					else {
						cell.button.setBackground(new Color(170, 215, 81)); //light
						cell.LG = true;
					}
				}
				else {
					if (j%2!=0) {
						cell.button.setBackground(new Color(162, 209, 73));  //dark
						cell.DG = true;
					}
					else {
						cell.button.setBackground(new Color(170, 215, 81)); //light
						cell.LG = true;
					}
				}
				this.grid[i][j] = cell;
				this.finestra.panel.add(this.grid[i][j].button);
			}
		}
	}
	public void setMines() {
		finestra.smiley.addActionListener(ascoltatore);
		finestra.smiley.setActionCommand("smiley");
		finestra.smiley.setIcon(new ImageIcon(getClass().getResource("images/smiley.png")));
		int placed = 0;
		while (placed<numMines) {
			Random random = new Random();
			int randRow = random.nextInt(height);
			int randCol = random.nextInt(width);
			if (!grid[randRow][randCol].mine && !(cantPlace.contains(grid[randRow][randCol]))) {
				grid[randRow][randCol].mine = true;
				//System.out.println(placed);
				grid[randRow][randCol].mineNumber = placed + 1;
				placed++;
			}
		}
		
	}
	public void generateEmptyCells(int height, int width) {
		int iterations = 0;
		while (emptyCells.size() <= this.numFreeCells) {
			if (iterations>1000) {
				JOptionPane.showMessageDialog(null, "TOO MANY ITERATIONS", "ERROR in method: generateEmptyCells", JOptionPane.ERROR_MESSAGE);
				break;
			}
			int randomCell = new Random().nextInt(emptyCells.size());
			Cell cell = emptyCells.get(randomCell);
			int i = new Random().nextInt(2 + 1) - 1;
			int j = new Random().nextInt(2 + 1) - 1;
			if (cell.row + i < 0 || cell.row + i >= height || cell.col + j < 0 || cell.col + j >= width || emptyCells.contains(this.grid[cell.row][cell.col + j]) || emptyCells.contains(this.grid[cell.row + i][cell.col])) {
				
			}
			else {
				if (new Random().nextInt(2) == 1) {
					emptyCells.add(this.grid[cell.row][cell.col + j]);
					this.grid[cell.row][cell.col + j].button.setText(":)");
				}
				else {
					emptyCells.add(this.grid[cell.row + i][cell.col]);
					this.grid[cell.row + i][cell.col].button.setText(":)");
				}
			}
			iterations++;
		}
		//ADD ALSO SURROUNDING CELLS WHERE MINES CANT BE PLACES (THEY CAN HAVE NUMBERS THO)
		for (int i = 0; i < this.emptyCells.size(); i++) {
			setNumsOnFieldAux(this.grid[this.emptyCells.get(i).row][this.emptyCells.get(i).col], -1, -1, true);
		}
		
		
	}
	public void setNumsOnField(int row, int col) {
		//caso base
		if (row >= height) {
			return;
		}
		//this.grid[row][col].button.setText(this.grid[row][col].button.getText() + String.valueOf(setNumsOnFieldAux(this.grid[row][col], -1, -1, false)));
		//System.out.println(setNumsOnFieldAux(this.grid[row][col], -1, -1));
		//caso ricorsivo
		if (col >= width) {
			setNumsOnField(row + 1, 0);
		}
		else{
			if (this.grid[row][col].mine) {
				this.grid[row][col].button.setIcon(new ImageIcon(getClass().getResource("images/blank.png")));
				this.grid[row][col].button.setDisabledIcon(new ImageIcon(getClass().getResource("images/mine.png")));
				setNumsOnField(row, col + 1);
			}
			else {
				int numSurrMines = setNumsOnFieldAux(this.grid[row][col], -1, -1, false);
				this.grid[row][col].button.setIcon(new ImageIcon(getClass().getResource("images/blank.png")));
				String numImage = String.valueOf(numSurrMines);
				String pathImage = "images/" + numImage + ".png";
				if (Integer.parseInt(numImage) != 0)
					this.grid[row][col].button.setDisabledIcon(new ImageIcon(getClass().getResource(pathImage)));
				else
					this.grid[row][col].button.setDisabledIcon(new ImageIcon(getClass().getResource("images/blank.png")));
				this.grid[row][col].numMines = numSurrMines;
				setNumsOnField(row, col + 1);
			}
		}
	}
	public int setNumsOnFieldAux(Cell cell, int i, int j, boolean AUXcantPlace) {
		//caso base
		if (i == 1 && j == 1) {
			if (cell.row + i < height && cell.col + j < width) {
				if (!cantPlace.contains(this.grid[cell.row + i][cell.col + j]) && AUXcantPlace)
					this.cantPlace.add(this.grid[cell.row + i][cell.col + j]);
				if (!this.grid[cell.row + i][cell.col + j].mine) {
					//this.grid[cell.row + i][cell.col + j].button.setText(this.grid[cell.row + i][cell.col + j].button.getText() + "-");
					return 0;
				}
				else {
					this.grid[cell.row + i][cell.col + j].button.setIcon(new ImageIcon(getClass().getResource("images/blank.png")));
					this.grid[cell.row + i][cell.col + j].button.setDisabledIcon(new ImageIcon(getClass().getResource("images/mine.png")));
					return 1;
				}
			}
			else
				return 0;
		}
		if (cell.row + i < 0)
			return setNumsOnFieldAux(cell, i + 1, j, AUXcantPlace);
		if (cell.row + i >= height)
			return 0;
		if (cell.col + j < 0)
			return setNumsOnFieldAux(cell, i, j + 1, AUXcantPlace);
		if (cell.col + j >= width)
			return setNumsOnFieldAux(cell, i + 1, -1, AUXcantPlace);
		//this.grid[cell.row + i][cell.col + j].button.setText(this.grid[cell.row + i][cell.col + j].button.getText() + "-");
		//caso ricorsivo
		if (j == 1) {
			if (!cantPlace.contains(this.grid[cell.row + i][cell.col + j]) && AUXcantPlace)
				this.cantPlace.add(this.grid[cell.row + i][cell.col + j]);
			if (this.grid[cell.row + i][cell.col + j].mine) {
				this.grid[cell.row + i][cell.col + j].button.setIcon(new ImageIcon(getClass().getResource("images/blank.png")));
				this.grid[cell.row + i][cell.col + j].button.setDisabledIcon(new ImageIcon(getClass().getResource("images/mine.png")));
				return 1 + setNumsOnFieldAux(cell, i + 1, -1, AUXcantPlace);
			}
			else
				return setNumsOnFieldAux(cell, i + 1, -1, AUXcantPlace);
		}
		else if (j < 1) {
			if (!cantPlace.contains(this.grid[cell.row + i][cell.col + j]) && AUXcantPlace)
				this.cantPlace.add(this.grid[cell.row + i][cell.col + j]);
			if (this.grid[cell.row + i][cell.col + j].mine) {
				this.grid[cell.row + i][cell.col + j].button.setIcon(new ImageIcon(getClass().getResource("images/blank.png")));
				this.grid[cell.row + i][cell.col + j].button.setDisabledIcon(new ImageIcon(getClass().getResource("images/mine.png")));
				return 1 + setNumsOnFieldAux(cell, i, j + 1, AUXcantPlace);
			}
			else
				return setNumsOnFieldAux(cell, i, j + 1, AUXcantPlace);
		}
		return 0;
	}
	public void clearField(int row, int col) {
		cleared = true;
		//caso base
		//System.out.println("row: " + row);System.out.println("col: " + col);System.out.println("\n");
		if (row >= height) {
			return;
		}
		//caso ricorsivo
		if (col >= width) {
			System.out.print("\n");
			clearField(row + 1, 0);
		}
		else {
			if (this.grid[row][col].mine)
				System.out.print("M ");
			else
				System.out.print(this.grid[row][col].numMines + " "); 
			RevealCells reveal = new RevealCells(finestra, this.grid[row][col], this);
			reveal.start();
			clearField(row, col + 1);
		}
	}
	public int revealBlankCells(Cell cell, int i, int j) { //check if cell.numMines == 0 && !cell.mine. Funzionalità da implementare: quando una cell viene premuta inizia a controllare se quelle adiacenti sono già state controllate e va a controllare le rimanenti per vedere se sono libere, nel caso le libera (disable)
		//caso base                  //implement cell becomes next cell in recursion   //i e j start from -1 -1
		if (i == 1 && j == 1) {
			if (cell.row + i < height && cell.col + j < width) {
				if (this.grid[cell.row + i][cell.col + j].numMines == 0 && !this.grid[cell.row + i][cell.col + j].checked) {
					new RevealCells(finestra, this.grid[cell.row + i][cell.col + j], this).start();
					this.grid[cell.row + i][cell.col + j].button.setText("-");
					return revealBlankCells(this.grid[cell.row + i][cell.col + j], -1, -1);
				}
				else {
					new RevealCells(finestra, this.grid[cell.row + i][cell.col + j], this).start();
					return 0;
				}
			}
			else {
				return 0;
			}
		}
		//controllo bound griglia
		if (cell.row + i < 0)
			return revealBlankCells(cell, i + 1, j);
		if (cell.row + i >= height)
			return 0;
		if (cell.col + j < 0)
			return revealBlankCells(cell, i, j + 1);
		if (cell.col + j >= width)
			return revealBlankCells(cell, i + 1, -1);
		//fine controllo bound griglia
		
		//caso ricorsivo
		if (j == 1) {
			if (this.grid[cell.row + i][cell.col + j].numMines == 0 && !this.grid[cell.row + i][cell.col + j].checked) {
				new RevealCells(finestra, this.grid[cell.row + i][cell.col + j], this).start();
				this.grid[cell.row + i][cell.col + j].button.setText("-");
				revealBlankCells(this.grid[cell.row + i][cell.col + j], -1, -1);
				return revealBlankCells(cell, i + 1, -1);
			}
			else {
				new RevealCells(finestra, this.grid[cell.row + i][cell.col + j], this).start();
				return revealBlankCells(cell, i + 1, -1);
			}
		}
		else if (j < 1) {
			if (this.grid[cell.row + i][cell.col + j].numMines == 0 && !this.grid[cell.row + i][cell.col + j].checked) {
				new RevealCells(finestra, this.grid[cell.row + i][cell.col + j], this).start();
				this.grid[cell.row + i][cell.col + j].button.setText("-");
				revealBlankCells(this.grid[cell.row + i][cell.col + j], -1, -1);
				return revealBlankCells(cell, i, j + 1);
			}
			else {
				new RevealCells(finestra, this.grid[cell.row + i][cell.col + j], this).start();
				return revealBlankCells(cell, i, j + 1);
			}
		}
		return 0;
	}
	public int iterateThroughBlankCells(Cell cell, int i, int j) {
		//caso base                  
		if (i == 1 && j == 1) {
			if (cell.row + i < height && cell.col + j < width) {
				if (this.grid[cell.row + i][cell.col + j].numMines == 0 && !this.grid[cell.row + i][cell.col + j].checked2) {
					this.grid[cell.row + i][cell.col + j].checked2 = true;
					return iterateThroughBlankCells(this.grid[cell.row + i][cell.col + j], -1, -1);
				}
				else {
					this.grid[cell.row + i][cell.col + j].checked2 = true;
					setBorders(this.grid[cell.row + i][cell.col + j], -1, -1);
					return 0;
				}
			}
			else {
				return 0;
			}
		}
		//controllo bound griglia
		if (cell.row + i < 0)
			return iterateThroughBlankCells(cell, i + 1, j);
		if (cell.row + i >= height)
			return 0;
		if (cell.col + j < 0)
			return iterateThroughBlankCells(cell, i, j + 1);
		if (cell.col + j >= width)
			return iterateThroughBlankCells(cell, i + 1, -1);
		//fine controllo bound griglia
		
		//caso ricorsivo
		if (j == 1) {
			if (this.grid[cell.row + i][cell.col + j].numMines == 0 && !this.grid[cell.row + i][cell.col + j].checked2) {
				this.grid[cell.row + i][cell.col + j].checked2 = true;
				iterateThroughBlankCells(this.grid[cell.row + i][cell.col + j], -1, -1);
				return iterateThroughBlankCells(cell, i + 1, -1);
			}
			else {
				this.grid[cell.row + i][cell.col + j].checked2 = true;
				setBorders(this.grid[cell.row + i][cell.col + j], -1, -1);
				return iterateThroughBlankCells(cell, i + 1, -1);
			}
		}
		else if (j < 1) {
			if (this.grid[cell.row + i][cell.col + j].numMines == 0 && !this.grid[cell.row + i][cell.col + j].checked2) {
				this.grid[cell.row + i][cell.col + j].checked2 = true;
				iterateThroughBlankCells(this.grid[cell.row + i][cell.col + j], -1, -1);
				return iterateThroughBlankCells(cell, i, j + 1);
			}
			else {
				this.grid[cell.row + i][cell.col + j].checked2 = true;
				setBorders(this.grid[cell.row + i][cell.col + j], -1, -1);
				return iterateThroughBlankCells(cell, i, j + 1);
			}
		}
		return 0;
	}
	public int setBorders(Cell cell, int i, int j) { //start form -1 0

		//caso base
		if (i == 1 && j == 1) {
				return 0;
		}
		if (cell.row + i < 0)
			return setBorders(cell, i + 1, j);
		if (cell.row + i >= height)
			return 0;
		if (cell.col + j < 0)
			return setBorders(cell, i, j + 1);
		if (cell.col + j >= width)
			return setBorders(cell, i + 1, -1);
		//caso ricorsivo
		if (j == 1) {
			if (i == 0 && this.grid[cell.row + 0][cell.col + 1].checked) {
				this.grid[cell.row + 0][cell.col + 1].button.setBorder(new MatteBorder(0, 0, 0, 0, new Color(135, 175, 58)));
			}
			//////////////////////
			if (i == 0 && !this.grid[cell.row + i][cell.col + j].checked) {
				Insets insets = this.grid[cell.row + i][cell.col + j].button.getMargin();
				if (insets.left + 1 > 1) insets.left -= 1;
				this.grid[cell.row + i][cell.col + j].button.setMargin(new Insets(insets.top, insets.left + 1, insets.bottom, insets.right));
				Insets newInsets = this.grid[cell.row + i][cell.col + j].button.getMargin();
				this.grid[cell.row + i][cell.col + j].button.setBorder(new MatteBorder(newInsets, new Color(135, 175, 58))); //LEFT BORDER
				return setBorders(cell, i + 1, -1);
			}
			else
				return setBorders(cell, i + 1, -1);
		}
		else if (j < 1) {
			if (i == 0 && j == -1 && this.grid[cell.row + 0][cell.col + -1].checked) {
				this.grid[cell.row + 0][cell.col + -1].button.setBorder(new MatteBorder(0, 0, 0, 0, new Color(135, 175, 58)));
			}
			else if(i == -1 && j == 0 && this.grid[cell.row + -1][cell.col + 0].checked) {
				this.grid[cell.row + -1][cell.col + 0].button.setBorder(new MatteBorder(0, 0, 0, 0, new Color(135, 175, 58)));
			}
			else if(i == 1 && j == 0 && this.grid[cell.row + 1][cell.col + 0].checked) {
				this.grid[cell.row + 1][cell.col + 0].button.setBorder(new MatteBorder(0, 0, 0, 0, new Color(135, 175, 58)));
			}
			///////////////////////
			if (i == 0 && j == -1 && !this.grid[cell.row + i][cell.col + j].checked) {
				Insets insets = this.grid[cell.row + i][cell.col + j].button.getMargin();
				if (insets.right + 1 > 1) insets.right -= 1;
				this.grid[cell.row + i][cell.col + j].button.setMargin(new Insets(insets.top, insets.left , insets.bottom, insets.right + 1));
				Insets newInsets = this.grid[cell.row + i][cell.col + j].button.getMargin();
				this.grid[cell.row + i][cell.col + j].button.setBorder(new MatteBorder(newInsets, new Color(135, 175, 58)));
				return setBorders(cell, i, j + 1);
			}
			else if (i == -1 && j == 0 && !this.grid[cell.row + i][cell.col + j].checked) {
				Insets insets = this.grid[cell.row + i][cell.col + j].button.getMargin();
				if (insets.bottom + 1 > 1) insets.bottom -= 1;
				this.grid[cell.row + i][cell.col + j].button.setMargin(new Insets(insets.top, insets.left, insets.bottom + 1, insets.right));
				Insets newInsets = this.grid[cell.row + i][cell.col + j].button.getMargin();
				this.grid[cell.row + i][cell.col + j].button.setBorder(new MatteBorder(newInsets, new Color(135, 175, 58)));
				return setBorders(cell, i, j + 1);
			}
			else if (i == 1 && j == 0 && !this.grid[cell.row + i][cell.col + j].checked) {
				Insets insets = this.grid[cell.row + i][cell.col + j].button.getMargin();
				if (insets.top + 1 > 1) insets.top -= 1;
				this.grid[cell.row + i][cell.col + j].button.setMargin(new Insets(insets.top + 1, insets.left, insets.bottom, insets.right));
				Insets newInsets = this.grid[cell.row + i][cell.col + j].button.getMargin();
				this.grid[cell.row + i][cell.col + j].button.setBorder(new MatteBorder(newInsets, new Color(135, 175, 58)));
				return setBorders(cell, i, j + 1);
			}
			else { // caselle diagonali
				return setBorders(cell, i, j + 1);
			}
		}
		return 0;
	}
	public void endGame() {
		for (int row = 0; row < height; row++) {
			System.out.println("\n");
			for (int col = 0; col < width; col++) {
				Cell cell = this.grid[row][col];
				cell.button.removeActionListener(ascoltatore);
				for (int i = 0; i < cell.button.getMouseListeners().length; i++) {
					cell.button.removeMouseListener(cell.button.getMouseListeners()[i]);
				}
				cell.button.removeMouseListener(ascoltatore_destro);
				System.out.print(cell.button.getMouseListeners().length + " ");
				if (!cell.mine) {
				}
				if (cell.mine)
					cell.button.setEnabled(false);
			}
		}
		finestra.smiley.setIcon(new ImageIcon(getClass().getResource("images/dead.png")));
		gameEnd = true;
	}
	public void newGame(Field field, DebugPanel debugPanel) {
		field.finestra.contentPane.remove(field.finestra.panel);   
		field.finestra.panel = new JPanel(new GridLayout(field.height, field.width, 0, 0));
		Finestra fin = field.finestra;
		field = new Field(field.height, field.width, fin);
		field.createField(fin);
		debugPanel.finestra.contentPane.add(field.finestra.panel, BorderLayout.CENTER);
		field.ascoltatore.debugPanel = debugPanel;
		field.finestra.smiley.setIcon(new ImageIcon(getClass().getResource("images/smiley.png")));
		field.finestra.revalidate();
		field.finestra.repaint();
	}
	
	@Override
    public Object clone() throws CloneNotSupportedException {
        Field clonedField = (Field) super.clone();

        // Deep copy mutable fields
        clonedField.multiplayerStartingCell = (Cell) this.multiplayerStartingCell.clone();
        clonedField.grid = deepCopyGrid();

        // Deep copy lists
        clonedField.emptyCells = new ArrayList<>(this.emptyCells.size());
        for (Cell cell : this.emptyCells) {
            clonedField.emptyCells.add((Cell) cell.clone());
        }
        clonedField.cantPlace = new ArrayList<>(this.cantPlace.size());
        for (Cell cell : this.cantPlace) {
            clonedField.cantPlace.add((Cell) cell.clone());
        }
        clonedField.checkedCells = new ArrayList<>(this.checkedCells.size());
        for (Cell cell : this.checkedCells) {
            clonedField.checkedCells.add((Cell) cell.clone());
        }

        return clonedField;
    }

    // Helper method for deep copying the grid
    private Cell[][] deepCopyGrid() {
        Cell[][] newGrid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try {
                    newGrid[i][j] = (Cell) this.grid[i][j].clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException("Clone not supported for Cell", e);
                }
            }
        }
        return newGrid;
    }
	
}
	//manca una classe
	


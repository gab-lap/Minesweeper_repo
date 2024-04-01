package main.client_server;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.border.MatteBorder;

import main.Cell;
import main.DebugPanel;
import main.Field;

public class ServerManager implements Runnable {
    private final Socket clientSocket; 
    static ObjectOutputStream outputStream;
    static ObjectInputStream inputStream;
    static DebugPanel debugPanel;
    static boolean receivingData = false;
    Field field;
    Cell startingCell;
    public static String message = "";
    
    public ServerManager(Socket socket, Field field, Cell startingCell) 
    { 
        this.clientSocket = socket; 
        this.field = field;
        this.startingCell = startingCell;
    } 

    public void run() { 
        try { 
        	System.out.println("HELLOOOOOOOOOOOOOOOO");
        	outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        	inputStream = new ObjectInputStream(clientSocket.getInputStream());
        	System.out.println("OKEEEEEEEEEEEE");
    		field.emptyCells.add(startingCell);
			field.generateEmptyCells(field.height, field.width);
			field.started = true;
			field.setMines();
			field.setNumsOnField(0, 0);
			startingCell.checked = true;
			startingCell.button.setText("-");
			startingCell.button.setEnabled(false);
			field.revealBlankCells(startingCell, -1, -1);
			field.iterateThroughBlankCells(startingCell, -1, -1);
			System.out.print("\nFIELD:");
			for (int row = 0; row < field.height; row++) {
				System.out.println("\n");
				for (int col = 0; col < field.width; col++) {
					if (field.grid[row][col].mine)
						System.out.print("M ");
					else
						System.out.print(field.grid[row][col].numMines + " "); 
				}
			}
			System.out.println("NUM MINES BEFORE CHANGE: " + field.numMines);
				System.out.println("\n\n");
			Field newField = null;
			try {
				newField = (Field) field.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int row = 0; row < field.height; row++) {
				for (int col = 0; col < field.width; col++) {
						field.grid[row][col].button.setBorder(new MatteBorder(0, 0, 0, 0, new Color(135, 175, 58)));
				}
			}
			for (int row = 0; row < field.height; row++) {
				for (int col = 0; col < field.width; col++) {
					if (field.grid[row][col].checked && field.grid[row][col].numMines != 0)
						field.setBorders(field.grid[row][col], -1, -1);
				}
			}
			System.out.println("NUM MINES CLONE: " + newField.numMines);
			System.out.println("NUM MINES AFTER CHANGE: " + field.numMines);
			System.out.print("\nFIELD AFTER CHANGE:");
			for (int row = 0; row < field.height; row++) {
				System.out.println("\n");
				for (int col = 0; col < field.width; col++) {
					//System.out.print(field.grid[row][col].checked + " ");
					System.out.print(field.grid[row][col].button.getBackground() + " ");
					
					//if (field.grid[row][col].mine)
						//System.out.print("M ");
					//else
					//	System.out.print(field.grid[row][col].numMines + " "); 
				}
			}
			System.out.print("\n\nCLONE:");
			
			for (int row = 0; row < newField.height; row++) {
				System.out.println("\n");
				for (int col = 0; col < newField.width; col++) {
					System.out.print(newField.grid[row][col].button.getBackground() + " ");
					//if (newField.grid[row][col].mine)
					//	System.out.print("M ");
					//else
					//	System.out.print(newField.grid[row][col].numMines + " "); 
				}
			}
			
			newField.finestra.menuBarListener = null;
			
			System.out.println("\n\n");
			outputStream.writeUTF("CIAOOOOOOOOOOOOOOOOOOOOO CLIENT");
			outputStream.flush();
			outputStream.writeUTF("COME STAI");
			outputStream.flush();
			outputStream.writeUTF("SENDING-DATA");
			outputStream.flush();
			outputStream.writeObject(newField);
			outputStream.flush();
			outputStream.writeUTF("HIHIHI");
			outputStream.flush();
			int counter = 0;
			while(true) {
    			
    			//outputStream.writeUTF("quit");
    			//outputStream.flush();
    			//message = "quit";
    			System.out.println(counter);
    			if (message.equals("quit") || counter == 100)
    				break;
    			counter++;
			}
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
        finally { 
        	System.out.println("CHIUSO TUTTO CAPOOOOOOO");
            try { 
                if (outputStream != null) { 
                    outputStream.close(); 
                } 
                if (inputStream != null) { 
                	inputStream.close(); 
                    clientSocket.close(); 
                } 
            } 
            catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
    }
}
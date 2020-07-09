package klotski;
/**
 * Program name:    Klotski.java
 * 
 * About:      		This is an application to play the game of Klotski
 * 					In order to win, get the big block out of the gate.
 * 
 * Written by:      Charles Thomas
 * 
 * Last Edited: 	1/7/2020        
 */

import javafx.application.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument.Iterator;

import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.*;

public class Klotski extends Application implements ActionListener {
	private static JFrame frame; // Start menu frame
    private static KlotskiBoard mainBoard;
    private static Stage stage;
    private static Scene scene;
    private static UndoStack undoStack; 
    private static Text movesText;
    private static Group root;
    private static Pane p;
    private static int numberOfGamesPlayed = 0;
    private static Klotski klotskiInstance = new Klotski();
    private static GameThread gt; 

    
    public static int numberOfThreads = 0;
    
    /**
     * Starts the Klotski game application
     */
    @Override
    public void start(Stage s) {  	
    	startKlotskiGame(s);
    }  

    public void startKlotskiGame(Stage s) {
    	stage = s;
    	undoStack = new UndoStack();
    	p = new Pane();
        setPrimaryStage(s);
        
    	// Start the stage and new KlotskiBoard
        stage = new Stage();
    	mainBoard = new KlotskiBoard();
    	root = new Group(mainBoard.getPane());
    	scene = new Scene(root, 400, 600);
        scene.setFill(null);
        stage.setOnCloseRequest(e -> System.exit(0));       
        stage.setTitle("Klotski");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
    	movesText = undoStack.getStackSizeAsText();
        p.getChildren().add(movesText);
        root.getChildren().add(p);
        p.relocate(135, 515);
        
        System.out.print("Start called...");
        
    	Solver solver = new Solver(mainBoard);
    	solver.createTree();
    }
    
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {        //menu based on button pressed
        int action = Integer.parseInt(e.getActionCommand()); 
        
        // switch menu called by startGUI()
        switch (action) {
        	// case 1 starts the game of Klotski
            case 1:
            	System.out.print("Start game...");
            	frame.dispose();
        		numberOfGamesPlayed++;
        		
            	Platform.runLater(() -> {
                    // fxThread is the JavaFX Application Thread after this call
            		try {
                		stage.close();
            		} catch (Exception ex) {
            			System.out.println("Exception: " + ex);
            		}
                    startKlotskiGame(stage);
                });
            	
            	//Thread.getAllStackTraces().clear();

            	System.out.println("Number of threads after clearing: " + Thread.activeCount());
        		//Application.launch(Klotski.class);
                break;
            // case 2 starts the Solve Game animation
            case 2:
            	System.out.print("Solve game...");
            	frame.dispose();
            	// TO DO create solve game animation
                break;
            // case 3 quits the game    
            case 3:
            	System.out.print("Quit game...");
                System.exit(0);
                break;
            // case 4 
            case 4:
            	System.out.print("About game...");
            	frame.dispose();
            	// TO DO 
                break;
            // case 5   
            case 5:
            	System.out.print("Rules...");
            	frame.dispose();
            	// TO DO 
                break;            
            default:
                System.out.print("\nInvalid option!!!");
        }
    }   
	
    public void startGUI() {
        Icon icon = new StartMenuIcon(Color.red);
        JButton start;
        JButton solve;
        JButton quit;
        JButton rules;
        JButton about;
        JLabel title;
        JPanel top;
        JPanel bottom;
        Font font1 = new Font("Serif", Font.PLAIN, 60);
        Font font2 = new Font("Serif", Font.PLAIN, 30);

    	// Start frame
        frame = new JFrame("Klotski - Created by Charles Thomas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        // Make title, about button, and rules button
        top = new JPanel();
        about = new JButton("About");
        about.setFont(font2);
        top.add(about);
        
        title = new JLabel("KLOTSKI");
        title.setFont(new Font("Serif", Font.PLAIN, 100));
        top.add(title);
        
        rules = new JButton("Rules");
        rules.setFont(font2);
        top.add(rules);
        
        frame.add(top, BorderLayout.NORTH);
        
        // Make buttons   
        bottom = new JPanel();
        start = new JButton("Start Playing", icon);
        start.setFont(font1);
        solve = new JButton("Solve Puzzle", icon);
        solve.setFont(font1);
        quit = new JButton("Quit Game", icon);
        quit.setFont(font1);
        bottom.add(start);
        bottom.add(solve);
        bottom.add(quit);

        // Make button listeners, set action commands (used in actionPerformed()), and add to frame
        start.addActionListener(this);
        solve.addActionListener(this);
        quit.addActionListener(this); 
        about.addActionListener(this);
        rules.addActionListener(this); 
        start.setActionCommand("1");
        solve.setActionCommand("2");
        quit.setActionCommand("3");      
        about.setActionCommand("4");
        rules.setActionCommand("5");
        frame.add(bottom, BorderLayout.CENTER); 
        frame.pack();     
    }
    
    public void setBlockPosition(int blockIndex, int x, int y) {
    	mainBoard.getBlocks()[blockIndex].setPosition(x, y);
    }
    
    public void winningCondition() {
    	gt.stop();
    	gt = klotskiInstance.new GameThread("Klotski");
    	System.out.println("Number of threads: " + numberOfThreads);
    }
    
    public static void setMovesText() {
    	p.getChildren().remove(movesText);
		movesText = undoStack.getStackSizeAsText();
        p.getChildren().add(movesText);
    }
    
    public static KlotskiBoard getMainBoard() {
    	return mainBoard;
    }
    
    public Stage getStage() {
    	return stage;
    }
    
    public Scene getScene() {
    	return scene;
    }
    
    public UndoStack getUndoStack() {
    	return undoStack;
    }
    
    public void incrementNumberOfGamesPlayed() {
    	numberOfGamesPlayed++;
    }
    
    public int getNumberOfGamesPlayed() {
    	return numberOfGamesPlayed;
    }
    
    public void setPrimaryStage(Stage s) {
        Klotski.stage = s;
    }
    
    /**
     * GameThread subclass
     * 
     * @author chuck
     *
     */
	 public class GameThread implements Runnable { 
	   
	     // to stop the thread 
	     private boolean exit; 
	   
	     private String name; 
	     Thread t; 
	   
	     GameThread(String threadname) 
	     { 
	    	 numberOfThreads++;
	         name = threadname; 
	         t = new Thread(this, name); 
	         System.out.println("New thread: " + t); 
	         exit = false; 
	         t.start(); // Starting the thread 
	     } 
	   
	     // execution of thread starts from run() method 
	     public void run() { 
	    	 klotskiInstance = new Klotski();
	    	 klotskiInstance.startGUI();
	    	 	    	 
	         int i = 0; 
	         while (!exit) { 
	             //System.out.println(name + ": " + i); 
	             i++; 
	             try { 
	                 Thread.sleep(1000); 
	             } 
	             catch (InterruptedException e) { 
	                 System.out.println("Caught:" + e); 
	             } 
	         } 
	         System.out.println(name + " Stopped."); 
	         try {
	        	 klotskiInstance.stop();
	         } 
	         catch (Exception e) {
	        	 System.out.println("Caught:" + e); 
	         }
	     }
	     
	     // for stopping the thread 
	     public void stop() {	      
		         exit = true; 
		 } 
	 } 
    
    public static void main(String[] args) {
    	gt = klotskiInstance.new GameThread("Klotski");
    	System.out.println("Number of threads: " + numberOfThreads);
    	
    	Scanner s = new Scanner(System.in);
    	s.close();
    }
}
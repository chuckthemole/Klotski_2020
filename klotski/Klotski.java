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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.*;

public class Klotski extends Application implements ActionListener {
	private static JFrame frame; // Start menu frame
    private static KlotskiBoard mainBoard;
    private static Stage stage;
    private static Scene scene;
    public static UndoStack undoStack; 
    public static Text movesText;
    public static Group root;
    public static Pane p;

    /**
     * Starts the Klotski game application
     */
    @Override
    public void start(Stage s) {
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
                Application.launch();
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
        //frame.setSize(1300, 400);
        frame.setLocationRelativeTo(null);
        //frame.setLayout(new FlowLayout());
        frame.setVisible(true);

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
    
    public static void setMovesText() {
    	p.getChildren().remove(movesText);
		movesText = undoStack.getStackSizeAsText();
        p.getChildren().add(movesText);
    }
    
    public KlotskiBoard getMainBoard() {
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
    
    private void setPrimaryStage(Stage s) {
        Klotski.stage = stage;
    }
    
    public static void main(String[] args) {
    	Klotski game = new Klotski();
    	game.startGUI();
    }
}
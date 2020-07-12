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

import javafx.util.Duration;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.Icon;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.paint.Color;

public class Klotski extends Application implements ActionListener {
	private static JFrame frame; // Start menu frame
    private static KlotskiBoard mainBoard;
    private static Stage stage;
    private static Scene scene;
    private static Text movesText;
    private static Group root;
    private static int numberOfGamesPlayed = 0;
    private static Klotski klotskiInstance = new Klotski();
    private static GameThread gt;
    private static Buttons buttons;
    private static Pane numberOfMovesPane;
    private static Pane pane;
    private UndoStack solver = null;
	private static SequentialTransition seqTransition;
	private static int solveCalled = 0;
	private BlockMove[] b;
	private Solver solveInstance = null;
    
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
    	Pane buttonPane = new Pane();
        pane = new Pane();
    	numberOfMovesPane = new Pane();
    	
        setPrimaryStage(s);
        
    	// Start the stage and new KlotskiBoard
        stage = new Stage();
        buttons = new Buttons();
        

    	if (solveCalled == -1) {
        	mainBoard = new KlotskiBoard();
        	//solveInstance = new Solver(mainBoard);
    		//mainBoard = solveInstance.getBoard().copy();
    	}
    	else if (solveCalled == 1) {
    		mainBoard = solveInstance.getBoard().copy();
    	}
    	else {
        	mainBoard = new KlotskiBoard();
    	}

    	// Set up the button pane
        buttonPane.setPrefSize(400, 100);
        buttonPane.relocate(0, 500);
        buttonPane.setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPane.getChildren().add(buttons.getHBox());
        buttons.getHBox().relocate(60, 15);
        pane.getChildren().add(0, buttonPane);
        
        // Add blocks to main pane
        pane.getChildren().add(mainBoard.getBlocks()[0].getRec());
        pane.getChildren().add(mainBoard.getBlocks()[1].getRec());
        pane.getChildren().add(mainBoard.getBlocks()[2].getRec());
        pane.getChildren().add(mainBoard.getBlocks()[3].getRec());
        pane.getChildren().add(mainBoard.getBlocks()[4].getRec());
        pane.getChildren().add(mainBoard.getBlocks()[5].getRec());
        pane.getChildren().add(mainBoard.getBlocks()[6].getRec());
        pane.getChildren().add(mainBoard.getBlocks()[7].getRec());
        pane.getChildren().add(mainBoard.getBlocks()[8].getRec());
        pane.getChildren().add(mainBoard.getBlocks()[9].getRec());
    	
        // Set stage
    	root = new Group(pane);
    	scene = new Scene(root, 400, 600);
        scene.setFill(Paint.valueOf("Black"));
        stage.setOnCloseRequest(e -> System.exit(0));       
        stage.setTitle("Klotski");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.sizeToScene();
        stage.show();
        
        movesText = mainBoard.getUndoStack().getStackSizeAsText();
        numberOfMovesPane.getChildren().add(movesText);
        root.getChildren().add(numberOfMovesPane);
        numberOfMovesPane.relocate(135, 515);
                
        System.out.print("Start called...");
        
        if (solveCalled == 1) {
        	//mainBoard.dissableMouse();
        	//solveGameFromIndex(solveInstance.getIndex(), solveInstance.getpathBoard()[solveInstance.getIndex()]);
        	solveGameFromIndex(solveInstance.getIndex());
        }
        else if (solveCalled == -1) {
        	solveGameFromIndex(0);
        }
        else {
        	
        }
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
            	solveCalled = 0;
            	Platform.runLater(() -> {
                    // fxThread is the JavaFX Application Thread after this call
            		try {
                		stage.close();
            		} catch (Exception ex) {
            			System.out.println("Exception: " + ex);
            		}
                    startKlotskiGame(stage);
                });
            	System.out.println("Number of threads after clearing: " + Thread.activeCount());
                break;
            // case 2 starts the Solve Game animation
            case 2:
            	System.out.print("Solve game...");
            	frame.dispose();
            	solveCalled = -1;
            	Platform.runLater(() -> {
                    // fxThread is the JavaFX Application Thread after this call
            		try {
                		stage.close();
            		} catch (Exception ex) {
            			System.out.println("Exception: " + ex);
            		}      
            		KlotskiBoard trashBoard = new KlotskiBoard();
            		trashBoard.disableMouse();
            		trashBoard = null;
                    startKlotskiGame(stage);
                });           	          
                break;
            // case 3 quits the game    
            case 3:
            	System.out.print("Quit game...");
                System.exit(0);
                break;
            // case 4 
            case 4:
            	solveCalled = 0;
            	System.out.print("About game...");
            	frame.dispose();
            	// TO DO 
                break;
            // case 5   
            case 5:
            	solveCalled = 0;
            	System.out.print("Rules...");
            	frame.dispose();
            	// TO DO 
                break;            
            default:
                System.out.print("\nInvalid option!!!");
        }
    }   
	
    public void startGUI() {
        Icon icon = new StartMenuIcon(Color.RED);
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
    	try {
	    	numberOfMovesPane.getChildren().remove(movesText);
	    	movesText = mainBoard.getUndoStack().getStackSizeAsText();
	        numberOfMovesPane.getChildren().add(movesText);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
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
    
    public static Buttons getButtons() {
    	return buttons;
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
    
    public void solveGame(int ind) {
    	LinkedList<BlockMove> list = new LinkedList<BlockMove>();
    	int i;

    	while (!solver.isEmpty()) {
        	list.add(new BlockMove(solver.peekUndoStack().getIndex(), solver.peekUndoStack().getNewPosition()));
   		 	solver.popUndoStack();
   		 	System.out.println("in solver*********************");
    	}
    	
    	seqTransition = new SequentialTransition ();
   		seqTransition.stop();
   		try {
    		stage.close();
        }
        catch (Exception e) {
        	System.out.print("Error closing stage...");
        }    
   		mainBoard.disableMouse();
        start(stage);
        
		double xStart;
		double yStart;
		double xEnd;
		double yEnd;
		Point[] blockPositions = new Point[10];
		for (i = 0; i < 10; i++) {
			blockPositions[i] = new Point((int) mainBoard.getBlocks()[i].getPosition().getX(), 
					(int) mainBoard.getBlocks()[i].getPosition().getY());
		}

   		for (i = 0; i < list.size(); i++) {
            //BlockMove bm = new BlockMove(b[i].getBlock(), b[i].getPosition());
   			Path path = new Path(); 
   			PathTransition pathTransition = new PathTransition();  
   			//xStart = mainBoard.getBlocks()[b[i].getBlock()].getPosition().getX();
   			//yStart = mainBoard.getBlocks()[b[i].getBlock()].getPosition().getY();
   			xEnd = list.get(i).getPosition().getX();
   			yEnd = list.get(i).getPosition().getY();
   	    	double xOffset;
   	    	double yOffset;
   	    	int index = i;
   	    	
   			xStart = blockPositions[list.get(i).getBlock()].getX();
   			yStart = blockPositions[list.get(i).getBlock()].getY();

   			//Setting up the path   
   			//path.getElements().add (new MoveTo ((float) b[i].getPosition().getX(), (float) b[i].getPosition().getY()));  
   			//path.getElements().add (new CubicCurveTo (240f, 230f, 500f, 340f, 600, 50f));  
   			if(mainBoard.getBlocks()[list.get(i).getBlock()].getType() == "smallSquare") {
   	   			path.getElements().add (new MoveTo (xStart + 50, yStart + 50)); 
   	   			path.getElements().add(new LineTo(xEnd + 50, yEnd + 50));
   			}
   			else if(mainBoard.getBlocks()[list.get(i).getBlock()].getType() == "verticalRectangle") {
   	   			path.getElements().add (new MoveTo (xStart + 50, yStart + 100)); 
   	   			path.getElements().add(new LineTo(xEnd + 50, yEnd + 100));
   			}
   			else if(mainBoard.getBlocks()[list.get(i).getBlock()].getType() == "horizontalRectangle") {
   	   			path.getElements().add (new MoveTo (xStart + 100, yStart + 50)); 
   	   			path.getElements().add(new LineTo(xEnd + 100, yEnd + 50));
   			}
   			else {
   	   			path.getElements().add (new MoveTo (xStart + 100, yStart + 100)); 
   	   			path.getElements().add(new LineTo(xEnd + 100, yEnd + 100));
   			}   	
   			
	   		blockPositions[list.get(i).getBlock()].setLocation(new Point((int) xEnd, (int) yEnd)); 

   			xOffset = mainBoard.getBlocks()[list.get(i).getBlock()].getPosition().getX() - list.get(i).getPosition().getX();
   			yOffset = mainBoard.getBlocks()[list.get(i).getBlock()].getPosition().getY() - list.get(i).getPosition().getY();
   			        
   			//Setting duration for the PathTransition  
   			pathTransition.setDuration(Duration.millis(500));  
         
   			//Setting Node on which the path transition will be applied
   			Node n = mainBoard.getBlocks()[list.get(i).getBlock()].getRec();
   			pathTransition.setNode(n);  
	
   			//setting path for the path transition   
   			pathTransition.setPath(path); 
         
   			//setting orientation for the path transition   
   			pathTransition.setOrientation(OrientationType.NONE);
            
            seqTransition.getChildren().add(i, pathTransition);
            seqTransition.getChildren().add(i + 1, new PauseTransition(Duration.millis(600)));
            
            seqTransition.getChildren().get(i).setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	mainBoard.setBlockPosition(mainBoard.getBlocks()[list.get(index).getBlock()], list.get(index).getPosition());	
                	mainBoard.getBlocks()[list.get(index).getBlock()].getRec().setX(list.get(index).getPosition().getX() + xOffset);
                	mainBoard.getBlocks()[list.get(index).getBlock()].getRec().setY(list.get(index).getPosition().getY() + yOffset);
		    		//mainBoard.setBlockPosition(mainBoard.getBlocks()[i], new Point((int) b[i].getPosition().getX() + 50, (int) b[i].getPosition().getY() + 100));
                }
            });
            
       		seqTransition.play();
       		
       		seqTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
               		solveGameFromIndex(ind);
                }
            });
   		}
    }
    
    /**
     * Method to solve the game from original position
     */
    public void solveGameFromIndex(int startHere) {    	
    	int i;
		double xStart;
		double yStart;
		double xEnd;
		double yEnd;
		Point[] blockPositions = new Point[10];
		
    	initializeSolvePath();
    	seqTransition = new SequentialTransition ();
   		seqTransition.stop();
		
		for (i = 0; i < 10; i++) {
			blockPositions[i] = new Point((int) mainBoard.getBlocks()[i].getPosition().getX(), 
					(int) mainBoard.getBlocks()[i].getPosition().getY());
		}

   		for (i = startHere; i < b.length; i++) {
            //BlockMove bm = new BlockMove(b[i].getBlock(), b[i].getPosition());
   			Path path = new Path(); 
   			PathTransition pathTransition = new PathTransition();  
   			//xStart = mainBoard.getBlocks()[b[i].getBlock()].getPosition().getX();
   			//yStart = mainBoard.getBlocks()[b[i].getBlock()].getPosition().getY();
   			xEnd = b[i].getPosition().getX();
   			yEnd = b[i].getPosition().getY();
   	    	double xOffset;
   	    	double yOffset;
   	    	int index = i;
   	    	
   			xStart = blockPositions[b[i].getBlock()].getX();
   			yStart = blockPositions[b[i].getBlock()].getY();

   			//Setting up the path   
   			//path.getElements().add (new MoveTo ((float) b[i].getPosition().getX(), (float) b[i].getPosition().getY()));  
   			//path.getElements().add (new CubicCurveTo (240f, 230f, 500f, 340f, 600, 50f));  
   			if(mainBoard.getBlocks()[b[i].getBlock()].getType() == "smallSquare") {
   	   			path.getElements().add (new MoveTo (xStart + 50, yStart + 50)); 
   	   			path.getElements().add(new LineTo(xEnd + 50, yEnd + 50));
   			}
   			else if(mainBoard.getBlocks()[b[i].getBlock()].getType() == "verticalRectangle") {
   	   			path.getElements().add (new MoveTo (xStart + 50, yStart + 100)); 
   	   			path.getElements().add(new LineTo(xEnd + 50, yEnd + 100));
   			}
   			else if(mainBoard.getBlocks()[b[i].getBlock()].getType() == "horizontalRectangle") {
   	   			path.getElements().add (new MoveTo (xStart + 100, yStart + 50)); 
   	   			path.getElements().add(new LineTo(xEnd + 100, yEnd + 50));
   			}
   			else {
   	   			path.getElements().add (new MoveTo (xStart + 100, yStart + 100)); 
   	   			path.getElements().add(new LineTo(xEnd + 100, yEnd + 100));
   			}   	
   			
	   		blockPositions[b[i].getBlock()].setLocation(new Point((int) xEnd, (int) yEnd)); 

   			xOffset = mainBoard.getBlocks()[b[i].getBlock()].getPosition().getX() - b[i].getPosition().getX();
   			yOffset = mainBoard.getBlocks()[b[i].getBlock()].getPosition().getY() - b[i].getPosition().getY();
   			        
   			//Setting duration for the PathTransition  
   			pathTransition.setDuration(Duration.millis(500));  
         
   			//Setting Node on which the path transition will be applied
   			Node n = mainBoard.getBlocks()[b[i].getBlock()].getRec();
   			pathTransition.setNode(n);  
	
   			//setting path for the path transition   
   			pathTransition.setPath(path); 
         
   			//setting orientation for the path transition   
   			pathTransition.setOrientation(OrientationType.NONE);
            
            seqTransition.getChildren().add(i - startHere, pathTransition);
            seqTransition.getChildren().add(i + 1 - startHere, new PauseTransition(Duration.millis(600)));
            
            seqTransition.getChildren().get(i).setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	mainBoard.setBlockPosition(mainBoard.getBlocks()[b[index].getBlock()], b[index].getPosition());	
                	mainBoard.getBlocks()[b[index].getBlock()].getRec().setX(b[index].getPosition().getX() + xOffset);
                	mainBoard.getBlocks()[b[index].getBlock()].getRec().setY(b[index].getPosition().getY() + yOffset);
		    		//mainBoard.setBlockPosition(mainBoard.getBlocks()[i], new Point((int) b[i].getPosition().getX() + 50, (int) b[i].getPosition().getY() + 100));
                }
            });
         
       
   		}	
   		seqTransition.play();
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
	    	 	    	 
	         //int i = 0; 
	         while (!exit) { 
	        	 /*
	             System.out.println("\n\n\n**************************" + name + ": " + i + "**************************\n\n"); 
	             i++; 
	             try { 
	                 Thread.sleep(5000); 
	             } 
	             catch (InterruptedException e) { 
	                 System.out.println("Caught:" + e); 
	             } 
	             */
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
	 
	 /**
	  * StartMenuIcon subclass
	  * 
	  * @author chuck
	  *
	  */
	 public class StartMenuIcon implements Icon {
		 Color color;
		     
		     public StartMenuIcon(Color c) {
		         color = c;
		     }
		     
		     public int getIconWidth() {
		         return 20;
		     }
		     
		     public int getIconHeight() {
		         return 20;
		     }
		     
		     public void paintIcon(Component c, Graphics g, int x, int y) {
		         //g.setColor(color);		       
		         g.fillArc(x, y, getIconWidth(), getIconHeight(), 45, 270);
		     }
		 }
	 
	 public class Buttons {
		    HBox hbox;
		    Button restart;
		    Button solve;
		    Button quit; 
		    Button undo;

		    Buttons() {     
		    	hbox = new HBox();
		    	restart = new Button("Restart");
		    	solve = new Button("Solve");
		        quit = new Button("Quit"); 
		        undo = new Button("Undo");
		    	
		        // Set up HBox style
		        hbox.setSpacing(10);
		        hbox.setPadding(new Insets(0, 20, 10, 20));
		        //hbox.getChildren().addAll(restart, solve, quit, undo);
		        hbox.getChildren().add(restart);
		        hbox.getChildren().add(solve);
		        hbox.getChildren().add(quit);
		        hbox.getChildren().add(undo);
		        hbox.setStyle("-fx-padding: 10;" + 
		                      "-fx-border-style: solid inside;" + 
		                      "-fx-border-width: 2;" +
		                      "-fx-border-insets: 5;" + 
		                      "-fx-border-radius: 5;" + 
		                      "-fx-border-color: blue;");
		       
		        hbox.setLayoutX(50);
		        hbox.setLayoutY(550);
		        
		        // Set up events for buttons
		        quit.setOnAction(new EventHandler<ActionEvent>() { 
		            @Override
		            public void handle(ActionEvent event) {
		                Platform.exit();
		                System.exit(0);
		            }
		        });
		        
		        solve.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		                // Set up solver
		            	solveCalled = 1;
		            	solveInstance = new Solver(mainBoard);
		            	Platform.runLater(() -> {
		                    // fxThread is the JavaFX Application Thread after this call
		            		try {
		                		stage.close();
		            		} catch (Exception ex) {
		            			System.out.println("Exception: " + ex);
		            		}
		            		mainBoard.disableMouse();
		                    startKlotskiGame(stage);
		                });   
		            }
		        });
		        
		        restart.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	solveCalled = 0;
		            	seqTransition.stop();
		            	try {
		            		stage.close();
		                }
		                catch (Exception e) {
		                	System.out.print("Error closing stage...");
		                }           	
		            	mainBoard.enableMouse();
		                start(stage);        
		            }
		        });
		        
		        undo.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	if(mainBoard.isMouseActive()) {
			            	mainBoard.undo();
			            	mainBoard.getUndoStack().printUndoStack();
		            	}
		            }
		        });
		    }
		    
		    public HBox getHBox() {
		        return hbox;
		    }
		    
		    public Button getRestart() {
		        return restart;
		    }
		    
		    public Button getUndo() {
		        return undo;
		    }
		    
		    public Button getSolve() {
		        return solve;
		    }
		    
		    public Button getQuit() {
		        return quit;
		    }
	 }
	 
	 public void initializeSolvePath() {
		b = new BlockMove[118];
    	b[0] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(200, 400));
    	b[1] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(100, 400));
    	b[2] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 300));
    	b[3] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(200, 200));
    	b[4] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(100, 200));
    	b[5] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 300));
    	b[6] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(0, 400));
    	b[7] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(100, 300));
    	b[8] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(100, 200));
    	b[9] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(0, 200));
    	b[10] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 200));
    	b[11] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(200, 300));
    	b[12] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(300, 200));
    	b[13] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(200, 200));
    	b[14] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(200, 300));
    	b[15] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(100, 400));
    	b[16] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 400));
    	b[17] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(0, 300));
    	b[18] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 200));
    	b[19] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(0, 200));
    	b[20] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 200));
    	b[21] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(100, 200));
    	b[22] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(200, 200));
    	b[23] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(200, 400));
    	b[24] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(100, 400));
    	b[25] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 200));
    	b[26] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(300, 400));
    	b[27] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 400));
    	b[28] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(0, 400));
    	b[29] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(0, 300));
    	b[30] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(0, 200));
    	b[31] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(100, 200));
    	b[32] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(200, 200));
    	b[33] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(300, 100));
    	b[34] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(300, 200));
    	b[35] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(200, 0));
    	b[36] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(100, 0));
    	b[37] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(0, 100));
    	b[38] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(0, 200));
    	b[39] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(0, 0));
    	b[40] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(0, 100));
    	b[41] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(0, 200));
    	b[42] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(100, 100));
    	b[43] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(100, 200));
    	b[44] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(100, 0));
    	b[45] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(300, 100));
    	b[46] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(300, 0));
    	b[47] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 200));
    	b[48] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 300));
    	b[49] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(200, 400));
    	b[50] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 200));
    	b[51] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(200, 300));
    	b[52] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(100, 400));
    	b[53] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(0, 300));
    	b[54] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(200, 400));
    	b[55] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(100, 300));
    	b[56] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(100, 200));
    	b[57] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 200));
    	b[58] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(100, 100));
    	b[59] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(100, 0));
    	b[60] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(0, 0));
    	b[61] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 0));
    	b[62] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 0));
    	b[63] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 100));
    	b[64] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 0));
    	b[65] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(0, 200));
    	b[66] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(0, 100));
    	b[67] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(0, 300));
    	b[68] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(100, 300));
    	b[69] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(100, 400));
    	b[70] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(100, 200));
    	b[71] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 100));
    	b[72] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(100, 0));
    	b[73] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(200, 0));
    	b[74] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 100));
    	b[75] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 0));
    	b[76] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(200, 200));
    	b[77] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 200));
    	b[78] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 300));
    	b[79] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(100, 100));
    	b[80] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(100, 0));
    	b[81] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(0, 0));
    	b[82] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(0, 200));
    	b[83] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(0, 400));
    	b[84] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 400));
    	b[85] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(100, 200));
    	b[86] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 100));
    	b[87] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 200));
    	b[88] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(300, 0));
    	b[89] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 100));
    	b[90] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 0));
    	b[91] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(100, 0));
    	b[92] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(0, 100));
    	b[93] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(0, 0));
    	b[94] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(0, 200));
    	b[95] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 200));
    	b[96] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 300));
    	b[97] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 100));
    	b[98] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 200));
    	b[99] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(200, 0));
    	b[100] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 100));
    	b[101] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(300, 300));
    	b[102] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 0));
    	b[103] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(300, 200));
    	b[104] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(200, 300));
    	b[105] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(200, 400));
    	b[106] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(300, 400));
    	b[107] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(100, 400));
    	b[108] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(200, 400));
    	b[109] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(0, 300));
    	b[110] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(100, 200));
    	b[111] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 200));
    	b[112] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 200));
    	b[113] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(100, 200));
    	b[114] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(200, 200));
    	b[115] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(300, 300));
    	b[116] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(300, 400));
    	b[117] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(100, 300));
	    	
	 }
    
    public static void main(String[] args) {
    	gt = klotskiInstance.new GameThread("Klotski");
    	System.out.println("Number of threads: " + numberOfThreads);
    }
}
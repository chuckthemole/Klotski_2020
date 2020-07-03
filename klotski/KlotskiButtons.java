package klotski;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Buttons placed in the pane while playing the game
*/

public class KlotskiButtons extends Klotski {
    HBox hbox;
    Button restart;
    Button solve;
    Button quit; 
    Button undo;

    KlotskiButtons() {     
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
            }
        });
        
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try {
                	getStage().close();
                }
                catch (Exception e) {
                	System.out.print("Error closing stage...");
                }           	
                start(getStage());        
            }
        });
        
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try {
	            	Block b = getUndoStack().pop();
	            	getMainBoard().setBlockPosition(b.getIndex(), b.getPosition());
	            	getMainBoard().setBoard(b.getBoardPositions());
	            	decrementMoveNumber();
	            	printMoveNumber();
            	}
            	catch(Exception e) {
    				System.out.println("*********** Error  " + e + "  Error*************\nEmpty Stack");
    			}
            	
            	printUndoStack();
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

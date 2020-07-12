package klotski;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

public class KlotskiBoardList {
    private HashMap<String, LinkedList<KlotskiBoardString>> list;
    private int size;
    
    KlotskiBoardList() {
        list = new HashMap<String, LinkedList<KlotskiBoardString>>();
        size = 0;
    }  

    public void insert(KlotskiBoard b) {
    	this.insert(b.getBoardByType());
    }

    public String find(KlotskiBoard b) {
    	KlotskiBoardString brd = new KlotskiBoardString(b.getBoardByType());
        return find(this.list, brd.getBoard());
    }
    
    public boolean contains(KlotskiBoard b) {
    	KlotskiBoardString brd = new KlotskiBoardString(b.getBoardByType());
        return contains(list, brd.getBoard());
    }
    
    public HashMap<String, LinkedList<KlotskiBoardString>> getList() {
        return this.list;
    }
    
    public Object getKlotskiBoard(String s) {
        return this.list.get(s);
    }
    
    public int getSize() {
        return size;
    }
    
    /* ******************************************************************
     * **************************** PRIVATE METHODS**********************
     * ******************************************************************  
     */
    
    /*
     * Insert Contact to HashMap list. Inserts 2 entries
     * into the list. One based on hashed name and the
     * other based on hashed phone.
     * 
     * Running time: 2 lines take linear time, l.add(c),
     * because we are adding a contact to a linked list,
     * we have to iterate to the end of the list.
     * So the running time is linear.
     */
    private void insert(int[][] b) {
    	KlotskiBoardString boardString = new KlotskiBoardString(b);
        LinkedList<KlotskiBoardString> l = null;

        //insert contact with hashed name 
        if(list.get(boardString.getBoard()) == null)
            l = new LinkedList<KlotskiBoardString>();
        else 
            l = list.get(boardString.getBoard()); //constant time
        l.add(boardString); //this takes the length of the list, x
        list.put(boardString.getBoard(), l); //constant time
        
        size++;
    }

    private String find(Map<String, LinkedList<KlotskiBoardString>> map, String s) {
    	ListIterator<KlotskiBoardString> temp = null;
    	KlotskiBoardString c = null;

        if(!map.isEmpty() && map.get(s) != null) { 
            temp = map.get(s).listIterator();
        	while(temp.hasNext()) {
        		c = temp.next();
        		//Compare string to Contact.name
            	if(c.getBoard().compareTo(s) == 0) {
            		//System.out.println("Index " + i + ": " + c.name + ", " + c.phone);          		
            		return c.getBoard();
            	}        	
            }
        }       	

    	return null;
    }
    
    private boolean contains(Map<String, LinkedList<KlotskiBoardString>> map, String s) {
    	ListIterator<KlotskiBoardString> temp = null;
    	KlotskiBoardString c = null;

        if(!map.isEmpty() && map.get(s) != null) { 
            temp = map.get(s).listIterator();
        	while(temp.hasNext()) {
        		c = temp.next();
        		//Compare string to Contact.name
            	if(c.getBoard().compareTo(s) == 0) {
            		//System.out.println("Index " + i + ": " + c.name + ", " + c.phone);          		
            		return true;
            	}        	
            }
        }       	

    	return false;
    }
    
    public class KlotskiBoardString {
    	String board;
    	
    	KlotskiBoardString(int[][] b) {
    		board = new String("");
    		int i, j;
    		
    		for (i = 0; i < 4; i++) {
    			for (j = 0; j < 5; j++) {
    				board += (b[i][j]);
    			}
    		}
    	}
    	
    	public String getBoard() {
    		return board;
    	}
    }
}


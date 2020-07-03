package klotski;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

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
        g.setColor(color);
        g.fillArc(x, y, getIconWidth(), getIconHeight(), 45, 270);
    }
}

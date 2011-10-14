
import java.awt.*;

public class ShapeContainer extends Container {
    
  public ShapeContainer () {
   super ();
   setBackground (Color.white); 
  }

  public void paint(Graphics g) {
    g.setColor(getBackground());
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor (Color.black);
    super.paint(g);
  }

  public Dimension getPreferredSize () {
    return new Dimension (440,400);
  }
}

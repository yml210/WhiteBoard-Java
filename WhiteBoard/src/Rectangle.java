
import java.awt.*;
import java.awt.event.*;

public class Rectangle extends Shape {

  public Rectangle (int x1, int y1, int x2, int y2) {
    super (x1,y1,x2,y2);
  }

  public Object clone () {      
    Rectangle rect=new Rectangle(x1,y1,x2,y2);
    rect.setLocation(getLocation());
    rect.setSelectState(getSelectState());
    return rect;
  }

  public void paint (Graphics g) {
      g.setColor(Color.black);
      super.paint(g);
      int h=Math.abs(y1-y2)-1, w=Math.abs(x1-x2)-1;
      //int x0=x1>x2?x2:x1, y0=y1>y2?y2:y1;
      int x0=0, y0=0;
      g.drawRect(x0,y0,w,h);
  }
  
}

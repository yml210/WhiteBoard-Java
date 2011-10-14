
import java.awt.*;
import java.awt.event.*;

public class Line extends Shape {

  protected int startX, startY, endX, endY;
  /** constructs a Line object */

  public Line (int x1, int y1, int x2, int y2) {
    super (x1,y1,x2,y2);
    startX=x1-this.x1; startY=y1-this.y1;
    endX=x2-this.x1; endY=y2-this.y1;
  }
  
  public double length(){
      return Math.sqrt((startX-endX)*(startX-endX)+(startY-endY)*(startY-endY));
  }
  
  public void setCoordinates(int x1,int y1, int x2, int y2){
      super.setCoordinates(x1, y1, x2, y2);
      startX=x1-this.x1;startY=y1-this.y1;
      endX=x2-this.x1; endY=y2-this.y1;
  }

  /** creates a copy of this Line object.  This method creates a new Line,
      sets its locations, sets its select state, and returns the new Line.
  */

  public Object clone () {
    Line l = new Line (startX,startY,endX,endY);
    l.setLocation (getLocation());
    l.setSelectState (getSelectState());
    return l;
  }

  /** this method paints the line.  */

  public void paint (Graphics g) {
    super.paint(g);

    if (GlobalDebug.isOn) 
      System.out.println ("Line.paint()");
    
    g.setColor (Color.black);
    g.drawLine(startX,startY,endX-1,endY-1);
 //   g.drawLine(0,0,30,30);
/*
    if (y1 <= y2) {
      g.drawLine (0,0,x2-x1-1,y2-y1-1);
    }
    else {
      g.drawLine (0,y1-y2-1,x2-x1-1,0);
    }
*/  
 }

}

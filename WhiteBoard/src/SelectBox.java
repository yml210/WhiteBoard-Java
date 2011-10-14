
import java.awt.*;
import java.awt.event.*;

public class SelectBox extends Shape {

  public SelectBox (int x1, int y1, int x2, int y2) {
    super (x1,y1,x2,y2);
  }

  public void setBoxSize (int boxSize) {
  }
  
  public int getBoxSize () {
    return dSelectedBoxSize;
  }

  public void paint (Graphics g) {
      int w=Math.abs(x1-x2)-1, h=Math.abs(y1-y2)-1;
      int x=0,y=0;
      g.drawRect(x,y,dSelectedBoxSize,dSelectedBoxSize);
      g.fillRect(x,y,dSelectedBoxSize,dSelectedBoxSize);
      x=w-dSelectedBoxSize;
      g.drawRect(x,y,dSelectedBoxSize,dSelectedBoxSize);
      g.fillRect(x,y,dSelectedBoxSize,dSelectedBoxSize);     
      y=h-dSelectedBoxSize;
      g.drawRect(x,y,dSelectedBoxSize,dSelectedBoxSize);
      g.fillRect(x,y,dSelectedBoxSize,dSelectedBoxSize);
      x=0;
      g.drawRect(x,y,dSelectedBoxSize,dSelectedBoxSize);
      g.fillRect(x,y,dSelectedBoxSize,dSelectedBoxSize);
  }
  
  private static int dSelectedBoxSize=4;
}

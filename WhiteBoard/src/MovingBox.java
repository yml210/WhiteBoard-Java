/*
 * MovingBox.java
 *
 * Created on July 31, 2001, 1:23 PM
 */

import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author  Yimin Li
 * @version 7/31/2001
 */
public class MovingBox extends Shape {
    
    private static int BoxSize=20;
    private int xb1,yb1,xb2,yb2;

    /** Creates new MovingBox */
    public MovingBox(int x1, int y1, int x2, int y2) {
        super(x1,y1,x2,y2);
        xb1=x1-this.x1;
        yb1=y1-this.y1;
        xb2=xb1+BoxSize/2;
        yb2=yb1+BoxSize/2;
    }
    
    public Object clone(){
        Shape mb=new MovingBox(x1,y1,x2,y2);
        mb.setLocation(getLocation());
        mb.setSelectState(getSelectState());
        return mb;
    }
    
    public void paint(Graphics g){
        super.paint(g);

        g.drawRect(xb1,yb1,BoxSize,BoxSize);
        g.drawRect(xb2,yb2,BoxSize,BoxSize);
        
    }
}

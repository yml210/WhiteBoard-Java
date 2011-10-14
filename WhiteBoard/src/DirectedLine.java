// DirectedLine.java

import java.awt.*;
import java.awt.event.*;
import java.lang.Double;
/**
 * Drawing a directed line
 * This class subclasses the Line class.
 *
 * by Yimin Li
 * 7/31/2001
 */

public class DirectedLine extends Line {
    private static final int ARROW_SIZE= 16;
    private static final double ARROW_ANGLE=Math.PI/15;
    
    public DirectedLine(int x1, int y1, int x2, int y2){
        super(x1,y1,x2,y2);
    }
    
    public Object clone(){
        DirectedLine dl=new DirectedLine(x1,y1,x2,y2);
        dl.setLocation(getLocation());
        dl.setSelectState(getSelectState());
        return dl;
    }
    /**Override the setCoordinates method in super class to make sure that
     * the arrow at one end of line is always in the paint area.
     */
    
    public void setCoordinates(int x1, int y1, int x2, int y2){
        // find lefttop and rightbottom points.
        int dx1=Math.min(x1,x2), dx2=Math.max(x1,x2),
            dy1=Math.min(y1,y2), dy2=Math.max(y1,y2);
        //hold the arrow in drawing area
        int dx=dx2-dx1, dy=dy2-dy1,ddx=0,ddy=0;
	float t=(new Double(Math.tan(ARROW_ANGLE))).floatValue();
        
        if(dx< dy*t){ 
            ddx=Math.round(dy*t);
            ddx-=dx;
        }
        else if(dy < dx*t){
            ddy=Math.round(dx*t);
            ddy-=dy;
        }
        int w=dx+2*ddx, h=dy+2*ddy;
        
        //set the lefttop and rightbottom of the bourdary box
        this.x1=dx1-ddx; this.y1=dy1-ddy;
        this.x2=this.x1+w; this.y2=this.y1+h;
        
        //set the start and end point of the line
        startX=x1-this.x1; startY=y1-this.y1;
        endX=x2-this.x1-1; endY=y2-this.y1-1;
        
        //set the draw area and location
        setSize(w,h);
        setLocation(this.x1,this.y1);       
    }
    
    public void paint(Graphics g){
        super.paint(g);

        float c1=(new Double((endX-startX)/length())).floatValue(), 
		s1=(new Double((endY-startY)/length())).floatValue(),
        	c2=(new Double(Math.cos(ARROW_ANGLE))).floatValue(), 
		s2=(new Double(Math.sin(ARROW_ANGLE))).floatValue();

        int dx1=Math.round(ARROW_SIZE*(c1*c2+s1*s2)), 
            dy1=Math.round(ARROW_SIZE*(s1*c2-s2*c1)),
            dx2=Math.round(ARROW_SIZE*(c1*c2-s1*s2)),
            dy2=Math.round(ARROW_SIZE*(s1*c2+s2*c1));
        
	int [] xx={endX, endX-dx1, endX-dx2};
	int [] yy={endY, endY-dy1, endY-dy2};
	
        g.fillPolygon(xx,yy,3);
    }
}

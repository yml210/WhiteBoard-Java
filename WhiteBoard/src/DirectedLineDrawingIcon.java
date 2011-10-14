/*
 * DirectedLineDrawingIcon.java
 *
 * Created on July 31, 2001, 10:02 AM
 */


/**
 *
 * @author Yimin Li 
 * @version 7/31/2001
 */
public class DirectedLineDrawingIcon extends DrawingIcon {

    /** Creates new DirectedLineDrawingIcon */
    public DirectedLineDrawingIcon() {
    }
    
    public Shape createShape(int x1, int y1, int x2, int y2){
        return new DirectedLine(x1,y1,x2,y2);
    }
    
    public String getCommand(){
        return "DirectedLine";
    }
}

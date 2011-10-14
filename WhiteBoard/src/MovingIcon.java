/*
 * MovingIcon.java
 *
 * Created on July 31, 2001, 1:19 PM
 */


/**
 *
 * @author  Yimin Li
 * @version 7/31/2001
 */
public class MovingIcon extends DrawingIcon {

    /** Creates new MovingIcon */
    public MovingIcon() {
        super();
    }
    
    public Shape createShape(int x1, int y1, int x2, int y2){
        return new MovingBox(x1,y1,x2,y2);
    }
    
    public String getCommand(){
        return "Moving";
    }
}
        
        



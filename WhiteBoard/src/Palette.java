
import java.awt.*;
import java.util.*;

/** A Palette is the visual entity (a Panel)
    that contains all of the drawing
    icons.  A Palette has a Vector of drawing icons. */

public class Palette extends Panel {

  private Vector dDrawingIcons;
  private  Dimension paletteDim;

  // should be generalized to take an array of strings...

  private Color dBorderColor = Color.black;

  /** this method should create all of the drawing icons that will be
      shown in the palette, and add them to the palette's vector of
      drawing icons. */

  private void createDrawingIcons () {
    // creates the icons, inserts them into dDrawingIcons,
    // and adds them to the palette
    dDrawingIcons = new Vector ();
    LineDrawingIcon lineIcon = new LineDrawingIcon();
    dDrawingIcons.add (lineIcon);
    add(lineIcon);
    
    DirectedLineDrawingIcon dlineIcon=new DirectedLineDrawingIcon();
    dDrawingIcons.add(dlineIcon);
    add(dlineIcon);
    
    RectangleDrawingIcon rectIcon=new RectangleDrawingIcon();
    dDrawingIcons.add(rectIcon);
    add(rectIcon);
    
    OvalDrawingIcon ovalIcon=new OvalDrawingIcon();
    dDrawingIcons.add(ovalIcon);
    add(ovalIcon);
    
    SelectionIcon selectionIcon=new SelectionIcon();
    dDrawingIcons.add(selectionIcon);
    add(selectionIcon);
    
    MovingIcon mIcon=new MovingIcon();
    dDrawingIcons.add(mIcon);
    add(mIcon);
  }

  public void paint (Graphics g) {
    super.paint(g);
    g.setColor(dBorderColor);
    g.drawRect(0,0,getWidth()-1,getHeight()-1);
  }
    
  /** returns an enumeration of all of the drawing icons. */

  public Enumeration getAllDrawingIcons () {
    return dDrawingIcons.elements();
  }

  /** the constructor should call createDrawingIcons(), and
      add each of the drawing icons to this panel. */

  public Palette () {
    super (new GridLayout (1,6));
    paletteDim = new Dimension(480,50);
    createDrawingIcons ();
  }

  /** returns the preferred size of the palette. */
  public Dimension getPreferredSize () {
    return paletteDim;
  }

  /** returns the minimum size of the palette. */
  public Dimension getMinimumSize () {
    return paletteDim;
  }

  /** returns the maximum size of the palette. */
  public Dimension getMaximumSize () {
    return paletteDim;
  }
  
}


import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class PaletteController implements ActionListener {

  private Palette dPalette;

  private Vector dDrawingIconSelectionListeners;

  private static final String dInitiallySelectedDrawingCommand = "Line";

  /** the icon that is initally selected when the application starts. */
  private DrawingIcon dInitiallySelectedIcon;

  public PaletteController (Palette aPalette) {
    dPalette = aPalette;
    dDrawingIconSelectionListeners=new Vector();

    /** add this as a listener to all DrawingIcons in the Palette. */
    Enumeration icons=dPalette.getAllDrawingIcons();
    while(icons.hasMoreElements()){
        DrawingIcon icon= (DrawingIcon) icons.nextElement();
        icon.addActionListener(this);
        if(icon.getCommand().equals(dInitiallySelectedDrawingCommand)){
            dInitiallySelectedIcon=icon;
            icon.setState(true);
        }
    }
  }

  public DrawingIcon getInitiallySelectedDrawingIcon () {
    return dInitiallySelectedIcon;
  }
    

  public void addDrawingIconSelectionListener
    (DrawingIconSelectionEventListener aDISE) {
        dDrawingIconSelectionListeners.add(aDISE);
  }

  public void removeDrawingIconSelectionListener
      (DrawingIconSelectionEventListener aDISE) {
          dDrawingIconSelectionListeners.remove(aDISE);
  }

  public void actionPerformed (ActionEvent ae) {
    /** Figure out which button went down,
	pop all others up, 
	generate a DrawingIconSelectionEvent, and
	send to all DrawingIconSelectionEventListeners
    */
      if(ae.getSource() instanceof DrawingIcon){
        DrawingIcon ic= (DrawingIcon) ae.getSource();
        updateStateOfDrawingIcons(ic);
        messageDrawingIconSelectionListeners(ic);
      }
  }
  
  private void updateStateOfDrawingIcons(DrawingIcon aSelectedIcon){
      aSelectedIcon.setState(true);
      Enumeration ics=dPalette.getAllDrawingIcons();
      while(ics.hasMoreElements()){
          DrawingIcon ic=(DrawingIcon) ics.nextElement();
          if(!ic.equals(aSelectedIcon)){
              ic.setState(false);
          }
      }
  }
  
  private void messageDrawingIconSelectionListeners(DrawingIcon aSelectedIcon){ 
      Enumeration listeners=dDrawingIconSelectionListeners.elements();
      while(listeners.hasMoreElements()){
          DrawingIconSelectionEventListener listener=
                    (DrawingIconSelectionEventListener) listeners.nextElement();
          listener.iconSelected(new DrawingIconSelectionEvent(aSelectedIcon));
      }
  }
}

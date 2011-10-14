
import java.awt.*;
import java.awt.event.*;

public class Whiteboard {

  private WhiteboardFrame dWhiteboardFrame;
  private PaletteController dPaletteController;
  private ShapeController dShapeController;

  public Whiteboard (WhiteboardFrame aWhiteboardFrame) {
    dWhiteboardFrame=aWhiteboardFrame;
    dPaletteController=
            new PaletteController(dWhiteboardFrame.getPalette());
    dShapeController=new ShapeController
            (dWhiteboardFrame,dWhiteboardFrame.getShapeContainer(),dPaletteController);
  }

  public static void main (String argv[]) {

    WhiteboardFrame aWhiteboardFrame = new WhiteboardFrame ("My Whiteboard");
    Whiteboard wb = new Whiteboard(aWhiteboardFrame);

    aWhiteboardFrame.setSize (640,480);
    aWhiteboardFrame.setLocation (100,100);
    aWhiteboardFrame.setVisible (true);
    aWhiteboardFrame.pack();
    aWhiteboardFrame.show();
  }

}


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.FileDialog;
import java.lang.NullPointerException;
import java.io.*;

public class ShapeController 
  implements DrawingIconSelectionEventListener,
	     MouseListener,
	     MouseMotionListener,
	     ActionListener
					
{

  private Container dShapeContainer;

  private Clipboard dClipboard;

  private static final short DRAW_MODE = 0;
  private static final short SELECT_MODE = 1;
  private static final short MOVE_MODE=2;

  private short dMode = DRAW_MODE;

  private DrawingIcon dCurrentSelectedIcon;

  private SelectedVector dSelectedShapes;

  private WhiteboardFrame dWhiteboardFrame;
  
  private MenuItem dMenuItemCut, dMenuItemPaste,
            dMenuItemClear, dMenuItemCopy, dMenuItemSaveAs;
  // change to Points
  private int downX = -1, downY = -1, upX = -1, upY = -1;

  public ShapeController (WhiteboardFrame aWhiteboardFrame,
			  Container aShapeContainer,
			  PaletteController aPaletteController) {
      dWhiteboardFrame=aWhiteboardFrame;
      dShapeContainer=aShapeContainer;
      dSelectedShapes=new SelectedVector();
      dClipboard=Clipboard.getInstance();
      
      //set the current select icon to the initially selected icon in Palette
      dCurrentSelectedIcon=aPaletteController.getInitiallySelectedDrawingIcon();
      
      //regist as a mouse listener of the ShapeContainer
      dShapeContainer.addMouseListener(this);
      dShapeContainer.addMouseMotionListener(this);
      
      //regist as a listener of Palette
      aPaletteController.addDrawingIconSelectionListener(this);
      
      //regist as a listener of all menu items
      MenuItem [] mi=dWhiteboardFrame.getAllMenuItems();
      for(int i=0; i<mi.length;i++){
          mi[i].addActionListener(this);
          
      }
      setMenuItems();
      updateEditMenu();
      dMenuItemSaveAs.setEnabled(false);
  }

  /* Drawing Icon Selection Event */

  public void iconSelected (DrawingIconSelectionEvent aDISE) {
      dCurrentSelectedIcon=aDISE.getIcon();
      dMode=DRAW_MODE;
      if((dCurrentSelectedIcon.getCommand()).equals("Select"))
          dMode=SELECT_MODE;
      else if((dCurrentSelectedIcon.getCommand()).equals("Moving"))
          dMode=MOVE_MODE;
  }

  /* Mouse Events */

  public void mouseClicked (MouseEvent me) {
      
      /* In Select Mode, click the left mouse button  on a shape to select the shape, 
         and click the right mouse button on a selected shape to deselect it.
       */
      if((dMode==SELECT_MODE)
      &&(me.getSource() instanceof Shape)){
          Shape source=(Shape) me.getSource();
          int button=me.getModifiers();
          boolean select=source.getSelectState();
          if((!select)&&(button==MouseEvent.BUTTON1_MASK)){
            source.setSelectState(true);
            dSelectedShapes.add(source);
            source.repaint();
          }
          else if(select && (button==MouseEvent.BUTTON1_MASK)){
              source.setSelectState(false);
              dSelectedShapes.remove(source);
              source.repaint();
          }
          updateEditMenu();
      }
          
  }

  public void mouseEntered (MouseEvent me) {
    // null
  }

  public void mouseExited (MouseEvent me) {
    // null
  }
  
  public void mousePressed (MouseEvent me) {
      if(me.getModifiers()==MouseEvent.BUTTON1_MASK){
          switch(dMode){
              case DRAW_MODE:
                  downX=me.getX();
                  downY=me.getY();
                  break;
              case MOVE_MODE:
                  lastX=me.getX();
                  lastY=me.getY();
          }
      }
      else if(me.getModifiers()==MouseEvent.BUTTON3_MASK){
          switch(dMode){     
              case MOVE_MODE:
                  lastX=me.getX();
                  lastY=me.getY();
          }
      }
  }

  public void mouseReleased (MouseEvent me) {
      if(dMode==DRAW_MODE &&(me.getModifiers()==MouseEvent.BUTTON1_MASK)){
       lastShape=null;
      }
  }

  /* Mouse Motion Events */

  private Shape lastShape = null;
  private int lastX = -1, lastY = -1;

  public void mouseDragged (MouseEvent me) {
      if(dMode==DRAW_MODE && (me.getModifiers()==MouseEvent.BUTTON1_MASK))
          redrawShape(me);    
      else if(dMode==MOVE_MODE)                        
          moveShapes(me);                               
  }

  public void mouseMoved (MouseEvent me) {
    // null
  }

  /* Action Listener (Menu Events) */

  public void actionPerformed (ActionEvent ae) {
    Object ob=ae.getSource();
    if(ob instanceof MenuItem){
        MenuItem mi=(MenuItem) ob;
        String label =mi.getLabel();
        
        //Dispatch the menu actions
        if(label.equals("New")){
            newContainer();
        }
        else if(label.equals("Import")){
            importShapes();
        }
        else if(label.equals("Save As")){
            saveAs();
        }else if(label.equals("Exit")){
            quit();
        }
        else if(label.equals("Cut")){
                cutSelectedShapes();
        }
        else if(label.equals("Copy")){
                copySelectedShapesToClipboard();
        }
        else if(label.equals("Paste")){
                pasteShapesToContainer();
        }
        else if(label.equals("Clear")){
                clearSelectedShapes();
        }
        else if(label.equals("About")){
            popupAboutDialog();
        }
        else if(label.equals("Move")){
            dMode=MOVE_MODE;
        }
        updateEditMenu();
    }
  }
  
  private void newContainer(){
      dMenuItemSaveAs.setEnabled(false);
      dShapeContainer.removeAll();
      dShapeContainer.repaint();
      dSelectedShapes.removeAllElements();
  }
  
  private void popupAboutDialog(){
       String title="About Whiteboard",
            message="Whiteboard version 0.1 \n By Yimin Li \n 7/30/2001";
       boolean isModal=false;
       MyMessageBox mb=new MyMessageBox(dWhiteboardFrame,title,message,isModal);
 //      mb.setSize(300,200);
       return;     
      
  }
  
  private void importShapes(){
      //Get the import file name using FileDialog
      FileDialog fd=new FileDialog(dWhiteboardFrame,"Open",FileDialog.LOAD);
      fd.setModal(true);
      fd.show();
      String fileName=null;
      if(fd.getDirectory()==null && fd.getFile()==null) return;

      fileName=fd.getDirectory()+fd.getFile();
      Component [] shapes=null;
      ObjectReader reader=null;
      try{
          reader=ObjectReader.openFileForReading(fileName);
          shapes=(Component[]) reader.readObject();
      }catch(NullPointerException nPE){
          String title="Warning",
                   message="Can not open file for reading!";
          boolean isModal=true;
          new MyMessageBox(dWhiteboardFrame,title,message,isModal);
          return;     
      }      
      
     
      try{
           Shape s=null;
            for(int i=0;i<shapes.length;i++){
              s=(Shape) shapes[i];
              s.addMouseListener(this);
              s.addMouseMotionListener(this);
              dShapeContainer.add(s);
              dShapeContainer.repaint();
            }
      }catch(NullPointerException eNP){}
      finally{
              reader.close();
              dMenuItemSaveAs.setEnabled(true);
      }

  }
  
  private void saveAs(){
      if(dMenuItemSaveAs.isEnabled()){
          //get the save file name 
          FileDialog fd=new FileDialog(dWhiteboardFrame,"Save as",FileDialog.SAVE);
          fd.setModal(true);
          fd.show();
          String fileName=null;
          if(fd.getDirectory()==null) return;
	  if(fd.getFile()==null) fileName=null;
	  else
	    fileName=fd.getDirectory()+fd.getFile();

          Component[] shapes=dShapeContainer.getComponents();
          //remove shape controller from the shap listener list
          try{
             for(int i=0; i<shapes.length;i++){
                shapes[i].removeMouseListener(this);
                shapes[i].removeMouseMotionListener(this);
             }
          }catch(NullPointerException e){}
          
          
          //open a file to write
   
          try{
	    ObjectWriter writer=ObjectWriter.openFileForWriting(fileName);        
             writer.writeObject(shapes);
             dMenuItemSaveAs.setEnabled(false);
             writer.close();
          }catch(NullPointerException eP){
             String title="Warning",
                   message="Can not open file for writing!";
             boolean isModal=true;
             new MyMessageBox(dWhiteboardFrame,title,message,isModal);
             return;
          }
      }
  }
  
  private void quit(){
      if(dMenuItemSaveAs.isEnabled()){
          saveAs();
      }
      dWhiteboardFrame.setVisible(false);
      System.exit(0);
  }
  
  private void cutSelectedShapes(){
      removeSelectedShapesFromContainer();
      copySelectedShapesToClipboard();
      dSelectedShapes.removeAllElements();
  }
  
  private void copySelectedShapesToClipboard(){
      Clipboard cb=Clipboard.getInstance();
      cb.setShapes(dSelectedShapes);
  }
  
  private void pasteShapesToContainer(){
     Clipboard cb=Clipboard.getInstance();
     Enumeration es=cb.getShapes().elements();
     Shape s=null;
     while(es.hasMoreElements()){
        s=(Shape) es.nextElement();
        s.addMouseListener(this);
        s.addMouseMotionListener(this);
        dShapeContainer.add(s);
     }
     dShapeContainer.repaint();
     dMenuItemSaveAs.setEnabled(true);
  }
  
  private void clearSelectedShapes(){
       removeSelectedShapesFromContainer();
       //clear the selectedVector
       dSelectedShapes.removeAllElements(); 
  }
  
  private void removeSelectedShapesFromContainer(){
      //remove selected shapes from ShapeContainer and repaint
      Enumeration es=dSelectedShapes.elements();
      Shape s=null;
      while(es.hasMoreElements()){
          s=(Shape) es.nextElement();
          s.removeMouseListener(this);
          s.removeMouseMotionListener(this);
          dShapeContainer.remove(s);
      }
      dShapeContainer.repaint();
      //The ShapeContainer is changed
      dMenuItemSaveAs.setEnabled(true);
  }
  
  private void updateEditMenu(){
      boolean b=!(dSelectedShapes.size()==0);
      dMenuItemCut.setEnabled(b);
      dMenuItemCopy.setEnabled(b);
      dMenuItemClear.setEnabled(b);
      b=!(dClipboard.numberOfShapes()==0);
      dMenuItemPaste.setEnabled(b);
      b=!(dShapeContainer.getComponentCount()==0);
  }
  
  private void setMenuItems(){
      //Initially disable the Clear and Paste menue item
       MenuItem [] mi=dWhiteboardFrame.getAllMenuItems();
       for(int i=0; i<mi.length;i++){         
          String label=mi[i].getLabel();
          if(label.equals("Clear")){ 
              dMenuItemClear=mi[i];
          }
          else if(label.equals("Paste")){
              dMenuItemPaste=mi[i];
          }
          else if(label.equals("Cut")){
              dMenuItemCut=mi[i];
          }
          else if(label.equals("Copy")){
              dMenuItemCopy=mi[i];
          }
          else if(label.equals("Save As")){
              dMenuItemSaveAs=mi[i];
          }
       }
  }
  
  private void redrawShape(MouseEvent me){
      dMenuItemSaveAs.setEnabled(true);
          if (lastShape==null){
            lastShape=dCurrentSelectedIcon.createShape
                        (downX,downY,me.getX(),me.getY());
            
            dShapeContainer.add(lastShape);
            lastShape.addMouseListener(this);
            lastShape.addMouseMotionListener(this);
          }
          else{
              lastShape.setCoordinates(downX,downY,me.getX(),me.getY());
          }
          lastShape.repaint(); 
  }
  
  private void moveShapes(MouseEvent me){
      //Check if this mouse clicked on a Shape object
      Object ob=me.getSource();
      if(!(ob instanceof Shape)) return;
      //get the translation of the mouse cursor
      int dx=me.getX()-lastX, dy=me.getY()-lastY;
      
      int modifier=me.getModifiers();
      Shape s=(Shape) ob;
      switch(modifier){
          //Using the left mouse button to move a single shape  
          case MouseEvent.BUTTON1_MASK:                              
              s.moveBy(dx, dy);              
              break;             
          //Using the right mouse button to move all the selected shapes
          case MouseEvent.BUTTON3_MASK:
              if(s.getSelectState()) moveSelectedShapes(dx, dy);         
      }
      lastX=me.getX();
      lastY=me.getY();
  }
  
  private void moveSelectedShapes(int dx, int dy){              
      Enumeration se=dSelectedShapes.elements();
      Shape s= null;
      while(se.hasMoreElements()){     
          s=(Shape) se.nextElement();          
          s.moveBy(dx,dy);         
      }
  }
  
}

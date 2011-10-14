
import java.awt.*;

public class WhiteboardFrame extends Frame {
        public static String [] MENU_TEXT={"File","Edit","Help"};
        public static String [][] MENU_ITEM_TEXT={
        {"New","Import","Save As","Exit"},
        {"Cut","Copy","Paste","Clear"},
        {"About"}};
        
        private static Color dBColor= new Color(200,180,200);
        private static Font dMenuFont=new Font("Courier New",Font.PLAIN,16);
  private MenuBar dMenuBar;
  private Palette dPalette;
  private Container dShapeContainer;
  private MenuItem [] dMenuItems;

  private void createMenuBar () {

    dMenuBar = new MenuBar ();
    setMenuBar (dMenuBar);

    // add menu items here       
        
    int menuItemCount=createMenu(MENU_TEXT, MENU_ITEM_TEXT);
    setAllMenuItems(menuItemCount);
        
    WhiteboardWindowAdapter adapter = new WhiteboardWindowAdapter (this);
    addWindowListener (adapter);

  }
  
  private int createMenu(String[] menuText,String[][] menuItemText){
      int itemCount=0;
      int menuCount=menuText.length;
      MenuItem mi=null;
      for(int i=0;i<menuCount;i++){
          Menu m=new Menu(menuText[i]);
	  m.setFont(dMenuFont);
          dMenuBar.add(m);
          String[] mit=menuItemText[i];
          for(int j=0;j<mit.length;j++){
              mi=new MenuItem(mit[j]);
              mi.setFont(dMenuFont);
              m.add(mi);
              itemCount++;
          }
      }
      return itemCount;
  }
  
  private void setAllMenuItems(int itemCount){
      dMenuItems=new MenuItem[itemCount];
      int count=0;
      for(int i=0; i<dMenuBar.getMenuCount();i++){
          Menu m=dMenuBar.getMenu(i);
          for(int j=0;j<m.getItemCount();j++){
              dMenuItems[count]=m.getItem(j);
          count++;
          }
      }
  }          

  public MenuItem [] getAllMenuItems () {
    return dMenuItems;
  }

  private void createPalette () {
    dPalette = new Palette ();
  }


  public Palette getPalette () {
    return dPalette;
  }

  private void createShapeContainer () {
    dShapeContainer = new ShapeContainer ();
  }

  public Container getShapeContainer () {
    return dShapeContainer;
  }
  
  
  public WhiteboardFrame (String title) {
    super (title);

    createMenuBar();
    createPalette();
    createShapeContainer();

    setLayout (new BorderLayout());
    add (dPalette, BorderLayout.SOUTH);
    add (dShapeContainer, BorderLayout.CENTER);
	
    setResizable (true);
   // setBackground (Color.lightGray);
    setBackground (dBColor);    
  }
}

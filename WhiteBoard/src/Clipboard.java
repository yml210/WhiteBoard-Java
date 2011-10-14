
import java.lang.NullPointerException;
/** The Clipboard can store an object using the set method and retrieve it
    if the caller calls the get method.  There is only one Clipboard for
    the entire applciation, and a caller may use the getInstance method
    to get the clipboard. */

public class Clipboard {

  private SelectedVector dShapes;
  private static Clipboard dClipboard=new Clipboard();

  private Clipboard () {
    dShapes=null;
  }

  public static Clipboard getInstance () {
    return dClipboard;
  }

  public void setShapes (SelectedVector s) {
      try{
        dShapes=(SelectedVector) s.clone();
      }
      catch(NullPointerException eNP){
          return;
      }
  }

  public SelectedVector getShapes () {
      try{
          return (SelectedVector) dShapes.clone();
      }
      catch(NullPointerException eNP){
          return new SelectedVector();
      }
  }

  public int numberOfShapes(){
      try{
        return dShapes.size();
      }
      catch(NullPointerException nPE){
          return 0;
      }
  }
}

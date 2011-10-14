
import java.util.*;

public class SelectedVector implements Cloneable {
  private Vector vector;

  public SelectedVector () {
      vector=new Vector();
  }

  public synchronized void add (Shape s) {
      vector.add(s);
  }

  public int size () {
    return vector.size();
  }

  public synchronized void remove (Shape s) {
      vector.remove(s);
  }

  public Object clone () {
    SelectedVector ret=new SelectedVector();
    Shape shape=null;
    for(int i=0; i< vector.size();i++){
        shape=(Shape) ((Shape)vector.elementAt(i)).clone();
        shape.setSelectState(false);
        ret.add(shape);
    }
    return ret;
  }

  public boolean contains (Shape s) {
    return vector.contains(s);
  }
  
  public Enumeration elements () {
    return vector.elements();
  }

  public synchronized void removeAllElements () {
      vector.removeAllElements();
  }

}

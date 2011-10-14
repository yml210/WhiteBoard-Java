//MyMessageBox.java

/**
 * A simple message box shows a message and an OK button
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class MyMessageBox extends Dialog implements ActionListener{
    public static Font dFont=new Font("Courier New",Font.BOLD,16);
    public MyMessageBox(Frame parent, String title,String message,boolean isModal){
        super(parent,title,isModal);
        //Add an OK button
	Panel p=new Panel();
	Button ok=new Button("OK");
//        ok.setFont(new Font("Serif",Font.BOLD,16));
        ok.setFont(dFont);
        ok.addActionListener(this);
        p.setLayout(new FlowLayout());
	p.add(ok);
        add(BorderLayout.SOUTH, p);
        add(BorderLayout.CENTER,new MyMessageBoard(message));
        
        //Window adapter
        addWindowListener(new MyMessageBoxAdapter(this));

	Point point=parent.getLocation();
        point.translate(100,100);
        setLocation(point);
        pack();
        show();
    }
    
    public void actionPerformed(ActionEvent ae){
            hide();
    }
}

class MyMessageBoard extends Panel{
    public MyMessageBoard(String message){
        StringTokenizer st=new StringTokenizer(message,"\n");
        setLayout(new GridLayout(st.countTokens(),1));
        Label lb=null;
        while(st.hasMoreElements()){
            lb=new Label(st.nextToken());
//            lb.setFont(new Font("Times",Font.BOLD,16));
            lb.setFont(MyMessageBox.dFont);
            lb.setAlignment(Label.CENTER);
            add(lb);
        }
    }
}
    
class MyMessageBoxAdapter extends WindowAdapter{
    private MyMessageBox mb;
    public MyMessageBoxAdapter(MyMessageBox mbox){
        mb=mbox;
    }
    
    public void windowClosing(WindowEvent we){
        mb.hide();
    }
}

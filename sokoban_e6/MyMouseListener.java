import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseListener extends MouseAdapter {
    InterfaceGraphique IG;
    MyMouseListener(InterfaceGraphique IG){
        this.IG = IG; 
    }
    public void mousePressed(MouseEvent e){
        System.out.println(e.getPoint());
    }
}

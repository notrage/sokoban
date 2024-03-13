import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyListener extends KeyAdapter {
    InterfaceGraphique IG;
    MyKeyListener(InterfaceGraphique IG){
        this.IG = IG; 
    }

    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_N) {
            IG.ProchainNiveau();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            IG.toggleFullscreen();
        }
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_Q) {
            IG.quit();
        }
    }
}

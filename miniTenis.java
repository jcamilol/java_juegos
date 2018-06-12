import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.Math;

public class miniTenis extends JPanel{

  private int xBola = 100;
  private int xa = 1;
  private int yBola = 1;
  private int ya = 1;
  private int xRaqueta = 100;
  public int keypress = 0;
  public int puntaje = 0;
  public int xspeed = 1;
  public int yspeed = 1;

  public miniTenis(){
    KeyListener listener = new MyKeyListener();
    addKeyListener(listener);
    setFocusable(true);
  }

  public class MyKeyListener implements KeyListener{
    public void keyTyped(KeyEvent e){
    }
    public void keyPressed(KeyEvent e){
      keypress = e.getKeyCode();
      if (keypress == 65){
        if(xRaqueta > 0)
          xRaqueta = xRaqueta - 20;
      }
      if(keypress == 68){
        if(xRaqueta < 200)
          xRaqueta = xRaqueta + 20;
      }
    }
    public void keyReleased(KeyEvent e){
    }
  }

  public void paint(Graphics g){
    super.paint(g);
    Graphics2D g2d = (Graphics2D)g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(Color .BLUE);
    g2d.fillOval(xBola,yBola,30,30);
    g2d.setColor(Color .RED);
    g2d.fillRect(xRaqueta,350,100,15);
    Font boldFont = new Font("TimesRoman",Font.BOLD,30);
    g2d.setColor(Color .BLACK);
    g2d.setFont(boldFont);
    g2d.drawString(String.valueOf(puntaje),25,50);
  }

  public void moveBola(){
    if(xBola <= 0)
      xa = xspeed;
    if(xBola >= 270)
      xa = -xspeed;

    if(yBola <= 0)
      ya = yspeed;
    if(((Math.abs((xRaqueta+50)-xBola) <= 50) && Math.abs(350-yBola) <= 30) && yBola <= 350 ){
      ya = -yspeed;
      xspeed++;
      yspeed++;
      puntaje++;
    }
    if(yBola > 400){
      JOptionPane.showMessageDialog(null,"\tGAME OVER\nPuntaje = "+puntaje);
      puntaje = 0;
      yBola = 0;
      xBola = 0;
      xa = 1;
      ya = 1;
      xspeed = 1;
      yspeed = 1;
    }

    xBola += xa;
    yBola += ya;
  }

  public static void main(String[] args){
    JFrame ventana = new JFrame("Mini Tenis");
    ventana.setSize(300,400);
    ventana.setVisible(true);
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    miniTenis juego1 = new miniTenis();
    ventana.add(juego1);
    while(true){
      juego1.moveBola();
      juego1.repaint();
      try{
        Thread.sleep(10);
      } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
  }
}

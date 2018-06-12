import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.Math;
import java.util.*;

public class Chess extends JPanel{

  private int T[][] = new int[][]{ //Declaramos e inicializamos una matriz de enteros de 8x8 que representará nuestro tablero, y donde cada elemento de la matriz representará una casilla. Los números positivos representan las piezas blancas, los negativos las negras y los ceros el lugar donde no hay piezas. 1 = peón, 5 = torre, 4 = alfil, 3 = caballo, 9 = dama, 7 = rey.
    {-5,-3,-4,-9,-7,-4,-3,-5},
    {-1,-1,-1,-1,-1,-1,-1,-1},
    {0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0},
    {1, 1, 1, 1, 1, 1, 1, 1},
    {5, 4, 3, 9, 7, 4, 3, 5},
  };

  private int xselecter1 = 0; //Creamos los elementos del cuadro seleccionador
  private int yselecter1 = 560;

  private int filaIn; //Creamos las variables de fila y columna de entrada
  private int columnaIn;
  private int filaOut; //Creamos las variables de fila y columna de entrada
  private int columnaOut;

  private boolean Turno = true; // Si Turno es true juegan blancas, si es false juegan negras
  private boolean jugadaLegal = true; //¿La jugada es legal?

  public int keypress = 0;

  public Chess(){
    KeyListener listener = new MyKeyListener();
    addKeyListener(listener);
    setFocusable(true);
  }

  public void Movida(){

    Scanner s1 = new Scanner(System.in);
    jugadaLegal = true;

    if(Turno == true){
      System.out.println("///////////////");
      System.out.println("JUEGAN BLANCAS");
      System.out.println("///////////////");
    }
    else if(Turno == false){
      System.out.println("///////////////");
      System.out.println("JUEGAN NEGRAS");
      System.out.println("///////////////");
    }
    Turno = !Turno;

    System.out.println("Desde:"); //Se toma la jugada del usuario; desde qué casilla y hacia qué casilla va a mover
    int columnaIn = s1.nextInt();
    int filaIn = s1.nextInt();
    System.out.println("Hacia:");
    int columnaOut = s1.nextInt();
    int filaOut = s1.nextInt();

    //Reglas del ajedrez que determinan si la jugada ingresada es legal o ilegal
    if( (T[filaIn][columnaIn] > 0 && T[filaOut][columnaOut] > 0) || (T[filaIn][columnaIn] < 0 && T[filaOut][columnaOut] < 0) ){ //Se captura una pieza del mismo color
      jugadaLegal = false;
    }
    if( filaIn == filaOut && columnaIn == columnaOut ){ //Se mueve una pieza a la casilla en la que está
      jugadaLegal = false;
    }

    if(jugadaLegal == true){ //Si la jugada es legal, se ejecuta
      T[filaOut][columnaOut] = T[filaIn][columnaIn];
      T[filaIn][columnaIn] = 0;
    }
    else{ //Si la jugada es ilegal, se pide reingresarla
      JOptionPane.showMessageDialog(null, "¡JUGADA ILEGAL!, vuelva a intentar");
      Turno = !Turno;
    }
  }

  public class MyKeyListener implements KeyListener{
    public void keyTyped(KeyEvent e){
    }
    public void keyPressed(KeyEvent e){ //En este apartado definimos lo que pasa si se presionan ciertas teclas
      /*keypress = e.getKeyCode();
      if(keypress == 65 && xselecter1 > 0){
        xselecter1 -= 80;
      }
      if(keypress == 68 && xselecter1 < 560){
        xselecter1 += 80;
      }
      if(keypress == 83 && yselecter1 < 560){
        yselecter1 += 80;
      }
      if(keypress == 87 && yselecter1 > 0){
        yselecter1 -= 80;
      }*/
    }
    public void keyReleased(KeyEvent e){
    }
  }

  public void paint(Graphics g){

    Graphics2D g2d = (Graphics2D)g;
    super.paint(g);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int temp1 = 0;
    Color CLARAS = new Color(236, 191, 124); //Color de la casillas claras
    Color OSCURAS = new Color(176, 101, 24); //Color de las casillas oscuras

    for(int jj = 0; jj < 640; jj += 80){ //Se imprimen la cuadrícula del tablero alternando el color verde y el color azul
      for(int ii = 0; ii < 640; ii += 80){
        if(temp1%2 != 0)
          g2d.setColor(CLARAS);
        if(temp1%2 == 0)
          g2d.setColor(OSCURAS);
        g2d.fillRect(ii,jj,80,80);
        temp1++;
      }
      temp1++;
    }

    /*g2d.setColor(Color .RED); //Se imprime nuestro cuadro seleccionador en una posición variable
    g2d.fillRect(xselecter1, yselecter1, 80,80);*/

    Font boldFont = new Font("TimesRoman",Font.BOLD,70);
    g2d.setFont(boldFont);

    String pieza = " "; //Variable donde guardaremos las piezas que se imprimen en el tablero
    Color BLANCO = new Color(255, 255, 255); //El color de las piezas blancas
    Color NEGRO = new Color(0, 0, 0); //El color de las piezas negras

    //Se ejecutan ciertas operaciones con los elemento de T[][]
    for(int ii=0; ii < 8; ii++){
      for(int jj=0; jj < 8; jj++){

        //En este apartado se mira que tipo de pieza está en cada elemento de la matriz y se le relaciona una letra para imprimirla en la pantalla
        if(Math.abs(T[ii][jj]) == 1)
          pieza = "P";
        else if (Math.abs(T[ii][jj]) == 5)
          pieza = "T";
        else if (Math.abs(T[ii][jj]) == 4)
          pieza = "A";
        else if (Math.abs(T[ii][jj]) == 3)
          pieza = "C";
        else if (Math.abs(T[ii][jj]) == 9)
          pieza = "D";
        else if (Math.abs(T[ii][jj]) == 7)
          pieza = "R";
        else if (Math.abs(T[ii][jj]) == 0)
          pieza = " ";

        //Ahora se mira si la pieza es de blancas o de negras dependiendo del signo del entero en la matriz, y se le asigna el color correspondiente a la letra
        if(T[ii][jj] > 0)
          g2d.setColor(BLANCO);
        else
          g2d.setColor(NEGRO);

        g2d.drawString(pieza,(80*(jj))+11,(80*(ii+1)-13)); //Se dibuja la letra de cada elemento de la matriz
      }
    }
  }

  public static void main(String[] args){ //El método main del programa

    JFrame ventana = new JFrame("Ajedrez");
    ventana.setSize(640,640);
    ventana.setVisible(true);
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Chess chess1 = new Chess();
    ventana.add(chess1);
    while(true){
      chess1.Movida();
      chess1.repaint();
      try{
        Thread.sleep(10);
      } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
   }
}

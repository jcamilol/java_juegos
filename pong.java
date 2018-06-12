/*Pong programado en java*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.Math;

public class pong extends JPanel{

  int ancho_ventana = 700;
  int alto_ventana = 500;
  int raqueta_1_pos[] = new int[2];
  int raqueta_2_pos[] = new int[2];
  int bola_pos[] = new int[2];
  int vel_bola[] = new int[2];
  int modo_Juego[] = new int[1];
  boolean modo_Juego_elegido[] = new boolean[1];
  int seleccionador[] = new int[1];
  int cursor_pos_1[] = new int[1];
  int cursor_pos_2[] = new int[1];
  int keypress = ' ';
  char orientacion_bola[] = new char[2];
  int puntaje_1[] = new int[1];
  int puntaje_2[] = new int[1];
  Color NEGRO = new Color(0, 0, 0);
  Color BLANCO = new Color(255, 255, 255);
  Color GRIS = new Color(133, 136, 128);

  int ii=0;

  void inicializar_arreglos(){
    raqueta_1_pos[0] = 20;
    raqueta_1_pos[1] = 200;
    raqueta_2_pos[0] = ancho_ventana-20-15;
    raqueta_2_pos[1] = 100;
    bola_pos[0] = 300;
    bola_pos[1] = 300;
    orientacion_bola[0] = 'd';
    orientacion_bola[1] = 's';
    vel_bola[0] = 3;
    vel_bola[1] = 3;
    modo_Juego[0] = 1;
    modo_Juego_elegido[0] = false;
    seleccionador[0] = 300;
    cursor_pos_1[0] = 300;
    cursor_pos_2[0] = 350;
    puntaje_1[0] = 0;
    puntaje_2[0] = 0;
  }

  void inicializar_bola(){
    bola_pos[0] = 300;
    bola_pos[1] = 300;
  }

  void imprimir_fondo_1(Graphics2D g2d){ //Función que imprime el fondo básico (todo negro)
    g2d.setColor(NEGRO);
    for(int ii = 0; ii<=ancho_ventana; ii++){
      g2d.drawLine(ii, 0, ii, alto_ventana);
    }
  }

  void imprimir_raqueta(Graphics2D g2d, int raqueta_pos[]){
    g2d.setColor(BLANCO);
    g2d.fillRect(raqueta_pos[0], raqueta_pos[1], 15, 100);
  }

  void imprimir_linea_1(Graphics2D g2d){
    g2d.setColor(GRIS);
    for(int ii=4; ii<=alto_ventana-10; ii+=20){
      g2d.fillRect(ancho_ventana/2-10, ii, 10, 10);
    }
  }

  void imprimir_bola(Graphics2D g2d, int bola_pos[]){
    g2d.setColor(BLANCO);
    g2d.fillRect(bola_pos[0], bola_pos[1], 12, 12);
  }

  boolean rebote_bola_raqueta(int bola_pos[], int raqueta_pos[]){
    if ( ( ((bola_pos[1]<raqueta_pos[1])&&(Math.abs(bola_pos[1]-raqueta_pos[1])<=12)) || ((bola_pos[1]>raqueta_pos[1])&&(Math.abs(bola_pos[1]-raqueta_pos[1])<=100)) ) && (Math.abs(bola_pos[0]-raqueta_pos[0])<=12) ){
      return true;
    }
    return false;
  }

  boolean rebote_raqueta_bola(int bola_pos[], int raqueta_pos[], int No_raqueta){
    if( ((No_raqueta == 2) && (bola_pos[1]<raqueta_2_pos[1]) && (Math.abs(bola_pos[1]-raqueta_2_pos[1])<=12) && (bola_pos[0]>ancho_ventana-20-12)) || ((No_raqueta==2) && (bola_pos[1]>raqueta_2_pos[1]+100) && (Math.abs(bola_pos[1]-raqueta_2_pos[1])<=12) && (bola_pos[0]>ancho_ventana-20-12)) ){
      return true;
    }
    if( ((No_raqueta == 1) && (bola_pos[1]<raqueta_1_pos[1]) && (Math.abs(bola_pos[1]-raqueta_1_pos[1])<=12) && (bola_pos[0]<20+12)) || ((No_raqueta==1) && (bola_pos[1]>raqueta_1_pos[1]+100) && (Math.abs(bola_pos[1]-raqueta_1_pos[1])<=12) && (bola_pos[0]<20+12)) ){
      return true;
    }
    return false;
  }

  boolean bola_sale_derecha(int bola_pos[]){
    if(bola_pos[0]>=ancho_ventana){
      return true;
    }
    return false;
  }

  boolean bola_sale_izquierda(int bola_pos[]){
    if(bola_pos[0]+12<=0){
      return true;
    }
    return false;
  }

  void get_move_bola(int bola_pos[], char orientacion_bola[]){

    if( rebote_bola_raqueta(bola_pos, raqueta_2_pos) ){ //Condiciones para que la bola rebote
      orientacion_bola[0] = 'a';
    }
    if( rebote_bola_raqueta(bola_pos, raqueta_1_pos) ){
      orientacion_bola[0] = 'd';
    }
    if(bola_sale_derecha(bola_pos)){
      puntaje_1[0]++;
      inicializar_bola();
    }
    if(bola_sale_izquierda(bola_pos)){
      puntaje_2[0]++;
      inicializar_bola();
    }
    if(bola_pos[1]>alto_ventana-12){
      orientacion_bola[1] = 'w';
    }
    if(bola_pos[1]<0){
      orientacion_bola[1] = 's';
    }
    if(rebote_raqueta_bola(bola_pos, raqueta_1_pos,1) || rebote_raqueta_bola(bola_pos, raqueta_2_pos,2)){
      if(orientacion_bola[1] == 'w'){
        orientacion_bola[1] = 's';
      }
      else{
        orientacion_bola[1] = 'w';
      }
    }

    switch(orientacion_bola[0]){ //Se mueve la bola dependiendo de las orientaciones que lleve
      case 'd':{
        bola_pos[0]+=vel_bola[0];
        break;
      }
      case 'a':{
        bola_pos[0]-=vel_bola[0];
        break;
      }
    }
    switch(orientacion_bola[1]){
      case 's':{
        bola_pos[1]+=vel_bola[1];
        break;
      }
      case 'w':{
        bola_pos[1]-=vel_bola[1];
        break;
      }
    }
  }

  void get_move_raqueta_2(int raqueta_2_pos[]){
    if(bola_pos[1]>raqueta_2_pos[1]){
      if(raqueta_1_pos[1]<=alto_ventana-110){
        raqueta_2_pos[1]+=2;
      }
    }
    else if(bola_pos[1]<raqueta_2_pos[1]){
      if(raqueta_2_pos[1]>=10){
        raqueta_2_pos[1]-=2;
      }
    }
  }

  public pong(){
    KeyListener listener = new MyKeyListener();
    addKeyListener(listener);
    setFocusable(true);
  }

  public class MyKeyListener implements KeyListener{
    public void keyTyped(KeyEvent e){
    }
    public void keyPressed(KeyEvent e){
      keypress = e.getKeyCode();
      if(keypress == (int)'W'){
        if(raqueta_1_pos[1]>=10){
          raqueta_1_pos[1]-=10;
        }
      }
      if(keypress == (int)'S'){
        if(raqueta_1_pos[1]<=alto_ventana-110){
          raqueta_1_pos[1]+=10;
        }
      }
      if(!modo_Juego_elegido[0]){
        if(keypress == (int)'X' && seleccionador[0]==cursor_pos_1[0]){
          modo_Juego[0] = 1;
          modo_Juego_elegido[0] = true;
        }
        if(keypress == (int)'X' && seleccionador[0]==cursor_pos_2[0]){
          modo_Juego[0] = 2;
          modo_Juego_elegido[0] = true;
        }
        if(seleccionador[0] == cursor_pos_1[0] && keypress == (int)'S'){
          seleccionador[0] = cursor_pos_2[0];
        }
        if(seleccionador[0] == cursor_pos_2[0] && keypress == (int)'W'){
          seleccionador[0] = cursor_pos_1[0];
        }
      }
      if(modo_Juego[0]==2){
        if(keypress == (int)'O'){
          if(raqueta_2_pos[1]>=10){
            raqueta_2_pos[1]-=10;
          }
        }
        if(keypress == (int)'L'){
          if(raqueta_2_pos[1]<=alto_ventana-110){
            raqueta_2_pos[1]+=10;
          }
        }
      }
    }
    public void keyReleased(KeyEvent e){
    }
  }

  void imprimir_menu(Graphics2D g2d){
    if (modo_Juego_elegido[0]==false) {
      g2d.setColor(BLANCO);
      // Font boldFont = new Font("Serif",Font.BOLD,30);
      // g2d.setFont(boldFont);
      g2d.setFont(new Font("Purisa", Font.BOLD, 100));
      g2d.drawString("PONG", ancho_ventana/2-150, 200);
      g2d.setFont(new Font("Roboto", Font.BOLD, 20));
      g2d.drawString("Jugar  VS  PC", ancho_ventana/2-50, cursor_pos_1[0]);
      g2d.drawString("Jugar  VS  Jugador 2", ancho_ventana/2-80, cursor_pos_2[0]);
      g2d.fillRect(ancho_ventana/2-115, seleccionador[0]-5, 20, 5);
    }
  }

  void imprimir_puntaje(Graphics2D g2d, int No_puntaje){
    g2d.setColor(GRIS);
    g2d.setFont(new Font("Inconsolata", Font.BOLD, 40));
    if(No_puntaje == 1){
      if(puntaje_1[0]<10){
        g2d.drawString(String.valueOf(puntaje_1[0]), ancho_ventana/2-50, 50);
      }
      else{
        g2d.drawString(String.valueOf(puntaje_1[0]), ancho_ventana/2-50-25, 50);
      }
    }
    if(No_puntaje == 2){
      g2d.drawString(String.valueOf(puntaje_2[0]), ancho_ventana/2+10, 50);
    }
  }

  public void paint (Graphics g){ //Función paint principal
    super.paint(g);
    Graphics2D g2d = (Graphics2D)g;
    imprimir_fondo_1(g2d);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    if(!modo_Juego_elegido[0]){
      imprimir_menu(g2d);
    }
    else{
      // imprimir_fondo_1(g2d);
      imprimir_raqueta(g2d, raqueta_1_pos);
      imprimir_raqueta(g2d, raqueta_2_pos);
      imprimir_linea_1(g2d);
      imprimir_bola(g2d, bola_pos);
      imprimir_puntaje(g2d, 1);
      imprimir_puntaje(g2d, 2);
    }
  }

  void crear_ventana(pong juego1){
    JFrame ventana = new JFrame("Pong");
    ventana.setSize(juego1.ancho_ventana+10,juego1.alto_ventana+30);
    ventana.setVisible(true);
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ventana.setResizable(true);
    ventana.add(juego1);
  }

  public static void main(String[] args) { //Función main
    pong juego1 = new pong();
    juego1.crear_ventana(juego1);
    juego1.inicializar_arreglos();
    while(true){
      juego1.get_move_bola(juego1.bola_pos, juego1.orientacion_bola); //Se mueve la bola
      if(juego1.modo_Juego[0]==1){
        juego1.get_move_raqueta_2(juego1.raqueta_2_pos); // Se mueve la raqueta_2
      }
      juego1.repaint(); //Se repinta la ventana
      try{
        Thread.sleep(10); //Se interrumpe un instante la ejecución del programa
      } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
  }
}

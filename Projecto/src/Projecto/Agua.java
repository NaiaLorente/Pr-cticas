package Projecto;


import java.awt.*;
import javax.swing.*;


public class Agua extends ElementoEcosistema {
  private int cantidad;
  private JPanel Panel;
  
  
  //Constructor para crear instancia tipo Agua
  public Agua(String titulo, int x, int y, int anchura, int altura) {
    this.titulo= titulo;
    this.cantidad= (int)Math.sqrt(anchura* altura);
    this.Panel= crearPanel();
    this.Panel.setLocation(x, y);
    this.Panel.setSize(new Dimension(anchura, altura));
    this.Panel.setBackground(Color.blue);
  }
  
  //Crear el panel del Agua
  private JPanel crearPanel() {
    JPanel panel= new JPanel();
    panel.setLayout(new BorderLayout());
    JLabel lblTitulo= new JLabel(this.titulo);
    JLabel lblCantidad= new JLabel(String.valueOf(this.cantidad));
    panel.add(lblTitulo,BorderLayout.NORTH);
    panel.add(lblCantidad, BorderLayout.CENTER);
    panel.add(new JLabel("Agua"), BorderLayout.SOUTH);
    return panel;
  }
  
  public JPanel getPanel() {
    return this.Panel;
  }
  
  public int getCantidad() {
    return this.cantidad;
  }
  
  public void setCantidad(int poblacion) {
    this.cantidad= poblacion;
  }
}


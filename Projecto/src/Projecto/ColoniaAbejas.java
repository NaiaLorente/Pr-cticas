package Projecto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ColoniaAbejas extends ElementoEcosistema implements Evolucionable {
  private int poblacion;
  private JPanel Panel;
  
  //Constructor para crear objeto de tipo Abejas
  public ColoniaAbejas(String titulo, int x, int y, int anchura, int altura) {
    this.titulo= titulo;
    this.poblacion= (int) Math.sqrt(anchura* altura);
    this.Panel= crearPanel();
    this.Panel.setLocation(x, y);
    this.Panel.setSize(new Dimension(anchura, altura));
    this.Panel.setBackground(Color.white);
  }
  //Crear el panel de las Abejas
  private JPanel crearPanel() {
    JPanel panel= new JPanel();
    panel.setLayout(new BorderLayout());
    JLabel lblTitulo= new JLabel(this.titulo);
    JLabel lblPoblacion= new JLabel(String.valueOf(this.poblacion));
    panel.add(lblTitulo,BorderLayout.NORTH);
    panel.add(lblPoblacion,BorderLayout.CENTER);
    panel.add(new JLabel("Abejas"), BorderLayout.SOUTH);
    return panel;
  }
  
  public JPanel getPanel() {
    return this.Panel;
  }
  
  public double getPoblacion() {
    return this.poblacion;
  }
  
  public void setPoblacion(int poblacion) {
    this.poblacion = poblacion;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  @Override
  public void evoluciona(int dias) {
      double factorCrecimiento = 1.0;
      int numFlores = 0;
      for (ElementoEcosistema ee : Ecosistema.getMundo().getElementos()) {
          int dist = Ecosistema.distancia(this, ee);
          if (ee instanceof ColoniaAbejas && ee != this) {
              if (dist < 500) factorCrecimiento = factorCrecimiento * dist / 500;
          } else if (ee instanceof PlantacionFlores) {
              if (dist < 500) factorCrecimiento = factorCrecimiento / dist * 500;
              numFlores += ((PlantacionFlores) ee).getCantidad();
          }
      }
      if (numFlores < 50) factorCrecimiento *= 0.1;
      
      
      poblacion =(int) (poblacion*factorCrecimiento*dias);
      
      
      
      
      if (poblacion > 5000) poblacion = 5000; // Limitar a 5000

      
      //Asignar variable de tipo JLabel al componente numero 2 del panel, que es lblPoblacion
      JLabel lblPoblacion =(JLabel) this.Panel.getComponent(1);
      // Actualizar el JLabel lblPoblacion con el nuevo valor de población
      lblPoblacion.setText(String.valueOf(poblacion));
  }


  
  
  
  
  
  
  
  
  
}
  
  
 

 

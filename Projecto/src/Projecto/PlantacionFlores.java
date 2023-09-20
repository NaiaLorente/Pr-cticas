package Projecto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlantacionFlores extends ElementoEcosistema implements Evolucionable {
  private long cantidad;
  private JPanel Panel;

  
  //Crear instancia tipo PlantacionFlores
  public PlantacionFlores(String titulo, int x, int y, int anchura, int altura) {
	  this.titulo= titulo;
	    this.cantidad= (int) Math.sqrt(anchura* altura);
	    this.Panel= crearPanel();
	    this.Panel.setLocation(x, y);
	    this.Panel.setSize(new Dimension(anchura, altura));
	    this.Panel.setBackground(Color.green);
    

    
    
  }
//Crear Panel de flores
  private JPanel crearPanel() {
	   JPanel panel= new JPanel();
	    panel.setLayout(new BorderLayout());
	    JLabel lblTitulo= new JLabel(this.titulo);
	    JLabel lblPoblacion= new JLabel(String.valueOf(this.cantidad));
	    panel.add(lblTitulo,BorderLayout.NORTH);
	    panel.add(lblPoblacion,BorderLayout.CENTER);
	    panel.add(new JLabel("Abejas"), BorderLayout.SOUTH);
    return panel;
  }

  public JPanel getPanel() {
    return this.Panel;
  }

  public long getCantidad() {
    return this.cantidad;
  }

  public void setCantidad(int poblacion) {
    this.cantidad = poblacion;
  }
  
  public void evoluciona(int dias) {
    double factorCrecimiento= 0.75;
    for (ElementoEcosistema ee : Ecosistema.getMundo().getElementos()) {
      int dist = Ecosistema.distancia(this, ee);
      if (ee instanceof ColoniaAbejas|| (ee instanceof Agua && dist< 500)) {
        factorCrecimiento = factorCrecimiento/ dist* 500.0;
      }
    }
    
    int aumentoCant= (int) (this.cantidad* factorCrecimiento);
    cantidad +=aumentoCant;
    
    if (this.cantidad > 5000)
      this.cantidad = 5000;
    
    
    
    //Obtener segundo elemento de la ventana
    JLabel lCantidad = (JLabel) this.Panel.getComponent(1);
    //Modificar el JLabel de cantidad para que vaya cambiando el número
    lCantidad.setText(String.valueOf(this.cantidad));
  }
 
}

package Projecto;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panel;
    private JToggleButton btnMover;
    private JToggleButton btnCrear;
    private JPanel pnlBotones;
    private JComboBox cbElementos;
    private JButton btnVida;
    
    private Hilo hilo;//Clase privada interna "Hilo"

    public VentanaPrincipal() {
        

        panel= new JPanel();
        pnlBotones=new JPanel();
        btnMover = new JToggleButton("Mover");
        btnCrear = new JToggleButton("Crear");
        btnVida=new JButton("Vida");
        cbElementos=new JComboBox<String>();
        
        cbElementos.addItem("Abejas");
        cbElementos.addItem("Flores");
        cbElementos.addItem("Agua");
        
        
        panel.setLayout(null);//Si quito esta linea no me funciona?
        pnlBotones.add(btnMover);
        pnlBotones.add(btnCrear);
        pnlBotones.add(cbElementos);
        pnlBotones.add(btnVida);
        this.add(panel,BorderLayout.CENTER);
        this.add(pnlBotones,BorderLayout.SOUTH);
        
        
        
        //Para que nunca esten ambos botones en "ON"
        btnCrear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(btnCrear.isSelected()) {
					btnMover.setSelected(false);
				}
			}
        	
        	
        });
        
        
        
        
        
        
        btnMover.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(btnMover.isSelected()) {
					btnCrear.setSelected(false);
				}
			}
        	
        	
        });
        
        
        
        
        
        
        
        
        
        
        panel.addMouseListener(new MouseAdapter() {
            Point Pulsar= null;

            public void mouseReleased(MouseEvent e) {
                Point Soltar= e.getPoint();
                if (btnMover.isSelected()) {
                   
                	for (ElementoEcosistema ele : Ecosistema.getMundo().getElementos()) {//elementos del arrayList
                        if (ele.getPanel().getBounds().contains(Pulsar)) {
                        
                        	//.getBounds obtiene las coordenadas del panel (agua,abejas o flores) y las compara
                        	
                            
                        	//Mover el panel donde hemos soltado el ratón
                        	
                        	 ele.getPanel().setLocation(Soltar);
                            
                            System.out.println("Mover "+ele.getPanel().getLocation() + " a " + Soltar);
                            
                            
                            
                            panel.add(ele.getPanel());
                            panel.revalidate();//Actualizar ventana
                            break;
                        }
                    }
                } else if (btnCrear.isSelected()) {
                    ElementoEcosistema ele = null;

                    if (cbElementos.getSelectedItem()==("Abejas")) {
                    	
                    	//Crear elementos
                    	//String titulo, int x, int y, int anchura(EJE X), int altura (EJE Y)
                        
                    	ele= new ColoniaAbejas("Colonia " +(Ecosistema.getMundo().getElementos().size()+1),Pulsar.x, Pulsar.y, Math.abs(Soltar.x- Pulsar.x),Math.abs(Soltar.y -Pulsar.y));
                    } 
                    
                    else if (cbElementos.getSelectedItem()==("Flores")) {
                        ele= new PlantacionFlores("Pradera " + (Ecosistema.getMundo().getElementos().size()+1),Pulsar.x, Pulsar.y, Math.abs(Soltar.x- Pulsar.x),Math.abs(Soltar.y -Pulsar.y));
                    
                    } else if (cbElementos.getSelectedItem()==("Agua")) {
                        ele= new Agua("Lago " + (Ecosistema.getMundo().getElementos().size()+1),Pulsar.x, Pulsar.y, Math.abs(Soltar.x- Pulsar.x), Math.abs(Soltar.y- Pulsar.y));
                    
                    }
                    //Añadimos el nuevo elemento al array
                    Ecosistema.getMundo().getElementos().add(ele);
                    //Creamos su panel
                    JPanel pNuevo= ele.getPanel();
                   //Metemos el nuevo panel del nuevo elemento en la ventana
                    panel.add(pNuevo);
                    //Actualizar
                    panel.revalidate();
                }
            }

            public void mousePressed(MouseEvent e) {
                Pulsar= e.getPoint();
            }
        });

 
        
        btnVida.addActionListener(e -> {
            if (btnVida.getText()==("Vida")) {
                btnVida.setText("Parar");
                hilo = new Hilo();//Nuevo objeto de tipo Hilo
                hilo.start();///Si pulso el botón vida, comienza el hilo
            } else {
                btnVida.setText("Vida");//Si presiono Parar cambia a "Vida"
                if (hilo != null)
                    ((Hilo) hilo).parar();//Llamo al método parar(); de la clase privada interna Hilo
            }
        });
        
        
   


    	this.setTitle("Ecosistema");
    	this.setSize(500,500);
    	this.setVisible(true);
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	
    }

     
      
    

    private class Hilo extends Thread {//Clase privada dentro de clase publica, solo para Hilo
        private boolean para= true;
 

        public void parar() {//Parar el hilo
            para= false;
        }

        public void run() {
            while (para) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                	e.getStackTrace();
                }
                for (ElementoEcosistema ee : Ecosistema.getMundo().getElementos()) {
                    if (ee instanceof Evolucionable) {
                        Evolucionable ev= (Evolucionable) ee;//Cambio ElementoEcosistema a Evolucionlable
                        ev.evoluciona(1);//Llamo al método, cada segundo simula un día de tiempo
                    }
                }
                panel.revalidate();
            }
        }
    }

    public static void main(String[] args) {
        	VentanaPrincipal se = new VentanaPrincipal();
    }
}

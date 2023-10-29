
package pck;

import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EventObject;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.synth.SynthOptionPaneUI;


public class VentanaTablaDatos extends JFrame {
	private JLabel lblInfo;
	private JTree tree;
	private JTable tDatos;
	private DefaultTableModel mDatos;
	private JPanel pnlGrafico;
	private JButton btnInsercion;
	private JButton btnBorrado;
	private JButton btnOrden;
	private JPanel pnlSuperior;
	private JPanel pnlBotones;
	private static DataSetMunicipios dataset;


public VentanaTablaDatos() {
	//Componentes de la ventana
	//Diseño de la ventana
	
	setSize(800, 600);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	String[] columnas = {"Código", "Nombre", "Habitantes","Provincia","Comunidad","Población"};
	mDatos=new DefaultTableModel(columnas,0);
	tDatos= new JTable(mDatos);
	
	
	JScrollPane tablaScrollPane = new JScrollPane(tDatos);
	add(tablaScrollPane, BorderLayout.CENTER);
	
	
	pnlSuperior = new JPanel();
	lblInfo = new JLabel("Información a mostrar");
	pnlSuperior.add(lblInfo);
	this.add(pnlSuperior, BorderLayout.NORTH);
	DefaultMutableTreeNode municipiosRaiz = new DefaultMutableTreeNode("Municipios");
	tree = new JTree(municipiosRaiz);
	JScrollPane treeScrollPane = new JScrollPane(tree);
	this.add(treeScrollPane, BorderLayout.WEST);
	pnlBotones=new JPanel();
	btnBorrado=new JButton("Borrar");
	btnInsercion=new JButton("Insertar");
	btnOrden=new JButton("Orden");
	pnlBotones.add(btnBorrado);
	pnlBotones.add(btnInsercion);
	pnlBotones.add(btnOrden);
	this.add(pnlBotones,BorderLayout.SOUTH);

	//Carga el dataset del Municipios
	try {
		dataset = new DataSetMunicipios("C:\\Users\\pemma\\eclipse-workspace\\Practica_06Swing\\src\\pck\\m.txt");
		
	} catch (IOException e) {
		e.printStackTrace(); 
}
	
	
	//Carga el dataset en el Tree
	ArrayList<String> listo = new ArrayList<>();
	ArrayList<String> provin=new ArrayList<>();
	
	if (dataset != null && dataset.getListaMunicipios() != null) {
	    for (int i = 0; i < dataset.getListaMunicipios().size(); i++) {
	        String autonoma = dataset.getListaMunicipios().get(i).getAutonomia();
	        String provincia = dataset.getListaMunicipios().get(i).getProvincia();

	    
	        int index = listo.indexOf(autonoma);//si la autonomia no existe -1
	        DefaultMutableTreeNode autonomia;

	        if (index != -1) {//si la autonomia existe
	            autonomia = (DefaultMutableTreeNode) municipiosRaiz.getChildAt(index);
	        
	        } else {//si la autonomia no existe
	            // Creamos una nueva raiz
	            autonomia = new DefaultMutableTreeNode(autonoma);
	            municipiosRaiz.add(autonomia);
	            listo.add(autonoma);
	        }

	        boolean provinExiste = false;
	        for (int j = 0; j < autonomia.getChildCount(); j++) {
	            DefaultMutableTreeNode nodoprov = (DefaultMutableTreeNode) autonomia.getChildAt(j);
	            if (nodoprov.getUserObject().equals(provincia)) {
	                provinExiste = true;
	                break;
	            }
	        }

	        if (!provinExiste) {
	            DefaultMutableTreeNode provincias = new DefaultMutableTreeNode(provincia);
	            autonomia.add(provincias);
	            provin.add(provincias.toString());
	        }
     
	        
	    }
   
	    
	}
	//Para crear un árbol customizado, con el progressbar
	tree.setCellRenderer(new CustomTreeCellRenderer());
	
	setVisible(true);

    
	//Cuando se seleccione una provincia que aparezca en la tabla sus datos
	tree.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	        TreePath click = tree.getPathForLocation(e.getX(), e.getY());
	        if (click != null) {
	            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) click.getLastPathComponent();
	            Object objetoUsu = nodo.getUserObject();

	           
	            if (provin.contains(objetoUsu)) {
	            	
	            	//Cuando se seleccione otra provincia, que la tabla actual se borre
	                while (mDatos.getRowCount() > 0) {
	                    mDatos.removeRow(0);
	                }

	                String provSeleccionada = objetoUsu.toString();


	                //Meter los datos de la provincia en la tabla
	                for (int i = 0; i < dataset.getListaMunicipios().size(); i++) {
	                    if (dataset.getListaMunicipios().get(i).getProvincia().equals(provSeleccionada)) {
	                        int codigo = dataset.getListaMunicipios().get(i).getCodigo();
	                        String nom = dataset.getListaMunicipios().get(i).getNombre();
	                        int hab = dataset.getListaMunicipios().get(i).getHabitantes();
	                        String prov = dataset.getListaMunicipios().get(i).getProvincia();
	                        String auto = dataset.getListaMunicipios().get(i).getAutonomia();
	                        int pob=dataset.getListaMunicipios().get(i).getPoblacion();
	                        
	                        
	                        Object[] fila1 = { codigo, nom, hab, prov, auto,pob};
	                        mDatos.addRow(fila1);
	                        

	                    	
	                        
	                        
	                    }
	                }
	                //Ordenar los datos de la provincia por orden alfabético
	                TableRowSorter<DefaultTableModel> ordena = new TableRowSorter<>(mDatos);
	                tDatos.setRowSorter(ordena);

	                Comparator<String> comparador = (s1, s2) -> s1.compareTo(s2);
	                ordena.setComparator(1, comparador); 

	                ordena.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
	                ordena.sort();
	                
	            }
	        }
	    }
	});


	//Para crear el panel con el gráfico en la izquierda de la ventana
	pnlGrafico = new JPanel() {
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        int provinciaPoblacion = 0; 
	        int estadoPoblacion = 0;    
	        int maxPoblacion = 5000000; 

	        int panelWidth = getWidth();
	        int panelHeight = getHeight();
	        int barWidth = panelWidth / 4; 
	        int barEspacio = panelWidth / 4; 


	        if (tDatos.getSelectedRow() >= 0) {
	            String provinciaSelec = mDatos.getValueAt(tDatos.getSelectedRow(), 3).toString();
	            for (int i = 0; i < dataset.getListaMunicipios().size(); i++) {
	                String provincia = dataset.getListaMunicipios().get(i).getProvincia();
	                int pop = dataset.getListaMunicipios().get(i).getPoblacion();

	                estadoPoblacion += pop;

	                if (provinciaSelec.equals(provincia)) {
	                    provinciaPoblacion += pop; // Accumulate province population
	                }
	            }
	        }

	        //Dibujar la barra de la provincia
	        int provinciaBarHeight = (int) ((double) provinciaPoblacion / maxPoblacion * panelHeight);
	        g.setColor(Color.BLUE); 
	        g.fillRect(barEspacio, panelHeight - provinciaBarHeight, barWidth, provinciaBarHeight);

	        //Dibujar la barra del estado
	        int estadoBarHeight = (int) ((double) estadoPoblacion / maxPoblacion * panelHeight);
	        g.setColor(Color.RED); 
	        g.fillRect(2 * barEspacio + barWidth, panelHeight - estadoBarHeight, barWidth, estadoBarHeight);

	        //Dibujar las rayas horizontales para distinguir
	        g.setColor(Color.BLACK);
	        for (int i = 1; i < 5; i++) {
	            int y = panelHeight - (i * panelHeight / 5);
	            g.drawLine(barEspacio, y, 3 * barEspacio + 2 * barWidth, y);
	        }
	    }
	};
	pnlGrafico.setPreferredSize(new Dimension(300, 300)); 
	this.add(pnlGrafico, BorderLayout.EAST);

	tDatos.getSelectionModel().addListSelectionListener(e -> pnlGrafico.repaint());



	
	
	
	
	//Crear el progressbar para la población en la columna 5
	tDatos.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
	    private JProgressBar pb = new JProgressBar(50000, 5000000);

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
	        
	    	if (col == 5) {
	    		int population = (Integer) value;

	            // Calcula el valor del color en función de la población
	            int green = 255 - (int) (200 * (population - 50000) / 4950000);
	            int red = 255 - green;

	            Color color = new Color(red, green, 0); // Crea un color personalizado

	            pb.setValue(population);
	            pb.setForeground(color); // Configura el color de fondo según el cálculo
	            pb.setString(value.toString());
	            pb.setStringPainted(true);

	                
	    	
	            
	            
	            return pb;
	    	}

	        JLabel rendPorDefecto = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
	        return rendPorDefecto;
	 
	    	}
	    	});
	

	//Crear una clase TableCellEditor personalizada que no permite la edición
	class NonEditableEditor extends DefaultCellEditor {
	    public NonEditableEditor(JTextField textField) {
	        super(textField);
	        setClickCountToStart(0);}

	    @Override
	    public boolean isCellEditable(EventObject e) {
	        return false; // No permite la edición en ninguna celda
	    }
	}

	//Asignar el TableCellEditor personalizado a la columna 3 y 4
	tDatos.getColumnModel().getColumn(3).setCellEditor(new NonEditableEditor(new JTextField()));
	tDatos.getColumnModel().getColumn(4).setCellEditor(new NonEditableEditor(new JTextField()));
	
	//Al hacer click derecho en una de las provincias que salga su información coloreada, si 
	//se vuelve a pulsar que cambie al color inicial(negro)
	tree.addMouseListener(new MouseAdapter() {
		boolean coloreado=false;
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			  if (e.getButton() == MouseEvent.BUTTON3) {
				  
				  TreePath click = tree.getPathForLocation(e.getX(), e.getY());
			        if (click != null) {
			            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) click.getLastPathComponent();
			            Object objectoUsu = nodo.getUserObject();
			            String nomProv=(objectoUsu.toString());
			    		
			   		 if(provin.contains(nomProv)) {
			   		 	//nomProv=Cadiz
			   		 	for(int i=0;i<dataset.getListaMunicipios().size();i++){
			   		 		if(dataset.getListaMunicipios().get(i).getProvincia().equals(nomProv)) {
			   		 			
			   		 			if(coloreado==false) {
			   		 			setCellRendererForTable(tDatos);
			   		 			coloreado=true;
			   		 			}else {
			   		 			setCellRendererForTable2(tDatos);
			   		 				
			   		 				coloreado=false;
			   		 			}
			   		 		
			   		 		
			   		 		
			   		 		
			   		 		}
			   		 	}
			   		 }
			            
			            
			            
			            
			        }
				  
				  
			  }
		}
		  
		  
		
		
	});
	
	//Codificar el botón de borrado, para que se borre la fila seleccionada y también se borre el municipio de la lista
	btnBorrado.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	
	    	int respuesta=JOptionPane.showConfirmDialog(null, "¿Eliminar fila?","Confirmar",JOptionPane.YES_NO_OPTION);
	    	if(respuesta==JOptionPane.YES_OPTION) {
	    	
	    	
	    	
	        int filaSel = tDatos.getSelectedRow();
	        if (filaSel >= 0) {
	        	String provincia = mDatos.getValueAt(filaSel, 1).toString();
	        	for(int i=0;i<dataset.getListaMunicipios().size();i++) {
	        		if(dataset.getListaMunicipios().get(i).getNombre().equals(provincia)) {
	        			dataset.getListaMunicipios().remove(i);
	        		}
	        	}
	        	
	        
	        	
	            int modelRow = tDatos.convertRowIndexToModel(filaSel); 
	            mDatos.removeRow(modelRow);
	        }
	        	
	        }
	    }
	});


	
	//Codificar el botón de insercción para que se añada una fila y se añada también un nuevo municipio a la lista
	btnInsercion.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		    int fila=tDatos.getSelectedRow();
		    
		   
            
		        	
		        	 int codigo = dataset.getListaMunicipios().size()+1; 
		             String nombre = ""; 
		             int habitantes = 50000; 
		             String provincia = mDatos.getValueAt(fila, 3).toString();
		             String autonomia = mDatos.getValueAt(fila, 4).toString();
		             int poblacion =50000; 

		             
		             
		        	Municipio m=new Municipio(codigo,nombre,habitantes,provincia,autonomia,poblacion);
		        	dataset.getListaMunicipios().add(m);
		             
		        	 Object[] infofila = { codigo, nombre, habitantes, provincia, autonomia, poblacion };
		             mDatos.addRow(infofila);

		        	
		        
			
		}
		
		
		
	});
	
	
	//Modificar el orden pasando de alfabético<->número habitantes
	btnOrden.addActionListener(new ActionListener() {
		boolean alfabetico=true;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			
			if(alfabetico==false) {
				 alfabetico=true;
				   TableRowSorter<DefaultTableModel> orden = new TableRowSorter<>(mDatos);
	                tDatos.setRowSorter(orden);

	                Comparator<String> comparador = (s1, s2) -> s1.compareTo(s2);
	                orden.setComparator(1, comparador);

	                orden.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
	                orden.sort();
	                
	               
				
				
			}else if(alfabetico==true){
				alfabetico=false;
				  TableRowSorter<DefaultTableModel> orden = new TableRowSorter<>(mDatos);
	                tDatos.setRowSorter(orden);

	                Comparator<Integer> comparador = (s1, s2) -> s1.compareTo(s2);
	                orden.setComparator(2, comparador); 

	                orden.setSortKeys(Arrays.asList(new RowSorter.SortKey(2, SortOrder.DESCENDING)));
	                orden.sort();
	                
	                
				
			}
			
			
			
			
			
		}
	});
	
	
	
	
	
	
	
	
	
	
	
	
}	
	//Metodo que se llama al hacer click derecho en una provincia, para pintar toda la fila del color calculado
private void setCellRendererForTable(JTable table) {
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            int poblacion = (Integer) table.getValueAt(row, 5);

            int green = 255 - (int) (200 * (poblacion - 50000) / 4950000);
            int red = 255 - green;

            Color color = new Color(red, green, 0); // Crea un color personalizado
            c.setForeground(color);

            return c;
        }
    });
}

//Método que se llama al hacer click derecho en una provincia que está ya coloreada, para que vuelva al color orginal
private void setCellRendererForTable2(JTable table) {
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            
            c.setForeground(Color.black);

            return c;
        }
    });

   
}


//Crear un arból personalizado con un progressbar
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
    private JProgressBar pb = new JProgressBar(96906, 4453929); // Define el rango de la barra de progreso.

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

            if (node.getLevel() == 1 && node.getUserObject() instanceof String) {
                String autonomia = (String) node.getUserObject();
                int sumaCiudades = 0;

                // Calcula la suma de habitantes para la autonomía
                for (int i = 0; i < dataset.getListaMunicipios().size(); i++) {
                    if (autonomia.equals(dataset.getListaMunicipios().get(i).getAutonomia())) {
                        sumaCiudades += dataset.getListaMunicipios().get(i).getHabitantes();
                    }
                }

                // Configura la barra de progreso con el valor de la suma de habitantes
                pb.setValue(sumaCiudades);
                pb.setStringPainted(true); // Muestra el valor en la barra de progreso

                // Devuelve un panel que contiene el nombre de la autonomía y la barra de progreso
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(c, BorderLayout.WEST);
                panel.add(pb, BorderLayout.CENTER);
                return panel;
            }
        }

        return c;
    }
}












	public static void main(String[] args) {
		VentanaTablaDatos ve = new VentanaTablaDatos();
}
}



















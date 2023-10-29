package Projecto;

import java.util.ArrayList;

public class Ecosistema {
	
	
	//Array donde estarán los objetos Agua, ColoniaAbejas y PlantacionFlores que son los que heredan de ElemetoEcosistema
	private ArrayList<ElementoEcosistema> elementos = new ArrayList<>();

	
	
	//Creamos una sola instancia de tipo Ecosistema, la única de todo el programa ya que solom hay un Mundo
	private static final Ecosistema mundo = new Ecosistema();
    
	//Devuelve la instancia mundo que he creado, método que se asegura que todos los elementos provienen de la misma instancia Ecosistema
    public static Ecosistema getMundo() {
        return mundo;
    }



    public static int distancia(ElementoEcosistema ee1, ElementoEcosistema ee2) {
        int x1= ee1.getPanel().getX(); //Coordenadas X del elemento ee1
        int y1= ee1.getPanel().getY(); //Coordenadas Y del elemento ee1
        int x2= ee2.getPanel().getX(); //Coordenadas X del elemento ee2
        int y2= ee2.getPanel().getY(); //Coordenadas Y del elemento ee2
        
        
        //Pitagoras, devuelve la distancia entre dos elementos (P.ej Agua y PlantacionFlores)
        return (int) Math.sqrt(Math.pow((x1 - x2), 2.0) + Math.pow((y1 - y2), 2.0));
    }
    
    //Metodo que devuelve el array de elementos
    public ArrayList<ElementoEcosistema> getElementos() {
        return elementos;
    }

}

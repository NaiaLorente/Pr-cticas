package Projecto;

import javax.swing.*;
public abstract class ElementoEcosistema {
	protected String titulo;//Las clases que hereden de esta tienen título propio
	public abstract JPanel getPanel();//Todas las clases que hereden de ElementoEcosistema tienen que tener este método
	//Obliga a que todas las clases tengan panel
	
	
	public String getTitulo() {
		return this.titulo;
}
public void setTitulo(String titulo) {
this.titulo = titulo;
}
}

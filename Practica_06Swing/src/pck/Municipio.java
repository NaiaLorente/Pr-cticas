package pck;
/** Permite crear objetos municipio con información de población, provincia y comunidad autónoma
*/
public class Municipio {
	private int codigo;
	private String nombre;
	private int habitantes;
	private String provincia;
	private String autonomia;
	private int poblacion;
	/** Crea un municipio
	 * @param codigo	Código único del municipio (1-n)
	 * @param nombre	Nombre oficial
	 * @param habitantes	Número de habitantes
	 * @param provincia	Nombre de su provincia
	 * @param autonomia	Nombre de su comunidad autónoma
	 */
	public Municipio(int codigo, String nombre, int habitantes, String provincia, String autonomia,int poblacion) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.habitantes = habitantes;
		this.provincia = provincia;
		this.autonomia = autonomia;
		this.poblacion=poblacion;
	}
	
	public void setAutonomia(String autonomia) {
		this.autonomia = autonomia;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getHabitantes() {
		return habitantes;
	}
	public void setHabitantes(int habitantes) {
		this.habitantes = habitantes;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getAutonomia() {
		return autonomia;
	}
	public int getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(int poblacion) {
		this.poblacion = poblacion;
	}
	
}
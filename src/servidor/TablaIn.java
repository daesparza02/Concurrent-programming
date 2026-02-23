package servidor;

import java.io.ObjectInputStream;
import java.util.HashMap;

public class TablaIn {
	private PasoTestigo semaforo;
	private HashMap<String, ObjectInputStream> tablaIn;
	public TablaIn() {
		this.tablaIn=new HashMap<String,ObjectInputStream>();
		this.semaforo= new PasoTestigo();
		
	}
	public void put( String nombreUsuario, ObjectInputStream in) {
		semaforo.requestEscribir();
		tablaIn.put(nombreUsuario, in);
		semaforo.releaseEscribir();
	}
	public void remove (String nombreUsuario, ObjectInputStream in) {
		semaforo.requestEscribir();
		tablaIn.remove(nombreUsuario,in);
		semaforo.releaseEscribir();
	}
	public ObjectInputStream getUserInputStream(String nombreUsuario)throws InterruptedException{
		semaforo.requestLeer();
		ObjectInputStream in = tablaIn.get(nombreUsuario);
		semaforo.releaseLeer();
		return in;
	}
}

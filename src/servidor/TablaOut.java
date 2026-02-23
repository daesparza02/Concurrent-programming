package servidor;

import java.io.ObjectOutputStream;
import java.util.HashMap;

public class TablaOut {
	private PasoTestigo semaforo;
	private HashMap<String, ObjectOutputStream> tablaOut;
	public TablaOut() {
		this.tablaOut=new HashMap<String,ObjectOutputStream>();
		this.semaforo= new PasoTestigo();
		
	}
	public void put( String nombreUsuario, ObjectOutputStream in) {
		semaforo.requestEscribir();
		tablaOut.put(nombreUsuario, in);
		semaforo.releaseEscribir();
	}
	public void remove (String nombreUsuario, ObjectOutputStream in) {
		semaforo.requestEscribir();
		tablaOut.remove(nombreUsuario,in);
		semaforo.releaseEscribir();
	}
	public ObjectOutputStream getUserOutputStream(String nombreUsuario)throws InterruptedException{
		semaforo.requestLeer();
		ObjectOutputStream out = tablaOut.get(nombreUsuario);
		semaforo.releaseLeer();
		return out;
	}
}
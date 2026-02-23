package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import usuario.Usuario;

public class TablaUsuario {
		private MonitorServidor monitor;
		private HashMap<String,Usuario> tablaUsuario;
	
		public TablaUsuario() {
		this.tablaUsuario = new HashMap<String,Usuario>();
		this.monitor = new MonitorServidor();
		}
	
		public void addUsuario(Usuario usuario) throws InterruptedException {
			monitor.requestEscribir();
			tablaUsuario.put(usuario.getId(), usuario);
			monitor.releaseEscribir();
		}
		public boolean yaesta(String id) {
			return tablaUsuario.containsKey(id);
		}
	    public void borrarUsuario(String userName) throws InterruptedException {
		    monitor.requestEscribir();
		    tablaUsuario.remove(userName);
		    monitor.releaseEscribir();
	    }
	    public void addArchivo(String nombre,String usuario) throws InterruptedException {
			monitor.requestEscribir();
			tablaUsuario.get(usuario).anadirarchivo(nombre);
			monitor.releaseEscribir();
		}
	   
	    public List<String> getListaUsuarios() throws InterruptedException{

		    monitor.requestLeer();
	
			List<String> listaUsuarios = new ArrayList<String>();
		
			for (Usuario valor : tablaUsuario.values()) {
				listaUsuarios.add(valor.getId());
			}
		
			monitor.releaseLeer();
			return listaUsuarios;
	    }
	    
	    
	    public List<List<String>> getListaArchivos() throws InterruptedException {

		    monitor.requestLeer();
	
			List<List<String>> listaUsuarios = new ArrayList<List<String>>();
		
			for (Usuario usuario : tablaUsuario.values()) {
		
				List<String> listaArchivos = new ArrayList<String>();
			
				for(String archivo : usuario.getArchivos()) {
					listaArchivos.add(archivo);
				}
			
				listaUsuarios.add(listaArchivos);
		
			}
		
			monitor.releaseLeer();
			return listaUsuarios;
	    }
	    
		public String getNombreUsuario(String fileName) throws InterruptedException {
	
			monitor.requestLeer();
		
			String nombre = null;
		
			for(Usuario usuario : tablaUsuario.values()) {
		
				for(String archivo : usuario.getArchivos()) {
			
					if(fileName.equals(archivo)) {
						nombre = usuario.getId();
						break;
					}
				}
			
				if(nombre != null) {
					break;
				}
			}
		
			monitor.releaseLeer();
			return nombre;
		}
		public boolean yalotienes(String fileName, String nombreUsuario) throws InterruptedException {
			monitor.requestLeer();
			boolean encontrado=false;
			Usuario aux=tablaUsuario.get(nombreUsuario);
			for(String archivo : aux.getArchivos()) {
				
				if(fileName.equals(archivo)) {
					encontrado=true;
					break;
				}
			}
			monitor.releaseLeer();
			return encontrado;
			
		}
}

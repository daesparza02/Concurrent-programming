package usuario;

import java.util.List;

public class Usuario {

	private String id;
	private List<String> archivosUsuario;
	
	public Usuario(String id, List<String> archivos) {
		this.id=id;
		this.archivosUsuario=archivos;
	}
	
	public String getId() {
		return this.id;
	}
	
	public List<String> getArchivos(){
		return this.archivosUsuario;
	}
	public void anadirarchivo(String name) {
		archivosUsuario.add(name);
	}
}

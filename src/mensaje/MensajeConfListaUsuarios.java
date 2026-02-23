package mensaje;

import java.io.Serializable;
import java.util.List;

public class MensajeConfListaUsuarios extends Mensaje implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<String> usuarios;//Lista de usuarios disponibles
	private List<List<String>> listaarchivos;//Lista de la lista de archivos de cada usuario

	public MensajeConfListaUsuarios(String origen, String destino, List<String> usuarios, List<List<String>> listaarchivos) {
		super(TipoMensaje.CONF_LISTA_USUARIOS, origen, destino);
		this.usuarios=usuarios;
		this.listaarchivos=listaarchivos;
	}
	
	public List<String> getUsuarios(){
		return this.usuarios;
	}
	
	public List<List<String>> getListaArchivos(){
		return this.listaarchivos;
	}

}

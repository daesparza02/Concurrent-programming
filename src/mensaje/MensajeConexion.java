package mensaje;

import java.io.Serializable;
import java.util.List;

public class MensajeConexion extends Mensaje implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<String> archivos;

	public MensajeConexion(String origen, String destino, List<String> archivos) {
		super(TipoMensaje.CONEXION, origen, destino);
		this.archivos=archivos;
	}
	
	public List<String> getArchivos(){
		return this.archivos;
	}

}

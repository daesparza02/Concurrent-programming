package mensaje;

import java.io.Serializable;

public class MensajePedirUsuario extends Mensaje implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String archivosPedidos;
	
	public MensajePedirUsuario(String origen, String destino, String archivos) {
		super(TipoMensaje.PEDIR_USUARIO, origen, destino);
		this.archivosPedidos = archivos;
	}
	
	public String getArchivosPedidos() {
		return this.archivosPedidos;
	}

}

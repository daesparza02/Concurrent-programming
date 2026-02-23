package mensaje;

import java.io.Serializable;

public class MensajePedirProducto extends Mensaje implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String archivoPedido;

	public MensajePedirProducto(String origen, String destino, String archivo) {
		super(TipoMensaje.PEDIR_PROD, origen, destino);
		this.archivoPedido=archivo;
	}
	
	public String getArchivoPedido() {
		return this.archivoPedido;
	}

}

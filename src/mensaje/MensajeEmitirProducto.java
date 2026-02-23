package mensaje;

import java.io.Serializable;

public class MensajeEmitirProducto extends Mensaje implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String archivoEmitido;

	public MensajeEmitirProducto(String origen, String destino, String archivo) {
		super(TipoMensaje.PEDIR_PROD, origen, destino);
		this.archivoEmitido=archivo;
	}
	
	public String getArchivoPedido() {
		return this.archivoEmitido;
	}
}

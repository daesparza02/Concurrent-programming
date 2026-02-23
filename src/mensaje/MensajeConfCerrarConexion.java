package mensaje;

import java.io.Serializable;

public class MensajeConfCerrarConexion extends Mensaje implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public MensajeConfCerrarConexion(String origen, String destino) {
		super(TipoMensaje.CONF_CERRAR_CONEX, origen, destino);
	}
}

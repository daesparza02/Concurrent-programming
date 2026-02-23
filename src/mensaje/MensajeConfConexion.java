package mensaje;

import java.io.Serializable;

public class MensajeConfConexion extends Mensaje implements Serializable{

	private static final long serialVersionUID = 1L;

	public MensajeConfConexion(String origen, String destino) {
		super(TipoMensaje.CONF_CONEXION, origen, destino);
	}

}

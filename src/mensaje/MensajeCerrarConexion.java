package mensaje;

import java.io.Serializable;

public class MensajeCerrarConexion extends Mensaje implements Serializable{

	private static final long serialVersionUID = 1L;

	public MensajeCerrarConexion(String origen, String destino) {
		super(TipoMensaje.CERRAR_CONEX, origen, destino);
	}

}

package mensaje;

import java.io.Serializable;

public class MensajeYaLogueado extends Mensaje implements Serializable {
	private static final long serialVersionUID = 1L;

	public MensajeYaLogueado(String origen, String destino) {
		super(TipoMensaje.YA_LOGUEADO, origen, destino);
	}
}

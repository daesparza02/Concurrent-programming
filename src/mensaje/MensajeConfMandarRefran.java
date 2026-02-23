package mensaje;

import java.io.Serializable;

public class MensajeConfMandarRefran extends Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;

	public MensajeConfMandarRefran(String origen, String destino) {
		super(TipoMensaje.CONF_MANDAR_REFRAN, origen, destino);
	}
}

package mensaje;

import java.io.Serializable;

public class MensajeNoExistente extends Mensaje implements Serializable{

	private static final long serialVersionUID = 1L;

	public MensajeNoExistente(String origen, String destino) {
		super(TipoMensaje.NO_EXISTENTE, origen, destino);
	}

}

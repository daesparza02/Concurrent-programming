package mensaje;

import java.io.Serializable;

public class MensajeListaUsuarios extends Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;

	public MensajeListaUsuarios(String origen, String destino) {
		super(TipoMensaje.LISTA_USUARIOS, origen, destino);
	}

}

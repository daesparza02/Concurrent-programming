package mensaje;
import java.io.Serializable;


public class MensajeRecibirCatedra extends Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;

	public MensajeRecibirCatedra(String origen, String destino) {
		super(TipoMensaje.RECIBIR_CATEDRA, origen, destino);
	}

}

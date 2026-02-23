package mensaje;

import java.io.Serializable;
import java.util.List;

public class MensajeConfRecibirCatedra extends Mensaje implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private String barra;

	public MensajeConfRecibirCatedra(String origen, String destino, String barra) {
		super(TipoMensaje.CONFRECIBIR_CATEDRA, origen, destino);
		this.barra=barra;
	}
	
	public String getRefran(){
		return this.barra;
	}

}

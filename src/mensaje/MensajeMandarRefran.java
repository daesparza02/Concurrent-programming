package mensaje;

import java.io.Serializable;

public class MensajeMandarRefran extends Mensaje implements Serializable {

private static final long serialVersionUID = 1L;
	
	private String barra;

	public MensajeMandarRefran(String origen, String destino, String barra) {
		super(TipoMensaje.MANDAR_REFRAN, origen, destino);
		this.barra=barra;
	}
	
	public String getRefran(){
		return this.barra;
	}
}

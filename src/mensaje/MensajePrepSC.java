package mensaje;

import java.io.Serializable;

public class MensajePrepSC extends Mensaje implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int puerto;

	public MensajePrepSC(String origen, String destino, int puerto) {
		super(TipoMensaje.PREPARADO_S_C, origen, destino);
		this.puerto=puerto;
	}
	
	public int getPuerto() {
		return this.puerto;
	}
}

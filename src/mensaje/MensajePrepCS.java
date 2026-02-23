package mensaje;

import java.io.Serializable;

public class MensajePrepCS extends Mensaje implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int puerto;

	public MensajePrepCS(String origen, String destino, int puerto) {
		super(TipoMensaje.PREPARADO_C_S, origen, destino);
		this.puerto=puerto;
	}
	
	public int getPuerto() {
		return this.puerto;
	}

}

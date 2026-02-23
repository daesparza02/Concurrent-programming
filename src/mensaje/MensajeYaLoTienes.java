package mensaje;

import java.io.Serializable;

public class MensajeYaLoTienes extends Mensaje implements Serializable{

	public MensajeYaLoTienes( String origen, String destino){
		super(TipoMensaje.YALOTIENES, origen, destino);
	}
}

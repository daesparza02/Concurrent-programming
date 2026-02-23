package servidor;

import java.io.IOException;

public class MainServidor {
	public static void main(String[] args) {
		try {
			Servidor servidor = new Servidor();
			servidor.initServidor();
			
			
		}catch(IOException e) {
			System.err.println("Error en el Main Servidor");
			e.printStackTrace();
		}
	}

}

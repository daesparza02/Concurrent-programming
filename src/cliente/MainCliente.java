package cliente;
import java.io.IOException;

public class MainCliente {

	public static void main(String[] args) {
		Cliente cliente;
		try {
			cliente=new Cliente();
			cliente.iniciar();
		}
		catch(IOException | InterruptedException e) {
			System.err.println("Error en main cliente.");
			e.printStackTrace();
		}
		
	}
}

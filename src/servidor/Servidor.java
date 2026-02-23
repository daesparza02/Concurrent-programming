package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

import cliente.lockRompeEmpate;

public class Servidor {
		private ServerSocket server;
		private Socket s;
	    private final static int port = 1111;	   
	    private TablaUsuario tablaUsuario;
	    private TablaIn tablaIn;
	    private TablaOut tablaOut;
	    private lockTicket lock;
	    private Almacen almacen;
	    private lockTicket lockPrints;
	    public Servidor() throws IOException{
				server = new ServerSocket(port);
				tablaUsuario = new TablaUsuario();
				this.tablaIn = new TablaIn();
				this.tablaOut = new TablaOut();
				lock = new lockTicket(1);
				almacen= new AlmacenK();
				this.lockPrints=new lockTicket(1);
	    }
	   
		public void initServidor() throws IOException{
			lockPrints.lockear();
			System.out.println("CREACION SERVIDOR.");
			lockPrints.deslockear();
			try {
				while(true) {
					s = server.accept();
					(new OyenteCliente(s, tablaUsuario, tablaIn, tablaOut, lock,almacen,lockPrints)).start();
				}
			}catch(IOException e) {
				server.close();
				lockPrints.lockear();
				System.out.println("SERVIDOR CERRADO.");
				lockPrints.deslockear();
			} 
		}
}

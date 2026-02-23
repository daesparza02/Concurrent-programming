package cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClienteEmisor extends Thread{
	private String archivo;//nombre del txt
	private int puerto;
	Cliente c;
	private lock rompeempate;
	
	public ClienteEmisor(String a, int p, Cliente c, lock l) {
		this.archivo=a;
		this.puerto=p;
		this.c=c;
		this.rompeempate=l;
	}
	
	public void run() {
		try {
			ServerSocket server = new ServerSocket(puerto);
			Socket s = server.accept();
			Archivo arch = new Archivo(this.archivo, System.getProperty("user.dir")+ "/data/" + c.getNombre()+ "/archivos/" + this.archivo);
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(arch);
			out.flush();
			
			server.close();
			s.close();
			
		} catch(IOException e) {
			rompeempate.lockear(2);
			System.err.println("ERROR EN EMISOR.");
			e.printStackTrace();
			rompeempate.lockear(2);
		}
	}
	
}

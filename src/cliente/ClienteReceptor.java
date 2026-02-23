package cliente;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class ClienteReceptor extends Thread{
	private int puerto;
	private Semaphore e;
	private Cliente c;
	private lock rompeEmpate;
	public ClienteReceptor( int port, Semaphore e, Cliente c, lock l) {
		this.puerto=port;
		this.e=e;
		this.c=c;
		this.rompeEmpate=l;
	}
	public void run() {
		
		try {
			Socket socket= new Socket("localhost",puerto);
			ObjectInputStream in= new ObjectInputStream(socket.getInputStream());
			Archivo nArchivos= (Archivo) in.readObject();
			
			//Descargamos el archivo en la carpeta del receptor
			File dir = new File(System.getProperty("user.dir")+ "/data/" + c.getNombre()+ "/archivos/");
			if(!dir.exists())dir.mkdirs();
			File archivodestino=new File(dir, nArchivos.getArchivo());
			try(FileOutputStream fos=new FileOutputStream(archivodestino)){
				fos.write(nArchivos.getBytes());
			}catch(IOException e){
				rompeEmpate.lockear(3);
				System.err.println("Error al descargar el archivo.");
				rompeEmpate.deslockear(3);
			}
			
			File archivo = new File(System.getProperty("user.dir")+ "/data/" + c.getNombre()+"/nombreArchivos.txt");
			try(FileWriter escritor = new FileWriter(archivo, true)){
				escritor.write(nArchivos.getArchivo() + System.lineSeparator());
			}catch(IOException e) {
				rompeEmpate.lockear(3);
				System.err.println("Error al escribir en nombreArchivos.");
				rompeEmpate.deslockear(3);
			}
			rompeEmpate.lockear(3);
			System.out.println("Contenido archivo: ");
			nArchivos.print();
			rompeEmpate.deslockear(3);
			socket.close();
			e.release();
		}catch (IOException|ClassNotFoundException e) {
			rompeEmpate.lockear(3);
			System.err.println( "ERROR EN EL CLIENTE RECEPTOR");
			e.printStackTrace();
			rompeEmpate.deslockear(3);
		}
	}
	
}

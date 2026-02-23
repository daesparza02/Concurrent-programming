package cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import mensaje.MensajeCerrarConexion;
import mensaje.MensajeConexion;
import mensaje.MensajeConfCerrarConexion;
import mensaje.MensajeListaUsuarios;
import mensaje.MensajeMandarRefran;
import mensaje.MensajePedirProducto;
import mensaje.MensajeRecibirCatedra;

public class Cliente {
	final String servidorIP="localhost";
	final int puerto=1111;
	
	private String nombre;
	private static Scanner s = new Scanner(System.in);
	private List<String> lineas;
	private lock lock;
	private Socket sock;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Semaphore e;
	private lock rompeEmpate;
	
	public Cliente() throws IOException {
		this.lineas=new ArrayList<String>();
		this.sock=new Socket(servidorIP, puerto);
		this.out=new ObjectOutputStream(sock.getOutputStream());
		this.in = new ObjectInputStream(sock.getInputStream());
		this.lock=new lockPanaderia(2);
		this.e=new Semaphore(1);
		rompeEmpate= new lockRompeEmpate(4);
	}
	
	public void iniciar() throws IOException, InterruptedException {
		funNombre();
		Thread oyente = new OyenteServidor(lock, this, e,rompeEmpate);
		oyente.start();
		leerArchivos();
		int opcion=1;
		MensajeConexion me= new MensajeConexion(nombre,"servidor", lineas);
		lock.lockear(0);
		out.writeObject(me);
		out.flush();
		lock.deslockear(0);
		while(opcion!=0) {
			
			e.acquire();//Para que el menú espere a que se realice la orden
			opcion=getop();
			if(opcion==1) {
				rompeEmpate.lockear(0);
				System.out.println("Perfecto, aquí tienes toda la información deseada:");
				rompeEmpate.deslockear(0);
				lock.lockear(0);
				out.writeObject(new MensajeListaUsuarios(nombre,"servidor"));
				out.flush();
				lock.deslockear(0);		
			}
			else if (opcion==2) {
				rompeEmpate.lockear(0);
				System.out.println("Perfecto, ahora proporcionanos el nombre del archivo deseado");
				rompeEmpate.deslockear(0);

				String nombreArchivo= s.next();
				lock.lockear(0);
				out.writeObject(new MensajePedirProducto(nombre,"servidor", nombreArchivo));
				out.flush();
				lock.deslockear(0);		
			}
			else if (opcion==3) {
				rompeEmpate.lockear(0);

				System.out.println("Perfecto, buscamos una buena cátedra:");
				rompeEmpate.deslockear(0);

				lock.lockear(0);
				out.writeObject(new MensajeRecibirCatedra(nombre,"servidor"));
				out.flush();
				lock.deslockear(0);	
			}
			else if (opcion==4) {
				rompeEmpate.lockear(0);

				System.out.println("Perfecto, escribe un refran:");
				rompeEmpate.deslockear(0);

				 s.nextLine();
				String refran = s.nextLine();
				
				
				lock.lockear(0);
				out.writeObject(new MensajeMandarRefran(nombre,"servidor",refran));
				out.flush();
				lock.deslockear(0);	
			}
			
		}
		rompeEmpate.lockear(0);

		System.out.println("Se está saliendo del programa.");
		rompeEmpate.deslockear(0);

		lock.lockear(0);

		out.writeObject(new MensajeCerrarConexion(nombre,"servidor"));
		
		out.flush();
		lock.deslockear(0);

		oyente.join();

		s.close();
		
		sock.close();
	}
	private void funNombre() {
		rompeEmpate.lockear(0);

		System.out.println("INTRODUCE TU NOMBRE DE USUARIO (usuario1, usuario2, usuario3 o usuario4) o introduce uno nuevo.");
		rompeEmpate.deslockear(0);

		String input=s.nextLine();
		File file=null;
		file= new File(System.getProperty("user.dir")+ "/data/"+input+ "/nombreArchivos.txt");
		if(!file.exists()) {
			rompeEmpate.lockear(0);

			System.out.println("Creando Usuario...");
			rompeEmpate.deslockear(0);

			File carpeta = new File(System.getProperty("user.dir")+ "/data/",input);
			carpeta.mkdirs();
			File carpetaarchivos = new File(System.getProperty("user.dir")+ "/data/"+ input, "archivos");
			carpetaarchivos.mkdirs();
			File archivo= new File( carpeta, "nombreArchivos.txt");
			try {
				archivo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.nombre=input;
		rompeEmpate.lockear(0);

		System.out.println("ENTRANDO EN "+ this.nombre);
		rompeEmpate.deslockear(0);

		
	}
	private void leerArchivos() throws IOException{
		FileReader fr= new FileReader (new File( System.getProperty("user.dir")+"/data/" + this.nombre + "/nombreArchivos.txt"));
		BufferedReader br= new BufferedReader (fr);
		String linea;
		while ((linea=br.readLine())!= null) {
			this.lineas.add(linea);
		}
				
	}
	private int getop() {
		int opcion=-1;
		boolean valido=false;
		rompeEmpate.lockear(0);

		System.out.println("Elige una de las siguientes opciones:");
		System.out.println("1. Consultar los usuarios con sus respectivos archivos");
		System.out.println("2. Ver y descargar el contenido de un archivo de otro usuario.");
		System.out.println("3. Recibir refran");
		System.out.println("4. Mandar refran");
		System.out.println("0 para salir");

		System.out.println("(Introduce el número de la opción que quieres)");
		rompeEmpate.deslockear(0);

		do {
			try {
				opcion=Integer.parseInt(s.next());
				valido=true;
			} catch (NumberFormatException e) {
				rompeEmpate.lockear(0);

				System.out.println("Opción no válida vuelve a introducir una válida");
				rompeEmpate.deslockear(0);

				valido=false;
			}
			if(opcion>4||opcion<0) {
				rompeEmpate.lockear(0);

				System.out.println("Opción no válida vuelve a introducir una válida");
				rompeEmpate.deslockear(0);

				valido=false;
			}
		}while(!valido);
		return opcion;
	}
	public String getNombre() {
		return this.nombre;
	}
	public ObjectInputStream getInputStream() {
		return this.in;
	}
	public ObjectOutputStream getOutputStream() {
		return this.out;
	}
}

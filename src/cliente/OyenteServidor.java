package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

import mensaje.Mensaje;
import mensaje.MensajeCerrarConexion;
import mensaje.MensajeConfListaUsuarios;
import mensaje.MensajeConfRecibirCatedra;
import mensaje.MensajeEmitirProducto;
import mensaje.MensajePedirUsuario;
import mensaje.MensajePrepCS;
import mensaje.MensajePrepSC;

public class OyenteServidor extends Thread{
	
	private Cliente c;
	private lock lock;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Semaphore e;
	private lock rompeEmpate;
	
	public OyenteServidor(lock l, Cliente cliente, Semaphore e, lock rompeEmpate) {
		this.c=cliente;
		this.lock=l;
		this.out = cliente.getOutputStream();
		this.in = cliente.getInputStream();
		this.e=e;
		this.rompeEmpate= rompeEmpate;
	}
	
	
	public void run() {
		try {
			boolean end = false;
			while(!end) {
				Mensaje mensaje = (Mensaje) in.readObject();
				switch(mensaje.getType()) {
				case CONF_CONEXION:
					rompeEmpate.lockear(1);
					System.out.println("Conexion Confirmada");
					rompeEmpate.deslockear(1);
					break;
				case CONF_LISTA_USUARIOS:
					MensajeConfListaUsuarios m= (MensajeConfListaUsuarios) mensaje;
					rompeEmpate.lockear(1);
					System.out.println("USUARIOS CONECTADOS:");
					rompeEmpate.deslockear(1);
					List<String> usuarios= m.getUsuarios();
					List<List<String>> nombresArchivos= m.getListaArchivos();
					for(int i=0;i<usuarios.size();i++) {
						rompeEmpate.lockear(1);
						System.out.println("Usuarios: "+ usuarios.get(i)+ "\nNOMBRES ARCHIVOS: ");
						rompeEmpate.deslockear(1);
						List<String> archus = nombresArchivos.get(i);
						for(int j=0; j<archus.size();j++) {
							rompeEmpate.lockear(1);
							System.out.println(archus.get(j));
							rompeEmpate.deslockear(1);
						}
					}
					e.release();
					break;
				
					
				case PREPARADO_S_C:

			  		MensajePrepSC men = (MensajePrepSC) mensaje;
			  		(new ClienteReceptor(men.getPuerto(), e, c, rompeEmpate)).start();
			  		break;
			  		
				case CONF_CERRAR_CONEX:
					rompeEmpate.lockear(1);
					System.out.println("CERRANDO CONEXION DE FORMA EXITOSA...");
					rompeEmpate.deslockear(1);
					end=true;
					break;
					
				case NO_EXISTENTE:
					rompeEmpate.lockear(1);
					System.err.println("NO EXISTE EL ARCHIVO!!!");
					rompeEmpate.deslockear(1);
					e.release();					
					break;
			  		
			  	
			  		
				case PEDIR_USUARIO:
			         MensajePedirUsuario mensaj= (MensajePedirUsuario) mensaje;
			         rompeEmpate.lockear(1);
			         System.out.println(mensaje.getOrigen() + " pide el archivo: " + mensaj.getArchivosPedidos());
			         System.out.println("Opcion: ");
			         rompeEmpate.deslockear(1);
			         int puertoNuevo2= (int) ThreadLocalRandom.current().nextInt(1024, 65001);
			         lock.lockear(0);
			         out.writeObject(new MensajePrepCS(c.getNombre(), mensaje.getOrigen(), puertoNuevo2));
			         out.flush();
			         lock.deslockear(0);
			         (new ClienteEmisor (mensaj.getArchivosPedidos(),puertoNuevo2,c, lock)).start();
			         break;
				case YALOTIENES:
					rompeEmpate.lockear(1);
					System.out.println("Ese archivo ya lo tienes");
					rompeEmpate.deslockear(1);
					e.release();
					break;
				case CONFRECIBIR_CATEDRA:
					MensajeConfRecibirCatedra mensajee= (MensajeConfRecibirCatedra) mensaje;
					rompeEmpate.lockear(1);
					System.out.println("Aquí tienes un interesante refrán:");
					String refran= mensajee.getRefran();
					System.out.println("--------------------------------");
					System.out.println(refran);
					System.out.println("--------------------------------");
					rompeEmpate.deslockear(1);

					e.release();
					break;
				case CONF_MANDAR_REFRAN:
					rompeEmpate.lockear(1);
					System.out.println("Perfecto ya se ha mandado el refran al refranero");
					rompeEmpate.deslockear(1);
					e.release();
					break;
				case YA_LOGUEADO://///
					rompeEmpate.lockear(1);
					System.out.println("Ya está ese usuario. Cerrando conexión.");
					rompeEmpate.deslockear(1);

					out.writeObject(new MensajeCerrarConexion(c.getNombre(),"servidor"));
					
					out.flush();
					break;
			    default:
			    	break;
				
				}
				
				
			}
			
			
		}catch(IOException| ClassNotFoundException e) {
			System.err.println("Error en oyente servidor");
			e.printStackTrace();
		}
		
	}

}

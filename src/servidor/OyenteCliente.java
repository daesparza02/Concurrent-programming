package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import mensaje.Mensaje;
import mensaje.MensajeCerrarConexion;
import mensaje.MensajeConexion;
import mensaje.MensajeConfCerrarConexion;
import mensaje.MensajeConfConexion;
import mensaje.MensajeConfListaUsuarios;
import mensaje.MensajeConfMandarRefran;
import mensaje.MensajeConfRecibirCatedra;
import mensaje.MensajeMandarRefran;
import mensaje.MensajeNoExistente;
import mensaje.MensajePedirProducto;
import mensaje.MensajePedirUsuario;
import mensaje.MensajePrepCS;
import mensaje.MensajePrepSC;
import mensaje.MensajeYaLoTienes;
import mensaje.MensajeYaLogueado;
import usuario.Usuario;

public class OyenteCliente extends Thread{
		private Socket s;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		
		private TablaUsuario tablaUsuario;
		private TablaOut tablaOut;
		private TablaIn tablaIn;
		private lockTicket lock;
		private Almacen almacen;
		private lockTicket lockPrints;
		
		public OyenteCliente(Socket s, TablaUsuario usersTable, TablaIn inTable, TablaOut outTable, lockTicket lock,Almacen almacen, lockTicket l) {
			this.s = s;
			this.tablaUsuario = usersTable;
			this.tablaOut = outTable;
			this.tablaIn = inTable;
			this.lock = lock;
			this.almacen=almacen;
			this.lockPrints=l;
		}
		
		public void run() {
			try {
				this.out = new ObjectOutputStream(s.getOutputStream());
				this.in = new ObjectInputStream(s.getInputStream());
				boolean end = false;
				while(!end) {
					Mensaje m = (Mensaje) in.readObject();
					switch(m.getType()) {
					case CONEXION:
						MensajeConexion me = (MensajeConexion) m;
			   			if(tablaUsuario.yaesta(me.getOrigen())) {
			   				lock.lockear();
							out.writeObject(new MensajeYaLogueado("server", m.getOrigen()));
							out.flush();
							lock.deslockear();

			   			}
			   			else {
			   				Usuario newUser = new Usuario(me.getOrigen(),  me.getArchivos());
				   			
				   			anadirUsuario(newUser, out, in);
				   			lock.lockear();
							out.writeObject(new MensajeConfConexion("server", m.getOrigen()));
							out.flush();
							lock.deslockear();
							lockPrints.lockear();
				   			System.out.println("Se crea la conexión del usuario: " + newUser.getId() + ".");
				   			lockPrints.deslockear();
			   			}
			   		
			   			
						break;
						
					case LISTA_USUARIOS:
						lock.lockear();   			
			   			out.writeObject(new MensajeConfListaUsuarios("server", m.getOrigen(), tablaUsuario.getListaUsuarios(), tablaUsuario.getListaArchivos()));
			   			out.flush();
			   			lock.deslockear(); 
				  		break;
						
						
					case CERRAR_CONEX:
						MensajeCerrarConexion men = (MensajeCerrarConexion) m;
			   			borrarUsuario(men.getOrigen());
			   			lock.lockear(); 
			   			out.writeObject(new MensajeConfCerrarConexion("server", m.getOrigen()));
			   			out.flush();
			   			lock.deslockear(); 
			   			lockPrints.lockear();
			   			System.out.println("Conexión cerrada " + men.getOrigen() + ".");
			   			lockPrints.deslockear();
			   			
			   			end = true;
			   			
				  		break;
						
						
					case PEDIR_PROD:
						MensajePedirProducto mensa = (MensajePedirProducto) m;
			   			String requestedFile = mensa.getArchivoPedido();
			   			String nombreUsuario=m.getOrigen();
			   			if (tablaUsuario.yalotienes(requestedFile,nombreUsuario)) {
		   					lock.lockear();
		   					ObjectOutputStream out3 = tablaOut.getUserOutputStream(nombreUsuario);
			   				out3.writeObject(new MensajeYaLoTienes(m.getOrigen(),m.getOrigen()));
			   				out3.flush();
			   				lock.deslockear();
			   				lockPrints.lockear();
					  		System.out.println("El cliente ya tiene el archivo");
			   				lockPrints.deslockear(); 

		   				}
			   			else {
			   				String name = tablaUsuario.getNombreUsuario(requestedFile);
				   			if(name != null) {
				   				
				   				ObjectOutputStream out2 = tablaOut.getUserOutputStream(name);
				   				
				   				
				   					lock.lockear();
				   					out2.writeObject(new MensajePedirUsuario(m.getOrigen(), name, requestedFile));
				   					out2.flush();
					   				lock.deslockear(); 
					   				tablaUsuario.addArchivo(requestedFile, m.getOrigen());

					   				lockPrints.lockear();
							  		System.out.println(m.getOrigen() + " Pidiendo archivo " + requestedFile + " al usuario " + name);
							  		lockPrints.deslockear();
				   				
				   			}
				   			
				   			else {
				   				lock.lockear(); 
				   				out.writeObject(new MensajeNoExistente("server", m.getOrigen()));
				   				out.flush();
				   				lock.deslockear(); 
				   				
				   				lockPrints.lockear();
				   				System.err.println("No existe el archivo " + mensa.getArchivoPedido() + ".");
				   				lockPrints.deslockear();
				   			}
				   			
			   			}
			   			
						
				  		break;
						
						
					case PREPARADO_C_S:
						MensajePrepCS mens = (MensajePrepCS) m;	
						
		   				lock.lockear(); 

			   			ObjectOutputStream out2 = tablaOut.getUserOutputStream(m.getDestino());
			   			
			   			out2.writeObject(new MensajePrepSC(m.getOrigen(), m.getDestino(), mens.getPuerto()));
			   			out2.flush();
		   				lock.deslockear(); 

			   			break;
			   			
					case RECIBIR_CATEDRA:
						lock.lockear(); 
						Producto producto=almacen.extraer();
			   			out.writeObject(new MensajeConfRecibirCatedra("server", m.getOrigen(), producto.getProducto()));
			   			out.flush();
			   			lock.deslockear(); 
						
						
						break;
					case MANDAR_REFRAN:
						MensajeMandarRefran mensajeee= (MensajeMandarRefran)m;
						String aux= mensajeee.getRefran();
						Producto pro= new Producto(aux);
						almacen.almacenar(pro);
						lock.lockear();
						out.writeObject(new MensajeConfMandarRefran("server", m.getOrigen()));
			   			out.flush();
						lock.deslockear();

			  		
			   		default:
			   			break;
					
					
					}
				}
				
			} catch(IOException | ClassNotFoundException | InterruptedException e) {
				lockPrints.lockear();
				System.err.println("Ocurrió un error en el thread CLIENT LISTENER.");
				lockPrints.deslockear();
			}
		}
		
		public void anadirUsuario(Usuario u, ObjectOutputStream out, ObjectInputStream in) throws InterruptedException {

			tablaUsuario.addUsuario(u);;
			tablaOut.put(u.getId(), out);
			tablaIn.put(u.getId(), in);
			
	    }
	    
	    public void borrarUsuario(String u) throws InterruptedException {
	    	tablaUsuario.borrarUsuario(u);;
	    	tablaOut.remove(u, out);
	    	tablaIn.remove(u, in);
			
		}
		
		
}

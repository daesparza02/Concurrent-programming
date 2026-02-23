package cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public class Archivo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nombreArchivo="";
	private String contenido="";
	private byte[] archivo;
	
	public Archivo(String archivo, String ubi) {
		this.nombreArchivo=archivo;
		
		File file = new File(ubi);
		
		try(FileInputStream fis = new FileInputStream(file)){
			this.archivo=fis.readAllBytes();
		}catch(IOException e) {
			System.err.println("NO SE PUDO ABRIR EL ARCHIVO " + archivo);
		}
		
		try(BufferedReader buf = new BufferedReader(new FileReader(ubi))) {
			String aux;
			while((aux=buf.readLine()) != null) {
				this.contenido+=aux+"\n";
			}
			
			
		} catch(IOException e) {
			System.err.println("NO SE PUDO ABRIR EL ARCHIVO " + archivo);
		}
	}
	
	public String getArchivo() {
		return this.nombreArchivo;
	}
	
	public String getContenido() {
		return this.contenido;
	}
	public void print() {
		System.out.print(contenido);
	}
	
	public byte[] getBytes() {
		return this.archivo;
	}

}

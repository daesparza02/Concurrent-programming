package servidor;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class AlmacenK implements Almacen{

	private Semaphore empty;//número de posiciones vacías (cuantos productores pueden entrar)
	private Semaphore full=new Semaphore(0);//número de posiciones llenas (cuantos consumidores pueden entrar)
	private Semaphore mutexD=new Semaphore(1);
	private Semaphore mutexF=new Semaphore(1);
	private volatile int front=0;//último elemento consumido
	private volatile int rear=0;//último elemento producido
	private Producto prod;
	private int K;
	private volatile ArrayList<Producto> carrito;
	
	public AlmacenK() {
		this.K=10;
		this.carrito=new ArrayList<Producto>(this.K);
		carrito.add(prod);
		this.empty=new Semaphore(this.K);
	}
	 
	@Override
	public void almacenar(Producto producto) {
		try {
			empty.acquire();
			mutexD.acquire();
			carrito.add(rear, producto);
			rear=(rear+1)%this.K;
			mutexD.release();
			full.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Producto extraer() {
		try {
			full.acquire();
			mutexF.acquire();
			this.prod=carrito.get(front);
			front=(front+1)%this.K;
			mutexF.release();
			empty.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.prod;
	}

	

}

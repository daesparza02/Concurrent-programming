package cliente;

public class Entero {
	public volatile int n = 0;
	
	public int getEntero() {
		return this.n;
	}
	
	public void incrEntero(int N) {
		this.n+=N;
	}
	public void setEntero(int N) {
		this.n=N;
	}
}

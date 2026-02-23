package servidor;


import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class PasoTestigo {
	private Semaphore e;
	private Semaphore r;
	private Semaphore w;
	private volatile int nr;
	private volatile int nw;
	private volatile int dr;
	private volatile int dw;
	
	public PasoTestigo() {
		this.e=new Semaphore(1);
		this.r=new Semaphore(0);
		this.w=new Semaphore(0);
		this.nr=0;
		this.nw=0;
		this.dr=0;
		this.dw=0;
	}
	
	public void requestEscribir() {
		while(!e.tryAcquire()) {}
		if(nr>0 || nw>0) {
			dw=dw+1;
			e.release();
			while(!w.tryAcquire()) {}
		}
		nw=nw+1;
		e.release();
	}
	
	public void releaseEscribir() {
		while(!e.tryAcquire()) {}
		nw=nw-1;
		if(dr>0) {
			dr=dr-1;
			r.release();
		}
		else if(dw>0) {
			dw=dw-1;
			w.release();
		}
		else e.release();
	}
	
	public void requestLeer() {
		while(!e.tryAcquire()) {}
		if(nw>0) {
			dr=dr+1;
			e.release();
			while(!r.tryAcquire()) {}
		}
		nr=nr+1;
		if(dr>0) {
			dr=dr-1;
			r.release();
		}
		else e.release();
	}
	
	public void releaseLeer() {
		while(!e.tryAcquire()) {}
		nr=nr-1;
		if(nr==0 && dw>0) {
			dw=dw-1;
			w.release();
		}
		else e.release();
	}
}


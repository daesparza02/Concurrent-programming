package servidor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorServidor {
		private int numLectores, numEscritores;
		private final Lock lock = new ReentrantLock(true);
	    private final Condition colalectores = lock.newCondition();
	    private final Condition colaescritores = lock.newCondition();
	   
	    public MonitorServidor() {
	    this.numLectores = 0;
	    this.numEscritores = 0;
	    }
	   
	    public void requestLeer() throws InterruptedException{
		    lock.lock();
		   
		    while(numEscritores > 0) {
		    	colalectores.wait();
		    }
		   
		    numLectores+=1;
		    lock.unlock();
	    }
	   
	    protected void releaseLeer() {
		    lock.lock();
		   
		    numLectores--;
		   
		    if(numLectores == 0) {
		    	colaescritores.signal();
		    }
		   
		    lock.unlock();
	    }
	   
	    protected void requestEscribir() throws InterruptedException {
		    lock.lock();
		   
		    while(numLectores > 0 || numEscritores > 0) {
		    	colaescritores.wait();
		    }
		   
		    numEscritores++;
		    lock.unlock();
	    }
	   
	    protected void releaseEscribir() {
		    lock.lock();
		   
		    numEscritores--;
		   
		    colalectores.signal();
		    colaescritores.signal();
	
		    lock.unlock();
	    }
	   
	   
	   
}

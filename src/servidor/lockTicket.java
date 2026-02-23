package servidor;


public class lockTicket implements lock {
	private int M;
	private volatile int num;
	private volatile int next;
	public lockTicket(int M) {
		this.M = M;
		num = 1;
		next = 1;
	}
	@Override
	public void lockear() {
		int turn = FA(num, 1);
		if(turn == M){ 
			FA(num, -M); 
		} 
		else if (turn > M){ 
			turn -= M; 
		}
		while(turn != next);
	}
	
	@Override
	public void deslockear() {
		next = (next % M) + 1;		
	}
	
	private int FA(int number, int incr) {
		int tmp = number; 
		this.num = number + incr; 
		return tmp; 
	}
}

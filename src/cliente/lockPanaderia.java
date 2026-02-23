package cliente;
import java.util.ArrayList;

public class lockPanaderia implements lock {
	private int M;
	private ArrayList<Entero> turn = new ArrayList<Entero>();
	public lockPanaderia(int M) {
		this.M = M;
		for(int i = 0; i < this.M ; i++) {
			turn.add(new Entero());
		}
	}
	@Override
	public void lockear(int i) {
		turn.get(i).setEntero(1);
		int maxi=0;
		for (int j=0; j<M; j++) {
			if(turn.get(j).getEntero()>maxi) {
				maxi=turn.get(j).getEntero();
			}
			
		}
		turn.get(i).setEntero(maxi+1);
		for(int j=0; j<M; j++) {
			if(j!=i) {
				while(turn.get(j).getEntero()!=0 && mayor(turn.get(i).getEntero(),i,turn.get(j).getEntero(),j)) {
					
				}
			}
			
			
			
		}
	}
	private boolean mayor(int v1,int i, int v2, int j) {
		return(v1>v2||(v1==v2 && i>j));
	}
	@Override
	public void deslockear(int i) {
		turn.get(i).setEntero(0);
	}
	
}
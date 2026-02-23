package cliente;

import java.util.ArrayList;

public class lockRompeEmpate implements lock {
    private int M;
    private ArrayList<Entero> in = new ArrayList<Entero>();
    private ArrayList<Entero> last = new ArrayList<Entero>();

    public lockRompeEmpate(int M) {
        this.M = M;
        for (int i = 0; i < this.M; i++) {
            in.add(new Entero());
            last.add(new Entero());
            in.get(i).setEntero(-1); 
        }
    }

    @Override
    public void lockear(int i) {
        for (int j = 0; j < M; j++) {
            in.get(i).setEntero(j);
            last.get(j).setEntero(i);

            for (int k = 0; k < M; k++) {
                if (k == i) {
                	
                }
                else {
                	while (in.get(k).getEntero() >= j && last.get(j).getEntero() == i) {
                    }
                }
                
            }
        }
    }

    @Override
    public void deslockear(int i) {
        in.get(i).setEntero(-1);
    }
}

package Client;

import java.io.Serializable;
import java.util.ArrayList;

// a genetic pair designed to save a string(object) and an arraylist of objects
// for this case, it saves a string that represents the information of business plans and a list of clients that currently viewing the plan
public class ViewingPlanPair<A,B>  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final A a;
    public final ArrayList<B> arrayb;

    public ViewingPlanPair(A a) {
        this.a = a;
        this.arrayb = new ArrayList<B>();
    }
    
    public void addB(B b) {
    	this.arrayb.add(b);
    }
    
    public void removeB(B b) {
    	for (int i =0; i<this.arrayb.size(); i++) {
    		if(this.arrayb.get(i).equals(b)) {
    			this.arrayb.remove(i);
    		}
    	}
    }

	public A getA() {
		return a;
	}

	public ArrayList<B> getArrayb() {
		return arrayb;
	}
	

	
    

};
//Geunuk Na, 111000447, geunuk.na@stonybrook.edu

package hw214_1;

//Integer modulo m (m is a prime number)
// IntMod(7, 5) is 7 in modulo 5 system
// IntMod(7, 5) is equivalent to IntMod(2, 5)
//
public class IntMod implements Field, Ordered {
    private int n, m;
    
    public IntMod(int n, int m) {
        if(m <= 0)
            throw new IllegalArgumentException("Not a positive divisor");
        n = n % m;
        n = n < 0 ? n + m : n; //if n is negative, make it positive
        this.n = n;
        this.m = m;
    }
    public int getInt() {
        return n;
    }
    public int getMod() {
        return m;
    }
    
    
    //Field
    public Ring mulIdentity() {
    	return new IntMod(1,m);
    }
    public Ring mulInverse() throws ArithmeticException{
    	for(int i=0;i<m;i++) {
    		if((this.n*i)%this.m==1) {
    			return new IntMod(i,m);
    		}
    	}
    	throw new ArithmeticException("Not Found.");
    }
    
    public Ring add(Ring a) {
    	IntMod mod = (IntMod) a;
    	if(this.m==mod.m) {
    		return new IntMod(this.n+mod.n,this.m);
    	}
    	else {
    		throw new IllegalArgumentException("Different m.");
    	}
    }
    public Ring addIdentity() {
    	return new IntMod(0,m);
    }
    public Ring addInverse() {
    	return new IntMod(-n,m);
    }
    public Ring mul(Ring a) {
    	IntMod mod = (IntMod) a;
    	if(this.m==mod.m) {
    		return new IntMod(this.n* mod.n, this.m);
    	}
    	else {
    		throw new IllegalArgumentException("Different m.");
    	}
    }
    
    
    //Ordered
    public boolean ge(Ordered a) {
    	IntMod mod = (IntMod) a;
    	if(this.m==mod.m) {
    		return (this.n%m)>=(mod.n%m);
    	}
    	else {
    		throw new IllegalArgumentException("Different m.");
    	}
    }
}

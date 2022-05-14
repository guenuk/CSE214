//Geunuk Na, 111000447, geunuk.na@stonybrook.edu

package hw214_2;

public class Euclidean {
    protected static Ring euclidean(Ring a, Ring b) {
    	if (Comp.eq(a, b)){
    		return a;
    	}
    	else if (Comp.eq(a, a.addIdentity())) {
    		return b;
    	}
    	else if (Comp.eq(b, b.addIdentity())) {
    		return a;
    	}
    	else {
    		if(Comp.ge(a, b)) {
        		return euclidean(((Modulo) a).mod(b),b); 
    		}
    		else {
    			return euclidean(((Modulo) b).mod(a),a); 
    		}
    	}
    }
    public static Ring GCD(Ring a, Ring b) {
        if(Comp.lt(a, a.addIdentity()))     //if a < 0
            a = a.addInverse();
        if(Comp.lt(b, b.addIdentity()))     //if b < 0
            b = b.addInverse();
        return euclidean(a, b);
    }
    
    public static Ring LCM(Ring a, Ring b) {
    	// What is this for??
        Ring gcd = GCD(a, b);
        Ring q = ((Modulo)b).quo(gcd);
        return a.mul(q);
    }
}

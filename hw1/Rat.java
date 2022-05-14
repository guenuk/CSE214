//Geunuk Na, 111000447, geunuk.na@stonybrook.edu

package hw214_1;

//Rational number
// Rat(10, 15) is 10/15 and is equivalent to Rat(2, 3)
//
public class Rat implements Field, Modulo, Ordered {
	
	private int n;
	private int d;
	
	
    public Rat(int numerator, int denumerator) {
        //TODO: make numerator and denominator prime to
        //each other using Euclidean.gcd
    	if (denumerator==0) {
    		throw new IllegalArgumentException("Denumerator cannot be 0");
    	}
    	
    	Int num = new Int(numerator);
    	Int denum = new Int(denumerator);
    	
    	int x = ((Int)(Euclidean.GCD(num, denum))).getInt();
    	
    	this.n = numerator/x;
    	this.d = denumerator/x;
    }
    
    public Ring mulIdentity() {
    	return new Rat(1,1); 
    }
    public Ring mulInverse() throws ArithmeticException{
    	if(n==0) {
    		throw new ArithmeticException("Dividing by zero");
    	}
    	return new Rat(this.d,this.n); 
    }
    
    public Ring add(Ring a) {
    	Rat r = (Rat) a;
    	return new Rat(this.d * r.n + r.d*this.n , this.d*r.d);
    }
    
    public Ring addIdentity() {
    	return new Rat(0,1);
    }
    
    public Ring addInverse() {
    	return new Rat(-1*this.n, 1*this.d); //???
    }
    
    public Ring mul(Ring a) {
    	Rat r = (Rat) a;
    	return new Rat(this.n*r.n, this.d*r.d);
    }
    
    //Modulo
    public Ring mod(Ring a) {
        Rat r = (Rat)a;
        if(r.n == 0)
            throw new ArithmeticException("Division by zero");
        return new Rat((n*r.d) % (d*r.n), d*r.d);
    }
    
    public Ring quo(Ring a) {
    	return new Rat(this.n*((Rat) a).d, this.d*((Rat) a).n);
    }
    
    //Ordered
    public boolean ge(Ordered a) {
        Rat r = (Rat)a;
        return n*r.d >= d*r.n;
    }
}

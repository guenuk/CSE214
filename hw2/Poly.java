//Geunuk Na, 111000447, Geunuk.na@stonybrook.edu
package hw214_2;


@SuppressWarnings("unchecked")
public class Poly<F extends Field> implements Ring, Modulo, Ordered { //extends Field?? WHY? field is not a class
    private F[] coef; 
    public Poly(F[] coef) { 
        //trim off th e leading zeros
        int n = coef.length;
        while(n >= 2 && Comp.eq(coef[n-1], coef[0].addIdentity()))
            n--;
        this.coef = (F[])new Field[n]; //creating a generic type array // Field[]  ??
        for(int i = 0; i < n; i++)
            this.coef[i] = coef[i];
            
    }    
    public F[] getCoef() {
        return coef;
    }    
    
    public Ring add(Ring a) {
    	Poly<F> that = (Poly<F>) a;
        F[] c = (F[])new Field[Math.max(this.coef.length, that.coef.length)];
        for(int i=0; i<c.length; i++) {
        	c[i] = (F) this.coef[0].addIdentity();
        }
        for(int i = 0; i < this.coef.length; i++)
            c[i] = this.coef[i];
        for(int i = 0; i < that.coef.length; i++)
            c[i] = (F)c[i].add(that.coef[i]);
        return new Poly<F>(c);
    }
    public Ring addIdentity() {
    	F[] d= (F[])new Field[this.coef.length];
    	for(int i=0;i<d.length;i++) {
    		d[i]= (F) this.coef[i].addIdentity();
    	}
    	return new Poly<F>(d);
    }
    public Ring addInverse() {
    	F[] d= (F[])new Field[this.coef.length];
    	for(int i=0; i<d.length; i++) {
        	d[i] = (F) this.coef[0].addIdentity();
        }
    	
    	for(int i=0;i<d.length;i++) {
    		d[i] = (F) d[i].add((F) this.coef[i].addInverse());
    	}
    	return new Poly<F>(d);
    }
    public Ring mul(Ring a) {
    	Poly<F> that = (Poly<F>) a;
        F[] c = (F[]) new Field[this.coef.length + that.coef.length - 1];
    	for(int i=0; i<c.length; i++) {
        	c[i] = (F) this.coef[0].addIdentity();
        }
        for(int i = 0; i < this.coef.length; i++)
            for(int j = 0; j < that.coef.length; j++)
                c[i+j] = (F) c[i+j].add(this.coef[i].mul(that.coef[j]));
        return new Poly<F>(c);
    }
    
    public Ring mod(Ring a) {
    	return longdiv(a)[1];
    }
    public Ring quo(Ring a) {
    	return longdiv(a)[0];
    }
    public Poly<F>[] longdiv(Ring a) {
    	Poly<F> that = (Poly<F>) a;
        F[] quo = (F[]) new Field[this.coef.length - that.coef.length + 1]; //quotient
        F[] num = (F[]) new Field[this.coef.length];  //numerator, remainder
        F[] den = that.coef;   //denominator
        int dd = den.length - 1;    //degree of denominator
        
        //copy this.coef to num, because num will be modified
        for(int i = 0; i < this.coef.length; i++)
            num[i] = this.coef[i];
        
        //the long division algorithm
        //num -> quo * den + num
        for(int qi = quo.length-1; qi >= 0; qi--) {
            quo[qi] = (F) ((Field) num[qi + dd]).mul(den[dd].mulInverse());
            for(int i = 0; i < dd; i++)
                num[qi+i] = (F)num[qi+i].add(quo[qi].mul(den[i]).addInverse());
            num[qi+dd] = (F)num[qi+dd].addIdentity();
        }
        return new Poly[] {new Poly<F>(quo), new Poly<F>(num)};
    }
    public boolean ge(Ordered a) {
    	Poly<F> that = (Poly<F>) a;
    	if (this.coef.length==that.coef.length) {
    		int n = that.coef.length;
    		while(Comp.eq(this.coef[n-1],that.coef[n-1])&&n>0) {
    			n--;
    			if(n==0) {
    				return Comp.ge(this.coef[n],that.coef[n]);
    			}
    		}
    		return Comp.ge(this.coef[n-1],that.coef[n-1]);
    	}
    	else {
    		return this.coef.length>that.coef.length;
    	}
    }
  
}

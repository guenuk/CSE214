//Geunuk Na, 111000447, Geunuk.na@stonybrook.edu
package hw214_2;

public class PolyDbl implements Ring, Modulo, Ordered {
    private double[] coef;
    public PolyDbl(double[] coef) {
        //trim the leading zeros
        int n = coef.length;
        while(n-1 >= 1 && coef[n-1] == 0)
            n--;
        
        this.coef = new double[n];
        for(int i = 0; i < n; i++)
            this.coef[i] = coef[i];
        
        //TODO: implement this constructor
        //      leading zeros should be trimmed off
    }
    public double[] getCoef() {
        return coef;
    }    
    //TODO: implement the rest
    public PolyDbl add(Ring a) {
    	PolyDbl that = (PolyDbl) a;
        double[] c = new double[Math.max(this.coef.length, that.coef.length)];
        //copy this
        for(int i = 0; i < this.coef.length; i++)
            c[i] = this.coef[i];
        //add that
        for(int i = 0; i < that.coef.length; i++)
            c[i] += that.coef[i];
        return new PolyDbl(c);
    }

    public Ring addIdentity() {
    	double[] d= new double[this.coef.length];
    	for(int i=0;i<d.length;i++) {
    		d[i]=0.0;
    	}
    	return new PolyDbl(d);
    }
    public Ring addInverse() {
    	double[] d= new double[this.coef.length];
    	for(int i=0;i<d.length;i++) {
    		d[i] = this.coef[i]*-1.0;
    	}
    	return new PolyDbl(d);
    }
    
    public PolyDbl mul(Ring a) {
    	PolyDbl that = (PolyDbl) a;
        double[] c = new double[this.coef.length + that.coef.length - 1];
        for(int i = 0; i < this.coef.length; i++)
            for(int j = 0; j < that.coef.length; j++)
                c[i+j] += this.coef[i] * that.coef[j];
        return new PolyDbl(c);
    }
    

    public Ring mod(Ring a) {
    	return longdiv(a)[1];
    }
    public Ring quo(Ring a) {
    	return longdiv(a)[0];
    }
    
    
    public PolyDbl[] longdiv(Ring a) {
    	PolyDbl that = (PolyDbl) a;
        //return value: longdiv(...)[0]: quotient,
        //              longdiv(...)[1]: remainder
        double[] quo = new double[this.coef.length - that.coef.length + 1]; //quotient
        double[] num = new double[this.coef.length];  //numerator, remainder
        double[] den = that.coef;   //denominator
        int dd = den.length - 1;    //degree of denominator
        
        //copy this.coef to num, because num will be modified
        for(int i = 0; i < this.coef.length; i++)
            num[i] = this.coef[i];
        
        //the long division algorithm
        //num -> quo * den + num
        for(int qi = quo.length-1; qi >= 0; qi--) {
            quo[qi] = num[qi + dd] / den[dd];
            for(int i = 0; i < dd; i++)
                num[qi+i] = num[qi+i] - quo[qi] * den[i];
            num[qi+dd] = 0;
        }
        return new PolyDbl[] {new PolyDbl(quo), new PolyDbl(num)};
    }
    
    public boolean ge(Ordered a) {
    	PolyDbl that = (PolyDbl) a;
    	if (this.coef.length==that.coef.length) {
    		int n = that.coef.length;
    		while(this.coef[n-1]==that.coef[n-1]&&n>0) {
    			n--;
    			if(n==0) {
    				return this.coef[n]>=that.coef[n];
    			}
    		}
    		return this.coef[n-1]>=that.coef[n-1];
    	}
    	else {
    		return this.coef.length>that.coef.length;
    	}
    }
    
}

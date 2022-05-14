//Geunuk Na, geunuk.na@stonybrook.edu
package hw214_6;

public class Lambda {
    public static final double EPS = 1e-10;

    //Interfaces
    public interface Func<T, R> {       //function
        public R apply(T a);
    }
    public interface Rec<T, R> {        //recursive function
        public R func(Rec<T, R> self, T a);
        public default R apply(T a) { return func(this, a); }
    }
    public interface Rec2<T1, T2, R> {  //recursive function 2
        public R func(Rec2<T1, T2, R> self, T1 a, T2 b);
        public default R apply(T1 a, T2 b) { return func(this, a, b); }
    }
    
    public interface Bisection {
        public double func(double x);
        public default double solve(double xl, double xr) {
            /*TODO: implement this function recursively
              repeat until |xl � xr| < EPS*/
        	double m = (xl+xr)/2;
        	
        	if(Math.abs(xl-xr)<EPS)
        		return xl;
        	
        	else if(func(xl)*func(m)<=0)
        		return solve(xl,m);
        	
        	else if(func(xr)*func(m)<=0)
        		return solve(m,xr);
        	
        	else {
        		throw new RuntimeException("Error");
        	}
        	
        }
    }
    
    
    public interface Newton {
        public double func(double x);
        public default double solve(double x0) {
            return fixedPoint.apply(toNewton.apply((Func<Double, Double>)x -> func(x)), x0);
        }
    }

    //Factorial
    public static Rec<Integer, Integer> fact =
            (self, a) -> a <= 1 ? a : a * self.apply(a - 1);
            
    //Derivative
    public static Func<Func<Double, Double>, Func<Double, Double>> derivative = 
            f -> x -> (f.apply(x + EPS) - f.apply(x))/EPS;
        
    //toNewton          
    public static Func<Func<Double, Double>, Func<Double, Double>> toNewton = 
            f -> x -> x-f.apply(x) / derivative.apply(f).apply(x);  /*TODO: implement this function*/
    
    //Fixed point            
    public static Rec2<Func<Double, Double>, Double, Double> fixedPoint = 
    		(self, f, x0) -> (Math.abs(f.apply(x0)-x0)<=EPS) ? f.apply(x0):self.apply(f,f.apply(x0));/*TODO: implement this function*/
                
    public static Rec<Func<Double, Double>, Func<Double, Double>> fixedPoint2 =
    		(self, f) -> x0 -> (Math.abs(f.apply(x0)-x0)<EPS) ? f.apply(x0):self.apply(f).apply(f.apply(x0));  /*TODO: implement this function*/ 

    //Sqrt                
    public static Func<Double, Double> sqrt =
            x -> ((Newton) y -> y * y - x).solve(1.0);    
    
    public static void main(String[] args) {
        //Finding a root
        Bisection b = x -> x*x + 4*x - 8;
        System.out.println("ans: " + b.solve(-10,  10));

        //Recursion
        System.out.println("ans: " + fact.apply(5));
        
        //Fixed point
        Func<Double, Double> cx = x -> Math.cos(x);
        System.out.println("ans: " + fixedPoint.apply(cx, 1.0));
        System.out.println("ans: " + fixedPoint2.apply(cx).apply(1.0));

        //Newton's method
        Newton n = x -> x*x + 4*x - 8;
        System.out.println("ans: " + n.solve(-10));
        System.out.println("ans: " + sqrt.apply(2.0));
    }
} 

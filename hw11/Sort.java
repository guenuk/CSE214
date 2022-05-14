//Geunuk Na, geunuk.na@stonybrook.edu
package hw214_11;

public class Sort {
    //TODO
    //Submit your course evaluation at
    //https://stonybrook.campuslabs.com/courseeval/
    //
    protected static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
    protected static String toString(int[] a) {
        StringBuilder sb = new StringBuilder();
        for(Integer e : a)
            sb.append(e + ", ");
        return sb.toString();
    }    
    protected static int checkBit(int a, int shift) {
        //return the shift-th bit of a
        return (a & (1 << shift)) != 0 ? 1 : 0; 
    }
    public static void radixSort1(int[] a, int l, int r, int b) {
        //TODO
        //return if l >= r or b < 0
        //repeat
        //  from l find the number whose b-th bit is 1
        //  from r find the number whose b-th bit is 0
        //  swap the two numbers
        //until the indexes of the numbers meet
        //recursively call radixSort1 with b-1 and new l, r
    	if(l>=r || b<0)
    		return;
    	int i = l;
    	int j = r;
    	while(true) {
    		while(checkBit(a[i],b)==0 && i<r)
    			i++;
    		while(checkBit(a[j],b)==1 && i<=j)
    			j--;
    		if(i>=j)
    			break;
    		swap(a,i,j);
    	}
    	if((i==r&&j==r)||(i==l&&j==l))
    		radixSort1(a,l,r,b-1);
    	radixSort1(a,l,i-1,b-1);
    	radixSort1(a,i,r,b-1);
    	
    }
    public static void radixSort1(int[] a) {
        radixSort1(a, 0, a.length-1, 31);
    }
    public static void radixSort2(int[] a) {
        int[] b = new int[a.length];
        //TODO
        //foreach i in [0, 31]
        //  k = 0;
        //  foreach j in [0, a.length)
        //      copy a[j] to b[k++] if a[j]'s i-th bit is 0
        //  foreach j in [0, a.length)
        //      copy a[j] to b[k++] if a[j]'s i-th bit is 1
        //  copy b to a
        for(int i=0; i<32; i++) {
        	int k=0;
        	for(int j=0;j<a.length; j++) {
        		if(checkBit(a[j],i)==0)
        			b[k++] = a[j];
        	}
        	for(int j=0; j<a.length; j++) {
        		if(checkBit(a[j],i)==1)
        			b[k++] = a[j];
        	}
        	for(int j=0; j<a.length; j++)
        		a[j] = b[j];
        }
    }
    
    public static void main(String[] args) {
        int[] a = new int[] {3, 1, 2, 4, 5, 7, 9, 0, 8, 6};
        radixSort1(a);
        System.out.println("Radix sort 1: " + toString(a));

        a = new int[] {3, 1, 2, 4, 5, 7, 9, 0, 8, 6};
        radixSort2(a);
        System.out.println("Radix sort 2: " + toString(a));
    }
}

//Geunuk Na, geunuk.na@stonybrook.edu 111000447
package hw214_3;

public class SetImpl<E extends Comparable<E>> implements Set<E> {
    private CircularlyDblLinkedList<E> list;
    
    public SetImpl() {
        list = new CircularlyDblLinkedList<E>();
    }
    public SetImpl(SetImpl<E> set) {
        list = set.copyList();
        dedupe();
    }
    public SetImpl(E[] arr) {
        list = new CircularlyDblLinkedList<E>();
        for(int i = 0; i < arr.length; i++)
            list.add(i, arr[i]);
        dedupe();
    }
    //TODO: implement interface Set
    public boolean isEqual(Set<E> set) {
        SetImpl<E> s = (SetImpl<E>)set;
        if(list.size() != s.list.size())
            return false;
        for(int i = 0; i < list.size(); i++)
            if(list.get(i).compareTo(s.list.get(i)) != 0)
                return false;
        return true;
    }
    public Set<E> union(Set<E> set){
    	SetImpl<E> s = (SetImpl<E>)set;
    	SetImpl<E> newSet = new SetImpl<E>(s);
    	for(int i=0; i<list.size;i++)
    		newSet.list.add(0, list.get(i));
    	newSet.dedupe();
    	return newSet;
    }
    public Set<E> intersection(Set<E> set){
    	SetImpl<E> s = (SetImpl<E>) set;
    	SetImpl<E> newSet = new SetImpl<E>();
    	for(int i=0; i<list.size; i++)
    		for(int j=0; j<s.list.size; j++)
    			if(list.get(i).compareTo(s.list.get(j))==0)
    				newSet.list.add(0, list.get(i));
    	newSet.dedupe();
    	return newSet;
    }
    public Set<E> difference(Set<E> set){
    	SetImpl<E> s = (SetImpl<E>) set;
    	SetImpl<E> union = new SetImpl<E>((SetImpl<E>)this.union(s));
    	SetImpl<E> intersection = new SetImpl<E>((SetImpl<E>)this.intersection(s));
    	for(int i=0; i<union.list.size;i++)
    		for(int j=0; j<intersection.list.size; j++)
    			if(union.list.get(i).compareTo(intersection.list.get(j))==0)
    				union.list.remove(i);
    	return union;
    }
    
    //helper methods
    private CircularlyDblLinkedList<E> copyList() {
        CircularlyDblLinkedList<E> dst = new CircularlyDblLinkedList<E>();
        int i = 0;
        for(E e : list)
            dst.add(i++, e);
        return dst;
    }
    private void dedupe() {
        //TODO: 1) sort-
        //      2) remove any consecutive duplicate elements
    	sort();
    	for(int i=0; i<list.size;i++)
    		for(int j=i+1; j<list.size; j++)
    			if(list.get(i).compareTo(list.get(j))==0) {
    				list.remove((j));
    				j--;
    			}
    }
    
    private void sort() {   //selection-sort
        //TODO: using the selection-sort, sort list
    	for(int i = 0; i < list.size ; i++) {
    		for (int j = i+1; j <list.size; j++) {
    			E temp = list.get(j);
    			if(list.get(i).compareTo(list.get(j)) > 0){
    	    		E temp2 = list.get(i);
    				list.set(i, temp);
    				list.set(j, temp2);
    			}
    		}
    	}
    }
    private static void onFalseThrow(boolean b) {
        if(!b)

        	throw new RuntimeException("Error: unexpected");            
    }
    public static void main(String[] args) {
        SetImpl<Integer> a = new SetImpl<Integer>(new Integer[] {1});
        SetImpl<Integer> b = new SetImpl<Integer>(new Integer[] {1, 2});
        SetImpl<Integer> c = new SetImpl<Integer>(new Integer[] {2, 3});
        SetImpl<Integer> e = new SetImpl<Integer>();
        SetImpl<Integer> x = new SetImpl<Integer>(new Integer[] {1, 1, 1});
        SetImpl<Integer> y = new SetImpl<Integer>(new Integer[] {1, 2, 1, 2});
        SetImpl<Integer> z = new SetImpl<Integer>(new Integer[] {3, 2, 3, 3});
        SetImpl<Integer> u = new SetImpl<Integer>(new Integer[] {1, 2, 3});
        
        
        onFalseThrow(x.isEqual(a)); 
        onFalseThrow(y.isEqual(b)); 
        onFalseThrow(z.isEqual(c));
        onFalseThrow(y.union(z).isEqual(u));
        onFalseThrow(x.intersection(y).isEqual(x));
        onFalseThrow(x.intersection(z).isEqual(e));
        onFalseThrow(u.difference(z).isEqual(x));
        System.out.println("Success!");
    }     
}

//Geunuk Na, geunuk.na@stonybrook.edu 111000447
package hw214_3;

//a = Subset({1,2}, {1,2,3,4}), b = Subset({2,3}, {1,2,3,4});
//a and b = Subset({2},     {1,2,3,4})
//a or  b = Subset({1,2,3}, {1,2,3,4})
//not a   = Subset({3,4},   {1,2,3,4})

public class Subset<E extends Comparable<E>> implements BooleanAlgebra {
    private Set<E> subset;
    private Set<E> univ;
    
    public Subset(Set<E> subset, Set<E> univ) {
        this.subset = subset.union(new SetImpl<E>());
        this.univ = univ.union(new SetImpl<E>());
    }
    
    //interface BooleanAlgebra
    public BooleanAlgebra or(BooleanAlgebra a) {
    	Subset<E> s = castOrThrow(a);
    	SetImpl<E> union = (SetImpl<E>)this.subset.union(s.subset);
    	return new Subset<E>(union,this.univ);
        //TODO: return the union as a Subset
    }
    public BooleanAlgebra and(BooleanAlgebra a) {
        //TODO: return the intersection as a Subset
    	Subset<E> s = castOrThrow(a);
    	SetImpl<E> intersection = (SetImpl<E>)this.subset.intersection(s.subset);
    	return new Subset<E>(intersection, this.univ);
    }
    public BooleanAlgebra not() {
        //TODO: return univ - subset as a Subset
    	SetImpl<E> difference = (SetImpl<E>)this.univ.difference(this.subset);
    	return new Subset<E>(difference, this.univ);
    }
    public BooleanAlgebra orIdentity() {
        //TODO: return the empty set as a Subset
    	return new Subset<E>(new SetImpl<E>(),this.univ);
    }
    public BooleanAlgebra andIdentity() {
        //TODO: return univ as a Subset
    	return new Subset<E>(this.univ, this.univ);
    }
    public boolean isEqual(BooleanAlgebra a) {
        Subset<E> s = castOrThrow(a);
        return subset.isEqual(s.subset) && univ.isEqual(s.univ);
    }

    @SuppressWarnings("unchecked")
    private Subset<E> castOrThrow(BooleanAlgebra a) {
        Subset<E> s = (Subset<E>)a;
        if(!univ.isEqual(s.univ))
            throw new IllegalArgumentException("Unmatched univere");
        return s;
    }
}

//Geunuk Na, geunuk.na@stonybrook.edu
package hw214_9;

public class RBTreeQueue<E extends Comparable<E>> implements Queue<E> {
    private RBTree<E> rbTree;
    
    public RBTreeQueue() {
        rbTree = new RBTree<E>();
    }
    public int size() {
        //TODO: implement this method
    	return rbTree.size();
    }
    public boolean isEmpty() {
        //TODO: implement this method
    	return rbTree.isEmpty();
    }
    public void enqueue(E e) {
        //TODO: implement this method
    	rbTree.insert(e);
    }
    public E dequeue() {
        //TODO: implement this method
    	return rbTree.delete(first());
    }
    public E first() {
        //TODO: implement this method
    	return rbTree.root().e;
    }
}

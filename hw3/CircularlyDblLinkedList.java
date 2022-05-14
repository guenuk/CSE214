//Geunuk Na, geunuk.na@stonybrook.edu 111000447
package hw214_3;

import java.util.Iterator;

public class CircularlyDblLinkedList<E> implements List<E>, Iterable<E> {
    protected static class Node<E> {
        public E e;
        public Node<E> prev, next;
        public Node() { 
            this.e = null; this.prev = this; this.next = this;
        }
        public Node(E e, Node<E> prev, Node<E> next) {
            this.e = e; this.prev = prev; this.next = next;
        }
    }
    public static class NodeIterator<E> implements Iterator<E> {
        private Node<E> head, curr;
        public NodeIterator(Node<E> head) {
            this.head = head; this.curr = head;
        }
        public boolean hasNext() {
        	return curr.next != head;
        }
        
        public E next() {
        	curr = curr.next;
        	return curr.e;
        }
        //TODO: implement Iterator<E> ???
    }
    
    protected Node<E> head;
    protected int size;

    //constructor
    public CircularlyDblLinkedList() {
        head = new Node<E>();
        size = 0;
    }
    
    //TODO: implement interface List
    public E get(int i) {
        return findNode(i).e;
    }
    public int size() {
    	return size;
    }
    public boolean isEmpty() {
    	return size==0;
    }
    public E set(int i, E e) throws IndexOutOfBoundsException{
    	if(i>size)
    		throw new IndexOutOfBoundsException("invalid index");
    	E old = findNode(i).e;
    	findNode(i).e =e;
    	return old;
    }
    public void add(int i, E e) throws IndexOutOfBoundsException{
    	if(i>size) 
    		throw new IndexOutOfBoundsException("invalid index");
    	else if(isEmpty()) {
    		Node<E> newNode = new Node<E>(e,head,head);
    		head.next = newNode;
    		head.prev = newNode;
    		size++;
    	}
    	else {
    		size++;
    		Node<E> newNode = new Node<E>(e,findNode(i).prev, findNode(i));
    		findNode(i).prev = newNode;
    		newNode.prev.next = newNode;
    	}
    }
    public E remove(int i) throws IndexOutOfBoundsException{
    	if(i>size)
    		throw new IndexOutOfBoundsException("invalid index");
        Node<E> old = findNode(i);
        old.prev.next=old.next;
        old.next.prev=old.prev;
        old.next =null;
        old.prev=null;
        size--;
        return old.e;
    }
    
    public Iterator<E> iterator(){
    	return new NodeIterator<E>(head);
    }
    
    //TODO: implement interface Iterable

    //helper methods
    protected Node<E> findNode(int i) {
        if(i < 0 || i >= size)
            throw new IndexOutOfBoundsException("invalid index: " + i + " is not in [ 0, " + size + ")");
        Node<E> ret = head;
       	int index = 0;
       	for(Node<E> search= head.next;!search.equals(null); search=search.next) {
       		ret = search;
       		if(index ==i)
       			return search;
      		index++;
       	}
       	return ret;
    }
    private static void onFalseThrow(boolean b) {
        if(!b)
            throw new RuntimeException("Error: unexpected");            
    }
    public static void main(String[] args) {
        CircularlyDblLinkedList<Integer> list = new CircularlyDblLinkedList<Integer>();
        list.add(list.size(), 2);
        list.add(list.size(),3);
        list.add(list.size(),4);
        list.add(0, 1);
        onFalseThrow(list.remove(list.size()-1) == 4); 
        onFalseThrow(list.remove(list.size()-1) == 3); 
        onFalseThrow(list.remove(0) == 1); 
        onFalseThrow(list.remove(list.size()-1) == 2); 
        onFalseThrow(list.isEmpty()); 
        System.out.println("Success!");
    }    
}

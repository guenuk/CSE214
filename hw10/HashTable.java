//Geunuk Na, geunuk.na@stonybrook.edu
package hw214_10;

import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class HashTable<K, V> implements Map<K, V> {
    protected static class HashEntry<K, V> implements Entry<K, V> {
        protected K key;
        protected V value;
        public HashEntry(K key, V value) { this.key = key; this.value = value; }
        public K key()                   { return this.key; }
        public V value()                 { return this.value; }
        public void setKey(K key)        { this.key = key; }
        public void setValue(V value)    { this.value = value; }
    }
    protected static final double REHASH_THRESHOLD = 0.5;
    protected int capacity;
    protected int size;
    protected ArrayList<Entry<K, V>>[] buckets;

    public HashTable() {
        this(17);
    }
    
    public HashTable(int capacity) {
        this.capacity = capacity;
        buckets = (ArrayList<Entry<K, V>>[]) new ArrayList[capacity];
    }

    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }
    public double loadFactor() {
        //TODO: compute the load factor
        return  (double)size/(double)capacity;
    }
    
    public V get(K key) {
        //TODO: find the bucket index from key
        //      look for the entry with the key from the bucket
        //      return the value of the entry if found
        //      return null otherwise
    	int idx = index(key);
    	if(buckets[idx]!=null) {
    		for(Entry<K,V> entry: buckets[idx]) { 
    			if(key.equals(entry.key())) {
    				return entry.value();
    			}
    		}
    	}
        return null;
    }
    public V put(K key, V value) {
        //TODO: if an entry with the key is found
        //        replace the entry's value and return the old value
        //        otherwise add a new entry,
        //        rehash if the load factor is larger than REHASH_THRESHOLD,
        //        and return null
    	int idx = index(key);
    	if(buckets[idx] != null) {
    		for(Entry<K,V> entry: buckets[idx]) {
    			if(key.equals(entry.key())) {
    				V old = entry.value();
    				entry.setValue(value);
    				return old;
    			}
    		}
    	}
    	Entry<K,V> newEntry = new HashEntry<K,V>(key,value);
    	buckets[idx] = new ArrayList<Entry<K,V>>();
    	buckets[idx].add(newEntry);
    	size++;
    	if(loadFactor()>REHASH_THRESHOLD)
    		rehash();
        return null;
    }
    
    public V remove(K key) {
        //TODO: if an entry with the key is found
        //        remove the entry and return its value
        //      otherwise return null
    	int idx = index(key);
    	for(Entry<K,V> entry: buckets[idx]) {
    		if(key.equals(entry.key())) {
    			V old = entry.value();
    			entry = null;
    			size--; 
    			return old;
    		}
    	}
        return null;
    }
    
    public Iterable<K> keys() {
        ArrayList<K> snapshot = new ArrayList<K>();
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i] == null)
                continue;
            for(Entry<K, V> entry : buckets[i])
                snapshot.add(entry.key());
        }
        return snapshot;
    }
    
    public Iterable<V> values() {
        ArrayList<V> snapshot = new ArrayList<V>();
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i] == null)
                continue;
            for(Entry<K, V> entry : buckets[i])
                snapshot.add(entry.value());
        }
        return snapshot;
    }
    
    public Iterable<Entry<K,V>> entries() {
        ArrayList<Entry<K,V>> snapshot = new ArrayList<Entry<K,V>>();
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i] == null)
                continue;
            for(Entry<K, V> entry : buckets[i])
                snapshot.add(entry);
        }
        return snapshot;
    }
    
    protected void rehash() {
        //TODO: get an Iterable<Entry<K,V>> of old entries
        //      compute a new capacity, a prime number after capacity*2
        //      allocate buckets with the new capacity
        //      reset size to 0 and 
        //      put all entries in old entries to the new bucket list
    	Iterable<Entry<K,V>> old = entries();
    	int newCap = primeNumberAfter(capacity*2);
    	capacity = newCap;
    	size = 0;
    	
    	ArrayList<Entry<K,V>>[] newBuckets = (ArrayList<Entry<K, V>>[]) new ArrayList[newCap];
    	for(Entry<K,V> entry: old) {
    		int idx = index(entry.key());
    		if(newBuckets[idx]==null)
    			newBuckets[idx] = new ArrayList<Entry<K,V>>();
    		newBuckets[idx].add(entry);
    		size++;
    	}
    	buckets = newBuckets;
    }
    
    protected int primeNumberAfter(int n) {
        while(true) {
            int i;
            for(i = 2; i*i < n; i ++)
                if(n % i == 0)
                    break;
            if(i*i < n)
                n++;
            else
                return n;
        }
    }
    
    protected int index(Object obj) {
        //hash code -> index
        return (hashCode(obj) % capacity + capacity) % capacity;
    }
    
    public static int hashCode(Object obj) {
        //serialize the object and compute a hash code from it
        try {
            PipedInputStream pis   = new PipedInputStream();
            PipedOutputStream pos  = new PipedOutputStream(pis);
            ObjectOutputStream oos = new ObjectOutputStream(pos);
            
            oos.writeObject(obj);
            int code = hashCode(pis);
            
            oos.close(); pos.close(); pis.close();
            return code;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    protected static int hashCode(InputStream is) throws IOException {
        //TODO: implement the 5 bit cyclic-shift hash code
        //        while is.available() > 0,
        //        update the code with is.read()
    	int h= 0;
    	while(is.available()>0) {
    		h=(h<<5|(h>>>27));
    		h += is.read();
    	}
        return h;
    }
    
    protected static void onFalseThrow(boolean b) {
        if(!b)
            throw new RuntimeException("Error: unexpected");
    }       
    public static void main(String[] args) {
        onFalseThrow(hashCode("Hello World") == 583231368);
        
        HashTable<String, Integer> ht = new HashTable<>(3);
        onFalseThrow(ht.get("key") == null);
        onFalseThrow(ht.put("key", 10) == null);
        onFalseThrow(ht.put("key", 20) == 10);
        onFalseThrow(ht.remove("key") == 20);
        onFalseThrow(ht.size() == 0);
        onFalseThrow(ht.isEmpty());

        for(int i = 0; i < 10; i++)
            ht.put("key_" + i, i);
        
        onFalseThrow(ht.size() == 10);
        onFalseThrow(!ht.isEmpty());
        onFalseThrow(ht.capacity == 37);

        for(String key: ht.keys())
            onFalseThrow(key.compareTo("key_0") >= 0 && key.compareTo("key_9") <= 0);
        
        for(int val: ht.values())
            onFalseThrow(0 <= val && val <= 9);

        for(Entry<String, Integer> entry: ht.entries()) {
            onFalseThrow(entry.key().compareTo("key_0") >= 0 && entry.key().compareTo("key_9") <= 0);
            onFalseThrow(0 <= entry.value() && entry.value() <= 9);
        }

        for(int i = 0; i < 10; i++)
            onFalseThrow(ht.get("key_" + i) == i);

        for(int i = 0; i < 10; i++)
            onFalseThrow(ht.put("key_" + i, -i) == i);
        
        for(int i = 0; i < 10; i++)
            onFalseThrow(ht.remove("key_" + i) == -i);
        
        System.out.println("Success!");
    }
}

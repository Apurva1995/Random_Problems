/*
* Internal working of add, get, size, isEmpty and remove methods of HashMap in java.
*/

import java.util.*;
import java.lang.*;
import java.io.*;

class HashNode<K,V> {
    
    K key;
    V value;
    HashNode<K,V> next;
    
    public HashNode(K key, V value) {
        
        this.key = key;
        this.value = value;
    }
}

class Map<K,V>
{
    private List<HashNode<K,V>> map;
    private int numberOfBuckets, size;
    
    public Map() {
        
        numberOfBuckets = 10;
        size = 0;
        map = new ArrayList<>(Collections.nCopies(numberOfBuckets, null));
    }
    
    private int getIndexOfKey(K key) {
        
        int hashCode = key.hashCode();
        return (hashCode % numberOfBuckets);
    }
    
    public int size() {
        
        return size;
    }
    
    public boolean isEmpty() {
        
        return (size() == 0);
    }
    
    public V remove(K key) {
        
        int index = getIndexOfKey(key);
        HashNode<K,V> head = map.get(index);
        HashNode<K,V> prev = null;
        
        while(head != null) {
            
            if(head.key.equals(key))
                break;
            
            prev = head;
            head = head.next;
        }
        
        if(head == null)
            return null;
        
        size--;
        if(prev != null)
            prev.next = head.next;
        else    
            map.set(index, head.next);
        
        return head.value;
    }
    
    public V get(K key) {
        
        int index = getIndexOfKey(key);
        HashNode<K,V> head = map.get(index);
        
        while(head != null) {
            
            if(head.key.equals(key))
                return head.value;
            
            head = head.next;
        }
        
        return null;
    }
    
    public void add(K key, V value) {
        
        int index = getIndexOfKey(key);
        HashNode<K, V> newNode = new HashNode<>(key, value);
        HashNode<K,V> head = map.get(index);
            
            head = map.get(index);
            while(head != null) {
                
                if(head.key.equals(key)) {
                    head.value = value;
                    return;
                }
                head = head.next;
            }
            
            size++;
            newNode.next = head;
            map.set(index, newNode);
            
            if((1.0*size)/numberOfBuckets > 0.7) {
                
                List<HashNode<K,V>> temp = map;
                numberOfBuckets *= 2;
                size = 0;
                map = new ArrayList<>(Collections.nCopies(numberOfBuckets, null));
                
                for(HashNode<K,V> hashNode : temp) {
                    
                    while(hashNode != null) {
                        add(hashNode.key, hashNode.value);
                        hashNode = hashNode.next;
                    }
                }
            }
    } 
    
	public static void main (String[] args) throws java.lang.Exception
	{
		Map<String, Integer>map = new Map<>(); 
        map.add("this",1 ); 
        map.add("coder",2 ); 
        map.add("this",4 ); 
        map.add("hi",5 );
        System.out.println(map.size()); 
        System.out.println(map.remove("this")); 
        System.out.println(map.remove("this")); 
        System.out.println(map.size()); 
        System.out.println(map.isEmpty()); 
	}
}

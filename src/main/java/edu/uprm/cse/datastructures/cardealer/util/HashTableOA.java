package edu.uprm.cse.datastructures.cardealer.util;

import java.util.Comparator;
import java.util.List;

import edu.uprm.cse.datastructures.cardealer.SortedCircularDoublyLinkedList;
import edu.uprm.cse.datastructures.cardealer.model.KeyComparator;
import edu.uprm.cse.datastructures.cardealer.model.ValueComparator;

public class HashTableOA<K, V> implements Map<K, V> {
	
	public static enum State{
		NEVER_USED, FULL, EMPTY;
	}

	public static class MapEntry<K, V> {
		private K key;
		private V value;
		private State state;
		public K getKey() {
			return key;
		}
		public void setKey(K key) {
			this.key = key;
		}
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
		public State getState() {
			return state;
		}
		public void setState(State state) {
			this.state = state;
		}
		public MapEntry(K key, V value, State state) {
			super();
			this.key = key;
			this.value = value;
			this.setState(State.FULL);
		}
		
		public MapEntry() {
			super();
			this.key = null;
			this.value = null;
			this.setState(State.NEVER_USED);
		}
	}
	
	private int currentSize;
	private Object[] buckets;
	private static final int DEFAULT_BUCKETS = 11;
	private Comparator<K> compK;
	private Comparator<V> compV;
	
	private int hashF1(K key) {
		return key.hashCode() % this.buckets.length;
	}
	
	private int hashF2(K key) {
		return DEFAULT_BUCKETS - (key.hashCode() % DEFAULT_BUCKETS);
	}
	
	public HashTableOA(int initialCapacity, Comparator<K> cmpK, Comparator<V> cmpV) {
		this.currentSize = 0;
		this.compK = cmpK;
		this.compV = cmpV;
		this.buckets = new Object[initialCapacity];
		for (int i =0; i < initialCapacity; ++i) {
			this.buckets[i] = new MapEntry<>();
		}
		
	}
	
	public HashTableOA(Comparator<K> cmpK, Comparator<V> cmpV) {
		this(DEFAULT_BUCKETS, cmpK, cmpV);
	}
	
	private void reAllocate() {
		HashTableOA<K, V> newHash = new HashTableOA<K, V>(this.buckets.length*2, this.compK, this.compV);
		
		for (Object o : this.buckets) {
			MapEntry<K, V> L = (MapEntry<K, V>) o;
			if (L.getKey() != null) {
				newHash.put(L.getKey(), L.getValue());
			}
		}
		
		this.buckets = newHash.buckets;
	}

	@Override
	public int size() {
		return this.currentSize;
	}
	

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}
	

	@Override
	public V get(K key) {
		if (key == null){
			return null;
		}
		
		int target = this.hashF1(key); // first hash
		MapEntry<K,V> L = (MapEntry<K, V>) this.buckets[target];
		
		if (((MapEntry<K, V>) L).getKey() != null && !((MapEntry<K, V>) L).getKey().equals(key)) {
			target = this.hashF2(key); // second hash
			L = (MapEntry<K, V>) this.buckets[target];
		}
		
		if (((MapEntry<K, V>) L).getKey() != null && !((MapEntry<K, V>) L).getKey().equals(key)) { // linear probing
			for (int i = 1; i < this.buckets.length; i++) {
				L = (MapEntry<K, V>) this.buckets[(target + i) % this.buckets.length];
				if (L.getKey() != null && L.getKey() == key) {
					return L.getValue();
				}
			}
		}
		
		return L.getValue();
	}

	@Override
	public V put(K key, V value) {
		if (this.currentSize == this.buckets.length) {
			this.reAllocate();
		}
		
		V oldValue = this.get(key);
		
		if (oldValue != null) {
			int target = this.hashF1(key); // first hash
			MapEntry<K,V> L = (MapEntry<K, V>) this.buckets[target];
			
			if (((MapEntry<K, V>) L).getKey() != null && !((MapEntry<K, V>) L).getKey().equals(key)) {
				target = this.hashF2(key); // second hash
				L = (MapEntry<K, V>) this.buckets[target];
			}
			
			if (((MapEntry<K, V>) L).getKey() != null && !((MapEntry<K, V>) L).getKey().equals(key)) { // linear probing
				for (int i = 1; i < this.buckets.length; i++) {
					L = (MapEntry<K, V>) this.buckets[(target + i) % this.buckets.length];
					if (L.getKey() != null && L.getKey() == key) {
						L.setValue(value);
						return oldValue; // return the old value
					}
				}
			}
			L.setValue(value);
			return oldValue;
		}
		
		else {
			int target = this.hashF1(key); // first hash
			MapEntry<K,V> L = (MapEntry<K, V>) this.buckets[target];
			
			if (((MapEntry<K, V>) L).getKey() != null && !((MapEntry<K, V>) L).getKey().equals(key)) {
				target = this.hashF2(key); // second hash
				L = (MapEntry<K, V>) this.buckets[target];
			}
			
			if (((MapEntry<K, V>) L).getKey() != null && !((MapEntry<K, V>) L).getKey().equals(key)) { // linear probing
				for (int i = 1; i < this.buckets.length; i++) {
					L = (MapEntry<K, V>) this.buckets[(target + i) % this.buckets.length];
					if (L.getState() != State.FULL) {
						L.setKey(key);
						L.setValue(value);
						this.currentSize++;
						return null; // there is no old value, so return null
					}
				}
			}
			L.setKey(key);
			L.setValue(value);
			this.currentSize++;
			return null; // there is no old value, so return null
		}
	}

	@Override
	public V remove(K key) {
		if (key == null) {
			return null;
		}
		
		int target = this.hashF1(key); // first hash
		MapEntry<K,V> L = (MapEntry<K, V>) this.buckets[target];
		
		if (((MapEntry<K, V>) L).getKey() != null && !((MapEntry<K, V>) L).getKey().equals(key)) {
			target = this.hashF2(key); // second hash
			L = (MapEntry<K, V>) this.buckets[target];
		}
		
		if (((MapEntry<K, V>) L).getKey() != null && !((MapEntry<K, V>) L).getKey().equals(key)) { // linear probing
			for (int i = 1; i < this.buckets.length; i++) {
				L = (MapEntry<K, V>) this.buckets[(target + i) % this.buckets.length];
				if (L.getKey() != null && L.getKey() == key) {
					V oldVal = L.getValue();
					L.setKey(null);
					L.setValue(null);
					L.setState(State.EMPTY);
					this.currentSize--;
					return oldVal; // return the old value
				}
			}
		}
		
		L.setKey(null);
		L.setValue(null);
		L.setState(State.EMPTY);
		this.currentSize--;
		return this.get(key); // return the old value
	}
	

	@Override
	public boolean contains(K key) {
		return this.get(key) != null;
	}
	

	@Override
	public SortedList<K> getKeys() {
		SortedList<K> result = new SortedCircularDoublyLinkedList<K>(compK);
		
		for (Object o : this.buckets) {
			MapEntry<K,V> L = (MapEntry<K,V>) o;
			if (L.getKey() != null) {
				result.add(L.getKey());
			}
		}
		return result;
	}

	@Override
	public SortedList<V> getValues() {
		SortedList<V> result = new SortedCircularDoublyLinkedList<V>(compV);
		
		for (Object o : this.buckets) {
			MapEntry<K,V> L = (MapEntry<K,V>) o;
			if (L.getValue() != null) {
				result.add(L.getValue());
			}
		}
		return result;
	}

}

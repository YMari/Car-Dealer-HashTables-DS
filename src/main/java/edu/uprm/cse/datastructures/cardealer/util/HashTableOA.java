package edu.uprm.cse.datastructures.cardealer.util;

import java.util.Comparator;
import java.util.List;

import edu.uprm.cse.datastructures.cardealer.SortedCircularDoublyLinkedList;

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
	private Comparator<MapEntry<K, V>> comp;
	
	private int hashF1(K key) {
		return key.hashCode() % this.buckets.length;
	}
	
	private int hashF2(K key) {
		return DEFAULT_BUCKETS - (key.hashCode() % DEFAULT_BUCKETS);
	}
	
	public HashTableOA(int initialCapacity) {
		this.currentSize = 0;
		this.buckets = new Object[initialCapacity];
		for (int i =0; i < initialCapacity; ++i) {
			this.buckets[i] = new MapEntry<>();
//			this.buckets[i] = new SortedCircularDoublyLinkedList<MapEntry<K,V>>(comp);
		}
	}
	
	public HashTableOA() {
		this(DEFAULT_BUCKETS);
	}
	
	private void reAllocate() {
		int newSize = nextPrime(this.size()*2);
		Object[] newHashTable = new Object[newSize];
		
		for (int i =0; i < newSize; ++i) {
			newHashTable[i] = new MapEntry<>();
		}
		
		for (Object o : this.buckets) {
			List<MapEntry<K,V>> L = (List<MapEntry<K,V>>) o;
			for (MapEntry<K,V> M : L) {
				K key = M.getKey();
				int target = this.hashF1(key);
				SortedList<MapEntry<K,V>> B = (SortedList<MapEntry<K, V>>) this.buckets[target];
				
				if (!((MapEntry<K, V>) B).getKey().equals(key)) {
					target = this.hashF2(key); // second hash
					B = (SortedList<MapEntry<K, V>>) this.buckets[target];
				}
				
				if (!((MapEntry<K, V>) B).getKey().equals(key)) { // linear probing
					for (int i = 1; i < this.buckets.length; i++) {
						if (this.buckets[(target + i) % this.buckets.length] == null) {
							i = target;
							break;
						}
					}
				}
				
				this.buckets[target] = new MapEntry<K, V>(M.getKey(), M.getValue(), State.FULL);
			}
		}
		
		this.buckets = newHashTable;
	}
	
	private int nextPrime(int n) {
		if (!isPrime(n)) {
			n = nextPrime(++n);
		}
		return n;
	}
	
	
	private boolean isPrime(int n) {
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
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
		int target = this.hashF1(key); // first hash
		SortedList<MapEntry<K,V>> L = (SortedList<MapEntry<K, V>>) this.buckets[target];
		
		if (!((MapEntry<K, V>) L).getKey().equals(key)) {
			target = this.hashF2(key); // second hash
			L = (SortedList<MapEntry<K, V>>) this.buckets[target];
		}
		
		if (!((MapEntry<K, V>) L).getKey().equals(key)) { // linear probing
			for (int i = 1; i < this.buckets.length; i++) {
				if (this.buckets[(target + i) % this.buckets.length] == null) {
					i = target;
					break;
				}
			}
		}
		
		MapEntry<K, V> M = (MapEntry<K, V>) this.buckets[target];
		return (M != null ? M.getValue() : null);	
		
		
		
//		int targetBucket = this.hashF1(key);
//		SortedList<MapEntry<K,V>> L = (SortedList<MapEntry<K, V>>) this.buckets[targetBucket];
//		if (((MapEntry<K, V>) L).getKey().equals(key)) {
//			return ((MapEntry<K, V>) L).getValue();
//		}
//		return null;
		
		
//		int i = this.hashF1(key);
//		int j = 0;
//		for (Object o : this.buckets) {
//			while (j != i) {
//				List<MapEntry<K,V>> L = (List<MapEntry<K,V>>) o;
//				for (MapEntry<K,V> M : L) {
//					if (M.getState() == State.NEVER_USED) {
//						return null;
//					}
//					if (M.getState() == State.FULL && M.getKey().equals(key)) {
//						return M.getValue();
//					}
//					else {
//						j = (j + 1) % this.buckets.length;
//					}
//				}
//			}
//		}
//		return null;
		
//		int i = this.hashF1(key);
//		int j = 0;
//		while (j != i) {
//			for (Object o : this.buckets) {
//				List<MapEntry<K,V>> L = (List<MapEntry<K,V>>) o;
//				for (MapEntry<K,V> M : L) {
//					if (M.getState() == State.NEVER_USED) {
//						return null;
//					}
//					if (M.getState() == State.FULL && M.getKey().equals(key)) {
//						return M.getValue();
//					}
//					else {
//						j = (j + 1) % this.buckets.length;
//					}
//				}		
//			}
//		}
//		
//		return null;
		
//		int targetBucket = this.hashF1(key);
//		SortedList<MapEntry<K,V>> L = (SortedList<MapEntry<K, V>>) this.buckets[targetBucket];
//		
//		for (MapEntry<K, V> M : L) {
////			if (M.getKey().equals(key) && M.getState() == State.FULL) {
//			if (M.getKey().equals(key)) {
//				return M.getValue();
//			}
//		}
//		return null;
	}

	@Override
	public V put(K key, V value) {
		V oldValue = this.get(key);
		
		if (oldValue != null) {
			this.remove(key);
		}
		else if (this.currentSize == this.buckets.length) {
			this.reAllocate();
		}
		
		int target = this.hashF1(key);
		SortedList<MapEntry<K,V>> L = (SortedList<MapEntry<K, V>>) this.buckets[target];
		
		if (!((MapEntry<K, V>) L).getKey().equals(key)) {
			target = this.hashF2(key); // second hash
			L = (SortedList<MapEntry<K, V>>) this.buckets[target];
		}
		
		if (!((MapEntry<K, V>) L).getKey().equals(key)) { // linear probing
			for (int i = 1; i < this.buckets.length; i++) {
				if (this.buckets[(target + i) % this.buckets.length] == null) {
					i = target;
					break;
				}
			}
		}
		
		MapEntry<K, V> M = new MapEntry<K,V>(key, value, State.FULL);
		L.add(M);
		this.currentSize++;
		return oldValue;
		
//		int targetBucket = this.hashF1(key);
//		SortedList<MapEntry<K,V>> L = (SortedList<MapEntry<K,V>>) this.buckets[targetBucket];
//		MapEntry<K,V> M = new MapEntry<K,V>(key, value, State.FULL);
//		L.add(M);
//		this.currentSize++;
//
//		return oldValue;
	}

	@Override
	public V remove(K key) {
		int target = this.hashF1(key);
		SortedList<MapEntry<K,V>> L = (SortedList<MapEntry<K, V>>) this.buckets[target];
		
		if (!((MapEntry<K, V>) L).getKey().equals(key)) {
			target = this.hashF2(key); // second hash
			L = (SortedList<MapEntry<K, V>>) this.buckets[target];
		}
		
		if (!((MapEntry<K, V>) L).getKey().equals(key)) { // linear probing
			for (int i = 1; i < this.buckets.length; i++) {
				if (this.buckets[(target + i) % this.buckets.length] == null) {
					i = target;
					break;
				}
			}
		}
		
		int i=0;
		
		for (MapEntry<K,V> M: L ) {
			if (M.getKey().equals(key) && M.getState() == State.FULL) {
				V result = M.getValue();
				L.remove(i);
				M.setState(State.EMPTY);
				this.currentSize--;
				return result;
			}
			else {
				i++;
			}
		}
		return null;
	}
	

	@Override
	public boolean contains(K key) {
		return this.get(key) != null;
	}
	

	@Override
	public SortedList<K> getKeys() {
		SortedList<K> result = (SortedList<K>) new SortedCircularDoublyLinkedList<>(comp);
		
		for (Object o : this.buckets) {
			List<MapEntry<K,V>> L = (List<MapEntry<K,V>>) o;
			for (MapEntry<K,V> M : L) {
				result.add(M.getKey());
			}
		}
		return result;
	}

	@Override
	public SortedList<V> getValues() {
		SortedList<V> result = (SortedList<V>) new SortedCircularDoublyLinkedList<>(comp);
		
		for (Object o : this.buckets) {
			List<MapEntry<K,V>> L = (List<MapEntry<K,V>>) o;
			for (MapEntry<K,V> M : L) {
				result.add(M.getValue());
			}
		}
		return result;
	}

}

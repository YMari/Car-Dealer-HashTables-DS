package edu.uprm.cse.datastructures.cardealer.util;

import java.util.Comparator;
import java.util.List;

import edu.uprm.cse.datastructures.cardealer.SortedCircularDoublyLinkedList;

public class HashTableOA<K, V> implements Map<K, V> {

	public static class MapEntry<K, V> {
		private K key;
		private V value;
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
		public MapEntry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}
	}
	
	private int currentSize;
	private Object[] buckets;
	private static final int DEFAULT_BUCKETS = 11;
	private Comparator<MapEntry<K, V>> comp;
	
	private int hashFunction(K key) {
		return key.hashCode() % this.buckets.length;
	}
	
	private int reHash(K key) {
		int singleHash = hashFunction(key);
		int doubleHash = DEFAULT_BUCKETS - (key.hashCode() % DEFAULT_BUCKETS);
		
//		if (this.contains(singleHash) && this.contains(doubleHash)) {
//			
//		}
		
		
		return 0;
	}
	
	public HashTableOA(int numBuckets) {
		this.currentSize  = 0;
		this.buckets = new Object[numBuckets];
		for (int i =0; i < numBuckets; ++i) {
			this.buckets[i] = new SortedCircularDoublyLinkedList<MapEntry<K,V>>(comp);
		}
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
		int targetBucket = this.hashFunction(key);
		SortedList<MapEntry<K,V>> L = (SortedList<MapEntry<K, V>>) this.buckets[targetBucket];
		
		for (MapEntry<K, V> M : L) {
			if (M.getKey().equals(key)) {
				return M.getValue();
			}
		}
		return null;
	}

	@Override
	public V put(K key, V value) {
		V oldValue = this.get(key);
		if (oldValue != null) {
			this.remove(key);
		}
		int targetBucket = this.hashFunction(key);
		SortedList<MapEntry<K,V>> L = (SortedList<MapEntry<K,V>>) this.buckets[targetBucket];
		MapEntry<K,V> M = new MapEntry<K,V>(key, value);
		L.add(M);
		this.currentSize++;

		return oldValue;
	}

	@Override
	public V remove(K key) {
		int targetBucket = this.hashFunction(key);
		SortedList<MapEntry<K,V>> L = (SortedList<MapEntry<K,V>>) this.buckets[targetBucket];
		int i=0;
		
		for (MapEntry<K,V> M: L ) {
			if (M.getKey().equals(key)) {
				// borrar
				V result = M.getValue();
				L.remove(i);
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

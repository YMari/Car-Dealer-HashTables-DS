package edu.uprm.cse.datastructures.cardealer.model;

import edu.uprm.cse.datastructures.cardealer.SortedCircularDoublyLinkedList;
import edu.uprm.cse.datastructures.cardealer.util.HashTableOA;
import edu.uprm.cse.datastructures.cardealer.util.SortedList;

public class CarTable {
	
	public static SortedCircularDoublyLinkedList<Car> carList = new SortedCircularDoublyLinkedList<Car>(new CarComparator());
	
	public static SortedCircularDoublyLinkedList<Car> getInstance() {
		return carList; // return a new instance of the list carList
	}
	
	public static void resetCars() {
		carList.clear(); // clears the elements on the list carList
	}

//	public static HashTableOA<K, V> carTable = new HashTableOA<>(); // K = CarID, V = CarRecord
//	
//	public static HashTableOA<K, V> getInstance() {
//		return carTable; // return a new instance of the HashTable carTable
//	}
//	
//	public static void resetCars() {
//		carTable.clear(); // clears the elements on the list carTable
//	}
	
}

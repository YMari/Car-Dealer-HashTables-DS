package edu.uprm.cse.datastructures.cardealer.model;

import edu.uprm.cse.datastructures.cardealer.util.HashTableOA;

public class CarTable {

	public static HashTableOA<Long, Car> carTable = new HashTableOA<>(new KeyComparator(), new ValueComparator());
	
	public static HashTableOA<Long, Car> getInstance() {
		return carTable; // return a new instance of the HashTable carTable
	}
	
	public static void resetCars() {
		carTable = new HashTableOA<>(new KeyComparator(), new ValueComparator()); // clears the elements on the HashTable carTable
	}
	
}

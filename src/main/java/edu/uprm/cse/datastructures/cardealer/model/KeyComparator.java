package edu.uprm.cse.datastructures.cardealer.model;

import java.util.Comparator;

public class KeyComparator implements Comparator<Long> {

	@Override
	public int compare(Long car1, Long car2) { // compare two car keys
		if (car1 > car2) {
			return 1;
		}
		else if (car1 < car2) {
			return -1;
		}
		else {
			return 0;
		}
	}

}

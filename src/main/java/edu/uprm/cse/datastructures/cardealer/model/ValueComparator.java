package edu.uprm.cse.datastructures.cardealer.model;

import java.util.Comparator;

public class ValueComparator implements Comparator<Car>{
	
	@Override
	public int compare(Car car1, Car car2) {
		// compares each individual string of the labeling in the Car class
		// to keep lexicographical order
		String car1Brand = car1.getCarBrand(); 
		String car2Brand = car2.getCarBrand();

		String car1Model = car1.getCarModel(); 
		String car2Model = car2.getCarModel();

		String car1Option = car1.getCarModelOption(); 
		String car2Option = car2.getCarModelOption();

		if (car1Brand.compareTo(car2Brand) == 0) {

			if(car1Model.compareTo(car2Model) == 0) {

				if(car1Option.compareTo(car2Option) == 0) {
					return 0;
				}
				else if (car1Option.compareTo(car2Option) > 0) {
					return 1;
				}
				else {
					return -1;
				}

			}
			else if (car1Model.compareTo(car2Model) > 0) {
				return 1;
			}
			else {
				return -1;
			}

		}
		else if (car1Brand.compareTo(car2Brand) > 0) {
			return 1;
		}
		else {
			return -1;
		}
		
	}
}

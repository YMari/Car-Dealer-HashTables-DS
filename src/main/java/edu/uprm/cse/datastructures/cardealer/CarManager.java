package edu.uprm.cse.datastructures.cardealer;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.uprm.cse.datastructures.cardealer.model.Car;
import edu.uprm.cse.datastructures.cardealer.model.CarTable;
import edu.uprm.cse.datastructures.cardealer.util.HashTableOA;
import edu.uprm.cse.datastructures.cardealer.util.HashTableOA.MapEntry;
import edu.uprm.cse.datastructures.cardealer.util.SortedList;

@Path("/cars")
public class CarManager {

	// call a new instance of the list
	private static HashTableOA<Long,Car> carTable = CarTable.getInstance();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Car[] getCarList(){ // returns all the elements available in the list
		Car[] carArray = new Car[carTable.size()];
		if (carTable.isEmpty()) {
			return carArray;
		}

		for (int i = 0; i < carTable.size(); i++) {
			carArray[i] = carTable.getValues().get(i);
		}
		return carArray;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car getCar(@PathParam("id") long id){ // returns the element with the specified id value
		if (carTable.get(id) != null) {
			return carTable.get(id);
		}
		throw new NotFoundException();
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCar(Car car){ // adds a new element to the list
		carTable = CarTable.getInstance();
		if (!carTable.contains(car.getCarId())) {
			carTable.put(car.getCarId(), car);
			return Response.status(201).build();
		}
		return Response.status(404).build();

	}

	@PUT
	@Path("/{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCar(Car car){ // updates an existing element with the specified id
		if (carTable.contains(car.getCarId())) {
			carTable.put(car.getCarId(), car);
			return Response.status(Response.Status.OK).build();
		}
		return Response.status(404).build(); // if the id does not exist, return 404 (Error)
	}

	@DELETE
	@Path("/{id}/delete")
	public Response deleteCar(@PathParam("id") long id){ // deletes an existing element with the specified id
		if (carTable.contains(id)) {
			carTable.remove(id);
			return Response.status(Response.Status.OK).build();
		}
		return Response.status(404).build(); // if the id does not exist, return 404 (Error)
	}	
	
//	// call a new instance of the list
////	private static final HashTableOA<Long,Car> carTable = CarTable.getInstance();
//	private static final SortedCircularDoublyLinkedList<Car> carTable = CarTable.getInstance();
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Car[] getCarList(){ // returns all the elements available in the list
//		if(carTable.isEmpty())return null;
//		Car[] carArray = new Car[carTable.size()];
//		for(int i=0;i<carArray.length; i++)carArray[i]=(Car)carTable.get(i);
//		return carArray;
//	}
//
//	@GET
//	@Path("/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Car getCar(@PathParam("id") long id){ // returns the element with the specified id value
//		for(int i=0; i<carTable.size(); i++){
//			if(((Car)carTable.get(i)).getCarId()==id){ return (Car)carTable.get(i);}  
//		}  throw new NotFoundException();
//	}
//
//	@POST
//	@Path("/add")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response addCar(Car car){ // adds a new element to the list
//		carTable.add(car);
//		return Response.status(201).build();
//	}
//
//	@PUT
//	@Path("/{id}/update")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateCar(Car car){ // updates an existing element with the specified id
//		for(int i=0; i<carTable.size(); i++){
//			if(((Car)carTable.get(i)).getCarId()==car.getCarId()){ // tests for same element id
//				carTable.remove(i);
//				carTable.add(car);
//				return Response.status(Response.Status.OK).build();
//			}
//		}
//		return Response.status(404).build(); // if the id does not exist, return 404 (Error)
//	}
//
//	@DELETE
//	@Path("/{id}/delete")
//	public Response deleteCar(@PathParam("id") long id){ // deletes an existing element with the specified id
//		for(int i=0; i<carTable.size(); i++){
//			if(id==((Car)carTable.get(i)).getCarId()){
//				carTable.remove(i);
//				return Response.status(Response.Status.OK).build();
//			}
//		}
//		return Response.status(404).build(); // if the id does not exist, return 404 (Error)
//
//	}
	
}
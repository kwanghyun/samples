package algorithm.ood.elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * Controller, intentionally not add abstraction.
 */
public class Building {

	int maxFloor;
	int minFloor;

	/*
	 * Need to get nearest elevator ASAP What data structure is good for this?
	 * search with elevator.floor.
	 * 
	 * In Database, we'd better use index it with B+ Index, so fast search and
	 * range query.
	 * 
	 * In data structure, we could use CompareTo, to array is sorted by floor as
	 * a second index
	 * 
	 * 1. How many data?(space complexity)
	 * 2. Need to be sorted?
	 * 3. Read performance
	 * 4. Write performance
	 */
		
	Set<Elevator> elevators = new TreeSet<Elevator>(
			new Comparator<Elevator>() {
				public int compare(Elevator elev1, Elevator elev2) {
					return elev1.floor - elev2.floor;
				}
			});

	Map<Elevator, String> sortedMap = new TreeMap<Elevator, String>(
			new Comparator<Elevator>() {
				public int compare(Elevator elev1, Elevator elev2) {
					return elev1.floor - elev2.floor;
				}
			});
	
	
	public Elevator findNearestElevator(int floor) {

		Elevator nearOne = null;
		int minDiff = Integer.MAX_VALUE;
		for (Elevator elevator : elevators) {
			if (elevator.getState() == State.IDLE) {
				int diff = Math.abs(elevator.floor - floor);
				if (minDiff > diff) {
					minDiff = diff;
					nearOne = elevator;
				}
			}
		}
		return nearOne;
	}

	public void getRideElevator(Pessenger person) {
		Elevator elevator = findNearestElevator(person.getFloor());
	}

	public int getMaxFloor() {
		return maxFloor;
	}

	public void setMaxFloor(int maxFloor) {
		this.maxFloor = maxFloor;
	}

	public int getMinFloor() {
		return minFloor;
	}

	public void setMinFloor(int minFloor) {
		this.minFloor = minFloor;
	}

	public Set<Elevator> getElevators() {
		return elevators;
	}

	public void setElevators(Set<Elevator> elevators) {
		this.elevators = elevators;
	}

}

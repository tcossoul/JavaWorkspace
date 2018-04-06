import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Bear extends Animal {
	private static final int BREEDING_AGE = 4;
	private static final int MAX_AGE = 25;
	private static final double BREEDING_PROBABILITY = 0.1;
	private static final int MAX_LITTER_SIZE = 3;
	private static final int FOOD_VALUE = 100;

	public Bear(boolean randomAge) {
		super( randomAge, BREEDING_AGE, MAX_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE, FOOD_VALUE );
	}
	
	// Only carnivor animals hunt (herbivors run)
	public void act(Field currentField, Field updatedField, List<Animal> newAnimals) {
		incrementAge();
		incrementHunger();
		if (alive) {
			// New Animals are born into adjacent locations.
			int births = breed();
			for (int b = 0; b < births; b++) {
				Animal newAnimal = new Bear( false );
				newAnimal.setFoodLevel( this.foodLevel );
				newAnimals.add( newAnimal );
				Location loc = updatedField.randomAdjacentLocation( location );
				newAnimal.setLocation( loc );
				updatedField.put( newAnimal, loc);
			}
			// Move towards the source of food if found.
			Location newLocation = findFood(currentField, location);
			if (newLocation == null) { // no food found - move randomly
				newLocation = updatedField.freeAdjacentLocation(location);
			}
			if (newLocation != null) {
				setLocation(newLocation);
				updatedField.put(this, newLocation);
			} else {
				// can neither move nor stay - overcrowding - all locations taken
				alive = false;
			}
		}
	}

    // Bears heat rabbits and foxes
	private Location findFood(Field field, Location location) {
		List<Location> adjacentLocations = field.adjacentLocations(location);

		for (Location where : adjacentLocations) {
			Object animal = field.getObjectAt(where);
			if (animal instanceof Rabbit) {
				Rabbit rabbit = (Rabbit) animal;
				if (rabbit.isAlive()) {
					rabbit.setEaten();
					foodLevel = rabbit.getFoodValue();
					return where;
				}
			} else if (animal instanceof Fox) {
				Fox fox = (Fox) animal;
				if (fox.isAlive()) {
					fox.setEaten();
					foodLevel = fox.getFoodValue();
					return where;
				}
			}
		}
		return null;
	}
}

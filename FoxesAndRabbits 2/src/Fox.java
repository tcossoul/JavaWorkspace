import java.util.List;

public class Fox extends Animal {
	private static final int BREEDING_AGE = 3;
	private static final int MAX_AGE = 50;
	private static final double BREEDING_PROBABILITY = 0.15;
	private static final int MAX_LITTER_SIZE = 6;
	private static final int FOOD_VALUE = 18;
	
	public Fox( boolean randomAge ) {
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
				Animal newAnimal = new Fox( false );
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

    // Foxes heat rabbits
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
			}
		}
		return null;
	}
}
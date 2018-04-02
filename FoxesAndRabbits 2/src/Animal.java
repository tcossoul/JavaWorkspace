import java.io.Serializable;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a animal. Animals age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kolling.  Modified by David Dobervich 2007-2013.
 * @version 2006.03.30
 */
public class Animal implements Serializable {
	// Characteristics shared by all Animals (static fields).
	private int BREEDING_AGE;
	// The age to which a animal can live.
	private int MAX_AGE;
	// The likelihood of a animal breeding.
	private double BREEDING_PROBABILITY;
	// The maximum number of births.
	private int MAX_LITTER_SIZE;
	// The food value of a single rabbit. In effect, this is the
	// number of steps a animal can go before it has to eat again.
	private int RABBIT_FOOD_VALUE;
	// A shared random number generator to control breeding.
	private static final Random rand = new Random();

	// Individual characteristics (instance fields).

	// The animal's age.
	private int age;
	// Whether the animal is alive or not.
	private boolean alive;
	// The animal's position
	private Location location;
	// The animal's food level, which is increased by eating rabbits.
	private int foodLevel;

	/**
	 * Create a animal. A animal can be created as a new born (age zero and not
	 * hungry) or with random age.
	 * 
	 * @param randomAge
	 *            If true, the animal will have random age and hunger level.
	 */
	public Animal(boolean randomAge) {
		age = 0;
		alive = true;
		if (randomAge) {
			age = rand.nextInt(MAX_AGE);
			foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
		} else {
			// leave age at 0
			foodLevel = RABBIT_FOOD_VALUE;
		}
	}

	/**
	 * This is what the animal does most of the time: it hunts for rabbits. In the
	 * process, it might breed, die of hunger, or die of old age.
	 * 
	 * @param currentField
	 *            The field currently occupied.
	 * @param updatedField
	 *            The field to transfer to.
	 * @param newAnimals
	 *            A list to add newly born Animals to.
	 */
	public void hunt(Field currentField, Field updatedField, List<Animal> newAnimals) {
		incrementAge();
		incrementHunger();
		if (alive) {
			// New Animals are born into adjacent locations.
			int births = breed();
			for (int b = 0; b < births; b++) {
				Animal newAnimal = new Animal(false);
				newAnimal.setFoodLevel(this.foodLevel);
				newAnimals.add(newAnimal);
				Location loc = updatedField.randomAdjacentLocation(location);
				newAnimal.setLocation(loc);
				updatedField.put(newAnimal, loc);
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
				// can neither move nor stay - overcrowding - all locations
				// taken
				alive = false;
			}
		}
	}

	/**
	 * Increase the age. This could result in the animal's death.
	 */
	private void incrementAge() {
		age++;
		if (age > MAX_AGE) {
			alive = false;
		}
	}

	/**
	 * Make this animal more hungry. This could result in the animal's death.
	 */
	private void incrementHunger() {
		foodLevel--;
		if (foodLevel <= 0) {
			alive = false;
		}
	}

	/**
	 * Tell the animal to look for rabbits adjacent to its current location. Only
	 * the first live rabbit is eaten.
	 * 
	 * @param field
	 *            The field in which it must look.
	 * @param location
	 *            Where in the field it is located.
	 * @return Where food was found, or null if it wasn't.
	 */
	private Location findFood(Field field, Location location) {
		List<Location> adjacentLocations = field.adjacentLocations(location);

		for (Location where : adjacentLocations) {
			Object animal = field.getObjectAt(where);
			if (animal instanceof Rabbit) {
				Rabbit rabbit = (Rabbit) animal;
				if (rabbit.isAlive()) {
					rabbit.setEaten();
					foodLevel = RABBIT_FOOD_VALUE;
					return where;
				}
			}
		}

		return null;
	}

	/**
	 * Generate a number representing the number of births, if it can breed.
	 * 
	 * @return The number of births (may be zero).
	 */
	private int breed() {
		int births = 0;
		if (canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
			births = rand.nextInt(MAX_LITTER_SIZE) + 1;
		}
		return births;
	}

	/**
	 * A animal can breed if it has reached the breeding age.
	 */
	private boolean canBreed() {
		return age >= BREEDING_AGE;
	}

	/**
	 * Check whether the animal is alive or not.
	 * 
	 * @return True if the animal is still alive.
	 */
	public boolean isAlive() {
		return alive;
	}

    /**
     * Tell the animal that it's dead now :(
     */
    public void setEaten()
    {
        alive = false;
    }
	
	/**
	 * Set the animal's location.
	 * 
	 * @param row
	 *            The vertical coordinate of the location.
	 * @param col
	 *            The horizontal coordinate of the location.
	 */
	public void setLocation(int row, int col) {
		this.location = new Location(row, col);
	}

	/**
	 * Set the animal's location.
	 * 
	 * @param location
	 *            The animal's location.
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	public void setFoodLevel(int fl) {
		this.foodLevel = fl;
	}
}

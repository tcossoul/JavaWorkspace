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
	protected int breeding_age;
	// The age to which a animal can live.
	protected int max_age;
	// The likelihood of a animal breeding.
	protected double breeding_probability;
	// The maximum number of births.
	protected int max_litter_size;
	// The food value of this animal when eaten
	protected int food_value;
	
	// A shared random number generator to control breeding.
	private static final Random rand = new Random();

	// Individual characteristics (instance fields).

	// The animal's age.
	protected int age;
	// Whether the animal is alive or not.
	protected boolean alive;
	// The animal's position
	protected Location location;
	// The animal's food level, which is increased by eating rabbits.
	protected int foodLevel;
	// New born food level
	private int foodLevelNewBorn = 6;

	/**
	 * Create a animal. A animal can be created as a new born (age zero and not
	 * hungry) or with random age.
	 * 
	 * @param randomAge
	 *            If true, the animal will have random age and hunger level.
	 */
	public Animal(boolean randomAge, int BREEDING_AGE, int MAX_AGE, double BREEDING_PROBABILITY, int MAX_LITTER_SIZE, int FOOD_VALUE ) {
		age = 0;
		alive = true;
		breeding_age = BREEDING_AGE;
		max_age = MAX_AGE;
		breeding_probability = BREEDING_PROBABILITY;
		max_litter_size = MAX_LITTER_SIZE;
		food_value = FOOD_VALUE;

		// Assume all new animals are born w/ a food level equal to the food level of a rabbit
		if (randomAge) {
			age = rand.nextInt(max_age);
			foodLevel = rand.nextInt( foodLevelNewBorn );
		} else {
			// leave age at 0
			foodLevel = foodLevelNewBorn;
		}
	}

	/**
	 * Increase the age. This could result in the animal's death.
	 */
	protected void incrementAge() {
		age++;
		if (age > max_age) {
			alive = false;
		}
	}

	/**
	 * Make this animal more hungry. This could result in the animal's death.
	 */
	protected void incrementHunger() {
		foodLevel--;
		if (foodLevel <= 0) {
			alive = false;
		}
	}


	/**
	 * Generate a number representing the number of births, if it can breed.
	 * 
	 * @return The number of births (may be zero).
	 */
	protected int breed() {
		int births = 0;
		if (canBreed() && rand.nextDouble() <= breeding_probability) {
			births = rand.nextInt(max_litter_size) + 1;
		}
		return births;
	}

	/**
	 * A animal can breed if it has reached the breeding age.
	 */
	private boolean canBreed() {
		return age >= breeding_age;
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
	
	public int getFoodValue() {
		return food_value;
	}
}

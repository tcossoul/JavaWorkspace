import java.util.List;

public class Rabbit extends Animal {
	private static final int BREEDING_AGE = 5;
	private static final int MAX_AGE = 30;
	private static final double BREEDING_PROBABILITY = 0.06;
	private static final int MAX_LITTER_SIZE = 5;
	private static final int FOOD_VALUE = 6;
	
	public Rabbit(boolean randomAge) {
		super( randomAge, BREEDING_AGE, MAX_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE, FOOD_VALUE );
	}
	
	// Only carnivor animals hunt (herbivors run)
    public void act(Field field, Field updatedField, List<Rabbit> newRabbits)
    {
        incrementAge();
        if(alive) {
            int births = breed();
            for(int b = 0; b < births; b++) {
                Rabbit newRabbit = new Rabbit( false );
                newRabbits.add(newRabbit);
                Location loc = updatedField.randomAdjacentLocation(location);
                newRabbit.setLocation(loc);
                updatedField.put(newRabbit, loc);
            }
            Location newLocation = updatedField.freeAdjacentLocation(location);
            // Only transfer to the updated field if there was a free location
            if(newLocation != null) {
                setLocation(newLocation);
                updatedField.put(this, newLocation);
            }
            else {
                // can neither move nor stay - overcrowding - all locations taken
                alive = false;
            }
        }
    }    
}

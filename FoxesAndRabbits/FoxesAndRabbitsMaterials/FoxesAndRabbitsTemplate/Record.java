/**
 *Recorder to allow serialization of current state of simulation
 * @author Sitar Harel 2012.1.25
 */

import java.io.Serializable;
import java.util.List;


public class Record implements Serializable{
    private List<Rabbit> rabbits;
    private List<Fox> foxes;
    private int steps;
    
    // The current state of the field.
    private Field field;
    
	public Record(List<Rabbit> r, List<Fox> f, Field field, int step){
		setRabbits(r);
		setFoxes(f);
		setField(field);
		setSteps(step);
	}
	public List<Rabbit> getRabbits() {
		return rabbits;
	}
	public void setRabbits(List<Rabbit> rabbits) {
		this.rabbits = rabbits;
	}
	public List<Fox> getFoxes() {
		return foxes;
	}
	public void setFoxes(List<Fox> foxes) {
		this.foxes = foxes;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}
}

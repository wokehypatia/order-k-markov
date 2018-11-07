import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

/**
 * A Markov Object to use in text generation.
 * 
 * @author Asie Mussard-Afcari April 20, 2015 (Happy Donut Day, Glaze It!!) I
 *         affirm I have adhered to the Honor Code in this assessment.
 * 
 */
public class Markov {

    public String substring;
    public int counter = 0;
    public TreeMap<Character, Integer> suffixes = new TreeMap<Character, Integer>();

    public Markov(String substring) {
	this.substring = substring;
    }

    /**
     * Increases the counter so we have a count of how many times a Markov has
     * been added to a hashmap
     */
    public void add() {
	counter++;
    }

    /**
     * Adds a character c to a treemap of possible suffixes for the Markov's
     * substring
     * 
     * @param c
     *            the character to be added to the treemap
     */
    public void add(char c) {
	int integer = 0;
	if (suffixes.get(c) != null) {
	    integer = suffixes.get(c);
	}
	suffixes.put(c, integer + 1);
    }

    /**
     * Pseudorandomly picks a suffix for the markov based on the frequency of
     * each suffix
     * 
     * @return a pseudorandom suffix to follow the markov's substring
     */
    public char random() {
	int count = 0;
	Iterator<Entry<Character, Integer>> itr = suffixes.entrySet()
		.iterator();

	while (itr.hasNext()) {
	    int nextValue = itr.next().getValue();
	    count += nextValue;
	}

	Random obj = new Random();
	int benchmark = obj.nextInt(count) + 1;
	itr = suffixes.entrySet().iterator();
	while (itr.hasNext()) {
	    Entry<Character, Integer> next = itr.next();
	    if (next.getValue() >= benchmark) {
		return next.getKey();
	    } else {
		benchmark -= next.getValue();
	    }
	}
	return suffixes.lastKey();
    }

    /**
     * Creates a string representation of the markov
     * 
     * @return a string representation of the markov
     */
    public String toString() {
	StringBuilder string = new StringBuilder();
	string.append(" :");
	Iterator<Entry<Character, Integer>> itr = suffixes.entrySet()
		.iterator();
	while (itr.hasNext()) {
	    Entry<Character, Integer> next = itr.next();
	    string.append(" " + next.getValue().toString() + " "
		    + next.getKey().toString());
	}
	return string.toString();
    }
}

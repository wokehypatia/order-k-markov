import java.util.Iterator;
import java.util.Scanner;

/**
 * Prints out the number of distinct keys of a user given length in a user given
 * string. Then prints out the frequency of each key in the string.
 * 
 * 
 */
public class FrequencyCounter {

    public static void main(String[] args) {
	MyHashMap<String, Markov> markovMap = new MyHashMap<String, Markov>();
	String string = "";
	int k = Integer.parseInt(args[0]);
	Scanner s = new Scanner(System.in);
	while (s.hasNextLine()) {
	    string = s.nextLine();
	    if (string.equals("exit")) {
		System.exit(1);
	    }
	    for (int i = 0; i < string.length() - k + 1; i++) {
		String substring = string.substring(i, i + k);
		Markov instances = new Markov(substring);
		if (!(markovMap.containsKey(substring))) {
		    instances.add();
		    markovMap.put(substring, instances);
		} else {
		    instances = markovMap.get(substring);
		    instances.add();
		}
	    }
	    System.out.println(markovMap.size + " distinct keys");
	    Iterator<Markov> markitr = markovMap.values();
	    while (markitr.hasNext()) {
		Markov next = markitr.next();
		System.out.println(next.counter + " " + next.substring);
	    }
	    markovMap.clear();
	}
    }

}

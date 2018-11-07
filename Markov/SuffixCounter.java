import java.util.Iterator;
import java.util.Scanner;

/**
 * Prints out the number of distinct keys of a user given length in a user given
 * string. Then prints out the frequency of each key in the string with
 * suffixes.
 * 
 * @author Asie Mussard-Afcari, April 20, 2015. I affirm that I have adhered to
 *         the Honor Code in this assessment.
 * 
 */
public class SuffixCounter {

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
	    for (int i = 0; i < string.length() - k; i++) {
		String substring = string.substring(i, i + k);
		Markov instances = new Markov(substring);
		if (!(markovMap.containsKey(substring))) {
		    instances.add();
		    markovMap.put(substring, instances);
		    markovMap.get(substring).add(string.charAt(i + k));
		} else {
		    instances = markovMap.get(substring);
		    instances.add();
		    markovMap.get(substring).add(string.charAt(i + k));
		}
	    }
	    System.out.println(markovMap.size + " distinct keys");
	    Iterator<Markov> markitr = markovMap.values();
	    while (markitr.hasNext()) {
		Markov next = markitr.next();
		System.out.println(next.counter + " " + next.substring + " "
			+ next.toString());
	    }
	    markovMap.clear();
	}
    }

}

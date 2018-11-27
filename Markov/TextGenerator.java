import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Generates random text from a source file using Markov chains.
 * 
 * 
 */
//Args <size of model in characters, size of output in characters, name of file> 
public class TextGenerator {

    public static void main(String[] args) {
	MyHashMap<String, Markov> markovMap = new MyHashMap<String, Markov>();
	int k = Integer.valueOf(args[0]);
	int M = Integer.valueOf(args[1]);
	int charcounter = 0;
	String file = args[2];
	int nextChar;
	FileReader input = null;
	try {
	    input = new FileReader(file);
	} catch (FileNotFoundException e) {
	    System.err.println("Could not open file " + file + ": "
		    + e.getMessage());
	    System.exit(2);
	}
	String output = "";
	String string = "";
	boolean firstk = false;

	try {
	    while (-1 != (nextChar = input.read())) {
		if (string.length() == k + 1) {
		    string = string.substring(1);
		}
		string += (char) nextChar;
		if (string.length() == k + 1) {
		    Markov markov = new Markov(string.substring(0, k));
		    if (markovMap.containsKey(string.substring(0, k))) {
			markov = markovMap.get(string.substring(0, k));
		    }
		    markov.add(string.charAt(k));
		    markovMap.put(string.substring(0, k), markov);
		    if (firstk == false) {
			output += string.substring(0, k);
			firstk = true;
		    }
		}

	    }
	} catch (IOException e) {
	    System.err.println("Error reading from file " + file + ": "
		    + e.getMessage());
	    System.exit(4);
	}

	String last = output;
	for (int i = 0; i < M; i++) {

	    if (markovMap.get(last) == null) {
		output += output.substring(0, k);
		last = output.substring(0, k);
	    }

	    output += markovMap.get(last).random();
	    last = output.substring(output.length() - k, output.length());
	}
	System.out.print(output + "\n");
    }
}

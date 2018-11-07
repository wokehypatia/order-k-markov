import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

public class MyHashMapTest {

    @Test
    public void testPart1() {
	HashMap<String, Integer> real = new HashMap<String, Integer>();
	MyHashMap<String, Integer> test = new MyHashMap<String, Integer>();
	assertEquals("Both maps are not the same size", real.size(),
		test.size());
	/*
	 * Scanner s = null; try { s = new Scanner( new File(
	 * "/usr/users/quota/students/18/ymussard/cs151/lab8/sotu-obama.txt"));
	 * } catch (FileNotFoundException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } int value = 0; while (s.hasNext()) {
	 * String next = s.next(); real.put(next, value); test.put(next, value);
	 * value += 1; }
	 */
	for (int i = 0; i < 100; i++) {
	    real.put(" " + i, i);
	    test.put(" " + i, i);
	}
	assertEquals("Both maps are not the same size", real.size(),
		test.size());
	real.clear();
	test.clear();
	assertEquals("Both maps are not the same size", real.size(),
		test.size());
	assertTrue(test.isEmpty());
	for (int i = 0; i < 100; i++) {
	    real.put(" " + i, i);
	    test.put(" " + i, i);
	}
	assertTrue("Does not contain", test.containsKey(" " + 70));
	assertFalse(test.containsKey(" " + 500));
	assertTrue(test.containsValue(70));
	assertFalse(test.containsValue(500));
	assertEquals(real.get(" " + 80), test.get(" " + 80));
	for (int i = 0; i < 120; i++) {
	    assertEquals(real.remove(" " + i), test.remove(" " + i));
	}
	assertTrue(test.isEmpty());

    }

    @Test
    public void testMarkov() {
	HashMap<String, Markov> real = new HashMap<String, Markov>();
	MyHashMap<String, Markov> test = new MyHashMap<String, Markov>();
	String string = "asdsdffdfdfdfdfdgfcjkhg";
	for (int i = 0; i < string.length() - 2 + 1; i++) {
	    String substring = string.substring(i, i + 2);
	    Markov instances = new Markov(substring);
	    if (!(test.containsKey(substring))) {
		instances.add();
		test.put(substring, instances);
		real.put(substring, instances);
	    } else {
		instances = test.get(substring);
		instances.add();
		instances = real.get(substring);
		instances.add();
	    }
	}
	for (int i = 0; i < real.size(); i++) {
	    assertEquals(real.remove(" " + i), test.remove(" " + i));
	}
    }

}

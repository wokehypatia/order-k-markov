import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * An implementation of a HashMap.
 * 
 * @author Asie Mussard-Afcari April 20, 2015 I affirm that I have adhered to
 *         the Honor Code in this assessment.
 * @param <K>
 *            key of the hashmap entry
 * @param <V>
 *            value of the hashmap entry
 */
public class MyHashMap<K, V> {

    LinkedList<MyEntry>[] table;

    int size = 0;

    float loadFactor;

    /**
     * HashMap constructor.
     * 
     * @param capacity
     *            the starting size of the hashmap
     * @param loadFactor
     *            the degree to which the hashmap can be filled before resizing
     */
    private void MyHashMap(int capacity, float loadFactor) {
	this.loadFactor = loadFactor;
	table = (LinkedList<MyEntry>[]) new LinkedList[capacity];
	for (int i = 0; i < capacity; i++) {
	    table[i] = new LinkedList<MyEntry>();
	}

    }

    /**
     * Constructs a hashmap with default capacity and LoadFactor
     */
    MyHashMap() {
	MyHashMap(11, (float) 0.75);
    }

    /**
     * 
     * @return the number of elements in the hashmap
     */
    public int size() {
	return this.size;
    }

    /**
     * 
     * @return true if the hashmap is empty, false otherwise
     */
    public boolean isEmpty() {
	if (this.size() == 0) {
	    return true;
	}
	return false;
    }

    /**
     * Clears out the hashmap and resets size to 0.
     */
    public void clear() {
	for (int i = 0; i < table.length; i++) {
	    this.table[i].clear();
	}
	size = 0;
    }

    /**
     * Creates a string representation of the hashmap.
     * 
     * @return
     */
    public String toString() {
	StringBuilder hsBuilder = new StringBuilder();
	for (int i = 0; i < size(); i++) {
	    hsBuilder.append("{ " + this.table[i].toString() + " }");
	}
	return hsBuilder.toString();

    }

    /**
     * Adds an item into the correct place in the hashmap. If it already has an
     * item with the same key, returns the previous value associated with the
     * key.
     * 
     * @param key
     *            the key of the entry
     * @param value
     *            the value of the entry
     * @return the previous value associated with the key, null if the key has
     *         never been added to the hashmap
     * @throws NullPointerException
     *             if someone tries to add an entry with a null key or value,
     *             throws a NullEntryException
     */
    public V put(K key, V value) throws NullPointerException {
	if (key == null || value == null) {
	    throw new NullPointerException(
		    "You cannot add an entry with a null key and/or value.");
	}
	if ((float) (this.size + 1) / table.length >= loadFactor) {
	    resize();
	}
	int hash = MyHash(key, this.table.length);
	ListIterator<MyEntry> listiterator = this.table[hash].listIterator();
	while (listiterator.hasNext()) {
	    MyEntry prevEntry = listiterator.next();
	    if (prevEntry == key) {
		V prevValue = prevEntry.value;
		prevEntry.value = value;
		size += 1;
		return prevValue;
	    }
	}
	this.table[hash].add(new MyEntry(key, value));
	size += 1;
	return null;

    }

    /**
     * Calculates the hash of a given key to simplify my code.
     * 
     * @param key
     *            the key we are getting the hash of
     * @param maxsize
     *            the size of the hashmap we are going to be putting the key
     *            into
     * @return the hash of the key
     */
    private int MyHash(K key, int maxsize) {
	return Math.abs(key.hashCode() % maxsize);
    }

    /**
     * Alters the size of the table to be the first prime that is double the old
     * size.
     */
    private void resize() {
	int prime = primes();
	LinkedList<MyEntry>[] newtable = (LinkedList<MyEntry>[]) new LinkedList[prime];
	int newtablesize = 0;
	for (int k = 0; k < prime; k++) {
	    newtable[k] = new LinkedList<MyEntry>();
	}
	for (int j = 0; j < table.length; j++) {
	    ListIterator<MyEntry> listiterator = table[j].listIterator();
	    while (listiterator.hasNext()) {
		MyEntry next = listiterator.next();
		int hash = MyHash(next.key, newtable.length);
		newtable[hash].add(next);
		newtablesize += 1;
	    }
	}
	table = newtable;
	this.size = newtablesize;
    }

    /**
     * Returns the value associated with a given key
     * 
     * @param key
     *            the key we want the value of
     * @return the value associated with key
     */
    V get(K key) {
	int hash = MyHash(key, table.length);
	ListIterator<MyEntry> listiterator = table[hash].listIterator();
	while (listiterator.hasNext()) {
	    MyEntry next = listiterator.next();
	    if (next.key.equals(key)) {
		return next.value;
	    }
	}
	return null;
    }

    /**
     * Removes a given key
     * 
     * @param key
     *            the key to be removed
     * @return the value associated with the key removed, null if the key does
     *         not exist in the hashmap
     */
    V remove(K key) {
	int hash = MyHash(key, table.length);
	ListIterator<MyEntry> listiterator = table[hash].listIterator();
	while (listiterator.hasNext()) {
	    MyEntry next = listiterator.next();
	    if (next.key.equals(key)) {
		V prevValue = next.value;
		table[hash].remove(next);
		this.size -= 1;
		return prevValue;
	    }
	}
	return null;
    }

    /**
     * Checks if a key is contained in the hashmap
     * 
     * @param key
     *            the key we are searching for
     * @return true if the key is in the hashmap, false otherwise
     */
    boolean containsKey(K key) {
	MyEntry holdKey = new MyEntry(key, null);
	int hash = MyHash(key, table.length);
	LinkedList<MyEntry> bucket = table[hash];
	if (bucket.contains(holdKey)) {
	    return true;
	}
	return false;
    }

    /**
     * Checks if a value is contained in the hashmap
     * 
     * @param value
     *            the value we are searching for
     * @return true if the value is in the hashmap, false otherwise
     */
    boolean containsValue(V value) {
	MyEntry holdValue = new MyEntry(null, value);
	Iterator<V> valueitr = values();
	while (valueitr.hasNext()) {
	    if (valueitr.next() == holdValue.value) {
		return true;
	    }
	}
	return false;
    }

    /**
     * An iterator of all the keys in the hashmap
     * 
     * @return a new iterator of the hashmap keys
     * @throws NoSuchElementException
     */
    public Iterator<K> keys() throws NoSuchElementException {

	return new Iterator<K>() {
	    int bucket = 0;
	    Iterator<MyEntry> itr = table[bucket].iterator();
	    int nextCount = 0;

	    public boolean hasNext() {
		if (nextCount < size) { // can just check nextCount and size
		    return true;
		}
		return false;
	    }

	    public K next() {
		if (!(hasNext())) { // if my hasNext() is false, I should throw
				    // a NoSuchElementException
		    throw new NoSuchElementException(
			    "There is no item to get there!");
		}
		while (!(itr.hasNext())) { // while itr.hasNext() is false,
					   // increment bucket and get the next
					   // iterator
		    if (bucket + 1 < table.length) {
			bucket += 1;
			itr = table[bucket].iterator();
		    }
		}
		nextCount += 1; // now increment nextCount and return the key
				// from the item itr.next() returns
		return itr.next().key;
	    }

	    public void remove() {
		nextCount -= 1;
		size -= 1;
		itr.remove(); // just ask itr to remove, but I need to update my
			      // size and nextCount

	    }

	};

    }

    /**
     * An iterator of all the values in the hashmap
     * 
     * @return a new iterator of the hashmap values
     * @throws NoSuchElementException
     */
    public Iterator<V> values() throws NoSuchElementException {

	return new Iterator<V>() {
	    int bucket = 0;
	    Iterator<MyEntry> itr = table[bucket].iterator();
	    int nextCount = 0;

	    public boolean hasNext() {
		if (nextCount < size) { // can just check nextCount and size
		    return true;
		}
		return false;
	    }

	    public V next() {
		if (!(hasNext())) { // if my hasNext() is false, I should throw
				    // a NoSuchElementException
		    throw new NoSuchElementException(
			    "There is no item to get there!");
		}
		while (!(itr.hasNext())) { // while itr.hasNext() is false,
					   // increment bucket and get the next
					   // iterator
		    if (bucket + 1 < table.length) {
			bucket += 1;
			itr = table[bucket].iterator();
		    }

		}
		nextCount += 1; // now increment nextCount and return the key
				// from the item itr.next() returns
		return itr.next().value;
	    }

	    public void remove() {
		nextCount -= 1;
		size -= 1;
		itr.remove(); // just ask itr to remove, but I need to update my
			      // size and nextCount

	    }

	};

    }

    /**
     * Calculates the next prime that is double the size of our hashmap for use
     * in our resize method
     * 
     * @return the right prime
     */
    private int primes() {
	int prime = 1685759167;
	int[] primes = { 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421,
		12853, 25717, 51437, 102877, 205759, 411527, 823117, 1646237,
		3292489, 6584983, 13169977, 26339969, 52679969, 105359939,
		210719881, 421439783, 842879579, 1685759167 };
	for (int i = 0; i < primes.length; i++) {
	    if (table.length * 2 + 1 <= primes[i]) {
		prime = primes[i];
		return prime;
	    }
	}
	return prime;
    }

    /**
     * A class of entries for the hashmap.
     * 
     * 
     */
    private class MyEntry {

	K key;

	V value;

	private MyEntry(K key, V value) {
	    this.key = key;
	    this.value = value;
	}

	public int hashCode() {
	    return this.key.hashCode();
	}

	public boolean equals(Object obj) {
	    return this.key.equals(((MyEntry) obj).key);
	}

	public String toString() {
	    StringBuilder meBuilder = new StringBuilder();
	    meBuilder.append(this.key.toString() + " = "
		    + this.value.toString());
	    return meBuilder.toString();
	}
    }
}

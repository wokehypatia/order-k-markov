Yasmeen Mussard-Afcari

A Markov model for generating stylized pseudo-random text.

TextGenerator takes as command line input an integer k, an integer M, and a filename file, and prints out M characters according to the order k Markov model based on file.

FrequencyCounter reads the order parameter k of the Markov model from the command-line, a text string from System.in, and uses a hash table to insert each k-character substring (key) from the text.

SuffixCounter, based off FrequencyCounter, inserts keys into the hash table (if necessary), and calls add(char c) to add the appropriate suffix characters to the Markov model.

The Markov class represents a k-character substring with a random method that returns a random character according to the Markov model.

My own hash map is implemented with separate chaining in a class called MyHashMap<K,V>.

MyHashMapTest.java is a JUnit test class for MyHashMap.

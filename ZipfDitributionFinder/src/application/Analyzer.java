package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Analyzer {
	int j;
	FileInputStream fis;
	String content;
	
	/**
	 * Read a file and return its content as a string.
	 */
	public String readFile(String filename)	{
		content = "";
		try	{
		fis = new FileInputStream(filename);
		
		do	{
			j = fis.read();
			if(j!=-1)
				content += (char) j;
		}	while(j!=-1);
		
		}	catch(IOException e)	{
			System.out.println("IO exception: " + e);
		}
		
		return content;
	}
	
	/**
	 * identify individual words in a string and put them into an ArrayList.
	 * the end of a word is defined as the position where the next char is not a letter or number.
	 * the beginning of a word is defined as the position of the first letter or number after the end of a word.
	 * the boolean countNumber determines if numbers qualify as words.
	 */
	public ArrayList<String> divideWords(String words, boolean countNumbers)	{
		char[] wordsAr = words.toCharArray();
		ArrayList<String> wordList = new ArrayList<>();
		String word = "";
		boolean lastLetter = false;			//indicates if the last char is a letter

		for(int i=0;i<words.length();i++)	{
			boolean isWord = countNumbers? 
					(wordsAr[i]>=65 && wordsAr[i]<=90) || (wordsAr[i]>=97 && wordsAr[i]<=122) || (wordsAr[i]>=48 && wordsAr[i]<=57)	
					:
					(wordsAr[i]>=65 && wordsAr[i]<=90) || (wordsAr[i]>=97 && wordsAr[i]<=122);
					//current char is an: uppercase letter | lowercase letter | number
			if(isWord)	{
				word += wordsAr[i];
				lastLetter = true;
			}
			else	{
				if(lastLetter)	{
					wordList.add(word);
					word = "";
					lastLetter = false;
				}
			}
		}	
		if(lastLetter)	
			wordList.add(word);
		
		return wordList;
	}
	
	static String removeS(String s)	{
		return s.substring(0, s.length()-1);
	}
	static String removeEs(String s)	{
		return s.substring(0, s.length()-2);
	}
	
	/**
	 * get a map containing the words in an ArrayList and their frequencies of appearance.
	 * all words added will be converted to lower case
	 */	
	public Map<Integer, String> getMap(ArrayList<String> words)	{
		//key is word (String); value is frequency (int)
		TreeMap<String, Integer> wordMap = new TreeMap<>();
		for(String word:words)	{
			if(!wordMap.containsKey(word.toLowerCase()))			//doesn't contain exact word
				wordMap.put(word.toLowerCase(), 1);
			else
				wordMap.put(word.toLowerCase(), wordMap.get(word.toLowerCase())+1);
		}
		
		//can't sort by value; reverse key and value and sort by key
		TreeMap<Integer, String> map2 = new TreeMap<>();
		Set<Entry<String, Integer>> keySet = wordMap.entrySet();
		Iterator<Entry<String, Integer>> itr = keySet.iterator();
		while(itr.hasNext())	{
			Entry<String, Integer> next = itr.next();
			map2.put(next.getValue(), next.getKey());
		}
		return map2;
	}	
	
	/**
	 * returns the ordinal form of n (e.g. 1st, 3rd, 11th) as a string.
	 */
	static String getOrdinal(int n)	{
		int lastDigit = n%10;
		if(n!=11 && lastDigit==1)	
			return Integer.toString(n) + "st";
		if(n!=12 && lastDigit==2)	
			return Integer.toString(n) + "nd";
		if(n!=13 && lastDigit==3)
			return Integer.toString(n) + "rd";
		else
			return Integer.toString(n) + "th";
	}
	
	/**
	 * trim a double to the significant digits given by sig.
	 */
	static double toSignificantDigits(double val, int sig)	{
		if(sig<0) throw new IllegalArgumentException();
		
		BigDecimal bd = BigDecimal.valueOf(val);
		bd = bd.setScale(sig, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	/**
	 * list the frequencies of the most common words, and how they compare with the most common word.
	 */
	public void listFreq(TreeMap<Integer, String> map)	{

		Set<Entry<Integer, String>> mapSet = map.descendingMap().entrySet();
		int firstFreq = map.descendingMap().firstKey(),
				idx = 1;
		double fraction;
		for(Entry<Integer, String> entry : mapSet)	{
			System.out.print(getOrdinal(idx++) + " word: \"" + entry.getValue() + "\" -- ");			// name of word and position
			System.out.print(entry.getKey() + " times;  ");			//frequency
			fraction =  toSignificantDigits((double) firstFreq/entry.getKey(), 3);
			System.out.println("(1/" + fraction+ ")");
		}
	}
	
	public static void main(String args[])	{
		Analyzer an = new Analyzer();
		String file = "C:/Users/Mmyro/Desktop/zipfs law.txt",
				content = an.readFile(file);
		ArrayList<String> wordList = an.divideWords(content, false);
		TreeMap<Integer, String> wordMap = (TreeMap<Integer, String>) an.getMap(wordList);
		an.listFreq(wordMap);
		//System.out.println(wordMap);
	}
}
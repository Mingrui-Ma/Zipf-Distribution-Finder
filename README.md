# Zipf-Distribution-Finder

### Background Information 

_(From Wikipedia))_

Zipf's law is an empirical law formulated using mathematical statistics that refers to the fact that many types of data studied in the physical and social sciences can be approximated with a Zipfian distribution, one of a family of related discrete power law probability distributions.

Zipf's law was originally formulated in terms of quantitative linguistics, stating that given some corpus of natural language utterances, the frequency of any word is inversely proportional to its rank in the frequency table. Thus the most frequent word will occur approximately twice as often as the second most frequent word, three times as often as the third most frequent word, etc.

### Project Idea

Zipf's law applies to all languages known to men when viewed from a macroscopic level. But can Zipfian distributions be found on writings of a smaller scale (e.g. an article)? This software aims to answer that question (that probably only I had asked). It does so by analyzing a given text file and lists the frequencies of words from most to least common, and display their frequency ratios compared with the most common word.

_technical description_

1. The user chooses a file. A _FileInputStream_ reads the file and convert its content to a String. (This gives the file a length limit of 2^31 - 1 characters)
1. A method divides the String into individual words and store them in an ArrayList. The method identifies words as chunks of characters delimited by non-letter characters.
1. A method analyzes the frequencies of the words and store the word-frequency pairs in a TreeMap. The TreeMap also identifies the words spelled with lower and upper cases of the same letters ("professor" vs "Professor") and singular and plural forms (doesn't work very well right now) and treat them as the same word.


--------------------------------------------------------
### Project Progress

Currently (Oct-10-2020) the program analyzing the text file is complete (with a few changes I intend to implement). The file is chosen by editing a String in the main() method, and the output is displayed in the console.

The next step is to implement a GUI. I will be using JavaFX for this.


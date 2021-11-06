# Project specification

The goal of this project is to implement Lempel-Ziv-Welch (LZW) and the Huffman coding algorithm in Java.
The performance of the implemented algorithms could be compared side by side in terms of execution time as well as compression rate.
Ideally, the target for the compression rate for both cases would be at 40-60 %. 

I can write peer reviews for projects written in Python or Java.
Voin myös arvioida suomenkielisiä projekteja.

This project is for the BSc of Computer science and will be written entirely in English. 

## Data structures, Significance of topic as well as reasons for choosing it

From the JAVA standard library, the data structures, HashMap and Priority Queue, will be used
for the LZW-algorithm and Huffman-algorithm respectively.

Because, we are living in a data driven world, being able to retain the same information in a smaller file size is of tremendous practical use given that data storage is expensive. To solve this problem, LZW and Huffman coding are both well-known approaches that provide lossless data compression. 

The topic was chosen out of my own personal curiosity as I've wondered for a while about the inner workings of 
lossless compression algorithms.

## Inputs as well as time and space complexity

Both small and big inputs will be considered to determine the compression rate. Generally, the bigger the file, the more likely it is that the compression rate is higher. However, the compression rate is largely determined by the actual contents of the input file. 

As of currently, the possibilities for the inputs I've considered are an excerpt from a book, Lorem ipsum and so forth. 

The complexity is at minimum O(n) since we need to traverse every single element of the input in order to able to compress it. 
LZW only uses a dictionary as its data structure with operations at O(1), so the overall complexity would be O(n).

However, Huffman encoding also uses a binary tree which operates at O(log(n)). Because the binary tree is traversed for each encoded element, the overall complexity would be at O(nlog(n)). 

## Sources

[Huffman](https://en.wikipedia.org/wiki/Huffman_coding)
[LZW](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch)











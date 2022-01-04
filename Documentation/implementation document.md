# Implementation document

The project consists of two classes named after the algorithms they implement, ```Huffman.java``` and ```LZW.java```. The program is executed primarily as a GUI app where the ```ui.Ui.java``` and ```ui.MainSceneController.java``` files handle all of the UI functions. Another component are the performance and compression benchmarks located in the ```benchmarks``` folder. 


Additional utility classes were required particularly for the Huffman implementation. ```Node.java``` is aimed to represent the Huffman Node Tree and the class ```HuffmanUtils``` provides utility methods to help read and write the outputs of the Huffman algorithm.
```FileUtils``` class provides the necessary File I/O functionalities of the program.

All of the encoded outputs are binary strings, which is then converted into bytes in order to produce an output file. The opposite happens when reading a file: output file first converted into bytes and then into a binary string. 

## Structure of the implementation

For both algorithms, the encoding process is defined such that it includes the construction of a binary string output in order to ensure the same exact definition for what is encoding. When decoding, both algorithms start after preprocessing a file where the necessary input values are already given. For Huffman these are the binary string encoded input as well as the Huffman node tree and for LZW algorithm the encoded values as an ArrayList-object. 

The complexity is at minimum ```O(n)``` for both algorithms since we need to traverse every single element of the input in order to able to compress it and afterwards decode the original message. 

## Optimization 

Construction of the encoded message as a binary string was optimized using the ```java.util.StringBuffer``` class. Concatenating with the Java String type leads to an exponential slow don in performance in relation to input size. This can be best seen in the following benchmark comparisons. 

```
_________Huffman Performance with String________
    Name of file          Encode          Decode       
100_KB_lorem.txt            8.88         1039.09       
   ASCII_256.txt            0.25             0.1       
    artofwar.txt           26.11        10467.18       
100_KB_cScSc.txt            8.09           999.1       

______Huffman Performance with StringBuffer_____
    Name of file          Encode          Decode       
100_KB_lorem.txt         8.42 ms        11.62 ms       
   ASCII_256.txt         0.05 ms         0.05 ms       
    artofwar.txt        26.73 ms        39.42 ms       
100_KB_cScSc.txt         7.73 ms         3.88 ms 
```

The slow down in performance is even more problematic with LZW as it could not finish running the benchmarks even after several minutes.Additionally, the program could not even finish executing due to it running out of heap space (limit was set at the default of 128 MB). Initially, the ```java.util.StringBuilder``` class was used to solve performance related issues, but ultimately, it introduced the heap space limit as the new bottleneck, since memory usage grows with StringBuilder. 


StringBuffer was chosen as the output-message constructing class, as it has the same performance capabilities as StringBuilder, but does not hog memory resources the same way as a StringBuilder. This enabled the encoding of very large files such as ```Large Lorem.txt```, which has a file size of almost 28 MB. Therefore, for both LZW and Huffman, whenever concatenation of a String is required, a StringBuffer will be used. 

### Huffman 

The current implementation of Huffman performs at ```O(n log(k))```, where n is the size of the input and k the number of nodes in the final Huffman Node tree. When traversing through the Huffman Node Tree, the average seek time on average is ```O(log k)``` as this is a binary search tree implementation. 

From the line graphs provided in the [testing document](https://github.com/limi96/compression-algorithm/blob/master/Documentation/testing%20document.md), the performance seems to be strictly linear in relation to input size. 

The achieved space complexity is ```O(k+n)```, where k is the number of nodes in the Huffman tree and n is the size of the input. 

### LZW 

The current implementation of LZW performs at ```O(n * m^2 / 16)```, where n is the size of the input and m the desired bit length of each of the elements in the encoded output. The number 16 represents the bit size of each character in Java. The input is first compressed to a length of ```(n*m/16)``` after which the algorithm copies each of the bits by the specified bit length m. Therefore, the entire algorithm runs through entire input ``` (n + (n*m^2/16))``` resulting in an overall time complexity of ```O(n * m^2 / 16)```. From the line graphs provided in the [testing document](https://github.com/limi96/compression-algorithm/blob/master/Documentation/testing%20document.md), the performance seems to be linear, but at very very large inputs (at 10^8), encoding and file reading do seem to slow down. The slowness of the encoding and file reading is due to the methods ```bitsToint()``` and ``` intToBits()``` in the FileUtils-class, which effectively multiplies the given input size by the desired bit length set in the LZW-class. However, this implementation was necessary to be able to produce a binary String output. 

The longer the length of encoded bits, the higher the maximum dictionary size that stores reoccuring patterns. With messages of large numbers of frequently occuring patterns, the longer the better. However, this will result in a longer encoded message. Nevertheless, the optimal bit length is usually around 9-12 bits. 

The achieved space complexity is ```O(n)``` as no additional information is processed. When converting to a specific bit length at the default of 12, the algorithm encodes 12 bits at a time and with the StringBuffer-class, it will empty the buffer. Therefore, with the default bit length of 12, the space complexity will only increase by a default amount of 192 bits (12 Java String characters which is 12*16 = 192 bits). The maximum increase is 32*16 = 512. 

## Comparison of Huffman and LZW

Please read the detailed comparison in the form of graphical data and findings concerning performance and compression in the [testing document](https://github.com/limi96/compression-algorithm/blob/master/Documentation/testing%20document.md). 

## Limitations 

Conversion of Java int to bits and vice versa is a huge bottleneck for LZW, so much so that LZW is effectively slower at encoding than Huffman. In most implementations LZW is considered faster at ```O(n)``` vs. Huffman at ```O(n log(k))```. Optimization of the conversion Java int conversion to binary strings and vice versa would greatly improve the performance of the current LZW implementation. 

Implementation of the Huffman Node Tree as a Ternary Search Tree (TST) could potentially greatly reduce the space complexity as well as the time complexity of the Huffman algorithm. The increase in performance could be significant with large Huffman trees and very large inputs especially during the decoding process, which was the bottleneck for the current Huffman implementation.

## Sources

[Huffman](https://en.wikipedia.org/wiki/Huffman_coding) Accessed at 26.12.2021

[Huffman pseudocode](http://cs.bc.edu/~alvarez/Algorithms/Notes/huffman.pdf) Accessed at 26.12.2021

[LZW](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch) Accessed at 26.12.2021

[LZW pseudocode](https://cs.carleton.edu/faculty/jondich/courses/cs337_w00/Assignments/lzw.html) Accessed at 26.12.2021


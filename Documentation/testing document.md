# Testing document

Project is now ready. Will be updating this document in detail. Testing consists of three parts: Unit tests, Performance tests and Compression tests. 

## Unit tests

Unit tests were implemented using JUnit and tests were designed for all classes except Benchmark-classes and UI-classes. 
Current lines coverage is 97 % and branch coverage 94 %.

<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/jacococoverage.png">

### Design of unit tests 

For the FileUtils-, Huffman-, HuffmanUtils- and LZW-classes, all input and output methods are tested with randomly generated String inputs. In the case of each individual randomly generated String, the tests check whether or not we can arrive to the same randomly generated String after encoding and decoding or writing and reading. 

If one wishes to adjust the parameters of these tests, ```numberOfRepetitions``` and ``` lengthOfInput``` are the global variables described in the ```public void setUp()``` class that adjust their namesake parameters. The default values are 1000 repetitions at 10000 input length. After this, all tests can be run with the command ```mvn test```. 

### UI-testing

UI-testing was done manually and all edge cases should be accounted for. The program will indicate an error in the right hand side text-field should there be an invalid input or input parameter. If trying to open a file that does not exist, the terminal will print a stack error of InvocationTargetException. These should be the only type of errors not accounted for. All other scenarios should be handled by the implemented error messages. 

## Current unedited version of the results

[Compression data](https://github.com/limi96/compression-algorithm/blob/master/Documentation/Compression%20data.txt)

[Performance data](https://github.com/limi96/compression-algorithm/blob/master/Documentation/Performance%20data.txt)

## Description of the purpose of the test files

- **100_KB_lorem.txt**: Fairly random, but repetetive natural language

- **artofwar.txt**: Natural English language.

- **warandpeace.txt**: Natural English language. Representative of a very long book in English. 

- **100_KB_cScSc.txt**: cScSc repeted to a 100 KB. Used as an extreme case to evaluate how high of a compression could be achieved. Therefore, can be considered as a positive control and also an edge case test for the LZW algorithm.

- **ASCII_256.txt**: Tests if algorithms are able to encode all 256 of ASCII UTF-8 characters. Functions mainly as a negative control, as these algorithms should not be able to compress to a smaller size since this is the extreme form of no repetition. 

- **Large Lorem.txt**: A 28 MB monster constructed from repeating the contents of 100_KB_lorem.txt. This acts mainly as a performance test, but also checks if compression rates are similar to that of 100_KB_lorem.txt. In theory, they should be, since the content is exactly the same, but repeated several times. 

## Performance results 

### Details of testing performances

All tests were done such that each method is tested 100 times and then extreme outliers are removed (2 of the highest compilation times and 1 lowest), because of JIT (Just in time) compilation and memory garbage collection. The results are then sorted and then the median value is picked. 

Performance tests for below 10000 input sizes are not shown, due to the unwanted effects of JIT compilation and memory garbage collection tampering with the performances. These affect the performance times such that no conclusions can be accurately drawn upon the data. 

### Huffman time complexity with growing input sizes

<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/huffmanlinechartsmall.png" width="600">
<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/huffmanlinechartbig.png" width="600">

From the Huffman algorithm line charts, we can evidently see that the time complexity grows scritly linearly. With decoding taking significantly more time than rest of the processes. This is quite expected as the algorithm needs to constantly iterate through the constructed binary tree in order to construct the original message. 


<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/LZWlinechartsmall.png" width="600">
<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/LZWlinechartsmall.png" width="600">

### LZW time complexity with growing input sizes

With the LZW algorithm, we can observe that reading and encoding are taking the longest times and that their growth is faster than linear. This is most likely due to the methods ```bitsToint()``` and ``` intToBits()``` in the FileUtils-class, which effectively multiplies the given input size by the desired bitLength set in the LZW-class. However, this implementation was necessary to be able to produce a binary String output. Although, the LZW algorithm should generally be faster than the Huffman algorithm, due to the time complexity being O(n), our current implementation is O(nm^2/16), where m is the desired bitLength and 16 represents the bitsize of each character in Java. 

### Performance comparison with random inputs 

<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/encodingcomparison.png" width="600">
<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/decodingcomparison.png" width="600">
<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/writefilecomparison.png" width="600">
<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/readfilecomparison.png" width="600">

We can clearly see that for all processes except decoding, the Huffman implementation was faster. As stated earlier, this is primarily due to the LZW-algorithm using  ```bitsToint()``` and ``` intToBits()``` and methods. This would also explain why decoding takes more time for Huffman as the decoding process requires the constant traversing of the Huffman binary tree. These two processes are the most performance taxing in the implementation of the two algorithms. 

### Performance comparison with test files

<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/smalltestfilecomparison.png">
<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/bigtestfilecomparison.png">

We can see the findings of our earlier results from random inputs, to also apply for the given various test files. It also gives important insights to see which processes take the longest for each algorithm. For Huffman, these would be encoding and decoding while for LZW, they are encoding and reading the file. 


## Compression results 

Compression tests were only done with test files as random inputs do not produce reoccuring patterns that allow meaningful ways to compare the two algorithms in terms of compression. This is due to the fact that LZW relies on reoccuring patterns. 

### Main Findings

- Large Lorem and 100_KB_lorem both yield in the same compression rates in both algorithms. This would make sense as the file contents are essentially the same as Large Lorem is just repetition of 100_KB_lorem. 
- Natural languages achieve similar compression rates for both algorithms at around 50 % (artofwar, warandpeace). 
- 100_KB_lorem compression is higher for LZW than Huffman (34 % vs. 54 %) most likely due to the lorem text being highly repetetive by definition. 
- Huffman compressions are always larger for small input sizes due to the size needed to allocate for the serialized huffman tree. 
- For the Huffman algorithm, the fraction that tree data occupies in the total file size decreases considerably, the bigger the given input
- Both algorithms were not able to compress the ASCII_256 text file as it functioned as a "negative control"
- LZW is compresses always more efficiently than the Huffman algorithm, the more reoccuring patterns there are. This is seen in the results of the 100_KB_cScSc file. 

### 100_KB_lorem

_____________Testing Huffman Compression Rates_____________
Filename                                    : test_files/100_KB_lorem.txt
Input   Message length        (in bits)     : 1605280
Encoded Message length        (in bits)     : 429442
Input File size               (in bytes)    : 100330
Huffman Compressed File size  (in bytes)    : 54398
Huffman Tree File size        (in bytes)    : 716
Huffman Message File size     (in bytes)    : 53682
Huffman compression rate                    : 54.22 %
Compression rate Without Tree               : 53.51 %


_____________Testing LZW Compression Rates_____________
Filename                                    : test_files/100_KB_lorem.txt
Input   Message length        (in bits)     : 1605280
Encoded Message length        (in bits)     : 275208
Input File size               (in bytes)    : 100330
LZW Compressed File size  (in bytes)        : 34401
LZW compression rate                        : 34.29 %

### Large Lorem

_____________Testing Huffman Compression Rates_____________
Filename                                    : test_files/Large Lorem.txt
Input   Message length        (in bits)     : 446263408
Encoded Message length        (in bits)     : 119382660
Input File size               (in bytes)    : 27891463
Huffman Compressed File size  (in bytes)    : 14923550
Huffman Tree File size        (in bytes)    : 716
Huffman Message File size     (in bytes)    : 14922834
Huffman compression rate                    : 53.51 %
Compression rate Without Tree               : 53.50 %


_____________Testing LZW Compression Rates_____________
Filename                                    : test_files/Large Lorem.txt
Input   Message length        (in bits)     : 446263408
Encoded Message length        (in bits)     : 72582176
Input File size               (in bytes)    : 27891463
LZW Compressed File size  (in bytes)        : 9072772
LZW compression rate                        : 32.53 %

## Art of war
_____________Testing Huffman Compression Rates_____________
Filename                                    : test_files/artofwar.txt
Input   Message length        (in bits)     : 5155968
Encoded Message length        (in bits)     : 1499670
Input File size               (in bytes)    : 322248
Huffman Compressed File size  (in bytes)    : 188924
Huffman Tree File size        (in bytes)    : 1464
Huffman Message File size     (in bytes)    : 187460
Huffman compression rate                    : 58.63 %
Compression rate Without Tree               : 58.17 %

_____________Testing LZW Compression Rates_____________
Filename                                    : test_files/artofwar.txt
Input   Message length        (in bits)     : 5155968
Encoded Message length        (in bits)     : 1323576
Input File size               (in bytes)    : 322248
LZW Compressed File size  (in bytes)        : 165447
LZW compression rate                        : 51.34 %


### War and peace

_____________Testing Huffman Compression Rates_____________
Filename                                    : test_files/warandpeace.txt
Input   Message length        (in bits)     : 52353312
Encoded Message length        (in bits)     : 14902645
Input File size               (in bytes)    : 3272082
Huffman Compressed File size  (in bytes)    : 1864249
Huffman Tree File size        (in bytes)    : 1417
Huffman Message File size     (in bytes)    : 1862832
Huffman compression rate                    : 56.97 %
Compression rate Without Tree               : 56.93 %


_____________Testing LZW Compression Rates_____________
Filename                                    : test_files/warandpeace.txt
Input   Message length        (in bits)     : 52353312
Encoded Message length        (in bits)     : 13248800
Input File size               (in bytes)    : 3272082
LZW Compressed File size  (in bytes)        : 1656100
LZW compression rate                        : 50.61 %

### 100_KB_cScSc

_____________Testing Huffman Compression Rates_____________
Filename                                    : test_files/100_KB_cScSc.txt
Input   Message length        (in bits)     : 1600016
Encoded Message length        (in bits)     : 150013
Input File size               (in bytes)    : 100001
Huffman Compressed File size  (in bytes)    : 18806
Huffman Tree File size        (in bytes)    : 53
Huffman Message File size     (in bytes)    : 18753
Huffman compression rate                    : 18.81 %
Compression rate Without Tree               : 18.75 %


_____________Testing LZW Compression Rates_____________
Filename                                    : test_files/100_KB_cScSc.txt
Input   Message length        (in bits)     : 1600016
Encoded Message length        (in bits)     : 13328
Input File size               (in bytes)    : 100001
LZW Compressed File size  (in bytes)        : 1666
LZW compression rate                        : 1.67 %

### ASCII 256

_____________Testing Huffman Compression Rates_____________
Filename                                    : test_files/ASCII_256.txt
Input   Message length        (in bits)     : 4320
Encoded Message length        (in bits)     : 1317
Input File size               (in bytes)    : 270
Huffman Compressed File size  (in bytes)    : 3221
Huffman Tree File size        (in bytes)    : 3055
Huffman Message File size     (in bytes)    : 166
Huffman compression rate                    : 1192.96 %
Compression rate Without Tree               : 61.48 %


_____________Testing LZW Compression Rates_____________
Filename                                    : test_files/ASCII_256.txt
Input   Message length        (in bits)     : 4320
Encoded Message length        (in bits)     : 2112
Input File size               (in bytes)    : 270
LZW Compressed File size  (in bytes)        : 264
LZW compression rate                        : 97.78 %
# Testing document

## Current scope

Currently, the testing program only checks for the construction of the Node class and the correct decoding output of the Huffman-algorithm.

The current progress is very limited due to the lengthy process of trying to make the Java testing units to work. Ultimately, this was resolved by resorting to 4.12 JUnit. 

## Future additions

First and foremost would be to fix the output of the LZW-algorithm to match its input and create tests around that.

Then tests for the auxiliary methods of the Huffman-algorithm such as the formation of the Huffman Tree and the generation of the codes for each character using the Huffman tree. 



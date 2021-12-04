# Testing document

## Current scope

- tests for Node class
- tests for Huffman and LZW that ensure that after encoding the input, the decoded value equals to the original input
- few tests for Huffman class ensuring individual methods are working correctly. This is still quite inadequate.
- Need to figure out why the bytesize is different from the rootnodes' frequency for ASCII_256.txt, but correct for all other. 

- Need to implement tests for serializing the resulting Node tree from Huffman encoding once I've finished implementing it. 

## Current compression rates: 

Testing file : 100_KB_lorem.txt
- Huffman compression rate : 53.287467125328746 %
- LZW     compression rate : 17.70682293177068 %

 Testing file : 100_KB_repeating_lorem_ipsum.txt
- Huffman compression rate : 74.89215098861594 %
- LZW     compression rate : 1.5418414220091872 %
 
 Testing file : 100_KB_cScSc.txt
- Huffman compression rate : 93.7470625293747 %
- LZW     compression rate : 1.108988910110899 %
 
 Testing file : ASCII_256.txt
- Huffman compression rate : 34607.08955223881 %
- LZW     compression rate : 64.55223880597015 %

## Current performance rates: 

Name of file  : 100_KB_repeating_lorem_ipsum.txt
- Algorithm     : Huffman
- Method        : encoding
- Time taken    : 8.14 ms

Name of file  : 100_KB_repeating_lorem_ipsum.txt
- Algorithm     : LZW
- Method        : encoding
- Time taken    : 5.68 ms

Name of file  : 100_KB_lorem.txt
- Algorithm     : Huffman
- Method        : encoding
- Time taken    : 6.14 ms

Name of file  : 100_KB_lorem.txt
- Algorithm     : LZW
- Method        : encoding
- Time taken    : 4.41 ms

Name of file  : 100_KB_cScSc.txt
- Algorithm     : Huffman
- Method        : encoding
- Time taken    : 5.25 ms

Name of file  : 100_KB_cScSc.txt
- Algorithm     : LZW
- Method        : encoding
- Time taken    : 3.67 ms

Name of file  : ASCII_256.txt
- Algorithm     : Huffman
- Method        : encoding
- Time taken    : 1.60 ms

Name of file  : ASCII_256.txt
- Algorithm     : LZW
- Method        : encoding
- Time taken    : 1.13 ms






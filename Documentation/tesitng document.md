# Testing document

## Current scope

- tests for Node class
- tests for Huffman and LZW that ensure that after encoding the input, the decoded value equals to the original input
- few tests for Huffman class ensuring individual methods are working correctly. This is still quite inadequate.
- Need to figure out why the bytesize is different from the rootnodes' frequency for ASCII_256.txt, but correct for all other. 

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






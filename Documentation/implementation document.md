# Implementation document

## Current scope

- Huffman works
- LZW works
- Compression Benchmark
- Performance Benchmark (only for Huffman) 

## Current limitations

- No Performance testing for LZW
- No Performance comparison between Huffman and LZW
- No UI
- No large tests (over 100 KB)

## Inputs as well as time and space complexity

The complexity is at minimum O(n) since we need to traverse every single element of the input in order to able to compress it. 
LZW only uses a dictionary as its data structure with operations at O(1), so the overall complexity would be O(n).

However, Huffman encoding also uses a binary tree which operates at O(log(n)). Because the binary tree is traversed for each encoded element, the overall complexity would be at O(nlog(n)). 

## Current compression rates: 

Testing file : 100_KB_lorem.txt
- Huffman compression rate : 53.29 %
- LZW     compression rate : 17.71 %

Testing file : 100_KB_repeating_lorem_ipsum.txt
- Huffman compression rate : 74.89 %
- LZW     compression rate : 1.54 %

Testing file : 100_KB_cScSc.txt
- Huffman compression rate : 93.75 %
- LZW     compression rate : 1.11 %

Testing file : ASCII_256.txt
- Huffman compression rate : 34607.09 %
- LZW     compression rate : 64.55 %

## Current performance times by Huffman: 

Encoding file : 100_KB_lorem.txt
- Method        : Huffman
- Time taken    : 12.77 ms

Encoding file : 100_KB_repeating_lorem_ipsum.txt
- Method        : Huffman
- Time taken    : 4.77 ms

Encoding file : 100_KB_cScSc.txt
- Method        : Huffman
- Time taken    : 4.79 ms

Encoding file : ASCII_256.txt
- Method        : Huffman
- Time taken    : 0.80 ms

## Sources

[Huffman](https://en.wikipedia.org/wiki/Huffman_coding)
[LZW](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch)



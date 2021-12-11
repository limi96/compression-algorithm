
# Week 6 report

Hours spent: 8

##  What have you done this week?

- Shortened the length of the methods in Huffman class. However, the ordering of the methods is quite messy. 
- Serialization and Deserialization of Huffman Node tree complete
- Method now works, but each bit is taking the space of a byte. Therefore compression benchmark tests are incorrect (File sizes are 8 times bigger). 
- Compression Benchmark is now using File I/Os

- Managed to implement methods to convert huffman encoded message into a byte array which can then be written as a file to disk. 
- This effectively solves the aforementioned problem and Compression benchmark tests will have the correct result. 
- File Input Output is very close to being finished. Just need to implement the aforementioned methods to the Huffman class. 

- Made a peer review

##  What did you learn this week / today?

- A lot of things from ByteBuffers to ByteArrays to Bit conversions, bitwise operations

##  What has been unclear or problematic? Please answer this question truthfully, as this is something the course assistant may be able to help with.

- Currently, I've managed to solve the biggest problems in the project concerning the File IO and bits to bytes to bits operations. 
- Hopefully, it should be smooth sailing from now on. 

##  What next?

- Finish the File I/O class
- Finish Huffman-algorithms file I/O and begin (and finish) the same for LZW
- This will permit finishing of the Compression Benchmarks. 
- Update the testing and implementation document after finishing Compression benchmarking.

- Start implementing JavaDoc
- Finish writing Unit tests to cover all the methods for both algorithms. 
- Begin testing for time taken to execute for both algorithms. 
- Begin developing a UI. 









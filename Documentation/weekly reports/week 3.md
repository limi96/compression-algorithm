
# Week 3 report

Hours spent: 3

##  What have you done this week?

- Fixed the issue with LZW not including the last character.

- Made a tester class for detemrining the compression rates as well as some test-input files. 

- Minor additions to unit testing. 

- Updated the testing document with compression testing.

I could not allocate enough time to work on the project this week due to limiting factors in real life.  

##  What did you learn this week / today?

Good review for the File I/O skills learned back from Ohjelmoinnin jatkokurssi when implementing the "compression rate" -tester. 

Deeper understanding on how LZW works as I fixed the bug concerning the missing last character. 

##  What has been unclear or problematic? Please answer this question truthfully, as this is something the course assistant may be able to help with.

I do not understand why for the Huffman-algorithm, the root node gets the incorrect frequency value.
It should be the same as the number of bytes of the input, but for ASCII_256.txt it returns the wrong value. 
For others, it seems to be working, although there may be more edge cases, I haven't noticed yet. 

##  What next?

- Finish writing Unit tests to cover all the methods for both algorithms. 
- Begin testing for time taken to execute for both algorithms. 
- Begin developing a UI. 
- Writing the implementation document.








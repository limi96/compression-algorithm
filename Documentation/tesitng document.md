# Testing document

Project is now ready. Will be updating this document in detail. 

Current unedited version of the results.

[Compression report](https://github.com/limi96/compression-algorithm/blob/master/Documentation/Compression%20report.txt)

[Performance report](https://github.com/limi96/compression-algorithm/blob/master/Documentation/Performance%20report.txt)

Description of the purpose of the test files. 

**100_KB_lorem.txt**: Fairly random, but repetetive natural language

**artofwar.txt**: Natural English language.

**warandpeace.txt**: Natural English language. Representative of a very long book in English. 

**100_KB_cScSc.txt**: cScSc repeted to a 100 KB. Used as an extreme case to evaluate how high of a compression could be achieved. Therefore, can be considered as a positive control and also an edge case test for the LZW algorithm.
**ASCII_256.txt**: Tests if algorithms are able to encode all 256 of ASCII UTF-8 characters. Functions mainly as a negative control, as these algorithms should not be able to compress to a smaller size since this is the extreme form of no repetition. 
**Large Lorem.txt**: A 28 MB monster constructed from repeating the contents of 100_KB_lorem.txt. This acts mainly as a performance test, but also checks if compression rates are similar to that of 100_KB_lorem.txt. In theory, they should be, since the content is exactly the same, but repeated several times. 






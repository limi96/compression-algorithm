# Data structures and Algorithm lab course

This repository is for the data structures and algorithms project course for Helsinki University.

# Current state

- Currently the Main-class has a method to test if bits to bytes to bits works correctly.
- Compression benchmark gives an incorrect result, because each bit is represented a byte (Therefore 8 times bigger files size). 
- This will be corrected with the aformentioned bits to bytes converters. I'll implement it as soon as I have time, next week. 
- Huffman class is terribly messy (apologies). 

# Running the project

Make sure you run the project in the "compression-algorithm" -folder. 
If run in the algorithms folder, the absolute path could be incorrect and results in a filepath of
"/algorithms/algorithms/test_files" instead of "/algorithms/test_files" !

If this doesn't work, you can also go to the FileUtils.java file and edit 
```
File testing = new File("algorithms/test_files/" + fileName);
```
into 

```
File testing = new File("test_files/" + fileName);
```

## Weekly reports

- [week 1](https://github.com/limi96/compression-algorithm/blob/master/Documentation/week%201%20report.md)

- [week 2](https://github.com/limi96/compression-algorithm/blob/master/Documentation/weekly%20reports/week%202.md)

- [week 3](https://github.com/limi96/compression-algorithm/blob/master/Documentation/weekly%20reports/week%203.md)

- [week 4](https://github.com/limi96/compression-algorithm/blob/master/Documentation/weekly%20reports/week%204.md)

- [week 5](https://github.com/limi96/compression-algorithm/blob/master/Documentation/weekly%20reports/week%205.md)

- [week 6](https://github.com/limi96/compression-algorithm/blob/master/Documentation/weekly%20reports/week%206.md)

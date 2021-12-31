# User manual

## Opening the program after downloading project folder 

To run the main GUI program use ```mvn compile exec:java``` in the programs root folder ```compression-algorithm/algorithms```.

In order to run the benchmarks use ``` mvn compile exec:java -Dexec.mainClass="Main" -Dexec.arguments="compression" ``` for Compression Benchmarks or ``` mvn compile exec:java -Dexec.mainClass="Main" -Dexec.arguments="performance" ``` for Performance benchmarks.

**Please note that the performance benchmark will take over an hour to complete at 100 reps.** The reps are adjusted by the global variable called ``` int reps ``` At 10 repetitions it will take about 8 minutes and 15 minutes for 20. 

## Using the executable 

Run ```java -jar Algorithms-1.0-SNAPSHOT.jar``` in the terminal and make sure that the terminal is running in the same directory, where the -jar file is located!

## Using the GUI

The entire program consists of a single main view that looks like the following picture after opening the program.

<p align="center">
<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/pictures/gui.png">
</p>

The following picture has different fields marked in colored frames. These are meant to help explaining the different parts of the program. Some fields have instruction labels next to them. Please read through them carefully. 

<p align="center">
<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/pictures/gui_colored.png">
</p>

There are three available options for the input marked by the blue frame. Note that you may only use one input option, which is why they are given as radio buttons. The program itself will check the validity of the input. An exception for this are files that do not exist or binary strings with invalid data. See more [here](https://github.com/limi96/compression-algorithm/blob/master/Documentation/testing%20document.md) under the section "UI-testing and known errors".

## Encoding

If you want to quickly test your own inpute choose "Use input field" radio option and write in the text field under the label "Input". This input field can be used for both Encoding and Decoding. 

Proceed to selecting either encoding or decoding using the radio options marked by the red frame. After this, press either of the button marked by the black frame, to begin execution of your selected algorithm, which is the namesake of the button you pressed.

If you have your own file, please create a folder called "test_files" and add your input as a .txt file. Then select the "Open text input from file" radio button and write the name of your file including the .txt-extension in the text field with "ENTER FILE NAME" placeholder text. 

## Decoding

You may also use a file that has been encoded as the input when decoding by choosing the option "Encoded input from file".
Note that the name of the file must contain an .hf or a .lzw-extension and the data inside the file must be valid otherwise, the program may give you an IllegalArgumentException or print nulls. See more [here](https://github.com/limi96/compression-algorithm/blob/master/Documentation/testing%20document.md) under the section "UI-testing and known errors".

Select the "Decode" radio option and then press the button with the desired compression algorithm. 

## Switchings and clearing inputs and outputs

The buttons that perform these functions are all marked in green. All text fields work with text editor key commands such as copy-pasting, cutting, undo, redo. Pressing a button with "Clear" will clear the text field entirely. **You cannot retrieve the original text after pressing one of the "Clear" buttons**.

Use the "Switch inputs and outputs" button to quickly test encoding and decoding, but remember also to change the radio-option marked in red! 

## Setting bitLength for LZW

By default the LZW encodes the outputs as 12-bit representations of encoded values. This can be adjusted using the text field that is between the blue and black marked rectangles. The value can be changed to between 8 and 32 to see its effect on the compression rate as well as performance. 

## Output file

By default, an output file will be produced regardless of whether or not the checkbox option "Name output file" has been selected. If the name field is left blank, the name of the file will be "test" and an extension of ".txt/.hf/.lzw" will be added depending on the algorithm selected. When decoding, the output will always be a .txt file and when encoding the output file will be either .hf or .lzw.**Please do not add a file extension in the name field, when naming the file yourself** .

All output files can be found in the same folder where the program was executed. If you are not using an executable, the output files will be found in the root folder (by default it is in ```compression/algorithm/algorithms```).


## Results

All results are presented in the right side text field under the "Results" label. If an error has occured, the "results" label will be changed to "Error" and an error text will be presented in the text field. 

If you wish to also view the performance, please check the "Include performance test" choicebox. This may slow the program down since performance testing is done separately. Additionally, the program executes the algorithms only once, meaning that **JIT compilation** and **garbage collection** (particularly the former) **may greatly affect the results!**

In the results text field, the output of the compression results are marked in blue and the performance results in red. If performance testing is not included, the area marked in red will be blank.

<p align="center">
<img src="https://github.com/limi96/compression-algorithm/blob/master/Documentation/pictures/gui_results.png">
</p>
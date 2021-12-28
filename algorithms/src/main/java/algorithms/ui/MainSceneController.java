package algorithms.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

import algorithms.Huffman;
import algorithms.LZW;
import algorithms.utils.FileUtils;
import algorithms.utils.Node;

public class MainSceneController implements Initializable {

    private Ui application;
    private Huffman h; 
    private LZW lzw; 
    
    String input;
    String output;
    String fileName;

    @FXML
    public TextArea outputArea; 

    @FXML
    public TextArea inputArea; 

    @FXML
    public RadioButton encodeRadio;

    @FXML
    public RadioButton decodeRadio;

    @FXML
    public Label resultLabel; 

    @FXML
    public TextArea resultArea; 

    @FXML
    public RadioButton inputFieldRadio; 

    @FXML
    public RadioButton inputTextFileRadio; 

    @FXML
    public RadioButton inputEncodedFileRadio; 

    @FXML
    public CheckBox outputFileCheckBox; 

    @FXML
    public CheckBox performanceChoiceBox; 
    
    @FXML
    public TextField inputNameField;

    @FXML
    public TextField outputNameField;

    @FXML
    public TextField encodedNameField;

    @FXML
    public TextField lzwBitArea;
    
    public boolean checkInputField() {
        String input = inputArea.getText(); 

        if (input.isEmpty()) {
            resultLabel.setText("Error!");
            resultArea.setText("INPUT FIELD CANNOT BE EMPTY!");
            return true;
        }

        if (!input.matches("[0-1]*") && decodeRadio.isSelected() && inputFieldRadio.isSelected()) {
            resultLabel.setText("Error!");
            resultArea.setText("WHEN DECODING INPUT FIELD MUST BE IN BINARY FORMAT!");
            return true; 
        }
        return false; 
    }

    public boolean checkFileNameField() {        

        if (encodeRadio.isSelected() && inputEncodedFileRadio.isSelected()) {
            resultArea.setText("CANNOT ENCODE WHILE HAVE ENCODED INPUT FROM FILE RADIO SELECTED");
            return true;
        }

        if (inputTextFileRadio.isSelected() && inputNameField.getText().isEmpty()) {
            resultArea.setText("INPUT NAME FIELD CANNOT BE EMPTY!");
            return true;
        }

        if (outputFileCheckBox.isSelected() && outputNameField.getText().isEmpty()) {
            resultArea.setText("OUTPUT NAME FIELD CANNOT BE EMPTY!");
            return true;
        }   

        if (inputEncodedFileRadio.isSelected() && encodedNameField.getText().isEmpty()) {
            resultArea.setText("ENCODED INPUT NAME FIELD CANNOT BE EMPTY!");
            return true;
        }

        if (outputFileCheckBox.isSelected() && outputNameField.getText().contains(".")) {
            resultArea.setText("OUTPUT NAME FIELD CANNOT CONTAIN THE FILE EXTENSION (.txt|.hf|.lzw)!");
            return true;
        }

        return false; 
    }

    public boolean checkInputFileName(String fileName) {
        if (!fileName.contains("txt")) {
            resultLabel.setText("Error!");
            resultArea.setText("File must end in .txt and File location must be in algorithms/test_files!!"); 
            return true; 
        }
        return false;
    }

    public boolean checkEncodedInputFileName(String fileName, String method) {

        if (!fileName.toLowerCase().contains(method)) {
            resultLabel.setText("Error!");
            resultArea.setText("Encoded file must end in " + method + " for files to be decoded with this method!"); 
            return true; 
        }
        return false; 
    }

    @FXML
    public void handleHuffman(ActionEvent event) throws java.io.IOException {

        if (handleInitiationFailed()) {
            return; 
        }

        if (encodeRadio.isSelected()) { 
            h.encode(input); 
            output = h.getEncodedMessage(); 
            huffmanCompressionPrints(input, output, false, false, "");
        }

        else if (decodeRadio.isSelected()) {

            if (inputFieldRadio.isSelected()) {
                h.readEncodedInput(input, false);
                input = h.getEncodedMessage();
                Node rootNode = h.getRootNode() == null ? h.getInputRootNode() : h.getRootNode();
                output = h.decode(rootNode, input); 
                huffmanCompressionPrints(output, input, true, false, ""); 
            }            
            
            if (inputEncodedFileRadio.isSelected()) {

                fileName = encodedNameField.getText();
                
                if (checkEncodedInputFileName(fileName, ".hf")) {
                    return; 
                }
                            
                h.readEncodedInput(fileName, true);
                input = h.getEncodedMessage(); 
                Node rootNode = h.getRootNode() == null ? h.getInputRootNode() : h.getRootNode() ; 
                output = h.decode(rootNode, input); 
                huffmanCompressionPrints(output, input, true, true, fileName);
            }
            
        }
    }



    public void huffmanCompressionPrints(String input, String output, boolean decode, boolean decodeFromFile, String originalInputFileName) throws java.io.IOException {
        
        String text = "Compression results for the Huffman algorithm \n";

        String outputName = outputFileCheckBox.isSelected() ? outputName = outputNameField.getText() : "test";         
        
        writeOutputFile(input, outputName, decode, true);

        outputName = decodeFromFile ? originalInputFileName : outputName + ".hf"; 
        output = h.getFullEncodedMessage();

        text += 
        ("Input Message length \t\t\t (in bits) : \t"  + input.getBytes().length * 16) + "\n" + 
        ("Output Message length \t\t\t (in bits) : \t" + output.length()) + "\n";

        long inputByteSize = input.getBytes().length; 
        long huffmanByteSize = FileUtils.readFile(outputName).length;
        Double compRateHuffman = ((double) huffmanByteSize / inputByteSize) * 100;
        Double compRateHuffmanNoTree = ((double) (huffmanByteSize - h.getTreeData().length) / inputByteSize) * 100;

        text += 
        ("Original input File size \t\t\t (in bytes) : \t" + inputByteSize) + "\n" + 
        ("Huffman Compressed File size \t (in bytes) : \t" + huffmanByteSize) + "\n" + 
        ("Huffman Tree File size \t\t\t (in bytes) : \t" + h.getTreeData().length) + "\n" + 
        ("Huffman Message File size \t\t (in bytes) : \t" + h.getMessageData().length) + "\n" + 
        ("Huffman compression rate \t\t\t\t: \t" + String.format("%.2f",compRateHuffman) + " %") + "\n" + 
        ("Compression rate Without Tree \t\t\t: \t" + String.format("%.2f",compRateHuffmanNoTree) + " %") + "\n";

        setCompressionResultArea(input, output, text, decode);
        
        if (performanceChoiceBox.isSelected()) {
            huffmanPerformancePrints(outputName);
        }
    }

    public void huffmanPerformancePrints(String outputName) throws java.io.IOException {
        String text = resultArea.getText() + "\n\n\n";

        long start = System.nanoTime(); 
        h.encode(input);
        long end = System.nanoTime();
        text += "Encoding the input \t :"  + (end-start)/1E6 + " ms" + "\n"; 

        start = System.nanoTime(); 
        writeOutputFile(input, "performanceTest", false, true);
        end = System.nanoTime();
        text += "Writing the file \t :"  + (end-start)/1E6 + " ms" + "\n"; 

        start = System.nanoTime(); 
        h.readEncodedInput("performanceTest.hf", true);
        end = System.nanoTime();
        text += "Reading the file \t :"  + (end-start)/1E6 + " ms" + "\n"; 
        
        Node rootNode = h.getRootNode() == null ? h.getInputRootNode() : h.getRootNode() ; 
        start = System.nanoTime(); 
        output = h.decode(rootNode, input); 
        end = System.nanoTime();
        text += "Decoding the file \t :"  + (end-start)/1E6 + " ms" + "\n"; 
        
        resultArea.setText(text); 
    }

    public void lzwPerformancePrints(String outputName) throws java.io.IOException {
        String text = resultArea.getText() + "\n\n\n\n\n\n";

        long start = System.nanoTime(); 
        lzw.compress(input);
        long end = System.nanoTime();
        text += "Encoding the input \t :"  + (end-start)/1E6 + " ms" + "\n"; 

        start = System.nanoTime(); 
        writeOutputFile(input, "performanceTest", false, false);
        end = System.nanoTime();
        text += "Writing the file \t :"  + (end-start)/1E6 + " ms" + "\n"; 

        start = System.nanoTime(); 
        lzw.readLZWFile("performanceTest.lzw");
        end = System.nanoTime();
        text += "Reading the file \t :"  + (end-start)/1E6 + " ms" + "\n"; 
        
        ArrayList<Integer> inputEncoded = lzw.getEncoded();
        start = System.nanoTime(); 
        output = lzw.uncompress(inputEncoded); 
        end = System.nanoTime();
        text += "Decoding the file \t :"  + (end-start)/1E6 + " ms" + "\n"; 
        
        resultArea.setText(text); 
    }


    public boolean checkBitLength() {
        String input = lzwBitArea.getText(); 
        
        if (!input.matches("[0-9]*")) {
            resultLabel.setText("Error!");
            resultArea.setText("BitLength MUST BE A NUMBER"); 
            return true; 
        }
        
        int inputBitLength = Integer.parseInt(input);
        
        if (inputBitLength < 8 || inputBitLength > 32) {
            resultLabel.setText("Error!");
            resultArea.setText("BitLength for LZW must be between 8 and 32 for the algorithm to work properly!"); 
            return true; 
        }

        lzw.setBitLength(inputBitLength);
        return false; 
    }
    
    @FXML
    public void handleLZW(ActionEvent event) throws java.io.IOException {

        ArrayList<Integer> inputEncoded = new ArrayList<>();

        if (handleInitiationFailed() || checkBitLength()) {
            return; 
        }

        if (encodeRadio.isSelected()) { 
            
            lzw.compress(input);
            
            lzwCompressionPrints(input, output, false, false, "");
        }

        else if (decodeRadio.isSelected()) {

            if (inputFieldRadio.isSelected()) {
                lzw.decodeLZWEncodedString(input);
                inputEncoded = lzw.getEncoded();
                output = lzw.uncompress(inputEncoded);
                lzwCompressionPrints(output, input, true, false, "");
            }

            if (inputEncodedFileRadio.isSelected()) {

                fileName = encodedNameField.getText();
                
                if (checkEncodedInputFileName(fileName, ".lzw")) {
                    return; 
                }
                
                lzw.readLZWFile(fileName);
                inputEncoded = lzw.getEncoded();
                input = lzw.getEncodedAsBits();
                output = lzw.uncompress(inputEncoded);
                lzwCompressionPrints(output, input, true, true, fileName);
            }
        }

    }
    
    public void lzwCompressionPrints(String input, String output, boolean decode, boolean decodeFromFile, String originalInputFileName) throws java.io.IOException {
        
        String text = "Compression results for the LZW algorithm\n";

        String outputName = outputFileCheckBox.isSelected() ? outputName = outputNameField.getText() : "test";     
        
        writeOutputFile(input, outputName, decode, false);
        output = lzw.getEncodedAsBits();
        
        outputName = decodeFromFile ? originalInputFileName : outputName + ".lzw"; 

        text += 
        ("Input Message length \t\t\t (in bits) : \t"  + input.getBytes().length * 16) + "\n" + 
        ("Output Message length \t\t\t (in bits) : \t" + output.length()) + "\n";
        
        long inputByteSize = input.getBytes().length; 
        long lzwByteSize = FileUtils.readFile(outputName).length;
        Double compRateLZW = ((double) lzwByteSize / inputByteSize) * 100;

        text += 
        ("Original input File size \t\t\t (in bytes) : \t" + inputByteSize) + "\n" + 
        ("LZW Compressed File size \t\t (in bytes) : \t" + lzwByteSize) + "\n" + 
        ("LZW compression rate \t\t\t\t\t: \t" + String.format("%.2f",compRateLZW) + " %") + "\n";

        setCompressionResultArea(input, output, text, decode);

        if (performanceChoiceBox.isSelected()) {
            lzwPerformancePrints(outputName);
        }
    }

    public void writeOutputFile(String input, String outputName, boolean decode, boolean huffman) throws java.io.IOException  {
        if (!decode) {
            if (huffman) {
                h.writeEncodedFile(outputName);
            }   else {
                lzw.writeLZWFile(outputName);
            }
        } else {
            FileUtils.writeTextFile(outputName, input);
        }
    }

    public void setCompressionResultArea(String input, String output, String text, boolean decode) {
        if (decode) {
            String temp = input; 
            input = output; 
            output = temp; 
        }
        inputArea.setText(input); 
        resultArea.setText(text);
        outputArea.setText(output); 
    }


    @FXML
    public boolean handleInitiationFailed() {
        input = "";
        output = ""; 
        fileName = ""; 
        resultLabel.setText("Results");
        resultArea.setText(""); 

        if (checkFileNameField()) {
            return true;
        } 
        
        if (inputTextFileRadio.isSelected()) {

            fileName = inputNameField.getText();
            if (checkInputFileName(fileName)) {
                return true; 
            }
            input = FileUtils.readTextFile(fileName);
            inputArea.setText(input); 
        }        

        else if (inputFieldRadio.isSelected()){
            if (checkInputField()) {
                return true; 
            }
            input = inputArea.getText(); 
        }
        return false;
    }

    @FXML
    public void handleSwitchFields() {
        String temp = inputArea.getText();
        inputArea.setText(outputArea.getText());
        outputArea.setText(temp);
    }

    @FXML
    public void handleClearBoth(ActionEvent event) { 
        inputArea.setText(""); 
        outputArea.setText(""); 
    }

    @FXML
    public void handleClearInput(ActionEvent event) { 
        inputArea.setText(""); 
    }

    @FXML
    public void handleClearOutput(ActionEvent event) { 
        outputArea.setText(""); 
    }
    
    public void setApplication(Ui application) {
        this.application = application; 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        start(); 
    }    

    @FXML 
    public void start() {
        h = new Huffman(); 
        lzw = new LZW(); 
    }

}

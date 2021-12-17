package algorithms.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

import algorithms.Huffman;
import algorithms.utils.FileUtils;
import algorithms.utils.Node;

public class MainSceneController implements Initializable {

    private Ui application;
    private Huffman h; 

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
    public TextField inputNameField;

    @FXML
    public TextField outputNameField;

    @FXML
    public TextField encodedNameField;
    
    public boolean checkEmptyInputArea() {
        if (inputArea.getText().isEmpty()) {
            resultLabel.setText("Error!");
            resultArea.setText("INPUT FIELD CANNOT BE EMPTY!");
            return true;
        }
        return false; 
    }

    public boolean checkFileNameField() {        
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
            huffmanCompressionPrints(input, output, false, "");
        }

        else if (decodeRadio.isSelected()) {
            
            if (inputEncodedFileRadio.isSelected()) {

                fileName = encodedNameField.getText();
                
                if (checkEncodedInputFileName(fileName, ".hf")) {
                    return; 
                }
                                
                h.readEncodedFile(fileName);
                input = h.getEncodedMessage(); 
                Node rootNode = h.getRootNode() == null ? h.getInputRootNode() : h.getRootNode() ; 
                output = h.decode(rootNode, input); 
                
                huffmanCompressionPrints(output, input, true, fileName);
            }
            
        }
    }



    public void writeOutputFile(String input, String outputName, boolean decode) throws java.io.IOException  {
        if (!decode) {
            h.writeEncodedFile(outputName);
        } else {
            FileUtils.writeTextFile(outputName, input);
        }
    }

    public void huffmanCompressionPrints(String input, String output, boolean decode, String originalInputFileName) throws java.io.IOException {
        
        String text = "Compression results \n";

        String outputName = outputFileCheckBox.isSelected() ? outputName = outputNameField.getText() : "test";         
        
        writeOutputFile(input, outputName, decode);

        outputName = decode ? originalInputFileName : outputName + ".hf"; 

        text += 
        ("Input Message length \t\t\t (in bits) : \t"  + input.getBytes().length * 8) + "\n" + 
        ("Output Message length \t\t\t (in bits) : \t" + output.length()) + "\n";

        long inputByteSize = input.getBytes().length; 
        long huffmanByteSize = FileUtils.readFile(outputName).length;
        Double compRateHuffman = ((double) huffmanByteSize / inputByteSize) * 100;
        Double compRateHuffmanNoTree = ((double) (huffmanByteSize - h.getTreeData().length) / inputByteSize) * 100;

        text += 
        ("Huffman Compressed File size \t (in bytes) : \t" + huffmanByteSize) + "\n" + 
        ("Huffman Tree File size \t\t\t (in bytes) : \t" + h.getTreeData().length) + "\n" + 
        ("Huffman Message File size \t\t (in bytes) : \t" + h.getMessageData().length) + "\n" + 
        ("Huffman compression rate \t\t\t\t: \t" + String.format("%.2f",compRateHuffman) + " %") + "\n" + 
        ("Compression rate Without Tree \t\t\t: \t" + String.format("%.2f",compRateHuffmanNoTree) + " %") + "\n";

        setCompressionResultArea(input, output, text, decode);
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

    String input;
    String output;
    String fileName;

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
            if (checkEmptyInputArea()) {
                return true; 
            }
            input = inputArea.getText(); 
        }
        return false;
    }

    @FXML
    public void handleLZW(ActionEvent event) throws java.io.IOException {

        handleInitiationFailed();

        if (encodeRadio.isSelected()) { 
            h.encode(input); 
            output = h.getEncodedMessage(); 
            // huffmanCompressionPrints(input, output, false);
        }

        else if (decodeRadio.isSelected()) {
            
            if (inputEncodedFileRadio.isSelected()) {

                fileName = encodedNameField.getText();
                
                if (checkEncodedInputFileName(fileName, ".hf")) {
                    return; 
                }
                                
                h.readEncodedFile(fileName);
                input = h.getEncodedMessage(); 
                Node rootNode = h.getRootNode() == null ? h.getInputRootNode() : h.getRootNode() ; 
                output = h.decode(rootNode, input); 

                // huffmanCompressionPrints(output, input, true);
            }
            
        }

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
    }

}

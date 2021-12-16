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

    public boolean checkEmptyFileNameField() {        
        if (inputTextFileRadio.isSelected() && inputNameField.getText().isEmpty()) {
            resultLabel.setText("Error!");
            resultArea.setText("INPUT NAME FIELD CANNOT BE EMPTY!");
            return true;
        }

        if (outputFileCheckBox.isSelected() && outputNameField.getText().isEmpty()) {
            resultLabel.setText("Error!");
            resultArea.setText("OUTPUT NAME FIELD CANNOT BE EMPTY!");
            return true;
        }   

        if (inputEncodedFileRadio.isSelected() && encodedNameField.getText().isEmpty()) {
            resultLabel.setText("Error!");
            resultArea.setText("ENCODED INPUT NAME FIELD CANNOT BE EMPTY!");
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

        String input = "";
        String output = ""; 
        String fileName = ""; 
        resultLabel.setText("Results");
        resultArea.setText(""); 

        if (checkEmptyFileNameField()) {
            return;
        } 
        
        if (inputTextFileRadio.isSelected()) {

            fileName = inputNameField.getText();
            if (checkInputFileName(fileName)) {
                return; 
            }
            input = FileUtils.textFileReaderOutput(fileName);
            inputArea.setText(input); 
        }        

        else if (inputFieldRadio.isSelected()){
            if (checkEmptyInputArea()) {
                return; 
            }
            input = inputArea.getText(); 
        }

        if (encodeRadio.isSelected()) { 
            h.encode(input); 
            output = h.getEncodedMessage(); 
            huffmanCompressionPrints(input, output);
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
                huffmanCompressionPrints(output, input);
            }
            
        }
    }

    public void huffmanCompressionPrints(String input, String output) throws java.io.IOException {
        String text = "Compression results \n";
    
        String outputName = "test.hf"; 
        
        if (outputFileCheckBox.isSelected()) {
            outputName = outputNameField.getText()+".hf";
        }

        text += 
        ("Input Message length \t\t\t (in bits) : \t"  + input.getBytes().length*8) + "\n" + 
        ("Output Message length \t\t\t (in bits) : \t" + output.length()) + "\n";

        h.writeEncodedFile(outputName);

        long inputByteSize = input.getBytes().length; 
        long huffmanByteSize = FileUtils.readFile(outputName).length;
        Double compRateHuffman = ((double) huffmanByteSize/inputByteSize)*100;
        Double compRateHuffmanNoTree = ((double) (huffmanByteSize-h.getTreeData().length)/inputByteSize)*100;

        text += 
        ("Huffman Compressed File size \t (in bytes) : \t" + huffmanByteSize) + "\n" + 
        ("Huffman Tree File size \t\t\t (in bytes) : \t" + h.getTreeData().length) + "\n" + 
        ("Huffman Message File size \t\t (in bytes) : \t" + h.getMessageData().length) + "\n" + 
        ("Huffman compression rate \t\t\t\t: \t" + String.format("%.2f",compRateHuffman) + " %") + "\n" + 
        ("Compression rate Without Tree \t\t\t: \t" + String.format("%.2f",compRateHuffmanNoTree) + " %") + "\n";
        resultArea.setText(text);
        outputArea.setText(output); 
    }


    @FXML
    public void handleLZW(ActionEvent event) {
        String input = inputArea.getText(); 
        outputArea.setText(input); 
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

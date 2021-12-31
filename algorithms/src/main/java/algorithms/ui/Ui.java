package algorithms.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;


/**
 * Sets the view of the GUI app
 */
public class Ui extends Application {

    private Scene mainScene;

    @Override
    public void init() throws Exception {        
        try {
            FXMLLoader mainSceneLoader = new FXMLLoader(getClass().getResource("/fxml/mainScene.fxml"));   
            Parent mainPane = mainSceneLoader.load(); 
            MainSceneController mainController = mainSceneLoader.getController(); 
            mainController.setApplication(this);
            mainScene = new Scene(mainPane);
        }
        catch (Exception e) {
            System.out.println("MainScene Error: " + e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setTitle("Huffman and LZW");
        stage.setScene(mainScene);   
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

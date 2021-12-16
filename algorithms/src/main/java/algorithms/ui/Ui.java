package algorithms.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;


public class Ui extends Application {

    private Stage stage;
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
        this.stage = stage;
        stage.setTitle("Huffman and LZW");
        setMainScene();
        // setLoginScene();
        // setRegisterScene();
        stage.show();
    }

    public void setMainScene() {
        stage.setScene(mainScene);   
    }

    public static void main(String[] args) {
        launch(args);
    }

}

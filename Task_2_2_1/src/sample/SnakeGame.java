package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.java.GameController;
import sample.java.MenuController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SnakeGame extends Application {

    private Stage stage;
    private ScheduledExecutorService executor;

    private static SnakeGame instance;

    public SnakeGame() {
        instance = this;
    }

    public static SnakeGame getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        executor = Executors.newSingleThreadScheduledExecutor();

//        MenuController.setAsNewScene();
        GameController.setAsNewScene();

        primaryStage.show();
    }

    @Override
    public void stop() {
        executor.shutdown();
    }

    public void changeScene(String fxmlName, Object object) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("fxml/" + fxmlName + ".fxml"));
            fxmlLoader.setController(object);
            Parent root = fxmlLoader.load();

            if (stage.getScene() == null) {
                stage.setTitle("Snake");
                stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
            } else {
                stage.getScene().setRoot(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
            /*Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Some error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();*/
        }
    }

    public ScheduledExecutorService getExecutor() {
        return executor;
    }
}

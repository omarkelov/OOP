package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.java.controllers.Controller;
import sample.java.controllers.GameController;
import sample.java.observer.EventManager;

public class SnakeGame extends Application {

    public static final String APP_CLOSING = "app_closing";
    public static final String FOOD_EATEN = "food_eaten";
    public static final String SNAKE_DEATH = "snake_death";

    private static SnakeGame instance;

    private EventManager eventManager;

    private Stage stage;
    private Controller controller;

    public SnakeGame() {
        instance = this;

        eventManager = new EventManager(APP_CLOSING, FOOD_EATEN, SNAKE_DEATH);
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

//        changeScene(new MenuController());
        changeScene(new GameController());

        primaryStage.show();
    }

    @Override
    public void stop() {
        eventManager.notify(APP_CLOSING);
    }

    public void changeScene(Controller controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("resources/fxml/" + controller.getFXMLFileName()));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();

            if (this.controller != null) {
                this.controller.dispose();
            }
            this.controller = controller;

            if (stage.getScene() == null) {
                stage.setTitle("Snake");
                stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
            } else {
                stage.getScene().setRoot(root);
            }

            controller.runAfterSceneSet(stage.getScene());
        } catch (Exception e) {
            e.printStackTrace();
            /*Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Some error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();*/
        }
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}

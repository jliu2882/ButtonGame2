package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private Button button1;
    private int randX;
    private int randY;
    private static final Integer STARTTIME = 10;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private Integer timeSeconds = STARTTIME;
    private int counter = 0;
    private Label count = new Label("Count: ");

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 500, 500);
        root.setAlignment(Pos.CENTER);

        button1 = new Button("WhackAMole");
        root.add(button1,0,0);

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                randNum();
                button1.setTranslateX(randX);
                button1.setTranslateY(randY);
                if (timeline != null) {
                    timeline.stop();
                }
                if (counter >= 0) {
                    counter = 0;
                    timeSeconds = STARTTIME;
                }
                timerLabel.setText(timeSeconds.toString());
                timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(1),
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        timeSeconds--;
                                        timerLabel.setText(timeSeconds.toString());
                                        button1.setOnAction(e -> {
                                            count.setText("Count: " + Integer.toString(counter));
                                            counter++;
                                            if (timeSeconds <= 0) {
                                                counter--;
                                            }
                                        });
                                        if (timeSeconds <= 0) {
                                            timeline.stop();
                                        }
                                    }
                                }));
                timeline.playFromStart();
            }
        });
        primaryStage.setTitle("Button Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void randNum() {
        randX = (int) (Math.random() * 500) - 250;
        randY = (int) (Math.random() * 500) - 250;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

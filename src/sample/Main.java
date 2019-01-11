package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main extends Application {

    private Button button1;
    private Timeline timeline;
    private Integer timeSeconds = 5;
    private int counter = 1;
    private Label count = new Label("Username: ");
    private String username;
    private TextField uname;
    private Label highScore;
    private boolean gameOn = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 500, 500);
        root.setAlignment(Pos.CENTER);

        GridPane highScores = new GridPane();
        highScores.setHgap(10);
        highScores.setVgap(10);
        highScores.setAlignment(Pos.CENTER);
        Label usernameHeader = new Label();
        usernameHeader.setText("Username");
        Label scoreHeader = new Label();
        scoreHeader.setText("Score");
        highScores.addRow(0, usernameHeader, scoreHeader);List<List<String>> scores = Controller.read()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(s -> Integer.parseInt(s.get(1)))))
                .collect(Collectors.toList());

        IntStream.range(0, 3).forEach(i -> {
            if (i < scores.size()) {
                List<String> user = scores.get(i);

                Label usernameLabel = new Label();
                usernameLabel.setText(user.get(0));

                Label scoreLabel = new Label();
                scoreLabel.setText(user.get(1));

                highScores.addRow(i + 1, usernameLabel, scoreLabel);
            }
        });



        username = "test";
        uname = new TextField();
        uname.setPromptText("Enter your username here");

        button1 = new Button();
        button1.setStyle("-fx-graphic: url('https://image.flaticon.com/icons/png/128/235/235368.png');");
        button1.setVisible(false);
 
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                count.setText("Score: " + Integer.toString(counter));
                if (timeline != null) {
                    timeline.stop();
                }
                button1.setOnAction(e -> {
                    if (timeSeconds > 0) {
                        counter++;
                        moleRun();
                    }
                    else if(gameOn){
                        addScores();
                        gameOn = false;
                    }
                    else{

                        counter = 1;
                        gameOn = true;
                    }
                    count.setText("Score: " + Integer.toString(counter));
                });
                timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(1),
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        timeSeconds--;
                                        if (timeSeconds <= 0) {
                                            timeline.stop();
                                        }
                                    }
                                }));
                timeline.playFromStart();
            }
        });


        root.add(button1,0,0);
        root.add(count,0,1);
        root.add(uname,1,1);
        root.add(highScore,1,2);
        primaryStage.setTitle("Button Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                //opens the game
                button1.setVisible(true);
                username = uname.getText();
                uname.setVisible(false);
                highScore.setVisible(false);


            }
        });
    }

    public void moleRun() {
        button1.setTranslateX((int) (Math.random() * 500) - 250);
        button1.setTranslateY((int) (Math.random() * 500) - 250);
    }

    public void addScores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/sample/scores.csv",true))) {
            bw.newLine();
            bw.write(username);
            bw.write(",");
            bw.write(String.valueOf(counter));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void terminate() {
        addScores();

        GridPane highScores = new GridPane();
        highScores.setHgap(10);
        highScores.setVgap(10);
        highScores.setAlignment(Pos.CENTER);

        // TODO: Could be fetched through CSVUtilities
        Label usernameHeader = new Label();
        usernameHeader.setText("Username");

        Label scoreHeader = new Label();
        scoreHeader.setText("Score");

        highScores.addRow(0, usernameHeader, scoreHeader);

        List<List<String>> scores = Controller.read()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(s -> Integer.parseInt(s.get(1)))))
                .collect(Collectors.toList());

        IntStream.range(0, 3).forEach(i -> {
            if (i < scores.size()) {
                List<String> user = scores.get(i);

                Label usernameLabel = new Label();
                usernameLabel.setText(user.get(0));

                Label scoreLabel = new Label();
                scoreLabel.setText(user.get(1));

                highScores.addRow(i + 1, usernameLabel, scoreLabel);
            }
        });
        //root.getChildren().add(highScores);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

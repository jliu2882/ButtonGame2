package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Button button1;
    private int randX;
    private int randY;
    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane root = new GridPane();
        Scene scene = new Scene(root,500,500);root.setAlignment(Pos.CENTER);

        button1 = new Button("WhackAMole");

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                randNum();
                button1.setTranslateX(randX);
                button1.setTranslateX(randY);
            }
        });
        root.add(button1, 0, 0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void randNum(){
        randX = (int)(Math.random()*500)-250;
        randY = (int)(Math.random()*500)-250;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

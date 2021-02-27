package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        ObservableList<Screen> screenSizes = Screen.getScreens();
        screenSizes.forEach(screen -> {
            System.out.println(screen.getBounds());
        });
        init(primaryStage);

    }

    private void init(Stage primaryStage) throws IOException {

        Pane root = new Pane();
        Scene scene = new Scene(root, 3840, 1080);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                primaryStage.close();
            }
        });


        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                primaryStage.close();
            }
        });


        ArrayList<Circle> circArray = new ArrayList<Circle>();
        for (int i = 0; i < 50; i++) {
            int x = ThreadLocalRandom.current().nextInt(50, 3000);
            int y = ThreadLocalRandom.current().nextInt(50, 1000);
            circArray.add(new Circle(60));
            circArray.get(i).setStyle("-fx-fill: linear-gradient(from 13px 38px to 52px 52px, reflect,  #d2691e, #ffa500 100%);" + "-fx-opacity: 30%;");
            circArray.get(i).relocate(x, y);
            root.getChildren().add(circArray.get(i));
        }


        primaryStage.setTitle("Line Chart");
        primaryStage.setHeight(1080);
        primaryStage.setWidth(3840);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 90%,#5c0067, #00d4ff)");
        primaryStage.setScene(scene);

        primaryStage.show();

        ArrayList<Timeline> timeLineArray = new ArrayList<Timeline>();
        for (int j = 0; j < circArray.size(); j++) {
            int finalJ = j;
            timeLineArray.add(new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
                double dx = (ThreadLocalRandom.current().nextInt(0, 8) - 4) * 2; //Step on x or velocity
                double dy = (ThreadLocalRandom.current().nextInt(0, 8) - 4) * 2; //Step on y

                @Override
                public void handle(ActionEvent t) {
                    //move the ball
                    circArray.get(finalJ).setLayoutX(circArray.get(finalJ).getLayoutX() + dx);
                    circArray.get(finalJ).setLayoutY(circArray.get(finalJ).getLayoutY() + dy);

                    Bounds bounds = root.getBoundsInLocal();

                    if (circArray.get(finalJ).getLayoutX() <= (bounds.getMinX() + circArray.get(finalJ).getRadius()) ||
                            circArray.get(finalJ).getLayoutX() >= (bounds.getMaxX() - circArray.get(finalJ).getRadius())) {

                        dx = -dx;

                    }


                    if ((circArray.get(finalJ).getLayoutY() >= (bounds.getMaxY() - circArray.get(finalJ).getRadius())) ||
                            (circArray.get(finalJ).getLayoutY() <= (bounds.getMinY() + circArray.get(finalJ).getRadius()))) {

                        dy = -dy;

                    }

                }
            })));
            timeLineArray.get(j).setCycleCount(Timeline.INDEFINITE);
            timeLineArray.get(j).play();
        }


    }


    public static void main(String[] args) {
        launch(args);
    }

}

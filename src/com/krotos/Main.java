package com.krotos;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String CALC_FORM_PATH = "views/CalcForm.fxml";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final String TITLE = "Calculator";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(CALC_FORM_PATH));
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }


}

package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent parent= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/serverForm.fxml")));
        stage.setScene(new Scene(parent));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
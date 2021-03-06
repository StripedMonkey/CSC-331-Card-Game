package com.magicgui.magicthegathering;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MagicGui extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MagicGui.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Magic The Gathering");
        stage.setScene(scene);
        stage.show();
    }
}
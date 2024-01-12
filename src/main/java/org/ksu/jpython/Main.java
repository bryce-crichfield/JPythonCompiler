package org.ksu.jpython;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        Application.launch(EditorApplication.class, args);
    }

    public static class EditorApplication extends Application {
        @Override
        public void start(Stage stage) throws IOException {
            Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
            FXMLLoader fxmlLoader = new FXMLLoader(EditorApplication.class.getResource("EditorView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("JPython Editor");
            stage.setScene(scene);
            stage.show();

            stage.setWidth(800);
            stage.setHeight(600);
        }
    }
}

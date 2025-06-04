package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.File;

public class App extends Application {
    public void start(Stage stage) {

        VBoxRoot root = new VBoxRoot();
        Scene scene = new Scene(root,1000 ,600);
        stage.setScene(scene);
        stage.setTitle("APPLI Calculator");
        stage.centerOnScreen();
        stage.show();

        File css = new File("css"+File.separator+"style.css");
        scene.getStylesheets().add(css.toURI().toString());

    }
    public static void main(String[] args) {
        launch(args);
    }
}

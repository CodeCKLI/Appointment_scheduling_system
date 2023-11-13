package li.helper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

/**
 * Manages page switching and navigation within a JavaFX application
 */
public class PageHelper {

    /**
     * Loads and displays a new JavaFX scene based on the provided FXML file name when triggered by a mouse event
     * @param mouseEvent
     * @param fxmlName
     * @throws IOException
     */
    public static void switchPages(MouseEvent mouseEvent, String fxmlName) throws IOException {
        String URL = "../view/" + fxmlName + ".fxml";
        Parent root = FXMLLoader.load(Objects.requireNonNull(PageHelper.class.getResource(URL)));
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

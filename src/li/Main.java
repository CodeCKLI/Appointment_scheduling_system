package li;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import li.helper.JDBCHelper;

/**
 * Entry point for a JavaFX application
 */
public class Main extends Application {

    /**
     * Initializes the GUI with the login screen
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("C195 - Appointment scheduling system");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        JDBCHelper.openConnection();
        launch(args);
        JDBCHelper.closeConnection();
    }
}

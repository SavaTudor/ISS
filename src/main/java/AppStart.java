import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.Services;

public class AppStart extends Application {
    static LoginController loginController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Services service = new Services();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        AnchorPane root = loader.load();
        loginController = (LoginController) loader.getController();
        loginController.setService(service);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

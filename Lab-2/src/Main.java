import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
  private static final String url = "jdbc:mysql://localhost:3306";
  private static final String user = "root";
  private static final String password = "pass";
  private BorderPane rootView;
  public Stage stage;
  private DbConn dbConn;
  private ShowController showController;


  public static void main(String[] args) {
    launch();
  }


  @Override
  public void start(Stage stage) throws Exception {
    dbConn = new DbConn();
    this.stage = stage;
    stage.setTitle("MySQL app");
    initRootView();
    showController = new ShowController(rootView, dbConn);
    showController.showConnectView();
  }

  public void initRootView() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("Root.fxml"));
      rootView = loader.load();
      Scene scene = new Scene(rootView);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

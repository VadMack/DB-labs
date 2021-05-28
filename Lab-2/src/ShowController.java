import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ShowController {
  private BorderPane rootView;
  private DbConn dbConn;

  public ShowController(BorderPane rootView, DbConn dbConn) {
    this.rootView = rootView;
    this.dbConn = dbConn;
  }

  public void showConnectView() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("ConnectForm.fxml"));
      loader.setControllerFactory(c -> new ConnectionFormController(dbConn, this));
      AnchorPane connectForm = loader.load();
      rootView.setCenter(connectForm);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showSelectDBView() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("SelectDBView.fxml"));
      loader.setControllerFactory(c -> new SelectDBController(dbConn, this));
      AnchorPane selectDBView = loader.load();
      rootView.setCenter(selectDBView);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showTablesView(String database) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("TablesView.fxml"));
      loader.setControllerFactory(c -> new TablesViewController(dbConn, this, database));
      AnchorPane selectDBView = loader.load();
      rootView.setCenter(selectDBView);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFormController {

  private DbConn dbConn;
  private ShowController showController;

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private Button connect;

  @FXML
  private TextField address;

  @FXML
  private TextField user;

  @FXML
  private PasswordField password;

  @FXML
  private TextField port;

  @FXML
  private Text header;

  @FXML
  void initialize() {
    connect.setOnAction(actionEvent -> setCredentialsAndConnect());
  }

  public ConnectionFormController(DbConn dbConn, ShowController showController) {
    this.dbConn = dbConn;
    this.showController = showController;
  }

  private void setCredentialsAndConnect() {
    if (address.getText().trim().length() > 0 && port.getText().trim().length() > 0
            && user.getText().trim().length() > 0 && password.getText().trim().length() > 0) {
      dbConn.setUrl(address.getText().trim() + ":" + port.getText().trim());
      dbConn.setUser(user.getText().trim());
      dbConn.setPassword(password.getText().trim());

      try {
        dbConn.connect();
        if(dbConn.isConnectionAlive()){
          header.setText("connected as " + dbConn.getUser());
          showController.showSelectDBView();
        }
      } catch (SQLException exception) {
        header.setText("sql exception: check data in fields");
        System.out.println(exception.getMessage());
      }

    } else {
      header.setText("Please complete all fields");
    }
  }

}

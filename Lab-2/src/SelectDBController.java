import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SelectDBController {

  private DbConn dbConn;
  private ShowController showController;
  private MultipleSelectionModel<String> selectionModel = null;
  private String selectedDB = null;

  public SelectDBController(DbConn dbConn, ShowController showController) {
    this.dbConn = dbConn;
    this.showController = showController;
  }

  private void getDBList() {
    ResultSet resultSet = null;
    ObservableList<String> list = FXCollections.observableArrayList();
    try {
      resultSet = dbConn.getStatement().executeQuery("SHOW DATABASES");
    } catch (SQLException exception) {
      close();
    }
    try {
      assert resultSet != null;
      while (resultSet.next()) {
        if (resultSet.getString("Database").startsWith("db_")) {
          list.add(resultSet.getString("Database"));
        }
      }
    } catch (SQLException exception) {
      close();
    }
    dblist.setItems(list);
  }

  private void chooseDB() {
    if (selectedDB != null) {
      String toExecute = "USE " + selectedDB;
      try {
        dbConn.getStatement().executeQuery(toExecute);
        showController.showTablesView(selectedDB);
      } catch (SQLException exception) {
        close();
      }
    }
  }

  private void createDB() {
    if (newdbname.getText().trim().length() > 0){
      try {
        dbConn.createDB(newdbname.getText().trim());
        getDBList();
      } catch (SQLException exception) {
        System.out.println(exception.getMessage());
        close();
      }
    }
  }

  private void deleteDB(){
    if (selectedDB != null) {
      try {
        dbConn.deleteDB(selectedDB);
        getDBList();
      } catch (SQLException exception) {
        System.out.println(exception.getMessage());
        close();
      }
    }
  }

  private void close() {
    try {
      dbConn.getConnection().close();
    } catch (SQLException exception) {
      exception.printStackTrace();
    } finally {
      showController.showConnectView();
    }
  }

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private Button select;

  @FXML
  private Button createnew;

  @FXML
  private Button delete;

  @FXML
  private TextField newdbname;

  @FXML
  private ListView<String> dblist;

  @FXML
  void initialize() {
    select.setOnAction(actionEvent -> chooseDB());
    createnew.setOnAction(actionEvent -> createDB());
    delete.setOnAction(actionEvent -> deleteDB());
    getDBList();
    selectionModel = dblist.getSelectionModel();
    selectionModel.selectedItemProperty().addListener((observableValue, s, t1) -> selectedDB = t1);
  }

}

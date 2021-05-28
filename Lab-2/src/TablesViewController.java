import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TablesViewController {
  private DbConn dbConn;
  private ShowController showController;
  private String database;
  private TableView.TableViewSelectionModel<Player> playerSelectionModel;
  private TableView.TableViewSelectionModel<Club> clubSelectionModel;

  public TablesViewController(DbConn dbConn, ShowController showController, String database) {
    this.dbConn = dbConn;
    this.showController = showController;
    this.database = database;
  }

  private void showPlayers() {
    try {
      players.setItems(dbConn.getPlayers(database));
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    playerid.setCellValueFactory(new PropertyValueFactory<>("id"));
    playername.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    playerlname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    playerclubid.setCellValueFactory(new PropertyValueFactory<>("clubId"));
    playersalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
  }

  private void showClubs() {
    try {
      clubs.setItems(dbConn.getClubs(database));
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    clubid.setCellValueFactory(new PropertyValueFactory<>("id"));
    clubname.setCellValueFactory(new PropertyValueFactory<>("name"));
    clubcity.setCellValueFactory(new PropertyValueFactory<>("city"));
    clubcosts.setCellValueFactory(new PropertyValueFactory<>("costs"));
  }

  private void addPlayerDialog() {
    Dialog<Void> dialog = new Dialog();
    dialog.setTitle("Add player");
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    TextField name = new TextField();
    name.setPromptText("name");
    TextField lname = new TextField();
    lname.setPromptText("last name");
    TextField clubid = new TextField();
    clubid.setPromptText("club id");
    TextField salary = new TextField();
    salary.setPromptText("salary");
    grid.add(name, 1, 0);
    grid.add(lname, 2, 0);
    grid.add(clubid, 1, 1);
    grid.add(salary, 2, 1);
    Button ok = new Button();
    grid.add(ok, 1, 3);
    ok.setText("OK");
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
    ok.setOnAction(actionEvent -> {
      if (name.getText().trim().length() > 0 && lname.getText().trim().length() > 0 &&
              clubid.getText().trim().length() > 0 && salary.getText().trim().length() > 0) {
        try {
          dbConn.insertPlayer(database, name.getText().trim(), lname.getText().trim(),
                  clubid.getText().trim(), salary.getText().trim());
          showPlayers();
          showClubs();
          dialog.close();
        } catch (SQLException | NumberFormatException exception) {
          System.out.println(exception.getMessage());
          dialog.setTitle("Please make sure the input is correct");
        }
      }
    });
    dialog.getDialogPane().setContent(grid);
    dialog.show();
  }

  private void addClubDialog() {
    Dialog<Void> dialog = new Dialog();
    dialog.setTitle("Add club");
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    TextField name = new TextField();
    name.setPromptText("name");
    TextField city = new TextField();
    city.setPromptText("city");
    grid.add(name, 1, 0);
    grid.add(city, 1, 1);
    Button ok = new Button();
    grid.add(ok, 1, 2);
    ok.setText("OK");
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
    ok.setOnAction(actionEvent -> {
      if (name.getText().trim().length() > 0 && city.getText().trim().length() > 0) {
        try {
          dbConn.insertClub(database, name.getText().trim(), city.getText().trim());
          showPlayers();
          showClubs();
          dialog.close();
        } catch (SQLException | NumberFormatException exception) {
          System.out.println(exception.getMessage());
          dialog.setTitle("Please make sure the input is correct");
        }
      }
    });
    dialog.getDialogPane().setContent(grid);
    dialog.show();
  }

  private void updatePlayerDialog() {
    if (!playerSelectionModel.isEmpty()) {
      Player toUpdate = playerSelectionModel.getSelectedItem();
      Dialog<Void> dialog = new Dialog<>();
      dialog.setTitle("Update player");
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(20, 150, 10, 10));
      TextField name = new TextField();
      name.setPromptText("name");
      name.setText(toUpdate.getFirstName());
      TextField lname = new TextField();
      lname.setPromptText("last name");
      lname.setText(toUpdate.getLastName());
      TextField clubid = new TextField();
      clubid.setPromptText("club id");
      clubid.setText(Integer.toString(toUpdate.getClubId()));
      TextField salary = new TextField();
      salary.setPromptText("salary");
      salary.setText(Integer.toString(toUpdate.getSalary()));
      grid.add(name, 1, 0);
      grid.add(lname, 2, 0);
      grid.add(clubid, 1, 1);
      grid.add(salary, 2, 1);
      Button ok = new Button();
      grid.add(ok, 1, 3);
      ok.setText("OK");
      dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
      ok.setOnAction(actionEvent -> {
        if (name.getText().trim().length() > 0 && lname.getText().trim().length() > 0 &&
                clubid.getText().trim().length() > 0 && salary.getText().trim().length() > 0) {
          try {
            dbConn.updatePlayer(database, name.getText().trim(), lname.getText().trim(),
                    clubid.getText().trim(), salary.getText().trim(), toUpdate.getId());
            showPlayers();
            showClubs();
            dialog.close();
          } catch (SQLException | NumberFormatException exception) {
            System.out.println(exception.getMessage());
            dialog.setTitle("Please make sure the input is correct");
          }
        }
      });
      dialog.getDialogPane().setContent(grid);
      dialog.show();
    }
  }

  private void updateClubDialog() {
    if (!clubSelectionModel.isEmpty()) {
      Club toUpdate = clubSelectionModel.getSelectedItem();
      Dialog<Void> dialog = new Dialog<>();
      dialog.setTitle("Update club");
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(20, 150, 10, 10));
      TextField name = new TextField();
      name.setPromptText("name");
      name.setText(toUpdate.getName());
      TextField city = new TextField();
      city.setPromptText("city name");
      city.setText(toUpdate.getCity());
      grid.add(name, 1, 0);
      grid.add(city, 2, 0);
      Button ok = new Button();
      grid.add(ok, 1, 3);
      ok.setText("OK");
      dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
      ok.setOnAction(actionEvent -> {
        if (name.getText().trim().length() > 0 && city.getText().trim().length() > 0) {
          try {
            dbConn.updateClub(database, name.getText().trim(), city.getText().trim(), toUpdate.getId());
            showPlayers();
            showClubs();
            dialog.close();
          } catch (SQLException | NumberFormatException exception) {
            System.out.println(exception.getMessage());
            dialog.setTitle("Please make sure the input is correct");
          }
        }
      });
      dialog.getDialogPane().setContent(grid);
      dialog.show();
    }
  }

  private void searchPlayerDialog() {
    Dialog<Void> dialog = new Dialog<>();
    dialog.setTitle("Search player");
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    TextField lname = new TextField();
    lname.setPromptText("last name");
    grid.add(lname, 1, 0);
    Button ok = new Button();
    grid.add(ok, 1, 3);
    ok.setText("OK");
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
    ok.setOnAction(actionEvent -> {
      if (lname.getText().trim().length() > 0) {
        ObservableList<Player> list = players.getItems();
        playerSelectionModel.clearSelection();
        for (int i = 0; i < list.size(); i++) {
          if (list.get(i).getLastName().equals(lname.getText().trim())) {
            players.requestFocus();
            playerSelectionModel.select(i);
            break;
          }
        }
        dialog.close();
      } else {
        dialog.setTitle("Please make sure the input is correct");
      }
    });
    dialog.getDialogPane().setContent(grid);
    dialog.show();
  }

  private void searchClubDialog() {
    Dialog<Void> dialog = new Dialog<>();
    dialog.setTitle("Search club");
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    TextField name = new TextField();
    name.setPromptText("Club name");
    grid.add(name, 1, 0);
    Button ok = new Button();
    grid.add(ok, 1, 3);
    ok.setText("OK");
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
    ok.setOnAction(actionEvent -> {
      if (name.getText().trim().length() > 0) {
        ObservableList<Club> list = clubs.getItems();
        clubSelectionModel.clearSelection();
        for (int i = 0; i < list.size(); i++) {
          if (list.get(i).getName().equals(name.getText().trim())) {
            clubs.requestFocus();
            clubSelectionModel.select(i);
            break;
          }
        }
        dialog.close();
      } else {
        dialog.setTitle("Please make sure the input is correct");
      }
    });
    dialog.getDialogPane().setContent(grid);
    dialog.show();
  }

  public void deletePlayerDialog() {
    Dialog<Void> dialog = new Dialog<>();
    dialog.setTitle("Remove players");
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    TextField lname = new TextField();
    lname.setPromptText("Player last name");
    grid.add(lname, 1, 0);
    Button ok = new Button();
    grid.add(ok, 1, 3);
    ok.setText("OK");
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
    ok.setOnAction(actionEvent -> {
      if (lname.getText().trim().length() > 0) {
        try {
          dbConn.deletePlayers(database, lname.getText().trim());
          showClubs();
          showPlayers();
        } catch (SQLException exception) {
          System.out.println(exception.getMessage());
        }
        dialog.close();
      } else {
        dialog.setTitle("Please make sure the input is correct");
      }
    });
    dialog.getDialogPane().setContent(grid);
    dialog.show();
  }

  public void deleteClubDialog() {
    Dialog<Void> dialog = new Dialog<>();
    dialog.setTitle("Remove club");
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    TextField name = new TextField();
    name.setPromptText("Club name");
    grid.add(name, 1, 0);
    Button ok = new Button();
    grid.add(ok, 1, 3);
    ok.setText("OK");
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
    ok.setOnAction(actionEvent -> {
      if (name.getText().trim().length() > 0) {
        try {
          dbConn.deleteClubs(database, name.getText().trim());
          showClubs();
          showPlayers();
        } catch (SQLException exception) {
          System.out.println(exception.getMessage());
        }
        dialog.close();
      } else {
        dialog.setTitle("Please make sure the input is correct");
      }
    });
    dialog.getDialogPane().setContent(grid);
    dialog.show();
  }

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private TableView<Player> players;

  @FXML
  private TableView<Club> clubs;

  @FXML
  private TableColumn<Club, Integer> clubid;

  @FXML
  private TableColumn<Club, String> clubname;

  @FXML
  private TableColumn<Club, String> clubcity;

  @FXML
  private TableColumn<Club, Integer> clubcosts;

  @FXML
  private MenuItem addplayer;

  @FXML
  private MenuItem addclub;

  @FXML
  private MenuItem updateplayer;

  @FXML
  private MenuItem updateclub;

  @FXML
  private MenuItem deleteplayer;

  @FXML
  private MenuItem deleteclub;

  @FXML
  private MenuItem findplayer;

  @FXML
  private MenuItem findclub;

  @FXML
  private Button closedb;

  @FXML
  private Text dbname;

  @FXML
  private TableColumn<Player, Integer> playerid;

  @FXML
  private TableColumn<Player, String> playername;

  @FXML
  private TableColumn<Player, String> playerlname;

  @FXML
  private TableColumn<Player, Integer> playerclubid;

  @FXML
  private TableColumn<Player, Integer> playersalary;


  @FXML
  void initialize() {
    dbname.setText(database);
    closedb.setOnAction(actionEvent -> showController.showSelectDBView());
    playerSelectionModel = players.getSelectionModel();
    clubSelectionModel = clubs.getSelectionModel();
    playerSelectionModel.setSelectionMode(SelectionMode.SINGLE);
    clubSelectionModel.setSelectionMode(SelectionMode.SINGLE);
    showPlayers();
    showClubs();
    addplayer.setOnAction(actionEvent -> addPlayerDialog());
    addclub.setOnAction(actionEvent -> addClubDialog());
    updateplayer.setOnAction(actionEvent -> updatePlayerDialog());
    updateclub.setOnAction(actionEvent -> updateClubDialog());
    findplayer.setOnAction(actionEvent -> searchPlayerDialog());
    findclub.setOnAction(actionEvent -> searchClubDialog());
    deleteplayer.setOnAction(actionEvent -> deletePlayerDialog());
    deleteclub.setOnAction(actionEvent -> deleteClubDialog());
  }

}

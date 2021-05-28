import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DbConn {
  private String url, user, password;
  private Connection connection = null;
  private Statement statement = null;

  public void connect() throws SQLException {
    connection = DriverManager.getConnection(url + "/?allowMultiQueries=true&user=" + user + "&password=" + password);
    statement = connection.createStatement();

  }

  public void createDB(String name) throws SQLException {
    name = "db_" + name;
    String toExecute = "CREATE DATABASE " + name;
    statement.executeUpdate(toExecute);
    toExecute = "USE " + name;
    statement.execute(toExecute);
    toExecute =
            "CREATE TABLE clubs\n" +
                    "(\n" +
                    "    id           INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    name         VARCHAR(255),\n" +
                    "    city_name    VARCHAR(255),\n" +
                    "    salary_costs INT UNSIGNED\n" +
                    "); " +
                    "CREATE TABLE players\n" +
                    "(\n" +
                    "    id         INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    first_name VARCHAR(255),\n" +
                    "    last_name  VARCHAR(255),\n" +
                    "    club_id    INT UNSIGNED,\n" +
                    "    salary     INT UNSIGNED NOT NULL DEFAULT 0,\n" +
                    "    FOREIGN KEY (club_id) REFERENCES clubs (id)\n" +
                    ");" +
                    "CREATE PROCEDURE show_players_contents()\n" +
                    "BEGIN \n" +
                    " SELECT * FROM players;\n" +
                    "END;" +
                    "\n" +
                    "CREATE PROCEDURE show_clubs_contents()\n" +
                    "BEGIN \n" +
                    " SELECT * FROM clubs;\n" +
                    "END;" +
                    "\n" +
                    "CREATE PROCEDURE truncate_players()\n" +
                    "BEGIN \n" +
                    "  TRUNCATE TABLE players;\n" +
                    "END;" +
                    "\n" +
                    "CREATE PROCEDURE truncate_clubs()\n" +
                    "BEGIN \n" +
                    "  TRUNCATE TABLE clubs;\n" +
                    "END;" +
                    "\n" +
                    "CREATE PROCEDURE insert_club(name varchar(255), city varchar(255))\n" +
                    "BEGIN \n" +
                    "  INSERT INTO clubs (name, city_name, salary_costs) VALUES (name, city, 0);\n" +
                    "END;" +
                    "\n" +
                    "CREATE PROCEDURE insert_player(fname varchar(255), lname varchar(255), club int, payment int)\n" +
                    "BEGIN \n" +
                    "  INSERT INTO players (first_name, last_name, club_id, salary)\n" +
                    "  VALUES (fname, lname, club, payment);\n" +
                    "END;" +
                    "\n" +
                    "CREATE PROCEDURE find_in_clubs(club_name varchar(255))\n" +
                    "BEGIN \n" +
                    "  SELECT * FROM clubs WHERE name = club_name;\n" +
                    "END;" +
                    "\n" +
                    "CREATE PROCEDURE find_in_players(player_lname varchar(255))\n" +
                    "BEGIN \n" +
                    "  SELECT * FROM players WHERE last_name = player_lname;\n" +
                    "END;\n" +
                    "CREATE PROCEDURE update_player(player_name varchar(255), player_lname varchar(255), player_club int, payment int, player_id int unsigned) " +
                    "BEGIN " +
                    "UPDATE players " +
                    "SET first_name = player_name, last_name = player_lname, club_id = player_club, salary = payment " +
                    "WHERE id = player_id; " +
                    "END;" +
                    "\n" +
                    "CREATE PROCEDURE update_club(club_name varchar(255), club_city varchar(255), club_id int unsigned) " +
                    "BEGIN " +
                    "UPDATE clubs " +
                    "SET name = club_name, city_name = club_city " +
                    "WHERE id = club_id; " +
                    "END;" +
                    "\n" +
                    "CREATE PROCEDURE remove_players(lname varchar(255)) " +
                    "BEGIN " +
                    "DELETE FROM players where last_name = lname; " +
                    "END;" +
                    "\n" +
                    "CREATE PROCEDURE remove_clubs(cname varchar(255)) " +
                    "BEGIN " +
                    "DELETE FROM clubs where name = cname; " +
                    "END;" +
                    "\n" +
                    "CREATE TRIGGER TRG_insert\n" +
                    "    AFTER INSERT\n" +
                    "    ON players\n" +
                    "    FOR EACH ROW\n" +
                    "BEGIN\n" +
                    "    UPDATE clubs\n" +
                    "    SET salary_costs=(SELECT SUM(salary)\n" +
                    "                      FROM players\n" +
                    "                      WHERE NEW.club_id = club_id)\n" +
                    "    where clubs.id = NEW.club_id" +
                    ";\n" +
                    "END;\n" +
                    "\n" +
                    "CREATE TRIGGER TRG_update\n" +
                    "    AFTER UPDATE\n" +
                    "    ON players\n" +
                    "    FOR EACH ROW\n" +
                    "BEGIN\n" +
                    "    UPDATE clubs\n" +
                    "    SET salary_costs=(SELECT SUM(salary)\n" +
                    "                      FROM players\n" +
                    "                    WHERE NEW.club_id = club_id)\n" +
                    "    where clubs.id = NEW.club_id;\n" +
                    "END;\n" +
                    "\n" +
                    "CREATE TRIGGER TRG_delete\n" +
                    "    AFTER DELETE\n" +
                    "    ON players\n" +
                    "    FOR EACH ROW\n" +
                    "BEGIN\n" +
                    "    UPDATE clubs\n" +
                    "    SET salary_costs=(SELECT SUM(salary)\n" +
                    "                      FROM players\n" +
                    "                      WHERE club_id = id)\n" +
                    "    where clubs.id = OLD.club_id;\n" +
                    "END;" +
                    "CREATE INDEX IND_last_name ON players (last_name);";
    statement.executeUpdate(toExecute);
  }

  public ObservableList<Player> getPlayers(String db) throws SQLException {
    String toExecute = "USE " + db;
    statement.executeQuery(toExecute);
    ObservableList<Player> players = FXCollections.observableArrayList();
    PreparedStatement preparedStatement = connection.prepareStatement("CALL show_players_contents");
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
      players.add(new Player(resultSet.getString("first_name"),
              resultSet.getString("last_name"),
              resultSet.getInt("id"),
              resultSet.getInt("club_id"),
              resultSet.getInt("salary")));
    }
    return players;
  }

  public ObservableList<Club> getClubs(String db) throws SQLException {
    String toExecute = "USE " + db;
    statement.executeQuery(toExecute);
    ObservableList<Club> clubs = FXCollections.observableArrayList();
    PreparedStatement preparedStatement = connection.prepareStatement("CALL show_clubs_contents");
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
      clubs.add(new Club(resultSet.getString("name"),
              resultSet.getString("city_name"),
              resultSet.getInt("id"),
              resultSet.getInt("salary_costs")));
    }
    return clubs;
  }

  public void deleteDB(String name) throws SQLException {
    String toExecute = "DROP DATABASE " + name;
    statement.executeUpdate(toExecute);
  }

  public void insertPlayer(String db, String name, String lname, String clubid, String salary) throws SQLException {
    String toExecute = "USE " + db;
    statement.executeUpdate(toExecute);
    PreparedStatement preparedStatement = connection.prepareStatement("CALL insert_player(?, ?, ?, ?)");
    preparedStatement.setString(1, name);
    preparedStatement.setString(2, lname);
    preparedStatement.setInt(3, Integer.parseInt(clubid));
    preparedStatement.setInt(4, Integer.parseInt(salary));
    preparedStatement.executeUpdate();
  }

  public void updatePlayer(String db, String name, String lname, String clubid, String salary, int id) throws SQLException {
    String toExecute = "USE " + db;
    statement.executeUpdate(toExecute);
    PreparedStatement preparedStatement = connection.prepareStatement("CALL update_player(?, ?, ?, ?, ?)");
    preparedStatement.setString(1, name);
    preparedStatement.setString(2, lname);
    preparedStatement.setInt(3, Integer.parseInt(clubid));
    preparedStatement.setInt(4, Integer.parseInt(salary));
    preparedStatement.setInt(5, id);
    preparedStatement.executeUpdate();
  }

  public void deletePlayers(String db, String lname) throws SQLException {
    String toExecute = "USE " + db;
    statement.executeUpdate(toExecute);
    PreparedStatement preparedStatement = connection.prepareStatement("CALL remove_players(?)");
    preparedStatement.setString(1, lname);
    preparedStatement.executeUpdate();
  }

  public void insertClub(String db, String name, String city) throws SQLException {
    String toExecute = "USE " + db;
    statement.executeUpdate(toExecute);
    PreparedStatement preparedStatement = connection.prepareStatement("CALL insert_club(?, ?)");
    preparedStatement.setString(1, name);
    preparedStatement.setString(2, city);
    preparedStatement.executeUpdate();
  }

  public void updateClub(String db, String name, String city, int id) throws SQLException {
    String toExecute = "USE " + db;
    statement.executeUpdate(toExecute);
    PreparedStatement preparedStatement = connection.prepareStatement("CALL update_club(?, ?, ?)");
    preparedStatement.setString(1, name);
    preparedStatement.setString(2, city);
    preparedStatement.setInt(3, id);
    preparedStatement.executeUpdate();
  }

  public void deleteClubs(String db, String name) throws SQLException {
    String toExecute = "USE " + db;
    statement.executeUpdate(toExecute);
    PreparedStatement preparedStatement = connection.prepareStatement("CALL remove_clubs(?)");
    preparedStatement.setString(1, name);
    preparedStatement.executeUpdate();
  }

  public DbConn() {
  }

  public boolean isConnectionAlive() {
    boolean result = false;
    try {
      result = connection.isValid(2000);
    } catch (SQLException exception) {
      System.out.println(exception.getMessage());
    }
    return result;
  }

  public DbConn(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  public Statement getStatement() {
    return statement;
  }

  public void setStatement(Statement statement) {
    this.statement = statement;
  }
}

public class Club {
  private String name;
  private String city;
  private int id;
  private int costs;

  public Club(String name, String city, int id, int costs) {
    this.name = name;
    this.city = city;
    this.id = id;
    this.costs = costs;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCosts() {
    return costs;
  }

  public void setCosts(int costs) {
    this.costs = costs;
  }
}

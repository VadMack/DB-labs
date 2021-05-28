public class Player {
  private String firstName;
  private String lastName;
  private int id;
  private int clubId;
  private int salary;

  public Player(String firstName, String lastName, int id, int clubId, int salary) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.id = id;
    this.clubId = clubId;
    this.salary = salary;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getClubId() {
    return clubId;
  }

  public void setClubId(int clubId) {
    this.clubId = clubId;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }
}

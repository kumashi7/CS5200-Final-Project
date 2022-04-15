package movierank.model;

public class Persons {
  protected String name_id;
  protected String name_;
  protected int birth_year;
  protected int death_year;

  public Persons(String name_id, String name_, int birth_year, int death_year) {
    this.name_id = name_id;
    this.name_ = name_;
    this.birth_year = birth_year;
    this.death_year = death_year;
  }

  public String getName_id() {
    return name_id;
  }

  public void setName_id(String name_id) {
    this.name_id = name_id;
  }

  public String getName_() {
    return name_;
  }

  public void setName_(String name_) {
    this.name_ = name_;
  }

  public int getBirth_year() {
    return birth_year;
  }

  public void setBirth_year(int birth_year) {
    this.birth_year = birth_year;
  }

  public int getDeath_year() {
    return death_year;
  }

  public void setDeath_year(int death_year) {
    this.death_year = death_year;
  }
}

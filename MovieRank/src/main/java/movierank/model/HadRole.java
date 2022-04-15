package movierank.model;


public class HadRole {
  protected Movies movies;
  protected Persons persons;
  protected String role_;

  public HadRole(Movies movies, Persons persons, String role_) {
    this.movies = movies;
    this.persons = persons;
    this.role_ = role_;
  }

  public Movies getMovies() {
    return movies;
  }

  public void setMovies(Movies movies) {
    this.movies = movies;
  }

  public Persons getPersons() {
    return persons;
  }

  public void setPersons(Persons persons) {
    this.persons = persons;
  }

  public String getRole_() {
    return role_;
  }

  public void setRole_(String role_) {
    this.role_ = role_;
  }
}

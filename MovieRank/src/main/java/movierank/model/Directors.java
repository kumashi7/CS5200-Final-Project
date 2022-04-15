package movierank.model;

public class Directors extends Persons{

	Movies movie;

	public Directors(String name_id, String name_, int birth_year, int death_year, Movies movie) {
		super(name_id, name_, birth_year, death_year);
		this.movie = movie;
	}

	public Movies getMovie() {
		return movie;
	}

	public void setMovie(Movies movie) {
		this.movie = movie;
	}
}

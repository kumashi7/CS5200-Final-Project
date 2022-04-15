package movierank.model;


public class Writer extends Persons{

	protected Movies movie;
	protected String nameId;
	
	public Writer(String nameId, String name, int birthYear, int deathYear, Movies movie) {
		super(nameId, name, birthYear, deathYear);
		this.movie = movie;
		this.nameId = nameId;
	}
	
	public String getNameId() {
		return nameId;
	}
	
	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	
	public Movies getMovie() {
		return movie;
	}

	public void setMovie(Movies movie) {
		this.movie = movie;
	}
}

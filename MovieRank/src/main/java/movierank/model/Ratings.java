// Qiushi Liang


package movierank.model;

public class Ratings {
	  protected String titleId;
	  protected double averageRating;
	  protected int numVotes;
	  protected Movies movie;

	  public Ratings(String titleId, double averageRating, int numVotes, Movies movie) {
	    this.titleId = titleId;
	    this.averageRating = averageRating;
	    this.numVotes = numVotes;
	    this.movie = movie;
	  }

	  public String getTitleId() {
	    return titleId;
	  }

	  public double getAverageRating() {
	    return averageRating;
	  }

	  public int getNumVotes() {
	    return numVotes;
	  }

	  public Movies getMovie() {
	    return movie;
	  }

	  public void setMovie(Movies movie) {
	    this.movie = movie;
	  }

	  public void setTitleId(String titleId) {
	    this.titleId = titleId;
	  }

	  public void setAverageRating(double averageRating) {
	    this.averageRating = averageRating;
	  }

	  public void setNumVotes(int numVotes) {
	    this.numVotes = numVotes;
	  }
	}
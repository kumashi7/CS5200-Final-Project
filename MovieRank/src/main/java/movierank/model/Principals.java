// Qiushi Liang


package movierank.model;


public class Principals extends Persons {
	  protected String titleId;
	  protected int ordering;
	  protected String jobCategory;
	  protected String job;
	  protected Movies movie;

	  public Principals(String titleId, int ordering, String nameId, String jobCategory,
	      String job, String name_, int birthYear, int deathYear, Movies movie) {
		super(nameId, name_, birthYear, deathYear);
	    this.titleId = titleId;
	    this.ordering = ordering;
	    this.jobCategory = jobCategory;
	    this.job = job;
	    this.movie = movie;
	  }

	  public Movies getMovie() {
	    return movie;
	  }

	  public void setMovie(Movies movie) {
	    this.movie = movie;
	  }

	  public String getTitleId() {
	    return titleId;
	  }

	  public void setTitleId(String titleId) {
	    this.titleId = titleId;
	  }

	  public int getOrdering() {
	    return ordering;
	  }

	  public void setOrdering(int ordering) {
	    this.ordering = ordering;
	  }

	  public String getJobCategory() {
	    return jobCategory;
	  }

	  public void setJobCategory(String jobCategory) {
	    this.jobCategory = jobCategory;
	  }

	  public String getJob() {
	    return job;
	  }

	  public void setJob(String job) {
	    this.job = job;
	  }
}
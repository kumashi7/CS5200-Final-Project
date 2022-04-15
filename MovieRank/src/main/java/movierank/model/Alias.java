package movierank.model;

public class Alias {
	protected Movies movie;
	protected int ordering;
	protected String title;
	protected String region;
	protected String language;
	protected boolean isOriginalTitle;
	
	public Alias(Movies movie, int ordering, String title, String region, String language, boolean isOriginalTitle) {
		super();
		this.movie = movie;
		this.ordering = ordering;
		this.title = title;
		this.region = region;
		this.language = language;
		this.isOriginalTitle = isOriginalTitle;
	}
	
	
	public Movies getMovie() {
		return movie;
	}
	public void setMovie(Movies movie) {
		this.movie = movie;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isOriginalTitle() {
		return isOriginalTitle;
	}
	public void setOriginalTitle(boolean isOriginalTitle) {
		this.isOriginalTitle = isOriginalTitle;
	}
}

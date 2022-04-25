package movierank.model;

public class Movies {
	protected String title_id;
	protected String primary_title;
	protected String title_type;
	protected String original_title;
	protected boolean is_Adult;
	protected int start_year;
	protected int end_year;
	protected int runtime_minutes;
	protected String genre;
	
	public Movies(String title_id, String primary_title, String title_type,  String original_title, boolean is_Adult, int start_year, int end_year,int runtime_minutes) {
		this.title_id = title_id;
		this.primary_title = primary_title;
		this.title_type = title_type;
		this.original_title = original_title;
		this.is_Adult = is_Adult;
		this.start_year = start_year;
		this.end_year = end_year;
		this.runtime_minutes = runtime_minutes;
	}
	
	public Movies(String title_id) {
		this.title_id = title_id;
	}
	
	public Movies(String title_id, String genre) {
		this.title_id = title_id;
		this.genre = genre;
	}
	
	public Movies(String primary_title, String title_type,  String original_title, boolean is_Adult, int start_year, int end_year,int runtime_minutes) {
		this.primary_title = primary_title;
		this.title_type = title_type;
		this.original_title = original_title;
		this.is_Adult = is_Adult;
		this.start_year = start_year;
		this.end_year = end_year;
		this.runtime_minutes = runtime_minutes;
	}
	
	public String getTitle_id() {
		return title_id;
	}

	public void setTitle_id(String title_id) {
		this.title_id = title_id;
	}

	public String getPrimary_title() {
		return primary_title;
	}

	public void setPrimary_title(String primary_title) {
		this.primary_title = primary_title;
	}

	public String getTitle_type() {
		return title_type;
	}

	public void setTitle_type(String title_type) {
		this.title_type = title_type;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public boolean is_Adult() {
		return is_Adult;
	}

	public void setIs_Adult(boolean is_Adult) {
		this.is_Adult = is_Adult;
	}

	public int getStart_year() {
		return start_year;
	}

	public void setStart_year(int start_year) {
		this.start_year = start_year;
	}

	public int getEnd_year() {
		return end_year;
	}
	
	public String getGenre() {
		return this.genre;
	}

	public void setEnd_year(int end_year) {
		this.end_year = end_year;
	}

	public int getRuntime_minutes() {
		return runtime_minutes;
	}

	public void setRuntime_minutes(int runtime_minutes) {
		this.runtime_minutes = runtime_minutes;
	}
	
}

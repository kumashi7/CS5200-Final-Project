package movierank.model;

public class AliasType {

	Alias alias;
	String type;

	public AliasType(Alias alias, String type) {
		super();
		this.alias = alias;
		this.type = type;
	}
	
	public Alias getAlias() {
		return alias;
	}

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

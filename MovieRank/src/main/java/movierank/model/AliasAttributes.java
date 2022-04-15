package movierank.model;

public class AliasAttributes {

	Alias alias;
	String attribute;
	
	

	public AliasAttributes(Alias alias, String attribute) {
		super();
		this.alias = alias;
		this.attribute = attribute;
	}
	
	public Alias getAlias() {
		return alias;
	}

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}

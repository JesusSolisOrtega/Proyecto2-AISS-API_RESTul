package aiss.model;

public class Author {
	
	private String id;
	private String name;
	private String first_name;
	private String birth_date;
	
	public Author() {
	}
	
	public Author (String id, String name, String first_name, String birth_date) {
			this.id = id;
			this.name = name;
			this.first_name = first_name;
			this.birth_date = birth_date;	
	}
	
	public Author (String name, String first_name, String birth_date) {
		this.name = name;
		this.first_name = first_name;
		this.birth_date = birth_date;	
}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	
}

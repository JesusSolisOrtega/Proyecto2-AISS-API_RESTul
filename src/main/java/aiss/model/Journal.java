package aiss.model;

import java.util.ArrayList;
import java.util.List;

public class Journal {

	private String id;
	private List<Publication> publications;
	private String director;
	private String first_pub;
	private String name;
	private String owner;
	
	public Journal() {}
	
	public Journal (String name) {
		this.name=name;
	}
	
	public Journal (String name, String director, String first_pub, String owner, String id) {
		this.director=director;
		this.owner=owner;
		this.id=id;
		this.first_pub=first_pub;
		this.name=name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getFirst_pub() {
		return first_pub;
	}

	public void setFirst_pub(String first_pub) {
		this.first_pub = first_pub;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public 	Publication getPublication(String id) {
		if(publications==null)
			return null;
		
		Publication publication =null;
		for(Publication p:publications)
			if(p.getId().equals(id)) {
				publication=p;
				break;
			}
		return publication;
	}
	
	public void addPublication(Publication p) {
		if (publications==null)
			publications = new ArrayList<Publication>();
		publications.add(p);
	}
	
	public void deletePublication(Publication p) {
		publications.remove(p);
	}
	
	public void deletePublication(String id) {
		Publication p = getPublication(id);
		if (p!=null)
			publications.remove(p);
	}
	
}

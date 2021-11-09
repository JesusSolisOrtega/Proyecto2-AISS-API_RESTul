package aiss.model;

import java.util.ArrayList;
import java.util.List;

public class Publication {
	
	private String id;
	private List<Author> authors;
	private String title;
	private String date;
	private String genre;
	
	public Publication() {		
	}

	public Publication(String title, String date, String genre) {
		this.title=title;
		this.date=date;
		this.genre=genre;
	}
	
	public Publication(String title, String date, String genre,String id) {
		this.id=id;
		this.title=title;
		this.date=date;
		this.genre=genre;
	}
	
	public Publication(String title) {
		this.title=title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public Author getAuthor(String id) {
		if(authors==null)
			return null;
		
		Author author =null;
		for(Author a:authors)
			if(a.getId().equals(id)){
				author=a;
				break;
			}
		return author;
	}
	
	public void addAuthor(Author a) {
		if (authors==null)
			authors = new ArrayList<Author>();
		authors.add(a);
	}
	
	public void deleteAuthor(Author a) {
		authors.remove(a);
	}
	
	public void deleteAuthor(String id) {
		Author a = getAuthor(id);
		if (a!=null)
			authors.remove(a);
	}
}

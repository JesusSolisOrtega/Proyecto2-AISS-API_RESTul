package aiss.model.repository;

import java.util.Collection;

import aiss.model.Author;
import aiss.model.Journal;
import aiss.model.Publication;

public interface JournalRepository {

	//Journals
	public void addJournal(Journal j);
	public Collection<Journal> getAllJournals();
	public Journal getJournal(String id);
	public void updateJournal(Journal j);
	public void deleteJournal(String id);
	public void addPublication(String journalId, String publicationId);
	public void removePublication(String journalId, String publicationId);
	public Collection<Publication> getAllPub(String journalId);
	
	//Publications
	public void addPublication(Publication p);
	public Collection<Publication> getAllPublications();
	public Publication getPublication(String id);
	public void updatePublication(Publication p);
	public void deletePublication(String id);
	public void addAuthor(String publicationId, String authorId);
	public void removeAuthor(String publicationId, String authorId);
	public Collection<Author> getAllAuth(String publicationId);
	
	//Authors
	public void addAuthor(Author a);
	public Collection<Author> getAllAuthors();
	public Author getAuthor(String id);
	public void updateAuthor(Author a);
	public void deleteAuthor(String authorId);
}

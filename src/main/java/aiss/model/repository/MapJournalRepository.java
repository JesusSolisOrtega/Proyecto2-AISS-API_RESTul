package aiss.model.repository;

import aiss.model.Journal;
import aiss.model.Publication;
import aiss.model.Author;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



public class MapJournalRepository implements JournalRepository {

	Map<String, Journal> journalMap;
	Map<String, Publication> publicationMap;
	Map<String, Author> authorMap;
	private static MapJournalRepository instance=null;
	private int index=0; 		//In order to identify the objects we are dealing with
	
	
	public void init() {
		
		journalMap = new HashMap<String,Journal>();
		publicationMap = new HashMap<String,Publication>();
		authorMap = new HashMap<String,Author>();
		
		
		// Create anonymous author - it is the first thing created in order to know its ID is "a0"
		Author anonymous = new Author();
		anonymous.setName("anonymous");
		anonymous.setFirst_name("");
		anonymous.setBirth_date("");
		addAuthor(anonymous);
		
		// Create journals
		Journal elPais =new Journal();
		elPais.setDirector("Javier Moreno Barber");
		elPais.setFirst_pub("4 de mayo de 1976");
		elPais.setName("El País");
		elPais.setOwner("Grupo PRISA");
		addJournal(elPais);
		
		Journal as= new Journal();
		as.setDirector("Vicente Jiménez");
		as.setFirst_pub("6 de diciembre de 1967");
		as.setName("As");
		as.setOwner("Grupo PRISA");
		addJournal(as);
		
		Journal marca= new Journal();
		marca.setName("Marca");
		marca.setFirst_pub("Domingo en la tarde");
		addJournal(marca);
		
		// Create publications
		Publication pub_1=new Publication();
		pub_1.setTitle("Atleti: partido a partido, susto a susto...");
		pub_1.setDate("13 de Mayo de 2021");
		pub_1.setGenre("Deporte");
		addPublication(pub_1);
		
		Publication pub_2=new Publication();
		pub_2.setTitle("El Banco de España advierte de los efectos persistentes de la crisis y pide un paquete integral de reformas");
		pub_2.setDate("13 de Mayo de 2021");
		pub_2.setGenre("Economía");
		addPublication(pub_2);
		
		Publication pub_3=new Publication();
		pub_3.setTitle("Oleada de inmigrantes en Ceuta");
		pub_3.setDate("17 de Mayo de 2021");
		pub_3.setGenre("Desgracia");
		addPublication(pub_3);

		//Create authors
		Author alrees = new Author();
		alrees.setName("Alfredo");
		alrees.setFirst_name("Relaño Estapé");
		alrees.setBirth_date("1951-01-13");
		addAuthor(alrees);
		
		Author joanro = new Author();
		joanro.setName("José Andrés");
		joanro.setFirst_name("Rojo");
		joanro.setBirth_date("1958-08-19");
		addAuthor(joanro);
		
		Author alveda = new Author();
		alveda.setName("Alejandro");
		alveda.setFirst_name("Verde Damasco");
		alveda.setBirth_date("1978-12-09");
		addAuthor(alveda);
		
		// Add publications to journals and authors to publications
		elPais.addPublication(pub_2);
		as.addPublication(pub_1);
		pub_1.addAuthor(alrees);
	}

	public static MapJournalRepository getInstance() {
		
		if(instance==null) {
			instance = new MapJournalRepository();
			instance.init();
		}
		return instance;
	}
	
	//Journal related operations
	@Override
	public void addJournal(Journal j) {
		String id = "j"+index++;
		j.setId(id);
		journalMap.put(id,j);
	}
	
	@Override
	public Collection<Journal> getAllJournals(){
		return journalMap.values();
		}
	
	@Override
	public Journal getJournal(String id) {
		return journalMap.get(id);
	}
	
	@Override
	public void updateJournal(Journal j) {
		journalMap.put(j.getId(),j);
	}
	
	@Override
	public void deleteJournal(String id) {
		journalMap.remove(id);
	}
	
	@Override
	public void addPublication(String journalId, String publicationId) {
		Journal journal = getJournal(journalId);
		journal.addPublication(publicationMap.get(publicationId));
	}
	
	@Override
	public void removePublication(String journalId, String publicationId) {
		Journal journal = getJournal(journalId);
		journal.deletePublication(publicationId);
	}
	
	@Override
	public Collection<Publication> getAllPub(String journalId){
		return getJournal(journalId).getPublications();
	}
	
	//Publication related operations
	@Override
	public void addPublication(Publication p) {
		String id = "p"+index++;
		p.setId(id);
		publicationMap.put(id,p);
	}
	
	@Override
	public Collection<Publication> getAllPublications(){
		return publicationMap.values();
		}
	
	@Override
	public Publication getPublication(String id) {
		return publicationMap.get(id);
	}
	
	@Override
	public void updatePublication(Publication p) {
		publicationMap.put(p.getId(),p);
	}
	
	@Override
	public void deletePublication(String id) {
		publicationMap.remove(id);
	}
	
	@Override
	public void addAuthor(String publicationId, String authorId) {
		Publication publication = getPublication(publicationId);
		publication.addAuthor(authorMap.get(authorId));
	}
	
	@Override
	public void removeAuthor(String publicationId, String authorId) {
		Publication publication = getPublication(publicationId);
		publication.deleteAuthor(authorId);
	}
	
	@Override
	public Collection<Author> getAllAuth(String publicationId){
		return getPublication(publicationId).getAuthors();
	}
	
	//Author related operations
	@Override
	public void addAuthor(Author a) {
		String id = "a"+index++;
		a.setId(id);
		authorMap.put(id,a);
	}
	
	@Override
	public Collection<Author> getAllAuthors(){
		return authorMap.values();
		}
	
	@Override
	public Author getAuthor(String id) {
		return authorMap.get(id);
	}
	
	@Override
	public void updateAuthor(Author a) {
		Author author = authorMap.get(a.getId());
		author.setName(a.getName());
		author.setBirth_date(a.getBirth_date());
		author.setFirst_name(a.getFirst_name());
	}
	
	@Override
	public void deleteAuthor(String authorId) {
		authorMap.remove(authorId);
	}
}

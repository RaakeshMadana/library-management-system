package library;

import java.util.List;
import java.util.ArrayList;

public class BookResult extends Book {

	private List<String> authors;

	public BookResult(String ISBN10, String ISBN13, String title, String cover, String publisher, int pages) {
		super(ISBN10, ISBN13, title, cover, publisher, pages);
		this.authors = null;
	}

	public void setAuthors(List<String> authors) {
		this.authors = new ArrayList<String>(authors);
	}

	public List<String> getAuthors() {
		return authors;
	}

}

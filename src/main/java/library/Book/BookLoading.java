package library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookLoading {

	@Autowired
	private BookRepository book;
	
	@RequestMapping("/loadbook")
	public Book loadBooks() {
		return book.getFirstBook();
	}
}

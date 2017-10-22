package library;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookService {

	@Autowired
	private BookRepository bookRepo;

	@RequestMapping("/book")
	public List<Book> sendBooks() {
		return bookRepo.getAllBooks();
	}
}

package library;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookService {
	
	@RequestMapping("/book")
	public Book sendBooks() {

		String fileName = "src/main/resources/books.csv";
		Book book = null;

		try {

			CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();	
			CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).withCSVParser(parser).build();

			String[] line = null;

			line = reader.readNext();
			book = new Book(line[0], line[1], line[2], line[4], line[5], Integer.parseInt(line[6])); 
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		
		return book;
	}
}

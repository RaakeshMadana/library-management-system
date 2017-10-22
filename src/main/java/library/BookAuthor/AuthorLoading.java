package library;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
public class AuthorLoading {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/loadauthor")
	public Set<String> loadAuthors() {
		
		String fileName = "src/main/resources/books.csv";
		Set<String> authors = new HashSet<String>();

		try {

			CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();
			CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).withCSVParser(parser).build();

			String[] line = null;

			while((line = reader.readNext()) != null) {
				String[] tempAuthors = line[3].split(",");
				for (String s: tempAuthors) {
					authors.add(s);
				}
			}

			for(String author: authors) {
				jdbcTemplate.update("INSERT INTO authors(name) VALUES(?)", author);
			}
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		
		return authors;
	}

}
		

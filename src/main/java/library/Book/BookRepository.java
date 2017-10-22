package library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@Repository
public class BookRepository {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public List<Book> getAllBooks() {
		return jdbcTemplate.query("SELECT * FROM book", bookMapper);
	}

	public int[] insertBooks(final List<Book> books) {
		List<Object[]> batch = new ArrayList<Object[]>();
		for(Book book : books) {
			Object[] values = new Object[] {
				book.getISBN10(),
				book.getISBN13(),
				book.getTitle(),
				book.getCover(),
				book.getPublisher(),
				book.getPages()};
			batch.add(values);
		}
		int[] updateCount = jdbcTemplate.batchUpdate("INSERT INTO book VALUES(?, ?, ?, ?, ?, ?)", batch);
		return updateCount;
	}

	public static final RowMapper<Book> bookMapper = new RowMapper<Book>() {
		public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
			Book book = new Book(rs.getString("ISBN10"), rs.getString("ISBN13"), rs.getString("title"), rs.getString("cover"), rs.getString("publisher"), rs.getInt("pages"));
			return book;
		}
	};

}

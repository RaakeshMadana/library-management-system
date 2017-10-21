package library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookRepository {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public Book getFirstBook(){
		return jdbcTemplate.queryForObject("SELECT * FROM book", bookMapper);
	}

	public static final RowMapper<Book> bookMapper = new RowMapper<Book>() {
		public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
			Book book = new Book(rs.getString("ISBN10"), rs.getString("ISBN13"), rs.getString("title"), rs.getString("cover"), rs.getString("publisher"), rs.getInt("pages"));
			return book;
		}
	};

}

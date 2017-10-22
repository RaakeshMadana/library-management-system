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
public class AuthorRepository {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public Integer getAuthorId(String name) {
		return jdbcTemplate.queryForObject("SELECT author_id FROM author WHERE name = ?", Integer.class, name);
/*		List<Integer> authors = jdbcTemplate.query("SELECT author_id FROM author WHERE name = ?", name, authorMapper);
		if(authors.isEmpty()){
			return null;
		}
		else {
			return authors.get(0);
		}*/
	}

	public String getAuthorName(String author_id) {
		return jdbcTemplate.queryForObject("SELECT name FROM author WHERE author_id = ?", String.class, author_id);
	}

	public int[] insertBookAuthors(final List<BookAuthor> bookAuthors) {
		List<Object[]> batch = new ArrayList<Object[]>();
		for(BookAuthor bookAuthor: bookAuthors) {
			Object[] values = new Object[] {
				bookAuthor.getAuthorId(),
				bookAuthor.getISBN13()};
			batch.add(values);
		}
		int [] updateCount = jdbcTemplate.batchUpdate("INSERT INTO book_authors VALUES(?, ?)", batch);
		return updateCount;
	}
	
	public static final RowMapper<Integer> authorMapper = new RowMapper<Integer>() {
		 public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			 return rs.getInt(1);
		 }
	};

}
